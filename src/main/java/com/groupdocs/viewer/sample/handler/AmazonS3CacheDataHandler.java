package com.groupdocs.viewer.sample.handler;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.aspose.ms.System.GC;
import com.groupdocs.viewer.common.IDisposable;
import com.groupdocs.viewer.common.MemoryStream;
import com.groupdocs.viewer.common.Path;
import com.groupdocs.viewer.domain.cache.*;
import com.groupdocs.viewer.exception.ArgumentNullException;
import com.groupdocs.viewer.exception.ArgumentOutOfRangeException;
import com.groupdocs.viewer.exception.InvalidOperationException;
import com.groupdocs.viewer.handler.cache.ICacheDataHandler;
import com.groupdocs.viewer.helper.comparer.Func2P;
import com.groupdocs.viewer.sample.storage.AmazonS3FileDataStore;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Amazon s 3 cache data handler.
 * @author Aleksey Permyakov (25.10.2016)
 */
public class AmazonS3CacheDataHandler implements ICacheDataHandler, IDisposable {
    /**
     * The attachements direcotry name
     */
    private String AttachementDirectoryName = "attachements";
    /**
     * The resources direcotry name
     */
    private String ResourcesDirecotoryName = "resources";
    /**
     * The page name prefix
     */
    private String PageNamePrefix = "page-";
    /**
     * The bucket name
     */
    private String _bucketName;
    /**
     * The Amazon S3 bucket directory name
     */
    private String _cacheDirectoryName;
    /**
     * The Amazon S3 client
     */
    private AmazonS3 _client;

    /**
     * Instantiates a new Amazon s 3 cache data handler.
     * @param bucketName the bucket name
     * @param cacheDirecotryName the cache direcotry name
     */
    public AmazonS3CacheDataHandler(String bucketName, String cacheDirecotryName) {
        if (StringUtils.isEmpty(bucketName))
            throw new ArgumentNullException("bucketName");
        _bucketName = bucketName;
        _cacheDirectoryName = cacheDirecotryName == null ? "cache" : cacheDirecotryName;
        _client = new AmazonS3Client(/*RegionEndpoint.USWest2*/);
    }

    /**
     * Existses the specified cache file description.
     * @param cacheFileDescription The cache file description.
     */
    public boolean exists(CacheFileDescription cacheFileDescription) throws Exception {
        try {
            String key = getFilePath(cacheFileDescription);
            GetObjectMetadataRequest request = new GetObjectMetadataRequest(_bucketName, key);
            request.setBucketName(_bucketName);
            request.setKey(key);
            _client.getObjectMetadata(request);
            return true;
        } catch (AmazonS3Exception amazonS3Exception) {
            if (amazonS3Exception.getErrorCode() != null &&
                    amazonS3Exception.getErrorCode().equals("NotFound")) {
                return false;
            }
            if (amazonS3Exception.getErrorCode() != null &&
                    (amazonS3Exception.getErrorCode().equals("InvalidAccessKeyId") ||
                            amazonS3Exception.getErrorCode().equals("InvalidSecurity"))) {
                throw new Exception("Please check the provided AWS Credentials. " +
                        "If you haven't signed up for Amazon S3, please visit http://aws.amazon.com/s3");
            } else {
                throw new Exception(String.format("An error occurred with the message '%s' when getting object metadata.", amazonS3Exception.getMessage()));
            }
        }
    }

    /**
     * Get stream with cached file
     * @param cacheFileDescription The cache file description.
     */
    public InputStream getInputStream(CacheFileDescription cacheFileDescription) throws Exception {
        try {
            String key = getFilePath(cacheFileDescription);
            final S3Object s3Object = _client.getObject(_bucketName, key);

            ByteArrayOutputStream memoryStream = new ByteArrayOutputStream();

            IOUtils.copy(s3Object.getObjectContent(), memoryStream);
            return new ByteArrayInputStream(memoryStream.toByteArray());
        } catch (AmazonS3Exception amazonS3Exception) {
            if (amazonS3Exception.getErrorCode() != null &&
                    (amazonS3Exception.getErrorCode().equals("InvalidAccessKeyId") ||
                            amazonS3Exception.getErrorCode().equals("InvalidSecurity"))) {
                throw new Exception("Please check the provided AWS Credentials. " +
                        "If you haven't signed up for Amazon S3, please visit http://aws.amazon.com/s3");
            } else {
                throw new Exception(String.format("An error occurred with the message '%s' when getting object metadata.", amazonS3Exception.getMessage()));
            }
        }
    }

    /**
     * The type Output save stream.
     */
    public class OutputSaveStream extends MemoryStream {
        private boolean _isExecuted;
        private Func2P<MemoryStream, Boolean> _executeWhenClosing;

        /**
         * Instantiates a new Output save stream.
         * @param executeWhenClosing the execute when closing
         */
        public OutputSaveStream(Func2P<MemoryStream, Boolean> executeWhenClosing) {
            _executeWhenClosing = executeWhenClosing;
        }

        @Override
        public void close() {
            try {
                if (!_isExecuted) {
                    _isExecuted = true;
                    MemoryStream ms = new MemoryStream();
                    this.setPosition(0);
                    IOUtils.copy(this.toInputStream(), ms.toOutputStream());
                    ms.setPosition(0);
                    _executeWhenClosing.invoke(ms);
                }
            } catch (Exception e) {
                e.printStackTrace();
                super.close();
            }
        }
    }


    /**
     * Prepare stream where file will be stored
     * @param cacheFileDescription The cache file description.
     */
    public OutputStream getOutputSaveStream(CacheFileDescription cacheFileDescription) throws Exception {
        final String key = getFilePath(cacheFileDescription);
        return new OutputSaveStream(new Func2P<MemoryStream, Boolean>() {
            @Override
            public Boolean invoke(MemoryStream memoryStream) {
                try {
                    PutObjectRequest request = new PutObjectRequest(_bucketName, key, memoryStream.toInputStream(), null);
                    _client.putObject(request);
                    return true;
                } catch (AmazonS3Exception amazonS3Exception) {
                    if (amazonS3Exception.getErrorCode() != null &&
                            (amazonS3Exception.getErrorCode().equals("InvalidAccessKeyId") ||
                                    amazonS3Exception.getErrorCode().equals("InvalidSecurity"))) {
                        System.out.println("Please check the provided AWS Credentials. " +
                                "If you haven't signed up for Amazon S3, please visit http://aws.amazon.com/s3");
                    } else {
                        System.out.println(String.format("An error occurred with the message '%s' when getting object metadata.", amazonS3Exception.getMessage()));
                    }
                }
                return false;
            }
        }).toOutputStream();
    }

    /**
     * Gets the last modification date.
     * @param cacheFileDescription The cache file description.
     */
    public Date getLastModificationDate(CachedPageDescription cacheFileDescription) throws Exception {
        try {
            String key = getFilePath(cacheFileDescription);
            GetObjectMetadataRequest request = new GetObjectMetadataRequest(_bucketName, key);
            request.setBucketName(_bucketName);
            request.setKey(key);
            final ObjectMetadata response = _client.getObjectMetadata(request);
            return response.getLastModified();
        } catch (AmazonS3Exception amazonS3Exception) {
            if (amazonS3Exception.getErrorCode() != null &&
                    (amazonS3Exception.getErrorCode().equals("InvalidAccessKeyId") ||
                            amazonS3Exception.getErrorCode().equals("InvalidSecurity"))) {
                throw new Exception("Please check the provided AWS Credentials. " +
                        "If you haven't signed up for Amazon S3, please visit http://aws.amazon.com/s3");
            } else {
                throw new Exception(String.format("An error occurred with the message '%s' when getting object metadata.", amazonS3Exception.getMessage()));
            }
        }
    }

    /**
     * Gets the html page resources folder path.
     * @param cachedPageDescription The cached page description
     */
    public String getHtmlPageResourcesFolder(CachedPageDescription cachedPageDescription) {
        String resourcesForPageFolderName = String.format("%s%s", PageNamePrefix, cachedPageDescription.getPageNumber());
        String relativeDirectoryName = AmazonS3FileDataStore.PathHelper.ToRelativeDirectoryName(cachedPageDescription.getGuid());
        String path = Path.combine(
                _cacheDirectoryName,
                relativeDirectoryName,
                ResourcesDirecotoryName,
                resourcesForPageFolderName);
        return AmazonS3FileDataStore.PathHelper.NormalizePath(path);
    }

    /**
     * Gets the html page resources descriptions.
     * @param cachedPageDescription The cached page description
     * @return List of page resources descriptions
     */
    public List<CachedPageResourceDescription> getHtmlPageResources(CachedPageDescription cachedPageDescription) throws Exception {
        try {
            String resourcesFolder = getHtmlPageResourcesFolder(cachedPageDescription);
            ListObjectsRequest request = new ListObjectsRequest();
            request.setBucketName(_bucketName);
            request.setPrefix(resourcesFolder);
            final ObjectListing response = _client.listObjects(request);
            List<CachedPageResourceDescription> result = new ArrayList<CachedPageResourceDescription>();
            for (S3ObjectSummary entry : response.getObjectSummaries()/*S3Objects*/) {
                CachedPageResourceDescription resource =
                        new CachedPageResourceDescription(cachedPageDescription, Path.getFileName(entry.getKey()));
                result.add(resource);
            }
            return result;
        } catch (AmazonS3Exception amazonS3Exception) {
            if (amazonS3Exception.getErrorCode() != null &&
                    (amazonS3Exception.getErrorCode().equals("InvalidAccessKeyId") ||
                            amazonS3Exception.getErrorCode().equals("InvalidSecurity"))) {
                throw new Exception("Please check the provided AWS Credentials. " +
                        "If you haven't signed up for Amazon S3, please visit http://aws.amazon.com/s3");
            } else {
                throw new Exception(String.format("An error occurred with the message '%s' when getting object metadata.", amazonS3Exception.getMessage()));
            }
        }
    }

    /**
     * Gets the path to the cached document.
     * @param cacheFileDescription The cached document description
     */
    public String getFilePath(CacheFileDescription cacheFileDescription) {
        String path;
        switch (cacheFileDescription.getCacheFileType()) {
            case CacheFileType.Page:
                path = getPageFilePath(cacheFileDescription);
                break;
            case CacheFileType.PageResource:
                path = getResourceFilePath(cacheFileDescription);
                break;
            case CacheFileType.Attachment:
                path = getAttachmentFilePath(cacheFileDescription);
                break;
            case CacheFileType.Document:
                path = getDocumentFilePath(cacheFileDescription);
                break;
            default:
                throw new ArgumentOutOfRangeException();
        }
        return AmazonS3FileDataStore.PathHelper.NormalizePath(path);
    }

    /**
     * Clears files from cache older than specified time interval.
     * @param olderThan The time interval.
     */
    public void clearCache(long olderThan) throws Exception {
        try {
            DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(_bucketName);
            multiObjectDeleteRequest.setBucketName(_bucketName);
            _client.deleteObjects(multiObjectDeleteRequest);
        } catch (AmazonS3Exception amazonS3Exception) {
            if (amazonS3Exception.getErrorCode() != null &&
                    (amazonS3Exception.getErrorCode().equals("InvalidAccessKeyId") ||
                            amazonS3Exception.getErrorCode().equals("InvalidSecurity"))) {
                throw new Exception("Please check the provided AWS Credentials. " +
                        "If you haven't signed up for Amazon S3, please visit http://aws.amazon.com/s3");
            } else {
                throw new Exception(String.format("An error occurred with the message '%s' when getting object metadata.", amazonS3Exception.getMessage()));
            }
        }
    }

    /**
     * Gets file path.
     * @param cacheFileDescription Cache file description.
     * @return File path.
     */
    private String getDocumentFilePath(CacheFileDescription cacheFileDescription) {
        CachedDocumentDescription document = (CachedDocumentDescription) cacheFileDescription;
        if (document == null)
            throw new InvalidOperationException(
                    "cacheFileDescription object should be an instance of CachedDocumentDescription class");
        String documentName = Path.changeExtension(document.getName(), document.getOutputExtension());
        String documentFolder = buildCachedDocumentFolderPath(document);
        return Path.combine(documentFolder, documentName);
    }

    /**
     * Builds the cache folder path.
     * @param cachedPageDescription The cache file description.
     */
    private String buildCachedDocumentFolderPath(CachedDocumentDescription cachedPageDescription) {
        String docFolder = AmazonS3FileDataStore.PathHelper.ToRelativeDirectoryName(cachedPageDescription.getGuid());
        return Path.combine(_cacheDirectoryName, docFolder);
    }

    /**
     * Gets the path to the cached attachment document
     * @param cacheFileDescription The cached attachment description
     */
    private String getAttachmentFilePath(CacheFileDescription cacheFileDescription) {
        CachedAttachmentDescription attachmentDescription = (CachedAttachmentDescription) cacheFileDescription;
        if (attachmentDescription == null)
            throw new InvalidOperationException(
                    "cacheFileDescription object should be an instance of CachedAttachmentDescription class");
        String docFolder = AmazonS3FileDataStore.PathHelper.ToRelativeDirectoryName(attachmentDescription.getGuid());
        return Path.combine(_cacheDirectoryName,
                docFolder,
                AttachementDirectoryName,
                attachmentDescription.getAttachmentName());
    }

    /**
     * Gets resource file path.
     * @param cacheFileDescription The cached file description.
     * @return The resource file path.
     */
    private String getResourceFilePath(CacheFileDescription cacheFileDescription) {
        CachedPageResourceDescription resourceDescription = (CachedPageResourceDescription) cacheFileDescription;
        if (resourceDescription == null)
            throw new InvalidOperationException(
                    "cacheFileDescription object should be an instance of CachedPageResourceDescription class");
        String resourcesPath = getHtmlPageResourcesFolder(resourceDescription.getCachedPageDescription());
        return Path.combine(resourcesPath, resourceDescription.getResourceName());
    }

    /**
     * Gets page file path.
     * @param cacheFileDescription The cached file description.
     * @return The page file path.
     */
    private String getPageFilePath(CacheFileDescription cacheFileDescription) {
        CachedPageDescription pageDescription = (CachedPageDescription) cacheFileDescription;
        if (pageDescription == null)
            throw new InvalidOperationException(
                    "cacheFileDescription object should be an instance of CachedPageDescription class");
        String fileName = buildPageFileName(pageDescription);
        String folder = buildCachedPageFolderPath(pageDescription);
        return Path.combine(folder, fileName);
    }

    /**
     * Builds the file path for cached page.
     * @param cachedPageDescription The cache page description.
     */
    private String buildPageFileName(CachedPageDescription cachedPageDescription) {
        return String.format("%s%d.%s",
                PageNamePrefix,
                cachedPageDescription.getPageNumber(),
                StringUtils.isEmpty(cachedPageDescription.getOutputExtension()) ? "html" : cachedPageDescription.getOutputExtension());
    }

    /**
     * Builds the cache folder path.
     * @param cachedPageDescription The cache file description.
     */
    private String buildCachedPageFolderPath(CachedPageDescription cachedPageDescription) {
        String docFolder = AmazonS3FileDataStore.PathHelper.ToRelativeDirectoryName(cachedPageDescription.getGuid());
        String dimmensionsSubFolder = getDimensionsSubFolder(cachedPageDescription);
        return Path.combine(_cacheDirectoryName, docFolder, dimmensionsSubFolder);
    }

    /**
     * Gets dimmensions folder name.
     * @param cachedPageDescription The cached page description.
     * @return Dimmensions sub folder name e.g. 100x100px or 100x100px.pdf
     */
    private String getDimensionsSubFolder(CachedPageDescription cachedPageDescription) {
        if (cachedPageDescription.getWidth() == 0 && cachedPageDescription.getHeight() == 0)
            return "";
        return String.format("%sx%spx",
                cachedPageDescription.getWidth(),
                cachedPageDescription.getHeight());
    }

    /**
     * Indicates whether Dispose was called
     */
    private boolean _disposed;

    /**
     * Releases resources
     * @param disposing
     */
    private void Dispose(boolean disposing) {
        if (_disposed)
            return;
        if (disposing && _client != null) {
//            _client.dispose();
            _client = null;
        }
        _disposed = true;
    }

    /**
     * Releases resources
     */
    public void dispose() {
        Dispose(true);
        GC.suppressFinalize(this);
    }

    /**
     * The type Path helper.
     */
    public static class PathHelper {
        private static char PathDelimeter = '/';

        /**
         * Converts guid to relative directory name.
         * @param guid The guid.
         * @return Relative directory name.
         */
        public static String toRelativeDirectoryName(String guid) {
            if (StringUtils.isEmpty(guid))
                return "";
            String result = guid;
            char replacementCharacter = '_';
            if (Path.isPathRooted(result)) {
                String root = Path.getPathRoot(result);
                if (root.equals("\\"))
                    result = result.substring(root.length());
                if (root.contains(":"))
                    result = result.replace(':', replacementCharacter).replace('\\', replacementCharacter).replace('/', replacementCharacter);
            }
            if (result.startsWith("http") || result.startsWith("ftp"))
                result = result.replace(':', replacementCharacter).replace('\\', replacementCharacter).replace('/', replacementCharacter);

            result = result.replaceAll("[_]{2,}", new String(new char[]{replacementCharacter}));
            while (result.startsWith(new String(new char[]{replacementCharacter}))) {
                result = result.substring(1);
            }
            return result.replace('.', replacementCharacter);
        }

        /**
         * Replaces double slashes with path delimiter '/'
         * @param path The path.
         * @return Normalized path.
         */
        public static String normalizePath(String path) {
            String res = path.replaceAll("\\+", new String(new char[]{PathDelimeter}));
            while (res.startsWith(new String(new char[]{PathDelimeter}))) {
                res = res.substring(1);
            }
            while (res.endsWith(new String(new char[]{PathDelimeter}))) {
                res = res.substring(0, res.length() - 1);
            }
            return res;
        }
    }
}

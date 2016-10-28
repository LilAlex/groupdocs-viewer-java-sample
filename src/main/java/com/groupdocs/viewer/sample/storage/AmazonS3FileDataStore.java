package com.groupdocs.viewer.sample.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.aspose.ms.System.GC;
import com.aspose.ms.System.IDisposable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupdocs.viewer.domain.EmailFileData;
import com.groupdocs.viewer.domain.FileData;
import com.groupdocs.viewer.domain.FileDescription;
import com.groupdocs.viewer.domain.WordsFileData;
import com.groupdocs.viewer.helper.IFileDataStore;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * The type Amazon s 3 file data store.
 * @author Aleksey Permyakov (24.10.2016)
 */
public class AmazonS3FileDataStore implements IFileDataStore, IDisposable {
    /**
     * The Amazon S3 bucket name
     */
    private String _bucketName;
    /**
     * The cache directory name, default is "cache"
     */
    private String _cacheDirectoryName;
    /**
     * The Amazon S3 client
     */
    private AmazonS3 _client;

    /**
     * Instantiates a new Amazon s 3 file data store.
     * @param bucketName the bucket name
     * @param cacheDirecotryName the cache direcotry name
     * @throws Exception the exception
     */
    public AmazonS3FileDataStore(String bucketName, String cacheDirecotryName) throws Exception {
        if (StringUtils.isEmpty(bucketName))
            throw new Exception("bucketName");
        _bucketName = bucketName;
        _cacheDirectoryName = cacheDirecotryName == null ? "cache" : cacheDirecotryName;
        _client = new AmazonS3Client(/*RegionEndpoint.USWest2*/);
    }

    /**
     * Retrives file data, returns null if file data does not exist
     * @param fileDescription The file description.
     * @return The file data or null.
     */
    public FileData getFileData(FileDescription fileDescription) throws Exception {
        try {
            String key = getObjectKey(fileDescription);
            final S3Object s3Object = _client.getObject(_bucketName, key);
            return deserialize(s3Object.getObjectContent());
        } catch (AmazonS3Exception amazonS3Exception) {
            if (amazonS3Exception.getErrorCode() != null &&
                    amazonS3Exception.getErrorCode().equals("NoSuchKey")) {
                return null;
            }
            if (amazonS3Exception.getErrorCode() != null &&
                    (amazonS3Exception.getErrorCode().equals("InvalidAccessKeyId") ||
                            amazonS3Exception.getErrorCode().equals("InvalidSecurity"))) {
                throw new Exception("Please check the provided AWS Credentials. " +
                        "If you haven't signed up for Amazon S3, please visit http://aws.amazon.com/s3");
            } else {
                throw new Exception(String.format("An error occurred with the message '%s' when getting object stream.", amazonS3Exception.getMessage()));
            }
        }
    }

    /**
     * Saves file data for file description.
     * @param fileDescription The file description.
     * @param fileData The file data.
     */
    public void saveFileData(FileDescription fileDescription, FileData fileData) throws Exception {
        try {
            String key = getObjectKey(fileDescription);
            PutObjectRequest request = new PutObjectRequest(_bucketName, key, (String) null);
            request.setBucketName(_bucketName);
            request.setKey(key);
            request.setInputStream(serialize(fileData));
            _client.putObject(request);
        } catch (AmazonS3Exception amazonS3Exception) {
            if (amazonS3Exception.getErrorCode() != null &&
                    (amazonS3Exception.getErrorCode().equals("InvalidAccessKeyId") ||
                            amazonS3Exception.getErrorCode().equals("InvalidSecurity"))) {
                throw new Exception("Please check the provided AWS Credentials. " +
                        "If you haven't signed up for Amazon S3, please visit http://aws.amazon.com/s3");
            } else {
                throw new Exception(String.format("An error occurred with the message '%s' when putting object.", amazonS3Exception.getMessage()));
            }
        }
    }

    /**
     * Deserialize stream into FileData object.
     * @param stream The stream.
     * @return The file data object.
     */
    private FileData deserialize(InputStream stream) throws Exception {
        ObjectMapper xmlMapper = new ObjectMapper();
        try {
            if (xmlMapper.canDeserialize(xmlMapper.constructType(WordsFileData.class))) {
                return xmlMapper.readValue(stream, WordsFileData.class);
            }
            if (xmlMapper.canDeserialize(xmlMapper.constructType(EmailFileData.class))) {
                return xmlMapper.readValue(stream, EmailFileData.class);
            }
            if (xmlMapper.canDeserialize(xmlMapper.constructType(FileData.class))) {
                return xmlMapper.readValue(stream, FileData.class);
            }
        } catch (java.io.IOException e) {
            throw new Exception(e);
        }
        return null;
    }

    /**
     * Serialize file data objec into stream.
     * @param fileData The file data.
     * @return The stream.
     */
    private InputStream serialize(FileData fileData) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ObjectMapper xmlMapper = new ObjectMapper();
        xmlMapper.writeValue(stream, fileData);
        return new ByteArrayInputStream(stream.toByteArray());
    }

    /**
     * Gets the object key by file description.
     * @param fileDescription The file description.
     * @return The object key.
     */
    private String getObjectKey(FileDescription fileDescription) {
        final String fileName = "file-data.xml";
        String directoryPath =
                PathHelper.ToRelativeDirectoryName(fileDescription.getGuid());
        String path = _cacheDirectoryName + File.separator + directoryPath + File.separator + fileName;
        return PathHelper.NormalizePath(path);
    }

    /**
     * Indicates whether Dispose was called
     */
    private boolean _disposed;

    /**
     * Releases resources
     * @param disposing
     */
    private void dispose(boolean disposing) {
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
        dispose(true);
        GC.suppressFinalize(this);
    }


    /**
     * The type Path helper.
     */
    public static class PathHelper {
        private final char PathDelimeter = '/';

        /**
         * Converts guid to relative directory name.
         * @param guid The guid.
         * @return Relative directory name.
         */
        public static String ToRelativeDirectoryName(String guid) {
            if (StringUtils.isEmpty(guid))
                return "";
            String result = guid;
            final String replacementCharacter = "_";
            if (new File(result).isAbsolute()) {
                String root = new File(result).getAbsolutePath();
                if (root.equals("\\")) {
                    result = result.substring(root.length());
                }
                if (root.contains(":")) {
                    result = result.replace(":", replacementCharacter).replace("\\", replacementCharacter).replace("/", replacementCharacter);
                }
            }
            if (result.startsWith("http") || result.startsWith("ftp"))
                result = result.replace(":", replacementCharacter).replace("\\", replacementCharacter).replace("/", replacementCharacter);

            result = result.replaceAll("[_]{2,}", replacementCharacter);
            while (result.startsWith(replacementCharacter)) {
                result = result.substring(1);
            }
            return result.replace(".", replacementCharacter);
        }

        /**
         * Replaces double slashes with path delimiter "/"
         * @param path The path.
         * @return Normalized path.
         */
        public static String NormalizePath(String path) {
            path = path.replaceAll("\\+", File.separator);
            while (path.startsWith(File.separator)) {
                path = path.substring(1);
            }
            while (path.endsWith(File.separator)) {
                path = path.substring(0, path.length() - 1);
            }
            return path;
        }
    }
}
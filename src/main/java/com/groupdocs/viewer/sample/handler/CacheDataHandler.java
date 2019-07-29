package com.groupdocs.viewer.sample.handler;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.domain.cache.*;
import com.groupdocs.viewer.exception.ArgumentNullException;
import com.groupdocs.viewer.handler.cache.ICacheDataHandler;
import com.groupdocs.viewer.storage.IFileInfo;
import com.groupdocs.viewer.storage.IFileStorage;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Generic cache data handler
 * </p>
 */
public class CacheDataHandler implements ICacheDataHandler {
    private /* final */ ViewerConfig _viewerConfig;
    private /* final */ IFileStorage _fileStorage;

    /**
     * <p>
     * Strings to replace when file guid is an absolute path
     * </p>
     */
    private /* final */ List<String> _patternsToReplace = new ArrayList<String>();

    public CacheDataHandler(ViewerConfig viewerConfig, IFileStorage fileStorage) {
        if (viewerConfig == null)
            throw new IllegalArgumentException("viewerConfig");
        if (fileStorage == null)
            throw new IllegalArgumentException("fileStorage");

        _viewerConfig = viewerConfig;
        _fileStorage = fileStorage;

        _patternsToReplace.add(_viewerConfig.getCachePath());
        _patternsToReplace.add(_viewerConfig.getStoragePath());
    }

    /**
     * <p>
     * Indicates whether the specified cache file description exists.
     * </p>
     *
     * @param cacheFileDescription The cache file description.
     * @return System.Boolean.
     */
    public boolean exists(CacheFileDescription cacheFileDescription) {
        String path = getFilePath(cacheFileDescription);
        return _fileStorage.fileExists(path);
    }

    /**
     * <p>
     * Get stream with cached file
     * </p>
     *
     * @param cacheFileDescription The cache file description.
     * @return System.IO.Stream
     */
    public InputStream getInputStream(CacheFileDescription cacheFileDescription) {
        String path = getFilePath(cacheFileDescription);

        if (_fileStorage.fileExists(path)) {
            InputStream fileStream = _fileStorage.getFile(path);
            try /*JAVA: was using*/ {
                final byte[] bytes = IOUtils.toByteArray(fileStream);
                return new ByteArrayInputStream(bytes);
                } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * <p>
     * Prepare stream where file will be stored
     * </p>
     *
     * @param cacheFileDescription The cache file description.
     * @return System.IO.Stream
     */
    public OutputStream getOutputSaveStream(CacheFileDescription cacheFileDescription) {
        final String path = getFilePath(cacheFileDescription);

        return new ByteArrayOutputStream(){
            @Override
            public void close() {
                _fileStorage.saveFile(path, new ByteArrayInputStream(toByteArray()));
            }
        };
    }

    /**
     * <p>
     * Gets the html page resources folder path.
     * </p>
     *
     * @param cachedPageDescription The cached page description
     * @return System.String.
     */
    public String getHtmlPageResourcesFolder(CachedPageDescription cachedPageDescription) {
        String resourcesForPageFolderName = _viewerConfig.getPageNamePrefix() + cachedPageDescription.getPageNumber();

        String docFolder = cachedPageDescription.getGuid();
        for (String str : _patternsToReplace) {
            docFolder = docFolder.replace(str, "");
        }
        String result = _viewerConfig.getCachePath() + docFolder + "\\html\\resources\\" + resourcesForPageFolderName;

        if (result.contains("//"))
            result = result.replace("//", File.separator);

        if (!new File(result).mkdirs()) {
            System.err.println("Can't create directory");
        }

        return result;
    }

    /**
     * <p>
     * Gets the html page resources descriptions.
     * </p>
     *
     * @param cachedPageDescription The cached page description
     * @return List of page resources descriptions
     */
    public List<CachedPageResourceDescription> getHtmlPageResources(CachedPageDescription cachedPageDescription) {
        List<CachedPageResourceDescription> result = new ArrayList<CachedPageResourceDescription>();
        String resourcesFolder = getHtmlPageResourcesFolder(cachedPageDescription);

        List<IFileInfo> resources = _fileStorage.getFilesInfo(resourcesFolder);
        for (IFileInfo resouce : resources) {
            if (!resouce.isDirectory()) {
                CachedPageResourceDescription resource = new CachedPageResourceDescription(cachedPageDescription, new File(resouce.getPath()).getName());
                result.add(resource);
            }
        }

        return result;
    }

    /**
     * <p>
     * Gets the last modification date.
     * </p>
     *
     * @param cacheFileDescription The cache file description.
     * @return System.Nullable&lt;System.DateTime&gt;.
     */
    public Date getLastModificationDate(CacheFileDescription cacheFileDescription) {
        String path = getFilePath(cacheFileDescription);

        if (_fileStorage.fileExists(path)) {
            IFileInfo fileInfo = _fileStorage.getFileInfo(path);
            return fileInfo.getLastModified();
        }

        return null;
    }

    /**
     * <p>
     * Clears files from cache older than specified time interval.
     * </p>
     */
    public void clearCache() {
        _fileStorage.deleteDirectory(_viewerConfig.getCachePath());
    }

    /**
     * <p>
     * Clears cache files related to specified document.
     * </p>
     *
     * @param guid The file unique identifier, full path for local storage e.g. c:\\storage\\document.txt,
     *             relative path e.g document.txt, url e.g. http://site.com/document.txt.
     */
    public void clearCache(String guid) {

        for (String str : _patternsToReplace) {
            guid = guid.replace(str, "");
        }
        String fullFolderPath = _viewerConfig.getCachePath() + File.separator + guid;

        _fileStorage.deleteDirectory(fullFolderPath);
    }

    /**
     * <p>
     * Gets the path to the cached document.
     * </p>
     *
     * @param cacheFileDescription The cached document description
     * @return System.String.
     */
    public String getFilePath(CacheFileDescription cacheFileDescription) {
        String path = null;
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
        }

        return path;
    }

    private String getDocumentFilePath(CacheFileDescription cacheFileDescription) {
        CachedDocumentDescription document = (CachedDocumentDescription) cacheFileDescription;

        if (document == null)
            throw new RuntimeException("cacheFileDescription object should be an instance of CachedDocumentDescription class");

        String documentName = document.getName().replaceAll("\\.\\w+$", document.getOutputExtension());
        String documentFolder = buildCachedDocumentFolderPath(document);
        return documentFolder + File.separator + documentName;
    }

    private String buildCachedDocumentFolderPath(CachedDocumentDescription cachedPageDescription) {
        String docFolder = cachedPageDescription.getGuid();
        for (String str : _patternsToReplace) {
            docFolder = docFolder.replace(str, "");
        }

//        switch (_viewerConfig.getEngineType()) {
//            case EngineTypes.Image:
//                return PathExtension.combine(_viewerConfig.getCachePath(), docFolder, Constants.IMAGE_DIR_NAME);
//            case EngineTypes.Html:
//                return PathExtension.combine(_viewerConfig.getCachePath(), docFolder, Constants.HTML_DIR_NAME);
//        }
        return null;
    }

    private String getAttachmentFilePath(CacheFileDescription cacheFileDescription) {
        CachedAttachmentDescription attachment = (CachedAttachmentDescription) cacheFileDescription;
        if (attachment == null)
            throw new RuntimeException("attachment is null");

        String docFolder = attachment.getGuid();
        for (String str : _patternsToReplace) {
            docFolder = docFolder.replace(str, "");
        }

        return _viewerConfig.getCachePath() + File.separator + docFolder + File.separator + "attahments" + File.separator + attachment.getAttachmentName();
    }

    private String getResourceFilePath(CacheFileDescription cacheFileDescription) {
        CachedPageResourceDescription resourceDescription = (CachedPageResourceDescription) cacheFileDescription;

        if (resourceDescription == null)
            throw new RuntimeException("resourceDescription is null");

        String resourcesPath = getHtmlPageResourcesFolder(resourceDescription.getCachedPageDescription());
        return resourcesPath + File.separator + resourceDescription.getResourceName();
    }

    private String getPageFilePath(CacheFileDescription cacheFileDescription) {
        CachedPageDescription pageDescription = (CachedPageDescription) cacheFileDescription;

        if (pageDescription == null)
            throw new RuntimeException("pageDescription is null");

        String fileName = buildPageFileName(pageDescription);
        String folder = buildCachedPageFolderPath(pageDescription);
        return folder + File.separator + fileName;
    }

    private String buildPageFileName(CachedPageDescription cachedPageDescription) {
//        switch (_viewerConfig.getEngineType()) {
//            case EngineTypes.Image:
//                return StringExtensions.format("{0}{1}{2}", _viewerConfig.getPageNamePrefix(), cachedPageDescription.getPageNumber(), cachedPageDescription.getOutputExtension());
//            case EngineTypes.Html:
//                return StringExtensions.format("{0}{1}.html", _viewerConfig.getPageNamePrefix(), cachedPageDescription.getPageNumber());
//        }
        return null;
    }

    private String buildCachedPageFolderPath(CachedPageDescription cachedPageDescription) {
        String docFolder = cachedPageDescription.getGuid();
        for (String str : _patternsToReplace) {
            docFolder = docFolder.replace(str, "");
        }

//        switch (_viewerConfig.getEngineType()) {
//            case EngineTypes.Image:
//                String dimmensionsSubFolder = getDimensionsSubFolder(cachedPageDescription);
//                return PathExtension.combine(_viewerConfig.getCachePath(), docFolder, Constants.IMAGE_DIR_NAME, dimmensionsSubFolder);
//            case EngineTypes.Html:
//                return PathExtension.combine(_viewerConfig.getCachePath(), docFolder, Constants.HTML_DIR_NAME);
//        }
        return null;
    }

    private String getDimensionsSubFolder(CachedPageDescription cachedPageDescription) {
        return cachedPageDescription.getWidth() + "x" + cachedPageDescription.getHeight() + "px";
    }
}

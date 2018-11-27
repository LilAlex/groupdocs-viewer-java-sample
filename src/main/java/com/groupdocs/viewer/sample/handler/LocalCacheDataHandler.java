package com.groupdocs.viewer.sample.handler;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.domain.cache.*;
import com.groupdocs.viewer.handler.cache.ICacheDataHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

/**
 * <p>
 * Cache data handler default implementation
 * </p>
 *
 * @author GroupDocs.Viewer Team
 * @version 18.6
 */
public class LocalCacheDataHandler implements ICacheDataHandler {
    /**
     * <p>
     * The _viewer configuration
     * </p>
     */
    private final ViewerConfig _viewerConfig;

    /**
     * <p>
     * Strings to replace when file GUID is an absolute path
     * </p>
     */
    private final List<String> _patternsToReplace = new ArrayList<String>();

    /**
     * <p>
     * Initializes a new instance of the {@code LocalCacheDataHandler} class.
     * </p>
     *
     * @param viewerConfig The viewer configuration.
     */
    public LocalCacheDataHandler(ViewerConfig viewerConfig) {
        _viewerConfig = viewerConfig;
        _patternsToReplace.add(_viewerConfig.getCachePath());
        _patternsToReplace.add(_viewerConfig.getStoragePath());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Check if the specified cache file exist.
     * </p>
     *
     * @param cachedPageDescription The cache file description.
     * @return boolean
     */
    public boolean exists(CacheFileDescription cachedPageDescription) {
        String path = getFilePath(cachedPageDescription);
        return new File(path).exists();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get stream with cached file
     * </p>
     *
     * @param cachedPageDescription The cache file description.
     * @return input stream
     */
    public InputStream getInputStream(CacheFileDescription cachedPageDescription) {
        String path = getFilePath(cachedPageDescription);

        if (!exists(cachedPageDescription)) {
            String exceptionMethod = "Cache data handler can not find file, located in this path: '" + path + "'";
            throw new RuntimeException(exceptionMethod);
        }

        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Prepare stream where file will be stored
     * </p>
     *
     * @param cacheFileDescription The cache file description.
     * @return OutputStream
     */
    public OutputStream getOutputSaveStream(CacheFileDescription cacheFileDescription) {
        String path = getFilePath(cacheFileDescription);
        new File(path).getParentFile().mkdirs();
        try {
            return new FileOutputStream(path);
        } catch (java.io.FileNotFoundException e) {
            Logger.getAnonymousLogger().warning(e.toString());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Gets the HTML page resources folder path.
     * </p>
     *
     * @param cachedPageDescription The cached page description
     * @return System.String.
     */
    public String getHtmlPageResourcesFolder(CachedPageDescription cachedPageDescription) {
        String resourcesForPageFolderName = _viewerConfig.getPageNamePrefix() +  cachedPageDescription.getPageNumber();

        String docFolder = cachedPageDescription.getGuid();
        for (String str : _patternsToReplace) {
            docFolder = docFolder.replace(str, "");
        }
        String result = _viewerConfig.getCachePath() + docFolder + "\\html\\resources\\" + resourcesForPageFolderName;

        if (result.contains("//"))
            result = result.replace("//", File.separator);

        new File(result).mkdirs();

        return result;
    }

    public List<CachedPageResourceDescription> getHtmlPageResources(CachedPageDescription cachedPageDescription) {
        List<CachedPageResourceDescription> result = new ArrayList<CachedPageResourceDescription>();
        String resourcesFolder = getHtmlPageResourcesFolder(cachedPageDescription);

        java.io.File file = new java.io.File(resourcesFolder);
        final String[] resources;
        if (file.isAbsolute()) {
            List<String> entities = new ArrayList<String>();

            final File[] files = file.listFiles();
            if (files != null) {
                for (File fl : files) {
                    if (fl.isFile()) {
                        entities.add(fl.getName());
                    }
                }
            }
            resources = (String[]) entities.toArray();
        } else {
            List<String> entities = new ArrayList<String>();

            final File[] files = new File(new File("").getAbsolutePath() + java.io.File.separator + resourcesFolder).listFiles();
            if (files != null) {
                for (File fl : files) {
                    if (fl.isFile()) {
                        entities.add(fl.getName());
                    }
                }
            }
            resources = (String[]) entities.toArray();
        }

        for (String resourcePath : resources) {
            CachedPageResourceDescription resource = new CachedPageResourceDescription(cachedPageDescription, new File(resourcePath).getName());
            result.add(resource);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Gets the last modification date.
     * </p>
     */
    public Date getLastModificationDate(CacheFileDescription cacheFileDescription) {
        String fullPath = getFilePath(cacheFileDescription);
        return new Date(new File(fullPath).lastModified());
    }

    //JAVA-added public wrapper for internalized method

    /**
     * {@inheritDoc}
     * <p>
     * Clears files from cache older than specified time interval.
     * </p>
     *
     * @param olderThan The time interval.
     */
    public void clearCache(long olderThan) {
        String cachePath = _viewerConfig.getCachePath();
        if (!new java.io.File(cachePath).isAbsolute()) {
            cachePath = new File("").getAbsolutePath() + java.io.File.separator + cachePath;
        }
        if (!new File(cachePath).exists()) {
            return;
        }

        clearCacheNoChecks(olderThan);
    }


    public void clearCache() {
        if (!new File(_viewerConfig.getCachePath()).exists())
            return;

        clearCacheNoChecks(0L);
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
        String fullFolderPath = _viewerConfig.getCachePath() + guid;

        if (!new File(fullFolderPath).exists())
            return;

        File[] files = new File("").listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        File[] directories = new File("").listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        for (File fileInfo : files) {
            try {
                if (fileInfo.exists()) {
                    //                    fileInfo.setReadOnly(false);
                    fileInfo.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //NOTE: ignore
            }
        }

        for (File dirInfo : directories) {
            try {
                if (dirInfo.exists()) {
                    dirInfo.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //NOTE: ignore
            }
        }

//            rootDirInfo.delete(true);
    }

    private void clearCacheNoChecks(long olderThan) {
        Date now = GregorianCalendar.getInstance().getTime();
        Date startFrom = new Date(now.getTime() - olderThan);

        String rootDirInfo = _viewerConfig.getCachePath();

        File[] files = new File("").listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        File[] directories = new File("").listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        for (File dirInfo : directories) {
            try {
                // Can't get creation time
                final Date lastModified = new Date(new java.io.File(dirInfo.getName()).lastModified());
                if (dirInfo.exists() && lastModified.before(startFrom) || olderThan == 0) {
                    dirInfo.delete();
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                //NOTE: ignore
            }
        }

        for (File fileInfo : files) {
            try {
                // Can't get creation time and change readonly
                final Date lastModified = new Date(fileInfo.lastModified());

                if (fileInfo.exists() && lastModified.before(startFrom) || olderThan == 0) {
//                    new java.io.File(fileInfo.getFullName()).setWritable(true); //fileInfo.setReadOnly(false);
                    fileInfo.delete();
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                //NOTE: ignore
            }
        }
    }

    /**
     * {@inheritDoc}
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
        return new File(path).getAbsolutePath();
    }

    private String getDocumentFilePath(CacheFileDescription cacheFileDescription) {
        CachedDocumentDescription document = (CachedDocumentDescription) cacheFileDescription;

        if (document == null) {
            throw new RuntimeException("document is null");
        }

        String documentName = document.getName().replaceAll("\\.\\w+$", "." + document.getOutputExtension());
        String documentFolder = buildCachedDocumentFolderPath(document);
        return documentFolder + File.separator + documentName;
    }

    /**
     * <p>
     * Builds the cache folder path.
     * </p>
     *
     * @param cachedPageDescription The cache file description.
     * @return System.String.
     */
    private String buildCachedDocumentFolderPath(CachedDocumentDescription cachedPageDescription) {


        String docFolder = cachedPageDescription.getGuid();
        for (String str : _patternsToReplace) {
            docFolder = docFolder.replace(str, "");
        }

//        switch (_viewerConfig.getEngineType()) {
//            case EngineTypes.Image:
//                return getPath().combine(_viewerConfig.getCachePath(), docFolder, Constants.IMAGE_DIR_NAME);
//
//            case EngineTypes.Html:
//                return getPath().combine(_viewerConfig.getCachePath(), docFolder, Constants.HTML_DIR_NAME);
//        }
        return null;
    }

    /**
     * <p>
     * Gets the path to the cached attachment document
     * </p>
     *
     * @param cacheFileDescription The cached attachment description
     * @return System.String
     */
    private String getAttachmentFilePath(CacheFileDescription cacheFileDescription) {
        CachedAttachmentDescription attach = (CachedAttachmentDescription) cacheFileDescription;

        if (attach == null) {
            throw new RuntimeException("attach is null");
        }

        String docFolder = attach.getGuid();
        for (String str : _patternsToReplace) {
            docFolder = docFolder.replace(str, "");
        }
        return _viewerConfig.getCachePath() +  docFolder + "attachments" + attach.getAttachmentName();
    }

    private String getResourceFilePath(CacheFileDescription cacheFileDescription) {
        CachedPageResourceDescription resourceDescription = (CachedPageResourceDescription) cacheFileDescription;

        if (resourceDescription == null) {
            throw new RuntimeException("resourceDescription is null");
        }

        String resourcesPath = getHtmlPageResourcesFolder(resourceDescription.getCachedPageDescription());
        return resourcesPath +  resourceDescription.getResourceName();
    }

    private String getPageFilePath(CacheFileDescription cacheFileDescription) {
        CachedPageDescription pageDescription = (CachedPageDescription) cacheFileDescription;

        if (pageDescription == null) {
            throw new RuntimeException("pageDescription is null");
        }

        String fileName = buildPageFileName(pageDescription);
        String folder = buildCachedPageFolderPath(pageDescription);
        return folder + fileName;
    }

    /**
     * <p>
     * Builds the file path for cached page.
     * </p>
     *
     * @param cachedPageDescription The cache page description.
     * @return System.String.
     */
    private String buildPageFileName(CachedPageDescription cachedPageDescription) {
//        switch (_viewerConfig.getEngineType()) {
//            case EngineTypes.Image:
//                String qualitySuffix = getQualitySuffix(cachedPageDescription);
//                return StringExtensions.format("{0}{1}{2}{3}", _viewerConfig.getPageNamePrefix(), Operators.boxing(cachedPageDescription.getPageNumber()), qualitySuffix, cachedPageDescription.getOutputExtension());
//
//            case EngineTypes.Html:
//                return StringExtensions.format("{0}{1}.html", _viewerConfig.getPageNamePrefix(), Operators.boxing(cachedPageDescription.getPageNumber()));
//        }
        return null;
    }

    /**
     * <p>
     * Builds the cache folder path.
     * </p>
     *
     * @param cachedPageDescription The cache file description.
     * @return System.String.
     */
    private String buildCachedPageFolderPath(CachedPageDescription cachedPageDescription) {

        String docFolder = cachedPageDescription.getGuid();
        for (String str : _patternsToReplace) {
            docFolder = docFolder.replace(str, "");
        }

//        switch (_viewerConfig.getEngineType()) {
//            case EngineTypes.Image:
//                String dimmensionsSubFolder = getDimensionsSubFolder(cachedPageDescription);
//                return getPath().combine(_viewerConfig.getCachePath(), docFolder, Constants.IMAGE_DIR_NAME, dimmensionsSubFolder);
//
//            case EngineTypes.Html:
//                return getPath().combine(_viewerConfig.getCachePath(), docFolder, Constants.HTML_DIR_NAME);
//        }
        return null;
    }

    /**
     * <p>
     * Gets the quality option suffix eg. -90
     * </p>
     *
     * @param cachedPageDescription The cached page description.
     * @return The quality suffix.
     */
    private String getQualitySuffix(CachedPageDescription cachedPageDescription) {
        return cachedPageDescription.getJpegQuality() != 90 && ".jpg".equals(cachedPageDescription.getOutputExtension()) ? "-" + cachedPageDescription.getJpegQuality() : "";
    }

    /**
     * <p>
     * Gets dimensions folder name.
     * </p>
     *
     * @param cachedPageDescription The cached page description.
     * @return Dimensions sub folder name e.g. 100x100px or 100x100px.pdf
     */
    private String getDimensionsSubFolder(CachedPageDescription cachedPageDescription) {
        final String usePdfIdentifier = ".pdf";
        return cachedPageDescription.getWidth() + "x" + cachedPageDescription.getHeight() + "px" + (cachedPageDescription.getExtractText() ? usePdfIdentifier : "");
    }
}

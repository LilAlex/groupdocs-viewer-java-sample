package com.groupdocs.viewer.sample.handler;

import com.amazonaws.util.StringUtils;
import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.domain.FileDescription;
import com.groupdocs.viewer.exception.ArgumentNullException;
import com.groupdocs.viewer.handler.input.IInputDataHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocalInputDataHandler implements IInputDataHandler {
    private final ViewerConfig _viewerConfig;

    public LocalInputDataHandler(ViewerConfig viewerConfig) {
        _viewerConfig = viewerConfig;
    }

    public InputStream getFile(String guid) {
        if (StringUtils.isNullOrEmpty(guid)) {
            throw new ArgumentNullException("guid");
        }

        String fullPath = getFullPath(guid);

        try {
            return new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public java.util.List<FileDescription> getEntities(String path) {
        if (path == null) {
            throw new ArgumentNullException("path");
        }

        File fullPath = new File(getFullPath(path));

        List<FileDescription> entities = new ArrayList<FileDescription>();

        final File[] files = fullPath.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    entities.add(new FileDescription(file.getAbsolutePath()));
                }
            }
        }

        return entities;
    }

    public Date getLastModificationDate(String guid) {
        String fullPath = getFullPath(guid);
        if (new java.io.File(fullPath).exists()) {
            final java.io.File file = new java.io.File(fullPath);
            return new Date(file.lastModified());
        } else {
            System.err.println("File " + fullPath + " does not exists!");
        }
        return null;
    }

    public FileDescription getFileDescription(String guid) {
        if (StringUtils.isNullOrEmpty(guid)) {
            throw new ArgumentNullException("guid");
        }
        String fullPath = getFullPath(guid);
        final File file = new File(fullPath);

        FileDescription fileDescription = new FileDescription(guid);

        if (file.exists()) {
            fileDescription.setLastModificationDate(new Date(file.lastModified()));
            fileDescription.setSize(file.length());
        }
        return fileDescription;
    }

    private String getFullPath(String guid) {
        if (guid.contains(":")) {
            return guid;
        }

        final String currentDirectory = ".";

        String withStorage = _viewerConfig.getStoragePath() + guid;
        if (StringUtils.isNullOrEmpty(withStorage)) {
            withStorage = currentDirectory;
        }

        return new File(withStorage).getAbsolutePath();
    }
}

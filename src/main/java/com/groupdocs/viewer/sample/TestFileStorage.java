package com.groupdocs.viewer.sample;

// ********* THIS FILE IS AUTO PORTED *********

import com.amazonaws.util.StringUtils;
import com.groupdocs.viewer.storage.FileInfo;
import com.groupdocs.viewer.storage.IFileInfo;
import com.groupdocs.viewer.storage.IFileStorage;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class TestFileStorage implements IFileStorage {
    private Map<String, byte[]> _files = new HashMap<String, byte[]>();

    public boolean fileExists(String path) {
        return _files.containsKey(path);
    }

    public InputStream getFile(String path) {
        byte[] file = _files.get(path);
        return new ByteArrayInputStream(file);
    }

    public void saveFile(String path, InputStream content) {
        try {
            _files.put(path, IOUtils.toByteArray(content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDirectory(String path) {
        throw new RuntimeException("deleteDirectory");
    }

    public IFileInfo getFileInfo(String path) {
        FileInfo info = new FileInfo();
        if (_files.containsKey(path)) {
            info.setPath(path);
            info.setLastModified(GregorianCalendar.getInstance().getTime());
            info.setSize(_files.get(path).length);
        }

        return info;
    }

    public List<IFileInfo> getFilesInfo(String path) {
        boolean getFilesFromRootDirectory = StringUtils.isNullOrEmpty(path) || path.equals(File.separator);

        List<IFileInfo> filesInfo = new ArrayList<IFileInfo>();
        for (Map.Entry<String, byte[]> file : _files.entrySet()) {
            if (getFilesFromRootDirectory || file.getKey().startsWith(path)) {
                String filePath = !StringUtils.isNullOrEmpty(path) && file.getKey().startsWith(path)
                        ? file.getKey().replace(path, "")
                        : file.getKey();

                filePath = filePath.contains(File.separator)
                        ? filePath.split(File.separator)[0]
                        : filePath;

                IFileInfo fileInfo = new FileInfo();
                fileInfo.setDirectory(!filePath.contains("."));
                fileInfo.setLastModified(GregorianCalendar.getInstance().getTime());
                fileInfo.setPath(filePath);
                fileInfo.setSize(file.getValue().length);

                filesInfo.add(fileInfo);
            }
        }
        return filesInfo;
    }
}

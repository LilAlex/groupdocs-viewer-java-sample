package com.groupdocs.viewer.sample.handler;

import com.groupdocs.viewer.domain.FileDescription;
import com.groupdocs.viewer.domain.cache.CachedDocumentDescription;
import com.groupdocs.viewer.domain.options.FileTreeOptions;
import com.groupdocs.viewer.handler.input.IInputDataHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Ftp input data handler.
 * @author Aleksey Permyakov (13.04.2016).
 */
public class FtpInputDataHandler implements IInputDataHandler {
    private final String _server = "ftp://localhost";
    private final String _userName = "anonymous";
    private final String _userPassword = "";

    @Override
    public FileDescription getFileDescription(String guid) throws Exception {
        return new FileDescription(guid);
    }

    @Override
    public InputStream getFile(String guid) throws Exception {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(_server);
            ftpClient.login(_userName, _userPassword);
            ftpClient.retrieveFile(guid, arrayOutputStream);
            ftpClient.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(arrayOutputStream.toByteArray());
    }

    @Override
    public Date getLastModificationDate(String guid) throws Exception {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(_server);
            ftpClient.login(_userName, _userPassword);
            FTPFile[] files = ftpClient.listFiles(guid);
            if (files.length > 0) {
                final FTPFile ftpFile = files[0];
                FileDescription fileDescription = new FileDescription(ftpFile.getName(), ftpFile.getName(), ftpFile.isDirectory());
                return fileDescription.getLastModificationDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<FileDescription> loadFileTree(FileTreeOptions fileTreeOptions) throws Exception {
        List<FileDescription> fileDescriptions = new ArrayList<FileDescription>();
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(_server);
            ftpClient.login(_userName, _userPassword);
            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile ftpFile : files) {
                FileDescription fileDescription = new FileDescription(ftpFile.getName(), ftpFile.getName(), ftpFile.isDirectory());
                fileDescriptions.add(fileDescription);
            }
            ftpClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileDescriptions;
    }

    @Override
    public void saveDocument(CachedDocumentDescription cachedDocumentDescription, InputStream documentStream) throws Exception {
        // ...
    }
}

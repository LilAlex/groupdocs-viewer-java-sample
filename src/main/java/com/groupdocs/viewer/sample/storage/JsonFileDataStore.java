package com.groupdocs.viewer.sample.storage;

import com.aspose.ms.System.ArgumentNullException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupdocs.viewer.common.Path;
import com.groupdocs.viewer.domain.FileData;
import com.groupdocs.viewer.domain.FileDescription;
import com.groupdocs.viewer.domain.WordsFileData;
import com.groupdocs.viewer.exception.DirectoryNotFoundException;
import com.groupdocs.viewer.helper.IFileDataStore;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * The type Json file data store.
 * @author Aleksey Permyakov (24.10.2016)
 */
public class JsonFileDataStore implements IFileDataStore {
    private final File _workingDirectory;

    /**
     * Instantiates a new Json file data store.
     * @param workingDirectory the working directory
     */
    public JsonFileDataStore(File workingDirectory) {
        if (workingDirectory == null)
            throw new ArgumentNullException("workingDirectory");
        if (!workingDirectory.exists())
            throw new DirectoryNotFoundException("Unable to find directory '" + workingDirectory.getName() + "'");

        _workingDirectory = workingDirectory;
    }

    public FileData getFileData(FileDescription fileDescription) throws IOException {
        if (fileDescription == null)
            throw new ArgumentNullException("fileDescription");

        String path = getFilePath(fileDescription);
        String json = FileUtils.readFileToString(new File(path));

        ObjectMapper xmlMapper = new ObjectMapper();
        if (fileDescription.getDocumentType().equals("Words")) {
            return xmlMapper.readValue(json.getBytes(), WordsFileData.class);
        }
        return xmlMapper.readValue(json.getBytes(), FileData.class);
    }

    public void saveFileData(FileDescription fileDescription, FileData fileData) throws IOException {
        if (fileDescription == null)
            throw new ArgumentNullException("fileDescription");
        if (fileData == null)
            throw new ArgumentNullException("fileData");

        String path = getFilePath(fileDescription);
        ObjectMapper xmlMapper = new ObjectMapper();
        String json = xmlMapper.writeValueAsString(fileData);

        String dir = new File(path).getName();
        if (dir != null && !new File(dir).exists())
            new File(dir).mkdirs();

        FileUtils.write(new File(path), json);
    }

    private String getFilePath(FileDescription fileDescription) {
        String directoryName = fileDescription.getGuid();
        final char replacementCharacter = '_';
        final String fileName = "file-data.json";

        char[] invalidChars = Path.getInvalidFileNameChars();
        for (char invalidChar : invalidChars)
            directoryName = directoryName.replace(invalidChar, replacementCharacter);

        String directoryPath = Path.combine(_workingDirectory.getName(), directoryName);
        return Path.combine(directoryPath, fileName);
    }
}

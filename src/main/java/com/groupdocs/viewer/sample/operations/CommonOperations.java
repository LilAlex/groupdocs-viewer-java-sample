package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.domain.PageData;
import com.groupdocs.viewer.domain.WindowsAuthenticationCredential;
import com.groupdocs.viewer.domain.containers.DocumentInfoContainer;
import com.groupdocs.viewer.domain.options.DocumentInfoOptions;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.sample.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

/**
 * The type Common operations.
 * @author Aleksey Permyakov (19.10.2016)
 */
public class CommonOperations {
    /**
     * Gets document information by guid 320.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getDocumentInformationByGuid320(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        // Get document information
        DocumentInfoOptions options = new DocumentInfoOptions(guid);
        DocumentInfoContainer documentInfo = htmlHandler.getDocumentInfo(options);

        System.out.println("DateCreated: " + documentInfo.getDateCreated());
        System.out.println("DocumentType: " + documentInfo.getDocumentType());
        System.out.println("Extension: " + documentInfo.getExtension());
        System.out.println("FileType: " + documentInfo.getFileType());
        System.out.println("Guid: " + documentInfo.getGuid());
        System.out.println("LastModificationDate: " + documentInfo.getLastModificationDate());
        System.out.println("Name: " + documentInfo.getName());
        System.out.println("PageCount: " + documentInfo.getPages().size());
        System.out.println("Size: " + documentInfo.getSize());

        for (PageData pageData : documentInfo.getPages()) {
            System.out.println("Page number: " + pageData.getNumber());
            System.out.println("Page name: " + pageData.getName());
        }
        System.out.println();
    }

    /**
     * Gets document information by guid 370.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getDocumentInformationByGuid370(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        // Get document information
        DocumentInfoOptions options = new DocumentInfoOptions();
        DocumentInfoContainer documentInfo = htmlHandler.getDocumentInfo(guid, options);

        System.out.println("DateCreated: " + documentInfo.getDateCreated());
        System.out.println("DocumentType: " + documentInfo.getDocumentType());
        System.out.println("DocumentTypeFormat: " + documentInfo.getDocumentTypeFormat());
        System.out.println("Extension: " + documentInfo.getExtension());
        System.out.println("FileType: " + documentInfo.getFileType());
        System.out.println("Guid: " + documentInfo.getGuid());
        System.out.println("LastModificationDate: " + documentInfo.getLastModificationDate());
        System.out.println("Name: " + documentInfo.getName());
        System.out.println("PageCount: " + documentInfo.getPages().size());
        System.out.println("Size: " + documentInfo.getSize());

        for (PageData pageData : documentInfo.getPages()) {
            System.out.println("Page number: " + pageData.getNumber());
            System.out.println("Page name: " + pageData.getName());
        }
        System.out.println();
    }

    /**
     * Sets custom font directory path.
     * @param config the config
     */
    public static void setCustomFontDirectoryPath(ViewerConfig config) {
        // Add custom fonts directories to FontDirectories list
        config.getFontDirectories().add(Utilities.FONTS_PATH);
        System.out.println("Font path: " + Utilities.FONTS_PATH);
        // Init viewer handler with config
//        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        System.out.println();
    }

    /**
     * Gets document information by stream.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getDocumentInformationByStream(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        // Get document stream
        InputStream stream = new FileInputStream(Utilities.STORAGE_PATH + File.separator + guid);
        // Get document information
        DocumentInfoOptions options = new DocumentInfoOptions();
        DocumentInfoContainer documentInfo = htmlHandler.getDocumentInfo(stream, options);

        System.out.println("DateCreated: " + documentInfo.getDateCreated());
        System.out.println("DocumentType: " + documentInfo.getDocumentType());
        System.out.println("DocumentTypeFormat: " + documentInfo.getDocumentTypeFormat());
        System.out.println("Extension: " + documentInfo.getExtension());
        System.out.println("FileType: " + documentInfo.getFileType());
        System.out.println("Guid: " + documentInfo.getGuid());
        System.out.println("LastModificationDate: " + documentInfo.getLastModificationDate());
        System.out.println("Name: " + documentInfo.getName());
        System.out.println("PageCount: " + documentInfo.getPages().size());
        System.out.println("Size: " + documentInfo.getSize());

        for (PageData pageData : documentInfo.getPages()) {
            System.out.println("Page number: " + pageData.getNumber());
            System.out.println("Page name: " + pageData.getName());
        }
        System.out.println();
    }

    /**
     * Gets document information by stream and name.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getDocumentInformationByStreamAndName(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        // Get document stream
        InputStream stream = new FileInputStream(Utilities.STORAGE_PATH + File.separator + guid);
        // Get document information
        DocumentInfoOptions options = new DocumentInfoOptions();
        DocumentInfoContainer documentInfo = htmlHandler.getDocumentInfo(stream, guid, options);
        System.out.println("DateCreated: " + documentInfo.getDateCreated());
        System.out.println("DocumentType: " + documentInfo.getDocumentType());
        System.out.println("DocumentTypeFormat: " + documentInfo.getDocumentTypeFormat());
        System.out.println("Extension: " + documentInfo.getExtension());
        System.out.println("FileType: " + documentInfo.getFileType());
        System.out.println("Guid: " + documentInfo.getGuid());
        System.out.println("LastModificationDate: " + documentInfo.getLastModificationDate());
        System.out.println("Name: " + documentInfo.getName());
        System.out.println("PageCount: " + documentInfo.getPages().size());
        System.out.println("Size: " + documentInfo.getSize());
        for (PageData pageData : documentInfo.getPages()) {
            System.out.println("Page number: " + pageData.getNumber());
            System.out.println("Page name: " + pageData.getName());
        }
        System.out.println();
    }

    /**
     * Gets document information by url.
     * @param htmlHandler the html handler
     * @param url the url
     * @throws Exception the exception
     */
    public static void getDocumentInformationByUrl(ViewerHtmlHandler htmlHandler, String url) throws Exception {
        URI uri = new URI(url);

        // Get document information
        DocumentInfoOptions options = new DocumentInfoOptions();
        DocumentInfoContainer documentInfo = htmlHandler.getDocumentInfo(uri, options);

        System.out.println("DateCreated: " + documentInfo.getDateCreated());
        System.out.println("DocumentType: " + documentInfo.getDocumentType());
        System.out.println("DocumentTypeFormat: " + documentInfo.getDocumentTypeFormat());
        System.out.println("Extension: " + documentInfo.getExtension());
        System.out.println("FileType: " + documentInfo.getFileType());
        System.out.println("Guid: " + documentInfo.getGuid());
        System.out.println("LastModificationDate: " + documentInfo.getLastModificationDate());
        System.out.println("Name: " + documentInfo.getName());
        System.out.println("PageCount: " + documentInfo.getPages().size());
        System.out.println("Size: " + documentInfo.getSize());

        for (PageData pageData : documentInfo.getPages()) {
            System.out.println("Page number: " + pageData.getNumber());
            System.out.println("Page name: " + pageData.getName());
        }
        System.out.println();
    }

    /**
     * Gets document information by url windows authentication credential.
     * @param htmlHandler the html handler
     * @param url the url
     * @throws Exception the exception
     */
    public static void getDocumentInformationByUrlWindowsAuthenticationCredential(ViewerHtmlHandler htmlHandler, String url) throws Exception {
        URI uri = new URI(url);
        WindowsAuthenticationCredential credential = new WindowsAuthenticationCredential("username", "password");
        // Get document information
        DocumentInfoOptions options = new DocumentInfoOptions();
        DocumentInfoContainer documentInfo = htmlHandler.getDocumentInfo(uri, credential, options);

        System.out.println("DateCreated: " + documentInfo.getDateCreated());
        System.out.println("DocumentType: " + documentInfo.getDocumentType());
        System.out.println("DocumentTypeFormat: " + documentInfo.getDocumentTypeFormat()
        );
        System.out.println("Extension: " + documentInfo.getExtension());
        System.out.println("FileType: " + documentInfo.getFileType());
        System.out.println("Guid: " + documentInfo.getGuid());
        System.out.println("LastModificationDate: " + documentInfo.getLastModificationDate());
        System.out.println("Name: " + documentInfo.getName());
        System.out.println("PageCount: " + documentInfo.getPages().size());
        System.out.println("Size: " + documentInfo.getSize());

        for (PageData pageData : documentInfo.getPages()) {
            System.out.println("Page number: " + pageData.getNumber());
            System.out.println("Page name: " + pageData.getName());
        }
        System.out.println();
    }
}

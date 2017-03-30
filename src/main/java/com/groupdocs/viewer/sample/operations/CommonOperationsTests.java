package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.domain.PageData;
import com.groupdocs.viewer.domain.WindowsAuthenticationCredential;
import com.groupdocs.viewer.domain.containers.DocumentInfoContainer;
import com.groupdocs.viewer.domain.options.DocumentInfoOptions;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.licensing.License;
import com.groupdocs.viewer.sample.Utilities;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import static com.groupdocs.viewer.sample.TestRunner.LICENSE_PATH;
import static com.groupdocs.viewer.sample.TestRunner.STORAGE_PATH;
import static com.groupdocs.viewer.sample.Utilities.applyLicense;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Aleksey Permyakov (13.03.2017).
 */
public class CommonOperationsTests {

    @Before
    public void before() {
        applyLicense();
    }

    @Test
    public void testLoadingALicenseFromFile() {
        Utilities.showTestHeader();
        // Path to license file
        String licensePath = LICENSE_PATH;
        License license = new License();
        // Set license
        license.setLicense(licensePath);
        System.out.println("License path: " + LICENSE_PATH);
//        assertTrue("License is not valid", License.isValidLicense());
    }

    @Test
    public void testLoadingALicenseFromAStreamObject() throws IOException {
        Utilities.showTestHeader();
        // Obtain license stream
        InputStream licenseStream = new FileInputStream(LICENSE_PATH);
        // Instantiate GroupDocs.Signature handler
        License license = new License();
        // setup license
        license.setLicense(licenseStream);
        System.out.println("License path: " + LICENSE_PATH);
//        assertTrue("License is not valid", License.isValidLicense());
    }

    @Test
    public void testGetDocumentInformationByGuid() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);

        String guid = "document.doc";
        // Get document information
        DocumentInfoOptions options = new DocumentInfoOptions();
        DocumentInfoContainer documentInfo = htmlHandler.getDocumentInfo(guid, options);

        System.out.println("\tDateCreated: " + documentInfo.getDateCreated());
        System.out.println("\tDocumentType: " + documentInfo.getDocumentType());
        System.out.println("\tDocumentTypeFormat: " + documentInfo.getDocumentTypeFormat());
        System.out.println("\tExtension: " + documentInfo.getExtension());
        System.out.println("\tFileType: " + documentInfo.getFileType());
        System.out.println("\tGuid: " + documentInfo.getGuid());
        System.out.println("\tLastModificationDate: " + documentInfo.getLastModificationDate());
        System.out.println("\tName: " + documentInfo.getName());
        System.out.println("\tPageCount: " + documentInfo.getPages().size());
        System.out.println("\tSize: " + documentInfo.getSize());

        assertNotEquals("Document size is incorrect", documentInfo.getSize(), 0);
        assertEquals("Document type is incorrect", documentInfo.getDocumentType(), "Words");
        assertEquals("File type is incorrect", documentInfo.getFileType(), "Doc");
        assertEquals("Page count is incorrect", documentInfo.getPages().size(), 2);
        for (PageData pageData : documentInfo.getPages()) {
            System.out.println("\t\tPage number: " + pageData.getNumber());
            System.out.println("\t\tPage name: " + pageData.getName());
        }
    }

    @Test
    public void testGetDocumentInformationByStream() throws Exception {
        Utilities.showTestHeader();
        String guid = "document.doc";
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        // Get document stream
        InputStream stream = new FileInputStream(STORAGE_PATH + File.separator + guid);
        // Get document information
        DocumentInfoOptions options = new DocumentInfoOptions();
        DocumentInfoContainer documentInfo = htmlHandler.getDocumentInfo(stream, options);

        System.out.println("\tDateCreated: " + documentInfo.getDateCreated());
        System.out.println("\tDocumentType: " + documentInfo.getDocumentType());
        System.out.println("\tDocumentTypeFormat: " + documentInfo.getDocumentTypeFormat());
        System.out.println("\tExtension: " + documentInfo.getExtension());
        System.out.println("\tFileType: " + documentInfo.getFileType());
        System.out.println("\tGuid: " + documentInfo.getGuid());
        System.out.println("\tLastModificationDate: " + documentInfo.getLastModificationDate());
        System.out.println("\tName: " + documentInfo.getName());
        System.out.println("\tPageCount: " + documentInfo.getPages().size());
        System.out.println("\tSize: " + documentInfo.getSize());

        assertNotEquals("Document size is incorrect", documentInfo.getSize(), 0);
        assertEquals("Document type is incorrect", documentInfo.getDocumentType(), "Words");
        assertEquals("File type is incorrect", documentInfo.getFileType(), "Doc");
        assertEquals("Page count is incorrect", documentInfo.getPages().size(), 2);
        for (PageData pageData : documentInfo.getPages()) {
            System.out.println("\t\tPage number: " + pageData.getNumber());
            System.out.println("\t\tPage name: " + pageData.getName());
        }
    }

    @Test
    public void testGetDocumentInformationByStreamAndDocumentName() throws Exception {
        Utilities.showTestHeader();
        String documentName = "document.doc";
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        // Get document stream
        InputStream stream = new FileInputStream(STORAGE_PATH + File.separator + documentName);
        // Get document information
        DocumentInfoOptions options = new DocumentInfoOptions();
        DocumentInfoContainer documentInfo = htmlHandler.getDocumentInfo(stream, documentName, options);
        System.out.println("\tDateCreated: " + documentInfo.getDateCreated());
        System.out.println("\tDocumentType: " + documentInfo.getDocumentType());
        System.out.println("\tDocumentTypeFormat: " + documentInfo.getDocumentTypeFormat());
        System.out.println("\tExtension: " + documentInfo.getExtension());
        System.out.println("\tFileType: " + documentInfo.getFileType());
        System.out.println("\tGuid: " + documentInfo.getGuid());
        System.out.println("\tLastModificationDate: " + documentInfo.getLastModificationDate());
        System.out.println("\tName: " + documentInfo.getName());
        System.out.println("\tPageCount: " + documentInfo.getPages().size());
        System.out.println("\tSize: " + documentInfo.getSize());

        assertNotEquals("Document size is incorrect", documentInfo.getSize(), 0);
        assertEquals("Document type is incorrect", documentInfo.getDocumentType(), "Words");
        assertEquals("File type is incorrect", documentInfo.getFileType(), "Doc");
        assertEquals("Page count is incorrect", documentInfo.getPages().size(), 2);
        for (PageData pageData : documentInfo.getPages()) {
            System.out.println("\t\tPage number: " + pageData.getNumber());
            System.out.println("\t\tPage name: " + pageData.getName());
        }
    }

    @Test
    public void testGetDocumentInformationByUrl() throws Exception {
        Utilities.showTestHeader();
        String url = "https://lists.w3.org/Archives/Public/uri/2004Nov/att-0015/App-Note-UseOfTheFileURLInJDF-031111.doc";

        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        URI uri = new URI(url);

        // Get document information
        DocumentInfoOptions options = new DocumentInfoOptions();
        DocumentInfoContainer documentInfo = htmlHandler.getDocumentInfo(uri, options);

        System.out.println("\tDateCreated: " + documentInfo.getDateCreated());
        System.out.println("\tDocumentType: " + documentInfo.getDocumentType());
        System.out.println("\tDocumentTypeFormat: " + documentInfo.getDocumentTypeFormat());
        System.out.println("\tExtension: " + documentInfo.getExtension());
        System.out.println("\tFileType: " + documentInfo.getFileType());
        System.out.println("\tGuid: " + documentInfo.getGuid());
        System.out.println("\tLastModificationDate: " + documentInfo.getLastModificationDate());
        System.out.println("\tName: " + documentInfo.getName());
        System.out.println("\tPageCount: " + documentInfo.getPages().size());
        System.out.println("\tSize: " + documentInfo.getSize());

        assertNotEquals("Document size is incorrect", 0, documentInfo.getSize());
        assertEquals("Document type is incorrect", "Words", documentInfo.getDocumentType());
        assertEquals("File type is incorrect", "Doc", documentInfo.getFileType());
        assertEquals("Page count is incorrect", 11, documentInfo.getPages().size());
        for (PageData pageData : documentInfo.getPages()) {
            System.out.println("\t\tPage number: " + pageData.getNumber());
            System.out.println("\t\tPage name: " + pageData.getName());
        }
    }

    @Test
    public void testGetDocumentInformationByUrlAndWindowsAuthenticationCredential() throws Exception {
        Utilities.showTestHeader();
        String url = "https://lists.w3.org/Archives/Public/uri/2004Nov/att-0015/App-Note-UseOfTheFileURLInJDF-031111.doc";

        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        URI uri = new URI(url);
        WindowsAuthenticationCredential credential = new WindowsAuthenticationCredential("username", "password");
        // Get document information
        DocumentInfoOptions options = new DocumentInfoOptions();
        DocumentInfoContainer documentInfo = htmlHandler.getDocumentInfo(uri, credential, options);

        System.out.println("\tDateCreated: " + documentInfo.getDateCreated());
        System.out.println("\tDocumentType: " + documentInfo.getDocumentType());
        System.out.println("\tDocumentTypeFormat: " + documentInfo.getDocumentTypeFormat());
        System.out.println("\tExtension: " + documentInfo.getExtension());
        System.out.println("\tFileType: " + documentInfo.getFileType());
        System.out.println("\tGuid: " + documentInfo.getGuid());
        System.out.println("\tLastModificationDate: " + documentInfo.getLastModificationDate());
        System.out.println("\tName: " + documentInfo.getName());
        System.out.println("\tPageCount: " + documentInfo.getPages().size());
        System.out.println("\tSize: " + documentInfo.getSize());

        assertNotEquals("Document size is incorrect", documentInfo.getSize(), 0);
        assertEquals("Document type is incorrect", documentInfo.getDocumentType(), "Words");
        assertEquals("File type is incorrect", documentInfo.getFileType(), "Doc");
        assertEquals("Page count is incorrect", documentInfo.getPages().size(), 11);
        for (PageData pageData : documentInfo.getPages()) {
            System.out.println("\t\tPage number: " + pageData.getNumber());
            System.out.println("\t\tPage name: " + pageData.getName());
        }
    }

    @Test
    public void testSetCustomFontsDirectoryPath() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Add custom fonts directories to FontDirectories list
        config.getFontDirectories().add("/usr/admin/Fonts");
        config.getFontDirectories().add("/home/admin/Fonts");

        // Init viewer handler with config
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        assertNotEquals("Font directories are not added", config.getFontDirectories().size(), 0);
    }
}

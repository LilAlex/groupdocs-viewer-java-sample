package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.domain.containers.*;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.domain.options.ReorderPageOptions;
import com.groupdocs.viewer.domain.options.RotatePageOptions;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.licensing.License;
import com.groupdocs.viewer.metered.Metered;
import com.groupdocs.viewer.sample.Utilities;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static com.groupdocs.viewer.sample.TestRunner.STORAGE_PATH;
import static com.groupdocs.viewer.sample.Utilities.applyLicense;
import static com.groupdocs.viewer.sample.Utilities.unsetLicense;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Extra tests to check different problems which were found by somebody
 * @author liosha (13.11.2017)
 */
public class ExtraOperationsTests {

    @Before
    public void before() {
        applyLicense();
    }

    @Test
    public void testMeteredIncreaseCountWhenLoadingDocument() throws Exception {
        Utilities.showTestHeader();
        unsetLicense();

        Metered metered = new Metered();
        metered.setMeteredKey("bb37c02641da4113a08a6aa70f3a8758", "jjZTTufdkO31IWB0Ng-RH5AV4p7QmmQGzYIZnVuFdHVQU0JV8l-WEM97J7Vi6RCLOglArFIGWdzCeGflU8LiGxPtAe9lLf7Ro7KpIdKYxR1fnCKs22V6nhB8wkglc75pggi0tdWCyGyY2VuErNS54HeJTTMDJt3jMQgjedtH6U4_");

        double amountBefore = Metered.getConsumptionQuantity();
        System.out.println("Amount before: " + amountBefore);

        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler();
        htmlHandler.getPages(STORAGE_PATH + File.separator + "candy.pdf");

        // Get metered value after usage of the comparison
        double amountAfter = Metered.getConsumptionQuantity();
        System.out.print("Amount of MB consumed after: " + amountAfter);

        if (amountAfter > amountBefore) {
            System.out.print("It works :)");
        } else {
            fail("Not works :(");
        }
    }

    @Test
    public void testMemoryLeaks() throws Exception {
        unsetLicense();
        Utilities.showTestHeader();
        File folder = new File(STORAGE_PATH);
        File[] listOfFiles = folder.listFiles();
        assertFalse(listOfFiles == null);

        for (File listOfFile : listOfFiles) {
//            if (!listOfFile.getName().endsWith(".one")) {
//                continue;
//            } else {
//                System.out.println("IT IS ONE FILE");
//            }
            if (listOfFile.isFile()) {

                String fileName = listOfFile.getName();

                System.out.println("Testing: " + fileName);

                for (int j = 0; j < 3; j++) {
                    try {
                        htmlNoCache(fileName);
                        htmlWithCache(fileName);
                        imageNoCache(fileName);
                        imageWithCache(fileName);
                        imageWithCacheAndPdf(fileName);
                    } catch (Exception e) {
                        fail(ExceptionUtils.getStackTrace(e));
                    }
                }

                System.out.println("\r\n");
            }
        }

        System.out.println("It works :)");
    }


    @Test
    public void testPrivatesNotVisible() throws Exception {
        Utilities.showTestHeader();

        License license = new License();
        try {
            assertTrue(license.getClass().getDeclaredField("isValidLicense") == null);
        } catch (NoSuchFieldException e) {
            return;
        }
        fail();
        //license.isValidLicense() //OK
    }

    private void htmlNoCache(String guid) throws Exception {

        System.out.println("htmlNoCache: " + guid);

        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        ViewerHtmlHandler handler = new ViewerHtmlHandler(config);

        // clear cache
        handler.clearCache();

        // get pages html
        java.util.List<PageHtml> pagesHtml = handler.getPages(guid);
        if (pagesHtml.isEmpty()) {
            throw new Exception();
        }

        // get pdf
        FileContainer pdfFileHtml = handler.getPdfFile(guid);
        if (pdfFileHtml.getStream().read() == -1) {
            throw new Exception();
        }
        pdfFileHtml.getStream().close();

        // get printable html
        PrintableHtmlContainer getPrintableHtml = handler.getPrintableHtml(guid);
        if (getPrintableHtml.getHtmlContent().isEmpty()) {
            throw new Exception();
        }

        // get document info
        DocumentInfoContainer documentInfo = handler.getDocumentInfo(guid);
        if (documentInfo.getPages().isEmpty()) {
            throw new Exception();
        }

        // get file
        FileContainer file = handler.getFile(guid);
        if (file.getStream().read() == -1) {
            throw new Exception();
        }file.getStream().close();

        // get supported document formats
        DocumentFormatsContainer formats = handler.getSupportedDocumentFormats();
        if (formats.getSupportedDocumentFormats().isEmpty()) {
            throw new Exception();
        }

        // get file list
        FileListContainer fileTree = handler.getFileList();
        if (fileTree.getFiles().size() == 0) {
            throw new Exception();
        }

        // manipulations
        handler.reorderPage(guid, new ReorderPageOptions(1, 2));
        handler.rotatePage(guid, new RotatePageOptions(1, 90));

        // clear cache
        //handler.clearCache();
    }

    private void htmlWithCache(String guid) throws Exception {

        System.out.println("htmlWithCache: " + guid);

        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        config.setUseCache(true);

        ViewerHtmlHandler handler = new ViewerHtmlHandler(config);

        // clear cache
        handler.clearCache();

        HtmlOptions options = new HtmlOptions();
        options.setResourcesEmbedded(false);

        // get pages html
        java.util.List<PageHtml> pagesHtml = handler.getPages(guid, options);
        if (pagesHtml.isEmpty()) {
            throw new Exception();
        }

        // get pdf
        FileContainer pdfFileHtml = handler.getPdfFile(guid);
        if (pdfFileHtml.getStream().read() == -1) {
            throw new Exception();
        }
        pdfFileHtml.getStream().close();

        // get printable html
        PrintableHtmlContainer getPrintableHtml = handler.getPrintableHtml(guid);
        if (getPrintableHtml.getHtmlContent().isEmpty()) {
            throw new Exception();
        }

        // get document info
        DocumentInfoContainer documentInfo = handler.getDocumentInfo(guid);
        if (documentInfo.getPages().isEmpty()) {
            throw new Exception();
        }

        // get file
        FileContainer file = handler.getFile(guid);
        if (file.getStream().read() == -1) {
            throw new Exception();
        }
        file.getStream().close();

        // get supported document formats
        DocumentFormatsContainer formats = handler.getSupportedDocumentFormats();
        if (formats.getSupportedDocumentFormats().isEmpty()) {
            throw new Exception();
        }

        // get file list
        FileListContainer fileTree = handler.getFileList();
        if (fileTree.getFiles().size() == 0) {
            throw new Exception();
        }

        // manipulations
        handler.reorderPage(guid, new ReorderPageOptions(1, 2));
        handler.rotatePage(guid, new RotatePageOptions(1, 90));
    }

    private void imageNoCache(String guid) throws Exception {

        System.out.println("imageNoCache: " + guid);

        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        ViewerImageHandler handler = new ViewerImageHandler(config);

        // clear cache
        handler.clearCache();

        // get pages html
        java.util.List<PageImage> pagesHtml = handler.getPages(guid);
        if (pagesHtml.isEmpty()) {
            throw new Exception();
        }

        // get pdf
        FileContainer pdfFileHtml = handler.getPdfFile(guid);
        if (pdfFileHtml.getStream().read() == -1) {
            throw new Exception();
        }
        pdfFileHtml.getStream().close();

        // get printable html
        PrintableHtmlContainer getPrintableHtml = handler.getPrintableHtml(guid);
        if (getPrintableHtml.getHtmlContent().isEmpty()) {
            throw new Exception();
        }

        // get document info
        DocumentInfoContainer documentInfo = handler.getDocumentInfo(guid);
        if (documentInfo.getPages().isEmpty()) {
            throw new Exception();
        }

        // get file
        FileContainer file = handler.getFile(guid);
        if (file.getStream().read() == -1) {
            throw new Exception();
        }
        file.getStream().close();

        // get supported document formats
        DocumentFormatsContainer formats = handler.getSupportedDocumentFormats();
        if (formats.getSupportedDocumentFormats().isEmpty()) {
            throw new Exception();
        }

        // get file tree
        FileListContainer fileTree = handler.getFileList();
        if (fileTree.getFiles().size() == 0) {
            throw new Exception();
        }

        // manipulations
        handler.reorderPage(guid, new ReorderPageOptions(1, 2));
        handler.rotatePage(guid, new RotatePageOptions(1, 90));

    }

    private void imageWithCache(String guid) throws Exception {

        System.out.println("imageWithCache: " + guid);

        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        config.setUseCache(true);

        ViewerImageHandler handler = new ViewerImageHandler(config);

        // clear cache
        handler.clearCache();

        // get pages html
        java.util.List<PageImage> pagesHtml = handler.getPages(guid);
        if (pagesHtml.isEmpty()) {
            throw new Exception();
        }

        // get pdf
        FileContainer pdfFileHtml = handler.getPdfFile(guid);
        if (pdfFileHtml.getStream().read() == -1) {
            throw new Exception();
        }
        pdfFileHtml.getStream().close();

        // get printable html
        PrintableHtmlContainer getPrintableHtml = handler.getPrintableHtml(guid);
        if (getPrintableHtml.getHtmlContent().isEmpty()) {
            throw new Exception();
        }

        // get document info
        DocumentInfoContainer documentInfo = handler.getDocumentInfo(guid);
        if (documentInfo.getPages().isEmpty()) {
            throw new Exception();
        }

        // get file
        FileContainer file = handler.getFile(guid);
        if (file.getStream().read() == -1) {
            throw new Exception();
        }
        file.getStream().close();

        // get supported document formats
        DocumentFormatsContainer formats = handler.getSupportedDocumentFormats();
        if (formats.getSupportedDocumentFormats().isEmpty()) {
            throw new Exception();
        }

        // get file tree
        FileListContainer fileTree = handler.getFileList();
        if (fileTree.getFiles().size() == 0) {
            throw new Exception();
        }

        // manipulations
        handler.reorderPage(guid, new ReorderPageOptions(1, 2));
        handler.rotatePage(guid, new RotatePageOptions(1, 90));

    }

    private void imageWithCacheAndPdf(String guid) throws Exception {

        System.out.println("imageWithCacheAndPdf: " + guid);

        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        config.setUseCache(true);
        config.setUsePdf(true);

        ViewerImageHandler handler = new ViewerImageHandler(config);

        // clear cache
        handler.clearCache();

        // get pages html
        java.util.List<PageImage> pagesHtml = handler.getPages(guid);
        if (pagesHtml.isEmpty()) {
            throw new Exception();
        }
        for (PageImage pageImage : pagesHtml) {
            pageImage.getStream().close();
        }

        // get pdf
        FileContainer pdfFileHtml = handler.getPdfFile(guid);
        if (pdfFileHtml.getStream().read() == -1) {
            throw new Exception();
        }
        pdfFileHtml.getStream().close();

        // get printable html
        PrintableHtmlContainer getPrintableHtml = handler.getPrintableHtml(guid);
        if (getPrintableHtml.getHtmlContent().isEmpty()) {
            throw new Exception();
        }

        // get document info
        DocumentInfoContainer documentInfo = handler.getDocumentInfo(guid);
        if (documentInfo.getPages().isEmpty()) {
            throw new Exception();
        }

        // get file
        FileContainer file = handler.getFile(guid);
        if (file.getStream().read() == -1) {
            throw new Exception();
        }
        file.getStream().close();

        // get supported document formats
        DocumentFormatsContainer formats = handler.getSupportedDocumentFormats();
        if (formats.getSupportedDocumentFormats().isEmpty()) {
            throw new Exception();
        }

        // get file tree
        FileListContainer fileTree = handler.getFileList();
        if (fileTree.getFiles().size() == 0) {
            throw new Exception();
        }

        // manipulations
        handler.reorderPage(guid, new ReorderPageOptions(1, 2));
        handler.rotatePage(guid, new RotatePageOptions(1, 90));
    }
}

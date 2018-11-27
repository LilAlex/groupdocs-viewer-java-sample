package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.html.CadToHtmlConverter;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.converter.options.TextOverflowMode;
import com.groupdocs.viewer.domain.FileDescription;
import com.groupdocs.viewer.domain.containers.DocumentInfoContainer;
import com.groupdocs.viewer.domain.containers.FileContainer;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.domain.options.ConvertOptions;
import com.groupdocs.viewer.domain.options.DocumentInfoOptions;
import com.groupdocs.viewer.domain.options.PdfFileOptions;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.sample.TestRunner;
import com.groupdocs.viewer.sample.Utilities;
import com.groupdocs.viewer.sample.handler.LocalCacheDataHandler;
import com.groupdocs.viewer.sample.handler.LocalInputDataHandler;
import com.groupdocs.viewer.service.DocumentService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

import static com.groupdocs.viewer.sample.TestRunner.STORAGE_PATH;
import static com.groupdocs.viewer.sample.Utilities.applyLicense;
import static com.groupdocs.viewer.sample.Utilities.initOutput;
import static org.junit.Assert.*;

/**
 * @author liosha (15.03.2017)
 */
public class AdvancedOperationsTests {

    @Before
    public void before() {
        applyLicense();
        initOutput();
    }

    @After
    public void after() throws IOException {
        Utilities.cleanOutput();
    }

    @Test
    public void testHowToRenderContentWithEnablePreciseRenderingEnabled() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.pdf";

        // Set pdf options to render content without glyphs grouping
        HtmlOptions options = new HtmlOptions();
        options.getPdfOptions().setEnablePreciseRendering(true); // Default value is false

        // Get pages
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            System.out.println("Html content: " + page.getHtmlContent());
        }
    }

    @Test
    public void testHowToRenderPdfDocumentInAPreciseMode() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.pdf";

        // Set pdf options to render content in a precise mode
        HtmlOptions options = new HtmlOptions();
        options.getPdfOptions().setEnablePreciseRendering(true); // Default value is false

        // Get pages
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            System.out.println("Html content: " + page.getHtmlContent());
        }
    }

    @Test
    public void testHowToRenderContentAccordingToZOrderInOriginalDocument() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.pdf";

        // Set pdf options to render content according to z-order in original document
        HtmlOptions options = new HtmlOptions();
        options.getPdfOptions().setUseOriginalContentOrdering(true); // Default value is false

        // Get pages
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            System.out.println("Html content: " + page.getHtmlContent());
        }
    }

    @Test
    public void testHowToRenderContentWithRenderLayersSeparatelyEnabled() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "layered_document.pdf";

        // Set pdf options to render pdf layers into separate html elements
        HtmlOptions options = new HtmlOptions();
        options.getPdfOptions().setRenderLayersSeparately(true); // Default value is false

        // Get pages
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            System.out.println("Html content: " + page.getHtmlContent());
        }
    }

    @Test
    public void testHowToRenderPdfDocumentsAndExcludeAnnotationsFromRenderingResult() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "with-annotations.pdf";

        // Set pdf options to render content without annotations
        HtmlOptions options = new HtmlOptions();
        options.setRenderComments(false); // Default value is false

        // Get pages
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            System.out.println("Html content: " + page.getHtmlContent());
        }
    }

    @Test
    public void testHowToGetOriginalPdfDocumentWithoutAnnotations() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "with-annotations.pdf";

        // Set pdf options to get original file without annotations
        PdfFileOptions pdfFileOptions = new PdfFileOptions();
        pdfFileOptions.setRenderComments(false); // Default value is false

        // Get original pdf document without annotations
        FileContainer fileContainer = imageHandler.getPdfFile(guid, pdfFileOptions);
        // Access result pdf document using fileContainer.Stream property
    }

    @Test
    public void testRenderingWordsDocumentsWithTrackedChanges() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document-with-comment.docx";

        // Set pdf options to render content without annotations
        HtmlOptions options = new HtmlOptions();
        options.getWordsOptions().setShowTrackedChanges(true); // Default value is false

        // Get pages
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            System.out.println("Html content: " + page.getHtmlContent());
        }
    }

    @Test
    public void testGetPDFRepresentationOfWordsDocumentWithTrackedChanges() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "with-annotations.pdf";

        // Set pdf options to get pdf file with tracked changes
        PdfFileOptions pdfFileOptions = new PdfFileOptions();
        pdfFileOptions.getWordsOptions().setShowTrackedChanges(true); // Default value is false

        // Get pdf document without tracked changes
        FileContainer fileContainer = imageHandler.getPdfFile(guid, pdfFileOptions);
        // Access result pdf document using fileContainer.Stream property
    }

    @Test
    public void testSettingResultImageSizeWhenRenderingCadDocuments() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.dwg";

        // Set Cad options to render content with a specified size
        ImageOptions options = new ImageOptions();
        options.getCadOptions().setHeight(750);
        options.getCadOptions().setWidth(450);

        // Get pages
        List<PageImage> pages = imageHandler.getPages(guid, options);

        for (PageImage page : pages)
        {
            System.out.println("Page number: " + page.getPageNumber());
            InputStream imageContent = page.getStream();
        }
    }

    @Test
    public void testRenderingModelAndAllNonEmptyLayoutsFromCADDocument() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.dwg";

        // Set CAD options to render Model and all non empty Layouts
        ImageOptions options = new ImageOptions();
        options.getCadOptions().setRenderLayouts(true);

        // Get pages
        List<PageImage> pages = imageHandler.getPages(guid, options);

        for (PageImage page : pages)
        {
            System.out.println("Page number: " + page.getPageNumber());
            InputStream imageContent = page.getStream();
        }
    }

    @Test
    public void testHowToRenderSpecificLayoutFromCADDocuments() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        final String fileName = "three-layouts.dwg";


        HtmlOptions options = new HtmlOptions();
        options.getCadOptions().setLayoutName("Layout2");
        options.getCadOptions().setRenderLayouts(true);
//        List<PageHtml> pages = runTest(documentName, options, false);
        ConvertOptions convertOptions = new ConvertOptions(config, new DocumentService(new LocalInputDataHandler(config){
            @Override
            public InputStream getFile(String guid) {
                try {
                    return new FileInputStream(STORAGE_PATH + File.separator + fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }), new LocalCacheDataHandler(config), new FileDescription(fileName), options);

        CadToHtmlConverter converter = new CadToHtmlConverter(convertOptions);
        final List<PageHtml> pages = converter.convert();
        assertEquals(1, pages.size());
    }

    @Test
    public void testHowToObtainTheListOfLayoutsContainedInCADDocument() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.dwg";

        // Set CAD options to get the full list of Layouts
        DocumentInfoOptions documentInfoOptions = new DocumentInfoOptions();
        documentInfoOptions.getCadOptions().setRenderLayouts(true);

        // Get DocumentInfoContainer and iterate through pages
        DocumentInfoContainer documentInfoContainer = imageHandler.getDocumentInfo(guid, documentInfoOptions);

        System.out.println("Name: " + documentInfoContainer.getName());
        System.out.println("Pages count: " + documentInfoContainer.getPages());
        assertNotNull(documentInfoContainer.getName());
        assertTrue(documentInfoContainer.getPages().size() > 0);
    }

    @Test
    public void testSettingOverflowedTextToBeHiddenWhenRenderingCellsDocuments() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.xlsx";

        // Set Cells options to hide overflowing text
        HtmlOptions options = new HtmlOptions();
        options.getCellsOptions().setTextOverflowMode(TextOverflowMode.HideText);

        // Get pages
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            final String htmlContent = page.getHtmlContent();
            System.out.println("Html content: " + htmlContent);
        }
    }

    @Test
    public void testHowToSetEncoding() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.eml";

        // Set encoding
        HtmlOptions options = new HtmlOptions();
        options.getEmailOptions().setEncoding(Charset.forName("shift-jis"));

        // Get pages
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            final String htmlContent = page.getHtmlContent();
            System.out.println("Html content: " + htmlContent);
        }
    }

    @Test
    public void testHowToAdjustTheQualityAndSizeWhenRenderingDjVuIntoPdf() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.djvu";

        // Set pdf options JpegQuality in a range between 1 and 100
        PdfFileOptions pdfFileOptions = new PdfFileOptions();
        pdfFileOptions.setJpegQuality(5);

        // Get file as pdf
        FileContainer container = imageHandler.getPdfFile(guid, pdfFileOptions);

        System.out.println("Name: " + container.getStream().available());
        assertTrue(container.getStream().available() > 0);
    }

    @Test
    public void testRenderingMicrosoftWordDocumentWithComments() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document-with-comment.docx";

        // Set words options to render content with comments
        HtmlOptions options = new HtmlOptions();
        options.setRenderComments(true); // Default value is false


        // Get pages
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            final String htmlContent = page.getHtmlContent();
            System.out.println("Html content: " + htmlContent);
        }
    }

    @Test
    public void testGetPDFRepresentationOfMicrosoftWordDocumentWithComments() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document-with-comment.docx";

        // Set pdf options to get pdf file with comments
        PdfFileOptions pdfFileOptions = new PdfFileOptions();
        pdfFileOptions.setRenderComments(true); // Default value is false

        // Get pdf document with comments
        FileContainer container = imageHandler.getPdfFile(guid, pdfFileOptions);

        System.out.println("Name: " + container.getStream().available());
        assertTrue(container.getStream().available() > 0);
    }
}

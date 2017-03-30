package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.*;
import com.groupdocs.viewer.domain.containers.*;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.domain.options.*;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.sample.Utilities;
import com.groupdocs.viewer.utils.CultureInfo;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static com.groupdocs.viewer.sample.TestRunner.STORAGE_PATH;
import static com.groupdocs.viewer.sample.TestRunner.applyLicense;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Aleksey Permyakov (14.03.2017).
 */
public class OtherOperationsTests {

    @Before
    public void before() {
        applyLicense();
    }


    @Test
    public void testRotate1stPageOfTheDocumentBy90Deg() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        String guid = "document.doc";

        // Get original file
        FileContainer container = imageHandler.getFile(guid);
        final InputStream stream = container.getStream();
        System.out.println("Stream lenght: " + stream.available());
        assertTrue("Stream content is empty!", stream.available() > 0);
    }

    @Test
    public void testGetOriginalFileInPdfFormat() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        String guid = "document.doc";

        // Get file as pdf
        FileContainer container = imageHandler.getPdfFile(guid);
        final InputStream stream = container.getStream();
        System.out.println("Stream lenght: " + stream.available());
        assertTrue("Stream content is empty!", stream.available() > 0);
    }

    @Test
    public void testGetOriginalFileInPdfFormatWithWatermark() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        String guid = "document.doc";

        // Set watermark properties
        Watermark watermark = new Watermark("This is watermark text");
        watermark.setColor(Color.BLUE);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);

        PdfFileOptions options = new PdfFileOptions();
        options.setWatermark(watermark);

        // Get file as pdf with watermaks
        FileContainer container = imageHandler.getPdfFile(guid, options);
        final InputStream stream = container.getStream();
        System.out.println("Stream lenght: " + stream.available());
        assertTrue("Stream content is empty!", stream.available() > 0);
    }

    @Test
    public void testGetOriginalFileInPdfFormatWithWatermarkAndFontNameSpecified() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        String guid = "document.doc";

        // Set watermark properties
        Watermark watermark = new Watermark("透かし文字、");
        watermark.setColor(Color.BLUE);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);
        // Set watermark font name which contains Japanese characters
        watermark.setFontName("MS Gothic");

        PdfFileOptions options = new PdfFileOptions();
        options.setWatermark(watermark);

        // Get file as pdf with watermaks
        FileContainer container = imageHandler.getPdfFile(guid, options);
        final InputStream stream = container.getStream();
        System.out.println("Stream lenght: " + stream.available());
        assertTrue("Stream content is empty!", container.getStream().available() > 0);
    }

    @Test
    public void testGetOriginalFileInPdfFormatWithPrintAction() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        String guid = "document.doc";

        // Set add print action property
        PdfFileOptions options = new PdfFileOptions();
        options.setTransformations(Transformation.AddPrintAction);

        // Get file as pdf with print action
        FileContainer container = imageHandler.getPdfFile(guid, options);
        final InputStream stream = container.getStream();
        System.out.println("Stream lenght: " + stream.available());
        assertTrue("Stream content is empty!", container.getStream().available() > 0);
    }

    @Test
    public void testGetOriginalFileInPdfFormatWithTransformations() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        String guid = "document.doc";

        // Perform page rotation
        RotatePageOptions rotatePageOptions = new RotatePageOptions(1, 90);
        imageHandler.rotatePage(guid, rotatePageOptions);

        // Reorder pages, move 1 page to the 2 position, it is assumed that "document.doc" document has at least two pages
        ReorderPageOptions reorderPageOptions = new ReorderPageOptions(1, 2);
        imageHandler.reorderPage(guid, reorderPageOptions);

        // Set apply rotate and reorder transformations
        PdfFileOptions options = new PdfFileOptions();
        options.setTransformations(Transformation.from(Transformation.Rotate, Transformation.Reorder, Transformation.AddPrintAction));


        // Get file as pdf with transformations
        FileContainer container = imageHandler.getPdfFile(guid, options);
        final InputStream stream = container.getStream();
        System.out.println("Stream lenght: " + stream.available());
        assertTrue("Stream content is empty!", container.getStream().available() > 0);
    }

    @Test
    public void testGetDocumentRepresentationFromAbsolutePath() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        // Set absolute path to file
        String guid = STORAGE_PATH + "\\document.doc";

        // Get pages by absolute path
        List<PageImage> pages = imageHandler.getPages(guid);
        System.out.println("Page count: " + pages.size());
        assertTrue("Pages count is incorrect!", pages.size() > 0);
    }

    @Test
    public void testGetDocumentRepresentationFromRelativePath() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        // Set relative path. So that full path will be C:\storage\document.doc
        String guid = "document.doc";

        // Get pages by absolute path
        List<PageImage> pages = imageHandler.getPages(guid);
        System.out.println("Page count: " + pages.size());
        assertTrue("Pages count is incorrect!", pages.size() > 0);
    }

    @Test
    public void testGetDocumentRepresentationFromUri() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        URI uri = new URI("http://groupdocs.com/images/banner/carousel2/signature.png");

        // Get pages by absolute path
        List<PageImage> pages = imageHandler.getPages(uri);
        System.out.println("Page count: " + pages.size());
        assertTrue("Pages count is incorrect!", pages.size() > 0);
    }

    @Test
    public void testGetDocumentRepresentationFromInputStream() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        FileInputStream fileStream = new FileInputStream(STORAGE_PATH + "\\document.doc");

        // Get pages by absolute path
        List<PageImage> pages = imageHandler.getPages(fileStream, "document.doc");
        System.out.println("Page count: " + pages.size());
        assertTrue("Pages count is incorrect!", pages.size() > 0);
    }

    @Test
    public void testHowToUseCustomInputDataHandler() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // File guid
        String guid = "document.doc";

//        // Use custom IInputDataHandler implementation
//        IInputDataHandler inputDataHandler = new CustomInputDataHandler();
//
//        // Get file HTML representation
//        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config, inputDataHandler);
//
//        List<PageHtml> pages = htmlHandler.getPages(guid);
//        System.out.println("Page count: " + pages.size());
//        assertTrue("Pages count is incorrect!", pages.size() > 0);
    }

    @Test
    public void testGetDocumentHtmlForPrint() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";

        // Get document html for print
        PrintableHtmlOptions options = new PrintableHtmlOptions(guid);
        PrintableHtmlContainer container = imageHandler.getPrintableHtml(options);

        System.out.println("Html content: " + container.getHtmlContent());
        assertTrue("Html content is empty!", container.getHtmlContent().length() > 0);
    }

    @Test
    public void testGetDocumentHtmlForPrintWithWatermark() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";

        // Get document html for print with watermark
        PrintableHtmlOptions options = new PrintableHtmlOptions(guid, new Watermark("Watermark text"));
        PrintableHtmlContainer container = imageHandler.getPrintableHtml(options);
        assertTrue("Html content is empty!", container.getHtmlContent().length() > 0);
    }

    @Test
    public void testGetDocumentHtmlForPrintWithCustomCss() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";

        // Get document html for print with custom css
        String css = "a { color: hotpink; }"; // Some style
        PrintableHtmlOptions options = new PrintableHtmlOptions(guid, css);
        PrintableHtmlContainer container = imageHandler.getPrintableHtml(options);
        assertTrue("Html content is empty!", container.getHtmlContent().length() > 0);
    }

    @Test
    public void testShowGridLinesForExcelFilesInImageRepresentation() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.xlsx";

        // Set image options to show grid lines
        ImageOptions options = new ImageOptions();
        options.getCellsOptions().setShowGridLines(true);

        List<PageImage> pages = imageHandler.getPages(guid, options);
        assertTrue("Pages count is incorrect!", pages.size() > 0);

        for (PageImage page : pages) {
            System.out.println("Page number: " + page.getPageNumber());

            // Page image stream
            InputStream imageContent = page.getStream();
            System.out.println("Stream length: " + imageContent.available());
            assertTrue("Page content is empty!", imageContent.available() > 0);
        }
    }

    @Test
    public void testShowGridLinesForExcelFilesInHtmlRepresentation() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.xlsx";

        // Set html options to show grid lines
        HtmlOptions options = new HtmlOptions();
        options.getCellsOptions().setShowGridLines(true);
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        assertTrue("Pages count is incorrect!", pages.size() > 0);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            System.out.println("Html content: " + page.getHtmlContent());
        }
    }

    @Test
    public void testMultiplePagesPerSheetForExcelFilesWhenRenderingToPdf() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.xlsx";

        // Set OnePagePerSheet = false to render multiple pages per sheet. By default OnePagePerSheet = true.
        PdfFileOptions pdfFileOptions = new PdfFileOptions();
        pdfFileOptions.getCellsOptions().setOnePagePerSheet(false);

        //Get pdf file
        FileContainer fileContainer = imageHandler.getPdfFile(guid, pdfFileOptions);
        System.out.println("Stream length: " + fileContainer.getStream().available());
    }

    @Test
    public void testMultiplePagesPerSheetForExcelFilesInImageMode() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.xlsx";

        // Set OnePagePerSheet = false to render multiple pages per sheet. By default OnePagePerSheet = true.
        ImageOptions imageOptions = new ImageOptions();
        imageOptions.getCellsOptions().setOnePagePerSheet(false);

        //Get pages
        List<PageImage> pages = imageHandler.getPages(guid, imageOptions);
        System.out.println("Pages count: " + pages.size());
        assertTrue("Pages count is incorrect!", pages.size() > 0);
    }

    @Test
    public void testMultiplePagesPerSheetForExcelFilesInHtmlMode() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.xlsx";

        // Set OnePagePerSheet = false to render multiple pages per sheet
        HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.getCellsOptions().setOnePagePerSheet(false);
        // Set count rows to render into one page. Default value is 50.
        htmlOptions.getCellsOptions().setCountRowsPerPage(50);

        // Get pages
        List<PageHtml> pages = htmlHandler.getPages(guid, htmlOptions);
        System.out.println("Pages count: " + pages.size());
        assertTrue("Pages count is incorrect!", pages.size() > 0);
    }

    @Test
    public void testGetAllSupportedDocumentFormats() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image or html handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        // Get supported document formats
        DocumentFormatsContainer documentFormatsContainer = imageHandler.getSupportedDocumentFormats();
        Map<String, String> supportedDocumentFormats = documentFormatsContainer.getSupportedDocumentFormats();
        assertTrue("Document formats count is incorrect!", supportedDocumentFormats.size() > 0);

        for (Map.Entry<String, String> supportedDocumentFormat : supportedDocumentFormats.entrySet()) {
            System.out.println("Extension: '" + supportedDocumentFormat.getKey() + "'; Document format: '" + supportedDocumentFormat.getValue() + "'");
        }
    }

    @Test
    public void testShowHiddenSheetsForExcelFilesInImageRepresentation() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.xlsx";

        // Set image options to show grid lines
        ImageOptions options = new ImageOptions();
        options.getCellsOptions().setShowHiddenSheets(true);

        DocumentInfoContainer container = imageHandler.getDocumentInfo(guid);

        for (PageData page : container.getPages())
            System.out.println("Page number: " + page.getNumber() + ", Page Name: " + page.getName() + ", IsVisible: " + page.isVisible());

        List<PageImage> pages = imageHandler.getPages(guid, options);
        assertTrue("Pages count is incorrect!", pages.size() > 0);

        for (PageImage page : pages) {
            System.out.println("Page number: " + page.getPageNumber());

            // Page image stream
            InputStream imageContent = page.getStream();
            System.out.println("Stream length: " + imageContent.available());
        }
    }

    @Test
    public void testShowHiddenSheetsForExcelFilesInHtmlRepresentation() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.xlsx";

        // Set html options to show grid lines
        HtmlOptions options = new HtmlOptions();
        options.getCellsOptions().setShowHiddenSheets(true);

        DocumentInfoContainer container = htmlHandler.getDocumentInfo(new DocumentInfoOptions(guid));

        for (PageData page : container.getPages()) {
            System.out.println("Page number: " + page.getNumber() + ", Page Name: " + page.getName() + ", IsVisible: " + page.isVisible());
        }
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        assertTrue("Pages count is incorrect!", pages.size() > 0);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
        }
    }

    @Test
    public void testSetupViewerConfigToUseFileWithLocalizedStringsCreatedAbove() throws Exception {
        Utilities.showTestHeader();
        // Setup viewer config
        ViewerConfig viewerConfig = new ViewerConfig();
        viewerConfig.setStoragePath(STORAGE_PATH);
        viewerConfig.setLocalesPath(STORAGE_PATH + "\\locales");

        // Create html handler
        CultureInfo cultureInfo = new CultureInfo("fr-FR");
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(viewerConfig, cultureInfo);
    }

    @Test
    public void testHowToGetDocumentPagesWithWordsCellsAndEmailDocumentEncodingSetting() throws Exception {
        Utilities.showTestHeader();
        // Setup viewer config
        ViewerConfig viewerConfig = new ViewerConfig();
        viewerConfig.setStoragePath(STORAGE_PATH);
        //Initialize viewer handler
        ViewerImageHandler viewerImageHandler = new ViewerImageHandler(viewerConfig);

        //Set encoding
        Charset encoding = Charset.forName("shift-jis");

        //Set image options
        ImageOptions imageOptions = new ImageOptions();
        imageOptions.getWordsOptions().setEncoding(encoding);
        imageOptions.getCellsOptions().setEncoding(encoding);
        imageOptions.getEmailOptions().setEncoding(encoding);

        //Get words document pages with encoding
        String wordsDocumentGuid = "document.txt";
        List<PageImage> wordsDocumentPages = viewerImageHandler.getPages(wordsDocumentGuid, imageOptions);
        System.out.println("Page number: " + wordsDocumentPages.size());
        assertTrue("Pages count is incorrect!", wordsDocumentPages.size() > 0);

        //Get cells document pages with encoding
        String cellsDocumentGuid = "document.csv";
        List<PageImage> cellsDocumentPages = viewerImageHandler.getPages(cellsDocumentGuid, imageOptions);
        System.out.println("Page number: " + cellsDocumentPages.size());
        assertTrue("Pages count is incorrect!", cellsDocumentPages.size() > 0);

        //Get email document pages with encoding
        String emailDocumentGuid = "document.msg";
        List<PageImage> emailDocumentPages = viewerImageHandler.getPages(emailDocumentGuid, imageOptions);
        System.out.println("Page number: " + emailDocumentPages.size());
        assertTrue("Pages count is incorrect!", emailDocumentPages.size() > 0);

        //Get words document info with encoding
        DocumentInfoOptions wordsDocumentInfoOptions = new DocumentInfoOptions();
        wordsDocumentInfoOptions.getWordsOptions().setEncoding(encoding);
        DocumentInfoContainer wordsDocumentInfoContainer = viewerImageHandler.getDocumentInfo(wordsDocumentGuid, wordsDocumentInfoOptions);

        //Get cells document info with encoding
        DocumentInfoOptions cellsDocumentInfoOptions = new DocumentInfoOptions();
        cellsDocumentInfoOptions.getCellsOptions().setEncoding(encoding);
        DocumentInfoContainer cellsDocumentInfoContainer = viewerImageHandler.getDocumentInfo(cellsDocumentGuid, cellsDocumentInfoOptions);

        //Get email document info with encoding
        DocumentInfoOptions emailDocumentInfoOptions = new DocumentInfoOptions();
        emailDocumentInfoOptions.getEmailOptions().setEncoding(encoding);
        DocumentInfoContainer emailDocumentInfoContainer = viewerImageHandler.getDocumentInfo(emailDocumentGuid, emailDocumentInfoOptions);
    }

    @Test
    public void testHowToGetTextCoordinatesInImageMode() throws Exception {
        Utilities.showTestHeader();
        // Setup viewer config
        ViewerConfig viewerConfig = new ViewerConfig();
        viewerConfig.setStoragePath(STORAGE_PATH);
        viewerConfig.setUsePdf(true);

        // Set document guid
        String guid = "document.doc";

        // Init viewer image handler
        ViewerImageHandler viewerImageHandler = new ViewerImageHandler(viewerConfig);

        // Init document info options
        DocumentInfoOptions documentInfoOptions = new DocumentInfoOptions();
        //Get document info
        DocumentInfoContainer documentInfoContainer = viewerImageHandler.getDocumentInfo(guid, documentInfoOptions);

        final List<PageData> pages = documentInfoContainer.getPages();
        assertTrue("Pages count is incorrect!", pages.size() > 0);
        // Go through all pages
        for (PageData pageData : documentInfoContainer.getPages()) {
            System.out.println("Page number: " + pageData.getNumber());

            //Go through all page rows
            for (int i = 0; i < pageData.getRows().size(); i++) {
                RowData rowData = pageData.getRows().get(i);

                // Write data to console
                System.out.println("Row: " + (i + 1));
                final String text = rowData.getText();
                System.out.println("Text: " + text);
                System.out.println("Text width: " + rowData.getLineWidth());
                System.out.println("Text height: " + rowData.getLineHeight());
                System.out.println("Distance from left: " + rowData.getLineLeft());
                System.out.println("Distance from top: " + rowData.getLineTop());

                // Get words
                if (text != null) {
                    String[] words = text.split(" ");

                    // Go through all word coordinates
                    for (int j = 0; j < words.length; j++) {
                        int coordinateIndex = j * 2;
                        final String word = words[j];
                        if (word == null || word.isEmpty()) {
                            continue;
                        }

                        // Write data to console
                        System.out.println("");
                        System.out.println("Word: '" + word + "'");
                        System.out.println("Word distance from left: " + rowData.getTextCoordinates().get(coordinateIndex));
                        System.out.println("Word width: " + rowData.getTextCoordinates().get(coordinateIndex + 1));
                        System.out.println("");
                    }
                }
            }
        }
    }

    @Test
    public void testHowToClearAllCacheFiles() throws Exception {
        Utilities.showTestHeader();
        // Setup viewer config
        ViewerConfig viewerConfig = new ViewerConfig();
        viewerConfig.setStoragePath(STORAGE_PATH);

        // Init viewer image or html handler
        ViewerImageHandler viewerImageHandler = new ViewerImageHandler(viewerConfig);

        //Clear all cache files
        viewerImageHandler.clearCache();
    }

    @Test
    public void testHowToClearFilesFromCacheOlderThanSpecifiedTimeInterval() throws Exception {
        Utilities.showTestHeader();
        // Setup viewer config
        ViewerConfig viewerConfig = new ViewerConfig();
        viewerConfig.setStoragePath(STORAGE_PATH);
        // Init viewer image or html handler
        ViewerImageHandler viewerImageHandler = new ViewerImageHandler(viewerConfig);

        //Clear files from cache older than specified time interval
        long olderThanTwoDays = 2 * 24 * 60 * 60 * 1000;
        viewerImageHandler.clearCache(olderThanTwoDays);
    }

    @Test
    public void testHowToUseCustomFileDataStore() throws Exception {
        Utilities.showTestHeader();
        // Setup viewer config
        ViewerConfig viewerConfig = new ViewerConfig();
        viewerConfig.setStoragePath(STORAGE_PATH);
        // File guid
        String guid = "document.doc";

//        // Use custom IFileDataStore implementation
//        IFileDataStore fileDataStore = new CustomFileDataStore();
//
//        // Get file HTML representation
//        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(viewerConfig, null, null, fileDataStore);
//
//        List<PageHtml> pages = htmlHandler.getPages(guid);
//        System.out.println("Page count: " + pages.size());
//        assertEquals("Page count is incorrect", pages.size(), 1);
    }

    @Test
    public void testSetDefaultFontName() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        config.setDefaultFontName("Calibri");
    }

    @Test
    public void testShowHiddenPagesForVisioFilesInImageRepresentation() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "sample.vdx";

        // Set image options to show hidden pages
        ImageOptions options = new ImageOptions();
        options.getDiagramOptions().setShowHiddenPages(true);

        DocumentInfoContainer container = imageHandler.getDocumentInfo(guid);

        for (PageData page : container.getPages()) {
            System.out.println("Page number: " + page.getNumber() + ", Page Name: " + page.getName() + ", IsVisible: " + page.isVisible());
        }

        List<PageImage> pages = imageHandler.getPages(guid, options);
        assertEquals("Page count is incorrect", pages.size(), 1);

        for (PageImage page : pages) {
            System.out.println("Page number: " + page.getPageNumber());

            // Page image stream
            InputStream imageContent = page.getStream();
            assertTrue("Page content is empty!", imageContent.available() > 0);
        }
    }

    @Test
    public void testShowHiddenPagesForVisioFilesInHtmlRepresentation() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "sample.vdx";

        // Set html options to show grid lines
        HtmlOptions options = new HtmlOptions();
        options.getDiagramOptions().setShowHiddenPages(true);

        DocumentInfoContainer container = htmlHandler.getDocumentInfo(guid);

        for (PageData page : container.getPages()) {
            System.out.println("Page number: " + page.getNumber() + ", Page Name: " + page.getName() + ", IsVisible: " + page.isVisible());
        }

        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        assertEquals("Page count is incorrect", pages.size(), 1);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            //Console.WriteLine("Html content: {0}", page.HtmlContent);
            String htmlContent = page.getHtmlContent();
            assertTrue("Page content is empty!", htmlContent.length() > 0);
        }
    }

    @Test
    public void testHowToUseCustomCacheDataHandler() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "sample.vdx";

        // Set html options to show grid lines
        HtmlOptions options = new HtmlOptions();
        options.getDiagramOptions().setShowHiddenPages(true);

        DocumentInfoContainer container = htmlHandler.getDocumentInfo(guid);

        for (PageData page : container.getPages()) {
            System.out.println("Page number: " + page.getNumber() + ", Page Name: " + page.getName() + ", IsVisible: " + page.isVisible());
        }

        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        assertEquals("Page count is incorrect", pages.size(), 1);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            //Console.WriteLine("Html content: {0}", page.HtmlContent);
            String htmlContent = page.getHtmlContent();
            assertTrue("Page content is empty!", htmlContent.length() > 0);
        }
    }

    @Test
    public void testLoadFileListForViewerConfigStoragePath() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        // Load file list for ViewerConfig.StoragePath
        FileListContainer container = imageHandler.getFileList();
        for (FileDescription node : container.getFiles()) {
            if (node.isDirectory()) {
                System.out.println("Guid: " + node.getGuid() + " | Name: " + node.getName() + " | LastModificationDate: " + node.getLastModificationDate());
            } else {
                System.out.println("Guid: " + node.getGuid() + " | Name: " + node.getName() + " | Document type: " + node.getDocumentType()
                        + " | File type: " + node.getFileType() + " | Extension: " + node.getExtension() + " | Size: " + node.getSize() + " | LastModificationDate: " + node.getLastModificationDate());
            }
        }
    }

    @Test
    public void testLoadFileListForCustomPath() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        FileListOptions options = new FileListOptions("D:\\");
        // Load file list for ViewerConfig.StoragePath
        FileListContainer container = imageHandler.getFileList(options);
        for (FileDescription node : container.getFiles()) {
            if (node.isDirectory()) {
                System.out.println("Guid: " + node.getGuid() + " | Name: " + node.getName() + " | LastModificationDate: " + node.getLastModificationDate());
            } else {
                System.out.println("Guid: " + node.getGuid() + " | Name: " + node.getName() + " | Document type: " + node.getDocumentType()
                        + " | File type: " + node.getFileType() + " | Extension: " + node.getExtension() + " | Size: " + node.getSize() + " | LastModificationDate: " + node.getLastModificationDate());
            }
        }
    }

    @Test
    public void testLoadFileListForCustomPathWithOrder() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        // Load file list sorted by Name and ordered Ascending for custom path
        FileListOptions options = new FileListOptions(STORAGE_PATH, FileListOptions.FileListSortBy.Name, FileListOptions.FileListOrderBy.Ascending);
        // Load file list for ViewerConfig.StoragePath
        FileListContainer container = imageHandler.getFileList(options);
        for (FileDescription node : container.getFiles()) {
            if (node.isDirectory()) {
                System.out.println("Guid: " + node.getGuid() + " | Name: " + node.getName() + " | LastModificationDate: " + node.getLastModificationDate());
            } else {
                System.out.println("Guid: " + node.getGuid() + " | Name: " + node.getName() + " | Document type: " + node.getDocumentType()
                        + " | File type: " + node.getFileType() + " | Extension: " + node.getExtension() + " | Size: " + node.getSize() + " | LastModificationDate: " + node.getLastModificationDate());
            }
        }
    }

    @Test
    public void testGetAttachmentOriginalFile() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        // Create attachment object and print out its name and file type
        EmailAttachment attachment = new EmailAttachment("document-with-attachments.msg", "attachment-image.png");
        System.out.println("Attach name: " + attachment.getName() + ", size: " + attachment.getFileType());

        // Get attachment original file and print out Stream length
        FileContainer fileContainer = imageHandler.getFile(attachment);
        final InputStream stream = fileContainer.getStream();
        System.out.println("Attach stream lenght: " + stream.available());

        assertTrue("File container is empty!", stream.available() > 0);
    }

}

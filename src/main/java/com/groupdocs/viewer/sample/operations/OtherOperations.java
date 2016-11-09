package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.*;
import com.groupdocs.viewer.domain.containers.*;
import com.groupdocs.viewer.domain.html.HtmlResource;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.domain.options.*;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.handler.cache.ICacheDataHandler;
import com.groupdocs.viewer.helper.FileDataStore;
import com.groupdocs.viewer.helper.IFileDataStore;
import com.groupdocs.viewer.sample.Utilities;
import com.groupdocs.viewer.sample.handler.AmazonS3CacheDataHandler;
import com.groupdocs.viewer.utils.CultureInfo;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;


/**
 * The type Other operations.
 * @author Aleksey Permyakov (27.10.2016)
 */
public class OtherOperations {
    /**
     * Gets original file.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getOriginalFile(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Get original file
        FileContainer container = imageHandler.getFile(guid);
        System.out.println("Stream length: " + container.getStream().available());
        System.out.println();
    }

    /**
     * Gets original file in pdf format without transformations 320.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getOriginalFileInPdfFormatWithoutTransformations320(ViewerImageHandler imageHandler, String guid) throws Exception {
        PdfFileOptions options = new PdfFileOptions();
        options.setGuid(guid);

        // Get file as pdf
        FileContainer container = imageHandler.getPdfFile(options);
        System.out.println("Stream length: " + container.getStream().available());
        System.out.println();
    }

    /**
     * Gets original file in pdf format without transformations 370.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getOriginalFileInPdfFormatWithoutTransformations370(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Get file as pdf
        FileContainer container = imageHandler.getPdfFile(guid);
        System.out.println("Stream lenght: " + container.getStream().available());
        System.out.println();
    }

    /**
     * Gets original file in pdf format with watermark 320.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getOriginalFileInPdfFormatWithWatermark320(ViewerImageHandler imageHandler, String guid) throws Exception {
        PdfFileOptions options = new PdfFileOptions();
        options.setGuid(guid);

        // Set watermark properties
        Watermark watermark = new Watermark("This is watermark text");
        watermark.setColor(Color.BLUE);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);

        options.setWatermark(watermark);

        // Get file as pdf with watermaks
        FileContainer container = imageHandler.getPdfFile(options);
        System.out.println("Stream length: " + container.getStream().available());
        System.out.println();
    }

    /**
     * Gets original file in pdf format with watermark 370.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getOriginalFileInPdfFormatWithWatermark370(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Set watermark properties
        Watermark watermark = new Watermark("This is watermark text");
        watermark.setColor(Color.BLUE);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);

        PdfFileOptions options = new PdfFileOptions();
        options.setWatermark(watermark);

        // Get file as pdf with watermaks
        FileContainer container = imageHandler.getPdfFile(guid, options);
        System.out.println("Stream length: " + container.getStream().available());
        System.out.println();
    }

    /**
     * Gets original file in pdf format with watermark and font name specified 370.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getOriginalFileInPdfFormatWithWatermarkAndFontNameSpecified370(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Set watermark properties
        Watermark watermark = new Watermark("透かし文字、");
        watermark.setColor(Color.BLUE);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);
        // Set watermark font name which contains Japanese characters
        watermark.setFontName("MS Gothic");

        PdfFileOptions options = new PdfFileOptions();
        options.setWatermark(watermark);

        // Get file as pdf with watermarks
        FileContainer container = imageHandler.getPdfFile(guid, options);
        System.out.println("Stream length: " + container.getStream().available());
        System.out.println();
    }

    /**
     * Gets original file in pdf format with print action 320.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getOriginalFileInPdfFormatWithPrintAction320(ViewerImageHandler imageHandler, String guid) throws Exception {
        PdfFileOptions options = new PdfFileOptions();
        options.setGuid(guid);

        // Set add print action property
        options.setAddPrintAction(true);

        // Get file as pdf with print action
        FileContainer container = imageHandler.getPdfFile(options);
        System.out.println("Stream lenght: " + container.getStream().available());
        System.out.println();
    }

    /**
     * Gets original file in pdf format with print action 370.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getOriginalFileInPdfFormatWithPrintAction370(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Set add print action property
        PdfFileOptions options = new PdfFileOptions();
        options.setTransformations(Transformation.AddPrintAction);

        // Get file as pdf with print action
        FileContainer container = imageHandler.getPdfFile(guid, options);
        System.out.println("Stream length: " + container.getStream().available());
        System.out.println();
    }

    /**
     * Gets original file in pdf format with transformations 320.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getOriginalFileInPdfFormatWithTransformations320(ViewerImageHandler imageHandler, String guid) throws Exception {
        PdfFileOptions options = new PdfFileOptions();
        options.setGuid(guid);

        // Set apply rotate and reorder transformations
        options.setTransformations(Transformation.Rotate, Transformation.Reorder, Transformation.AddPrintAction);

        // Get file as pdf with transformations
        FileContainer container = imageHandler.getPdfFile(options);
        System.out.println("Stream length: " + container.getStream().available());
        System.out.println();
    }

    /**
     * Gets original file in pdf format with transformations 370.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getOriginalFileInPdfFormatWithTransformations370(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Perform page rotation
        RotatePageOptions rotatePageOptions = new RotatePageOptions(guid, 1, 90);
        imageHandler.rotatePage(rotatePageOptions);

        // Reorder pages, move 1 page to the 2 position, it is assumed that "word.doc" document has at least two pages
        ReorderPageOptions reorderPageOptions = new ReorderPageOptions(guid, 1, 2);
        imageHandler.reorderPage(reorderPageOptions);

        // Set apply rotate and reorder transformations
        PdfFileOptions options = new PdfFileOptions();
        options.setTransformations(Transformation.Rotate, Transformation.Reorder, Transformation.AddPrintAction);

        // Get file as pdf with transformations
        FileContainer container = imageHandler.getPdfFile(guid, options);
        System.out.println("Stream length: " + container.getStream().available());
        System.out.println();
    }

    /**
     * Gets document representation from absolute path.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getDocumentRepresentationFromAbsolutePath(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Get pages by absolute path
        List<PageImage> pages = imageHandler.getPages(guid);
        System.out.println("Page count: " + pages.size());
        for (PageImage pageImage : pages) {
            System.out.println("\tPage number: " + pageImage.getPageNumber());
            System.out.println("\tStream content: " + pageImage.getStream().available());
        }
        System.out.println();
    }

    /**
     * Gets document representation from relative path.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getDocumentRepresentationFromRelativePath(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Get pages by absolute path
        List<PageImage> pages = imageHandler.getPages(guid);
        System.out.println("Page count: " + pages.size());
        for (PageImage pageImage : pages) {
            System.out.println("\tPage number: " + pageImage.getPageNumber());
            System.out.println("\tStream content: " + pageImage.getStream().available());
        }
        System.out.println();
    }

    /**
     * Gets document representation from url.
     * @param imageHandler the image handler
     * @param url the url
     * @throws Exception the exception
     */
    public static void getDocumentRepresentationFromUrl(ViewerImageHandler imageHandler, String url) throws Exception {
        URI uri = new URI(url);

        // Get pages by absolute path
        List<PageImage> pages = imageHandler.getPages(uri);
        System.out.println("Page count: " + pages.size());
        for (PageImage pageImage : pages) {
            System.out.println("\tPage number: " + pageImage.getPageNumber());
            System.out.println("\tStream content: " + pageImage.getStream().available());
        }
        System.out.println();
    }

    /**
     * Gets document representation from input stream.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getDocumentRepresentationFromInputStream(ViewerImageHandler imageHandler, String guid) throws Exception {
        FileInputStream fileStream = new FileInputStream(Utilities.STORAGE_PATH + File.separator + guid);

        // Get pages by absolute path
        List<PageImage> pages = imageHandler.getPages(fileStream, guid);
        System.out.println("Page count: " + pages.size());
        for (PageImage pageImage : pages) {
            System.out.println("\tPage number: " + pageImage.getPageNumber());
            System.out.println("\tStream content: " + pageImage.getStream().available());
        }
        System.out.println();
    }

    /**
     * How to use custom input data handler.
     * @param config the config
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void howToUseCustomInputDataHandler(ViewerConfig config, String guid) throws Exception {
        // Use custom IInputDataHandler implementation
//        IInputDataHandler inputDataHandler = new CustomInputDataHandler();
//        IInputDataHandler inputDataHandler = new AzureInputDataHandler("name", "key", "container");
//        IInputDataHandler inputDataHandler = new FtpInputDataHandler();
        // Get file HTML representation
//        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config, inputDataHandler);
//        List<PageHtml> pages = htmlHandler.getPages(guid);
//        System.out.println(pages.size());
        System.out.println();
    }

    /**
     * Load file tree list for viewer config storage path.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void loadFileTreeListForViewerConfigStoragePath(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Load file tree list for ViewerConfig.StoragePath
        FileTreeContainer container = imageHandler.loadFileTree();

        for (FileDescription node : container.getFileTree()) {
            if (node.isDirectory()) {
                System.out.println("Guid: " + node.getGuid() + " | Name: " + node.getName() + " | LastModificationDate: " + node.getLastModificationDate());
            } else {
                System.out.println("Guid: " + node.getGuid() + " | Name: " + node.getName() + " | Document type: " + node.getDocumentType() + " | File type: " + node.getFileType() + " | Extension: " + node.getExtension() + " | Size: " + node.getSize() + " | LastModificationDate: " + node.getLastModificationDate());
            }
        }
        System.out.println();
    }

    /**
     * http://lisbon.dynabic.com/wiki/display/viewer/05.++Load+file+tree
     * @param imageHandler the image handler
     * @param path the path
     * @throws Exception the exception
     */
    public static void loadFileTreeListForCustomPath(ViewerImageHandler imageHandler, String path) throws Exception {
        // Load file tree list for custom path
        FileTreeOptions options = new FileTreeOptions(path);

        FileTreeContainer container = imageHandler.loadFileTree(options);

        for (FileDescription node : container.getFileTree()) {
            if (node.isDirectory()) {
                System.out.println("Guid: " + node.getGuid() + " | Name: " + node.getName() + " | LastModificationDate: " + node.getLastModificationDate());
            } else {
                System.out.println("Guid: " + node.getGuid() + " | Name: " + node.getName() + " | Document type: " + node.getDocumentType() + " | File type: " + node.getFileType() + " | Extension: " + node.getExtension() + " | Size: " + node.getSize() + " | LastModificationDate: " + node.getLastModificationDate());
            }
        }
        System.out.println();
    }

    /**
     * http://lisbon.dynabic.com/wiki/display/viewer/05.++Load+file+tree
     * @param imageHandler the image handler
     * @param path the path
     * @throws Exception the exception
     */
    public static void loadFileTreeListForCustomPathWithOrder(ViewerImageHandler imageHandler, String path) throws Exception {
        // Load file tree list ordered by Name field for custom path
        FileTreeOptions options = new FileTreeOptions(path, FileTreeOptions.FileTreeOrderBy.Name, true);

        FileTreeContainer container = imageHandler.loadFileTree(options);

        for (FileDescription node : container.getFileTree()) {
            if (node.isDirectory()) {
                System.out.println("Guid: " + node.getGuid() + " | Name: " + node.getName() + " | LastModificationDate: " + node.getLastModificationDate());
            } else {
                System.out.println("Guid: " + node.getGuid() + " | Name: " + node.getName() + " | Document type: " + node.getDocumentType() + " | File type: " + node.getFileType() + " | Extension: " + node.getExtension() + " | Size: " + node.getSize() + " | LastModificationDate: " + node.getLastModificationDate());
            }
        }
        System.out.println();
    }

    /**
     * Gets document html for print.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getDocumentHtmlForPrint(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Get document html for print
        PrintableHtmlOptions options = new PrintableHtmlOptions(guid);
        PrintableHtmlContainer container = imageHandler.getPrintableHtml(options);

        System.out.println("Html content: " + container.getHtmlContent());
        System.out.println();
    }

    /**
     * Gets document html for print with watermark.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getDocumentHtmlForPrintWithWatermark(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Get document html for print with watermark
        PrintableHtmlOptions options = new PrintableHtmlOptions(guid, new Watermark("Watermark text"));
        PrintableHtmlContainer container = imageHandler.getPrintableHtml(options);

        System.out.println("Html content: " + container.getHtmlContent());
        System.out.println();
    }

    /**
     * Gets document html for print with custom css.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getDocumentHtmlForPrintWithCustomCss(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Get document html for print with custom css
        String css = "a { color: hotpink; }"; // Some style
        PrintableHtmlOptions options = new PrintableHtmlOptions(guid, css);
        PrintableHtmlContainer container = imageHandler.getPrintableHtml(options);

        System.out.println("Html content: " + container.getHtmlContent());
        System.out.println();
    }

    /**
     * Show grid lines for excel files in image representation.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void showGridLinesForExcelFilesInImageRepresentation(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Set image options to show grid lines
        ImageOptions options = new ImageOptions();
        options.getCellsOptions().setShowGridLines(true);

        List<PageImage> pages = imageHandler.getPages(guid, options);

        for (PageImage page : pages) {
            System.out.println("Page number: " + page.getPageNumber());

            // Page image stream
            InputStream imageContent = page.getStream();
            System.out.println(imageContent.available());
        }
        System.out.println();
    }

    /**
     * Show grid lines for excel files in html representation.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void showGridLinesForExcelFilesInHtmlRepresentation(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        // Set html options to show grid lines
        HtmlOptions options = new HtmlOptions();
        options.getCellsOptions().setShowGridLines(true);
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            System.out.println("Html content: " + page.getHtmlContent().substring(0, 150) + "...");
        }
        System.out.println();
    }

    /**
     * Multiple pages per sheet with get pages method in image mode 320.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void multiplePagesPerSheetWithGetPagesMethodInImageMode320(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Set pdf file one page per sheet option to false, default value of this option is true
        PdfFileOptions pdfFileOptions = new PdfFileOptions();
        pdfFileOptions.setGuid(guid);
        pdfFileOptions.getCellsOptions().setOnePagePerSheet(false);

        //Get pdf file
        FileContainer fileContainer = imageHandler.getPdfFile(pdfFileOptions);

        //The pdf file stream
        InputStream pdfStream = fileContainer.getStream();
        System.out.println("Stream length: " + pdfStream.available());
        System.out.println();
    }

    /**
     * Multiple pages per sheet with get pages method in image mode 370.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void multiplePagesPerSheetWithGetPagesMethodInImageMode370(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Set pdf file one page per sheet option to false, default value of this option is true
        ImageOptions imageOptions = new ImageOptions();
        imageOptions.getCellsOptions().setOnePagePerSheet(false);

        //Get pages
        List<PageImage> pages = imageHandler.getPages(guid, imageOptions);
        System.out.println("Page count: " + pages.size());
        for (PageImage pageImage : pages) {
            System.out.println("\tPage number: " + pageImage.getPageNumber());
            System.out.println("\tStream content: " + pageImage.getStream().available());
        }
        System.out.println();
    }

    /**
     * Gets all supported document formats.
     * @param imageHandler the image handler
     */
    public static void getAllSupportedDocumentFormats(ViewerImageHandler imageHandler) {
        // Get supported document formats
        DocumentFormatsContainer documentFormatsContainer = imageHandler.getSupportedDocumentFormats();
        Map<String, String> supportedDocumentFormats = documentFormatsContainer.getSupportedDocumentFormats();

        for (Map.Entry<String, String> supportedDocumentFormat : supportedDocumentFormats.entrySet()) {
            System.out.println("Extension: '" + supportedDocumentFormat.getKey() + "'; Document format: '" + supportedDocumentFormat.getValue() + "'");
        }
        System.out.println();
    }

    /**
     * Show hidden sheets for excel files in image representation.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void showHiddenSheetsForExcelFilesInImageRepresentation(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Set image options to show grid lines
        ImageOptions options = new ImageOptions();
        options.getCellsOptions().setShowHiddenSheets(true);

        DocumentInfoContainer container = imageHandler.getDocumentInfo(new DocumentInfoOptions(guid));

        for (PageData page : container.getPages()) {
            System.out.println("Page number: " + page.getNumber() + ", Page Name: " + page.getName() + ", IsVisible: " + page.isVisible());
        }
        List<PageImage> pages = imageHandler.getPages(guid, options);

        for (PageImage page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            // Page image stream
            System.out.println("Stream length: " + page.getStream().available());
        }
        System.out.println();
    }

    /**
     * Show hidden sheets for excel files in html representation.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void showHiddenSheetsForExcelFilesInHtmlRepresentation(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        // Set html options to show grid lines
        HtmlOptions options = new HtmlOptions();
        options.getCellsOptions().setShowHiddenSheets(true);

        DocumentInfoContainer container = htmlHandler.getDocumentInfo(new DocumentInfoOptions(guid));

        for (PageData page : container.getPages()) {
            System.out.println("Page number: " + page.getNumber() + ", Page Name: " + page.getName() + ", IsVisible: " + page.isVisible());
        }
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            // Page image stream
            System.out.println("Stream length: " + page.getHtmlContent().substring(0, 150) + "...");
        }
        System.out.println();
    }

    /**
     * How to create and use file with localized strings.
     */
    public static void howToCreateAndUseFileWithLocalizedStrings() {
        // Setup viewer config
        ViewerConfig viewerConfig = new ViewerConfig();
        viewerConfig.setStoragePath("c:\\storage");
        viewerConfig.setLocalesPath("c:\\locales");

        // Create html handler
        CultureInfo cultureInfo = new CultureInfo("fr-FR");
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(viewerConfig, cultureInfo);
        System.out.println("Html handler: " + htmlHandler);
        System.out.println();
    }

    /**
     * How to get document pages with words cells and email document encoding setting.
     * @param imageHandler the image handler
     * @param wordsDocumentGuid the words document guid
     * @param cellsDocumentGuid the cells document guid
     * @param emailDocumentGuid the email document guid
     * @throws Exception the exception
     */
    public static void howToGetDocumentPagesWithWordsCellsAndEmailDocumentEncodingSetting(ViewerImageHandler imageHandler, String wordsDocumentGuid, String cellsDocumentGuid, String emailDocumentGuid) throws Exception {
        //Set encoding
        Charset encoding = Charset.forName("shift-jis");

        //Set image options
        ImageOptions imageOptions = new ImageOptions();
        imageOptions.getWordsOptions().setEncoding(encoding);
        imageOptions.getCellsOptions().setEncoding(encoding);
        imageOptions.getEmailOptions().setEncoding(encoding);

        //Get words document pages with encoding
        List<PageImage> wordsDocumentPages = imageHandler.getPages(wordsDocumentGuid, imageOptions);
        System.out.println("Pages count: " + wordsDocumentPages.size());

        //Get cells document pages with encoding
        List<PageImage> cellsDocumentPages = imageHandler.getPages(cellsDocumentGuid, imageOptions);
        System.out.println("Pages count: " + cellsDocumentPages.size());

        //Get email document pages with encoding
        List<PageImage> emailDocumentPages = imageHandler.getPages(emailDocumentGuid, imageOptions);
        System.out.println("Pages count: " + emailDocumentPages.size());

        //Get words document info with encoding
        DocumentInfoOptions wordsDocumentInfoOptions = new DocumentInfoOptions(wordsDocumentGuid);
        wordsDocumentInfoOptions.getWordsDocumentInfoOptions().setEncoding(encoding);
        DocumentInfoContainer wordsDocumentInfoContainer = imageHandler.getDocumentInfo(wordsDocumentInfoOptions);
        System.out.println("File size: " + wordsDocumentInfoContainer.getSize());

        //Get cells document info with encoding
        DocumentInfoOptions cellsDocumentInfoOptions = new DocumentInfoOptions(cellsDocumentGuid);
        cellsDocumentInfoOptions.getCellsDocumentInfoOptions().setEncoding(encoding);
        DocumentInfoContainer cellsDocumentInfoContainer = imageHandler.getDocumentInfo(cellsDocumentInfoOptions);
        System.out.println("File size: " + cellsDocumentInfoContainer.getSize());

        //Get email document info with encoding
        DocumentInfoOptions emailDocumentInfoOptions = new DocumentInfoOptions(emailDocumentGuid);
        emailDocumentInfoOptions.getEmailDocumentInfoOptions().setEncoding(encoding);
        DocumentInfoContainer emailDocumentInfoContainer = imageHandler.getDocumentInfo(emailDocumentInfoOptions);
        System.out.println("File size: " + emailDocumentInfoContainer.getSize());
        System.out.println();
    }

    /**
     * How to get document information with words cells and email document encoding setting 320.
     * @param imageHandler the image handler
     * @param wordsDocumentGuid the words document guid
     * @param cellsDocumentGuid the cells document guid
     * @param emailDocumentGuid the email document guid
     * @throws Exception the exception
     */
    public static void howToGetDocumentInformationWithWordsCellsAndEmailDocumentEncodingSetting320(ViewerImageHandler imageHandler, String wordsDocumentGuid, String cellsDocumentGuid, String emailDocumentGuid) throws Exception {
        //Set encoding
        Charset encoding = Charset.forName("shift-jis");

        //Get words document info with encoding
        DocumentInfoOptions wordsDocumentInfoOptions = new DocumentInfoOptions(wordsDocumentGuid);
        wordsDocumentInfoOptions.getWordsDocumentInfoOptions().setEncoding(encoding);
        DocumentInfoContainer wordsDocumentInfoContainer = imageHandler.getDocumentInfo(wordsDocumentInfoOptions);
        System.out.println("File size: " + wordsDocumentInfoContainer.getSize());

        //Get cells document info with encoding
        DocumentInfoOptions cellsDocumentInfoOptions = new DocumentInfoOptions(cellsDocumentGuid);
        cellsDocumentInfoOptions.getCellsDocumentInfoOptions().setEncoding(encoding);
        DocumentInfoContainer cellsDocumentInfoContainer = imageHandler.getDocumentInfo(cellsDocumentInfoOptions);
        System.out.println("File size: " + cellsDocumentInfoContainer.getSize());

        //Get email document info with encoding
        DocumentInfoOptions emailDocumentInfoOptions = new DocumentInfoOptions(emailDocumentGuid);
        emailDocumentInfoOptions.getEmailDocumentInfoOptions().setEncoding(encoding);
        DocumentInfoContainer emailDocumentInfoContainer = imageHandler.getDocumentInfo(emailDocumentInfoOptions);
        System.out.println("File size: " + emailDocumentInfoContainer.getSize());
        System.out.println();
    }

    /**
     * How to get document information with words cells and email document encoding setting 370.
     * @param imageHandler the image handler
     * @param wordsDocumentGuid the words document guid
     * @param cellsDocumentGuid the cells document guid
     * @param emailDocumentGuid the email document guid
     * @throws Exception the exception
     */
    public static void howToGetDocumentInformationWithWordsCellsAndEmailDocumentEncodingSetting370(ViewerImageHandler imageHandler, String wordsDocumentGuid, String cellsDocumentGuid, String emailDocumentGuid) throws Exception {
        //Set encoding
        Charset encoding = Charset.forName("shift-jis");

        //Get words document info with encoding
        DocumentInfoOptions wordsDocumentInfoOptions = new DocumentInfoOptions();
        wordsDocumentInfoOptions.getWordsOptions().setEncoding(encoding);
        DocumentInfoContainer wordsDocumentInfoContainer = imageHandler.getDocumentInfo(wordsDocumentGuid, wordsDocumentInfoOptions);
        System.out.println("File size: " + wordsDocumentInfoContainer.getSize());

        //Get cells document info with encoding
        DocumentInfoOptions cellsDocumentInfoOptions = new DocumentInfoOptions();
        cellsDocumentInfoOptions.getCellsOptions().setEncoding(encoding);
        DocumentInfoContainer cellsDocumentInfoContainer = imageHandler.getDocumentInfo(cellsDocumentGuid, cellsDocumentInfoOptions);
        System.out.println("File size: " + cellsDocumentInfoContainer.getSize());

        //Get email document info with encoding
        DocumentInfoOptions emailDocumentInfoOptions = new DocumentInfoOptions();
        emailDocumentInfoOptions.getEmailOptions().setEncoding(encoding);
        DocumentInfoContainer emailDocumentInfoContainer = imageHandler.getDocumentInfo(emailDocumentGuid, emailDocumentInfoOptions);
        System.out.println("File size: " + emailDocumentInfoContainer.getSize());
        System.out.println();
    }

    /**
     * How to get text coordinates in image mode.
     * @param config the config
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void howToGetTextCoordinatesInImageMode(ViewerConfig config, String guid) throws Exception {
        config.setUsePdf(true);

        // Init viewer image handler
        ViewerImageHandler viewerImageHandler = new ViewerImageHandler(config);

        // Init document info options
        DocumentInfoOptions documentInfoOptions = new DocumentInfoOptions();
        //Get document info
        DocumentInfoContainer documentInfoContainer = viewerImageHandler.getDocumentInfo(guid, documentInfoOptions);
        System.out.println("File name: " + documentInfoContainer.getName());
        // Go through all pages
        for (PageData pageData : documentInfoContainer.getPages()) {
            System.out.println("\tPage number: " + pageData.getNumber());

            //Go through all page rows
            for (int i = 0; i < pageData.getRows().size(); i++) {
                RowData rowData = pageData.getRows().get(i);

                // Write data to console
                System.out.println("\t\tRow: " + (i + 1));
                System.out.println("\t\tText: " + rowData.getText());
                System.out.println("\t\tText width: " + rowData.getLineWidth());
                System.out.println("\t\tText height: " + rowData.getLineHeight());
                System.out.println("\t\tDistance from left: " + rowData.getLineLeft());
                System.out.println("\t\tDistance from top: " + rowData.getLineTop());

                // Get words
                String[] words = rowData.getText().split(" ");

                // Go through all word coordinates
                for (int j = 0; j < words.length; j++) {
                    int coordinateIndex = j == 0 ? 0 : j + 1;

                    // Write data to console
                    System.out.println("\t\t\tWord: '" + words[j] + "'");
                    System.out.println("\t\t\tWord distance from left: " + rowData.getTextCoordinates().get(coordinateIndex));
                    System.out.println("\t\t\tWord width: " + rowData.getTextCoordinates().get(coordinateIndex + 1));
                }
            }
        }
        System.out.println();
    }

    /**
     * How to clear all cache files.
     * @param imageHandler the image handler
     * @throws Exception the exception
     */
    public static void howToClearAllCacheFiles(ViewerImageHandler imageHandler) throws Exception {
        //Clear all cache files
        imageHandler.clearCache();
        System.out.println();
    }

    /**
     * How to clear files from cache older than specified time interval.
     * @param imageHandler the image handler
     * @throws Exception the exception
     */
    public static void howToClearFilesFromCacheOlderThanSpecifiedTimeInterval(ViewerImageHandler imageHandler) throws Exception {
        //Clear files from cache older than specified time interval
        long olderThanTwoDays = 2 * 24 * 60 * 60 * 1000;
        imageHandler.clearCache(olderThanTwoDays);
        System.out.println();
    }

    /**
     * How to use custom file data store.
     * @param config the config
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void howToUseCustomFileDataStore(ViewerConfig config, String guid) throws Exception {
        // Use custom IFileDataStore implementation
//        IFileDataStore fileDataStore = new CustomFileDataStore();
        IFileDataStore fileDataStore = new FileDataStore(config);

        // Get file HTML representation
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config, null, null, fileDataStore);

        List<PageHtml> pages = htmlHandler.getPages(guid);
        System.out.println("Page count: " + pages.size());
        System.out.println();
    }

    /**
     * Sets default font name.
     * @param config the config
     */
    public static void setDefaultFontName(ViewerConfig config) {
        config.setDefaultFontName("Calibri");
        System.out.println();
    }


    /**
     * Show hidden pages for visio files in image representation.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void showHiddenPagesForVisioFilesInImageRepresentation(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Set image options to show hidden pages
        ImageOptions options = new ImageOptions();
        options.getDiagramOptions().setShowHiddenPages(true);

        DocumentInfoContainer container = imageHandler.getDocumentInfo(guid);
        System.out.println("File name: " + container.getSize());
        System.out.println("Page count" + container.getPages().size());

        for (PageData page : container.getPages()) {
            System.out.println("\tPage number: " + page.getNumber() + ", Page Name: " + page.getName() + ", IsVisible: " + page.isVisible());
        }

        List<PageImage> pages = imageHandler.getPages(guid, options);

        for (PageImage page : pages) {
            System.out.println("\tPage number: " + page.getPageNumber() + ", Stream length: " + page.getStream().available());
        }
        System.out.println();
    }

    /**
     * Show hidden pages for visio files in html representation.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void showHiddenPagesForVisioFilesInHtmlRepresentation(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        // Set html options to show grid lines
        HtmlOptions options = new HtmlOptions();
        options.getDiagramOptions().setShowHiddenPages(true);

        DocumentInfoContainer container = htmlHandler.getDocumentInfo(guid);
        System.out.println("File name: " + container.getSize());
        System.out.println("Page count: " + container.getPages().size());

        for (PageData page : container.getPages()) {
            System.out.println("\tPage number: " + page.getNumber());
            System.out.println("\tPage Name: " + page.getName());
            System.out.println("\tIsVisible: " + page.isVisible());
        }

        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            System.out.println("\tPage number: " + page.getPageNumber());
            System.out.println("\tHtml content: " + page.getHtmlContent().substring(0, 150) + "...");
        }
        System.out.println();
    }

    /**
     * How to use custom cache data handler.
     * @param config the config
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void howToUseCustomCacheDataHandler(ViewerConfig config, String guid) throws Exception {
        // Use custom ICacheDataHandler implementation
//        ICacheDataHandler cacheDataHandler = new CustomCacheDataHandler();
//        ICacheDataHandler cacheDataHandler = new AmazonS3CacheDataHandler(null, null);
//
//        // Get file HTML representation
//        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config, null, cacheDataHandler);
//
//        List<PageHtml> pages = htmlHandler.getPages(guid);
//        System.out.println(pages.size());
//        System.out.println();
    }

    /**
     * Gets attachment original file.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getAttachmentOriginalFile(ViewerImageHandler imageHandler, String guid, String name) throws Exception {
        EmailAttachment attachment = new EmailAttachment(guid, name);

        // Get attachment original file
        FileContainer fileContainer = imageHandler.getFile(attachment);
        System.out.println("Attach name: " + attachment.getName() + ", size: " + attachment.getFileType());
        System.out.println("Attach stream length: " + fileContainer.getStream().available());
        System.out.println();
    }

    /**
     * Gets attachment document html representation.
     * @param config the config
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getAttachmentDocumentHtmlRepresentation(ViewerConfig config, String guid) throws Exception {
        config.setUseCache(true);

        // Setup html conversion options
        HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.setResourcesEmbedded(false);

        // Init viewer html handler
        ViewerHtmlHandler handler = new ViewerHtmlHandler(config);

        DocumentInfoContainer info = handler.getDocumentInfo(guid);
        System.out.println("File name: " + info.getSize());
        System.out.println("Page count: " + info.getPages().size());

        // Iterate over the attachments collection
        for (AttachmentBase attachment : info.getAttachments()) {
            System.out.println("\tAttach name: " + attachment.getName() + ", size: " + attachment.getFileType());

            // Get attachment document html representation
            List<PageHtml> pages = handler.getPages(attachment, htmlOptions);
            for (PageHtml page : pages) {
                System.out.println("\t\tPage: " + page.getPageNumber());
                System.out.println("\t\tSize: " + page.getHtmlContent().length());
                for (HtmlResource htmlResource : page.getHtmlResources()) {
                    InputStream resourceStream = handler.getResource(attachment, htmlResource);
                    System.out.println("\t\t\tResource: " + htmlResource.getResourceName());
                    System.out.println("\t\t\tSize: " + resourceStream.available());
                }
            }
        }
        System.out.println();
    }

    /**
     * Gets attachment document image representation.
     * @param config the config
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getAttachmentDocumentImageRepresentation(ViewerConfig config, String guid) throws Exception {
        config.setUseCache(true);

        // Init viewer image handler
        ViewerImageHandler handler = new ViewerImageHandler(config);

        DocumentInfoContainer info = handler.getDocumentInfo(guid);
        System.out.println("File name: " + info.getSize());
        System.out.println("Page count: " + info.getPages().size());

        // Iterate over the attachments collection
        for (AttachmentBase attachment : info.getAttachments()) {
            System.out.println("\tAttach name: " + attachment.getName());
            System.out.println("\tSize: " + attachment.getFileType());

            // Get attachment document image representation
            List<PageImage> pages = handler.getPages(attachment);
            for (PageImage page : pages) {
                System.out.println("\t\tPage: " + page.getPageNumber());
                System.out.println("\t\tSize: " + page.getStream().available());
            }
        }
        System.out.println();
    }
}

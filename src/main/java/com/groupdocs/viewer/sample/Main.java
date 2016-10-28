package com.groupdocs.viewer.sample;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.sample.operations.*;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        /**
         *  Applying product license
         *  Please uncomment the statement if you do have license.
         */
        Utilities.applyLicense();

        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(Utilities.STORAGE_PATH);

        CommonOperations.setCustomFontDirectoryPath(config);
        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        CommonOperations.getDocumentInformationByGuid320(htmlHandler, "word.doc");
        CommonOperations.getDocumentInformationByGuid370(htmlHandler, "word.doc");
        CommonOperations.getDocumentInformationByStream(htmlHandler, "word.doc");
        CommonOperations.getDocumentInformationByStreamAndName(htmlHandler, "word.doc");
        CommonOperations.getDocumentInformationByUrl(htmlHandler, "http://eve-marketdata.com/images/icon_uploader.png");
        CommonOperations.getDocumentInformationByUrlWindowsAuthenticationCredential(htmlHandler, "http://eve-marketdata.com/images/icon_uploader.png");

        System.out.println();

        HtmlOperations.getHtmlRepresentationWithEmbeddedResources(htmlHandler, "word.doc");
        HtmlOperations.getHtmlRepresentation(htmlHandler, "word.doc");
        HtmlOperations.getHtmlRepresentationOfNConsecutivePages(htmlHandler, "word.doc");
        HtmlOperations.getHtmlRepresentationOfCustomPageNumbers(htmlHandler, "word.doc");
        HtmlOperations.specifyInternalHyperlinkPrefixForExcelFiles();
        HtmlOperations.specifyResourcePrefix();

        System.out.println();

        ImageOperations.getImageRepresentation(imageHandler, "word.doc");
        ImageOperations.getImageRepresentationOfNConsecutivePages(imageHandler, "word.doc");
        ImageOperations.getImageRepresentationOfCustomPageNumbers(imageHandler, "word.doc");
        ImageOperations.getImageRepresentationWithJpegQuality(imageHandler, "word.doc");

        System.out.println();

        TransformationOperations.rotate1stImagePageBy90Deg(imageHandler, "word.doc");
        TransformationOperations.reorder1stAnd2ndImagePages(imageHandler, "word.doc");

        System.out.println();

        TransformationOperations.retrieveAllImagePagesIncludingTransformation(imageHandler, "word.doc");
        TransformationOperations.retrieveAllImagePagesExcludingTransformation(imageHandler, "word.doc");

        System.out.println();

        TransformationOperations.rotateHtml1stPageBy90Deg(htmlHandler, "word.doc");
        TransformationOperations.reorderHtml1stPageBy90Deg(htmlHandler, "word.doc");

        System.out.println();

        TransformationOperations.retrieveAllHtmlPagesIncludingTransformation(htmlHandler, "word.doc");
        TransformationOperations.retrieveAllHtmlPagesExcludingTransformation(htmlHandler, "word.doc");

        System.out.println();

        TransformationOperations.addWatermarkToImagePageRepresentation(imageHandler, "word.doc");
        TransformationOperations.addWatermarkWithFontNameToImagePageRepresentation(imageHandler, "word.doc");
        TransformationOperations.addWatermarkToHtmlPageRepresentation(htmlHandler, "word.doc");
        TransformationOperations.addWatermarkWithFontNameToHtmlPageRepresentation(htmlHandler, "word.doc");

        System.out.println();

        TransformationOperations.performMultipleTransformationsInImageMode(imageHandler, "word.doc");
        TransformationOperations.performMultipleTransformationsInHtmlMode(htmlHandler, "word.doc");

        System.out.println();

        OtherOperations.getOriginalFile(imageHandler, "word.doc");

        System.out.println();

        OtherOperations.getOriginalFileInPdfFormatWithoutTransformations320(imageHandler, "word.doc");
        OtherOperations.getOriginalFileInPdfFormatWithoutTransformations370(imageHandler, "word.doc");
        OtherOperations.getOriginalFileInPdfFormatWithWatermark320(imageHandler, "word.doc");
        OtherOperations.getOriginalFileInPdfFormatWithWatermark370(imageHandler, "word.doc");
        OtherOperations.getOriginalFileInPdfFormatWithWatermarkAndFontNameSpecified370(imageHandler, "word.doc");
        OtherOperations.getOriginalFileInPdfFormatWithPrintAction320(imageHandler, "word.doc");
        OtherOperations.getOriginalFileInPdfFormatWithPrintAction370(imageHandler, "word.doc");
        OtherOperations.getOriginalFileInPdfFormatWithTransformations320(imageHandler, "word.doc");
        OtherOperations.getOriginalFileInPdfFormatWithTransformations370(imageHandler, "word.doc");

        System.out.println();

        OtherOperations.getDocumentRepresentationFromAbsolutePath(imageHandler, "word.doc");
        OtherOperations.getDocumentRepresentationFromRelativePath(imageHandler, "word.doc");
        OtherOperations.getDocumentRepresentationFromUrl(imageHandler, "http://eve-marketdata.com/images/icon_uploader.png");
        OtherOperations.getDocumentRepresentationFromInputStream(imageHandler, "word.doc");

        System.out.println();

        OtherOperations.howToUseCustomInputDataHandler(config, "word.doc");

        System.out.println();

        OtherOperations.loadFileTreeListForViewerConfigStoragePath(imageHandler, "word.doc");
        OtherOperations.loadFileTreeListForCustomPath(imageHandler, "D:\\");
        OtherOperations.loadFileTreeListForCustomPathWithOrder(imageHandler, "D:\\");
        OtherOperations.getDocumentHtmlForPrint(imageHandler, "word.doc");
        OtherOperations.getDocumentHtmlForPrintWithWatermark(imageHandler, "word.doc");
        OtherOperations.getDocumentHtmlForPrintWithCustomCss(imageHandler, "word.doc");

        System.out.println();

        OtherOperations.showGridLinesForExcelFilesInImageRepresentation(imageHandler, "word.doc");
        OtherOperations.showGridLinesForExcelFilesInHtmlRepresentation(htmlHandler, "word.doc");

        System.out.println();

        OtherOperations.multiplePagesPerSheetWithGetPagesMethodInImageMode320(imageHandler, "word.doc");
        OtherOperations.multiplePagesPerSheetWithGetPagesMethodInImageMode370(imageHandler, "word.doc");

        System.out.println();

        OtherOperations.getAllSupportedDocumentFormats(imageHandler);

        System.out.println();

        OtherOperations.showHiddenSheetsForExcelFilesInImageRepresentation(imageHandler, "word.doc");
        OtherOperations.showHiddenSheetsForExcelFilesInHtmlRepresentation(htmlHandler, "word.doc");

        System.out.println();

        OtherOperations.howToCreateAndUseFileWithLocalizedStrings();

        System.out.println();

        OtherOperations.howToGetDocumentPagesWithWordsCellsAndEmailDocumentEncodingSetting(imageHandler, "word.txt", "word.csv", "word.msg");
        OtherOperations.howToGetDocumentInformationWithWordsCellsAndEmailDocumentEncodingSetting320(imageHandler, "word.txt", "word.csv", "word.msg");
        OtherOperations.howToGetDocumentInformationWithWordsCellsAndEmailDocumentEncodingSetting370(imageHandler, "word.txt", "word.csv", "word.msg");

        System.out.println();

        OtherOperations.howToGetTextCoordinatesInImageMode(config, "word.doc");

        System.out.println();

        OtherOperations.howToClearAllCacheFiles(imageHandler);
        OtherOperations.howToClearFilesFromCacheOlderThanSpecifiedTimeInterval(imageHandler);

        System.out.println();

        OtherOperations.howToUseCustomFileDataStore(config, "word.doc");

        System.out.println();

        OtherOperations.setDefaultFontName(config);

        System.out.println();

        OtherOperations.showHiddenPagesForVisioFilesInImageRepresentation(imageHandler, "word.vdx");
        OtherOperations.showHiddenPagesForVisioFilesInHtmlRepresentation(htmlHandler, "word.vdx");

        System.out.println();

        OtherOperations.howToUseCustomCacheDataHandler(config, "word.doc");

        System.out.println();

        OtherOperations.getAttachmentOriginalFile(imageHandler, "word.doc");
        OtherOperations.getAttachmentDocumentHtmlRepresentation(config, "document-with-attachments.msg");
        OtherOperations.getAttachmentDocumentImageRepresentation(config, "document-with-attachments.msg");
    }
}

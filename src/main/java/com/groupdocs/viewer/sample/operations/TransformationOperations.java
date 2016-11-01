package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.Transformation;
import com.groupdocs.viewer.domain.Watermark;
import com.groupdocs.viewer.domain.WatermarkPosition;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.domain.options.ReorderPageOptions;
import com.groupdocs.viewer.domain.options.RotatePageOptions;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.handler.ViewerImageHandler;

import java.awt.*;
import java.util.List;

/**
 * The type Transformation operations.
 * @author Aleksey Permyakov (20.10.2016)
 */
public class TransformationOperations {
    /**
     * Rotate 1 st image page by 90 deg.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void rotate1stImagePageBy90Deg(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Set rotation angle 90 for page number 1
        RotatePageOptions rotateOptions = new RotatePageOptions(guid, 1, 90);

        // Perform page rotation
        imageHandler.rotatePage(rotateOptions);
        System.out.println();
    }

    /**
     * Retrieve all image pages including transformation.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void retrieveAllImagePagesIncludingTransformation(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Set image options to include rotate transformations
        ImageOptions imageOptions = new ImageOptions();
        imageOptions.setTransformations(Transformation.Rotate);

        // Get image representation of all document pages, including rotate transformations
        List<PageImage> pages = imageHandler.getPages(guid, imageOptions);
        System.out.println("Pages count: " + pages.size());
        for (PageImage pageImage : pages) {
            System.out.println("\tStream length: " + pageImage.getStream().available());
            System.out.println("\tPage number: " + pageImage.getPageNumber());
        }
        System.out.println();
    }

    /**
     * Retrieve all image pages excluding transformation.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void retrieveAllImagePagesExcludingTransformation(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Set image options NOT to include ANY transformations
        ImageOptions noTransformationsOptions = new ImageOptions();
        noTransformationsOptions.setTransformations(Transformation.None); // This is set by default

        // Get image representation of all document pages, without transformations
        List<PageImage> pagesWithoutTransformations = imageHandler.getPages(guid, noTransformationsOptions);
        System.out.println("Pages count: " + pagesWithoutTransformations.size());
        for (PageImage pageImage : pagesWithoutTransformations) {
            System.out.println("\tStream length: " + pageImage.getStream().available());
            System.out.println("\tPage number: " + pageImage.getPageNumber());
        }

        // Get image representation of all document pages, without transformations
        List<PageImage> pages = imageHandler.getPages(guid);
        System.out.println("Pages count: " + pages.size());
        for (PageImage pageImage : pages) {
            System.out.println("\tStream length: " + pageImage.getStream().available());
            System.out.println("\tPage number: " + pageImage.getPageNumber());
        }
        System.out.println();
    }

    /**
     * Rotate html 1 st page by 90 deg.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void rotateHtml1stPageBy90Deg(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        // Set rotation angle 90 for page number 1
        RotatePageOptions rotateOptions = new RotatePageOptions(guid, 1, 90);

        // Perform page rotation
        htmlHandler.rotatePage(rotateOptions);
        System.out.println();
    }

    /**
     * Retrieve all html pages including transformation.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void retrieveAllHtmlPagesIncludingTransformation(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        // Get html representation of all document pages, including rotate transformations
        List<PageHtml> pages = htmlHandler.getPages(guid, new HtmlOptions());
        System.out.println("Pages count: " + pages.size());
        for (PageHtml pageHtml : pages) {
            System.out.println("\tHtml content: " + pageHtml.getHtmlContent());
            System.out.println("\tPage number: " + pageHtml.getPageNumber());
        }
        System.out.println();
    }

    /**
     * Retrieve all html pages excluding transformation.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void retrieveAllHtmlPagesExcludingTransformation(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        // Set html options NOT to include ANY transformations
        HtmlOptions noTransformationsOptions = new HtmlOptions();
        noTransformationsOptions.setTransformations(Transformation.None); // This is set by default

        // Get html representation of all document pages, without transformations
        List<PageHtml> pagesWithoutTransformations = htmlHandler.getPages(guid, noTransformationsOptions);
        System.out.println(pagesWithoutTransformations.size());

        // Get html representation of all document pages, without transformations
        List<PageHtml> pagesWithoutTransformations2 = htmlHandler.getPages(guid);
        System.out.println(pagesWithoutTransformations2.size());
        System.out.println();
    }

    /**
     * Reorder 1 st and 2 nd image pages.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void reorder1stAnd2ndImagePages(ViewerImageHandler imageHandler, String guid) throws Exception {
        int pageNumber = 1;
        int newPosition = 2;

        // Perform page reorder
        ReorderPageOptions options = new ReorderPageOptions(guid, pageNumber, newPosition);
        imageHandler.reorderPage(options);
        System.out.println();
    }

    /**
     * Reorder html 1 st page by 90 deg.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void reorderHtml1stPageBy90Deg(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        int pageNumber = 1;
        int newPosition = 2;

        // Perform page reorder
        ReorderPageOptions options = new ReorderPageOptions(guid, pageNumber, newPosition);
        htmlHandler.reorderPage(options);
        System.out.println();
    }

    /**
     * Add watermark to image page representation.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void addWatermarkToImagePageRepresentation(ViewerImageHandler imageHandler, String guid) throws Exception {
        ImageOptions options = new ImageOptions();

        // Set watermark properties
        Watermark watermark = new Watermark("This is watermark text");
        watermark.setColor(Color.blue);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);

        options.setWatermark(watermark);

        // Get document pages image representation with watermark
        List<PageImage> pages = imageHandler.getPages(guid, options);
        System.out.println(pages.size());
        System.out.println();
    }

    /**
     * Add watermark with font name to image page representation.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void addWatermarkWithFontNameToImagePageRepresentation(ViewerImageHandler imageHandler, String guid) throws Exception {
        ImageOptions options = new ImageOptions();

        // Set watermark properties
        Watermark watermark = new Watermark("Watermark text");
        watermark.setColor(Color.BLUE);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);
        // Set watermark font name which contains Japanese characters
        watermark.setFontName("MS Gothic");

        options.setWatermark(watermark);

        // Get document pages image representation with watermark
        List<PageImage> pages = imageHandler.getPages(guid, options);
        System.out.println(pages.size());
        System.out.println();
    }

    /**
     * Add watermark to html page representation.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void addWatermarkToHtmlPageRepresentation(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        HtmlOptions options = new HtmlOptions();

        // Set watermark properties
        Watermark watermark = new Watermark("This is watermark text");
        watermark.setColor(Color.blue);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);

        options.setWatermark(watermark);

        // Get document pages html representation with watermark
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        System.out.println(pages.size());
        System.out.println();
    }

    /**
     * Add watermark with font name to html page representation.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void addWatermarkWithFontNameToHtmlPageRepresentation(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        HtmlOptions options = new HtmlOptions();

        // Set watermark properties
        Watermark watermark = new Watermark("This is watermark text");
        watermark.setColor(Color.BLUE);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);
        // Set watermark tag font-family css property
        watermark.setFontName("\"Comic Sans MS\", cursive, sans-serif");

        options.setWatermark(watermark);

        // Get document pages html representation with watermark
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        System.out.println(pages.size());
        System.out.println();
    }

    /**
     * Perform multiple transformations in image mode.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void performMultipleTransformationsInImageMode(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Rotate first page 90 degrees
        imageHandler.rotatePage(new RotatePageOptions(guid, 1, 90));

        // Rotate second page 180 degrees
        imageHandler.rotatePage(new RotatePageOptions(guid, 2, 180));

        // Reorder first and second pages
        imageHandler.reorderPage(new ReorderPageOptions(guid, 1, 2));

        // Set options to include rotate and reorder transformations
        ImageOptions options = new ImageOptions();
        options.setTransformations(Transformation.fromValue(Transformation.Rotate.value() | Transformation.Reorder.value()));

        // Set watermark properties
        Watermark watermark = new Watermark("This is watermark text");
        watermark.setColor(Color.BLUE);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);

        options.setWatermark(watermark);

        // Get document pages image representation with multiple transformations
        List<PageImage> pages = imageHandler.getPages(guid, options);
        System.out.println(pages.size());
        System.out.println();
    }

    /**
     * Perform multiple transformations in html mode.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void performMultipleTransformationsInHtmlMode(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        // Rotate first page 90 degrees
        htmlHandler.rotatePage(new RotatePageOptions(guid, 1, 90));

        // Rotate second page 180 degrees
        htmlHandler.rotatePage(new RotatePageOptions(guid, 2, 180));

        // Reorder first and second pages
        htmlHandler.reorderPage(new ReorderPageOptions(guid, 1, 2));

        // Set options to include rotate and reorder transformations
        HtmlOptions options = new HtmlOptions();
        options.setTransformations(Transformation.fromValue(Transformation.Rotate.value() | Transformation.Reorder.value()));

        // Set watermark properties
        Watermark watermark = new Watermark("This is watermark text");
        watermark.setColor(Color.BLUE);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);

        options.setWatermark(watermark);

        // Get document pages html representation with multiple transformations
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        System.out.println(pages.size());
        System.out.println();
    }
}

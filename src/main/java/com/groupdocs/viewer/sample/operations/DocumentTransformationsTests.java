package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
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
import com.groupdocs.viewer.sample.Utilities;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.List;

import static com.groupdocs.viewer.sample.TestRunner.STORAGE_PATH;
import static com.groupdocs.viewer.sample.TestRunner.applyLicense;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Aleksey Permyakov (13.03.2017).
 */
public class DocumentTransformationsTests {

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

        // Set rotation angle 90 for page number 1
        RotatePageOptions rotateOptions = new RotatePageOptions(1, 90);

        // Perform page rotation
        imageHandler.rotatePage(guid, rotateOptions);
    }

    @Test
    public void testRetrieveAllDocumentPagesIncludingTransformation() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";

        // Set image options to include rotate transformations
        ImageOptions imageOptions = new ImageOptions();
        imageOptions.setTransformations(Transformation.Rotate);

        // Get image representation of all document pages, including rotate transformations
        List<PageImage> pages = imageHandler.getPages(guid, imageOptions);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageImage page : pages) {
            assertTrue("Page content is empty", page.getStream().available() > 0);
        }
    }

    @Test
    public void testRetrieveAllDocumentPagesExcludingTransformation() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";
        // Set image options NOT to include ANY transformations
        ImageOptions noTransformationsOptions = new ImageOptions();
        noTransformationsOptions.setTransformations(Transformation.None); // This is set by default

        // Get image representation of all document pages, without transformations
        List<PageImage> pagesWithoutTransformations = imageHandler.getPages(guid, noTransformationsOptions);
        System.out.println("\tPages count: " + pagesWithoutTransformations.size());
        assertEquals("Page count is incorrect", pagesWithoutTransformations.size(), 2);
        for (PageImage page : pagesWithoutTransformations) {
            assertTrue("Page content is empty", page.getStream().available() > 0);
        }

        // Get image representation of all document pages, without transformations
        List<PageImage> pagesWithoutTransformations2 = imageHandler.getPages(guid);
        System.out.println("\tPages count: " + pagesWithoutTransformations2.size());
        assertEquals("Page count is incorrect", pagesWithoutTransformations2.size(), 2);
        for (PageImage page : pagesWithoutTransformations2) {
            assertTrue("Page content is empty", page.getStream().available() > 0);
        }
    }

    @Test
    public void testRotate1stPageOfTheDocumentBy90Deg2() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.doc";

        // Set rotation angle 90 for page number 1
        RotatePageOptions rotateOptions = new RotatePageOptions(1, 90);

        // Perform page rotation
        htmlHandler.rotatePage(guid, rotateOptions);
    }

    @Test
    public void testRetrieveAllDocumentPagesIncludingTransformation2() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.doc";
        // Set html options to include rotate transformation
        HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.setTransformations(Transformation.Rotate);

        // Get html representation of all document pages, including rotate transformations
        List<PageHtml> pages = htmlHandler.getPages(guid, htmlOptions);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageHtml page : pages) {
            assertTrue("Page content is empty", page.getHtmlContent().length() > 0);
        }
    }

    @Test
    public void testRetrieveAllDocumentPagesExcludingTransformation2() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.doc";
        // Set html options NOT to include ANY transformations
        HtmlOptions noTransformationsOptions = new HtmlOptions();
        noTransformationsOptions.setTransformations(Transformation.None); // This is set by default

        // Get html representation of all document pages, without transformations
        List<PageHtml> pagesWithoutTransformations = htmlHandler.getPages(guid, noTransformationsOptions);
        System.out.println("\tPages count: " + pagesWithoutTransformations.size());
        assertEquals("Page count is incorrect", pagesWithoutTransformations.size(), 2);
        for (PageHtml page : pagesWithoutTransformations) {
            assertTrue("Page content is empty", page.getHtmlContent().length() > 0);
        }

        // Get html representation of all document pages, without transformations
        List<PageHtml> pagesWithoutTransformations2 = htmlHandler.getPages(guid);
        System.out.println("\tPages count: " + pagesWithoutTransformations2.size());
        assertEquals("Page count is incorrect", pagesWithoutTransformations2.size(), 2);
        for (PageHtml page : pagesWithoutTransformations2) {
            assertTrue("Page content is empty", page.getHtmlContent().length() > 0);
        }
    }

    @Test
    public void testReorder1stAnd2ndPages() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";
        int pageNumber = 1;
        int newPosition = 2;
        // Perform page reorder
        ReorderPageOptions options = new ReorderPageOptions(pageNumber, newPosition);
        imageHandler.reorderPage(guid, options);
    }

    @Test
    public void testRetrieveAllDocumentPagesIncludingTransformation3() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";
        // Set image options to include reorder transformations
        ImageOptions imageOptions = new ImageOptions();
        imageOptions.setTransformations(Transformation.Reorder);
        // Get image representation of all document pages, including reorder transformations
        List<PageImage> pages = imageHandler.getPages(guid, imageOptions);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageImage page : pages) {
            assertTrue("Page content is empty", page.getStream().available() > 0);
        }
    }

    @Test
    public void testRetrieveAllDocumentPagesIncludingTransformation4() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";
        // Set image options NOT to include ANY transformations
        ImageOptions noTransformationsOptions = new ImageOptions();
        noTransformationsOptions.setTransformations(Transformation.None); // This is by default
        // Get image representation of all document pages, without transformations
        List<PageImage> pagesWithoutTransformations = imageHandler.getPages(guid, noTransformationsOptions);
        System.out.println("\tPages count: " + pagesWithoutTransformations.size());
        assertEquals("Page count is incorrect", pagesWithoutTransformations.size(), 2);
        for (PageImage page : pagesWithoutTransformations) {
            assertTrue("Page content is empty", page.getStream().available() > 0);
        }

        // Get image representation of all document pages, without transformations
        List<PageImage> pagesWithoutTransformations2 = imageHandler.getPages(guid);
        System.out.println("\tPages count: " + pagesWithoutTransformations2.size());
        assertEquals("Page count is incorrect", pagesWithoutTransformations2.size(), 2);
        for (PageImage page : pagesWithoutTransformations2) {
            assertTrue("Page content is empty", page.getStream().available() > 0);
        }
    }

    @Test
    public void testRetrieveAllDocumentPagesExcludingTransformation3() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";
        // Set image options NOT to include ANY transformations
        ImageOptions noTransformationsOptions = new ImageOptions();
        noTransformationsOptions.setTransformations(Transformation.None); // This is by default

        // Get image representation of all document pages, without transformations
        List<PageImage> pagesWithoutTransformations = imageHandler.getPages(guid, noTransformationsOptions);
        System.out.println("\tPages count: " + pagesWithoutTransformations.size());
        assertEquals("Page count is incorrect", pagesWithoutTransformations.size(), 2);
        for (PageImage page : pagesWithoutTransformations) {
            assertTrue("Page content is empty", page.getStream().available() > 0);
        }

        // Get image representation of all document pages, without transformations
        List<PageImage> pagesWithoutTransformations2 = imageHandler.getPages(guid);
        System.out.println("\tPages count: " + pagesWithoutTransformations2.size());
        assertEquals("Page count is incorrect", pagesWithoutTransformations2.size(), 2);
        for (PageImage page : pagesWithoutTransformations2) {
            assertTrue("Page content is empty", page.getStream().available() > 0);
        }
    }

    @Test
    public void testReorder1stAnd2ndPages2() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.doc";
        int pageNumber = 1;
        int newPosition = 2;

        // Perform page reorder
        ReorderPageOptions options = new ReorderPageOptions(pageNumber, newPosition);
        htmlHandler.reorderPage(guid, options);
    }

    @Test
    public void testRetrieveAllDocumentPagesIncludingTransformation5() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.doc";
        // Set html options to include reorder transformations
        HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.setTransformations(Transformation.Reorder);

        // Get html representation of all document pages, including reorder transformations
        List<PageHtml> pages = htmlHandler.getPages(guid, htmlOptions);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageHtml page : pages) {
            assertTrue("Page content is empty", page.getHtmlContent().length() > 0);
        }
    }

    @Test
    public void testRetrieveAllDocumentPagesExcludingTransformation4() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.doc";
        // Set html options NOT to include ANY transformations
        HtmlOptions noTransformationsOptions = new HtmlOptions();
        noTransformationsOptions.setTransformations(Transformation.None); // This is by default

        // Get html representation of all document pages, without transformations
        List<PageHtml> pagesWithoutTransformations = htmlHandler.getPages(guid, noTransformationsOptions);
        System.out.println("\tPages count: " + pagesWithoutTransformations.size());
        assertEquals("Page count is incorrect", pagesWithoutTransformations.size(), 2);
        for (PageHtml page : pagesWithoutTransformations) {
            assertTrue("Page content is empty", page.getHtmlContent().length() > 0);
        }

        // Get html representation of all document pages, without transformations
        List<PageHtml> pagesWithoutTransformations2 = htmlHandler.getPages(guid);
        System.out.println("\tPages count: " + pagesWithoutTransformations2.size());
        assertEquals("Page count is incorrect", pagesWithoutTransformations2.size(), 2);
        for (PageHtml page : pagesWithoutTransformations2) {
            assertTrue("Page content is empty", page.getHtmlContent().length() > 0);
        }
    }

    @Test
    public void testAddWatermarkToImagePageRepresentation() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";
        ImageOptions options = new ImageOptions();

        // Set watermark properties
        Watermark watermark = new Watermark("This is watermark text");
        watermark.setColor(Color.blue);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);

        options.setWatermark(watermark);

        // Get document pages image representation with watermark
        List<PageImage> pages = imageHandler.getPages(guid, options);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageImage page : pages) {
            assertTrue("Page content is empty", page.getStream().available() > 0);
        }
    }

    @Test
    public void testSpecifyWatermarkFontNameBySettingFontNamePropertyOfWatermark() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String documentName = "document.doc";
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
        List<PageImage> pages = imageHandler.getPages(documentName, options);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageImage page : pages) {
            assertTrue("Page content is empty", page.getStream().available() > 0);
        }
    }

    @Test
    public void testAddWatermarkToDocumentPagesBySettingWatermarkPropertyOfHtmlOptions() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.doc";

        HtmlOptions options = new HtmlOptions();

        // Set watermark properties
        Watermark watermark = new Watermark("This is watermark text");
        watermark.setColor(Color.blue);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);

        options.setWatermark(watermark);

        // Get document pages html representation with watermark
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageHtml page : pages) {
            assertTrue("Page content is empty", page.getHtmlContent().length() > 0);
        }
    }

    @Test
    public void testAddWatermarkToDocumentPagesBySettingFontNameOfWatermarkOfHtmlOptions() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String documentName = "document.doc";
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
        List<PageHtml> pages = htmlHandler.getPages(documentName, options);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageHtml page : pages) {
            assertTrue("Page content is empty", page.getHtmlContent().length() > 0);
        }
    }

    @Test
    public void testPerformMultipleTransformationsInImageMode() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";

        // Rotate first page 90 degrees
        imageHandler.rotatePage(guid, new RotatePageOptions(1, 90));

        // Rotate second page 180 degrees
        imageHandler.rotatePage(guid, new RotatePageOptions(2, 180));

        // Reorder first and second pages
        imageHandler.reorderPage(guid, new ReorderPageOptions(1, 2));

        // Set options to include rotate and reorder transformations
        ImageOptions options = new ImageOptions();
        options.setTransformations(Transformation.from(Transformation.Rotate, Transformation.Reorder));

        // Set watermark properties
        Watermark watermark = new Watermark("This is watermark text");
        watermark.setColor(Color.BLUE);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);

        options.setWatermark(watermark);

        // Get document pages image representation with multiple transformations
        List<PageImage> pages = imageHandler.getPages(guid, options);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageImage page : pages) {
            assertTrue("Page content is empty", page.getStream().available() > 0);
        }
    }

    @Test
    public void testPerformMultipleTransformationsInHtmlMode() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";

        // Rotate first page 90 degrees
        imageHandler.rotatePage(guid, new RotatePageOptions(1, 90));

        // Rotate second page 180 degrees
        imageHandler.rotatePage(guid, new RotatePageOptions(2, 180));

        // Reorder first and second pages
        imageHandler.reorderPage(guid, new ReorderPageOptions(1, 2));

        // Set options to include rotate and reorder transformations
        ImageOptions options = new ImageOptions();
        options.setTransformations(Transformation.from(Transformation.Rotate, Transformation.Reorder));

        // Set watermark properties
        Watermark watermark = new Watermark("This is watermark text");
        watermark.setColor(Color.BLUE);
        watermark.setPosition(WatermarkPosition.Diagonal);
        watermark.setWidth(100f);

        options.setWatermark(watermark);

        // Get document pages image representation with multiple transformations
        List<PageImage> pages = imageHandler.getPages(guid, options);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageImage page : pages) {
            assertTrue("Page content is empty", page.getStream().available() > 0);
        }
    }
}

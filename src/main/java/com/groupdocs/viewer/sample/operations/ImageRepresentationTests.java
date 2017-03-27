package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.ConvertImageFileType;
import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.sample.Utilities;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static com.groupdocs.viewer.sample.TestRunner.STORAGE_PATH;
import static com.groupdocs.viewer.sample.TestRunner.applyLicense;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Aleksey Permyakov (13.03.2017).
 */
public class ImageRepresentationTests {

    @Before
    public void before() {
        applyLicense();
    }

    @Test
    public void testGetImageRepresentationsOfDocumentPages() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";

        List<PageImage> pages = imageHandler.getPages(guid);

        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageImage page : pages)
        {
            System.out.println("\t\tPage number: " + page.getPageNumber());

            // Page image stream
            InputStream imageContent = page.getStream();
            System.out.println("\t\tLength: " + imageContent.available());
            assertTrue("Page content is empty!", imageContent.available() > 0);
        }
    }

    @Test
    public void testGetDocumentImageRepresentationOfNConsecutiveDocumentPages() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";

        // Options to render 5 consecutive pages starting from page number 2
        ImageOptions options = new ImageOptions();
        options.setPageNumber(2);
        options.setCountPagesToRender(5);

        List<PageImage> pages = imageHandler.getPages(guid, options);

        assertEquals("Page count is incorrect", pages.size(), 1);
        for (PageImage page : pages)
        {
            System.out.println("Page number: " + page.getPageNumber());

            // Page image stream
            InputStream imageContent = page.getStream();
            System.out.println("\t\tLength: " + imageContent.available());
            assertTrue("Page content is empty!", imageContent.available() > 0);
        }
    }

    @Test
    public void testGetDocumentImageRepresentationForCustomPageNumbersList() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String guid = "document.doc";

        // Options to convert 1, 3, 5, 6, 8 page numbers
        ImageOptions options = new ImageOptions();
        options.setPageNumbersToRender(Arrays.asList(1, 2/*, 5, 6, 8*/));

        List<PageImage> pages = imageHandler.getPages(guid, options);

        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageImage page : pages)
        {
            System.out.println("Page number: " + page.getPageNumber());

            // Page image stream
            InputStream imageContent = page.getStream();
            System.out.println("\t\tLength: " + imageContent.available());
            assertTrue("Page content is empty!", imageContent.available() > 0);
        }
    }

    @Test
    public void testGetDocumentImageRepresentationWithJpegQualitySetting() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);
        String fileName = "document.doc";
        //Create image options
        ImageOptions imageOptions = new ImageOptions();
        //Override default PNG render image file type to JPG
        imageOptions.setConvertImageFileType(ConvertImageFileType.JPG);
        //Specify jpeg quality, valid values are in 1..100 range, default value is 75
        imageOptions.setJpegQuality(90);

        List<PageImage> pages = imageHandler.getPages(fileName, imageOptions);

        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageImage page : pages) {
            System.out.println("\t\tPage number: " + page.getPageNumber());

            // Page image stream
            InputStream imageContent = page.getStream();
            System.out.println("\t\tLength: " + imageContent.available());
            assertTrue("Page content is empty!", imageContent.available() > 0);
        }
    }

}

package com.groupdocs.viewer.sample;

import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.licensing.License;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Aleksey Permyakov (03.06.2016).
 */
//@Ignore
@RunWith(Theories.class)
public class OpenDocumentAsImageTests extends GenericJUnit {
    @Theory
    public void testSetLicense() {
        new License().setLicense(System.getenv("GROUPDOCS_TOTAL"));
        assertTrue(License.isValidLicense());
    }

    @Theory
    public void testOpenExcelFileAsImage(
            @FromDataPoints("_EXCEL_FILES") String fileName,
            @FromDataPoints("_QUALITY") int quality,
            @FromDataPoints("_TRUE_FALSE") boolean showGridLines,
            @FromDataPoints("_TRUE_FALSE") boolean onePagePerSheet,
            @FromDataPoints("_TRUE_FALSE") boolean showHiddenSheets,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tquality: %d\n\t\tshowGridLines: %s\n\t\tonePagePerSheet: %s\n\t\tshowHiddenSheets: %s\n\t\tuseCache: %s\n\t\tusePdf: %s",
                        fileName, quality, showGridLines, onePagePerSheet, showHiddenSheets, useCache, usePdf
                ));
        // Setup GroupDocs.Viewer config
        ViewerImageHandler imageHandler = createImageHandler(useCache, usePdf);

        // Set image options to show grid lines
        ImageOptions options = createOptions(quality);
        options.getCellsOptions().setShowGridLines(showGridLines);
        options.getCellsOptions().setOnePagePerSheet(onePagePerSheet);
        options.getCellsOptions().setShowHiddenSheets(showHiddenSheets);

        assertPagesAsImages(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenWordFileAsImage(
            @FromDataPoints("_WORD_FILES") String fileName,
            @FromDataPoints("_QUALITY") int quality,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tquality: %d\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, quality, useCache, usePdf));
        ViewerImageHandler imageHandler = createImageHandler(useCache, usePdf);

        // Set image options
        ImageOptions options = createOptions(quality);

        assertPagesAsImages(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenPdfFileAsImage(
            @FromDataPoints("_PDF_FILES") String fileName,
            @FromDataPoints("_QUALITY") int quality,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tquality: %d\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, quality, useCache, usePdf));
        ViewerImageHandler imageHandler = createImageHandler(useCache, usePdf);
        // Set image options
        ImageOptions options = createOptions(quality);

        assertPagesAsImages(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenSlideFileAsImage(
            @FromDataPoints("_SLIDE_FILES") String fileName,
            @FromDataPoints("_QUALITY") int quality,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tquality: %d\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, quality, useCache, usePdf));
        ViewerImageHandler imageHandler = createImageHandler(useCache, usePdf);
        // Set image options
        ImageOptions options = createOptions(quality);

        assertPagesAsImages(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenProjectFileAsImage(
            @FromDataPoints("_PROJECT_FILES") String fileName,
            @FromDataPoints("_QUALITY") int quality,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tquality: %d\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, quality, useCache, usePdf));
        ViewerImageHandler imageHandler = createImageHandler(useCache, usePdf);
        // Set image options
        ImageOptions options = createOptions(quality);

        assertPagesAsImages(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenMailFileAsImage(
            @FromDataPoints("_MAIL_FILES") String fileName,
            @FromDataPoints("_QUALITY") int quality,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tquality: %d\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, quality, useCache, usePdf));
        ViewerImageHandler imageHandler = createImageHandler(useCache, usePdf);
        // Set image options
        ImageOptions options = createOptions(quality);

        assertPagesAsImages(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenTaskFileAsImage(
            @FromDataPoints("_TASK_FILES") String fileName,
            @FromDataPoints("_QUALITY") int quality,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tquality: %d\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, quality, useCache, usePdf));
        ViewerImageHandler imageHandler = createImageHandler(useCache, usePdf);
        // Set image options
        ImageOptions options = createOptions(quality);

        assertPagesAsImages(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenImageFileAsImage(
            @FromDataPoints("_IMAGE_FILES") String fileName,
            @FromDataPoints("_QUALITY") int quality,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tquality: %d\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, quality, useCache, usePdf));
        ViewerImageHandler imageHandler = createImageHandler(useCache, usePdf);
        // Set image options
        ImageOptions options = createOptions(quality);

        assertPagesAsImages(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenPhotoshopFileAsImage(
            @FromDataPoints("_PHOTOSHOP_FILES") String fileName,
            @FromDataPoints("_QUALITY") int quality,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tquality: %d\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, quality, useCache, usePdf));
        ViewerImageHandler imageHandler = createImageHandler(useCache, usePdf);
        // Set image options
        ImageOptions options = createOptions(quality);

        assertPagesAsImages(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenTiffFileAsImage(
            @FromDataPoints("_TIFF_FILES") String fileName,
            @FromDataPoints("_QUALITY") int quality,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tquality: %d\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, quality, useCache, usePdf));
        ViewerImageHandler imageHandler = createImageHandler(useCache, usePdf);
        // Set image options
        ImageOptions options = createOptions(quality);

        assertPagesAsImages(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenDiagramFileAsImage(
            @FromDataPoints("_DIAGRAM_FILES") String fileName,
            @FromDataPoints("_QUALITY") int quality,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tquality: %d\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, quality, useCache, usePdf));
        ViewerImageHandler imageHandler = createImageHandler(useCache, usePdf);
        ImageOptions options = createOptions(quality);


        assertPagesAsImages(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenWrongFileAsImage(
            @FromDataPoints("_WRONG_FILES") String fileName,
            @FromDataPoints("_QUALITY") int quality,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tquality: %d\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, quality, useCache, usePdf));
        ViewerImageHandler imageHandler = createImageHandler(useCache, usePdf);
        // Set image options
        ImageOptions options = createOptions(quality);

        thrown.expect(Exception.class);
        assertPagesAsImages(fileName, imageHandler, options);
    }

    private void assertPagesAsImages(String fileName, ViewerImageHandler imageHandler, ImageOptions options) throws Exception {
        List<PageImage> pages = imageHandler.getPages(fileName, options);
        assertNotNull(pages);

        for (PageImage page : pages) {
            assertTrue(page.getPageNumber() > 0);
            assertNotNull(page.getStream());
            assertTrue(page.getStream().available() > 0);
        }
    }

    private ImageOptions createOptions(int quality) {
        // Set image options
        ImageOptions options = new ImageOptions();
        options.setJpegQuality(quality);
        return options;
    }
}

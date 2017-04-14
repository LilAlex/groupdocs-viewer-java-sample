package com.groupdocs.viewer.sample;

import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
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
public class OpenDocumentAsHtmlTests extends GenericJUnit {
    @Theory
    public void testSetLicense() {
        new License().setLicense(System.getenv("GROUPDOCS_TOTAL"));
//        assertTrue(License.isValidLicense());
    }

    @Theory
    public void testOpenExcelFileAsHtml(
            @FromDataPoints("_EXCEL_FILES") String fileName,
            @FromDataPoints("_TRUE_FALSE") boolean resourcesEmbedded,
            @FromDataPoints("_TRUE_FALSE") boolean showGridLines,
            @FromDataPoints("_TRUE_FALSE") boolean onePagePerSheet,
            @FromDataPoints("_TRUE_FALSE") boolean showHiddenSheets,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tresourcesEmbedded: %s\n\t\tshowGridLines: %s\n\t\tonePagePerSheet: %s\n\t\tshowHiddenSheets: %s\n\t\tuseCache: %s\n\t\tusePdf: %s",
                        fileName, resourcesEmbedded, showGridLines, onePagePerSheet, showHiddenSheets, useCache, usePdf
                ));
        // Setup GroupDocs.Viewer config
        ViewerHtmlHandler imageHandler = createHtmlHandler(useCache, usePdf);

        // Set image options to show grid lines
        HtmlOptions options = createOptions(resourcesEmbedded);
        options.getCellsOptions().setShowGridLines(showGridLines);
        options.getCellsOptions().setOnePagePerSheet(onePagePerSheet);
        options.getCellsOptions().setShowHiddenSheets(showHiddenSheets);

        assertPagesAsHtmls(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenWordFileAsHtml(
            @FromDataPoints("_WORD_FILES") String fileName,
            @FromDataPoints("_TRUE_FALSE") boolean resourcesEmbedded,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tresourcesEmbedded: %s\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, resourcesEmbedded, useCache, usePdf));
        ViewerHtmlHandler imageHandler = createHtmlHandler(useCache, usePdf);
        HtmlOptions options = createOptions(resourcesEmbedded);

        assertPagesAsHtmls(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenPdfFileAsHtml(
            @FromDataPoints("_PDF_FILES") String fileName,
            @FromDataPoints("_TRUE_FALSE") boolean resourcesEmbedded,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tresourcesEmbedded: %s\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, resourcesEmbedded, useCache, usePdf));
        ViewerHtmlHandler imageHandler = createHtmlHandler(useCache, usePdf);
        // Set image options
        HtmlOptions options = createOptions(resourcesEmbedded);

        assertPagesAsHtmls(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenSlideFileAsHtml(
            @FromDataPoints("_SLIDE_FILES") String fileName,
            @FromDataPoints("_TRUE_FALSE") boolean resourcesEmbedded,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tresourcesEmbedded: %s\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, resourcesEmbedded, useCache, usePdf));
        ViewerHtmlHandler imageHandler = createHtmlHandler(useCache, usePdf);
        // Set image options
        HtmlOptions options = createOptions(resourcesEmbedded);

        assertPagesAsHtmls(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenProjectFileAsHtml(
            @FromDataPoints("_PROJECT_FILES") String fileName,
            @FromDataPoints("_TRUE_FALSE") boolean resourcesEmbedded,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tresourcesEmbedded: %s\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, resourcesEmbedded, useCache, usePdf));
        ViewerHtmlHandler imageHandler = createHtmlHandler(useCache, usePdf);
        // Set image options
        HtmlOptions options = createOptions(resourcesEmbedded);

        assertPagesAsHtmls(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenMailFileAsHtml(
            @FromDataPoints("_MAIL_FILES") String fileName,
            @FromDataPoints("_TRUE_FALSE") boolean resourcesEmbedded,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tresourcesEmbedded: %s\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, resourcesEmbedded, useCache, usePdf));
        ViewerHtmlHandler imageHandler = createHtmlHandler(useCache, usePdf);
        // Set image options
        HtmlOptions options = createOptions(resourcesEmbedded);

        assertPagesAsHtmls(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenTaskFileAsHtml(
            @FromDataPoints("_TASK_FILES") String fileName,
            @FromDataPoints("_TRUE_FALSE") boolean resourcesEmbedded,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tresourcesEmbedded: %s\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, resourcesEmbedded, useCache, usePdf));
        ViewerHtmlHandler imageHandler = createHtmlHandler(useCache, usePdf);
        // Set image options
        HtmlOptions options = createOptions(resourcesEmbedded);

        assertPagesAsHtmls(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenHtmlFileAsHtml(
            @FromDataPoints("_IMAGE_FILES") String fileName,
            @FromDataPoints("_TRUE_FALSE") boolean resourcesEmbedded,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tresourcesEmbedded: %s\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, resourcesEmbedded, useCache, usePdf));
        ViewerHtmlHandler imageHandler = createHtmlHandler(useCache, usePdf);
        // Set image options
        HtmlOptions options = createOptions(resourcesEmbedded);

        assertPagesAsHtmls(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenPhotoshopFileAsHtml(
            @FromDataPoints("_PHOTOSHOP_FILES") String fileName,
            @FromDataPoints("_TRUE_FALSE") boolean resourcesEmbedded,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tresourcesEmbedded: %s\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, resourcesEmbedded, useCache, usePdf));
        ViewerHtmlHandler imageHandler = createHtmlHandler(useCache, usePdf);
        // Set image options
        HtmlOptions options = createOptions(resourcesEmbedded);

        assertPagesAsHtmls(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenTiffFileAsHtml(
            @FromDataPoints("_TIFF_FILES") String fileName,
            @FromDataPoints("_TRUE_FALSE") boolean resourcesEmbedded,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tresourcesEmbedded: %s\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, resourcesEmbedded, useCache, usePdf));
        ViewerHtmlHandler imageHandler = createHtmlHandler(useCache, usePdf);
        // Set image options
        HtmlOptions options = createOptions(resourcesEmbedded);

        assertPagesAsHtmls(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenDiagramFileAsHtml(
            @FromDataPoints("_DIAGRAM_FILES") String fileName,
            @FromDataPoints("_TRUE_FALSE") boolean resourcesEmbedded,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tresourcesEmbedded: %s\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, resourcesEmbedded, useCache, usePdf));
        ViewerHtmlHandler imageHandler = createHtmlHandler(useCache, usePdf);
        // Set image options
        HtmlOptions options = createOptions(resourcesEmbedded);

        assertPagesAsHtmls(fileName, imageHandler, options);
    }

    @Theory
    public void testOpenWrongFileAsHtml(
            @FromDataPoints("_WRONG_FILES") String fileName,
            @FromDataPoints("_TRUE_FALSE") boolean resourcesEmbedded,
            @FromDataPoints("_TRUE_FALSE") boolean useCache,
            @FromDataPoints("_TRUE_FALSE") boolean usePdf) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s\n\t\tresourcesEmbedded: %s\n\t\tuseCache: %s\n\t\tusePdf: %s", fileName, resourcesEmbedded, useCache, usePdf));
        ViewerHtmlHandler imageHandler = createHtmlHandler(useCache, usePdf);
        // Set image options
        HtmlOptions options = createOptions(resourcesEmbedded);

        thrown.expect(Exception.class);
        assertPagesAsHtmls(fileName, imageHandler, options);
    }

    private void assertPagesAsHtmls(String fileName, ViewerHtmlHandler imageHandler, HtmlOptions options) throws Exception {
        List<PageHtml> pages = imageHandler.getPages(fileName, options);
        assertNotNull(pages);

        for (PageHtml page : pages) {
            assertTrue(page.getPageNumber() > 0);
            assertNotNull(page.getHtmlContent());
            assertTrue(page.getHtmlContent().length() > 0);
        }
    }

    private HtmlOptions createOptions(boolean resourcesEmbedded) {
        // Set image options
        HtmlOptions options = new HtmlOptions();
        options.setResourcesEmbedded(resourcesEmbedded);
        return options;
    }
}

package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.domain.html.HtmlResource;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.sample.Utilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static com.groupdocs.viewer.sample.TestRunner.STORAGE_PATH;
import static com.groupdocs.viewer.sample.Utilities.initOutput;
import static org.junit.Assert.*;

/**
 * @author Aleksey Permyakov (13.03.2017).
 */
public class HtmlRepresentationTests {

    @Before
    public void before() {
        Utilities.applyLicense();
        initOutput();
    }

    @After
    public void after() throws IOException {
        Utilities.cleanOutput();
    }

    @Test
    public void testGetDocumentHtmlRepresentationWithEmbeddedResources() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.doc";

        HtmlOptions options = new HtmlOptions();
        options.setEmbedResources(true);
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageHtml page : pages) {
            System.out.println("\t\tPage number: " + page.getPageNumber());
            System.out.println("\t\tHtml content: " + page.getHtmlContent());
            assertNotNull("Html content is empty", page.getHtmlContent());
        }
    }

    @Test
    public void testGetDocumentHtmlRepresentation() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.doc";

        List<PageHtml> pages = htmlHandler.getPages(guid);

        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageHtml page : pages) {
            System.out.println("\t\tPage number: " + page.getPageNumber());
            System.out.println("\t\tResources count: " + page.getHtmlResources().size());
            System.out.println("\t\tHtml content: " + page.getHtmlContent());
            assertNotNull("Html content is empty", page.getHtmlContent());

            // Html resources descriptions
            for (HtmlResource resource : page.getHtmlResources()) {
                System.out.println(resource.getResourceName() + " " + resource.getResourceType());

                // Get html page resource stream
                InputStream resourceStream = htmlHandler.getResource(guid, resource);
                System.out.println("\t\t\tLength: " + resourceStream.available());
                assertTrue("Resource content is empty", resourceStream.available() > 0);
            }
        }
    }

    @Test
    public void testGetDocumentHtmlRepresentationOfNConsecutiveDocumentPages() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.doc";

        // Options to render 5 consecutive pages starting from page number 2
        HtmlOptions options = new HtmlOptions();
        options.setPageNumber(2);
        options.setCountPagesToRender(5);
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        assertEquals("Page count is incorrect", pages.size(), 1);
        for (PageHtml page : pages) {
            System.out.println("\t\tPage number: " + page.getPageNumber());
            System.out.println("\t\tHtml content: " + page.getHtmlContent());
            assertNotNull("Html content is empty", page.getHtmlContent());
        }
    }

    @Test
    public void testGetDocumentHtmlRepresentationForCustomPageNumbersList() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.doc";

        // Options to render 1, 3, 5, 6, 8 page numbers
        HtmlOptions options = new HtmlOptions();
        options.setPageNumbersToRender(Arrays.asList(1, 2/*, 5, 6, 8*/));

        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageHtml page : pages) {
            System.out.println("\t\tPage number: " + page.getPageNumber());
            System.out.println("\t\tHtml content: " + page.getHtmlContent());
            assertNotNull("Html content is empty", page.getHtmlContent());
        }
    }

    @Test
    public void testHowToSpecifyInternalHyperlinkPrefixForExcelFilesWithPageName() throws Exception {
        Utilities.showTestHeader();
        HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.getCellsOptions().setInternalHyperlinkPrefix("http://contoso.com/api/getPage?name=");
        assertNotNull(htmlOptions.getCellsOptions().getInternalHyperlinkPrefix());
    }

    @Test
    public void testHowToSpecifyInternalHyperlinkPrefixForExcelFilesWithPageNumber() throws Exception {
        Utilities.showTestHeader();
        HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.getCellsOptions().setInternalHyperlinkPrefix("http://contoso.com/api/getPage?number={page-number}");
        assertNotNull(htmlOptions.getCellsOptions().getInternalHyperlinkPrefix());
    }

    @Test
    public void testHowToSpecifyResourcePrefix() throws Exception {
        Utilities.showTestHeader();
        HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.setHtmlResourcePrefix("http://contoso.com/api/getResource?name=");
        assertNotNull(htmlOptions.getHtmlResourcePrefix());
    }

    @Test
    public void testHowToSpecifyResourcePrefixWithIgnoreResourcePrefixForCss() throws Exception {
        Utilities.showTestHeader();
        HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.setHtmlResourcePrefix("http://contoso.com/api/getResource?name=");
        htmlOptions.setIgnorePrefixInResources(true);
        assertNotNull(htmlOptions.getHtmlResourcePrefix());
        assertTrue(htmlOptions.getIgnorePrefixInResources());
    }

    @Test
    public void testHowToSpecifyResourcePrefixWithResourcesEmbeddedAndHtmlResourcePrefix() throws Exception {
        Utilities.showTestHeader();
        HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.setEmbedResources(false);
        htmlOptions.setHtmlResourcePrefix("http://example.com/api/pages/{page-number}/resources/{resource-name}");
        //The {page-number} and {resource-name} patterns will be replaced with current processing page number and resource name accordingly.
        assertNotNull(htmlOptions.getHtmlResourcePrefix());
        assertFalse(htmlOptions.getEmbedResources());
    }
}

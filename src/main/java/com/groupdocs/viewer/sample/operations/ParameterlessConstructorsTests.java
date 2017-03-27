package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.sample.Utilities;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.groupdocs.viewer.sample.TestRunner.STORAGE_PATH;
import static com.groupdocs.viewer.sample.TestRunner.applyLicense;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author liosha (15.03.2017)
 */
public class ParameterlessConstructorsTests {

    @Before
    public void before() {
        applyLicense();
    }

    @Test
    public void testGetAttachmentDocumentHtmlRepresentation() throws Exception {
        Utilities.showTestHeader();
        ViewerConfig config = new ViewerConfig();
        ViewerHtmlHandler handler = new ViewerHtmlHandler(config);
        List<PageHtml> pages = handler.getPages("document.doc");
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageHtml page : pages) {
            assertTrue("Page content is empty", page.getHtmlContent().length() > 0);
        }
    }

    @Test
    public void testRenderingDocumentFromFolderAndGettingResourceFilesSeparateOfHtml() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        config.setUseCache(true);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.doc";

        HtmlOptions options = new HtmlOptions();
        options.setResourcesEmbedded(false);
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageHtml page : pages) {
            assertTrue("Page content is empty", page.getHtmlContent().length() > 0);
        }
    }

    @Test
    public void testRenderingDocumentUsingParameterlessConstructorAndConfigurations() throws Exception {
        Utilities.showTestHeader();
        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler();
        String guid = "document.doc";

        HtmlOptions options = new HtmlOptions();
        options.setResourcesEmbedded(false);
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageHtml page : pages) {
            assertTrue("Page content is empty", page.getHtmlContent().length() > 0);
        }
    }

}

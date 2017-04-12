package com.groupdocs.viewer.sample.tasks;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.domain.containers.DocumentInfoContainer;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.sample.Utilities;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.groupdocs.viewer.sample.TestRunner.STORAGE_PATH;
import static com.groupdocs.viewer.sample.TestRunner.applyLicense;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Aleksey Permyakov
 */
public class CommonIssuesTests {

    @Before
    public void before() {
        applyLicense();
    }

    @Test
    public void testVIEWERJAVA1325() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        int pageNumber = 1, pagesCount = 1;
        final HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.setCountPagesToRender(pagesCount);
        htmlOptions.setPageNumber(pageNumber);
        htmlOptions.setPageNumbersToRender(Arrays.asList(pageNumber));
        // Create html handler
        final ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        final String guids[] = new String[]{"candy.pdf", "candy.pdf", "candy.pdf"};
        final CountDownLatch latch = new CountDownLatch(guids.length);
        for (final String guid : guids) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String prefix = "Thread-" + this.hashCode() + ": ";
//                    System.out.println();
//                    System.out.println(prefix + "Document: " + guid);
                    try {
                        final List<PageHtml> pages;
                        pages = htmlHandler.getPages(guid, htmlOptions);
                        for (PageHtml page : pages) {
                            assertEquals("Page count incorrect", 1, page.getPageNumber());
                            assertNotNull("Html content of resource is null", page.getHtmlContent());
//                            System.out.println(prefix + "Page number: " + page.getPageNumber());
//                            System.out.println(prefix + "Html content: " + page.getHtmlContent());
                        }
                    } catch (Throwable e) {
                        System.err.println(prefix + "Uncaught exception - " + e.getMessage());
                        e.printStackTrace(System.err);
                    }
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
    }

    @Test
    public void testVIEWERJAVA1358() throws Exception {
        String guid = "input.xlsx";

        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        config.setUseCache(true);

        ViewerHtmlHandler handler = new ViewerHtmlHandler(config);

        DocumentInfoContainer container = handler.getDocumentInfo(guid);

        System.out.println("name: " + container.getName());
        System.out.println("pages: " + container.getPages().size());
    }

    @Test
    public void testVIEWERJAVAxxx() {
//        // setup Viewer configuration
//        ViewerConfig signConfig = new ViewerConfig();
//        signConfig.setStoragePath(STORAGE_PATH);
//        signConfig.setOutputPath(OUTPUT_PATH);
//        signConfig.setImagesPath(IMAGES_PATH);
//        // instantiating the conversion handler
//        ViewerHandler<String> handler = new ViewerHandler<String>(signConfig);
//
//        // setup image viewer options
//        PdfSignTextOptions signOptions = new PdfSignTextOptions("John Smith");
//        signOptions.setLeft(100);
//        signOptions.setTop(100);
//        final SaveOptions saveOptions = new SaveOptions();
//        saveOptions.setOutputType(OutputType.String);
//        saveOptions.setOutputFileName("testVIEWERJAVA102.pdf");
//        // sign document
//        String signedPath = handler.<String>sign(getStoragePath("digital viewers.pdf"), signOptions, saveOptions);
//        System.out.println("Signed file path is: " + signedPath);
    }
}

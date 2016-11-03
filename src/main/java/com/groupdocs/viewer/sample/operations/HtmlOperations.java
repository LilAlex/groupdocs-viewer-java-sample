package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.domain.html.HtmlResource;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * The type Html operations.
 * @author Aleksey Permyakov (24.10.2016)
 */
public class HtmlOperations {
    /**
     * Gets html representation with embedded resources.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getHtmlRepresentationWithEmbeddedResources(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        HtmlOptions options = new HtmlOptions();
        options.setResourcesEmbedded(true);
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        System.out.println("Page count: " + pages.size());

        for (PageHtml page : pages) {
            System.out.println("\tPage number: " + page.getPageNumber());
            System.out.println("\tHtml content: " + page.getHtmlContent().substring(0, 150) + "...");
        }
        System.out.println();
    }

    /**
     * Gets html representation.
     * @param config
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getHtmlRepresentation(ViewerConfig config, String guid) throws Exception {
        config.setUseCache(true);
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        List<PageHtml> pages = htmlHandler.getPages(guid);
        System.out.println("Page count: " + pages.size());

        for (PageHtml page : pages) {
            System.out.println("\tPage number: " + page.getPageNumber());
            System.out.println("\tResources count: " + page.getHtmlResources().size());
            System.out.println("\tHtml content: " + page.getHtmlContent().substring(0, 150) + "...");

            // Html resources descriptions
            for (HtmlResource resource : page.getHtmlResources()) {
                System.out.println(resource.getResourceName() + resource.getResourceType());

                // Get html page resource stream
                InputStream resourceStream = htmlHandler.getResource(guid, resource);
                System.out.println("\t\tStream size: " + resourceStream.available());
            }
        }
        System.out.println();
    }

    /**
     * Gets html representation of n consecutive pages.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getHtmlRepresentationOfNConsecutivePages(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        // Options to convert 5 consecutive pages starting from page number 2
        HtmlOptions options = new HtmlOptions();
        options.setPageNumber(2);
        options.setCountPagesToConvert(5);
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        System.out.println("Page count: " + pages.size());

        for (PageHtml page : pages) {
            System.out.println("\tPage number: " + page.getPageNumber());
            System.out.println("\tHtml content: " + page.getHtmlContent().substring(0, 150) + "...");
        }
        System.out.println();
    }

    /**
     * Gets html representation of custom page numbers.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getHtmlRepresentationOfCustomPageNumbers(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        // Options to convert 1, 3, 5, 6, 8 page numbers
        HtmlOptions options = new HtmlOptions();
        options.setPageNumbersToConvert(Arrays.asList(1, 3, 5, 6, 8));

        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        System.out.println("Page count: " + pages.size());

        for (PageHtml page : pages) {
            System.out.println("\tPage number: " + page.getPageNumber());
            System.out.println("\tHtml content: " + page.getHtmlContent().substring(0, 150) + "...");
        }
        System.out.println();
    }

    /**
     * Specify internal hyperlink prefix for excel files.
     */
    public static void specifyInternalHyperlinkPrefixForExcelFiles() {
        // Without page number
        HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.getCellsOptions().setInternalHyperlinkPrefix("http://contoso.com/api/getPage?name=");
        // With page number
        HtmlOptions htmlOptionsPN = new HtmlOptions();
        htmlOptionsPN.getCellsOptions().setInternalHyperlinkPrefix("http://contoso.com/api/getPage?number={page-number}");
        System.out.println();
    }

    /**
     * Specify resource prefix.
     */
    public static void specifyResourcePrefix() {
        // With processing css files
        HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.setHtmlResourcePrefix("http://contoso.com/api/getResource?name=");
        // Without processing css files
        HtmlOptions htmlOptionsCP = new HtmlOptions();
        htmlOptionsCP.setHtmlResourcePrefix("http://contoso.com/api/getResource?name=");
        htmlOptionsCP.setIgnoreResourcePrefixForCss(true);
        System.out.println();
    }
}

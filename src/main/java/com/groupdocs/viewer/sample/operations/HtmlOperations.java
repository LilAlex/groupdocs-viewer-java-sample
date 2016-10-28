package com.groupdocs.viewer.sample.operations;

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

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            System.out.println("Html content: " + page.getHtmlContent());
        }
        System.out.println();
    }

    /**
     * Gets html representation.
     * @param htmlHandler the html handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getHtmlRepresentation(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        List<PageHtml> pages = htmlHandler.getPages(guid);

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            System.out.println("Resources count: " + page.getHtmlResources().size());
            System.out.println("Html content: " + page.getHtmlContent());

            // Html resources descriptions
            for (HtmlResource resource : page.getHtmlResources()) {
                System.out.println(resource.getResourceName() + resource.getResourceType());

                // Get html page resource stream
                InputStream resourceStream = htmlHandler.getResource(guid, resource);
                System.out.println("Stream size: " + resourceStream.available());
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

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            System.out.println("Html content: " + page.getHtmlContent());
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

        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            System.out.println("Html content: " + page.getHtmlContent());
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

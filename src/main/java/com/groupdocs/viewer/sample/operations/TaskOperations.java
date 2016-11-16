package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.domain.html.HtmlResource;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.sample.Utilities;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Aleksey Permyakov
 */
public class TaskOperations {
    public static void VIEWERJAVA853(ViewerHtmlHandler htmlHandler, String guid) throws Exception {
        HtmlOptions htmlOptions = new HtmlOptions();
//        htmlOptions.getCellsOptions().setInternalHyperlinkPrefix("http://contoso.com/api/getPage?name=");
        htmlOptions.getCellsOptions().setInternalHyperlinkPrefix("http://contoso.com/api/getPage?number={page-number}");

        List<PageHtml> pages = htmlHandler.getPages(guid, htmlOptions);
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

    public static void VIEWERJAVA924(ViewerConfig config, String guid) throws Exception {
        config.setUseCache(false);
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
        final boolean isCacheExists = new File(Utilities.STORAGE_PATH + File.separator + "temp").exists();
        if (config.getUseCache() && isCacheExists) {
            throw new Exception("use cache is false but directory was created");
        }
        System.out.println("Cache directory exists: " + isCacheExists);
        System.out.println();
    }
}

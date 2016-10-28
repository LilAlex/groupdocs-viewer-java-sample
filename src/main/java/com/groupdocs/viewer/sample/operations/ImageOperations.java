package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.converter.options.ConvertImageFileType;
import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.handler.ViewerImageHandler;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * The type Image operations.
 * @author Aleksey Permyakov (22.10.2016)
 */
public class ImageOperations {
    /**
     * Gets image representation.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getImageRepresentation(ViewerImageHandler imageHandler, String guid) throws Exception {
        List<PageImage> pages = imageHandler.getPages(guid);

        for (PageImage page : pages) {
            System.out.println("Page number: " + page.getPageNumber());

            // Page image stream
            InputStream imageContent = page.getStream();
            System.out.println(imageContent.available());
        }
        System.out.println();
    }

    /**
     * Gets image representation of n consecutive pages.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getImageRepresentationOfNConsecutivePages(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Options to convert 5 consecutive pages starting from page number 2
        ImageOptions options = new ImageOptions();
        options.setPageNumber(2);
        options.setCountPagesToConvert(5);

        List<PageImage> pages = imageHandler.getPages(guid, options);

        for (PageImage page : pages) {
            System.out.println("Page number: " + page.getPageNumber());

            // Page image stream
            InputStream imageContent = page.getStream();
            System.out.println(imageContent.available());
        }
        System.out.println();
    }

    /**
     * Gets image representation of custom page numbers.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getImageRepresentationOfCustomPageNumbers(ViewerImageHandler imageHandler, String guid) throws Exception {
        // Options to convert 1, 3, 5, 6, 8 page numbers
        ImageOptions options = new ImageOptions();
        options.setPageNumbersToConvert(Arrays.asList(1, 3, 5, 6, 8));

        List<PageImage> pages = imageHandler.getPages(guid, options);

        for (PageImage page : pages) {
            System.out.println("Page number: " + page.getPageNumber());

            // Page image stream
            InputStream imageContent = page.getStream();
            System.out.println(imageContent.available());
        }
        System.out.println();
    }

    /**
     * Gets image representation with jpeg quality.
     * @param imageHandler the image handler
     * @param guid the guid
     * @throws Exception the exception
     */
    public static void getImageRepresentationWithJpegQuality(ViewerImageHandler imageHandler, String guid) throws Exception {
        //Create image options
        ImageOptions imageOptions = new ImageOptions();
        //Override default PNG convert image file type to JPG
        imageOptions.setConvertImageFileType(ConvertImageFileType.JPG);
        //Specify jpeg quality, valid values are in 1..100 range, default value is 75
        imageOptions.setJpegQuality(90);

        List<PageImage> pages = imageHandler.getPages(guid, imageOptions);

        for (PageImage page : pages) {
            System.out.println("Page number: " + page.getPageNumber());

            // Page image stream
            InputStream imageContent = page.getStream();
            System.out.println(imageContent.available());
        }
        System.out.println();
    }
}

package com.groupdocs.viewer.sample;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.FileDescription;
import com.groupdocs.viewer.domain.Transformation;
import com.groupdocs.viewer.domain.WatermarkPosition;
import com.groupdocs.viewer.domain.containers.FileContainer;
import com.groupdocs.viewer.domain.containers.FileTreeContainer;
import com.groupdocs.viewer.domain.html.HtmlResource;
import com.groupdocs.viewer.domain.html.HtmlResourceType;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.domain.options.FileTreeOptions;
import com.groupdocs.viewer.domain.options.PdfFileOptions;
import com.groupdocs.viewer.handler.ViewerHandler;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.handler.ViewerImageHandler;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * The type View generator.
 * @author Aleksey Permyakov (21.03.2016).
 */
public class ViewGenerator {

    /**
     * Render document as html.
     * @param DocumentName the document name
     * @throws Exception the exception
     */
    public static void renderDocumentAsHtml(String DocumentName) throws Exception {
        renderDocumentAsHtml(DocumentName, null);
    }

    /**
     * Render simple document in html representation
     * @param DocumentName File name
     * @param DocumentPassword Optional
     * @throws Exception the exception
     */
    public static void renderDocumentAsHtml(String DocumentName, String DocumentPassword) throws Exception {
        //ExStart:RenderAsHtml
        //Get Configurations
        ViewerConfig config = Utilities.getConfiguration();

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);


        // Guid implies that unique document name 
        String guid = DocumentName;

        //Instantiate the HtmlOptions object
        HtmlOptions options = new HtmlOptions();

        //to get html representations of pages with embedded resources
        options.setResourcesEmbedded(true);

        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }
        options.setPageNumbersToConvert(Arrays.asList(1, 2));
        //Get document pages in html form
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            //Save each page at disk
            Utilities.saveAsHtml(page.getPageNumber() + "_" + DocumentName, page.getHtmlContent());
        }
    }


    /**
     * Render document in html representation with watermark
     * @param DocumentName file/document name
     * @param WatermarkText watermark text
     * @param WatermarkColor System.Drawing.Color
     * @param position Watermark Position is optional parameter. Default value is WatermarkPosition.Diagonal
     * @param WatermarkWidth width of watermark as integer. it is optional Parameter default value is 100
     * @param DocumentPassword Password Parameter is optional
     * @throws Exception the exception
     */
    public static void renderDocumentAsHtml(String DocumentName, String WatermarkText, Color WatermarkColor, WatermarkPosition position, int WatermarkWidth, String DocumentPassword) throws Exception {
        position = position == null ? WatermarkPosition.Diagonal : position;
        //ExStart:RenderAsHtmlWithWaterMark
        //Get Configurations
        ViewerConfig config = Utilities.getConfiguration();

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);

        // Guid implies that unique document name 
        String guid = DocumentName;

        //Instantiate the HtmlOptions object 
        HtmlOptions options = new HtmlOptions();

        options.setResourcesEmbedded(true);
        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        // Call AddWatermark and pass the reference of HtmlOptions object as 1st parameter
        Utilities.PageTransformations.AddWatermark(options, WatermarkText, WatermarkColor, position, WatermarkWidth);

        //Get document pages in html form
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            //Save each page at disk
            Utilities.saveAsHtml(page.getPageNumber() + "_" + DocumentName, page.getHtmlContent());
            processHtmlResources(guid, htmlHandler, page);
        }
    }

    /**
     * document in html representation and reorder a page
     * @param DocumentName file/document name
     * @param CurrentPageNumber Page existing order number
     * @param NewPageNumber Page new order number
     * @param DocumentPassword Password Parameter is optional
     * @throws Exception the exception
     */
    public static void renderDocumentAsHtml(String DocumentName, int CurrentPageNumber, int NewPageNumber, String DocumentPassword) throws Exception {
        //ExStart:RenderAsHtmlAndReorderPage
        //Get Configurations
        ViewerConfig config = Utilities.getConfiguration();

        // Cast ViewerHtmlHandler class object to its base class(ViewerHandler).
        ViewerHandler handler = new ViewerHtmlHandler(config);

        // Guid implies that unique document name 
        String guid = DocumentName;

        //Instantiate the HtmlOptions object with setting of Reorder Transformation
        HtmlOptions options = new HtmlOptions();
        options.setTransformations(Transformation.Reorder);

        //to get html representations of pages with embedded resources
        options.setResourcesEmbedded(true);

        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        //Call ReorderPage and pass the reference of ViewerHandler's class  parameter by reference. 
        Utilities.PageTransformations.ReorderPage(handler, guid, CurrentPageNumber, NewPageNumber);

        //down cast the handler(ViewerHandler) to viewerHtmlHandler
        ViewerHtmlHandler htmlHandler = (ViewerHtmlHandler) handler;

        //Get document pages in html form
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages) {
            //Save each page at disk
            Utilities.saveAsHtml(page.getPageNumber() + "_" + DocumentName, page.getHtmlContent());
            processHtmlResources(guid, htmlHandler, page);
        }
    }

    private static void processHtmlResources(String guid, ViewerHtmlHandler htmlHandler, PageHtml page) {
        final List<HtmlResource> htmlResources = page.getHtmlResources();
        for (HtmlResource htmlResource : htmlResources) {
            final String resourceName = htmlResource.getResourceName();
            try {
                final InputStream resource = htmlHandler.getResource(guid, htmlResource);
                final HtmlResourceType resourceType = htmlResource.getResourceType();
                final int documentPageNumber = htmlResource.getDocumentPageNumber();
                switch (resourceType) {
                    case Image:
                        Utilities.saveResourceAsImage(resourceName, documentPageNumber, resource);
                        break;
                    case Style:
                        Utilities.saveResourceFile(resourceName, documentPageNumber, resource);
                        break;
                    case Font:
                        Utilities.saveResourceFile(resourceName, documentPageNumber, resource);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Render a document in html representation whom located at web/remote location.
     * @param DocumentURL URL of the document
     * @param DocumentPassword Password Parameter is optional
     * @throws Exception the exception
     */
    public static void renderDocumentAsHtml(URI DocumentURL, String DocumentPassword) throws Exception {
        //ExStart:RenderRemoteDocAsHtml
        //Get Configurations 
        ViewerConfig config = Utilities.getConfiguration();

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);

        //Instantiate the HtmlOptions object
        HtmlOptions options = new HtmlOptions();

        if (DocumentPassword == null || DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        //Get document pages in html form
        List<PageHtml> pages = htmlHandler.getPages(DocumentURL, options);

        for (PageHtml page : pages) {
            //Save each page at disk
            Utilities.saveAsHtml(page.getPageNumber() + "_" + new File(DocumentURL.getPath()).getName(), page.getHtmlContent());
        }
    }

    /**
     * Render simple document in image representation
     * @param DocumentName File name
     * @param DocumentPassword Optional
     * @throws Exception the exception
     */
    public static void renderDocumentAsImages(String DocumentName, String DocumentPassword) throws Exception {
        //ExStart:RenderAsImage
        //Get Configurations
        ViewerConfig config = Utilities.getConfiguration();

        // Create image handler 
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        // Guid implies that unique document name 
        String guid = DocumentName;

        //Initialize ImageOptions Object
        ImageOptions options = new ImageOptions();

        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        //Get document pages in image form
        List<PageImage> Images = imageHandler.getPages(guid, options);

        for (PageImage image : Images) {
            //Save each image at disk
            Utilities.saveAsImage(image.getPageNumber() + "_" + DocumentName, image.getStream());
        }
    }

    /**
     * Render document in image representation with watermark
     * @param DocumentName file/document name
     * @param WatermarkText watermark text
     * @param WatermarkColor System.Drawing.Color
     * @param position Watermark Position is optional parameter. Default value is WatermarkPosition.Diagonal
     * @param WatermarkWidth width of watermark as integer. it is optional Parameter default value is 100
     * @param DocumentPassword Password Parameter is optional
     * @throws Exception the exception
     */
    public static void renderDocumentAsImages(String DocumentName, String WatermarkText, Color WatermarkColor, WatermarkPosition position, int WatermarkWidth, String DocumentPassword) throws Exception {
        position = position == null ? WatermarkPosition.Diagonal : position;
        //ExStart:RenderAsImageWithWaterMark
        //Get Configurations
        ViewerConfig config = Utilities.getConfiguration();

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        // Guid implies that unique document name
        String guid = DocumentName;

        //Initialize ImageOptions Object
        ImageOptions options = new ImageOptions();

        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        // Call AddWatermark and pass the reference of ImageOptions object as 1st parameter
        Utilities.PageTransformations.AddWatermark(options, WatermarkText, WatermarkColor, position, WatermarkWidth);

        //Get document pages in image form
        List<PageImage> Images = imageHandler.getPages(guid, options);

        for (PageImage image : Images) {
            //Save each image at disk
            Utilities.saveAsImage(image.getPageNumber() + "_" + DocumentName, image.getStream());
        }
    }

    /**
     * Render the document in image form and set the rotation angle to rotate the page while display.
     * @param DocumentName the document name
     * @param RotationAngle rotation angle in digits
     * @param DocumentPassword the document password
     * @throws Exception the exception
     */
    public static void renderDocumentAsImages(String DocumentName, int RotationAngle, String DocumentPassword) throws Exception {
        //ExStart:RenderAsImageWithRotationTransformation
        //Get Configurations
        ViewerConfig config = Utilities.getConfiguration();

        // Create image handler
        ViewerHandler handler = new ViewerImageHandler(config);

        // Guid implies that unique document name 
        String guid = DocumentName;

        //Initialize ImageOptions Object and setting Rotate Transformation
        ImageOptions options = new ImageOptions();
        options.setTransformations(Transformation.Rotate);

        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        //Call RotatePages to apply rotate transformation to a page
        Utilities.PageTransformations.RotatePages(handler, guid, 1, RotationAngle);

        //down cast the handler(ViewerHandler) to viewerHtmlHandler
        ViewerImageHandler imageHandler = (ViewerImageHandler) handler;

        //Get document pages in image form
        List<PageImage> Images = imageHandler.getPages(guid, options);

        for (PageImage image : Images) {
            //Save each image at disk
            Utilities.saveAsImage(image.getPageNumber() + "_" + DocumentName, image.getStream());
        }
    }

    /**
     * document in image representation and reorder a page
     * @param DocumentName file/document name
     * @param CurrentPageNumber Page existing order number
     * @param NewPageNumber Page new order number
     * @param DocumentPassword Password Parameter is optional
     * @throws Exception the exception
     */
    public static void renderDocumentAsImages(String DocumentName, int CurrentPageNumber, int NewPageNumber, String DocumentPassword) throws Exception {
        //ExStart:RenderAsImageAndReorderPage
        //Get Configurations
        ViewerConfig config = Utilities.getConfiguration();

        // Cast ViewerHtmlHandler class object to its base class(ViewerHandler).
        ViewerHandler handler = new ViewerImageHandler(config);

        // Guid implies that unique document name 
        String guid = DocumentName;

        //Initialize ImageOptions Object and setting Reorder Transformation
        ImageOptions options = new ImageOptions();
        options.setTransformations(Transformation.Reorder);

        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        //Call ReorderPage and pass the reference of ViewerHandler's class  parameter by reference. 
        Utilities.PageTransformations.ReorderPage(handler, guid, CurrentPageNumber, NewPageNumber);

        //down cast the handler(ViewerHandler) to viewerHtmlHandler
        ViewerImageHandler imageHandler = (ViewerImageHandler) handler;

        //Get document pages in image form
        List<PageImage> Images = imageHandler.getPages(guid, options);

        for (PageImage image : Images) {
            //Save each image at disk
            Utilities.saveAsImage(image.getPageNumber() + "_" + DocumentName, image.getStream());
        }
    }

    /**
     * Render a document in image representation whom located at web/remote location.
     * @param DocumentURL URL of the document
     * @param DocumentPassword Password Parameter is optional
     * @throws Exception the exception
     */
    public static void renderDocumentAsImages(URI DocumentURL, String DocumentPassword) throws Exception {
        //ExStart:RenderRemoteDocAsImages
        //Get Configurations
        ViewerConfig config = Utilities.getConfiguration();

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        //Initialize ImageOptions Object
        ImageOptions options = new ImageOptions();

        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        //Get document pages in image form
        List<PageImage> Images = imageHandler.getPages(DocumentURL, options);

        for (PageImage image : Images) {
            //Save each image at disk
            Utilities.saveAsImage(image.getPageNumber() + "_" + new File(DocumentURL.getPath()).getName(), image.getStream());
        }
    }

    /**
     * Render a document as it is (original form)
     * @param DocumentName the document name
     * @throws Exception the exception
     */
    public static void renderDocumentAsOriginal(String DocumentName) throws Exception {
        //ExStart:RenderOriginal
        // Create image handler 
        ViewerImageHandler imageHandler = new ViewerImageHandler(Utilities.getConfiguration());

        // Guid implies that unique document name 
        String guid = DocumentName;

        // Get original file
        FileContainer container = imageHandler.getFile(guid);

        //Save each image at disk
        Utilities.saveAsImage(DocumentName, container.getStream());
    }

    /**
     * Render a document in PDF Form
     * @param DocumentName the document name
     * @throws Exception the exception
     */
    public static void renderDocumentAsPDF(String DocumentName) throws Exception {
        //ExStart:RenderAsPdf
        // Create/initialize image handler 
        ViewerImageHandler imageHandler = new ViewerImageHandler(Utilities.getConfiguration());

        //Initialize PdfFileOptions object
        PdfFileOptions options = new PdfFileOptions();

        // Guid implies that unique document name 
        options.setGuid(DocumentName);

        // Call GetPdfFile to get FileContainer type object which contains the stream of pdf file.
        FileContainer container = imageHandler.getPdfFile(options);

        //Change the extension of the file and assign to a String type variable filename
        String filename = Utilities.getFileNameWithoutExtension(DocumentName) + ".pdf";

        //Save each image at disk
        Utilities.saveFile(filename, container.getStream());
    }

    /**
     * Load directory structure as file tree
     * @throws Exception the exception
     */
    public static void loadFileTree() throws Exception {
        loadFileTree("");
    }

    /**
     * Load directory structure as file tree
     * @param path the path
     * @throws Exception the exception
     */
    public static void loadFileTree(String path) throws Exception {
        loadFileTree(path, FileTreeOptions.FileTreeOrderBy.Unknown, true);
    }

    /**
     * Load directory structure as file tree
     * @param Path the path
     * @param orderBy the order by
     * @param orderAsc the order asc
     * @throws Exception the exception
     */
    public static void loadFileTree(String Path, FileTreeOptions.FileTreeOrderBy orderBy, boolean orderAsc) throws Exception {
        //ExStart:loadFileTree
        // Create/initialize image handler 
        ViewerImageHandler imageHandler = new ViewerImageHandler(Utilities.getConfiguration());

        // Load file tree list for custom path 
        FileTreeOptions options = new FileTreeOptions(Path);
        options.setOrderBy(orderBy);
        options.setOrderAsc(orderAsc);

        // Load file tree list for ViewerConfig.StoragePath
        FileTreeContainer container = imageHandler.loadFileTree(options);

        for (FileDescription node : container.getFileTree()) {
            if (node.isDirectory()) {
                Utilities.writeLine(
                        "Guid: {0} | Name: {1} | LastModificationDate: {2}",
                        node.getGuid(),
                        node.getName(),
                        node.getLastModificationDate()
                );
            } else {
                Utilities.writeLine(
                        "Guid: {0} | Name: {1} | Document type: {2} | File type: {3} | Extension: {4} | Size: {5} | LastModificationDate: {6}",
                        node.getGuid(),
                        node.getName(),
                        node.getDocumentType(),
                        node.getFileType(),
                        node.getExtension(),
                        node.getSize(),
                        node.getLastModificationDate()
                );
            }
        }
        System.out.println();
    }

    /**
     * Render document as images.
     * @param name the name
     * @throws Exception the exception
     */
    public static void renderDocumentAsImages(String name) throws Exception {
        renderDocumentAsImages(name, null);
    }

    /**
     * Render document as html.
     * @param DocumentName the document name
     * @param WatermarkText the watermark text
     * @param watermarkColor the watermark color
     * @throws Exception the exception
     */
    public static void renderDocumentAsHtml(String DocumentName, String WatermarkText, Color watermarkColor) throws Exception {
        renderDocumentAsHtml(DocumentName, WatermarkText, watermarkColor, WatermarkPosition.Diagonal, 100, null);
    }
}

package com.groupdocs.viewer.sample;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.FileDescription;
import com.groupdocs.viewer.domain.Transformation;
import com.groupdocs.viewer.domain.WatermarkPosition;
import com.groupdocs.viewer.domain.containers.FileContainer;
import com.groupdocs.viewer.domain.containers.FileTreeContainer;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.domain.options.FileTreeOptions;
import com.groupdocs.viewer.domain.options.PdfFileOptions;
import com.groupdocs.viewer.handler.ViewerHandler;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.handler.ViewerImageHandler;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * @author Aleksey Permyakov (21.03.2016).
 */
public class ViewGenerator {

    public static void RenderDocumentAsHtml(String DocumentName) throws Exception {
        RenderDocumentAsHtml(DocumentName, null);
    }
    /// <summary>
    /// Render simple document in html representation
    /// </summary>
    /// <param name="DocumentName">File name</param>
    /// <param name="DocumentPassword">Optional</param>
    public static void RenderDocumentAsHtml(String DocumentName, String DocumentPassword) throws Exception {
        //ExStart:RenderAsHtml
        //Get Configurations
        ViewerConfig config = Utilities.GetConfigurations();

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);


        // Guid implies that unique document name 
        String guid = DocumentName;

        //Instantiate the HtmlOptions object
        HtmlOptions options = new HtmlOptions();

        //to get html representations of pages with embedded resources
        options.setResourcesEmbedded(true);

        // Set password if document is password protected. 
        if(DocumentPassword == null || DocumentPassword.isEmpty())
            options.setPassword(DocumentPassword);
        options.setPageNumbersToConvert(Arrays.asList(1, 2));
        //Get document pages in html form
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages)
        {
            //Save each page at disk
            Utilities.SaveAsHtml(page.getPageNumber() + "_" + DocumentName, page.getHtmlContent());
        }
        //ExEnd:RenderAsHtml
    }



    /// <summary>
    /// Render document in html representation with watermark
    /// </summary>
    /// <param name="DocumentName">file/document name</param>
    /// <param name="WatermarkText">watermark text</param>
    /// <param name="WatermarkColor"> System.Drawing.Color</param>
    /// <param name="position">Watermark Position is optional parameter. Default value is WatermarkPosition.Diagonal</param>
    /// <param name="WatermarkWidth"> width of watermark as integer. it is optional Parameter default value is 100</param>
    /// <param name="DocumentPassword">Password Parameter is optional</param>
    public static void RenderDocumentAsHtml(String DocumentName, String WatermarkText, Color WatermarkColor, WatermarkPosition position, int WatermarkWidth, String DocumentPassword) throws Exception {
        position = position == null ? WatermarkPosition.Diagonal : position;
        //ExStart:RenderAsHtmlWithWaterMark
        //Get Configurations
        ViewerConfig config = Utilities.GetConfigurations();

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);

        // Guid implies that unique document name 
        String guid = DocumentName;

        //Instantiate the HtmlOptions object 
        HtmlOptions options = new HtmlOptions();

        options.setResourcesEmbedded(false);
        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty())
            options.setPassword(DocumentPassword);

        // Call AddWatermark and pass the reference of HtmlOptions object as 1st parameter
        Utilities.PageTransformations.AddWatermark(options, WatermarkText, WatermarkColor, position, WatermarkWidth);

        //Get document pages in html form
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages)
        {
            //Save each page at disk
            Utilities.SaveAsHtml(page.getPageNumber() + "_" + DocumentName, page.getHtmlContent());
        }
        //ExEnd:RenderAsHtmlWithWaterMark
    }
    /// <summary>
    ///  document in html representation and reorder a page
    /// </summary>
    /// <param name="DocumentName">file/document name</param>
    /// <param name="CurrentPageNumber">Page existing order number</param>
    /// <param name="NewPageNumber">Page new order number</param>
    /// <param name="DocumentPassword">Password Parameter is optional</param>
    public static void RenderDocumentAsHtml(String DocumentName, int CurrentPageNumber, int NewPageNumber, String DocumentPassword) throws Exception {
        //ExStart:RenderAsHtmlAndReorderPage
        //Get Configurations
        ViewerConfig config = Utilities.GetConfigurations();

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
        if (DocumentPassword == null || DocumentPassword.isEmpty())
            options.setPassword(DocumentPassword);

        //Call ReorderPage and pass the reference of ViewerHandler's class  parameter by reference. 
        Utilities.PageTransformations.ReorderPage(handler, guid, CurrentPageNumber, NewPageNumber);

        //down cast the handler(ViewerHandler) to viewerHtmlHandler
        ViewerHtmlHandler htmlHandler = (ViewerHtmlHandler)handler;

        //Get document pages in html form
        List<PageHtml> pages = htmlHandler.getPages(guid, options);

        for (PageHtml page : pages)
        {
            //Save each page at disk
            Utilities.SaveAsHtml(page.getPageNumber() + "_" + DocumentName, page.getHtmlContent());
        }
        //ExEnd:RenderAsHtmlAndReorderPage
    }
    /// <summary>
    /// Render a document in html representation whom located at web/remote location.
    /// </summary>
    /// <param name="DocumentURL">URL of the document</param>
    /// <param name="DocumentPassword">Password Parameter is optional</param>
    public static void RenderDocumentAsHtml(URI DocumentURL, String DocumentPassword) throws Exception {
        //ExStart:RenderRemoteDocAsHtml
        //Get Configurations 
        ViewerConfig config = Utilities.GetConfigurations();

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);

        //Instantiate the HtmlOptions object
        HtmlOptions options = new HtmlOptions();

        if (DocumentPassword == null || DocumentPassword.isEmpty())
            options.setPassword(DocumentPassword);

        //Get document pages in html form
        List<PageHtml> pages = htmlHandler.getPages(DocumentURL,options);

        for (PageHtml page : pages)
        {
            //Save each page at disk
            Utilities.SaveAsHtml(page.getPageNumber() + "_" + new File(DocumentURL.getPath()).getName(), page.getHtmlContent());
        }
        //ExEnd:RenderRemoteDocAsHtml
    }

    /// <summary>
    /// Render simple document in image representation
    /// </summary>
    /// <param name="DocumentName">File name</param>
    /// <param name="DocumentPassword">Optional</param>
    public static void RenderDocumentAsImages(String DocumentName,String DocumentPassword)
    {
        //ExStart:RenderAsImage
        //Get Configurations
        ViewerConfig config = Utilities.GetConfigurations();

        // Create image handler 
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        // Guid implies that unique document name 
        String guid = DocumentName;

        //Initialize ImageOptions Object
        ImageOptions options = new ImageOptions();

        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty())
            options.setPassword(DocumentPassword);

        //Get document pages in image form
        List<PageImage> Images = imageHandler.getPages(guid, options);

        for (PageImage image : Images)
        {
            //Save each image at disk
            Utilities.SaveAsImage(image.getPageNumber() + "_" + DocumentName, image.getStream());
        }
        //ExEnd:RenderAsImage

    }
    /// <summary>
    /// Render document in image representation with watermark
    /// </summary>
    /// <param name="DocumentName">file/document name</param>
    /// <param name="WatermarkText">watermark text</param>
    /// <param name="WatermarkColor"> System.Drawing.Color</param>
    /// <param name="position">Watermark Position is optional parameter. Default value is WatermarkPosition.Diagonal</param>
    /// <param name="WatermarkWidth"> width of watermark as integer. it is optional Parameter default value is 100</param>
    /// <param name="DocumentPassword">Password Parameter is optional</param>
    public static void RenderDocumentAsImages(String DocumentName, String WatermarkText, Color WatermarkColor, WatermarkPosition position, int WatermarkWidth, String DocumentPassword)
    {
        position = position == null ? WatermarkPosition.Diagonal : position;
        //ExStart:RenderAsImageWithWaterMark
        //Get Configurations
        ViewerConfig config = Utilities.GetConfigurations();

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        // Guid implies that unique document name
        String guid = DocumentName;

        //Initialize ImageOptions Object
        ImageOptions options = new ImageOptions();

        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty())
            options.setPassword(DocumentPassword);

        // Call AddWatermark and pass the reference of ImageOptions object as 1st parameter
        Utilities.PageTransformations.AddWatermark(options, WatermarkText, WatermarkColor, position, WatermarkWidth);

        //Get document pages in image form
        List<PageImage> Images = imageHandler.getPages(guid, options);

        for (PageImage image : Images)
        {
            //Save each image at disk
            Utilities.SaveAsImage(image.getPageNumber() + "_" + DocumentName, image.getStream());
        }
        //ExEnd:RenderAsImageWithWaterMark
    }
    /// <summary>
    /// Render the document in image form and set the rotation angle to rotate the page while display.
    /// </summary>
    /// <param name="DocumentName"></param>
    /// <param name="RotationAngle">rotation angle in digits</param>
    /// <param name="DocumentPassword"></param>
    public static void RenderDocumentAsImages(String DocumentName, int RotationAngle, String DocumentPassword) throws Exception {
        //ExStart:RenderAsImageWithRotationTransformation
        //Get Configurations
        ViewerConfig config = Utilities.GetConfigurations();

        // Create image handler
        ViewerHandler handler = new ViewerImageHandler(config);

        // Guid implies that unique document name 
        String guid = DocumentName;

        //Initialize ImageOptions Object and setting Rotate Transformation
        ImageOptions options = new ImageOptions();
        options.setTransformations(Transformation.Rotate);

        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty())
            options.setPassword(DocumentPassword);

        //Call RotatePages to apply rotate transformation to a page
        Utilities.PageTransformations.RotatePages(handler, guid,1,RotationAngle);

        //down cast the handler(ViewerHandler) to viewerHtmlHandler
        ViewerImageHandler imageHandler = (ViewerImageHandler)handler;

        //Get document pages in image form
        List<PageImage> Images = imageHandler.getPages(guid, options);

        for (PageImage image : Images)
        {
            //Save each image at disk
            Utilities.SaveAsImage(image.getPageNumber() + "_" + DocumentName, image.getStream());
        }
        //ExEnd:RenderAsImageWithRotationTransformation
    }
    /// <summary>
    ///  document in image representation and reorder a page
    /// </summary>
    /// <param name="DocumentName">file/document name</param>
    /// <param name="CurrentPageNumber">Page existing order number</param>
    /// <param name="NewPageNumber">Page new order number</param>
    /// <param name="DocumentPassword">Password Parameter is optional</param>
    public static void RenderDocumentAsImages(String DocumentName, int CurrentPageNumber, int NewPageNumber, String DocumentPassword) throws Exception {
        //ExStart:RenderAsImageAndReorderPage
        //Get Configurations
        ViewerConfig config = Utilities.GetConfigurations();

        // Cast ViewerHtmlHandler class object to its base class(ViewerHandler).
        ViewerHandler handler = new ViewerImageHandler(config);

        // Guid implies that unique document name 
        String guid = DocumentName;

        //Initialize ImageOptions Object and setting Reorder Transformation
        ImageOptions options = new ImageOptions();
        options.setTransformations(Transformation.Reorder);

        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty())
            options.setPassword(DocumentPassword);

        //Call ReorderPage and pass the reference of ViewerHandler's class  parameter by reference. 
        Utilities.PageTransformations.ReorderPage(handler,guid,CurrentPageNumber,NewPageNumber);

        //down cast the handler(ViewerHandler) to viewerHtmlHandler
        ViewerImageHandler imageHandler = (ViewerImageHandler)handler;

        //Get document pages in image form
        List<PageImage> Images = imageHandler.getPages(guid, options);

        for (PageImage image : Images)
        {
            //Save each image at disk
            Utilities.SaveAsImage(image.getPageNumber() + "_" + DocumentName, image.getStream());
        }
        //ExEnd:RenderAsImageAndReorderPage
    }
    /// <summary>
    /// Render a document in image representation whom located at web/remote location.
    /// </summary>
    /// <param name="DocumentURL">URL of the document</param>
    /// <param name="DocumentPassword">Password Parameter is optional</param>
    public static void RenderDocumentAsImages(URI DocumentURL, String DocumentPassword)
    {
        //ExStart:RenderRemoteDocAsImages
        //Get Configurations
        ViewerConfig config = Utilities.GetConfigurations();

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(config);

        //Initialize ImageOptions Object
        ImageOptions options = new ImageOptions();

        // Set password if document is password protected. 
        if (DocumentPassword == null || DocumentPassword.isEmpty())
            options.setPassword(DocumentPassword);

        //Get document pages in image form
        List<PageImage> Images = imageHandler.getPages(DocumentURL, options);

        for (PageImage image : Images)
        {
            //Save each image at disk
            Utilities.SaveAsImage(image.getPageNumber() + "_" + new File(DocumentURL.getPath()).getName(), image.getStream());
        }
        //ExEnd:RenderRemoteDocAsImages
    }

    /// <summary>
    /// Render a document as it is (original form)
    /// </summary>
    /// <param name="DocumentName"></param>
    public static void RenderDocumentAsOriginal(String DocumentName)
    {
        //ExStart:RenderOriginal
        // Create image handler 
        ViewerImageHandler imageHandler = new ViewerImageHandler(Utilities.GetConfigurations());

        // Guid implies that unique document name 
        String guid = DocumentName;

        // Get original file
        FileContainer container = imageHandler.getFile(guid);

        //Save each image at disk
        Utilities.SaveAsImage(DocumentName, container.getStream());
        //ExEnd:RenderOriginal

    }
    /// <summary>
    /// Render a document in PDF Form
    /// </summary>
    /// <param name="DocumentName"></param>
    public static void RenderDocumentAsPDF(String DocumentName)
    {
        //ExStart:RenderAsPdf
        // Create/initialize image handler 
        ViewerImageHandler imageHandler = new ViewerImageHandler(Utilities.GetConfigurations());

        //Initialize PdfFileOptions object
        PdfFileOptions options = new PdfFileOptions();

        // Guid implies that unique document name 
        options.setGuid(DocumentName);

        // Call GetPdfFile to get FileContainer type object which contains the stream of pdf file.
        FileContainer container = imageHandler.getPdfFile(options);

        //Change the extension of the file and assign to a String type variable filename
        String filename = Utilities.getFileNameWithoutExtension(DocumentName)+".pdf";

        //Save each image at disk
        Utilities.SaveFile(filename, container.getStream());
        //ExEnd:RenderAsPdf

    }
    /// <summary>
    /// Load directory structure as file tree
    /// </summary>
    /// <param name="Path"></param>
    public static void LoadFileTree(String Path)
    {
        //ExStart:LoadFileTree
        // Create/initialize image handler 
        ViewerImageHandler imageHandler = new ViewerImageHandler(Utilities.GetConfigurations());

        // Load file tree list for custom path 
        FileTreeOptions options = new FileTreeOptions(Path);

        // Load file tree list for ViewerConfig.StoragePath
        FileTreeContainer container = imageHandler.loadFileTree(options);

        for (FileDescription node : container.getFileTree())
        {
            if (node.isDirectory())
            {
                Utilities.writeLine("Guid: {0} | Name: {1} | LastModificationDate: {2}",
                        node.getGuid(),
                        node.getName(),
                        node.getLastModificationDate());
            }
            else
                Utilities.writeLine("Guid: {0} | Name: {1} | Document type: {2} | File type: {3} | Extension: {4} | Size: {5} | LastModificationDate: {6}",
                        node.getGuid(),
                        node.getName(),
                        node.getDocumentType(),
                        node.getFileType(),
                        node.getExtension(),
                        node.getSize(),
                        node.getLastModificationDate());
        }

        //ExEnd:LoadFileTree

    }

    public static void RenderDocumentAsImages(String name) {
        RenderDocumentAsImages(name, null);
    }

    public static void RenderDocumentAsHtml(String DocumentName, String WatermarkText, Color watermarkColor) throws Exception {
        RenderDocumentAsHtml(DocumentName, WatermarkText, watermarkColor, WatermarkPosition.Diagonal, 100, null);
    }
}

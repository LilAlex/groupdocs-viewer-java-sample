package com.groupdocs.viewer.sample;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.Watermark;
import com.groupdocs.viewer.domain.WatermarkPosition;
import com.groupdocs.viewer.domain.options.ReorderPageOptions;
import com.groupdocs.viewer.domain.options.RotatePageOptions;
import com.groupdocs.viewer.handler.ViewerHandler;
import com.groupdocs.viewer.licensing.License;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Aleksey Permyakov (21.03.2016).
 */
public class Utilities {
    public static String PROJECT_PATH = new java.io.File("Data/").getAbsolutePath();
    public static String STORAGE_PATH = PROJECT_PATH + "/Storage/";
    public static String OUTPUT_HTML_PATH = PROJECT_PATH + "/Output/html/";
    public static String OUTPUT_IMAGE_PATH = PROJECT_PATH + "/Output/images/";
    public static String OUTPUT_PATH = PROJECT_PATH + "/Output/";
    public static String LICENSE_PATH = PROJECT_PATH + "/Storage/GroupDocs.Viewer.lic";

    static {
        final java.io.File sp = new java.io.File(STORAGE_PATH);
        final java.io.File ohp = new java.io.File(OUTPUT_HTML_PATH);
        final java.io.File oip = new java.io.File(OUTPUT_IMAGE_PATH);
        if ((!sp.exists() && !sp.mkdirs()) || (!ohp.exists() && !ohp.mkdirs()) || (!oip.exists() && !oip.mkdirs())) {
            System.out.println("Can't create data directories!");
        }
    }

    /// <summary>
    /// Initialize, populate and return the ViewerConfig object
    /// </summary>
    /// <returns>Populated ViewerConfig Object</returns>
    public static ViewerConfig GetConfigurations()
    {
        //ExStart:Configurations
        ViewerConfig config = new ViewerConfig();
        //set the storage path
        config.setStoragePath(STORAGE_PATH);
        //Uncomment the below line for cache purpose
        config.setUseCache(true);
        return config;
        //ExEnd:Configurations

    }

    public static String getFileNameWithoutExtension(String name) {
        final String shortName = new File(name).getName();
        return shortName.substring(0, shortName.lastIndexOf('.'));
    }

    public static void writeLine(String template, Object... params) {
        for (int n = 0; n < params.length; n++) {
            template = template.replaceAll("\\{" + Integer.toString(n) + "\\}", params[n].toString());
        }
        System.out.println(template);
    }




    public static class PageTransformations
    {
        /// <summary>
        /// Rotate a Page before rendering
        /// </summary>
        /// <param name="options"></param>
        /// <param name="angle"></param>

        public static void RotatePages(ViewerHandler handler, String guid, int PageNumber, int angle) throws Exception {
            //ExStart:rotationAngle
            // Set the property of handler's rotate Page
            handler.rotatePage(new RotatePageOptions(guid, PageNumber, angle));
            //ExEnd:rotationAngle
        }
        /// <summary>
        /// Reorder a page before rendering
        /// </summary>
        /// <param name="Handler">Base class of handlers</param>
        /// <param name="guid">File name</param>
        /// <param name="currentPageNumber">Existing number of page</param>
        /// <param name="newPageNumber">New number of page</param>
        public static void ReorderPage(ViewerHandler Handler, String guid, int currentPageNumber, int newPageNumber) throws Exception {
            //ExStart:reorderPage
            //Initialize the ReorderPageOptions object by passing guid as document name, current Page Number, new page number
            ReorderPageOptions options = new ReorderPageOptions(guid, currentPageNumber, newPageNumber);
            // call ViewerHandler's Reorder page function by passing initialized ReorderPageOptions object.
            Handler.reorderPage(options);
            //ExEnd:reorderPage
        }
        /// <summary>
        /// add a watermark text to all rendered images.
        /// </summary>
        /// <param name="options">HtmlOptions by reference</param>
        /// <param name="text">Watermark text</param>
        /// <param name="color">System.Drawing.Color</param>
        /// <param name="position"></param>
        /// <param name="width"></param>
        public static void AddWatermark(ImageOptions options, String text, Color color, WatermarkPosition position, float width)
        {
            //ExStart:AddWatermark
            //Initialize watermark object by passing the text to display.
            Watermark watermark = new Watermark(text);
            //Apply the watermark color by assigning System.Drawing.Color.
            watermark.setColor(color);
            //Set the watermark's position by assigning an enum WatermarkPosition's value.
            watermark.setPosition(position);
            //set an integer value as watermark width 
            watermark.setWidth(width);
            //Assign intialized and populated watermark object to ImageOptions or HtmlOptions objects
            options.setWatermark(watermark);
            //ExEnd:AddWatermark
        }
        /// <summary>
        /// add a watermark text to all rendered Html pages.
        /// </summary>
        /// <param name="options">HtmlOptions by reference</param>
        /// <param name="text">Watermark text</param>
        /// <param name="color">System.Drawing.Color</param>
        /// <param name="position"></param>
        /// <param name="width"></param>
        public static void AddWatermark(HtmlOptions options, String text, Color color, WatermarkPosition position, float width)
        {

            Watermark watermark = new Watermark(text);
            watermark.setColor(color);
            watermark.setPosition(position);
            watermark.setWidth(width);
            options.setWatermark(watermark);
        }

    }


    /// <summary>
    /// Set product's license 
    /// </summary>
    public static void ApplyLicense()
    {
        License lic = new License();
        lic.setLicense(LICENSE_PATH);
    }

    /// <summary>
    /// Save file in html form
    /// </summary>
    /// <param name="filename">Save as provided String</param>
    /// <param name="content">Html contents in String form</param>
    public static void SaveAsHtml(String filename, String content)
    {
        try
        {
            //ExStart:SaveAsHTML
            // set an html file name with absolute path
            String fname = new File(OUTPUT_HTML_PATH).getAbsolutePath() + "\\" + getFileNameWithoutExtension(filename) + ".html";
            new java.io.File(fname).getParentFile().mkdirs();
            // create a file at the disk
            FileUtils.writeByteArrayToFile(new File(fname), content.getBytes());
            //ExEnd:SaveAsHTML
        }catch (IOException e) {
            writeLine(e.getMessage());
        }

    }
    /// <summary>
    /// Save the rendered images at disk
    /// </summary>
    /// <param name="imageName">Save as provided String</param>
    /// <param name="imageContent">stream of image contents</param>
    public static void SaveAsImage(String imageName, InputStream imageContent)
    {
        try
        {
            //ExStart:SaveAsImage
            // extract the image from stream
            BufferedImage img = ImageIO.read(imageContent);

            final String path = new File(OUTPUT_IMAGE_PATH).getAbsolutePath() + "\\" + getFileNameWithoutExtension(imageName) + ".jpeg";
            new java.io.File(path).getParentFile().mkdirs();
            //save the image in the form of jpeg
            ImageIO.write(img, "jpg", new File(path));
        }
        catch (Exception e)
        {
            writeLine(e.getMessage());
        }
    }
    /// <summary>
    /// Save file in any format
    /// </summary>
    /// <param name="filename">Save as provided String</param>
    /// <param name="content">Stream as content of a file</param>
    public static void SaveFile(String filename, InputStream content)
    {
        try
        {
            //ExStart:SaveAnyFile
            //Create file stream
            final String path = new File(OUTPUT_PATH).getAbsolutePath() + filename;
            new java.io.File(path).getParentFile().mkdirs();
            FileOutputStream fileStream = new FileOutputStream(path);

            // Initialize the bytes array with the stream length and then fill it with data
            final long length = content.available();
            byte[] bytesInStream = new byte[(int) length];
            IOUtils.read(content, bytesInStream);

            // Use write method to write to the file specified above
            fileStream.write(bytesInStream, 0, bytesInStream.length);
            //ExEnd:SaveAnyFile
        }
        catch ( Exception ex)
        {
            writeLine(ex.getMessage());
        }
    }

}

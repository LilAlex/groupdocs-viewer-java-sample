package com.groupdocs.viewer.sample;

import com.groupdocs.viewer.License;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import static com.groupdocs.viewer.sample.TestRunner.*;

/**
 * The type Utilities.
 * @author Aleksey Permyakov (21.03.2016).
 */
public class Utilities {

    public static final double DELTA = 0.001;

    /**
     * Gets file name without extension.
     * @param name the name
     * @return the file name without extension
     */
    public static String getFileNameWithoutExtension(String name) {
        final String shortName = new File(name).getName();
        return shortName.substring(0, shortName.lastIndexOf('.'));
    }

    /**
     * Write line.
     * @param template the template
     * @param params   the params
     */
    public static void writeLine(String template, Object... params) {
        for (int n = 0; n < params.length; n++) {
            template = template.replaceAll("\\{" + Integer.toString(n) + "\\}", params[n].toString());
        }
        System.out.println(template);
    }

    public static void showTestHeader() {
        try {
            throw new Exception();
        } catch (Exception e) {
            System.out.println("=====================================================");
            System.out.println("Running test: " + e.getStackTrace()[1].getMethodName());
        }
    }

    /**
     * Set product's license
     */
    public static void applyLicense() {

        License lic = new License();
        if (LICENSE_PATH != null && new File(LICENSE_PATH).exists()) {
            lic.setLicense(LICENSE_PATH);
        }else {
            lic.setLicense(System.getenv("GROUPDOCS_TOTAL"));
        }
    }

    /**
     * Set product's license
     */
    public static void unsetLicense() {
        License lic = new License();
        lic.setLicense(new ByteArrayInputStream(new byte[0]));
    }

    /**
     * Save file in html form
     * @param filename Save as provided String
     * @param content  Html contents in String form
     */
    public static void saveAsHtml(String filename, String content) {
        try {
            //ExStart:SaveAsHTML
            // set an html file name with absolute path
            String fname = new File(OUTPUT_HTML_PATH).getAbsolutePath() + "\\" + getFileNameWithoutExtension(filename) + ".html";
            new java.io.File(fname).getParentFile().mkdirs();
            // create a file at the disk
            FileUtils.writeByteArrayToFile(new File(fname), content.getBytes());
            //ExEnd:SaveAsHTML
        } catch (IOException e) {
            writeLine(e.getMessage());
        }

    }

    /**
     * Save the rendered images at disk
     * @param imageName    Save as provided String
     * @param imageContent stream of image contents
     */
    public static void saveAsImage(String imageName, InputStream imageContent) {
        try {
            // extract the image from stream
            BufferedImage img = ImageIO.read(imageContent);
            final String path = new File(OUTPUT_IMAGE_PATH).getAbsolutePath() + "\\" + getFileNameWithoutExtension(imageName) + ".png";
            new java.io.File(path).getParentFile().mkdirs();
            //save the image in the form of jpeg
            ImageIO.write(img, "png", new File(path));
        } catch (Exception e) {
            writeLine(e.getMessage());
        }
    }

    /**
     * Save file in any format
     * @param filename Save as provided String
     * @param content  Stream as content of a file
     */
    public static void saveFile(String filename, InputStream content) {
        try {
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
        } catch (Exception ex) {
            writeLine(ex.getMessage());
        }
    }


    public static int countZIndexes(String source) {
        String subString = "z-index";
        int count = 0, n = 0;
        while ((n = source.indexOf(subString, n)) != -1) {
            n += subString.length();
            ++count;
        }

        return count;
    }

    public static void copyFile(String source, String target) throws IOException {
        FileUtils.copyFile(new File(source), new File(target));
    }

    public static void cleanOutput() {
        final File file = new File(OUTPUT_PATH);
        if (!file.delete()) {
            file.deleteOnExit();
        }
    }

    public static void initOutput() {
        final java.io.File sp = new java.io.File(STORAGE_PATH);
        final java.io.File op = new java.io.File(OUTPUT_PATH);
        final java.io.File ip = new java.io.File(OUTPUT_PATH);
        final java.io.File ohp = new java.io.File(OUTPUT_HTML_PATH);
        final java.io.File oip = new java.io.File(OUTPUT_IMAGE_PATH);
        final java.io.File lcp = new java.io.File(LICENSE_PATH);
        if (!lcp.exists()) {
            LICENSE_PATH = System.getenv("GROUPDOCS_TOTAL");
            System.out.println("License file does not exists! Using license from %GROUPDOCS_TOTAL% ...");
        }
        if ((!sp.exists() && !sp.mkdirs()) || (!op.exists() && !op.mkdirs()) || (!ip.exists() && !ip.mkdirs()) || (!ohp.exists() && !ohp.mkdirs()) || (!oip.exists() && !oip.mkdirs())) {
            System.err.println("Can't create data directories!!!");
        }
    }
}

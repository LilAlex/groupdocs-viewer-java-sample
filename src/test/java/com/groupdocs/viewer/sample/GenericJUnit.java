package com.groupdocs.viewer.sample;

import com.groupdocs.viewer.licensing.License;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.rules.ExpectedException;

import java.io.File;

/**
 * @author Aleksey Permyakov (03.06.2016).
 */
public class GenericJUnit {
    protected static final String WORKING_DIRECTORY = System.getProperty("test.working.directory");
    protected static final String RESOURCES_DIRECTORY = System.getProperty("test.resources.directory");

    @DataPoints("_TRUE_FALSE")
    @SuppressWarnings("unused")
    public static final Boolean[] _TRUE_FALSE = {
            true,
            false
    };
    @SuppressWarnings("unused")
    @DataPoints("_EXCEL_FILES")
    public static final String[] _EXCEL_FILES = {
            "excel-document.xls",
            "excel-document.xlsx",
            "excel-document.xlsm",
            "excel-document.xlsb",
            "excel-document.csv",
            "excel-document.ods",
            "excel-document.xls2003"
    };
    @SuppressWarnings("unused")
    @DataPoints("_WORD_FILES")
    public static final String[] _WORD_FILES = {
            "word-document.doc",
            "word-document.docx",
            "word-document.docm",
            "word-document.dot",
            "word-document.dotm",
            "word-document.dotx",
            "word-document.rtf",
            "word-document.txt",
            "word-document.odt",
            "word-document.ott",
            "word-document.xml",
            "word-document.htm",
            "word-document.html"
    };
    @SuppressWarnings("unused")
    @DataPoints("_PDF_FILES")
    public static final String[] _PDF_FILES = {
            "pdf-document.pdf",
            "pdf-document.epub",
            "pdf-document.xps"
    };

    @SuppressWarnings("unused")
    @DataPoints("_SLIDE_FILES")
    public static final String[] _SLIDE_FILES = {
            "slide-document.ppt",
            "slide-document.pps",
            "slide-document.pptx",
            "slide-document.ppsx",
            "slide-document.odp"
    };

    @SuppressWarnings("unused")
    @DataPoints("_PROJECT_FILES")
    public static final String[] _PROJECT_FILES = {
            "project-document.mpt",
            "project-document.mpp"
    };
    @SuppressWarnings("unused")
    @DataPoints("_MAIL_FILES")
    public static final String[] _MAIL_FILES = {
            "mail-document.msg",
            "mail-document.eml",
//            "mail-document.emlx",
            "mail-document.mht"
    };
    @SuppressWarnings("unused")
    @DataPoints("_TASK_FILES")
    public static final String[] _TASK_FILES = {
//            "task-document.vdx",
            "task-document.vdw",
            "task-document.vsd",
            "task-document.vsdx",
//            "task-document.vsx",
            "task-document.vss",
            "task-document.vst",
//            "task-document.vtx"
    };
    @SuppressWarnings("unused")
    @DataPoints("_IMAGE_FILES")
    public static final String[] _IMAGE_FILES = {
            "image-document.jpeg",
            "image-document.jpg",
            "image-document.png",
            "image-document.gif",
            "image-document.bmp",
            "image-document.ico",
            "image-document-m.ico"
    };
    @SuppressWarnings("unused")
    @DataPoints("_PHOTOSHOP_FILES")
    public static final String[] _PHOTOSHOP_FILES = {
            "photoshop-document.psd"
    };
    @SuppressWarnings("unused")
    @DataPoints("_TIFF_FILES")
    public static final String[] _TIFF_FILES = {
            "tiff-document.tiff",
            "tiff-document.tif"
    };
    @SuppressWarnings("unused")
    @DataPoints("_DIAGRAM_FILES")
    public static final String[] _DIAGRAM_FILES = {
            "diagram-document.dwg",
            "diagram-document.dxf"
    };
    @SuppressWarnings("unused")
    @DataPoints("_WRONG_FILES")
    public static final String[] _WRONG_FILES = {
            "wrong-document.*",
            "wrong-document.abc"
    };
    @SuppressWarnings("unused")
    @DataPoints("_QUALITY")
    public static final int[] _QUALITY = {
            0,
            25,
            50,
            75,
            100
    };

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        final File file = new File(WORKING_DIRECTORY);
        if (!file.exists() && !file.mkdirs()){
            Assert.fail("Can't create working directory!");
        }
        new License().setLicense(System.getenv("GROUPDOCS_TOTAL"));
    }
}

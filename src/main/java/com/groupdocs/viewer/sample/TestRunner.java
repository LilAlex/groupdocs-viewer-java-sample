package com.groupdocs.viewer.sample;

import com.groupdocs.viewer.licensing.License;
import com.groupdocs.viewer.sample.operations.*;
import com.groupdocs.viewer.sample.tasks.CommonIssuesTests;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.File;
import java.io.IOException;

/**
 * The type TestRunner.
 */
public class TestRunner {
    public static String PROJECT_PATH = new File("Data").getAbsolutePath();
    public static String STORAGE_PATH = PROJECT_PATH + "\\Storage";
    public static String OUTPUT_PATH = PROJECT_PATH + "\\Output";
    public static String OUTPUT_HTML_PATH = OUTPUT_PATH + "\\html";
    public static String OUTPUT_IMAGE_PATH = OUTPUT_PATH + "\\images";
    public static String LICENSE_PATH = STORAGE_PATH + "\\GroupDocs.Total.Java.lic";

    /**
     * The entry point of application.
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {

        Result result = JUnitCore.runClasses(
                InnerImageHandlerTests.class,
                InnerHtmlHandlerTests.class,
                CommonOperationsTests.class,
                HtmlRepresentationTests.class,
                ImageRepresentationTests.class,
                DocumentTransformationsTests.class,
                EmailAttachmentsTests.class,
                ParameterlessConstructorsTests.class,
                AdvancedOperationsTests.class,
                CommonIssuesTests.class,
                ExtraOperationsTests.class
        );

        for (Failure failure : result.getFailures()) {
            System.err.println(failure.toString());
            failure.getException().printStackTrace();
        }

        System.out.println(String.format("=== SUCCESS: %d, FAIL: %d, IGNORE: %d ===", result.getRunCount(), result.getFailureCount(), result.getIgnoreCount()));

        if (result.getFailures().size() > 0) {
            Assert.fail();
        }
    }

    public static String getStoragePath(String fileName, String... subDirectories) {
        StringBuilder builder = new StringBuilder(STORAGE_PATH);
        for (String part : subDirectories) {
            builder.append(File.separator).append(part);
        }
        return builder.append(File.separator).append(fileName).toString();
    }

    public static String getOutputPath(String fileName) {
        return OUTPUT_PATH + File.separator + fileName;
    }
}

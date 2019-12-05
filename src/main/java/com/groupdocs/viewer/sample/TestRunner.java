package com.groupdocs.viewer.sample;

import com.groupdocs.viewer.licensing.License;
import com.groupdocs.viewer.sample.operations.*;
import com.groupdocs.viewer.sample.tasks.CommonIssuesTests;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.internal.TextListener;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * The type TestRunner.
 */
public class TestRunner {
    public static String PROJECT_PATH = new File("Data").getAbsolutePath();
    public static String STORAGE_PATH = PROJECT_PATH + "\\Storage";
    public static String OUTPUT_PATH = PROJECT_PATH + "\\Output";
    public static String OUTPUT_HTML_PATH = OUTPUT_PATH + "\\html";
    public static String OUTPUT_IMAGE_PATH = OUTPUT_PATH + "\\images";
    public static String LICENSE_PATH = STORAGE_PATH + "\\..\\GroupDocs.Total.Java.lic";

    /**
     * The entry point of application.
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {

        // https://www.logicbig.com/tutorials/unit-testing/junit/junit-core.html
        Class<?>[] testClasses = {
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
        };

        Result result;
        if (args.length == 0) {
            JUnitCore junit = new JUnitCore();
            junit.addListener(new TextListener(System.out));
            result = junit.run(Computer.serial(), testClasses); // ParallelComputer.methods()
        } else {
            final String testName = args[0];
            Class<?> clazz = null;
            for (Class<?> testClass : testClasses) {
                try {
                    final Method testMethod = testClass.getDeclaredMethod(testName);
                    if (testMethod != null) {
                        if (clazz != null) {
                            System.err.println("The test '" + testName + "' CAN NOT be run because there are few classes with the same test name!");
                            System.exit(-1);
                        } else {
                            clazz = testClass;
                        }
                    }
                } catch (NoSuchMethodException e) {
                    // pass
                }
            }
            if (clazz == null) {
                System.err.println("The test '" + testName + "' was not found!");
                System.exit(-2);
            }
            final Request request = Request.method(clazz, testName);
            JUnitCore junit = new JUnitCore();
            junit.addListener(new TextListener(System.out));
            result = junit.run(request);
        }

        final int runCount = result.getRunCount();
        final int failureCount = result.getFailureCount();
        final int ignoreCount = result.getIgnoreCount();
        System.out.println(String.format("\n===== RUN: %d, SUCCESS: %d, FAIL: %d, IGNORE: %d =====\n", runCount, (runCount - failureCount - ignoreCount), failureCount, ignoreCount));

        System.exit(failureCount);    }

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

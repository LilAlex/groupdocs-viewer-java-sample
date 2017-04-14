package com.groupdocs.viewer.sample;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.domain.containers.DocumentInfoContainer;
import com.groupdocs.viewer.domain.options.DocumentInfoOptions;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.licensing.License;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Aleksey Permyakov (13.06.2016).
 */
//@Ignore
@RunWith(Theories.class)
public class CommonTests extends GenericJUnit {
    @Theory
    public void testSetLicense() {
        new License().setLicense(System.getenv("GROUPDOCS_TOTAL"));
//        assertTrue(License.isValidLicense());
    }

    @Theory
    public void testGetDocumentInfo(@FromDataPoints("_WORD_FILES") String fileName) throws Exception {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfileName: %s", fileName));
        // Init viewer handler with config
        ViewerHtmlHandler htmlHandler = createHtmlHandler(false, false);
        final DocumentInfoContainer documentInfo = htmlHandler.getDocumentInfo(new DocumentInfoOptions(fileName));
        assertNotNull(documentInfo);
        assertNotNull(documentInfo.getGuid());
    }
    @Theory
    public void testSetCustomFontsDirectory(@FromDataPoints("_FONT_PATHS") String fontPath) {
        System.out.println(
                String.format("\t- run configuration: \n\t\tfontPath: %s", fontPath));
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath("C:\\storage");

        // Add custom fonts directories to FontDirectories list
        config.getFontDirectories().add(fontPath);

        // Init viewer handler with config
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
    }
}

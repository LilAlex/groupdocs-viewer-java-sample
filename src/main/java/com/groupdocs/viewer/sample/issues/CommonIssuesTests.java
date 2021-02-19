package com.groupdocs.viewer.sample.issues;

import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.ViewInfoOptions;
import com.groupdocs.viewer.results.Page;
import com.groupdocs.viewer.results.ViewInfo;
import com.groupdocs.viewer.sample.BaseJUnit;
import com.groupdocs.viewer.sample.TestRunner;
import com.groupdocs.viewer.sample.Utilities;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Aleksey Permyakov
 */
public class CommonIssuesTests extends BaseJUnit {

    @Test
    public void testVIEWERJAVA2316() {
        Utilities.showTestHeader();
        for (String fileName : new String[]{"document.vdx", "document.vsd", "document.vss"}) {
            System.out.println("Processing " + fileName);
            final File fileTxt = new File(TestRunner.STORAGE_PATH + File.separator + fileName);

            try (Viewer viewer = new Viewer(fileTxt.getAbsolutePath())) {

                final ViewInfoOptions viewInfoOptions = ViewInfoOptions.forPngView(true);

                final ViewInfo viewInfo = viewer.getViewInfo(viewInfoOptions);
                assertNotNull(viewInfo);

                final List<Page> pages = viewInfo.getPages();
                for (Page page : pages) {
                    System.out.println("\tPage size is " + page.getWidth() + "x" + page.getHeight());
                    assertNotEquals(0, page.getWidth());
                    assertNotEquals(0, page.getHeight());
                }
            }
        }
    }
}

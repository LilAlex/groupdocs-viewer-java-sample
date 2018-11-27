package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.domain.containers.FileContainer;
import com.groupdocs.viewer.domain.html.HtmlResource;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.exception.ArgumentNullException;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.handler.cache.ICacheDataHandler;
import com.groupdocs.viewer.handler.input.IInputDataHandler;
import com.groupdocs.viewer.sample.TestFileStorage;
import com.groupdocs.viewer.sample.TestRunner;
import com.groupdocs.viewer.sample.Utilities;
import com.groupdocs.viewer.sample.handler.CacheDataHandler;
import com.groupdocs.viewer.sample.handler.LocalInputDataHandler;
import com.groupdocs.viewer.storage.IFileStorage;
import com.groupdocs.viewer.storage.LocalFileStorage;
import com.groupdocs.viewer.utils.CultureInfo;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.UUID;

import static org.junit.Assert.*;

public class InnerHtmlHandlerTests {
    private ViewerConfig _viewerConfig;
    private CultureInfo _cultureInfo;
    private IInputDataHandler _inputDataHandler;
    private ICacheDataHandler _cacheDataHandler;

    @Before
    public void beforeMethod() {
        Utilities.applyLicense();
        _viewerConfig = new ViewerConfig();
        _viewerConfig.setStoragePath(TestRunner.STORAGE_PATH);

        _cultureInfo = new CultureInfo("en-US");
        _inputDataHandler = new LocalInputDataHandler(_viewerConfig);
        _cacheDataHandler = new CacheDataHandler(_viewerConfig, new LocalFileStorage());
    }

    @Test
    public void testTwoParamsConstructorCreatesInstance() {
        ViewerHtmlHandler viewerHandler = new ViewerHtmlHandler(_viewerConfig, _cultureInfo);
        assertNotNull(viewerHandler);
    }

    @Test
    public void testThreeParamsConstructorWithCultureInfoCreatesInstance() {
        ViewerHtmlHandler viewerHandler =
                new ViewerHtmlHandler(_viewerConfig, _inputDataHandler, _cultureInfo);
        assertNotNull(viewerHandler);
    }

    @Test
    public void testThreeParamsConstructorWithCacheDataHandlerCreatesInstance() {
        ViewerHtmlHandler viewerHandler =
                new ViewerHtmlHandler(_viewerConfig, _inputDataHandler, _cacheDataHandler);
        assertNotNull(viewerHandler);
    }

    @Test
    public void testFourParamsConstructorCreatesInstance() {
        ViewerHtmlHandler viewerHandler =
                new ViewerHtmlHandler(_viewerConfig, _inputDataHandler, _cacheDataHandler, _cultureInfo);
        assertNotNull(viewerHandler);
    }

    @Test
    public void testGetResourceShouldThrowExceptionIfGuidIsNullOrEmpty() throws Exception {
        for (String param : new String[]{"", null}) {
            HtmlResource htmlResource = new HtmlResource("styles.css");
            try {
                new ViewerHtmlHandler(_viewerConfig).getResource(param, htmlResource);
                fail();
            } catch (ArgumentNullException e) {
                // pass
            }
        }
    }

    @Test
    public void testGetResourceShouldThrowExceptionIfHtmlResourceIsNull() throws Exception {
        try {
            new ViewerHtmlHandler(_viewerConfig).getResource("document.docx", null);
            fail();
        } catch (ArgumentNullException e) {
            // pass
        }
    }

    @Test
    public void testParameterlessConstructorShouldConvertToPagesWithoutConfigFiles() throws Exception {
        ViewerHtmlHandler viewerHandler = new ViewerHtmlHandler();

        java.util.List<PageHtml> pages = viewerHandler.getPages(TestRunner.STORAGE_PATH + File.separator + "document.doc");

        assertTrue(pages.size() > 0);
    }

    @Test
    public void testShouldProduceDifferentResultWhenEnablePreciseRendering() throws Exception {

        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(TestRunner.STORAGE_PATH);
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "glyphs-grouping.pdf";
        HtmlOptions options = new HtmlOptions();
        options.getPdfOptions().setEnablePreciseRendering(true);

        java.util.List<PageHtml> pages = htmlHandler.getPages(guid, options);

        int countWithPreventGrouping = Utilities.countZIndexes(pages.get(0).getHtmlContent());

        options.getPdfOptions().setEnablePreciseRendering(false);
        pages = htmlHandler.getPages(guid, options);
        int countWithoutPreventGrouping = Utilities.countZIndexes(pages.get(0).getHtmlContent());

        assertNotEquals(countWithoutPreventGrouping, countWithPreventGrouping);
    }

    @Test
    public void testShouldNotCreateCacheFolderWhenCachingDisabled() throws Exception {
        String document = "document-4page.doc";

        String storagePath = TestRunner.OUTPUT_PATH + File.separator + UUID.randomUUID();
        final File storageDirectory = new File(storagePath);
        if (!storageDirectory.mkdir()) {
            System.err.println("Cant create directory");
        }
        Utilities.copyFile(TestRunner.STORAGE_PATH + File.separator + document, storagePath + File.separator + document);

        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(storagePath);
        config.setEnableCaching(false);

        ViewerHtmlHandler hander = new ViewerHtmlHandler(config);
        hander.getPages(document);

        assertFalse(new File(config.getCachePath()).exists());

        if (!storageDirectory.delete()) {
            storageDirectory.deleteOnExit();
        }
    }

    @Test
    public void testShouldFallBackToLocalFileStorage() throws Exception {
        final File fileTxt = new File(TestRunner.STORAGE_PATH + File.separator + "file.txt");
        final File fileToDelete = new File(fileTxt.getName());
        FileUtils.copyFile(fileTxt, fileToDelete);

        ViewerHtmlHandler handler = new ViewerHtmlHandler((IFileStorage) null);
        FileContainer fileContainer = handler.getFile(fileTxt.getName());

        assertTrue(fileContainer.getStream().available() > 0);
        assertEquals("file.txt", fileContainer.getFileName());

        if (!fileToDelete.delete()) {
            fileToDelete.deleteOnExit();
        }
        fileContainer.dispose();
    }

    @Test
    public void testShouldCreateInstanceWithCustomStorageAndCultureInfo() {
        TestFileStorage storage = new TestFileStorage();
        CultureInfo cultureInfo = new CultureInfo("en-US");

        ViewerHtmlHandler handler = new ViewerHtmlHandler(storage, new CultureInfo(cultureInfo.getName()));

        assertNotNull(handler);
    }

    @Test
    public void testShouldCreateInstanceWithConfigCustomStorageAndCultureInfo() {
        ViewerConfig config = new ViewerConfig();
        TestFileStorage storage = new TestFileStorage();
        CultureInfo cultureInfo = new CultureInfo("en-US");

        ViewerHtmlHandler handler = new ViewerHtmlHandler(config, storage, new CultureInfo(cultureInfo.getName()));

        assertNotNull(handler);
    }
}

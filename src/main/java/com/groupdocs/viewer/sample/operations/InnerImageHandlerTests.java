package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.domain.containers.FileContainer;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.handler.cache.ICacheDataHandler;
import com.groupdocs.viewer.sample.TestFileStorage;
import com.groupdocs.viewer.sample.TestRunner;
import com.groupdocs.viewer.sample.handler.LocalCacheDataHandler;
import com.groupdocs.viewer.sample.handler.LocalInputDataHandler;
import com.groupdocs.viewer.storage.IFileStorage;
import com.groupdocs.viewer.utils.CultureInfo;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static com.groupdocs.viewer.sample.Utilities.applyLicense;
import static org.junit.Assert.*;

public class InnerImageHandlerTests {
    private LocalInputDataHandler _inputDataHandler;
    private ViewerConfig _viewerConfig;
    private CultureInfo _cultureInfo;
    private ICacheDataHandler _cacheDataHandler;

    @Before
    public void beforeMethod() {
        applyLicense();
        _viewerConfig = new ViewerConfig();
        _viewerConfig.setStoragePath(TestRunner.STORAGE_PATH);

        _cultureInfo = new CultureInfo("en-US");
        _inputDataHandler = new LocalInputDataHandler(_viewerConfig);
        _cacheDataHandler = new LocalCacheDataHandler(_viewerConfig);
    }

    @Test
    public void shouldNotThrowExceptionWhenUnsupportedCultureProvided() {
        final CultureInfo[] unsupportedCulture = {new CultureInfo("tk-TM")};
        try {
            new ViewerImageHandler(_viewerConfig, unsupportedCulture[0]);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void threeParamsConstructorWithCultureInfoCreatesInstance() {
        ViewerImageHandler viewerHandler =
                new ViewerImageHandler(_viewerConfig, _inputDataHandler, _cultureInfo);
        assertNotNull(viewerHandler);
    }

    @Test
    public void threeParamsConstructorWithCacheDataHandlerCreatesInstance() {
        ViewerImageHandler viewerHandler =
                new ViewerImageHandler(_viewerConfig, _inputDataHandler, _cacheDataHandler);
        assertNotNull(viewerHandler);
    }

    @Test
    public void fourParamsConstructorCreatesInstance() {
        ViewerImageHandler viewerHandler =
                new ViewerImageHandler(_viewerConfig, _inputDataHandler, _cacheDataHandler, _cultureInfo);
        assertNotNull(viewerHandler);
    }

    @Test
    public void parameterlessConstructorShouldConvertToPagesWithoutConfigFiles() throws Exception {

        ViewerImageHandler viewerHandler = new ViewerImageHandler();
        java.util.List<PageImage> pages = viewerHandler.getPages(TestRunner.STORAGE_PATH + File.separator + "document.doc");

        assertTrue(pages.size() > 0);
    }

    @Test
    public void shouldFallBackToLocalFileStorage() throws Exception {
        final File fileTxt = new File(TestRunner.STORAGE_PATH + File.separator + "file.txt");
        final File fileToDelete = new File(fileTxt.getName());
        FileUtils.copyFile(fileTxt, fileToDelete);

        ViewerImageHandler handler = new ViewerImageHandler((IFileStorage) null);
        FileContainer fileContainer = handler.getFile(fileTxt.getName());

        assertTrue(fileContainer.getStream().available() > 0);
        assertEquals("file.txt", fileContainer.getFileName());

        if (!fileToDelete.delete()) {
            fileToDelete.deleteOnExit();
        }
        fileContainer.dispose();
    }

    @Test
    public void shouldCreateInstanceWithCustomStorageAndCultureInfo() {
        TestFileStorage storage = new TestFileStorage();
        CultureInfo cultureInfo = new CultureInfo("en-US");

        ViewerImageHandler handler = new ViewerImageHandler(storage, new CultureInfo(cultureInfo.getName()));

        assertNotNull(handler);
    }

    @Test
    public void shouldCreateInstanceWithConfigCustomStorageAndCultureInfo() {
        ViewerConfig config = new ViewerConfig();
        TestFileStorage storage = new TestFileStorage();
        CultureInfo cultureInfo = new CultureInfo("en-US");

        ViewerImageHandler handler = new ViewerImageHandler(config, storage, new CultureInfo(cultureInfo.getName()));

        assertNotNull(handler);
    }
}

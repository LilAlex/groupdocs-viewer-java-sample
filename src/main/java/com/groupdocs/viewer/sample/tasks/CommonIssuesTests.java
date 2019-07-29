package com.groupdocs.viewer.sample.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.domain.*;
import com.groupdocs.viewer.domain.containers.DocumentInfoContainer;
import com.groupdocs.viewer.domain.html.HtmlResource;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.domain.options.DocumentInfoOptions;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.sample.Utilities;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.groupdocs.viewer.common.ViewerUtils.closeStreams;
import static com.groupdocs.viewer.sample.TestRunner.*;
import static com.groupdocs.viewer.sample.Utilities.applyLicense;
import static com.groupdocs.viewer.sample.Utilities.initOutput;
import static org.junit.Assert.*;

/**
 * @author Aleksey Permyakov
 */
public class CommonIssuesTests {

    @Before
    public void before() {
        applyLicense();
        initOutput();
    }

    @After
    public void after() throws IOException {
        Utilities.cleanOutput();
    }

    @Test
    public void testVIEWERJAVA1325() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        int pageNumber = 1, pagesCount = 1;
        final HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.setCountPagesToRender(pagesCount);
        htmlOptions.setPageNumber(pageNumber);
        htmlOptions.setPageNumbersToRender(Arrays.asList(pageNumber));
        // Create html handler
        final ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        final String guids[] = new String[]{"candy.pdf", "candy.pdf", "candy.pdf"};
        final CountDownLatch latch = new CountDownLatch(guids.length);
        for (final String guid : guids) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String prefix = "Thread-" + this.hashCode() + ": ";
                    try {
                        final List<PageHtml> pages;
                        pages = htmlHandler.getPages(guid, htmlOptions);
                        for (PageHtml page : pages) {
                            assertEquals("Page count incorrect", 1, page.getPageNumber());
                            assertNotNull("Html content of resource is null", page.getHtmlContent().substring(0, 100).replaceAll("\\s+", " "));
                        }
                    } catch (Throwable e) {
                        System.err.println(prefix + "Uncaught exception - " + e.getMessage());
                        e.printStackTrace(System.err);
                    }
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
    }

    @Test
    public void testVIEWERJAVA1358() throws Exception {
        String guid = "document-input.xlsx";

        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        config.setEnableCaching(true);

        ViewerHtmlHandler handler = new ViewerHtmlHandler(config);

        DocumentInfoContainer container = handler.getDocumentInfo(guid);

        System.out.println("name: " + container.getName());
        System.out.println("pages: " + container.getPages().size());
    }

    @Test
    public void testVIEWERJAVA1318() throws Exception {
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setEnableCaching(true);
        config.setStoragePath(STORAGE_PATH);

        // Setup html conversion options
        HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.setEmbedResources(false);

        // Init viewer html handler
        ViewerHtmlHandler handler = new ViewerHtmlHandler(config);

        //Clear files from cache
        handler.clearCache();

        DocumentInfoContainer info = handler.getDocumentInfo(STORAGE_PATH + File.separator + "Test.msg");

        // Iterate over the attachments collection
        for (AttachmentBase attachment : info.getAttachments()) {
            System.out.println("Attach name: " + attachment.getName() + ", Type: " + attachment.getFileType() + ", Source: " + attachment.getSourceDocumentGuid());

            // Get attachment document html representation
            List<PageHtml> pages = handler.getPages(attachment, htmlOptions);
            for (PageHtml page : pages) {
                System.out.println("	Page: " + page.getPageNumber() + ", size: " + page.getHtmlContent().length());
                for (HtmlResource htmlResource : page.getHtmlResources()) {
                    InputStream resourceStream = handler.getResource(attachment, htmlResource);
                    final FileOutputStream outputStream = new FileOutputStream(OUTPUT_HTML_PATH + "_" + page.getPageNumber() + "." + htmlResource.getResourceName());
                    IOUtils.copy(resourceStream, outputStream);
                    closeStreams(resourceStream, outputStream);
//                    FileUtils.writeStringToFile(new File(OUTPUT_HTML_PATH + "_" + page.getPageNumber() + attachment.getName()), page.getHtmlContent());
                    System.out.println("	Resource: " + htmlResource.getResourceName());
                }
            }
        }
    }

    @Test
    public void testVIEWERJAVA1320() throws Exception {
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        config.setEnableCaching(true);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "document.pdf";

        HtmlOptions options = new HtmlOptions();
        options.setEmbedResources(false);
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", pages.size(), 2);
        for (PageHtml page : pages) {
            System.out.println("Page number: " + page.getPageNumber());
            System.out.println("Html content: " + page.getHtmlContent().substring(0, 100).replaceAll("\\s+", " "));
            assertTrue("Page content is empty", page.getHtmlContent().length() > 0);
        }
    }

    @Test
    public void testVIEWERJAVA1316() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        config.setEnableCaching(false);
        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "VIEWERJAVA-1316.msg";
        // Get html representation of all document pages, without transformations
        List<PageHtml> pages = htmlHandler.getPages(guid);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", pages.size(), 1);
        for (PageHtml page : pages) {
            final String htmlContent = page.getHtmlContent();
            FileUtils.writeStringToFile(new File(OUTPUT_PATH + File.separator + "testVIEWERJAVA1316.html"), htmlContent);
            assertTrue("Page content is too short", htmlContent.length() > 1024 /* bytes */);
        }
    }

    @Test
    public void testVIEWERJAVA766() throws Exception {
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        config.setEnableCaching(true);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "VIEWERJAVA-766.xlsx";

        HtmlOptions options = new HtmlOptions();
        options.setEmbedResources(true);
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", 3, pages.size());

        for (PageHtml page : pages) {
            final String htmlContent = page.getHtmlContent();
            System.out.println("Page number: " + page.getPageNumber());
            FileUtils.writeStringToFile(new File(OUTPUT_PATH + File.separator + "testVIEWERJAVA766.html"), htmlContent);
            assertTrue("Page content is empty", htmlContent.length() > 0);
            System.out.println("Html content: " + htmlContent.substring(0, 100).replaceAll("\\s+", " ") + "...");

            final List<HtmlResource> htmlResources = page.getHtmlResources();
            assertFalse("Resources list is not empty", htmlResources.size() > 0);
        }
    }

    @Test
    public void testVIEWERJAVA976() throws Exception {
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        config.setEnableCaching(true);

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        String guid = "VIEWERJAVA-976.xlsx";

        HtmlOptions options = new HtmlOptions();
        options.setEmbedResources(true);
        List<PageHtml> pages = htmlHandler.getPages(guid, options);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", pages.size(), 13);

        for (PageHtml page : pages) {
            final String htmlContent = page.getHtmlContent();
            System.out.println("Page number: " + page.getPageNumber());
            FileUtils.writeStringToFile(new File(OUTPUT_PATH + File.separator + "testVIEWERJAVA976.html"), htmlContent);
            assertTrue("Page content is empty", htmlContent.length() > 0);
            System.out.println("Html content: " + htmlContent.substring(0, 100).replaceAll("\\s+", " ") + "...");

            final List<HtmlResource> htmlResources = page.getHtmlResources();
            assertFalse("Resources list is not empty", htmlResources.size() > 0);
        }
    }

    @Test
    public void testVIEWERJAVA1080() throws Exception {
        Utilities.showTestHeader();
        // Setup GroupDocs.Viewer config
        ViewerConfig config = new ViewerConfig();
        config.setStoragePath(STORAGE_PATH);
        config.setEnableCaching(false);
        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(config);
        HtmlOptions htmlOptions = new HtmlOptions();
        htmlOptions.setPageNumbersToRender(Arrays.asList(1, 3, 5));
        htmlOptions.setCountPagesToRender(3);
        String guid = "VIEWERJAVA-1080.docx";
        // Get html representation of all document pages, without transformations
        List<PageHtml> pages = htmlHandler.getPages(guid, htmlOptions);
        System.out.println("\tPages count: " + pages.size());
        assertEquals("Page count is incorrect", 3, pages.size());
        for (PageHtml page : pages) {
            final String htmlContent = page.getHtmlContent();
            FileUtils.writeStringToFile(new File(OUTPUT_PATH + File.separator + "testVIEWERJAVA1080.html"), htmlContent);
            assertTrue("Page content is too short", htmlContent.length() > 1024 /* bytes */);
        }
    }

    @Test
    @SuppressWarnings("All")
    public void testVIEWERJAVA1311() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        byte[] databytes = null;
        assertTrue("FileData is not serializable!", new FileData() instanceof Serializable);
        assertTrue("WordsFileData is not serializable!", new FileData() instanceof Serializable);
        assertTrue("EmailFileData is not serializable!", new FileData() instanceof Serializable);
        assertTrue("EmailAttachment is not serializable!", new Attachment() instanceof Serializable);
        assertTrue("RowData is not serializable!", new RowData() instanceof Serializable);
        assertTrue("PageData is not serializable!", new PageData() instanceof Serializable);
        assertTrue("DocumentInfoContainer is not serializable!", new DocumentInfoContainer() instanceof Serializable);
        try {
            final FileData fileData = new FileData();
            fileData.setDateCreated(new Date());
            fileData.setDateModified(new Date());
            fileData.setAttachments(Arrays.asList(new Attachment(), new Attachment()));
            final PageData pageData = new PageData();
            final RowData rowData = new RowData();
            rowData.setText("text");
            pageData.setRows(Arrays.asList(rowData, new RowData()));
            fileData.setPages(Arrays.asList(pageData, new PageData()));
            out = new ObjectOutputStream(bos);
            out.writeObject(fileData);
            out.flush();
            databytes = bos.toByteArray();
            assertTrue("Result data is empty!", databytes.length > 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @Ignore
    public void testVIEWERJAVA1214() throws Exception {
        // setup Viewer configuration
        ViewerConfig signConfig = new ViewerConfig();
        signConfig.setStoragePath(STORAGE_PATH);
        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(signConfig);
        FileData fileData = new FileData();
        String guid = "VIEWERJAVA-1214.docx";
        // Get document information
        DocumentInfoOptions options = new DocumentInfoOptions(guid);
        DocumentInfoContainer documentInfo = htmlHandler.getDocumentInfo(guid, options);
        int maxWidth = 0;
        int maxHeight = 0;
        for (PageData pageData : documentInfo.getPages()) {
            if (pageData.getHeight() > maxHeight) {
                maxHeight = pageData.getHeight();
                maxWidth = pageData.getWidth();
            }
        }
        fileData.setDateCreated(new Date());
        fileData.getDateCreated();
        fileData.setDateModified(documentInfo.getLastModificationDate());
        fileData.getDateModified();
        fileData.setPages(documentInfo.getPages());

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(fileData);
        out.flush();
        byte[] databytes = bos.toByteArray();
        assertTrue("Result data is empty!", databytes.length > 0);

        // Json
        ObjectMapper jsonMapper = new ObjectMapper();
        final String jsonContent = jsonMapper.writeValueAsString(fileData);
        assertNotNull("jsonContent is null", jsonContent);
        assertFalse("jsonContent is empty", jsonContent.isEmpty());
        System.out.println(jsonContent);

        assertNotNull("DocumentInfoContainer was not serialized", jsonMapper.writeValueAsString(new DocumentInfoContainer()));

        // Xml
        XmlMapper xmlMapper = new XmlMapper();
        final String xmlContent = xmlMapper.writeValueAsString(fileData);
        assertNotNull("xmlContent is null", xmlContent);
        assertFalse("xmlContent is empty", xmlContent.isEmpty());
        System.out.println(xmlContent);

        assertNotNull("DocumentInfoContainer was not serialized", xmlMapper.writeValueAsString(new DocumentInfoContainer()));
    }

    @Test
    public void testVIEWERJAVA1366() throws Exception {
        String guid = "VIEWERJAVA-1366.pdf";
        ViewerHtmlHandler handler = new ViewerHtmlHandler();
        DocumentInfoContainer container = handler.getDocumentInfo(STORAGE_PATH + File.separator + guid);
        assertNotNull("container is null!", container);
        System.out.println(container);
    }

    @Test
    public void testMsgToImage() throws Exception {

        final String sourceFile = "document.msg", targetFile = sourceFile + ".png",
                targetPath = OUTPUT_IMAGE_PATH + "\\" + targetFile;
        // setup Viewer configuration
        ViewerConfig signConfig = new ViewerConfig();
        signConfig.setStoragePath(STORAGE_PATH);
        // instantiating the conversion handler
        ViewerImageHandler handler = new ViewerImageHandler(signConfig);
        final List<PageImage> pages = handler.getPages(sourceFile);
        assertTrue(pages.size() > 0);
        for (PageImage page : pages) {
            final InputStream stream = page.getStream();
            assertNotNull(stream);
            final FileOutputStream outputStream = new FileOutputStream(targetPath);
            IOUtils.copy(stream, outputStream);
            closeStreams(stream, outputStream);
        }
        System.out.println("Output file: " + targetPath);
    }

    @Test
    public void testVIEWERJAVAxxx() throws Exception {

        System.out.println("test " + new ViewerConfig().getStoragePath());

//        // setup Viewer configuration
//        ViewerConfig signConfig = new ViewerConfig();
//        signConfig.setStoragePath(STORAGE_PATH);
//        signConfig.setOutputPath(OUTPUT_PATH);
//        signConfig.setImagesPath(IMAGES_PATH);
//        // instantiating the conversion handler
//        ViewerHandler<String> handler = new ViewerHandler<String>(signConfig);
//
//        // setup image viewer options
//        PdfSignTextOptions signOptions = new PdfSignTextOptions("John Smith");
//        signOptions.setLeft(100);
//        signOptions.setTop(100);
//        final SaveOptions saveOptions = new SaveOptions();
//        saveOptions.setOutputType(OutputType.String);
//        saveOptions.setOutputFileName("testVIEWERJAVA102.pdf");
//        // sign document
//        String signedPath = handler.<String>sign(getStoragePath("digital viewers.pdf"), signOptions, saveOptions);
//        System.out.println("Signed file path is: " + signedPath);
    }
}

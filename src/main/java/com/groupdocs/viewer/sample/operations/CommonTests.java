package com.groupdocs.viewer.sample.operations;

import com.groupdocs.viewer.FileType;
import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.ViewerSettings;
import com.groupdocs.viewer.caching.FileCache;
import com.groupdocs.viewer.interfaces.FileReader;
import com.groupdocs.viewer.options.LoadOptions;
import com.groupdocs.viewer.options.ViewInfoOptions;
import com.groupdocs.viewer.results.Character;
import com.groupdocs.viewer.results.*;
import com.groupdocs.viewer.sample.BaseJUnit;
import com.groupdocs.viewer.sample.TestRunner;
import com.groupdocs.viewer.sample.Utilities;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CommonTests extends BaseJUnit {

    @Test
    public void testInputStreamConstructorNotThrowingException() {
        new Viewer(new ByteArrayInputStream(new byte[]{})).close();
    }

    @Test
    public void testInputStreamLoadOptionsConstructorNotThrowingException() {
        new Viewer(new ByteArrayInputStream(new byte[]{}), new LoadOptions()).close();
    }

    @Test
    public void testInputStreamViewerSettingsConstructorNotThrowingException() {
        new Viewer(new ByteArrayInputStream(new byte[]{}), new ViewerSettings(new FileCache(""))).close();
    }

    @Test
    public void testFileReaderViewerSettingsConstructorNotThrowingException() {
        new Viewer(new FileReader() {
            @Override
            public InputStream read() {
                return new ByteArrayInputStream(new byte[0]);
            }
        }, new ViewerSettings(new FileCache(""))).close();
    }

    @Test
    public void testFileReaderLoadOptionsViewerSettingsConstructorNotThrowingException() {
        new Viewer(new FileReader() {
            @Override
            public InputStream read() {
                return new ByteArrayInputStream(new byte[0]);
            }
        }, new LoadOptions(), new ViewerSettings(new FileCache(""))).close();
    }

    @Test
    public void testStringConstructorNotThrowingException() throws IOException {
        final File tempFile = File.createTempFile("groupdocs-viewer", ".tmp");
        try {
            new Viewer(tempFile.getAbsolutePath()).close();
        } finally {
            if (!tempFile.delete()) {
                tempFile.deleteOnExit();
            }
        }
    }

    @Test
    public void testStringLoadOptionsConstructorNotThrowingException() throws IOException {
        final File tempFile = File.createTempFile("groupdocs-viewer", ".tmp");
        try {
            new Viewer(tempFile.getAbsolutePath(), new LoadOptions()).close();
        } finally {
            if (!tempFile.delete()) {
                tempFile.deleteOnExit();
            }
        }
    }

    @Test
    public void testStringViewerSettingsConstructorNotThrowingException() throws IOException {
        final File tempFile = File.createTempFile("groupdocs-viewer", ".tmp");
        try {
            new Viewer(tempFile.getAbsolutePath(), new ViewerSettings(new FileCache(""))).close();
        } finally {
            if (!tempFile.delete()) {
                tempFile.deleteOnExit();
            }
        }
    }

    @Test
    public void testStringLoadOptionsViewerSettingsConstructorNotThrowingException() throws IOException {
        final File tempFile = File.createTempFile("groupdocs-viewer", ".tmp");
        try {
            new Viewer(tempFile.getAbsolutePath(), new LoadOptions(), new ViewerSettings(new FileCache(""))).close();
        } finally {
            if (!tempFile.delete()) {
                tempFile.deleteOnExit();
            }
        }
    }

    @Test
    public void testGetViewInfoForPngViewFalse() {
        final File fileTxt = new File(TestRunner.STORAGE_PATH + File.separator + "file.txt");
        try (Viewer viewer = new Viewer(fileTxt.getAbsolutePath())) {

            final ViewInfoOptions viewInfoOptions = ViewInfoOptions.forPngView(false);

            final ViewInfo viewInfo = viewer.getViewInfo(viewInfoOptions);
            assertNotNull(viewInfo);

            final FileType fileType = viewInfo.getFileType();
            assertEquals(FileType.TXT, fileType);

            final List<Page> pages = viewInfo.getPages();
            assertEquals(1, pages.size());

            for (Page page : pages) {
                assertEquals(816, page.getWidth());
                assertEquals(1056, page.getHeight());
                assertEquals(1, page.getNumber());
                final List<Line> lines = page.getLines();
                assertNotNull(lines);
                assertEquals(0, lines.size());
            }
        }
    }

    @Test
    public void testGetViewInfoForPngViewTrue() {
        final File fileTxt = new File(TestRunner.STORAGE_PATH + File.separator + "file.txt");
        Viewer viewer = null;
        try {
            viewer = new Viewer(fileTxt.getAbsolutePath());

            final ViewInfoOptions viewInfoOptions = ViewInfoOptions.forPngView(true);

            final ViewInfo viewInfo = viewer.getViewInfo(viewInfoOptions);
            assertNotNull(viewInfo);

            final FileType fileType = viewInfo.getFileType();
            assertEquals(FileType.TXT, fileType);

            final List<Page> pages = viewInfo.getPages();
            assertEquals(1, pages.size());

            double[] wordsX = new double[]{56.7, 92.71};
            double[] wordsY = new double[]{57.025, 57.025};
            double[] wordsW = new double[]{30.0, 36.0};
            double[] wordsH = new double[]{11.0, 11.0};
            String[] wordsV = new String[]{"Hello", "world."};

            int[] charactersSize = new int[]{5, 6};
            double[][] characterX = new double[][]{
                    {56.7, 62.7, 68.7, 74.7, 80.7},
                    {92.71, 98.71, 104.71, 110.71, 116.71, 122.71}
            };
            double[][] characterY = new double[][]{
                    {57.025, 57.025, 57.025, 57.025, 57.025},
                    {57.025, 57.025, 57.025, 57.025, 57.025, 57.025}
            };
            double[][] characterW = new double[][]{
                    {6.0, 6.0, 6.0, 6.0, 6.0},
                    {6.0, 6.0, 6.0, 6.0, 6.0, 6.0}
            };
            double[][] characterH = new double[][]{
                    {11.0, 11.0, 11.0, 11.0, 11.0},
                    {11.0, 11.0, 11.0, 11.0, 11.0, 11.0}
            };
            String[][] characterV = new String[][]{
                    {"H", "e", "l", "l", "o"},
                    {"w", "o", "r", "l", "d", "."}
            };

            for (Page page : pages) {
                assertEquals(612, page.getWidth());
                assertEquals(792, page.getHeight());
                assertEquals(1, page.getNumber());
                final List<Line> lines = page.getLines();
                assertNotNull(lines);
                assertEquals(1, lines.size());
                for (Line line : lines) {
//                    System.out.print(line.getX() + ", ");
                    assertEquals(56.7, line.getX(), Utilities.DELTA);
                    assertEquals(57.025, line.getY(), Utilities.DELTA);
                    assertEquals(72.01, line.getWidth(), Utilities.DELTA);
                    assertEquals(11.0, line.getHeight(), Utilities.DELTA);
                    assertEquals("Hello world.", line.getValue());
                    final List<Word> words = line.getWords();
                    assertNotNull(words);
                    assertEquals(2, words.size());
                    for (int wordIndex = 0; wordIndex < words.size(); wordIndex++) {
                        Word word = words.get(wordIndex);
//                        System.out.print(word.getX() + ", ");
                        assertEquals(wordsX[wordIndex], word.getX(), Utilities.DELTA);
                        assertEquals(wordsY[wordIndex], word.getY(), Utilities.DELTA);
                        assertEquals(wordsW[wordIndex], word.getWidth(), Utilities.DELTA);
                        assertEquals(wordsH[wordIndex], word.getHeight(), Utilities.DELTA);
                        assertEquals(wordsV[wordIndex], word.getValue());
                        final List<Character> characters = word.getCharacters();
                        assertNotNull(characters);
                        assertEquals(charactersSize[wordIndex], characters.size());
                        for (int characterIndex = 0; characterIndex < characters.size(); characterIndex++) {
                            Character character = characters.get(characterIndex);
//                            System.out.print(character.getX() + ", ");
                            assertEquals(characterX[wordIndex][characterIndex], character.getX(), Utilities.DELTA);
                            assertEquals(characterY[wordIndex][characterIndex], character.getY(), Utilities.DELTA);
                            assertEquals(characterW[wordIndex][characterIndex], character.getWidth(), Utilities.DELTA);
                            assertEquals(characterH[wordIndex][characterIndex], character.getHeight(), Utilities.DELTA);
                            assertEquals(characterV[wordIndex][characterIndex], String.valueOf(character.getValue()));
                        }
//                        System.out.println();
                    }
                }
            }
        } finally {
            if (viewer != null) {
                viewer.close();
            }
        }
    }

}

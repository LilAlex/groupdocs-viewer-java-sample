package com.groupdocs.viewer.sample;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        /**
         *  Applying product license
         *  Please uncomment the statement if you do have license.
         */
        Utilities.applyLicense();
        //ExEnd:ApplyingLicense

        // File tree from root storage path
        ViewGenerator.loadFileTree();

        // File tree from 'temp' directory in storage path
//        ViewGenerator.loadFileTree("temp");

        // File tree from root storage path with sorting by size descending
//        ViewGenerator.loadFileTree("", FileTreeOptions.FileTreeOrderBy.Size, false);

        //Render a power point presentation in html form
//        ViewGenerator.renderDocumentAsHtml("word.doc");

        //Render a spreadsheet in html form
//         ViewGenerator.renderDocumentAsHtml("spreadsheet.xlsx");

        //Render a MS word document by replacing its 1st page order with 2nd page
//        ViewGenerator.renderDocumentAsHtml("large_will_not_getting_processed.docx");

        //Render a word document in html form and also apply a text as watermark on each page
//        ViewGenerator.renderDocumentAsHtml("word.docx", "Show me as watermark", Color.CYAN, WatermarkPosition.Diagonal, 100, null);

        //Render a document located at a web location
//         ViewGenerator.renderDocumentAsHtml(new URI("http://www.example.com/sample.doc"), null);


        //Render a power point presentation in images form.
//        ViewGenerator.renderDocumentAsImages("sample.pptx");

        //Render a spreadsheet in images form.
//        ViewGenerator.renderDocumentAsImages("spreadsheet.xlsx");

        //Render a MS word document by replacing its 1st page order with 2nd page.
//         ViewGenerator.renderDocumentAsImages("word.doc", 1, 2, null);

        //Render a word document in images form and also apply a text as watermark on each page.
//         ViewGenerator.renderDocumentAsImages("word.doc", "Show me as watermark", Color.getPurple(), null, 0, null);

        //Render a word document in images form and set the rotation angle to display the rotated page.
//         ViewGenerator.renderDocumentAsImages("word.doc", 180, null);

        //Render a document located at a web location // http://www.kdpu-nt.gov.ua/sites/default/files/referat_32.doc
//         ViewGenerator.renderDocumentAsImages(new URI("http://www.translate.ru/images/common/logo.gif"), null);

        //Render the word document in the form of pdf markup
//         ViewGenerator.renderDocumentAsPDF("test.pdf");

        //Render the document as it is (Original form)
//        ViewGenerator.renderDocumentAsOriginal("factsheet.pdf");

        //Render a document from Azure Storage
//        ViewGenerator.RenderDocFromAzure("word.doc");
        //Render a document from ftp location
        //ViewGenerator.RenderDocFromAzure("word.doc");


    }
}

package com.groupdocs.viewer.sample;

public class Main {
    public static void main(String[] args) throws Exception {

        //ExStart:ApplyingLicense
        /**
         *  Applying product license
         *  Please uncomment the statement if you do have license.
         */
        Utilities.ApplyLicense();
        //ExEnd:ApplyingLicense

        //Render a power point presentation in html form
        ViewGenerator.RenderDocumentAsHtml("word.doc");

        //Render a spreadsheet in html form
//         ViewGenerator.RenderDocumentAsHtml("spreadsheet.xlsx");

        //Render a MS word document by replacing its 1st page order with 2nd page
//        ViewGenerator.RenderDocumentAsHtml("large_will_not_getting_processed.docx");

        //Render a word document in html form and also apply a text as watermark on each page
//        ViewGenerator.RenderDocumentAsHtml("word.doc", "Show me as watermark", Color.CYAN);

        //Render a document located at a web location
//         ViewGenerator.RenderDocumentAsHtml(new URI("http://www.example.com/sample.doc"), null);


        //Render a power point presentation in images form.
//        ViewGenerator.RenderDocumentAsImages("sample.pptx");

        //Render a spreadsheet in images form.
//        ViewGenerator.RenderDocumentAsImages("spreadsheet.xlsx");

        //Render a MS word document by replacing its 1st page order with 2nd page.
//         ViewGenerator.RenderDocumentAsImages("word.doc", 1, 2, null);

        //Render a word document in images form and also apply a text as watermark on each page.
//         ViewGenerator.RenderDocumentAsImages("word.doc", "Show me as watermark", Color.getPurple(), null, 0, null);

        //Render a word document in images form and set the rotation angle to display the rotated page.
//         ViewGenerator.RenderDocumentAsImages("word.doc", 180, null);

        //Render a document located at a web location // http://www.kdpu-nt.gov.ua/sites/default/files/referat_32.doc
//         ViewGenerator.RenderDocumentAsImages(new URI("http://www.translate.ru/images/common/logo.gif"), null);

        //Render the word document in the form of pdf markup
//         ViewGenerator.RenderDocumentAsPDF("test.pdf");

        //Render the document as it is (Original form)
//        ViewGenerator.RenderDocumentAsOriginal("factsheet.pdf");

        //Render a document from Azure Storage
//        ViewGenerator.RenderDocFromAzure("word.doc");
        //Render a document from ftp location
        //ViewGenerator.RenderDocFromAzure("word.doc");


    }
}

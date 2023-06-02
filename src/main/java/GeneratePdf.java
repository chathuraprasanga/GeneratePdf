import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.FileNotFoundException;

public class GeneratePdf {

    public static void main(String[]args) throws FileNotFoundException {
        String path="invoice.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);

        float threecol = 190f;
        float twocol = 285f;
        float twocol150 = twocol+150f;
        float twocolumnWidth[] = {twocol150,twocol};
        float fullWidth[] = {threecol*3};
        Paragraph onesp = new Paragraph("\n");

        Table table = new Table(twocolumnWidth);
        table.addCell(new Cell().add("Invoice").setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
        Table nestedTable = new Table(new float[]{twocol/2,twocol/2});
        nestedTable.addCell(getHeaderTextCell("Invoice No. "));
        nestedTable.addCell(getHeaderTextCellValue("123456789"));
        nestedTable.addCell(getHeaderTextCell("Invoice Date. "));
        nestedTable.addCell(getHeaderTextCellValue("02/06/2023"));


        table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));

        Border gb = new SolidBorder(Color.GRAY,1f/2f);
        Table divider = new Table(fullWidth);
        divider.setBorder(gb);
        document.add(table);
        document.add(onesp);
        document.add(divider);
        document.add(onesp);

        Table twoColTable = new Table(twocolumnWidth);
        twoColTable.addCell(getBillingAndShippingCell("Billing Information"));
        twoColTable.addCell(getBillingAndShippingCell("Shipping Information"));
        document.add(twoColTable.setMarginBottom(12f));

        Table twoColTable2 = new Table(twocolumnWidth);
        twoColTable2.addCell(getCell10Left("Company",true));
        twoColTable2.addCell(getCell10Left("Name",true));
        twoColTable2.addCell(getCell10Left("Chathura Prasanga",false));
        twoColTable2.addCell(getCell10Left("Chathura",false));
        document.add(twoColTable2);

        Table twoColTable3 = new Table(twocolumnWidth);
        twoColTable3.addCell(getCell10Left("Name",true));
        twoColTable3.addCell(getCell10Left("Address",true));
        twoColTable3.addCell(getCell10Left("Chathura Prasanga",false));
        twoColTable3.addCell(getCell10Left("Godawele Watta,\n Kotikapola, Mawathagama",false));
        document.add(twoColTable3);





        document.close();
        System.out.println("PDF Generated Successfully");
    }

    static Cell getHeaderTextCell(String textValue){
        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getHeaderTextCellValue(String textValue){
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getBillingAndShippingCell(String textValue){
        return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getCell10Left(String textValue,Boolean isBold){
        Cell myCell = new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ?myCell.setBold():myCell;
    }

}

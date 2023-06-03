import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class GeneratePdf {

    public static void main(String[]args) throws FileNotFoundException, MalformedURLException {
        String path="invoice.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);

        String imagePath = "D:\\Projects\\GeneratePdf\\logo1.png";
        ImageData imageData = ImageDataFactory.create(imagePath);
        Image image = new Image(imageData);

        float x = pdfDocument.getDefaultPageSize().getWidth()/4096;
        float y = pdfDocument.getDefaultPageSize().getHeight()/4096;
        image.setFixedPosition(x-50,y-50);
        image.setOpacity(0.1f);
        document.add(image);


        float threecol = 190f;
        float twocol = 285f;
        float twocol150 = twocol+150f;
        float twocolumnWidth[] = {twocol150,twocol};
        float threeColumnWidth[] = {threecol,threecol,threecol};
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

        float oneColumnWidth[] = {twocol150};

        Table oneColTable1 = new Table(oneColumnWidth);
        oneColTable1.addCell(getCell10Left("Address",true));
        oneColTable1.addCell(getCell10Left("Godawele Watta, Kotikapola, \nMawathagama",false));
        oneColTable1.addCell(getCell10Left("Email",true));
        oneColTable1.addCell(getCell10Left("chathuraprasanga98@gmail.com",false));
        document.add(oneColTable1.setMarginBottom(10f));

        Table tableDivider2 = new Table(fullWidth);
        Border dgb = new DashedBorder(Color.GRAY,0.5f);
        document.add(tableDivider2.setBorder(dgb));

        Paragraph productPara = new Paragraph("Products");
        document.add(productPara.setBold());

        Table threeColTable1 = new Table(threeColumnWidth);
        threeColTable1.setBackgroundColor(Color.BLACK,0.7f);

        threeColTable1.addCell(new Cell().add("Description").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Quantity").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Price").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));
        document.add(threeColTable1);

        List<Product> productList = new ArrayList<Product>();
        productList.add(new Product("apple",2,159));
        productList.add(new Product("mango",4,285));
        productList.add(new Product("banana",2,98));
        productList.add(new Product("grapes",3,10));
        productList.add(new Product("coconut",2,61));
        productList.add(new Product("cherry",1,1000));
        productList.add(new Product("kiwi",3,30));

        Table threeColTable2 = new Table(threeColumnWidth);

        float totalSum = 0f;
        for (Product product:productList){
            float total = product.getQuantity()*product.getPriceperpiece();
            totalSum += total;
            threeColTable2.addCell(new Cell().add(product.getPname()).setBorder(Border.NO_BORDER).setMarginLeft(10f));
            threeColTable2.addCell(new Cell().add(String.valueOf(product.getQuantity())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            threeColTable2.addCell(new Cell().add(String.valueOf(total)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT)).setMarginRight(15f);
        }
        document.add(threeColTable2.setMarginBottom(20f));

        float onetwo[] = {threecol+125f,threecol*2};
        Table threeColTable4 = new Table(onetwo);
        threeColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        threeColTable4.addCell(new Cell().add(tableDivider2).setBorder(Border.NO_BORDER));
        document.add(threeColTable4);

        Table threeColTable3 = new Table(threeColumnWidth);
        threeColTable3.addCell(new Cell().add("").setBorder(Border.NO_BORDER).setMarginLeft(10f));
        threeColTable3.addCell(new Cell().add("Total").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        threeColTable3.addCell(new Cell().add(String.valueOf(totalSum)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT).setMarginRight(15f));
        document.add(threeColTable3);

        document.add(tableDivider2);
        document.add(new Paragraph("\n"));
        document.add(divider.setBorder(new SolidBorder(Color.GRAY,1)).setMarginBottom(15f));


        Table tb = new Table(fullWidth);
        tb.addCell(new Cell().add("TERMS AND CONDITIONS\n").setBold().setBorder(Border.NO_BORDER));
        List<String> TncList = new ArrayList<String>();
        TncList.add("1. The Seller shall not be liable to the Buyer directly or indirectly for any loss or damage suffered by the Buyer");
        TncList.add("2. The Seller warrants the product for one (1) year of the date of shipment");

        for (String tnc:TncList){
            tb.addCell(new Cell().add(tnc).setBorder(Border.NO_BORDER));
        }
        document.add(tb);


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

class Product{
    private String pname;
    private int quantity;
    private float priceperpiece;

    public Product() {
    }

    public Product(String pname, int quantity, float priceperpiece) {
        this.pname = pname;
        this.quantity = quantity;
        this.priceperpiece = priceperpiece;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPriceperpiece() {
        return priceperpiece;
    }

    public void setPriceperpiece(float priceperpiece) {
        this.priceperpiece = priceperpiece;
    }
}

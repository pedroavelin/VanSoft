/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Gonga
 */
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
//import java.awt.Font;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class StampPageXofY {

    private Image IMG;
    private String NumDoc = "";
    private String total = "";
    private String licenca = "u2gi-Processado por programa validado nº xx/AGT/20xx";
    private String gota_de_agua = "src/cramervan/Vista/img/Capturar.png";
    private boolean estado;
    

    public StampPageXofY() {}

    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        BaseFont helv = BaseFont.createFont(BaseFont.HELVETICA,
                BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        PdfReader reader = new PdfReader(src);
        int n = reader.getNumberOfPages();

        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        PdfContentByte pagecontent;
//        System.out.println("qnt pga: "+n);

//        Image watermark = Image.getInstance("C:\\Users\\Gonga\\Desktop\\Capturar.png");
         Image watermark = Image.getInstance(this.gota_de_agua);
        watermark.setAbsolutePosition(100, 800);
        watermark.scaleAbsolute(300, 25);

        for (int i = 0; i < n;) {

            float x = 559;
            float y = 806;
            pagecontent = stamper.getOverContent(++i);
            if (!this.isEstado()) {
                pagecontent.addImage(watermark);

                watermark.setAbsolutePosition(150, 660);
                watermark.scaleAbsolute(300, 25);
                pagecontent.addImage(watermark);

                watermark.setAbsolutePosition(200, 550);
                watermark.scaleAbsolute(300, 25);
                pagecontent.addImage(watermark);
            }
            if (n < 2) {
                ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                        new Phrase(String.format(" pag. %s de %s ", i, n), new Font(helv, 8)), x, y, 0);
                y = y - 770;
                x = x - 200;

                ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                        new Phrase(this.licenca), x, y, 0);
            } else {
//                if (i == 1) {
//                    ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
//                            new Phrase(String.format(" pag. %s de %s", i, n), new Font(helv, 8)), x, y, 0);
//                    y = y-750;
//                    x = x-210;
//                    ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
//                                new Phrase("Transportar "+this.total, new Font(helv, 8)), x, y, 0);
//                }
                if (n > i) {
                    ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                            new Phrase(String.format(" pag. %s de %s ", i, n), new Font(helv, 8)), x, y, 0);
                    y = y - 750;
                    x = x - 200;
                    ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                            new Phrase(" ", new Font(helv, 8)), x, y, 0);
                    y = y - 10;
                    ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                            new Phrase(("Nº " + this.NumDoc + "    Transportar " + this.total), new Font(helv, 8)), x, y, 0);
                    y = y - 10;
                    ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                            new Phrase(this.licenca), x, y, 0);

                } else {
                    ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                            new Phrase(String.format(" pag. %s de %s ", i, n), new Font(helv, 8)), x, y, 0);
                    y = y - 10;
                    ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                            new Phrase("Transportado " + this.total, new Font(helv, 8)), x, y, 0);

                    y = y - 750;
                    x = x - 200;
                    ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                            new Phrase("Nº " + this.NumDoc, new Font(helv, 8)), x, y, 0);
                    y = y - 10;
                    ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                            new Phrase(this.licenca), x, y, 0);

                }

            }
//            pagecontent.addImage(watermark,true);
        }

        stamper.close();
        reader.close();
    }
    
    public void Paginar(String src, String dest) throws IOException, DocumentException {
        BaseFont helv = BaseFont.createFont(BaseFont.HELVETICA,
                BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        PdfReader reader = new PdfReader(src);
        int n = reader.getNumberOfPages();

        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        PdfContentByte pagecontent;
//        System.out.println("qnt pga: "+n);

//        Image watermark = Image.getInstance("C:\\Users\\Gonga\\Desktop\\Capturar.png");
         Image watermark = Image.getInstance(this.gota_de_agua);
        watermark.setAbsolutePosition(100, 800);
        watermark.scaleAbsolute(300, 25);

        for (int i = 0; i < n;) {

            float x = 559;
            float y = 806;
            pagecontent = stamper.getOverContent(++i);
            if (!this.isEstado()) {
                pagecontent.addImage(watermark);

                watermark.setAbsolutePosition(150, 660);
                watermark.scaleAbsolute(300, 25);
                pagecontent.addImage(watermark);

                watermark.setAbsolutePosition(200, 550);
                watermark.scaleAbsolute(300, 25);
                pagecontent.addImage(watermark);
            }
             ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                        new Phrase(String.format(" pag. %s de %s ", i, n), new Font(helv, 8)), x, y, 0);
                y = y - 770;
                x = x - 200;

                ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT,
                        new Phrase(this.licenca), x, y, 0);              
        }

        stamper.close();
        reader.close();
    }

    public Image getIMG() {
        return IMG;
    }

    public void setIMG(Image IMG) {
        this.IMG = IMG;
    }

    public String getNumDoc() {
        return NumDoc;
    }

    public void setNumDoc(String NumDoc) {
        this.NumDoc = NumDoc;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getLicenca() {
        return licenca;
    }

    public void setLicenca(String licenca) {
        this.licenca = licenca;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    

    public void PaginarX(byte [] src, String dest) throws IOException, DocumentException {
        BaseFont helv = BaseFont.createFont(BaseFont.HELVETICA,
                BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        PdfReader reader = new PdfReader(src);
        int n = reader.getNumberOfPages();

        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        PdfContentByte pagecontent;
//        System.out.println("qnt pga: "+n);

//        Image watermark = Image.getInstance("C:\\Users\\Gonga\\Desktop\\Capturar.png");
         Image watermark = Image.getInstance(this.gota_de_agua);
        watermark.setAbsolutePosition(100, 800);
        watermark.scaleAbsolute(300, 25);

        for (int i = 0; i < n;) {

            
            pagecontent = stamper.getOverContent(++i);
                       
        }

        stamper.close();
        reader.close();
    }
    
    
}

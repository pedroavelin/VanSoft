/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.itextpdf.text.BaseColor;
import java.awt.Color;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.OutputStreamCounter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonga
 */
public class Paginacao extends PdfPageEventHelper {

    protected PdfTemplate total;
    protected BaseFont helv;
    protected PdfContentByte cb;
    protected String caminho;
    int numPg;

    public Paginacao(int n) {
        this.numPg = n;
        System.out.println("O doc tem "+n+" Página(s)");
    }
    
    
    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
    
    
    
    public void onOpenDocument(PdfWriter writer, Document document) {

        total = writer.getDirectContent().createTemplate(100, 100);

        total.setBoundingBox(new Rectangle(87, 26, 102, 26));
        try {
            helv = BaseFont.createFont(BaseFont.HELVETICA,
                    BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
        
        System.out.println("open");
    }

    public int GetTotalpage(){
        try {
           
            PdfReader pdfr = new PdfReader(cb.toPdf(cb.getPdfWriter()));
            int paginas = pdfr.getNumberOfPages();
            System.out.println("num: "+paginas);
            return paginas;
        } catch (IOException ex) {
            Logger.getLogger(Paginacao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return 0;
    }
    public void onEndPage(PdfWriter writer, Document document) {
        cb = writer.getDirectContent();
        cb.saveState();
        String text = "Folha: " + writer.getPageNumber() + "/"+this.numPg;
//        this.numPg++;
        float textBase = document.top();
        float textSize = helv.getWidthPoint(text, 8);
        float adjust = helv.getWidthPoint("0", 80);
        cb.beginText();
        cb.setFontAndSize(helv, 8);
       
//        cb.setColorFill(new Color(0, 0, 0));
        cb.setColorFill(BaseColor.BLACK);
//        cb.setColorStroke(new Color(0, 0, 0));
        cb.setColorStroke(BaseColor.BLACK);
        cb.setTextMatrix(document.right() - textSize - adjust, textBase);
        cb.showText(text);

        cb.endText();
        cb.addTemplate(total, document.right() - adjust, textBase);

        cb.restoreState();
        
//        System.out.println("onend");
       
    }

    public void onCloseDocument(PdfWriter writer, Document document) {

        total.beginText();
        total.setFontAndSize(helv, 8);
        total.setTextMatrix(0, 0);
        total.showText(String.valueOf(writer.getPageNumber() - 1));
        total.endText();
         
        
    }
    
    
    private void adicionarMarcaDagua(String path, String name) {
        try {
            String filepath = path + name;
            String newfilepath = path + name.substring(5);

            PdfReader pdfr = new PdfReader(filepath);
            int paginas = pdfr.getNumberOfPages(); 

            PdfStamper stamp = new PdfStamper(pdfr, new FileOutputStream(newfilepath));

            int i = 0;
            Image watermark = Image.getInstance("C:\\Users\\Hugo\\Dropbox\\Programação\\Java\\Projetos\\Eduardo\\Hidros\\Workspace\\Hidros\\WebContent\\resources\\img\\watermark.png");

            watermark.setAbsolutePosition(50, 375);
            watermark.scaleAbsolute(500, 110);
//            watermark.scaleAbsolute(800, 110);
            PdfContentByte pdfcb;
            while (i < paginas) {
                i++;
                pdfcb = stamp.getUnderContent(i);
                pdfcb.addImage(watermark);   
            }
            stamp.close();
            pdfr.close();

            excluirArquivo(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    
    private void excluirArquivo(String path) {
        File f = new File(path);
        if (f.delete())
            System.out.println("Excluiu");
        else
            System.out.println("Não excluiu");
    }
    
    public int atualPage(){
       return cb.getPdfWriter().getCurrentPageNumber();
    }

    public int getNumPg() {
        return numPg;
    }

    public void setNumPg(int numPg) {
        this.numPg = numPg;
    }
    
    int TotalPg(String caminho){
        PdfReader reader;
        try {
            reader = new PdfReader(caminho.getBytes());
            int n = reader.getNumberOfPages();
            return n;
        } catch (IOException ex) {
            Logger.getLogger(Paginacao.class.getName()).log(Level.SEVERE, null, ex);
        }
     return 0;
    }
}


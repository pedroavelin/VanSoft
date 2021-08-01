/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.  exportA42_Nota

            tb = "";
            coluna_tb = "";
 */
package Modelo;

import Modelo.*;
import Controle.ControloBD;
import Controle.Tempo;
import Controle.Extenso;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfReader;
import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.print.event.PrintEvent;

/**
 *
 * @author Gonga
 */
public class ModeloPDF_2_via {
//    private ModeloPDF_2_via Segunda_via;
    private boolean estadoLicenca = false;
    private String LocalImg = "src/cramervan/Vista/img/Capturar.png";
    private File file;
    private JTable tabla;
    private String nom_files;
    private ModeloEmpresa ME;
    private ModeloCliente MC;
    private ModeloUtilizador MU;
    private ModeloFatura MF;
    private ArrayList<String> FormasDePagamento;
    Extenso exten = new Extenso();
//    ControloBD liga = new ControloBD(); 
    String caminho = null;
    Tempo tempo = new Tempo();
//    private String licenca = "u2gi-Processado por programa validado nº xx/AGT/20xx";
    private String licenca = " ";
    ControloBD conexao;
    
    public ModeloPDF_2_via(String caminho) {
        //To change body of generated methods, choose Tools | Templates.
       
        this.caminho = caminho;
        ControloBD liga = new ControloBD();
        liga.setCaminho(caminho);
        this.estadoLicenca = liga.licenca();
        this.conexao = liga;
        
    }

    public ModeloEmpresa getME() {
        return ME;
    }

    public void setME(ModeloEmpresa ME) {
        this.ME = ME;
    }

    public ModeloFatura getMF() {
        return MF;
    }

    public void setMF(ModeloFatura MF) {
        this.MF = MF;
    }

    public ModeloPDF_2_via(File file, JTable tabla, String nom_files, String caminho) {
       
                    
        this.file = file;
//        System.out.println("DIR" + file.getParent());
//        System.out.println("Nome do ficheiro " + file.getName());
        this.tabla = tabla;
        this.nom_files = nom_files;
        this.caminho = caminho;
        ControloBD liga = new ControloBD();
        liga.setCaminho(caminho);
        this.estadoLicenca = liga.licenca();
        this.conexao = liga;
    }

    public ModeloPDF_2_via(JTable tabla, String nom_files, String caminho) {
//       this.Segunda_via = new ModeloPDF_2_via(file, tabla, "Documento", caminho);
//                    this.Segunda_via.setME(ME);
//                   this.Segunda_via.setMC(MC);
//                    this.Segunda_via.setMU(MU);
//                    this.Segunda_via.setMF(MF);
//                    this.Segunda_via.setFormasDePagamento(FormasDePagamento);
//                    
//        this.tabla = tabla;
//        this.nom_files = nom_files;
//        this.caminho = caminho;
        ControloBD liga = new ControloBD();
        liga.setCaminho(caminho);
        this.estadoLicenca = liga.licenca();
        this.conexao = liga;
    }

    private Object GetData(JTable jtable, int row, int collum) {
        if (jtable.getModel().getValueAt(row, collum) != null) {
            return jtable.getModel().getValueAt(row, collum);
        } else //            System.out.println("valor nulo"); StampPageXofY
        {
            return null;
        }
    }

    public ArrayList<String> getFormasDePagamento() {
        return FormasDePagamento;
    }

    public void setFormasDePagamento(ArrayList<String> FormasDePagamento) {
        this.FormasDePagamento = FormasDePagamento;
    }

    public void exportA4() throws IOException {

        Document doc = null;
        OutputStream out = null;

        try {
            doc = new Document();
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;
                doc.setPageSize(PageSize.A4);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 100);
//                IMG.setAbsolutePosition(400, 700);
                doc.add(IMG);
                Paragraph p1 = new Paragraph();
                Paragraph p3 = new Paragraph();
                Paragraph p2 = new Paragraph();
                Paragraph p4 = new Paragraph();
                p1.setSpacingBefore(-50);
                p1.setAlignment(1);
                p2.setAlignment(1);
                p3.setAlignment(1);
                p4.setAlignment(1);
                p1.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                p2.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                p3.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                p4.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                p1.add(ME.getDesignacao());
                p2.add("Telefone: " + Integer.toString(ME.getTelefone()));
                p3.add(ME.getPais() + ", " + ME.getProvincia() + ", " + ME.getRua() + "");
                p4.add("NIF: " + ME.getNif());
                doc.add(p1);
                doc.add(p2);
                doc.add(p3);
                doc.add(p4);
                Paragraph P5 = new Paragraph("\n \n");
                P5.setSpacingBefore(40);

                doc.add(P5);

                PdfPTable tb = new PdfPTable(6);
//                Acrescentar se é visitante ou não como cabeçalho
                PdfPCell cab1 = new PdfPCell(new Paragraph("Código"));

                cab1.setHorizontalAlignment(Element.ALIGN_LEFT);
                cab1.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab2 = new PdfPCell(new Paragraph("Tipo"));
                cab2.setHorizontalAlignment(Element.ALIGN_LEFT);
                cab2.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab3 = new PdfPCell(new Paragraph("Descrição"));
                cab3.setHorizontalAlignment(Element.ALIGN_LEFT);
                cab3.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab4 = new PdfPCell(new Paragraph("Preço unitário"));
                cab4.setHorizontalAlignment(Element.ALIGN_LEFT);
                cab4.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab5 = new PdfPCell(new Paragraph("Quantidade"));
                cab5.setHorizontalAlignment(Element.ALIGN_LEFT);
                cab5.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab6 = new PdfPCell(new Paragraph("Desconto"));
                cab6.setHorizontalAlignment(Element.ALIGN_LEFT);
                cab6.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab7 = new PdfPCell(new Paragraph("Total"));
                cab7.setHorizontalAlignment(Element.ALIGN_LEFT);
                cab7.setBackgroundColor(BaseColor.GRAY);

//                PdfPCell cab8 = new PdfPCell(new Paragraph("Hora de saida"));
//                cab8.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cab8.setBackgroundColor(BaseColor.GRAY);
//
//                PdfPCell cab9 = new PdfPCell(new Paragraph("Visitante"));
//                cab9.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cab9.setBackgroundColor(BaseColor.GRAY);
                tb.addCell(cab1);
                tb.addCell(cab2);
                tb.addCell(cab3);
                tb.addCell(cab4);
                tb.addCell(cab5);
                tb.addCell(cab6);
                tb.addCell(cab7);
//                tb.addCell(cab8);
////                tb.addCell(cab9);
//
                for (int i = 0; i < this.tabla.getRowCount(); i++) {

                    PdfPCell cel1 = null;
                    PdfPCell cel2 = null;
                    PdfPCell cel3 = null;
                    PdfPCell cel4 = null;
                    PdfPCell cel5 = null;
                    PdfPCell cel6 = null;

                    if (null != GetData(tabla, i, 2)) {
                        cel1 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 2).toString()));

//                            System.out.println("tem valor");
                    }
                    if (cel1 == null) {
                        cel1 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                    }
                    cel1.setBorder(0);
                    cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel1.getColumn().updateFilledWidth(3);
                    tb.addCell(cel1);
                    if (null != GetData(tabla, i, 3)) {
                        cel2 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 3).toString()));

//                            System.out.println("tem valor");
                    }
                    if (cel2 == null) {
                        cel2 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                    }
                    cel2.setBorder(0);
                    cel2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel2.getColumn().updateFilledWidth(3);
                    tb.addCell(cel2);
                    if (null != GetData(tabla, i, 7)) {
                        cel3 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 7).toString()));

//                            System.out.println("tem valor");
                    }
                    if (cel3 == null) {
                        cel3 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                    }
                    cel3.setBorder(0);
                    cel3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel3.getColumn().updateFilledWidth(3);
                    tb.addCell(cel3);

                    if (null != GetData(tabla, i, 4)) {
                        cel4 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 4).toString()));

//                            System.out.println("tem valor");
                    }
                    if (cel4 == null) {
                        cel4 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                    }
                    cel4.setBorder(0);
                    cel4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel4.getColumn().updateFilledWidth(3);
                    tb.addCell(cel4);
                    if (null != GetData(tabla, i, 8)) {
                        cel5 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 8).toString()));

//                            System.out.println("tem valor");
                    }
                    if (cel5 == null) {
                        cel5 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                    }
                    cel5.setBorder(0);
                    cel5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel5.getColumn().updateFilledWidth(3);
                    tb.addCell(cel5);

                    if (null != GetData(tabla, i, 9)) {
                        cel6 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 9).toString()));

//                            System.out.println("tem valor");
                    }
                    if (cel6 == null) {
                        cel6 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                    }
                    cel6.setBorder(0);
                    cel6.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel6.getColumn().updateFilledWidth(3);
                    tb.addCell(cel6);

                }

                doc.add(tb);
//                
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
                doc.close();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }

        }
        String tb, coluna_tb;

        if (MF.getTitulo().equals("Fatura")) {
            tb = "fatura";
            coluna_tb = "idfactura";
        } else if (MF.getTitulo().equals("Proforma")) {
            tb = "proforma";
            coluna_tb = "idproforma";
        } else if (MF.getTitulo().equals("Orçamento")) {
            tb = "orcamento";
            coluna_tb = "idorcamento";
        } //                    if(MF.getTitulo().equals("Fatura recibo"))
        else {
            tb = "recibo";
            coluna_tb = "idfatura_recibo";
        }
        this.ToByte(this.file, tb, coluna_tb);

    }

    public Image revert() {
        try {
            ControloBD liga = new ControloBD();
            liga.setCaminho(this.caminho);
            liga.conexao();
            liga.executeSql("select foto from empresa");
            if (liga.rs.first()) {
                try {
                    return Image.getInstance(liga.rs.getBytes(1), true);
                } catch (IOException ex) {
                    Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadElementException ex) {
                    Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            liga.deconecta();

        } catch (SQLException ex) {
            Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; 
    }

    public void exportA42(String Tamanho, int num) throws IOException {
//         this.Segunda_via = new ModeloPDF_2_via(file, tabla, "Documento", caminho);
//                    this.Segunda_via.setME(ME);
//                   this.Segunda_via.setMC(MC);
//                    this.Segunda_via.setMU(MU);
//                    this.Segunda_via.setMF(MF);
//                    this.Segunda_via.setFormasDePagamento(FormasDePagamento);
//                    
//        this.Segunda_via.exportA42(Tamanho, num);
//        System.out.println("");
//        File file_2 = file;
        Document doc = null;
        OutputStream out = null;
        StampPageXofY stampPageXofY = null;
        if (Tamanho.equals("A4")) {
            try {
                doc = new Document(PageSize.A4, 30, 20, 50, 50);
//             StampPageXofY()
                try {
                    DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                    out = outt;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
                try {
                    PdfWriter.getInstance(doc, out);
                    doc.open();
//                Adicionar o logotipo da empresa
                    Image IMG = Image.getInstance(revert());
                    IMG.scaleToFit(100, 120);
                    IMG.setAbsolutePosition(90, 720);
//                doc.add(IMG);

                    BaseColor cor = new BaseColor(47, 117, 181);
                    Paragraph P1Fatura = new Paragraph();
                    P1Fatura.setFont(new Font(Font.FontFamily.HELVETICA, 20, 0, cor));
                    P1Fatura.setAlignment(2);
//                P1Fatura.setSpacingAfter(40);
                    P1Fatura.add(" ");
//                doc.add(P1Fatura);
                    PdfPTable tb = new PdfPTable(2);

                    PdfPCell celIMG = new PdfPCell();
                    celIMG.setBorder(0);
                    celIMG.addElement(IMG);
                    tb.addCell(celIMG);

                    PdfPCell celf = new PdfPCell(P1Fatura);
                    celf.setBorder(0);
                    tb.addCell(celf);

                    tb.setSpacingBefore(30);
                    Paragraph P1 = new Paragraph();
                    Paragraph P2 = new Paragraph();
                    Paragraph P3 = new Paragraph();
                    Paragraph P4 = new Paragraph();

                    P1.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P2.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P3.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P4.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P1.add(ME.getDesignacao());
                    P2.add("Telefone: " + Integer.toString(ME.telefone));
                    P3.add(ME.pais + ", " + ME.getProvincia() + ", " + ME.rua + "");
                    P4.add("NIF: " + ME.getNif());

                    PdfPCell cel1 = new PdfPCell();
                    cel1.addElement(P1);
                    cel1.addElement(P2);
                    cel1.addElement(P3);
                    cel1.addElement(P4);
                    cel1.setBorder(0);

//                tb.setWidthPercentage(200);
                    tb.addCell(cel1);

                    Paragraph P5 = new Paragraph();
                    Paragraph P6 = new Paragraph();
                    Paragraph P7 = new Paragraph();
                    Paragraph P8 = new Paragraph();

                    P5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                    P6.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                    P7.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    P8.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

                    P5.add(this.MF.getTitulo() + " Nº");
                    P6.add("  Data");
                    String NUM = "0";
                    if (this.MF.getIdfactra() > 9) {
                        NUM = Integer.toString(this.MF.getIdfactra());
                    } else {
                        NUM = NUM + Integer.toString(this.MF.getIdfactra());
                    }
                    String NumDoc = this.ME.getIndicativo() + " " + this.ME.getSerie() + NUM;
                    P7.add(NumDoc + "\n\nSEGUNDA VIA");
                    P8.add(tempo.Date() + " " + tempo.Hours() + "\n\nVALIDADE: " + tempo.NextDay(num));

                    PdfPTable TBin = new PdfPTable(2);
                    PdfPCell cel3 = new PdfPCell();
                    PdfPCell cel4 = new PdfPCell();
                    PdfPCell cel5 = new PdfPCell(P7);
                    PdfPCell cel6 = new PdfPCell(P8);

                    cel3.setBorder(0);
                    cel4.setBorder(0);
                    cel5.setBorder(0);
                    cel6.setBorder(0);

                    cel3.setBackgroundColor(cor);
                    cel3.addElement(P5);

                    cel4.setBackgroundColor(cor);
                    cel4.addElement(P6);

                    TBin.addCell(cel3);
                    TBin.addCell(cel4);
                    TBin.addCell(cel5);
                    TBin.addCell(cel6);

                    PdfPCell celT = new PdfPCell(TBin);
                    celT.setBorder(0);
                    tb.addCell(celT);

                    PdfPTable TBin2 = new PdfPTable(1);
                    PdfPTable TBin3 = new PdfPTable(1);

                    Paragraph P9 = new Paragraph();
                    Paragraph P10 = new Paragraph();
                    Paragraph P11 = new Paragraph();
                    Paragraph P12 = new Paragraph();

                    P9.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                    P10.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                    P11.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    P12.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

                    P9.add("Cliente");
                    P10.add("  NIF");
                    P12.add("  " + MC.getNif());
                    Paragraph dc1 = new Paragraph();
                    Paragraph dc2 = new Paragraph();
                    Paragraph dc3 = new Paragraph();
                    Paragraph dc4 = new Paragraph();
                    Paragraph dc5 = new Paragraph();
                    dc1.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc2.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc3.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc4.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc1.add(MC.getDesignacao());
                    dc2.add(MC.getEndereco());
                    dc3.add(MC.getCidade());
                    dc4.add(MC.getTelefone());
                    dc5.add(MC.getEmail());

                    PdfPCell cel7 = new PdfPCell();
                    PdfPCell cel8 = new PdfPCell();
                    PdfPCell cel9 = new PdfPCell();
                    cel9.addElement(dc1);
                    cel9.addElement(dc2);
                    cel9.addElement(dc3);
                    cel9.addElement(dc4);
                    cel9.addElement(dc5);
                    PdfPCell cel10 = new PdfPCell(P12);

                    cel7.setBorder(0);
                    cel8.setBorder(0);
                    cel9.setBorder(0);
                    cel10.setBorder(0);

                    cel7.setBackgroundColor(cor);
                    cel7.addElement(P9);

                    cel8.setBackgroundColor(cor);
                    cel8.addElement(P10);

                    TBin2.addCell(cel7);
                    TBin3.addCell(cel8);
                    TBin2.addCell(cel9);
                    TBin3.addCell(cel10);
                    PdfPCell celT2 = new PdfPCell(TBin2);
                    celT2.setBorder(0);
                    PdfPCell celT3 = new PdfPCell(TBin3);
                    celT3.setBorder(0);
                    PdfPCell space = new PdfPCell(new Paragraph(" "));
                    space.setBorder(0);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(celT2);
                    tb.addCell(celT3);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(space);
                    doc.add(tb);

//             SECOND HALF 
                    PdfPTable tbMaterial = new PdfPTable(6);

                    PdfPCell cab3 = new PdfPCell(new Paragraph("Descrição", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab3.setBackgroundColor(cor);
                    cab3.setBorder(0);

                    PdfPCell cab4 = new PdfPCell(new Paragraph("Preço Unit." + "(KZ)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab4.setBackgroundColor(cor);
                    cab4.setBorder(0);

                    PdfPCell cab5 = new PdfPCell(new Paragraph("Quant.", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab5.setBackgroundColor(cor);
                    cab5.setBorder(0);

                    PdfPCell cabVA = new PdfPCell(new Paragraph("IVA. (%)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cabVA.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cabVA.setBackgroundColor(cor);
                    cabVA.setBorder(0);

                    PdfPCell cab6 = new PdfPCell(new Paragraph("Desc.(KZ)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab6.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab6.setBackgroundColor(cor);
                    cab6.setBorder(0);
                    PdfPCell cab7 = new PdfPCell(new Paragraph("Total (KZ)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab7.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab7.setBackgroundColor(cor);
                    cab7.setBorder(0);

                    tbMaterial.addCell(cab3);
                    tbMaterial.addCell(cab4);
                    tbMaterial.addCell(cab5);
                    tbMaterial.addCell(cabVA);
                    tbMaterial.addCell(cab6);
                    tbMaterial.addCell(cab7);
                    Document docAux;
                    File Auxe;
//                    doc.close();

                    int atualpg = 1;
                    Font fonte = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
                    for (int i = 0; i < this.tabla.getRowCount(); i++) {

                        PdfPCell cel11 = null;
                        PdfPCell cel21 = null;
                        PdfPCell cel31 = null;
                        PdfPCell cel41 = null;
                        PdfPCell cel51 = null;
                        PdfPCell cel61 = null;
                        
                        if (null != GetData(tabla, i, 2)) {
                            cel11 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 2).toString(), fonte));
//                            System.out.println("Descrição" );
                        }
                        if (cel11 == null) {
                            cel11 = new PdfPCell(new Paragraph());

//                            System.out.println("Descrição");
                        }
                        cel11.setBorder(0);
                        cel11.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel11.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel11);

                        if (null != GetData(tabla, i, 3)) {
                            cel21 = new PdfPCell(new Paragraph(Chang(this.tabla.getModel().getValueAt(i, 3).toString()), fonte));

//                            System.out.println("Preço");
                        }
                        if (cel21 == null) {
                            cel21 = new PdfPCell(new Paragraph());

//                            System.out.println("Preço");
                        }
                        cel21.setBorder(0);
                        cel21.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel21.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel21);

                        if (null != GetData(tabla, i, 7)) {
                            cel31 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 7).toString(), fonte));

//                            System.out.println("QNT");
                        }
                        if (cel31 == null) {
                            cel31 = new PdfPCell(new Paragraph());

//                            System.out.println("QNT");
                        }
                        cel31.setBorder(0);
                        cel31.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel31.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel31);

                        if (null != GetData(tabla, i, 4)) {
                            String iv= this.tabla.getModel().getValueAt(i, 4).toString();
                            cel41 = new PdfPCell();
                            cel41.addElement(new Paragraph(iv, fonte));
                            if (iv.equals("ISENTO")) {
                            String motivo_de_isencao= this.tabla.getModel().getValueAt(i, 5).toString();
                            cel41.addElement(new Paragraph(motivo_de_isencao, new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                        }
//                            System.out.println("tem valor");
                        }
                        if (cel41 == null) {
                            cel41 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                        }
                        cel41.setBorder(0);
                        cel41.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel41.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel41);

                        if (null != GetData(tabla, i, 8)) {
                            cel51 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 8).toString(), fonte));

//                            System.out.println("tem valor 5");
                        }
                        if (cel51 == null) {
                            cel51 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 5");
                        }
                        cel51.setBorder(0);
                        cel51.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel51.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel51);

                        if (null != GetData(tabla, i, 9)) {
                            cel61 = new PdfPCell(new Paragraph(Chang(this.tabla.getModel().getValueAt(i, 9).toString()), fonte));

//                            System.out.println("tem valor");
                        }
                        if (cel61 == null) {
                            cel61 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                        }
                        cel61.setBorder(0);
                        cel61.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel61.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel61);
                        //System.out.println("O item "+i+" está na pg "+atualpg);
                    }
                    doc.add(tbMaterial);

                    //            THIRD HALF 
                    PdfPTable tbResumo = new PdfPTable(3);
                    PdfPCell side1;
//                        PdfPCell side1 = new PdfPCell(new Paragraph(ME.getRegime_de_iva() + "\nBens colocados à disposição do cliente a " + tempo.Date() + " " + tempo.Hours() + " no " + MF.getLocalizacao_armazem() + "-" + MF.getArmazem() + "\n", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));

                    ControloBD liga = new ControloBD();
                    liga.setCaminho(this.caminho);
                    liga.conexao();
                    Paragraph paragraph = new Paragraph();
                    exten.setMeoda("Kwanza");
                    if (MF.getTitulo().equals("Proforma") || MF.getTitulo().equals("Orçamento")) {
//                        PdfPCell side1 = new PdfPCell();
                        paragraph.add(new Paragraph(ME.getRegime_de_iva() + "\nEste documento não contitui uma fatura." + "\nOperador(a)  " + MU.getNome() + "\n", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));

                        liga.executeSql("select abreviacao, ibam from conta order by nome_banco");

                        try {
                            if (liga.rs.first()) {
                                do {
                                    paragraph.add(new Paragraph("Coordenadas bancárias:\n\n" + liga.rs.getString("abreviacao") + ": " + liga.rs.getString("ibam") + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                                } while (liga.rs.next());
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else if (MF.getTitulo().equals("Fatura")) {
                        paragraph.add(new Paragraph(ME.getRegime_de_iva() + "\nBens colocados à disposição do cliente a " + tempo.Date() + " " + tempo.Hours() + " no " + MF.getLocalizacao_armazem() + "-" + MF.getArmazem() + "\n", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                        paragraph.add(new Paragraph("Operador(a)  " + MU.getNome() + "\n", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));

                        liga.executeSql("select abreviacao, ibam from conta order by nome_banco");

                        try {
                            if (liga.rs.first()) {
                                do {
                                    paragraph.add(new Paragraph("Coordenadas bancárias:\n\n" + liga.rs.getString("abreviacao") + ": " + liga.rs.getString("ibam") + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));

                                } while (liga.rs.next());
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (MF.getTitulo().equals("Fatura recibo")) {
                        this.exten.setNumber(MF.getPagamento());
//                        P.add( "\nProcessado pelo programa certificado Cramer Nº XX/AGT/YYYY.\nOperador(a) " + MU.getNome() + ".");

                        paragraph.add(new Paragraph("Valor pago:" + Chang(MF.getPagamento()) + " " + exten.getMeoda() + "(s)" + "\nValor por extenso: " + exten.show() + "\nTroco: " + Chang(MF.getTroco()) + " " + exten.getMeoda() + "\n" + ME.getRegime_de_iva() + "\nBens colocados à disposição do cliente a " + tempo.Date() + " " + tempo.Hours() + " no " + MF.getLocalizacao_armazem() + "-" + MF.getArmazem() + "\n", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                        paragraph.add(new Paragraph("Operador(a)  " + MU.getNome() + "\n", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));

                        liga.executeSql("select abreviacao, ibam, numero_da_conta from conta order by nome_banco");

                        {
                            paragraph.add(new Paragraph(this.FormasDePagamento.get(0) + this.FormasDePagamento.get(1) + " " + this.FormasDePagamento.get(2) + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                        }

                    }
                    side1 = new PdfPCell(paragraph);

                    //fim
                    side1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side1.setBackgroundColor(BaseColor.WHITE);
                    side1.setBorder(0);
                    side1.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side1);

                    PdfPCell side2 = new PdfPCell(new Paragraph("Desconto", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side2.setBackgroundColor(new BaseColor(189, 215, 238));
                    side2.setBorder(0);
                    side2.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side2);
                    PdfPCell side3 = new PdfPCell(new Paragraph(Chang(this.MF.getDesconto()) + " " + MF.getModeda() + "(s)", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side3.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side3.setBackgroundColor(new BaseColor(221, 235, 247));
                    side3.setBorder(0);
                    side3.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side3);
                    PdfPCell side12 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                    side12.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side12.setBackgroundColor(BaseColor.WHITE);
                    side12.setBorder(0);
                    side12.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side12);
                    PdfPCell side22 = new PdfPCell(new Paragraph("Taxa do IVA", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side22.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side22.setBackgroundColor(new BaseColor(189, 215, 238));
                    side22.setBorder(0);
                    side22.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side22);
                    PdfPCell side32 = new PdfPCell(new Paragraph(Double.toString(this.MF.getTaxa()) + "%", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side32.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side32.setBackgroundColor(new BaseColor(221, 235, 247));
                    side32.setBorder(0);
                    side32.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side32);
                    PdfPCell side13 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                    side13.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side13.setBackgroundColor(BaseColor.WHITE);
                    side13.setBorder(0);
                    side13.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side13);
                    PdfPCell side23 = new PdfPCell(new Paragraph("Imposto", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side23.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side23.setBackgroundColor(new BaseColor(189, 215, 238));
                    side23.setBorder(0);
                    side23.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side23);
                    this.exten.setMeoda("Kwanza");
                    PdfPCell side33 = new PdfPCell(new Paragraph(Chang(this.MF.getTotal_doIVA()) + exten.getMeoda() + "(s)\n", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side33.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side33.setBackgroundColor(new BaseColor(221, 235, 247));
                    side33.setBorder(0);
                    side33.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side33);
                    PdfPCell side14 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                    side14.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side14.setBackgroundColor(BaseColor.WHITE);
                    side14.setBorder(0);
                    side14.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side14);

                    side23 = new PdfPCell(new Paragraph("Retenção na Fonte", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side23.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side23.setBackgroundColor(new BaseColor(189, 215, 238));
                    side23.setBorder(0);
                    side23.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side23);
                    this.exten.setMeoda("Kwanza");
                    side33 = new PdfPCell(new Paragraph(Chang(this.MF.getRetencao_na_fonte()) + exten.getMeoda() + "(s)\n", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side33.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side33.setBackgroundColor(new BaseColor(221, 235, 247));
                    side33.setBorder(0);
                    side33.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side33);
                    side14 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                    side14.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side14.setBackgroundColor(BaseColor.WHITE);
                    side14.setBorder(0);
                    side14.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side14);

                    PdfPCell side24 = new PdfPCell(new Paragraph("Total", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLDITALIC, cor)));
                    side24.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side24.setBackgroundColor(new BaseColor(189, 215, 238));
                    side24.setBorder(0);
                    side24.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side24);
                    exten.setNumber(MF.getTotal());
                    this.exten.setMeoda("Kwanza");
                    PdfPCell side34 = new PdfPCell(new Paragraph(Chang(MF.getTotal()) + exten.getMeoda() + "(s)\n" + exten.show() + " ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLDITALIC, BaseColor.BLACK)));
                    side34.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side34.setBackgroundColor(new BaseColor(221, 235, 247));
                    side34.setBorder(0);
                    side34.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side34);

                    doc.add(tbResumo);
                    PdfPTable tbCoordenadas = new PdfPTable(2);
                    tbCoordenadas.setHorizontalAlignment(1);
                    tbCoordenadas.setSpacingBefore(100);
                    Paragraph P = new Paragraph();
                    P.setFont(new Font(Font.FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK));
                    this.exten.setMeoda("Kwanza");

//                    
//
                    doc.add(tbCoordenadas);
                    doc.addAuthor(MU.getNome());

                    stampPageXofY = new StampPageXofY();
                    stampPageXofY.setEstado(this.estadoLicenca);
                    stampPageXofY.setNumDoc(NumDoc);
                    stampPageXofY.setTotal(Chang(MF.getTotal()) + exten.getMeoda() + "(s)");
                } catch (DocumentException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }

            } finally {
                if (doc != null) {
//                    try {
                    doc.close();

                    String SRC = this.file.getAbsolutePath().toString();
                    String nome = this.file.getAbsolutePath().toString();
                    String DEST = this.file.getParent() + "/_" + this.file.getName();

                    File file = new File(DEST);
                    file.getParentFile().mkdirs();
                    try {
                        stampPageXofY.manipulatePdf(SRC, DEST);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.file.delete();
                    file.renameTo(new File(nome));
//                    Desktop.getDesktop().open(new File(nome));
                       
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Erro" + ex);
                    }
                }

            }
        } else if (Tamanho.equals("A5")) {
            try {
                doc = new Document(PageSize.A4, 30, 20, 20, 30);

                try {
                    DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                    out = outt;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
                try {
                    PdfWriter.getInstance(doc, out);
                    doc.open();
//                Adicionar o logotipo da empresa
                    Image IMG = Image.getInstance(revert());
                    IMG.scaleToFit(100, 120);
                    IMG.setAbsolutePosition(90, 720);
//                doc.add(IMG);

                    BaseColor cor = new BaseColor(47, 117, 181);
                    Paragraph P1Fatura = new Paragraph();
                    P1Fatura.setFont(new Font(Font.FontFamily.HELVETICA, 20, 0, cor));
                    P1Fatura.setAlignment(2);
//                P1Fatura.setSpacingAfter(40);
                    P1Fatura.add(" ");
//                doc.add(P1Fatura);
                    PdfPTable tb = new PdfPTable(2);

                    PdfPCell celIMG = new PdfPCell();
                    celIMG.setBorder(0);
                    celIMG.addElement(IMG);
                    tb.addCell(celIMG);

                    PdfPCell celf = new PdfPCell(P1Fatura);
                    celf.setBorder(0);
                    tb.addCell(celf);

                    tb.setSpacingBefore(30);
                    Paragraph P1 = new Paragraph();
                    Paragraph P2 = new Paragraph();
                    Paragraph P3 = new Paragraph();
                    Paragraph P4 = new Paragraph();

                    P1.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P2.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P3.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P4.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P1.add(ME.getDesignacao());
                    P2.add("Telefone: " + Integer.toString(ME.telefone));
                    P3.add(ME.pais + ", " + ME.getProvincia() + ", " + ME.rua + "");
                    P4.add("NIF: " + ME.getNif());

                    PdfPCell cel1 = new PdfPCell();
                    cel1.addElement(P1);
                    cel1.addElement(P2);
                    cel1.addElement(P3);
                    cel1.addElement(P4);
                    cel1.setBorder(0);

//                tb.setWidthPercentage(200);
                    tb.addCell(cel1);

                    Paragraph P5 = new Paragraph();
                    Paragraph P6 = new Paragraph();
                    Paragraph P7 = new Paragraph();
                    Paragraph P8 = new Paragraph();

                    P5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                    P6.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                    P7.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    P8.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

                    P5.add(this.MF.getTitulo() + " Nº");
                    P6.add("  Data");
                    String NUM = "0";
                    if (this.MF.getIdfactra() > 9) {
                        NUM = Integer.toString(this.MF.getIdfactra());
                    } else {
                        NUM = NUM + Integer.toString(this.MF.getIdfactra());
                    }

                    P7.add(this.ME.getIndicativo() + " " + this.ME.getSerie() + NUM + "\n\nSEGUNDA VIA");
                    P8.add(tempo.Date() + " " + tempo.Hours() + "\n\nVALIDADE: " + tempo.NextDay(num));

                    PdfPTable TBin = new PdfPTable(2);
                    PdfPCell cel3 = new PdfPCell();
                    PdfPCell cel4 = new PdfPCell();
                    PdfPCell cel5 = new PdfPCell(P7);
                    PdfPCell cel6 = new PdfPCell(P8);

                    cel3.setBorder(0);
                    cel4.setBorder(0);
                    cel5.setBorder(0);
                    cel6.setBorder(0);

                    cel3.setBackgroundColor(cor);
                    cel3.addElement(P5);

                    cel4.setBackgroundColor(cor);
                    cel4.addElement(P6);

                    TBin.addCell(cel3);
                    TBin.addCell(cel4);
                    TBin.addCell(cel5);
                    TBin.addCell(cel6);

                    PdfPCell celT = new PdfPCell(TBin);
                    celT.setBorder(0);
                    tb.addCell(celT);

                    PdfPTable TBin2 = new PdfPTable(1);
                    PdfPTable TBin3 = new PdfPTable(1);

                    Paragraph P9 = new Paragraph();
                    Paragraph P10 = new Paragraph();
                    Paragraph P11 = new Paragraph();
                    Paragraph P12 = new Paragraph();

                    P9.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                    P10.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                    P11.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    P12.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

                    P9.add("Cliente");
                    P10.add("  NIF");
                    P12.add("  " + MC.getNif());
                    Paragraph dc1 = new Paragraph();
                    Paragraph dc2 = new Paragraph();
                    Paragraph dc3 = new Paragraph();
                    Paragraph dc4 = new Paragraph();
                    Paragraph dc5 = new Paragraph();
                    dc1.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc2.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc3.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc4.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc1.add(MC.getDesignacao());
                    dc2.add(MC.getEndereco());
                    dc3.add(MC.getCidade());
                    dc4.add(MC.getTelefone());
                    dc5.add(MC.getEmail());

                    PdfPCell cel7 = new PdfPCell();
                    PdfPCell cel8 = new PdfPCell();
                    PdfPCell cel9 = new PdfPCell();
                    cel9.addElement(dc1);
                    cel9.addElement(dc2);
                    cel9.addElement(dc3);
                    cel9.addElement(dc4);
                    cel9.addElement(dc5);
                    PdfPCell cel10 = new PdfPCell(P12);

                    cel7.setBorder(0);
                    cel8.setBorder(0);
                    cel9.setBorder(0);
                    cel10.setBorder(0);

                    cel7.setBackgroundColor(cor);
                    cel7.addElement(P9);

                    cel8.setBackgroundColor(cor);
                    cel8.addElement(P10);

                    TBin2.addCell(cel7);
                    TBin3.addCell(cel8);
                    TBin2.addCell(cel9);
                    TBin3.addCell(cel10);
                    PdfPCell celT2 = new PdfPCell(TBin2);
                    celT2.setBorder(0);
                    PdfPCell celT3 = new PdfPCell(TBin3);
                    celT3.setBorder(0);
                    PdfPCell space = new PdfPCell(new Paragraph(" "));
                    space.setBorder(0);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(celT2);
                    tb.addCell(celT3);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(space);
                    doc.add(tb);

//             SECOND HALF 
                    PdfPTable tbMaterial = new PdfPTable(6);

                    PdfPCell cab3 = new PdfPCell(new Paragraph("Descrição", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab3.setBackgroundColor(cor);
                    cab3.setBorder(0);

                    PdfPCell cab4 = new PdfPCell(new Paragraph("Preço Unit." + "(KZ)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab4.setBackgroundColor(cor);
                    cab4.setBorder(0);

                    PdfPCell cab5 = new PdfPCell(new Paragraph("Quant.", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab5.setBackgroundColor(cor);
                    cab5.setBorder(0);

                    PdfPCell cabVA = new PdfPCell(new Paragraph("IVA. (%)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cabVA.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cabVA.setBackgroundColor(cor);
                    cabVA.setBorder(0);

                    PdfPCell cab6 = new PdfPCell(new Paragraph("Desc.(KZ)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab6.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab6.setBackgroundColor(cor);
                    cab6.setBorder(0);
                    PdfPCell cab7 = new PdfPCell(new Paragraph("Total (KZ)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab7.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab7.setBackgroundColor(cor);
                    cab7.setBorder(0);

                    tbMaterial.addCell(cab3);
                    tbMaterial.addCell(cab4);
                    tbMaterial.addCell(cab5);
                    tbMaterial.addCell(cabVA);
                    tbMaterial.addCell(cab6);
                    tbMaterial.addCell(cab7);

                    for (int i = 0; i < this.tabla.getRowCount(); i++) {

                        PdfPCell cel11 = null;
                        PdfPCell cel21 = null;
                        PdfPCell cel31 = null;
                        PdfPCell cel41 = null;
                        PdfPCell cel51 = null;
                        PdfPCell cel61 = null;

                        if (null != GetData(tabla, i, 2)) {
                            cel11 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 2).toString()));

//                            System.out.println("tem valor 1" );
                        }
                        if (cel11 == null) {
                            cel11 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 1");
                        }
                        cel11.setBorder(0);
                        cel11.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel11.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel11);

                        if (null != GetData(tabla, i, 3)) {
                            cel21 = new PdfPCell(new Paragraph(Chang(this.tabla.getModel().getValueAt(i, 3).toString())));

//                            System.out.println("tem valor 2");
                        }
                        if (cel21 == null) {
                            cel21 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 2");
                        }
                        cel21.setBorder(0);
                        cel21.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel21.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel21);

                        if (null != GetData(tabla, i, 7)) {
                            cel31 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 7).toString()));

//                            System.out.println("tem valor 3");
                        }
                        if (cel31 == null) {
                            cel31 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 3");
                        }
                        cel31.setBorder(0);
                        cel31.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel31.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel31);

                        if (null != GetData(tabla, i, 4)) {
                            cel41 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 4).toString()));

//                            System.out.println("tem valor");
                        }
                        if (cel41 == null) {
                            cel41 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                        }
                        cel41.setBorder(0);
                        cel41.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel41.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel41);

                        if (null != GetData(tabla, i, 8)) {
                            cel51 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 8).toString()));

//                            System.out.println("tem valor 5");
                        }
                        if (cel51 == null) {
                            cel51 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 5");
                        }
                        cel51.setBorder(0);
                        cel51.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel51.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel51);

                        if (null != GetData(tabla, i, 9)) {
                            cel61 = new PdfPCell(new Paragraph(Chang(this.tabla.getModel().getValueAt(i, 9).toString())));

//                            System.out.println("tem valor");
                        }
                        if (cel61 == null) {
                            cel61 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                        }
                        cel61.setBorder(0);
                        cel61.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel61.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel61);
                    }
                    doc.add(tbMaterial);

                    //            THIRD HALF 
                    PdfPTable tbResumo = new PdfPTable(3);
                    PdfPCell side1 = new PdfPCell(new Paragraph("Agradecemos...", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                    side1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side1.setBackgroundColor(BaseColor.WHITE);
                    side1.setBorder(0);
                    side1.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side1);

                    PdfPCell side2 = new PdfPCell(new Paragraph("Desconto", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side2.setBackgroundColor(new BaseColor(189, 215, 238));
                    side2.setBorder(0);
                    side2.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side2);
                    PdfPCell side3 = new PdfPCell(new Paragraph(Chang(this.MF.getDesconto()) + " " + MF.getModeda() + "(s)", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side3.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side3.setBackgroundColor(new BaseColor(221, 235, 247));
                    side3.setBorder(0);
                    side3.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side3);
                    PdfPCell side12 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                    side12.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side12.setBackgroundColor(BaseColor.WHITE);
                    side12.setBorder(0);
                    side12.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side12);
                    PdfPCell side22 = new PdfPCell(new Paragraph("Taxa do IVA", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side22.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side22.setBackgroundColor(new BaseColor(189, 215, 238));
                    side22.setBorder(0);
                    side22.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side22);
                    PdfPCell side32 = new PdfPCell(new Paragraph(Double.toString(this.MF.getTaxa()) + "%", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side32.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side32.setBackgroundColor(new BaseColor(221, 235, 247));
                    side32.setBorder(0);
                    side32.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side32);
                    PdfPCell side13 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                    side13.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side13.setBackgroundColor(BaseColor.WHITE);
                    side13.setBorder(0);
                    side13.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side13);
                    PdfPCell side23 = new PdfPCell(new Paragraph("Imposto", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side23.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side23.setBackgroundColor(new BaseColor(189, 215, 238));
                    side23.setBorder(0);
                    side23.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side23);
                    this.exten.setMeoda("Kwanza");
                    PdfPCell side33 = new PdfPCell(new Paragraph(Chang(this.MF.getTotal_doIVA()) + exten.getMeoda() + "(s)\n", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side33.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side33.setBackgroundColor(new BaseColor(221, 235, 247));
                    side33.setBorder(0);
                    side33.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side33);
                    PdfPCell side14 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                    side14.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side14.setBackgroundColor(BaseColor.WHITE);
                    side14.setBorder(0);
                    side14.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side14);

                    side23 = new PdfPCell(new Paragraph("Retenção na Fonte", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side23.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side23.setBackgroundColor(new BaseColor(189, 215, 238));
                    side23.setBorder(0);
                    side23.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side23);
                    this.exten.setMeoda("Kwanza");
                    side33 = new PdfPCell(new Paragraph(Chang(this.MF.getRetencao_na_fonte()) + exten.getMeoda() + "(s)\n", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side33.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side33.setBackgroundColor(new BaseColor(221, 235, 247));
                    side33.setBorder(0);
                    side33.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side33);
                    side14 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                    side14.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side14.setBackgroundColor(BaseColor.WHITE);
                    side14.setBorder(0);
                    side14.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side14);

                    PdfPCell side24 = new PdfPCell(new Paragraph("Total", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLDITALIC, cor)));
                    side24.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side24.setBackgroundColor(new BaseColor(189, 215, 238));
                    side24.setBorder(0);
                    side24.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side24);
                    exten.setNumber(MF.getTotal());
                    this.exten.setMeoda("Kwanza");
                    PdfPCell side34 = new PdfPCell(new Paragraph(Chang(MF.getTotal()) + exten.getMeoda() + "(s)\n" + exten.show() + " ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLDITALIC, BaseColor.BLACK)));
                    side34.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side34.setBackgroundColor(new BaseColor(221, 235, 247));
                    side34.setBorder(0);
                    side34.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side34);

                    doc.add(tbResumo);
                    PdfPTable tbCoordenadas = new PdfPTable(2);
                    tbCoordenadas.setHorizontalAlignment(1);
                    tbCoordenadas.setSpacingBefore(100);
                    Paragraph P = new Paragraph();
                    P.setFont(new Font(Font.FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK));
                    this.exten.setMeoda("Kwanza");

                    ControloBD liga = new ControloBD();
                    liga.setCaminho(this.caminho);
                    liga.conexao();
                    Paragraph Cord = new Paragraph();
                    ;

                    Cord.add(new Paragraph(ME.getRegime_de_iva() + "\nBens colocados à disposição do cliente a " + tempo.Date() + " " + tempo.Hours() + " no " + MF.getLocalizacao_armazem() + "-" + MF.getArmazem() + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));

                    if (MF.getTitulo().equals("Proforma") || MF.getTitulo().equals("Orçamento")) {
                        P.add("Este documento não contitui uma fatura. \n " + this.licenca + "\nOperador(a) " + MU.getNome() + ".");

                        liga.executeSql("select abreviacao, ibam from conta order by nome_banco");

                        try {
                            if (liga.rs.first()) {
                                do {
                                    Cord.add(new Paragraph("Coordenadas bancárias:\n\n" + liga.rs.getString("abreviacao") + ": " + liga.rs.getString("ibam") + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                                } while (liga.rs.next());
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else if (MF.getTitulo().equals("Fatura")) {
                        P.add(this.licenca + "\nOperador(a) " + MU.getNome() + ".");

                        liga.executeSql("select abreviacao, ibam from conta order by nome_banco");

                        try {
                            if (liga.rs.first()) {
                                do {
                                    Cord.add(new Paragraph("Coordenadas bancárias:\n\n" + liga.rs.getString("abreviacao") + ": " + liga.rs.getString("ibam") + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                                } while (liga.rs.next());
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (MF.getTitulo().equals("Fatura recibo")) {
                        this.exten.setNumber(MF.getPagamento());
                        P.add("Valor pago:" + Chang(MF.getPagamento()) + " " + exten.getMeoda() + "(s)" + "\nValor por extenso: " + exten.show() + "\nTroco: " + Chang(MF.getTroco()) + " " + exten.getMeoda() + "\n" + this.licenca + ".\nOperador(a) " + MU.getNome() + ".");

                        liga.executeSql("select abreviacao, ibam, numero_da_conta from conta order by nome_banco");

                        {
                            Cord.add(new Paragraph(this.FormasDePagamento.get(0) + this.FormasDePagamento.get(1) + " " + this.FormasDePagamento.get(2) + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                        }

                    }

                    tbCoordenadas.addCell(new PdfPCell(P));
                    tbCoordenadas.addCell(new PdfPCell(Cord));

                    doc.add(tbCoordenadas);
                    doc.addAuthor(MU.getNome());
                } catch (DocumentException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }

            } finally {
                if (doc != null) {
                    doc.close();
//                    Desktop.getDesktop().open(new File(this.file.getAbsolutePath().toString()));
//                Desktop.getDesktop().print(new File(this.file.getAbsolutePath().toString()));

                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Erro" + ex);
                    }
                }

            }
        } else if (Tamanho.equals("A6")) {
            try {
        doc = new Document(PageSize.A6, 30, 20, 20, 30);

                try {
                    DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                    out = outt;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
                try {
                    PdfWriter.getInstance(doc, out);
                    doc.open();
//                Adicionar o logotipo da empresa
                    Image IMG = Image.getInstance(revert());
                    IMG.scaleToFit(100, 120);
                    IMG.setAbsolutePosition(90, 720);
//                doc.add(IMG);

                    BaseColor cor = new BaseColor(47, 117, 181);
                    Paragraph P1Fatura = new Paragraph();
                    P1Fatura.setFont(new Font(Font.FontFamily.HELVETICA, 20, 0, cor));
                    P1Fatura.setAlignment(2);
//                P1Fatura.setSpacingAfter(40);
                    P1Fatura.add(" ");
//                doc.add(P1Fatura);
                    PdfPTable tb = new PdfPTable(2);

                    PdfPCell celIMG = new PdfPCell();
                    celIMG.setBorder(0);
                    celIMG.addElement(IMG);
                    tb.addCell(celIMG);

                    PdfPCell celf = new PdfPCell(P1Fatura);
                    celf.setBorder(0);
                    tb.addCell(celf);

                    tb.setSpacingBefore(30);
                    Paragraph P1 = new Paragraph();
                    Paragraph P2 = new Paragraph();
                    Paragraph P3 = new Paragraph();
                    Paragraph P4 = new Paragraph();

                    P1.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P2.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P3.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P4.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P1.add(ME.getDesignacao());
                    P2.add("Telefone: " + Integer.toString(ME.telefone));
                    P3.add(ME.pais + ", " + ME.getProvincia() + ", " + ME.rua + "");
                    P4.add("NIF: " + ME.getNif());

                    PdfPCell cel1 = new PdfPCell();
                    cel1.addElement(P1);
                    cel1.addElement(P2);
                    cel1.addElement(P3);
                    cel1.addElement(P4);
                    cel1.setBorder(0);

//                tb.setWidthPercentage(200);
                    tb.addCell(cel1);

                    Paragraph P5 = new Paragraph();
                    Paragraph P6 = new Paragraph();
                    Paragraph P7 = new Paragraph();
                    Paragraph P8 = new Paragraph();

                    P5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                    P6.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                    P7.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    P8.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

                    P5.add(this.MF.getTitulo() + " Nº");
                    P6.add("  Data");
                    String NUM = "0";
                    if (this.MF.getIdfactra() > 9) {
                        NUM = Integer.toString(this.MF.getIdfactra());
                    } else {
                        NUM = NUM + Integer.toString(this.MF.getIdfactra());
                    }

                    P7.add(this.ME.getIndicativo() + " " + this.ME.getSerie() + NUM + "\n\nSEGUNDA VIA");
                    P8.add(tempo.Date() + " " + tempo.Hours() + "\n\nVALIDADE: " + tempo.NextDay(num));

                    PdfPTable TBin = new PdfPTable(2);
                    PdfPCell cel3 = new PdfPCell();
                    PdfPCell cel4 = new PdfPCell();
                    PdfPCell cel5 = new PdfPCell(P7);
                    PdfPCell cel6 = new PdfPCell(P8);

                    cel3.setBorder(0);
                    cel4.setBorder(0);
                    cel5.setBorder(0);
                    cel6.setBorder(0);

                    cel3.setBackgroundColor(cor);
                    cel3.addElement(P5);

                    cel4.setBackgroundColor(cor);
                    cel4.addElement(P6);

                    TBin.addCell(cel3);
                    TBin.addCell(cel4);
                    TBin.addCell(cel5);
                    TBin.addCell(cel6);

                    PdfPCell celT = new PdfPCell(TBin);
                    celT.setBorder(0);
                    tb.addCell(celT);

                    PdfPTable TBin2 = new PdfPTable(1);
                    PdfPTable TBin3 = new PdfPTable(1);

                    Paragraph P9 = new Paragraph();
                    Paragraph P10 = new Paragraph();
                    Paragraph P11 = new Paragraph();
                    Paragraph P12 = new Paragraph();

                    P9.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                    P10.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                    P11.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    P12.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

                    P9.add("Cliente");
                    P10.add("  NIF");
                    P12.add("  " + MC.getNif());
                    Paragraph dc1 = new Paragraph();
                    Paragraph dc2 = new Paragraph();
                    Paragraph dc3 = new Paragraph();
                    Paragraph dc4 = new Paragraph();
                    Paragraph dc5 = new Paragraph();
                    dc1.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc2.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc3.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc4.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc1.add(MC.getDesignacao());
                    dc2.add(MC.getEndereco());
                    dc3.add(MC.getCidade());
                    dc4.add(MC.getTelefone());
                    dc5.add(MC.getEmail());

                    PdfPCell cel7 = new PdfPCell();
                    PdfPCell cel8 = new PdfPCell();
                    PdfPCell cel9 = new PdfPCell();
                    cel9.addElement(dc1);
                    cel9.addElement(dc2);
                    cel9.addElement(dc3);
                    cel9.addElement(dc4);
                    cel9.addElement(dc5);
                    PdfPCell cel10 = new PdfPCell(P12);

                    cel7.setBorder(0);
                    cel8.setBorder(0);
                    cel9.setBorder(0);
                    cel10.setBorder(0);

                    cel7.setBackgroundColor(cor);
                    cel7.addElement(P9);

                    cel8.setBackgroundColor(cor);
                    cel8.addElement(P10);

                    TBin2.addCell(cel7);
                    TBin3.addCell(cel8);
                    TBin2.addCell(cel9);
                    TBin3.addCell(cel10);
                    PdfPCell celT2 = new PdfPCell(TBin2);
                    celT2.setBorder(0);
                    PdfPCell celT3 = new PdfPCell(TBin3);
                    celT3.setBorder(0);
                    PdfPCell space = new PdfPCell(new Paragraph(" "));
                    space.setBorder(0);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(celT2);
                    tb.addCell(celT3);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(space);
                    tb.addCell(space);
                    doc.add(tb);

//             SECOND HALF 
                    PdfPTable tbMaterial = new PdfPTable(6);

                    PdfPCell cab3 = new PdfPCell(new Paragraph("Descrição", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab3.setBackgroundColor(cor);
                    cab3.setBorder(0);

                    PdfPCell cab4 = new PdfPCell(new Paragraph("Preço Unit." + "(KZ)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab4.setBackgroundColor(cor);
                    cab4.setBorder(0);

                    PdfPCell cab5 = new PdfPCell(new Paragraph("Quant.", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab5.setBackgroundColor(cor);
                    cab5.setBorder(0);

                    PdfPCell cabVA = new PdfPCell(new Paragraph("IVA. (%)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cabVA.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cabVA.setBackgroundColor(cor);
                    cabVA.setBorder(0);

                    PdfPCell cab6 = new PdfPCell(new Paragraph("Desc.(KZ)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab6.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab6.setBackgroundColor(cor);
                    cab6.setBorder(0);
                    PdfPCell cab7 = new PdfPCell(new Paragraph("Total (KZ)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                    cab7.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab7.setBackgroundColor(cor);
                    cab7.setBorder(0);

                    tbMaterial.addCell(cab3);
                    tbMaterial.addCell(cab4);
                    tbMaterial.addCell(cab5);
                    tbMaterial.addCell(cabVA);
                    tbMaterial.addCell(cab6);
                    tbMaterial.addCell(cab7);

                    for (int i = 0; i < this.tabla.getRowCount(); i++) {

                        PdfPCell cel11 = null;
                        PdfPCell cel21 = null;
                        PdfPCell cel31 = null;
                        PdfPCell cel41 = null;
                        PdfPCell cel51 = null;
                        PdfPCell cel61 = null;

                        if (null != GetData(tabla, i, 2)) {
                            cel11 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 2).toString()));

//                            System.out.println("tem valor 1" );
                        }
                        if (cel11 == null) {
                            cel11 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 1");
                        }
                        cel11.setBorder(0);
                        cel11.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel11.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel11);

                        if (null != GetData(tabla, i, 3)) {
                            cel21 = new PdfPCell(new Paragraph(Chang(this.tabla.getModel().getValueAt(i, 3).toString())));

//                            System.out.println("tem valor 2");
                        }
                        if (cel21 == null) {
                            cel21 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 2");
                        }
                        cel21.setBorder(0);
                        cel21.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel21.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel21);

                        if (null != GetData(tabla, i, 7)) {
                            cel31 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 7).toString()));

//                            System.out.println("tem valor 3");
                        }
                        if (cel31 == null) {
                            cel31 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 3");
                        }
                        cel31.setBorder(0);
                        cel31.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel31.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel31);

                        if (null != GetData(tabla, i, 4)) {
                            cel41 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 4).toString()));

//                            System.out.println("tem valor");
                        }
                        if (cel41 == null) {
                            cel41 = new PdfPCell(new Paragraph());

                        }
                        cel41.setBorder(0);
                        cel41.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel41.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel41);

                        if (null != GetData(tabla, i, 8)) {
                            cel51 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 8).toString()));

//                            System.out.println("tem valor 5");
                        }
                        if (cel51 == null) {
                            cel51 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 5");
                        }
                        cel51.setBorder(0);
                        cel51.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel51.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel51);

                        if (null != GetData(tabla, i, 9)) {
                            cel61 = new PdfPCell(new Paragraph(Chang(this.tabla.getModel().getValueAt(i, 9).toString())));

//                            System.out.println("tem valor");
                        }
                        if (cel61 == null) {
                            cel61 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                        }
                        cel61.setBorder(0);
                        cel61.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel61.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel61);
                    }
                    doc.add(tbMaterial);

                    //            THIRD HALF 
                    PdfPTable tbResumo = new PdfPTable(3);
                    PdfPCell side1 = new PdfPCell(new Paragraph("Agradecemos...", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                    side1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side1.setBackgroundColor(BaseColor.WHITE);
                    side1.setBorder(0);
                    side1.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side1);

                    PdfPCell side2 = new PdfPCell(new Paragraph("Desconto", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side2.setBackgroundColor(new BaseColor(189, 215, 238));
                    side2.setBorder(0);
                    side2.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side2);
                    PdfPCell side3 = new PdfPCell(new Paragraph(Chang(this.MF.getDesconto()) + " " + MF.getModeda() + "(s)", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side3.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side3.setBackgroundColor(new BaseColor(221, 235, 247));
                    side3.setBorder(0);
                    side3.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side3);
                    PdfPCell side12 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                    side12.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side12.setBackgroundColor(BaseColor.WHITE);
                    side12.setBorder(0);
                    side12.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side12);
                    PdfPCell side22 = new PdfPCell(new Paragraph("Taxa do IVA", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side22.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side22.setBackgroundColor(new BaseColor(189, 215, 238));
                    side22.setBorder(0);
                    side22.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side22);
                    PdfPCell side32 = new PdfPCell(new Paragraph(Double.toString(this.MF.getTaxa()) + "%", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side32.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side32.setBackgroundColor(new BaseColor(221, 235, 247));
                    side32.setBorder(0);
                    side32.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side32);
                    PdfPCell side13 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                    side13.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side13.setBackgroundColor(BaseColor.WHITE);
                    side13.setBorder(0);
                    side13.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side13);
                    PdfPCell side23 = new PdfPCell(new Paragraph("Imposto", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side23.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side23.setBackgroundColor(new BaseColor(189, 215, 238));
                    side23.setBorder(0);
                    side23.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side23);
                    PdfPCell side33 = new PdfPCell(new Paragraph(Chang(this.MF.getTotal_doIVA()) + " " + MF.getModeda() + "(s)", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                    side33.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side33.setBackgroundColor(new BaseColor(221, 235, 247));
                    side33.setBorder(0);
                    side33.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side33);
                    PdfPCell side14 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                    side14.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side14.setBackgroundColor(BaseColor.WHITE);
                    side14.setBorder(0);
                    side14.getColumn().updateFilledWidth(8);
                    tbResumo.addCell(side14);
                    PdfPCell side24 = new PdfPCell(new Paragraph("Total", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLDITALIC, cor)));
                    side24.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side24.setBackgroundColor(new BaseColor(189, 215, 238));
                    side24.setBorder(0);
                    side24.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side24);
                    PdfPCell side34 = new PdfPCell(new Paragraph(Chang(MF.getTotal()) + " " + MF.getModeda() + "(s)", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLDITALIC, BaseColor.BLACK)));
                    side34.setHorizontalAlignment(Element.ALIGN_LEFT);
                    side34.setBackgroundColor(new BaseColor(221, 235, 247));
                    side34.setBorder(0);
                    side34.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side34);

                    doc.add(tbResumo);
                    PdfPTable tbCoordenadas = new PdfPTable(2);
                    tbCoordenadas.setHorizontalAlignment(1);
                    tbCoordenadas.setSpacingBefore(100);
                    Paragraph P = new Paragraph();
                    P.setFont(new Font(Font.FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK));
                    this.exten.setMeoda("Kwanza");

                    ControloBD liga = new ControloBD();
                    liga.setCaminho(this.caminho);
                    liga.conexao();
                    Paragraph Cord = new Paragraph();
                    ;

                    if (MF.getTitulo().equals("Proforma") || MF.getTitulo().equals("Orçamento")) {
                        P.add("Este documento não contitui uma fatura. \n" + this.licenca + "\nOperador(a) " + MU.getNome() + ".");

                        liga.executeSql("select abreviacao, ibam from conta order by nome_banco");

                        try {
                            if (liga.rs.first()) {
                                do {
                                    Cord.add(new Paragraph("Coordenadas bancárias:\n\n" + liga.rs.getString("abreviacao") + ": " + liga.rs.getString("ibam") + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                                } while (liga.rs.next());
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else if (MF.getTitulo().equals("Fatura")) {
                        P.add(this.licenca + "\nOperador(a) " + MU.getNome() + ".");

                        liga.executeSql("select abreviacao, ibam from conta order by nome_banco");

                        try {
                            if (liga.rs.first()) {
                                do {
                                    Cord.add(new Paragraph("Coordenadas bancárias:\n\n" + liga.rs.getString("abreviacao") + ": " + liga.rs.getString("ibam") + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                                } while (liga.rs.next());
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (MF.getTitulo().equals("Fatura recibo")) {
                        this.exten.setNumber(MF.getPagamento());
                        P.add("Valor pago:" + Chang(MF.getPagamento()) + " " + exten.getMeoda() + "(s)" + "\nValor por extenso: " + exten.show() + "\nTroco: " + Chang(MF.getTroco()) + " " + exten.getMeoda() + "\n" + this.licenca + " \nOperador(a) " + MU.getNome() + ".");

                        liga.executeSql("select abreviacao, ibam, numero_da_conta from conta order by nome_banco");

                        {
                            Cord.add(new Paragraph(this.FormasDePagamento.get(0) + this.FormasDePagamento.get(1) + " " + this.FormasDePagamento.get(2) + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                        }

                    }

                    tbCoordenadas.addCell(new PdfPCell(P));
                    tbCoordenadas.addCell(new PdfPCell(Cord));

                    doc.add(tbCoordenadas);
                    doc.addAuthor(MU.getNome());
                } catch (DocumentException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }

            } finally {
                if (doc != null) {
                    doc.close();
//                    Desktop.getDesktop().open(new File(this.file.getAbsolutePath().toString()));
//                Desktop.getDesktop().print(new File(this.file.getAbsolutePath().toString()));

                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Erro" + ex);
                    }
                }

            }
        } else if (Tamanho.equals("A7")) {
            try {
//            

                doc = new Document(PageSize.A7, 5, 5, 20, 5);

                try {
                    DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                    out = outt;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
                try {
                    PdfWriter.getInstance(doc, out);
                    doc.open();
                    
//                Adicionar o logotipo da empresa
                    Image IMG;
                    if (!this.estadoLicenca) 
                        IMG = Image.getInstance(LocalImg);
                    else
                        IMG = Image.getInstance(revert());
                    IMG.scaleToFit(100, 120);
                    IMG.setAbsolutePosition(90, 720);
//                doc.add(IMG);

                    BaseColor cor = BaseColor.WHITE;
//                BaseColor cor = new BaseColor(47, 117, 181);
                    Paragraph P1Fatura = new Paragraph();
                    P1Fatura.setFont(new Font(Font.FontFamily.HELVETICA, 20, 0, cor));
                    P1Fatura.setAlignment(2);
//                P1Fatura.setSpacingAfter(40);
                    P1Fatura.add(" ");
//                doc.add(P1Fatura);
                    PdfPTable tb = new PdfPTable(1);

                    PdfPCell celIMG = new PdfPCell();
                    celIMG.setBorder(0);
                    celIMG.addElement(IMG);
                    tb.addCell(celIMG);
//                    tb.addCell(celIMG);
//                        tb.addCell(celIMG);
                    PdfPCell celf = new PdfPCell(P1Fatura);
                    celf.setBorder(0);
                    tb.addCell(celf);

                    tb.setSpacingBefore(30);
                    Paragraph P1 = new Paragraph();
                    Paragraph P2 = new Paragraph();
                    Paragraph P3 = new Paragraph();
                    Paragraph P4 = new Paragraph();

                    P1.setFont(new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK));
                    P2.setFont(new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK));
                    P3.setFont(new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK));
                    P4.setFont(new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK));
                    P1.add(ME.getDesignacao());
                    P2.add("Telefone: " + Integer.toString(ME.telefone));
                    P3.add(ME.pais + ", " + ME.getProvincia() + ", " + ME.rua + "");
                    P4.add("NIF: " + ME.getNif());

                    PdfPCell cel1 = new PdfPCell();
                    cel1.addElement(P1);
                    cel1.addElement(P2);
                    cel1.addElement(P3);
                    cel1.addElement(P4);
                    cel1.setBorder(0);

//                tb.setWidthPercentage(200);
                    tb.addCell(cel1);

                    Paragraph P5 = new Paragraph();
                    Paragraph P6 = new Paragraph();
                    Paragraph P7 = new Paragraph();
                    Paragraph P8 = new Paragraph();
//                                                             8               WIHTE
                    P5.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));
                    P6.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));
                    P7.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));
                    P8.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));

                    P5.add(this.MF.getTitulo() + " Nº");
                    P6.add("  Data");
                    String NUM = "0";
                    if (this.MF.getIdfactra() > 9) {
                        NUM = Integer.toString(this.MF.getIdfactra());
                    } else {
                        NUM = NUM + Integer.toString(this.MF.getIdfactra());
                    }

                    P7.add(this.ME.getIndicativo() + " " + this.ME.getSerie() + NUM + "\n\nSEGUNDA VIA");
                    P8.add(tempo.Date() + " " + tempo.Hours() + "\n\nVALIDADE: " + tempo.NextDay(num));

                    PdfPTable TBin = new PdfPTable(2);
                    PdfPCell cel3 = new PdfPCell();
                    PdfPCell cel4 = new PdfPCell();
                    PdfPCell cel5 = new PdfPCell(P7);
                    PdfPCell cel6 = new PdfPCell(P8);

                    cel3.setBorder(0);
                    cel4.setBorder(0);
                    cel5.setBorder(0);
                    cel6.setBorder(0);

                    cel3.setBackgroundColor(cor);
                    cel3.addElement(P5);

                    cel4.setBackgroundColor(cor);
                    cel4.addElement(P6);

                    TBin.addCell(cel3);
                    TBin.addCell(cel4);
                    TBin.addCell(cel5);
                    TBin.addCell(cel6);

                    PdfPCell celT = new PdfPCell(TBin);
                    celT.setBorder(0);
                    tb.addCell(celT);

                    PdfPTable TBin2 = new PdfPTable(1);
                    PdfPTable TBin3 = new PdfPTable(1);

                    Paragraph P9 = new Paragraph();
                    Paragraph P10 = new Paragraph();
                    Paragraph P11 = new Paragraph();
                    Paragraph P12 = new Paragraph();

                    P9.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));
                    P10.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));
                    P11.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));
                    P12.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));

                    P9.add("Cliente: " + MC.getDesignacao());
                    P10.add("NIF: " + MC.getNif());
                    P12.add("Validade: " + "10-05-2021");
                    Paragraph dc1 = new Paragraph();
                    Paragraph dc2 = new Paragraph();
                    Paragraph dc3 = new Paragraph();
                    Paragraph dc4 = new Paragraph();
                    Paragraph dc5 = new Paragraph();
                    dc1.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc2.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc3.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc4.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc1.add(MC.getDesignacao());
                    dc2.add(MC.getEndereco());
                    dc3.add(MC.getCidade());
                    dc4.add(MC.getTelefone());
                    dc5.add(MC.getEmail());

                    PdfPCell cel7 = new PdfPCell();
                    PdfPCell cel8 = new PdfPCell();
                    PdfPCell cel9 = new PdfPCell();
//                    cel9.addElement(dc1);
//                    cel9.addElement(dc2);
//                    cel9.addElement(dc3);
//                    cel9.addElement(dc4);
//                    cel9.addElement(dc5);
//                    PdfPCell cel10 = new PdfPCell(P12);

                    cel7.setBorder(0);
                    cel8.setBorder(0);
                    cel9.setBorder(0);
//                    cel10.setBorder(0);

                    cel7.setBackgroundColor(cor);
                    cel7.addElement(P9);

                    cel8.setBackgroundColor(cor);
                    cel8.addElement(P10);

                    TBin2.addCell(cel7);
                    TBin3.addCell(cel8);
                    TBin2.addCell(cel9);
//                    TBin3.addCell(cel10);
                    PdfPCell celT2 = new PdfPCell(TBin2);
                    celT2.setBorder(0);
                    PdfPCell celT3 = new PdfPCell(TBin3);

                    celT3.setBorder(0);
                    PdfPCell space = new PdfPCell(new Paragraph(" "));
                    space.setBorder(0);
//                    tb.addCell(space);
//                    tb.addCell(space);
//                    tb.addCell(space);
//                    tb.addCell(space);
                    tb.addCell(celT2);
                    tb.addCell(celT3);
                    tb.addCell(space);
                    if(!this.estadoLicenca)
                        tb.addCell(celIMG);
                    else
                        tb.addCell(space);
                    tb.addCell(space);
//                    tb.addCell(space);
//                    tb.addCell(space);
                    doc.add(tb);

//             SECOND HALF 
                    PdfPTable tbMaterial1 = new PdfPTable(1);
                    PdfPTable tbMaterial = new PdfPTable(5);
//                    tbMaterial1.setBackgroundColor(BaseColor.GRAY);
//                    tbMaterial1.setBackgroundColor(BaseColor.GRAY);
                    PdfPCell cab3 = new PdfPCell(new Paragraph("DESCRIÇÃO", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                    cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab3.setBackgroundColor(BaseColor.GRAY);
                    cab3.setBorder(0);

                    PdfPCell cab4 = new PdfPCell(new Paragraph("PREÇO\n(KZ)", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                    cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab4.setBackgroundColor(BaseColor.GRAY);
                    cab4.setBorder(0);

                    PdfPCell cab5 = new PdfPCell(new Paragraph("QNT", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                    cab5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab5.setBackgroundColor(BaseColor.GRAY);
                    cab5.setBorder(0);

                    PdfPCell cabVA = new PdfPCell(new Paragraph("IMP\n(%)", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                    cabVA.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cabVA.setBackgroundColor(BaseColor.GRAY);
                    cabVA.setBorder(0);

                    PdfPCell cab6 = new PdfPCell(new Paragraph("DESCONTO\n(KZ)", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                    cab6.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab6.setBackgroundColor(BaseColor.GRAY);
                    cab6.setBorder(0);
                    PdfPCell cab7 = new PdfPCell(new Paragraph("TOTAL\n(KZ)", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                    cab7.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab7.setBackgroundColor(BaseColor.GRAY);
                    cab7.setBorder(0);

                    tbMaterial1.addCell(cab3);
                    tbMaterial.addCell(cab4);
                    tbMaterial.addCell(cab5);
                    tbMaterial.addCell(cabVA);
                    tbMaterial.addCell(cab6);
                    tbMaterial.addCell(cab7);
                    doc.add(tbMaterial1);
                    doc.add(tbMaterial);

                    for (int i = 0; i < this.tabla.getRowCount(); i++) {

                        PdfPCell cel11 = null;
                        PdfPCell cel21 = null;
                        PdfPCell cel31 = null;
                        PdfPCell cel41 = null;
                        PdfPCell cel51 = null;
                        PdfPCell cel61 = null;

                        tbMaterial1 = new PdfPTable(1);
                        tbMaterial = new PdfPTable(5);

                        if (null != GetData(tabla, i, 2)) {
                            cel11 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 2).toString(), new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
//                            System.out.println("tem valor 1" );

                        }
                        if (cel11 == null) {
                            cel11 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 1");
                        }
                        cel11.setBorder(0);
                        cel11.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        cel11.getColumn().updateFilledWidth(3);
                        tbMaterial1.addCell(cel11);

                        if (null != GetData(tabla, i, 3)) {
                            cel21 = new PdfPCell(new Paragraph(Chang(this.tabla.getModel().getValueAt(i, 3).toString()), new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
//                            new PdfPCell(new Paragraph(Chang(this.tabla.getModel().getValueAt(i, 3).toString(), new Font(Font.FontFamily.HELVETICA, 4.5, Font.BOLD, BaseColor.BLACK)));

//                            System.out.println("tem valor 2");
                        }
                        if (cel21 == null) {
                            cel21 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 2");
                        }
                        cel21.setBorder(0);
                        cel21.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel21.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel21);

                        if (null != GetData(tabla, i, 7)) {
                            String iva = this.tabla.getModel().getValueAt(i, 7).toString();
                            cel31 = new PdfPCell(new Paragraph(iva, new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));

//                            System.out.println("tem valor 3");
                        }
                        if (cel31 == null) {
                            cel31 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 3");
                        }
                        cel31.setBorder(0);
                        cel31.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel31.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel31);

                        if (null != GetData(tabla, i, 4)) {
                            String iva = this.tabla.getModel().getValueAt(i, 4).toString();
                            if (iva.equals("ISENTO")) {
                                cel41 = new PdfPCell(new Paragraph("0,0", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                            } else {
                                cel41 = new PdfPCell(new Paragraph(iva, new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                            }

//                            System.out.println("tem valor");
                        }
                        if (cel41 == null) {
                            cel41 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                        }
                        cel41.setBorder(0);
                        cel41.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel41.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel41);

                        if (null != GetData(tabla, i, 8)) {
                            cel51 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 8).toString(), new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));

//                            System.out.println("tem valor 5");
                        }
                        if (cel51 == null) {
                            cel51 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 5");
                        }
                        cel51.setBorder(0);
                        cel51.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel51.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel51);

                        if (null != GetData(tabla, i, 9)) {
                            cel61 = new PdfPCell(new Paragraph(Chang(this.tabla.getModel().getValueAt(i, 9).toString()), new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));

//                            System.out.println("tem valor");
                        }
                        if (cel61 == null) {
                            cel61 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                        }
                        cel61.setBorder(0);
                        cel61.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel61.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel61);

                        doc.add(tbMaterial1);
                        doc.add(tbMaterial);
                    }

//                    doc.add(tbMaterial1);
//                    doc.add(tbMaterial);
                    //            THIRD HALF 
                    PdfPTable tbResumo = new PdfPTable(1);
//                    PdfPCell side1 = new PdfPCell(new Paragraph("Agradecemos...", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, cor)));
//                    side1.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    side1.setBackgroundColor(BaseColor.WHITE);
////                    side1.setBorder(0);
//                    side1.getColumn().updateFilledWidth(8);
//                    tbResumo.addCell(side1);
//
//                    PdfPCell side2 = new PdfPCell(new Paragraph("Desconto", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK)));
//                    side2.setHorizontalAlignment(Element.ALIGN_LEFT);
////                    side2.setBackgroundColor(new BaseColor(189, 215, 238));
////                    side2.setBorder(0);
//                    side2.getColumn().updateFilledWidth(2);
//                    tbResumo.addCell(side2);
                    PdfPCell side3 = new PdfPCell(new Paragraph("Desconto: " + Chang(this.MF.getDesconto()) + " " + MF.getModeda() + "(s)", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK)));
                    side3.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    side3.setBackgroundColor(new BaseColor(221, 235, 247));
//                    side3.setBorder(0);
                    side3.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side3);

                    PdfPCell side32 = new PdfPCell(new Paragraph("Taxa do IVA: " + Double.toString(this.MF.getTaxa()) + "%", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK)));
                    side32.setHorizontalAlignment(Element.ALIGN_LEFT);

                    side32.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side32);

                    PdfPCell side33 = new PdfPCell(new Paragraph("Imposto: " + Chang(this.MF.getTotal_doIVA()) + " " + MF.getModeda() + "(s)", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK)));
                    side33.setHorizontalAlignment(Element.ALIGN_LEFT);

                    side33.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side33);

                    this.exten.setMeoda("Kwanza");
                    exten.setNumber(MF.getTotal());
                    PdfPCell side34 = new PdfPCell(new Paragraph("Total: " + Chang(MF.getTotal()) + " Kz\n\n" + exten.show(), new Font(Font.FontFamily.HELVETICA, 8, Font.BOLDITALIC, BaseColor.BLACK)));
                    side34.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    side34.setBackgroundColor(new BaseColor(221, 235, 247));
//                    side34.setBorder(0);
                    side34.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side34);

                    if (MF.getTitulo().equals("Fatura recibo")) {
                        this.exten.setNumber(MF.getPagamento());
//                        P.add("Valor pago:" + Chang(MF.getPagamento()) + " " + exten.getMeoda() + "(s)" + "\nValor por extenso: " + exten.show() + "\nTroco: " + Chang(MF.getTroco()) + " " + exten.getMeoda() + "\nProcessado pelo programa certificado Cramer Nº XX/AGT/YYYY.\nOperador(a) " + MU.getNome() + ".");

                        side34 = new PdfPCell(new Paragraph("Valor pago: " + Chang(MF.getPagamento()) + " " + exten.getMeoda() + "(s)" + "\nValor por extenso: " + exten.show() + "\nTroco: " + Chang(MF.getTroco()) + " " + exten.getMeoda(), new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                        side34.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    side34.setBackgroundColor(new BaseColor(221, 235, 247));
//                    side34.setBorder(0);
                        side34.getColumn().updateFilledWidth(2);
                        tbResumo.addCell(side34);
                    }
                    doc.add(tbResumo);
                    PdfPTable tbCoordenadas = new PdfPTable(1);
                    tbCoordenadas.setHorizontalAlignment(1);
                    tbCoordenadas.setSpacingBefore(20);
                    Paragraph P = new Paragraph();
                    P.setFont(new Font(Font.FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK));

                    ControloBD liga = new ControloBD();
                    liga.setCaminho(this.caminho);
                    liga.conexao();
                    Paragraph Cord = new Paragraph();

                    Cord.add(new Paragraph(ME.getRegime_de_iva() + "\n\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));

                    if (MF.getTitulo().equals("Proforma") || MF.getTitulo().equals("Orçamento")) {
                        P.add(new Paragraph("Este documento não contitui uma fatura. \n" + this.licenca + "\nOperador(a) " + MU.getNome() + ".", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));

                        Cord.add(new Paragraph("Coordenadas bancárias: \n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));

                        liga.executeSql("select abreviacao, ibam from conta order by nome_banco");

                        try {
                            if (liga.rs.first()) {
                                do {
                                    Cord.add(new Paragraph(liga.rs.getString("abreviacao") + ": " + liga.rs.getString("ibam") + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                                } while (liga.rs.next());
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));

                    } else if (MF.getTitulo().equals("Fatura")) {
                        P.add(new Paragraph(this.licenca + "\nOperador(a) " + MU.getNome() + ".", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                        Cord.add(new Paragraph("Coordenadas bancárias: \n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));

                        liga.executeSql("select abreviacao, ibam from conta order by nome_banco");

                        try {
                            if (liga.rs.first()) {
                                do {
                                    Cord.add(new Paragraph(liga.rs.getString("abreviacao") + ": " + liga.rs.getString("ibam") + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                                } while (liga.rs.next());
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));

                    } else if (MF.getTitulo().equals("Fatura recibo")) {
//                        this.exten.setNumber(MF.getPagamento());
//                        P.add("Valor pago:" + Chang(MF.getPagamento()) + " " + exten.getMeoda() + "(s)" + "\nValor por extenso: " + exten.show() + "\nTroco: " + Chang(MF.getTroco()) + " " + exten.getMeoda() + "\nProcessado pelo programa certificado Cramer Nº XX/AGT/YYYY.\nOperador(a) " + MU.getNome() + ".");
                        P.add(new Paragraph(this.licenca + "\nOperador(a) " + MU.getNome() + ".", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));

                        liga.executeSql("select abreviacao, ibam, numero_da_conta from conta order by nome_banco");

                        {
                            Cord.add(new Paragraph(this.FormasDePagamento.get(0) + this.FormasDePagamento.get(1) + " " + this.FormasDePagamento.get(2) + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                        }

                    }
                    tbCoordenadas.addCell(new PdfPCell(Cord));
                    tbCoordenadas.addCell(new PdfPCell(P));

                    doc.add(tbCoordenadas);
                    doc.addAuthor(MU.getNome());


                } catch (DocumentException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }

            } finally {
                if (doc != null) {
                    doc.close();
//                    Desktop.getDesktop().open(new File(this.file.getAbsolutePath().toString()));
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Erro" + ex);
                    }
                }

            }
        } else if (Tamanho.equals("A8")) {
            try {

                doc = new Document(PageSize.A8, 5, 5, 20, 5);
                try {
                    DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                    out = outt;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
                try {
                    PdfWriter.getInstance(doc, out);
                    doc.open();
//                Adicionar o logotipo da empresa
                    Image IMG;
                    if (!this.estadoLicenca) 
                        IMG = Image.getInstance(LocalImg);
                    else
                        IMG = Image.getInstance(revert());
                    
                    IMG.scaleToFit(100, 120);
                    IMG.setAbsolutePosition(90, 720);
//                doc.add(IMG);

                    BaseColor cor = BaseColor.WHITE;
//                BaseColor cor = new BaseColor(47, 117, 181);
                    Paragraph P1Fatura = new Paragraph();
                    P1Fatura.setFont(new Font(Font.FontFamily.HELVETICA, 20, 0, cor));
                    P1Fatura.setAlignment(2);
//                P1Fatura.setSpacingAfter(40);
                    P1Fatura.add(" ");
//                doc.add(P1Fatura);
                    PdfPTable tb = new PdfPTable(1);

                    PdfPCell celIMG = new PdfPCell();
                    celIMG.setBorder(0);
                    celIMG.addElement(IMG);
                    tb.addCell(celIMG);
//                    if (!this.estadoLicenca) 
//                        IMG = Image.getInstance(LocalImg);
                    
                    PdfPCell celf = new PdfPCell(P1Fatura);
                    celf.setBorder(0);
                    tb.addCell(celf);

                    tb.setSpacingBefore(30);
                    Paragraph P1 = new Paragraph();
                    Paragraph P2 = new Paragraph();
                    Paragraph P3 = new Paragraph();
                    Paragraph P4 = new Paragraph();

                    P1.setFont(new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK));
                    P2.setFont(new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK));
                    P3.setFont(new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK));
                    P4.setFont(new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD, BaseColor.BLACK));
                    P1.add(ME.getDesignacao());
                    P2.add("Telefone: " + Integer.toString(ME.telefone));
                    P3.add(ME.pais + ", " + ME.getProvincia() + ", " + ME.rua + "");
                    P4.add("NIF: " + ME.getNif());

                    PdfPCell cel1 = new PdfPCell();
                    cel1.addElement(P1);
                    cel1.addElement(P2);
                    cel1.addElement(P3);
                    cel1.addElement(P4);
                    cel1.setBorder(0);

//                tb.setWidthPercentage(200);
                    tb.addCell(cel1);

                    Paragraph P5 = new Paragraph();
                    Paragraph P6 = new Paragraph();
                    Paragraph P7 = new Paragraph();
                    Paragraph P8 = new Paragraph();
//                                                             8               WIHTE
                    P5.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));
                    P6.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));
                    P7.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));
                    P8.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));

                    P5.add(this.MF.getTitulo() + " Nº");
                    P6.add("  Data");
                    String NUM = "0";
                    if (this.MF.getIdfactra() > 9) {
                        NUM = Integer.toString(this.MF.getIdfactra());
                    } else {
                        NUM = NUM + Integer.toString(this.MF.getIdfactra());
                    }

                    P7.add(this.ME.getIndicativo() + " " + this.ME.getSerie() + NUM + "\n\nSEGUNDA VIA");
                    P8.add(tempo.Date() + " " + tempo.Hours() + "\n\nVALIDADE: " + tempo.NextDay(num));

                    PdfPTable TBin = new PdfPTable(2);
                    PdfPCell cel3 = new PdfPCell();
                    PdfPCell cel4 = new PdfPCell();
                    PdfPCell cel5 = new PdfPCell(P7);
                    PdfPCell cel6 = new PdfPCell(P8);

                    cel3.setBorder(0);
                    cel4.setBorder(0);
                    cel5.setBorder(0);
                    cel6.setBorder(0);

                    cel3.setBackgroundColor(cor);
                    cel3.addElement(P5);

                    cel4.setBackgroundColor(cor);
                    cel4.addElement(P6);

                    TBin.addCell(cel3);
                    TBin.addCell(cel4);
                    TBin.addCell(cel5);
                    TBin.addCell(cel6);

                    PdfPCell celT = new PdfPCell(TBin);
                    celT.setBorder(0);
                    tb.addCell(celT);

                    PdfPTable TBin2 = new PdfPTable(1);
                    PdfPTable TBin3 = new PdfPTable(1);

                    Paragraph P9 = new Paragraph();
                    Paragraph P10 = new Paragraph();
                    Paragraph P11 = new Paragraph();
                    Paragraph P12 = new Paragraph();

                    P9.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));
                    P10.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));
                    P11.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));
                    P12.setFont(new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK));

                    P9.add("Cliente: " + MC.getDesignacao());
                    P10.add("NIF: " + MC.getNif());
//                    P12.add("Validade: " + "10-05-2021");
                    Paragraph dc1 = new Paragraph();
                    Paragraph dc2 = new Paragraph();
                    Paragraph dc3 = new Paragraph();
                    Paragraph dc4 = new Paragraph();
                    Paragraph dc5 = new Paragraph();
                    dc1.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc2.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc3.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc4.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                    dc1.add(MC.getDesignacao());
                    dc2.add(MC.getEndereco());
                    dc3.add(MC.getCidade());
                    dc4.add(MC.getTelefone());
                    dc5.add(MC.getEmail());

                    PdfPCell cel7 = new PdfPCell();
                    PdfPCell cel8 = new PdfPCell();
                    PdfPCell cel9 = new PdfPCell();
//                    cel9.addElement(dc1);
//                    cel9.addElement(dc2);
//                    cel9.addElement(dc3);
//                    cel9.addElement(dc4);
//                    cel9.addElement(dc5);
//                    PdfPCell cel10 = new PdfPCell(P12);

                    cel7.setBorder(0);
                    cel8.setBorder(0);
                    cel9.setBorder(0);
//                    cel10.setBorder(0);

                    cel7.setBackgroundColor(cor);
                    cel7.addElement(P9);

                    cel8.setBackgroundColor(cor);
                    cel8.addElement(P10);

                    TBin2.addCell(cel7);
                    TBin3.addCell(cel8);
                    TBin2.addCell(cel9);
//                    TBin3.addCell(cel10);
                    PdfPCell celT2 = new PdfPCell(TBin2);
                    celT2.setBorder(0);
                    PdfPCell celT3 = new PdfPCell(TBin3);
                    celT3.setBorder(0);
                    PdfPCell space = new PdfPCell(new Paragraph(" "));
                    space.setBorder(0);
//                    tb.addCell(space);
//                    tb.addCell(space);
//                    tb.addCell(space);
//                    tb.addCell(space);
                    tb.addCell(celT2);
                    tb.addCell(celT3);
                    tb.addCell(space);
                    if (!this.estadoLicenca) 
                        IMG = Image.getInstance(LocalImg);
                    else
                        tb.addCell(space);
                    tb.addCell(space);
//                    tb.addCell(space);
                    doc.add(tb);

//             SECOND HALF 
                    PdfPTable tbMaterial1 = new PdfPTable(1);
                    PdfPTable tbMaterial = new PdfPTable(5);
//                    tbMaterial1.setBackgroundColor(BaseColor.GRAY);
//                    tbMaterial1.setBackgroundColor(BaseColor.GRAY);
                    PdfPCell cab3 = new PdfPCell(new Paragraph("DESCRIÇÃO", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                    cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab3.setBackgroundColor(BaseColor.GRAY);
                    cab3.setBorder(0);

                    PdfPCell cab4 = new PdfPCell(new Paragraph("PREÇO\n(KZ)", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                    cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab4.setBackgroundColor(BaseColor.GRAY);
                    cab4.setBorder(0);

                    PdfPCell cab5 = new PdfPCell(new Paragraph("QNT", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                    cab5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab5.setBackgroundColor(BaseColor.GRAY);
                    cab5.setBorder(0);

                    PdfPCell cabVA = new PdfPCell(new Paragraph("IMP\n(%)", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                    cabVA.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cabVA.setBackgroundColor(BaseColor.GRAY);
                    cabVA.setBorder(0);

                    PdfPCell cab6 = new PdfPCell(new Paragraph("DESCONTO\n(KZ)", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                    cab6.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab6.setBackgroundColor(BaseColor.GRAY);
                    cab6.setBorder(0);
                    PdfPCell cab7 = new PdfPCell(new Paragraph("TOTAL\n(KZ)", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                    cab7.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab7.setBackgroundColor(BaseColor.GRAY);
                    cab7.setBorder(0);

                    tbMaterial1.addCell(cab3);
                    tbMaterial.addCell(cab4);
                    tbMaterial.addCell(cab5);
                    tbMaterial.addCell(cabVA);
                    tbMaterial.addCell(cab6);
                    tbMaterial.addCell(cab7);
                    doc.add(tbMaterial1);
                    doc.add(tbMaterial);

                    for (int i = 0; i < this.tabla.getRowCount(); i++) {

                        PdfPCell cel11 = null;
                        PdfPCell cel21 = null;
                        PdfPCell cel31 = null;
                        PdfPCell cel41 = null;
                        PdfPCell cel51 = null;
                        PdfPCell cel61 = null;

                        tbMaterial1 = new PdfPTable(1);
                        tbMaterial = new PdfPTable(5);

                        if (null != GetData(tabla, i, 2)) {
                            cel11 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 2).toString(), new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
//                            System.out.println("tem valor 1" );

                        }
                        if (cel11 == null) {
                            cel11 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 1");
                        }
                        cel11.setBorder(0);
                        cel11.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        cel11.getColumn().updateFilledWidth(3);
                        tbMaterial1.addCell(cel11);

                        if (null != GetData(tabla, i, 3)) {
                            cel21 = new PdfPCell(new Paragraph(Chang(this.tabla.getModel().getValueAt(i, 3).toString()), new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
//                            new PdfPCell(new Paragraph(Chang(this.tabla.getModel().getValueAt(i, 3).toString(), new Font(Font.FontFamily.HELVETICA, 4.5, Font.BOLD, BaseColor.BLACK)));

//                            System.out.println("tem valor 2");
                        }
                        if (cel21 == null) {
                            cel21 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 2");
                        }
                        cel21.setBorder(0);
                        cel21.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel21.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel21);

                        if (null != GetData(tabla, i, 7)) {
                            String iva = this.tabla.getModel().getValueAt(i, 7).toString();
                            cel31 = new PdfPCell(new Paragraph(iva, new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));

//                            System.out.println("tem valor 3");
                        }
                        if (cel31 == null) {
                            cel31 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 3");
                        }
                        cel31.setBorder(0);
                        cel31.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel31.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel31);

                        if (null != GetData(tabla, i, 4)) {
                            String iva = this.tabla.getModel().getValueAt(i, 4).toString();
                            if (iva.equals("ISENTO")) {
                                cel41 = new PdfPCell(new Paragraph("0,0", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                            } else {
                                cel41 = new PdfPCell(new Paragraph(iva, new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));
                            }

//                            System.out.println("tem valor");
                        }
                        if (cel41 == null) {
                            cel41 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                        }
                        cel41.setBorder(0);
                        cel41.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel41.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel41);

                        if (null != GetData(tabla, i, 8)) {
                            cel51 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 8).toString(), new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));

//                            System.out.println("tem valor 5");
                        }
                        if (cel51 == null) {
                            cel51 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor 5");
                        }
                        cel51.setBorder(0);
                        cel51.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel51.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel51);

                        if (null != GetData(tabla, i, 9)) {
                            cel61 = new PdfPCell(new Paragraph(Chang(this.tabla.getModel().getValueAt(i, 9).toString()), new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLACK)));

//                            System.out.println("tem valor");
                        }
                        if (cel61 == null) {
                            cel61 = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                        }
                        cel61.setBorder(0);
                        cel61.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel61.getColumn().updateFilledWidth(3);
                        tbMaterial.addCell(cel61);

                        doc.add(tbMaterial1);
                        doc.add(tbMaterial);
                    }

//                    doc.add(tbMaterial1);
//                    doc.add(tbMaterial);
                    //            THIRD HALF 
                    PdfPTable tbResumo = new PdfPTable(1);
//                    PdfPCell side1 = new PdfPCell(new Paragraph("Agradecemos...", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, cor)));
//                    side1.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    side1.setBackgroundColor(BaseColor.WHITE);
////                    side1.setBorder(0);
//                    side1.getColumn().updateFilledWidth(8);
//                    tbResumo.addCell(side1);
//
//                    PdfPCell side2 = new PdfPCell(new Paragraph("Desconto", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK)));
//                    side2.setHorizontalAlignment(Element.ALIGN_LEFT);
////                    side2.setBackgroundColor(new BaseColor(189, 215, 238));
////                    side2.setBorder(0);
//                    side2.getColumn().updateFilledWidth(2);
//                    tbResumo.addCell(side2);
                    PdfPCell side3 = new PdfPCell(new Paragraph("Desconto: " + Chang(this.MF.getDesconto()) + " " + MF.getModeda() + "(s)", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK)));
                    side3.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    side3.setBackgroundColor(new BaseColor(221, 235, 247));
//                    side3.setBorder(0);
                    side3.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side3);
//                    PdfPCell side12 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, cor)));
//                    side12.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    side12.setBackgroundColor(BaseColor.WHITE);
//                    side12.setBorder(0);
//                    side12.getColumn().updateFilledWidth(8);
//                    tbResumo.addCell(side12);
//                    PdfPCell side22 = new PdfPCell(new Paragraph("Taxa do IVA", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK)));
//                    side22.setHorizontalAlignment(Element.ALIGN_LEFT);
////                    side22.setBackgroundColor(new BaseColor(189, 215, 238));
////                    side22.setBorder(0);
//                    side22.getColumn().updateFilledWidth(2);
//                    tbResumo.addCell(side22);
                    PdfPCell side32 = new PdfPCell(new Paragraph("Taxa do IVA: " + Double.toString(this.MF.getTaxa()) + "%", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK)));
                    side32.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    side32.setBackgroundColor(new BaseColor(221, 235, 247));
//                    side32.setBorder(0);
                    side32.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side32);
//                    PdfPCell side13 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, cor)));
//                    side13.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    side13.setBackgroundColor(BaseColor.WHITE);
//                    side13.setBorder(0);
//                    side13.getColumn().updateFilledWidth(8);
//                    tbResumo.addCell(side13);
//                    PdfPCell side23 = new PdfPCell(new Paragraph("Imposto", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK)));
//                    side23.setHorizontalAlignment(Element.ALIGN_LEFT);
////                    side23.setBackgroundColor(new BaseColor(189, 215, 238));
////                    side23.setBorder(0);
//                    side23.getColumn().updateFilledWidth(2);
//                    tbResumo.addCell(side23);
                    PdfPCell side33 = new PdfPCell(new Paragraph("Imposto: " + Chang(this.MF.getTotal_doIVA()) + " " + MF.getModeda() + "(s)", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK)));
                    side33.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    side33.setBackgroundColor(new BaseColor(221, 235, 247));
//                    side33.setBorder(0);
                    side33.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side33);
//                    PdfPCell side14 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, cor)));
//                    side14.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    side14.setBackgroundColor(BaseColor.WHITE);
//                    side14.setBorder(0);
//                    side14.getColumn().updateFilledWidth(8);
//                    tbResumo.addCell(side14);
//                    PdfPCell side24 = new PdfPCell(new Paragraph("Total", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLDITALIC, BaseColor.BLACK)));
//                    side24.setHorizontalAlignment(Element.ALIGN_LEFT);
////                    side24.setBackgroundColor(new BaseColor(189, 215, 238));
////                    side24.setBorder(0);
//                    side24.getColumn().updateFilledWidth(2);
//                    tbResumo.addCell(side24);
                    this.exten.setMeoda("Kwanza");
                    exten.setNumber(MF.getTotal());
                    PdfPCell side34 = new PdfPCell(new Paragraph("Total: " + Chang(MF.getTotal()) + " Kz\n\n" + exten.show(), new Font(Font.FontFamily.HELVETICA, 8, Font.BOLDITALIC, BaseColor.BLACK)));
                    side34.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    side34.setBackgroundColor(new BaseColor(221, 235, 247));
//                    side34.setBorder(0);
                    side34.getColumn().updateFilledWidth(2);
                    tbResumo.addCell(side34);

                    if (MF.getTitulo().equals("Fatura recibo")) {
                        this.exten.setNumber(MF.getPagamento());
//                        P.add("Valor pago:" + Chang(MF.getPagamento()) + " " + exten.getMeoda() + "(s)" + "\nValor por extenso: " + exten.show() + "\nTroco: " + Chang(MF.getTroco()) + " " + exten.getMeoda() + "\nProcessado pelo programa certificado Cramer Nº XX/AGT/YYYY.\nOperador(a) " + MU.getNome() + ".");

                        side34 = new PdfPCell(new Paragraph("Valor pago: " + Chang(MF.getPagamento()) + " " + exten.getMeoda() + "(s)" + "\nValor por extenso: " + exten.show() + "\nTroco: " + Chang(MF.getTroco()) + " " + exten.getMeoda(), new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                        side34.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    side34.setBackgroundColor(new BaseColor(221, 235, 247));
//                    side34.setBorder(0);
                        side34.getColumn().updateFilledWidth(2);
                        tbResumo.addCell(side34);
                    }
                    doc.add(tbResumo);
                    PdfPTable tbCoordenadas = new PdfPTable(1);
                    tbCoordenadas.setHorizontalAlignment(1);
                    tbCoordenadas.setSpacingBefore(20);
                    Paragraph P = new Paragraph();
                    P.setFont(new Font(Font.FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK));

                    ControloBD liga = new ControloBD();
                    liga.setCaminho(this.caminho);
                    liga.conexao();
                    Paragraph Cord = new Paragraph();

                    Cord.add(new Paragraph(ME.getRegime_de_iva() + "\n\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));

                    if (MF.getTitulo().equals("Proforma") || MF.getTitulo().equals("Orçamento")) {
                        P.add(new Paragraph("Este documento não contitui uma fatura. \n" + this.licenca + "\nOperador(a) " + MU.getNome() + ".", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));

                        Cord.add(new Paragraph("Coordenadas bancárias: \n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));

                        liga.executeSql("select abreviacao, ibam from conta order by nome_banco");

                        try {
                            if (liga.rs.first()) {
                                do {
                                    Cord.add(new Paragraph(liga.rs.getString("abreviacao") + ": " + liga.rs.getString("ibam") + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                                } while (liga.rs.next());
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));

                    } else if (MF.getTitulo().equals("Fatura")) {
                        P.add(new Paragraph(this.licenca + "\nOperador(a) " + MU.getNome() + ".", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                        Cord.add(new Paragraph("Coordenadas bancárias: \n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));

                        liga.executeSql("select abreviacao, ibam from conta order by nome_banco");

                        try {
                            if (liga.rs.first()) {
                                do {
                                    Cord.add(new Paragraph(liga.rs.getString("abreviacao") + ": " + liga.rs.getString("ibam") + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                                } while (liga.rs.next());
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));
//                        tbCoordenadas.addCell(new PdfPCell(Cord));
//                        tbCoordenadas.addCell(new PdfPCell(P));

                    } else if (MF.getTitulo().equals("Fatura recibo")) {
//                        this.exten.setNumber(MF.getPagamento());
//                        P.add("Valor pago:" + Chang(MF.getPagamento()) + " " + exten.getMeoda() + "(s)" + "\nValor por extenso: " + exten.show() + "\nTroco: " + Chang(MF.getTroco()) + " " + exten.getMeoda() + "\nProcessado pelo programa certificado Cramer Nº XX/AGT/YYYY.\nOperador(a) " + MU.getNome() + ".");
                        P.add(new Paragraph(this.licenca + "\nOperador(a) " + MU.getNome() + ".", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));

                        liga.executeSql("select abreviacao, ibam, numero_da_conta from conta order by nome_banco");

                        {
                            Cord.add(new Paragraph(this.FormasDePagamento.get(0) + this.FormasDePagamento.get(1) + " " + this.FormasDePagamento.get(2) + "\n", new Font(Font.FontFamily.HELVETICA, 6, Font.BOLDITALIC, BaseColor.BLACK)));
                        }

                    }
                    tbCoordenadas.addCell(new PdfPCell(Cord));
                    tbCoordenadas.addCell(new PdfPCell(P));

                    doc.add(tbCoordenadas);
                    doc.addAuthor(MU.getNome());

                    if (!this.estadoLicenca) {
                        Image watermark = Image.getInstance(LocalImg);
                        watermark.setAbsolutePosition(100, 720);
                        watermark.scaleAbsolute(100, 50);
                        doc.add(watermark);

                        watermark.setAbsolutePosition(110, 700);
                        watermark.scaleAbsolute(100, 25);
                        doc.add(watermark);

                        watermark.setAbsolutePosition(100, 680);
                        watermark.scaleAbsolute(100, 125);
                        doc.add(watermark);
                        System.out.println("ADD IMG");
                    }

                } catch (DocumentException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }

            } finally {
                if (doc != null) {
                    doc.close();
//                    Desktop.getDesktop().open(new File(this.file.getAbsolutePath().toString()));

                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Erro" + ex);
                    }
                }

            }
        }
        
            String tb, coluna_tb;

        if (MF.getTitulo().equals("Fatura")) {
            tb = "fatura";
            coluna_tb = "idfactura";
        } else if (MF.getTitulo().equals("Proforma")) {
            tb = "proforma";
            coluna_tb = "idproforma";
        } else if (MF.getTitulo().equals("Orçamento")) {
            tb = "orcamento";
            coluna_tb = "idorcamento";
        } //                    if(MF.getTitulo().equals("Fatura recibo"))
        else {
            tb = "recibo";
            coluna_tb = "idfatura_recibo";
        }
        this.ToByte(this.file, tb, coluna_tb);
 }

    public ModeloUtilizador getMU() {
        return MU;
    }

    public void setMU(ModeloUtilizador MU) {
        this.MU = MU;
    }

    public ModeloCliente getMC() {
        return MC;
    }

    public void setMC(ModeloCliente MC) {
        this.MC = MC;
    }

    public void exportA4Fatura(boolean pint) throws IOException {
        BaseColor cor = new BaseColor(47, 117, 181);
        Document doc = null;
        OutputStream out = null;

        try {
            doc = new Document();
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 100);
//                IMG.setAbsolutePosition(400, 700);
                IMG.setAlignment(1);
                doc.add(IMG);

//                Image imagem;
//                imagem = Image.getInstance("NetBeansProjects\\Somoil\\src\\Modelo\\download.png");
//                doc.add(imagem);
                Paragraph p1 = new Paragraph();
                p1.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                p1.setAlignment(1);
                p1.add("Dados da exportação: Relatório das Faturas" + ", exportado em: " + this.tempo.Date() + " às" + this.tempo.Hours() + " \n \n");
                doc.add(p1);

                PdfPTable tb = new PdfPTable(this.tabla.getColumnCount());
//                Acrescentar se é visitante ou não como cabeçalho
                Font cab = new Font(Font.FontFamily.TIMES_ROMAN, 10);
                PdfPCell cab1 = new PdfPCell(new Paragraph("Nº", cab));

                cab1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab1.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab2 = new PdfPCell(new Paragraph("Título", cab));
                cab2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab2.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab3 = new PdfPCell(new Paragraph("Desc.(Kz)", cab));
                cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab3.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab4 = new PdfPCell(new Paragraph("Data", cab));
                cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab4.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab5 = new PdfPCell(new Paragraph("Hora", cab));
                cab5.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab5.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab6 = new PdfPCell(new Paragraph("Ret. Fonte", cab));
                cab6.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab6.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab7 = new PdfPCell(new Paragraph("Dívida(Kz)", cab));
                cab7.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab7.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab8 = new PdfPCell(new Paragraph("Liquidação(%)", cab));
                cab8.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab8.setBackgroundColor(BaseColor.GRAY);

                tb.addCell(cab1);
                tb.addCell(cab2);
                tb.addCell(cab3);
                tb.addCell(cab4);
                tb.addCell(cab5);
                tb.addCell(cab6);
                tb.addCell(cab7);
                tb.addCell(cab8);

                for (int i = 0; i < this.tabla.getRowCount(); i++) {
                    for (int j = 0; j < this.tabla.getColumnCount(); j++) {
                        PdfPCell cel = null;

                        if (null != GetData(tabla, i, j)) {
                            cel = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, j).toString(), cab));
                            ;
//                            System.out.println("tem valor");
                        }

                        if (cel == null) {
                            cel = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                        }
                        cel.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel.getColumn().updateFilledWidth(3);
                        tb.addCell(cel);

                    }
                }

                doc.add(tb);
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
                doc.close();

            }
            if (out != null) {
                try {
                    out.close();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }

        }
        String tb, coluna_tb;
        tb = "";
        coluna_tb = "";
        
//        this.ToByte(this.file, tb, coluna_tb);
    }

    public void exportA4Recibo(boolean pint) throws IOException {
        BaseColor cor = new BaseColor(47, 117, 181);
        Document doc = null;
        OutputStream out = null;

        try {
            doc = new Document();
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 100);
//                IMG.setAbsolutePosition(400, 700);
                IMG.setAlignment(1);
                doc.add(IMG);

//                Image imagem;
//                imagem = Image.getInstance("NetBeansProjects\\Somoil\\src\\Modelo\\download.png");
//                doc.add(imagem);
                Paragraph p1 = new Paragraph();
                p1.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                p1.setAlignment(1);
                p1.add("Dados da exportação: Relatório das Faturas recibo" + ", exportado em: " + this.tempo.Date() + " às " + this.tempo.Hours() + " \n \n");
                doc.add(p1);

                PdfPTable tb = new PdfPTable(this.tabla.getColumnCount());
//                Acrescentar se é visitante ou não como cabeçalho
                Font cab = new Font(Font.FontFamily.TIMES_ROMAN, 10);
                PdfPCell cab1 = new PdfPCell(new Paragraph("Nº", cab));

                cab1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab1.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab2 = new PdfPCell(new Paragraph("Título", cab));
                cab2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab2.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab3 = new PdfPCell(new Paragraph("Desc.(Kz)", cab));
                cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab3.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab4 = new PdfPCell(new Paragraph("Data", cab));
                cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab4.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab5 = new PdfPCell(new Paragraph("Hora", cab));
                cab5.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab5.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab6 = new PdfPCell(new Paragraph("Ret. Fonte", cab));
                cab6.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab6.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab7 = new PdfPCell(new Paragraph("Total(Kz)", cab));
                cab7.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab7.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab8 = new PdfPCell(new Paragraph("Pagamento(Kz)", cab));
                cab8.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab8.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab9 = new PdfPCell(new Paragraph("Troco (Kz)", cab));
                cab9.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab9.setBackgroundColor(BaseColor.GRAY);

                tb.addCell(cab1);
                tb.addCell(cab2);
                tb.addCell(cab3);
                tb.addCell(cab4);
                tb.addCell(cab5);
                tb.addCell(cab6);
                tb.addCell(cab7);
                tb.addCell(cab8);
                tb.addCell(cab9);

                for (int i = 0; i < this.tabla.getRowCount(); i++) {
                    for (int j = 0; j < this.tabla.getColumnCount(); j++) {
                        PdfPCell cel = null;

                        if (null != GetData(tabla, i, j)) {
                            cel = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, j).toString(), cab));
                            ;
//                            System.out.println("tem valor");
                        }

                        if (cel == null) {
                            cel = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                        }
                        cel.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel.getColumn().updateFilledWidth(3);
                        tb.addCell(cel);

                    }
                }

                doc.add(tb);
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
             if (doc != null) {
//                   exportA4Recibo
                    doc.close();
                    StampPageXofY stampPageXofY = new StampPageXofY();
                    stampPageXofY.setEstado(this.estadoLicenca);
                    String SRC = this.file.getAbsolutePath().toString();
                    String nome = this.file.getAbsolutePath().toString();
                    String DEST = this.file.getParent() + "/_" + this.file.getName();

                    File file = new File(DEST);
                    file.getParentFile().mkdirs();
                    try {

                        stampPageXofY.Paginar(SRC, DEST);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.file.delete();
                    file.renameTo(new File(nome));
//                    Desktop.getDesktop().open(new File(nome));

                }
            if (out != null) {
                try {
                    out.close();
                    
                    
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro");
                }
            }
            if (pint) {
                Desktop.getDesktop().print(file);

            }

        }
        String tb, coluna_tb;

            tb = "";
            coluna_tb = "";
        
//        this.ToByte(this.file, tb, coluna_tb);
    }

    public void exportA4Proforma(boolean pint) throws IOException {
        BaseColor cor = new BaseColor(47, 117, 181);
        Document doc = null;
        OutputStream out = null;

        try {
            doc = new Document();
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 100);
//                IMG.setAbsolutePosition(400, 700);
                IMG.setAlignment(1);
                doc.add(IMG);

//                Image imagem;
//                imagem = Image.getInstance("NetBeansProjects\\Somoil\\src\\Modelo\\download.png");
//                doc.add(imagem);
                Paragraph p1 = new Paragraph();
                p1.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                p1.setAlignment(1);
                p1.add("Dados da exportação: Relatório das Faturas proforma" + ", exportado em: " + this.tempo.Date() + " às " + this.tempo.Hours() + " \n \n");
                doc.add(p1);

                PdfPTable tb = new PdfPTable(this.tabla.getColumnCount());
//                Acrescentar se é visitante ou não como cabeçalho
                Font cab = new Font(Font.FontFamily.TIMES_ROMAN, 10);
                PdfPCell cab1 = new PdfPCell(new Paragraph("Nº", cab));

                cab1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab1.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab2 = new PdfPCell(new Paragraph("Título", cab));
                cab2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab2.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab3 = new PdfPCell(new Paragraph("Desc.(Kz)", cab));
                cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab3.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab4 = new PdfPCell(new Paragraph("Data", cab));
                cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab4.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab5 = new PdfPCell(new Paragraph("Hora", cab));
                cab5.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab5.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab6 = new PdfPCell(new Paragraph("Ret. Fonte", cab));
                cab6.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab6.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab7 = new PdfPCell(new Paragraph("Total(Kz)", cab));
                cab7.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab7.setBackgroundColor(BaseColor.GRAY);

                tb.addCell(cab1);
                tb.addCell(cab2);
                tb.addCell(cab3);
                tb.addCell(cab4);
                tb.addCell(cab5);
                tb.addCell(cab6);
                tb.addCell(cab7);

                for (int i = 0; i < this.tabla.getRowCount(); i++) {
                    for (int j = 0; j < this.tabla.getColumnCount(); j++) {
                        PdfPCell cel = null;

                        if (null != GetData(tabla, i, j)) {
                            cel = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, j).toString(), cab));
                            ;
//                            System.out.println("tem valor");
                        }

                        if (cel == null) {
                            cel = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                        }
                        cel.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel.getColumn().updateFilledWidth(3);
                        tb.addCell(cel);

                    }
                }

                doc.add(tb);
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
//                doc.close();
                doc.close();
                    StampPageXofY stampPageXofY = new StampPageXofY();
                    stampPageXofY.setEstado(this.estadoLicenca);
                    String SRC = this.file.getAbsolutePath().toString();
                    String nome = this.file.getAbsolutePath().toString();
                    String DEST = this.file.getParent() + "/_" + this.file.getName();

                    File file = new File(DEST);
                    file.getParentFile().mkdirs();
                    try {

                        stampPageXofY.Paginar(SRC, DEST);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.file.delete();
                    file.renameTo(new File(nome));
//                    Desktop.getDesktop().open(new File(nome));
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }
            if (pint) {
                Desktop.getDesktop().print(file);

            }

        }
        String tb, coluna_tb;

            tb = "";
            coluna_tb = "";
        
//        this.ToByte(this.file, tb, coluna_tb);
    }   

    public void exportA4Liquidacao(boolean pint) throws IOException {
        BaseColor cor = new BaseColor(47, 117, 181);
        Document doc = null;
        OutputStream out = null;

        try {
            doc = new Document();
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 100);

                IMG.setAlignment(1);
                doc.add(IMG);

                Paragraph p1 = new Paragraph();
                p1.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                p1.setAlignment(1);
                p1.add("Dados da exportação: Relatório das Liquidações" + ", exportado em: " + this.tempo.Date() + " às " + this.tempo.Hours() + " \n \n");
                doc.add(p1);

                PdfPTable tb = new PdfPTable(this.tabla.getColumnCount());
//                Acrescentar se é visitante ou não como cabeçalho
                Font cab = new Font(Font.FontFamily.TIMES_ROMAN, 10);
                PdfPCell cab1 = new PdfPCell(new Paragraph("Nº", cab));

                cab1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab1.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab2 = new PdfPCell(new Paragraph("Título", cab));
                cab2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab2.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab3 = new PdfPCell(new Paragraph("Obectivo", cab));
                cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab3.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab4 = new PdfPCell(new Paragraph("Observacao", cab));
                cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab4.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab5 = new PdfPCell(new Paragraph("Data e Hora", cab));
                cab5.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab5.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab6 = new PdfPCell(new Paragraph("Valor da liquidação(Kz)", cab));
                cab6.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab6.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab7 = new PdfPCell(new Paragraph("Referente a fatura Nº", cab));
                cab7.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab7.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab8 = new PdfPCell(new Paragraph("Total a liquidar(Kz)", cab));
                cab8.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab8.setBackgroundColor(BaseColor.GRAY);

                tb.addCell(cab1);
                tb.addCell(cab2);
                tb.addCell(cab3);
                tb.addCell(cab4);
                tb.addCell(cab5);
                tb.addCell(cab6);
                tb.addCell(cab7);
                tb.addCell(cab8);

                for (int i = 0; i < this.tabla.getRowCount(); i++) {
                    for (int j = 0; j < this.tabla.getColumnCount(); j++) {
                        PdfPCell cel = null;

                        if (null != GetData(tabla, i, j)) {
                            cel = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, j).toString(), cab));
                            ;
//                            System.out.println("tem valor");
                        }

                        if (cel == null) {
                            cel = new PdfPCell(new Paragraph());

//                            System.out.println("sem valor");
                        }
                        cel.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel.getColumn().updateFilledWidth(3);
                        tb.addCell(cel);

                    }
                }

                doc.add(tb);
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
//                doc.close();
                doc.close();
                    StampPageXofY stampPageXofY = new StampPageXofY();
                    stampPageXofY.setEstado(this.estadoLicenca);
                    String SRC = this.file.getAbsolutePath().toString();
                    String nome = this.file.getAbsolutePath().toString();
                    String DEST = this.file.getParent() + "/_" + this.file.getName();

                    File file = new File(DEST);
                    file.getParentFile().mkdirs();
                    try {

                        stampPageXofY.Paginar(SRC, DEST);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.file.delete();
                    file.renameTo(new File(nome));
//                    Desktop.getDesktop().open(new File(nome));
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }
            if (pint) {
                Desktop.getDesktop().print(file);

            }

        }
        String tb, coluna_tb;

            tb = "";
            coluna_tb = "";
        
//        this.ToByte(this.file, tb, coluna_tb);
    }   

    public String Chang(double V) {

        BigDecimal valor = new BigDecimal(V);
        NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);
        String formatado = nf.format(valor);


        return formatado;

    }

    public String Chang(String V) {

        BigDecimal valor = new BigDecimal(V);
        NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);
        String formatado = nf.format(valor);


        return formatado;

    }

    public void exportA42_NotaCredito(String titulo_nota, String num_nota, String numRecibo, Double valor, String motivo) throws IOException {
        Document doc = null;
        OutputStream out = null;

        try {
//            doc = new Document(PageSize.A4, 30, 20, 20, 30);
             doc = new Document(PageSize.A4,30, 20, 50, 50);
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro " + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
//                Adicionar o logotipo da empresa
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 120);
                IMG.setAbsolutePosition(90, 720);
//                doc.add(IMG);

                BaseColor cor = new BaseColor(47, 117, 181);
                Paragraph P1Fatura = new Paragraph();
                P1Fatura.setFont(new Font(Font.FontFamily.HELVETICA, 20, 0, cor));
                P1Fatura.setAlignment(2);
//                P1Fatura.setSpacingAfter(40);
                P1Fatura.add("");
//                doc.add(P1Fatura);
                PdfPTable tb = new PdfPTable(2);

                PdfPCell celIMG = new PdfPCell();
                celIMG.setBorder(0);
                celIMG.addElement(IMG);
                tb.addCell(celIMG);

                PdfPCell celf = new PdfPCell(P1Fatura);
                celf.setBorder(0);
                tb.addCell(celf);

                tb.setSpacingBefore(30);
                Paragraph P1 = new Paragraph();
                Paragraph P2 = new Paragraph();
                Paragraph P3 = new Paragraph();
                Paragraph P4 = new Paragraph();

                P1.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P2.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P3.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P4.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P1.add(ME.getDesignacao());
                P2.add("Telefone: " + Integer.toString(ME.telefone));
                P3.add(ME.pais + ", " + ME.getProvincia() + ", " + ME.rua + "");
                P4.add("NIF: " + ME.getNif());

                PdfPCell cel1 = new PdfPCell();
                cel1.addElement(P1);
                cel1.addElement(P2);
                cel1.addElement(P3);
                cel1.addElement(P4);
                cel1.setBorder(0);

//                tb.setWidthPercentage(200);
                tb.addCell(cel1);

                Paragraph P5 = new Paragraph();
                Paragraph P6 = new Paragraph();
                Paragraph P7 = new Paragraph();
                Paragraph P8 = new Paragraph();

                P5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P6.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P7.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                P8.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

                P5.add(titulo_nota + " ");
                P6.add("  Data");

                P7.add(num_nota+"\nSEGUNDA VIA");
                P8.add(tempo.Date() + " " + tempo.Hours());

                PdfPTable TBin = new PdfPTable(2);
                PdfPCell cel3 = new PdfPCell();
                PdfPCell cel4 = new PdfPCell();
                PdfPCell cel5 = new PdfPCell(P7);
                PdfPCell cel6 = new PdfPCell(P8);

                cel3.setBorder(0);
                cel4.setBorder(0);
                cel5.setBorder(0);
                cel6.setBorder(0);

                cel3.setBackgroundColor(cor);
                cel3.addElement(P5);

                cel4.setBackgroundColor(cor);
                cel4.addElement(P6);

                TBin.addCell(cel3);
                TBin.addCell(cel4);
                TBin.addCell(cel5);
                TBin.addCell(cel6);

                PdfPCell celT = new PdfPCell(TBin);
                celT.setBorder(0);
                tb.addCell(celT);

                PdfPTable TBin2 = new PdfPTable(1);
                PdfPTable TBin3 = new PdfPTable(1);

                Paragraph P9 = new Paragraph();
                Paragraph P10 = new Paragraph();
                Paragraph P11 = new Paragraph();
                Paragraph P12 = new Paragraph();

                P9.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P10.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P11.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                P12.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
//                System.out.println("2222222222");
                P9.add("Cliente");
                P10.add("  NIF");
                P12.add("  " + MC.getNif());
                Paragraph dc1 = new Paragraph();
                Paragraph dc2 = new Paragraph();
                Paragraph dc3 = new Paragraph();
                Paragraph dc4 = new Paragraph();
                Paragraph dc5 = new Paragraph();
                dc1.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc2.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc3.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc4.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc1.add(MC.getDesignacao());
                dc2.add(MC.getEndereco());
                dc3.add(MC.getCidade());
                dc4.add(MC.getTelefone());
                dc5.add(MC.getEmail());

                PdfPCell cel7 = new PdfPCell();
                PdfPCell cel8 = new PdfPCell();
                PdfPCell cel9 = new PdfPCell();
                cel9.addElement(dc1);
                cel9.addElement(dc2);
                cel9.addElement(dc3);
                cel9.addElement(dc4);
                cel9.addElement(dc5);
                PdfPCell cel10 = new PdfPCell(P12);

                cel7.setBorder(0);
                cel8.setBorder(0);
                cel9.setBorder(0);
                cel10.setBorder(0);

                cel7.setBackgroundColor(cor);
                cel7.addElement(P9);

                cel8.setBackgroundColor(cor);
                cel8.addElement(P10);

                TBin2.addCell(cel7);
                TBin3.addCell(cel8);
                TBin2.addCell(cel9);
                TBin3.addCell(cel10);
                PdfPCell celT2 = new PdfPCell(TBin2);
                celT2.setBorder(0);
                PdfPCell celT3 = new PdfPCell(TBin3);
                celT3.setBorder(0);
                PdfPCell space = new PdfPCell(new Paragraph(" "));
                space.setBorder(0);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(celT2);
                tb.addCell(celT3);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                doc.add(tb);

               

                PdfPTable tbf = new PdfPTable(1);
                this.exten.setMeoda(" Kwanza");
                this.exten.setNumber(valor);
                String ValorPago = this.exten.show();
                //this.exten.setNumber(valorP);
                P9 = new Paragraph(
                        new Phrase("Nota retificativa dirigida a(o) cliente " + MC.getDesignacao()
                                + " indicando a devolução do valor total equivalente a " + Chang(valor)
                                + "(" + ValorPago
                                + ") Referente ao documento " + numRecibo + "\n\nMotivo: " + motivo, new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));

                P10 = new Paragraph(new Phrase("Assinatura do adequerente: ____________________________________", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK)));
                P9.setAlignment(1);
                P10.setAlignment(1);

                PdfPCell f1 = new PdfPCell(P9);
                PdfPCell f2 = new PdfPCell(P10);
                PdfPCell f3 = new PdfPCell(new Phrase(" "));
                f1.setBorder(0);
                f2.setBorder(0);
                f3.setBorder(0);

                tbf.addCell(f3);
                tbf.addCell(f3);
                tbf.addCell(f3);
                tbf.addCell(f1);
                tbf.addCell(f3);
                tbf.addCell(f3);
                tbf.addCell(f2);

                doc.add(tbf);

            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
                    doc.close();
                    StampPageXofY stampPageXofY = new StampPageXofY();
                    stampPageXofY.setEstado(this.estadoLicenca);
                    String SRC = this.file.getAbsolutePath().toString();
                    String nome = this.file.getAbsolutePath().toString();
                    String DEST = this.file.getParent() + "/_" + this.file.getName();

                    File file = new File(DEST);
                    file.getParentFile().mkdirs();
                    try {

                        stampPageXofY.Paginar(SRC, DEST);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.file.delete();
                    file.renameTo(new File(nome));
//                    Desktop.getDesktop().open(new File(nome));

            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }

        }
        String tb, coluna_tb;

        tb = "nota_de_credito";
        coluna_tb = "idnota_de_credito";
        
        this.ToByte(this.file, tb, coluna_tb);
    }

    public void exportA42_NotaDebito(String titulo_nota, String num_nota, String numRecibo, Double valor, String motivo) throws IOException {
        Document doc = null;
        OutputStream out = null;

        try {
//            doc = new Document(PageSize.A4, 30, 20, 20, 30);
             doc = new Document(PageSize.A4,30, 20, 50, 50);
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro 111" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
//                Adicionar o logotipo da empresa
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 120);
                IMG.setAbsolutePosition(90, 720);
//                doc.add(IMG);

                BaseColor cor = new BaseColor(47, 117, 181);
                Paragraph P1Fatura = new Paragraph();
                P1Fatura.setFont(new Font(Font.FontFamily.HELVETICA, 20, 0, cor));
                P1Fatura.setAlignment(2);
//                P1Fatura.setSpacingAfter(40);
                P1Fatura.add("");
//                doc.add(P1Fatura);
                PdfPTable tb = new PdfPTable(2);

                PdfPCell celIMG = new PdfPCell();
                celIMG.setBorder(0);
                celIMG.addElement(IMG);
                tb.addCell(celIMG);

                PdfPCell celf = new PdfPCell(P1Fatura);
                celf.setBorder(0);
                tb.addCell(celf);

                tb.setSpacingBefore(30);
                Paragraph P1 = new Paragraph();
                Paragraph P2 = new Paragraph();
                Paragraph P3 = new Paragraph();
                Paragraph P4 = new Paragraph();

                P1.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P2.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P3.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P4.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P1.add(ME.getDesignacao());
                P2.add("Telefone: " + Integer.toString(ME.telefone));
                P3.add(ME.pais + ", " + ME.getProvincia() + ", " + ME.rua + "");
                P4.add("NIF: " + ME.getNif());

                PdfPCell cel1 = new PdfPCell();
                cel1.addElement(P1);
                cel1.addElement(P2);
                cel1.addElement(P3);
                cel1.addElement(P4);
                cel1.setBorder(0);

//                tb.setWidthPercentage(200);
                tb.addCell(cel1);

                Paragraph P5 = new Paragraph();
                Paragraph P6 = new Paragraph();
                Paragraph P7 = new Paragraph();
                Paragraph P8 = new Paragraph();

                P5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P6.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P7.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                P8.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

                P5.add(titulo_nota + " ");
                P6.add("  Data");
                String NUM = "0";
//                if (num_nota > 9) {
//                    NUM = Integer.toString(num_nota);
//                } else {
//                    NUM = NUM+Integer.toString(num_nota);
//                }

                P7.add(num_nota);
                P8.add(tempo.Date() + " " + tempo.Hours());

                PdfPTable TBin = new PdfPTable(2);
                PdfPCell cel3 = new PdfPCell();
                PdfPCell cel4 = new PdfPCell();
                PdfPCell cel5 = new PdfPCell(P7);
                PdfPCell cel6 = new PdfPCell(P8);

                cel3.setBorder(0);
                cel4.setBorder(0);
                cel5.setBorder(0);
                cel6.setBorder(0);

                cel3.setBackgroundColor(cor);
                cel3.addElement(P5);

                cel4.setBackgroundColor(cor);
                cel4.addElement(P6);

                TBin.addCell(cel3);
                TBin.addCell(cel4);
                TBin.addCell(cel5);
                TBin.addCell(cel6);

                PdfPCell celT = new PdfPCell(TBin);
                celT.setBorder(0);
                tb.addCell(celT);

                PdfPTable TBin2 = new PdfPTable(1);
                PdfPTable TBin3 = new PdfPTable(1);

                Paragraph P9 = new Paragraph();
                Paragraph P10 = new Paragraph();
                Paragraph P11 = new Paragraph();
                Paragraph P12 = new Paragraph();

                P9.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P10.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P11.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                P12.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
//                System.out.println("2222222222");
                P9.add("Cliente");
                P10.add("  NIF");
                P12.add("  " + MC.getNif());
                Paragraph dc1 = new Paragraph();
                Paragraph dc2 = new Paragraph();
                Paragraph dc3 = new Paragraph();
                Paragraph dc4 = new Paragraph();
                Paragraph dc5 = new Paragraph();
                dc1.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc2.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc3.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc4.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc1.add(MC.getDesignacao());
                dc2.add(MC.getEndereco());
                dc3.add(MC.getCidade());
                dc4.add(MC.getTelefone());
                dc5.add(MC.getEmail());

                PdfPCell cel7 = new PdfPCell();
                PdfPCell cel8 = new PdfPCell();
                PdfPCell cel9 = new PdfPCell();
                cel9.addElement(dc1);
                cel9.addElement(dc2);
                cel9.addElement(dc3);
                cel9.addElement(dc4);
                cel9.addElement(dc5);
                PdfPCell cel10 = new PdfPCell(P12);

                cel7.setBorder(0);
                cel8.setBorder(0);
                cel9.setBorder(0);
                cel10.setBorder(0);

                cel7.setBackgroundColor(cor);
                cel7.addElement(P9);

                cel8.setBackgroundColor(cor);
                cel8.addElement(P10);

                TBin2.addCell(cel7);
                TBin3.addCell(cel8);
                TBin2.addCell(cel9);
                TBin3.addCell(cel10);
                PdfPCell celT2 = new PdfPCell(TBin2);
                celT2.setBorder(0);
                PdfPCell celT3 = new PdfPCell(TBin3);
                celT3.setBorder(0);
                PdfPCell space = new PdfPCell(new Paragraph(" "));
                space.setBorder(0);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(celT2);
                tb.addCell(celT3);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                doc.add(tb);


                PdfPTable tbf = new PdfPTable(1);
                this.exten.setMeoda(" Kwanza");
                this.exten.setNumber(valor);
                String ValorPago = this.exten.show();
                //this.exten.setNumber(valorP);
                P9 = new Paragraph(
                        new Phrase("Nota retificativa dirigida a(o) cliente " + MC.getDesignacao()
                                + " indicando a devolução do valor do imposto equivalente a " + Chang(valor)
                                + "(" + ValorPago
                                + ") Referente a F. recibo Nº  " + numRecibo + "\n\nMotivo: " + motivo, new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));

                P10 = new Paragraph(new Phrase("De: __________________________                                 Para: __________________________", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK)));
                P9.setAlignment(1);
                P10.setAlignment(1);

                PdfPCell f1 = new PdfPCell(P9);
                PdfPCell f2 = new PdfPCell(P10);
                PdfPCell f3 = new PdfPCell(new Phrase(" "));
                f1.setBorder(0);
                f2.setBorder(0);
                f3.setBorder(0);

                tbf.addCell(f3);
                tbf.addCell(f3);
                tbf.addCell(f3);
                tbf.addCell(f1);
                tbf.addCell(f3);
                tbf.addCell(f3);
                tbf.addCell(f2);

                doc.add(tbf);

            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
//                doc.close();
                // Desktop.getDesktop().open(new File(this.file.getAbsolutePath().toString()));
//                Desktop.getDesktop().print(new File(this.file.getAbsolutePath().toString()));
                doc.close();
                    StampPageXofY stampPageXofY = new StampPageXofY();
                    stampPageXofY.setEstado(this.estadoLicenca);
                    String SRC = this.file.getAbsolutePath().toString();
                    String nome = this.file.getAbsolutePath().toString();
                    String DEST = this.file.getParent() + "/_" + this.file.getName();

                    File file = new File(DEST);
                    file.getParentFile().mkdirs();
                    try {

                        stampPageXofY.Paginar(SRC, DEST);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.file.delete();
                    file.renameTo(new File(nome));
//                    Desktop.getDesktop().open(new File(nome));

            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }

        }
        String tb, coluna_tb;

            tb = "nota_de_debito";
            coluna_tb = "idnota_de_debito";
        
        this.ToByte(this.file, tb, coluna_tb);
    }

    public void export42_Liquidacao(String entidade, Double valor, Double valorP, String numFAT, String ID) throws IOException {
        Document doc = null;
        OutputStream out = null;

        try {
//            doc = new Document(PageSize.A4, 40, 20, 20, 40);
             doc = new Document(PageSize.A4,30, 20, 50, 50);
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
//                Adicionar o logotipo da empresa
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 120);
                IMG.setAbsolutePosition(90, 720);
//                doc.add(IMG);

                BaseColor cor = new BaseColor(47, 117, 181);
                Paragraph P1Fatura = new Paragraph();
                P1Fatura.setFont(new Font(Font.FontFamily.HELVETICA, 20, 0, cor));
                P1Fatura.setAlignment(2);
//                P1Fatura.setSpacingAfter(40);
                P1Fatura.add(" ");
//                doc.add(P1Fatura);
                PdfPTable tb = new PdfPTable(2);

                PdfPCell celIMG = new PdfPCell();
                celIMG.setBorder(0);
                celIMG.addElement(IMG);
                tb.addCell(celIMG);

                PdfPCell celf = new PdfPCell(P1Fatura);
                celf.setBorder(0);
                tb.addCell(celf);

                tb.setSpacingBefore(30);
                Paragraph P1 = new Paragraph();
                Paragraph P2 = new Paragraph();
                Paragraph P3 = new Paragraph();
                Paragraph P4 = new Paragraph();

                P1.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P2.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P3.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P4.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P1.add(ME.getDesignacao());
                P2.add("Telefone: " + Integer.toString(ME.telefone));
                P3.add(ME.pais + ", " + ME.getProvincia() + ", " + ME.rua + "");
                P4.add("NIF: " + ME.getNif());

                PdfPCell cel1 = new PdfPCell();
                cel1.addElement(P1);
                cel1.addElement(P2);
                cel1.addElement(P3);
                cel1.addElement(P4);
                cel1.setBorder(0);

//                tb.setWidthPercentage(200);
                tb.addCell(cel1);

                Paragraph P5 = new Paragraph();
                Paragraph P6 = new Paragraph();
                Paragraph P7 = new Paragraph();
                Paragraph P8 = new Paragraph();

                P5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P6.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P7.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                P8.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

                P5.add("Recibo Nº");
                P6.add("  Data");
                String NUM = "0";


                P7.add(ID+"\nSegunda via");
                P8.add(tempo.Date() + " " + tempo.Hours());

                PdfPTable TBin = new PdfPTable(2);
                PdfPCell cel3 = new PdfPCell();
                PdfPCell cel4 = new PdfPCell();
                PdfPCell cel5 = new PdfPCell(P7);
                PdfPCell cel6 = new PdfPCell(P8);

                cel3.setBorder(0);
                cel4.setBorder(0);
                cel5.setBorder(0);
                cel6.setBorder(0);

                cel3.setBackgroundColor(cor);
                cel3.addElement(P5);

                cel4.setBackgroundColor(cor);
                cel4.addElement(P6);

                TBin.addCell(cel3);
                TBin.addCell(cel4);
                TBin.addCell(cel5);
                TBin.addCell(cel6);

                PdfPCell celT = new PdfPCell(TBin);
                celT.setBorder(0);
                tb.addCell(celT);
                doc.add(tb);
                PdfPTable tbf = new PdfPTable(1);
                this.exten.setMeoda(" Kwanza");
                this.exten.setNumber(valor);
                String ValorPago = this.exten.show();
                this.exten.setNumber(valorP);
                Paragraph P9 = new Paragraph(new Phrase("Recebemos do(a) sr(a) " + entidade + " a quantida de " + Chang(valor) + " (" + ValorPago + ") referente a dívida de " + Chang(valorP) + " (" + this.exten.show() + ") da fatura Nº " + (numFAT), new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                Paragraph P10 = new Paragraph(new Phrase("De: __________________________                          Para: __________________________", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK)));
                P9.setAlignment(1);
                P10.setAlignment(1);

                PdfPCell f1 = new PdfPCell(P9);
                PdfPCell f2 = new PdfPCell(P10);
                PdfPCell f3 = new PdfPCell(new Phrase(" "));
                f1.setBorder(0);
                f2.setBorder(0);
                f3.setBorder(0);

                tbf.addCell(f3);
                tbf.addCell(f3);
                tbf.addCell(f3);
                tbf.addCell(f1);
                tbf.addCell(f3);
                tbf.addCell(f3);
                tbf.addCell(f2);

                doc.add(tbf);

            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
//                doc.close();
//                Desktop.getDesktop().open(new File(this.file.getAbsolutePath().toString()));
//                Desktop.getDesktop().print(new File(this.file.getAbsolutePath().toString()));
                doc.close();
                    StampPageXofY stampPageXofY = new StampPageXofY();
                    stampPageXofY.setEstado(this.estadoLicenca);
                    String SRC = this.file.getAbsolutePath().toString();
                    String nome = this.file.getAbsolutePath().toString();
                    String DEST = this.file.getParent() + "/_" + this.file.getName();

                    File file = new File(DEST);
                    file.getParentFile().mkdirs();
                    try {

                        stampPageXofY.Paginar(SRC, DEST);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.file.delete();
                    file.renameTo(new File(nome));
//                    Desktop.getDesktop().open(new File(nome));

            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }

        }
//        System.out.println("idliquidacao "+MF.getIndicativo());
        String tb, coluna_tb;

            tb = "liquidacao";
            coluna_tb = "idliquidacao";
        
        this.ToByte(this.file, tb, coluna_tb);
    }

    public void ExportarVendas(String tex1, boolean print) {
        BaseColor cor = new BaseColor(47, 117, 181);
        Document doc = null;
        OutputStream out = null;

        try {
            doc = new Document();
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 100);
//                IMG.setAbsolutePosition(400, 700);
                IMG.setAlignment(1);
                doc.add(IMG);

//                Image imagem;
//                imagem = Image.getInstance("NetBeansProjects\\Somoil\\src\\Modelo\\download.png");
//                doc.add(imagem);
                Paragraph p1 = new Paragraph();
                p1.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                p1.setAlignment(1);
                p1.add("\n\n" + tex1 + "\nData de exportação: " + tempo.Date() + " " + tempo.Hours());
                doc.add(p1);
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                PdfPTable tb = new PdfPTable(this.tabla.getColumnCount());
//                Acrescentar se é visitante ou não como cabeçalho
                Font cab = new Font(Font.FontFamily.TIMES_ROMAN, 10);
                PdfPCell cab1 = new PdfPCell(new Paragraph("Nº", cab));

                cab1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab1.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab2 = new PdfPCell(new Paragraph("Descrição", cab));
                cab2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab2.setBackgroundColor(BaseColor.GRAY);
                PdfPCell cab3 = new PdfPCell(new Paragraph("Stock", cab));
                cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab3.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab4 = new PdfPCell(new Paragraph("Físico", cab));
                cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab4.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab5 = new PdfPCell(new Paragraph("Dif", cab));
                cab5.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab5.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab8 = new PdfPCell(new Paragraph("Data", cab));
                cab8.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab8.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab9 = new PdfPCell(new Paragraph("Hora", cab));
                cab9.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab9.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab7 = new PdfPCell(new Paragraph("Armazém", cab));
                cab7.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab7.setBackgroundColor(BaseColor.GRAY);

//                PdfPCell cab10 = new PdfPCell(new Paragraph("Nº Fat/rec", cab));
//                cab10.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cab10.setBackgroundColor(BaseColor.GRAY);
//
//                PdfPCell cab11 = new PdfPCell(new Paragraph("IVA", cab));
//                cab11.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cab11.setBackgroundColor(BaseColor.GRAY);
                tb.addCell(cab1);
                tb.addCell(cab2);
                tb.addCell(cab3);
                tb.addCell(cab4);
                tb.addCell(cab5);
                //tb.addCell(cab6);

                tb.addCell(cab8);
                tb.addCell(cab9);
                tb.addCell(cab7);
//                tb.addCell(cab10);
//                tb.addCell(cab11);

                for (int i = 0; i < this.tabla.getRowCount(); i++) {
                    PdfPCell cel1 = null;
                    cel1 = new PdfPCell(new Paragraph(Integer.toString(i + 1), cab));

                    cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel1.getColumn().updateFilledWidth(3);
                    tb.addCell(cel1);

                    for (int j = 1; j < this.tabla.getColumnCount(); j++) {
                        PdfPCell cel = null;

                        if (null != GetData(tabla, i, j)) {
                            cel = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, j).toString(), cab));
                            ;
                            // System.out.println("tem valor");
                        }

                        if (cel == null) {
                            cel = new PdfPCell(new Paragraph());
                            // System.out.println("sem valor");
                        }
                        cel.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel.getColumn().updateFilledWidth(3);
                        tb.addCell(cel);

                    }
                }

                doc.add(tb);
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
                doc.close();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }

        }
        if (print) {
            try {
                Desktop.getDesktop().print(file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível imprimir.");
            }

        }
        String tb, coluna_tb;

            tb = "";
            coluna_tb = "";
        
//        this.ToByte(this.file, tb, coluna_tb);
    }

    public void ExportarAgenda(String tex1, boolean print) {
        BaseColor cor = new BaseColor(47, 117, 181);
        Document doc = null;
        OutputStream out = null;

        try {
            doc = new Document();
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 100);
//                IMG.setAbsolutePosition(400, 700);
                IMG.setAlignment(1);
                doc.add(IMG);

//                Image imagem;
//                imagem = Image.getInstance("NetBeansProjects\\Somoil\\src\\Modelo\\download.png");
//                doc.add(imagem);
                Paragraph p1 = new Paragraph();
                p1.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                p1.setAlignment(1);
                p1.add(tex1);

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                doc.add(p1);
                doc.add(new Paragraph(" "));
                PdfPTable tb = new PdfPTable(this.tabla.getColumnCount());
//                Acrescentar se é visitante ou não como cabeçalho
                Font cab = new Font(Font.FontFamily.TIMES_ROMAN, 10);
                PdfPCell cab1 = new PdfPCell(new Paragraph("Nº", cab));

                cab1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab1.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab2 = new PdfPCell(new Paragraph("Tarefa", cab));
                cab2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab2.setBackgroundColor(BaseColor.GRAY);
                PdfPCell cab3 = new PdfPCell(new Paragraph("Data", cab));
                cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab3.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab4 = new PdfPCell(new Paragraph("Responsável", cab));
                cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab4.setBackgroundColor(BaseColor.GRAY);

                tb.addCell(cab1);
                tb.addCell(cab2);
                tb.addCell(cab3);
                tb.addCell(cab4);

                for (int i = 0; i < this.tabla.getRowCount(); i++) {
                    for (int j = 0; j < this.tabla.getColumnCount(); j++) {
                        PdfPCell cel = null;

                        if (null != GetData(tabla, i, j)) {
                            cel = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, j).toString(), cab));
                            ;
                            // System.out.println("tem valor");
                        }

                        if (cel == null) {
                            cel = new PdfPCell(new Paragraph());
                            // System.out.println("sem valor");
                        }
                        cel.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel.getColumn().updateFilledWidth(3);
                        tb.addCell(cel);

                    }
                }

                doc.add(tb);
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
                doc.close();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }

        }
        if (print) {
            try {
                Desktop.getDesktop().print(file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível imprimir.");
            }

        }
        String tb, coluna_tb;

            tb = "";
            coluna_tb = "";
        
//        this.ToByte(this.file, tb, coluna_tb);
    }

    public void Saida(String tex1, boolean print) {
        BaseColor cor = new BaseColor(47, 117, 181);
        Document doc = null;
        OutputStream out = null;

        try {
            doc = new Document();
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 100);

                IMG.setAlignment(1);
                doc.add(IMG);

                Paragraph p1 = new Paragraph();
                p1.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                p1.setAlignment(1);
                p1.add(tex1);

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                doc.add(p1);
                doc.add(new Paragraph(" "));
                PdfPTable tb = new PdfPTable(this.tabla.getColumnCount());
//                Acrescentar se é visitante ou não como cabeçalho
                Font cab = new Font(Font.FontFamily.TIMES_ROMAN, 10);
                PdfPCell cab1 = new PdfPCell(new Paragraph("Nº", cab));

                cab1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab1.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab2 = new PdfPCell(new Paragraph("Tarefa", cab));
                cab2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab2.setBackgroundColor(BaseColor.GRAY);
                PdfPCell cab3 = new PdfPCell(new Paragraph("Data", cab));
                cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab3.setBackgroundColor(BaseColor.GRAY);

                PdfPCell cab4 = new PdfPCell(new Paragraph("Responsável", cab));
                cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab4.setBackgroundColor(BaseColor.GRAY);

                tb.addCell(cab1);
                tb.addCell(cab2);
                tb.addCell(cab3);
                tb.addCell(cab4);

                for (int i = 0; i < this.tabla.getRowCount(); i++) {
                    for (int j = 0; j < this.tabla.getColumnCount(); j++) {
                        PdfPCell cel = null;

                        if (null != GetData(tabla, i, j)) {
                            cel = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, j).toString(), cab));
                            ;
                            // System.out.println("tem valor");
                        }

                        if (cel == null) {
                            cel = new PdfPCell(new Paragraph());
                            // System.out.println("sem valor");
                        }
                        cel.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cel.getColumn().updateFilledWidth(3);
                        tb.addCell(cel);

                    }
                }

                doc.add(tb);
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
                doc.close();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }

        }
        if (print) {
            try {
                Desktop.getDesktop().print(file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível imprimir.");
            }

        }
        String tb, coluna_tb;

            tb = "";
            coluna_tb = "";
        
//        this.ToByte(this.file, tb, coluna_tb);
    }

    public void exportA4Saida(int num, String DATA_R, String HORA_R) throws IOException {
        Document doc = null;
        OutputStream out = null;

        try {
//            doc = new Document(PageSize.A4, 30, 20, 20, 30);
             doc = new Document(PageSize.A4,30, 20, 50, 50);
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
//                Adicionar o logotipo da empresa
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 120);
                IMG.setAbsolutePosition(90, 720);
//                doc.add(IMG);

                BaseColor cor = new BaseColor(47, 117, 181);
                Paragraph P1Fatura = new Paragraph();
                P1Fatura.setFont(new Font(Font.FontFamily.HELVETICA, 20, 0, cor));
                P1Fatura.setAlignment(2);
//                P1Fatura.setSpacingAfter(40);
                P1Fatura.add(" ");
//                doc.add(P1Fatura);
                PdfPTable tb = new PdfPTable(2);

                PdfPCell celIMG = new PdfPCell();
                celIMG.setBorder(0);
                celIMG.addElement(IMG);
                tb.addCell(celIMG);

                PdfPCell celf = new PdfPCell(P1Fatura);
                celf.setBorder(0);
                tb.addCell(celf);

                tb.setSpacingBefore(30);
                Paragraph P1 = new Paragraph();
                Paragraph P2 = new Paragraph();
                Paragraph P3 = new Paragraph();
                Paragraph P4 = new Paragraph();

                P1.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P2.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P3.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P4.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P1.add(ME.getDesignacao());
                P2.add("Telefone: " + Integer.toString(ME.telefone));
                P3.add(ME.pais + ", " + ME.getProvincia() + ", " + ME.rua + "");
                P4.add("NIF: " + ME.getNif());

                PdfPCell cel1 = new PdfPCell();
                cel1.addElement(P1);
                cel1.addElement(P2);
                cel1.addElement(P3);
                cel1.addElement(P4);
                cel1.setBorder(0);

//                tb.setWidthPercentage(200);
                tb.addCell(cel1);

                Paragraph P5 = new Paragraph();
                Paragraph P6 = new Paragraph();
                Paragraph P7 = new Paragraph();
                Paragraph P8 = new Paragraph();

                P5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P6.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P7.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                P8.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

                P5.add("BAIXA DE PRODUTO" + " Nº");
                P6.add("  Data");
                String NUM = "0";
                if (num > 9) {
                    NUM = Integer.toString(num);
                } else {
                    NUM = NUM + Integer.toString(num);
                }

                P7.add(NUM);
                P8.add(DATA_R + " " + HORA_R);

                PdfPTable TBin = new PdfPTable(2);
                PdfPCell cel3 = new PdfPCell();
                PdfPCell cel4 = new PdfPCell();
                PdfPCell cel5 = new PdfPCell(P7);
                PdfPCell cel6 = new PdfPCell(P8);

                cel3.setBorder(0);
                cel4.setBorder(0);
                cel5.setBorder(0);
                cel6.setBorder(0);

                cel3.setBackgroundColor(cor);
                cel3.addElement(P5);

                cel4.setBackgroundColor(cor);
                cel4.addElement(P6);

                TBin.addCell(cel3);
                TBin.addCell(cel4);
                TBin.addCell(cel5);
                TBin.addCell(cel6);

                PdfPCell celT = new PdfPCell(TBin);
                celT.setBorder(0);
                tb.addCell(celT);

                PdfPTable TBin2 = new PdfPTable(1);
                PdfPTable TBin3 = new PdfPTable(1);

                Paragraph P9 = new Paragraph();
                Paragraph P10 = new Paragraph();
                Paragraph P11 = new Paragraph();
                Paragraph P12 = new Paragraph();

                P9.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P10.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P11.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                P12.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

//                P9.add(" ssss ");
//                P10.add("  sss  ");
//                P12.add(" sss ");
                Paragraph dc1 = new Paragraph();
                Paragraph dc2 = new Paragraph();
                Paragraph dc3 = new Paragraph();
                Paragraph dc4 = new Paragraph();
                Paragraph dc5 = new Paragraph();
                dc1.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc2.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc3.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc4.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
//                dc1.add(" 111  ");
//                dc2.add(" 222  ");
//                dc3.add("  111 ");
//                dc4.add(" 222  ");
//                dc5.add(" 2222  ");

                PdfPCell cel7 = new PdfPCell();
                PdfPCell cel8 = new PdfPCell();
                PdfPCell cel9 = new PdfPCell();
                cel9.addElement(dc1);
                cel9.addElement(dc2);
                cel9.addElement(dc3);
                cel9.addElement(dc4);
                cel9.addElement(dc5);
                PdfPCell cel10 = new PdfPCell(P12);

                cel7.setBorder(0);
                cel8.setBorder(0);
                cel9.setBorder(0);
                cel10.setBorder(0);

                cel7.setBackgroundColor(cor);
                cel7.addElement(P9);

                cel8.setBackgroundColor(cor);
                cel8.addElement(P10);

                TBin2.addCell(cel7);
                TBin3.addCell(cel8);
                TBin2.addCell(cel9);
                TBin3.addCell(cel10);
                PdfPCell celT2 = new PdfPCell(TBin2);
                celT2.setBorder(0);
                PdfPCell celT3 = new PdfPCell(TBin3);
                celT3.setBorder(0);
                PdfPCell space = new PdfPCell(new Paragraph(" "));
                space.setBorder(0);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(celT2);
                tb.addCell(celT3);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                doc.add(tb);

//             SECOND HALF 
                PdfPTable tbMaterial = new PdfPTable(7);

                PdfPCell cab3 = new PdfPCell(new Paragraph("ID", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab3.setBackgroundColor(cor);
                cab3.setBorder(0);

                PdfPCell cab4 = new PdfPCell(new Paragraph("Descrição", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab4.setBackgroundColor(cor);
                cab4.setBorder(0);

                PdfPCell cab5 = new PdfPCell(new Paragraph("QNT.", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab5.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab5.setBackgroundColor(cor);
                cab5.setBorder(0);

                PdfPCell cabVA = new PdfPCell(new Paragraph("Lote", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cabVA.setHorizontalAlignment(Element.ALIGN_CENTER);
                cabVA.setBackgroundColor(cor);
                cabVA.setBorder(0);

                PdfPCell cab6 = new PdfPCell(new Paragraph("Custo(KZ)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab6.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab6.setBackgroundColor(cor);
                cab6.setBorder(0);
                PdfPCell cab7 = new PdfPCell(new Paragraph("Total(KZ)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab7.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab7.setBackgroundColor(cor);
                cab7.setBorder(0);
                PdfPCell cab8 = new PdfPCell(new Paragraph("Armazem", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab8.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab8.setBackgroundColor(cor);
                cab8.setBorder(0);

                tbMaterial.addCell(cab3);
                tbMaterial.addCell(cab4);
                tbMaterial.addCell(cab5);
                tbMaterial.addCell(cabVA);
                tbMaterial.addCell(cab6);
                tbMaterial.addCell(cab7);
                tbMaterial.addCell(cab8);

                double TOTAL = 0;

                for (int i = 0; i < this.tabla.getRowCount(); i++) {

                    PdfPCell cel11 = null;
                    PdfPCell cel21 = null;
                    PdfPCell cel31 = null;
                    PdfPCell cel41 = null;
                    PdfPCell cel51 = null;
                    PdfPCell cel61 = null;
                    PdfPCell cel71 = null;
                    PdfPCell cel81 = null;

                    if (null != GetData(tabla, i, 0)) {
                        cel11 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 0).toString()));

                        System.out.println("tem valor 1:" + this.tabla.getModel().getValueAt(i, 0).toString());
                    } else {
                        cel11 = new PdfPCell(new Paragraph(" "));
                        System.out.println("Sem valor 1");
                    }

                    cel11.setBorder(0);
                    cel11.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel11.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel11);

                    if (null != GetData(tabla, i, 1)) {
                        cel21 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 1).toString()));

                        System.out.println("tem valor 2");
                    } else {
                        cel21 = new PdfPCell(new Paragraph(" "));

                        System.out.println("sem valor 2");
                    }
                    cel21.setBorder(0);
                    cel21.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel21.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel21);

                    if (null != GetData(tabla, i, 2)) {
                        cel31 = new PdfPCell(new Paragraph((this.tabla.getModel().getValueAt(i, 2).toString())));

                        System.out.println("tem valor 3");
                    } else {
                        cel31 = new PdfPCell(new Paragraph(" "));

                        System.out.println("sem valor 3");
                    }
                    cel31.setBorder(0);
                    cel31.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel31.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel31);

                    if (null != GetData(tabla, i, 3)) {
                        cel41 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 3).toString()));

                        System.out.println("tem valor 4");
                    } else {
                        cel41 = new PdfPCell(new Paragraph(" "));

                        System.out.println("sem valor 4");
                    }
                    cel41.setBorder(0);
                    cel41.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel41.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel41);

                    if (null != GetData(tabla, i, 4)) {
                        cel51 = new PdfPCell(new Paragraph((this.tabla.getModel().getValueAt(i, 4).toString())));

                        System.out.println("tem valor 5");
                    } else {
                        cel51 = new PdfPCell(new Paragraph(" "));

                        System.out.println("sem valor 5");
                    }
                    cel51.setBorder(0);
                    cel51.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel51.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel51);

                    if (null != GetData(tabla, i, 5)) {
                        String stotal = this.tabla.getModel().getValueAt(i, 5).toString();
                        cel61 = new PdfPCell(new Paragraph((stotal)));
                        TOTAL += Double.parseDouble(Convert(stotal));
                        System.out.println("7 tota: " + TOTAL);
                    } else {
                        cel61 = new PdfPCell(new Paragraph(" "));
                        System.out.println("7 tota: " + TOTAL);
//                            System.out.println("sem valor");
                    }
                    cel61.setBorder(0);
                    cel61.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel61.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel61);

                    if (null != GetData(tabla, i, 6)) {
                        cel71 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 6).toString()));

                        System.out.println("tem valor 8");
                    } else {
                        cel71 = new PdfPCell(new Paragraph(" "));

                        System.out.println("sem valor 8");
                    }
                    cel71.setBorder(0);
                    cel71.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel71.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel71);
                }

                space.setBorder(0);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);

                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);

                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);

                doc.add(tbMaterial);

                System.out.println("ADD MT");

                //            THIRD HALF 
                PdfPTable tbResumo = new PdfPTable(3);
                PdfPCell side1 = new PdfPCell(new Paragraph("Este documento não serve de factura.", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                side1.setHorizontalAlignment(Element.ALIGN_LEFT);
                side1.setBackgroundColor(BaseColor.WHITE);
                side1.setBorder(0);
                side1.getColumn().updateFilledWidth(8);
                tbResumo.addCell(side1);

                PdfPCell side2 = new PdfPCell(new Paragraph("Baixa de produto.  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side2.setHorizontalAlignment(Element.ALIGN_LEFT);
                side2.setBackgroundColor(new BaseColor(189, 215, 238));
                side2.setBorder(0);
                side2.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side2);
                PdfPCell side3 = new PdfPCell(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side3.setHorizontalAlignment(Element.ALIGN_LEFT);
                side3.setBackgroundColor(new BaseColor(221, 235, 247));
                side3.setBorder(0);
                side3.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side3);
                PdfPCell side12 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                side12.setHorizontalAlignment(Element.ALIGN_LEFT);
                side12.setBackgroundColor(BaseColor.WHITE);
                side12.setBorder(0);
                side12.getColumn().updateFilledWidth(8);
                tbResumo.addCell(side12);
                PdfPCell side22 = new PdfPCell(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side22.setHorizontalAlignment(Element.ALIGN_LEFT);
                side22.setBackgroundColor(new BaseColor(189, 215, 238));
                side22.setBorder(0);
                side22.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side22);
                PdfPCell side32 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side32.setHorizontalAlignment(Element.ALIGN_LEFT);
                side32.setBackgroundColor(new BaseColor(221, 235, 247));
                side32.setBorder(0);
                side32.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side32);
                PdfPCell side13 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                side13.setHorizontalAlignment(Element.ALIGN_LEFT);
                side13.setBackgroundColor(BaseColor.WHITE);
                side13.setBorder(0);
                side13.getColumn().updateFilledWidth(8);
                tbResumo.addCell(side13);
                PdfPCell side23 = new PdfPCell(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side23.setHorizontalAlignment(Element.ALIGN_LEFT);
                side23.setBackgroundColor(new BaseColor(189, 215, 238));
                side23.setBorder(0);
                side23.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side23);
                PdfPCell side33 = new PdfPCell(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side33.setHorizontalAlignment(Element.ALIGN_LEFT);
                side33.setBackgroundColor(new BaseColor(221, 235, 247));
                side33.setBorder(0);
                side33.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side33);
                PdfPCell side14 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                side14.setHorizontalAlignment(Element.ALIGN_LEFT);
                side14.setBackgroundColor(BaseColor.WHITE);
                side14.setBorder(0);
                side14.getColumn().updateFilledWidth(8);
                tbResumo.addCell(side14);
                PdfPCell side24 = new PdfPCell(new Paragraph("Total", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLDITALIC, cor)));
                side24.setHorizontalAlignment(Element.ALIGN_LEFT);
                side24.setBackgroundColor(new BaseColor(189, 215, 238));
                side24.setBorder(0);
                side24.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side24);
                double t = TOTAL;
                this.exten.setNumber(t);
                this.exten.setMeoda("Kwanza");
//                System.out.println(t);
                System.out.println(exten.show());
                PdfPCell side34 = new PdfPCell(new Paragraph(Chang(TOTAL) + exten.getMeoda() + "(s)\n" + this.exten.show() + " " + exten.getMeoda() + "(s)\n", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLDITALIC, BaseColor.BLACK)));
                side34.setHorizontalAlignment(Element.ALIGN_LEFT);
                side34.setBackgroundColor(new BaseColor(221, 235, 247));
                side34.setBorder(0);
                side34.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side34);

                doc.add(tbResumo);
                PdfPTable tbCoordenadas = new PdfPTable(2);
                tbCoordenadas.setHorizontalAlignment(1);
                tbCoordenadas.setSpacingBefore(100);
                Paragraph P = new Paragraph();
                P.setFont(new Font(Font.FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK));
                this.exten.setMeoda("Kwanza");

                ControloBD liga = new ControloBD();
                liga.setCaminho(this.caminho);
                liga.conexao();
                Paragraph Cord = new Paragraph();
                ;

                P.add("Este documento não serve de fatura. \n" + this.licenca + "\nOperador(a) " + MU.getNome() + ".\n");

                tbCoordenadas.addCell(new PdfPCell(P));
                tbCoordenadas.addCell(new PdfPCell(Cord));

                doc.add(tbCoordenadas);
                doc.addAuthor(MU.getNome());
                //doc.addTitle("Baixa "+NUM);
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
//                doc.close();
//                Desktop.getDesktop().open(new File(this.file.getAbsolutePath().toString()));
                doc.close();
                    StampPageXofY stampPageXofY = new StampPageXofY();
                    stampPageXofY.setEstado(this.estadoLicenca);
                    String SRC = this.file.getAbsolutePath().toString();
                    String nome = this.file.getAbsolutePath().toString();
                    String DEST = this.file.getParent() + "/_" + this.file.getName();

                    File file = new File(DEST);
                    file.getParentFile().mkdirs();
                    try {

                        stampPageXofY.Paginar(SRC, DEST);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.file.delete();
                    file.renameTo(new File(nome));
//                    Desktop.getDesktop().open(new File(nome));
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }

        }
        String tb, coluna_tb;

            tb = "";
            coluna_tb = "";
        
//        this.ToByte(this.file, tb, coluna_tb);
    }   

    public void exportA4Entrada(int num, String DATA_R, String HORA_R, String armazem, String pagamento) throws IOException {
        Document doc = null;
        OutputStream out = null;

        try {
//            doc = new Document(PageSize.A4, 30, 20, 20, 30);
             doc = new Document(PageSize.A4,30, 20, 50, 50);
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
//                Adicionar o logotipo da empresa
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 120);
                IMG.setAbsolutePosition(90, 720);
//                doc.add(IMG);

                BaseColor cor = new BaseColor(47, 117, 181);
                Paragraph P1Fatura = new Paragraph();
                P1Fatura.setFont(new Font(Font.FontFamily.HELVETICA, 20, 0, cor));
                P1Fatura.setAlignment(2);
//                P1Fatura.setSpacingAfter(40);
                P1Fatura.add(" ");
//                doc.add(P1Fatura);
                PdfPTable tb = new PdfPTable(2);

                PdfPCell celIMG = new PdfPCell();
                celIMG.setBorder(0);
                celIMG.addElement(IMG);
                tb.addCell(celIMG);

                PdfPCell celf = new PdfPCell(P1Fatura);
                celf.setBorder(0);
                tb.addCell(celf);

                tb.setSpacingBefore(30);
                Paragraph P1 = new Paragraph();
                Paragraph P2 = new Paragraph();
                Paragraph P3 = new Paragraph();
                Paragraph P4 = new Paragraph();

                P1.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P2.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P3.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P4.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P1.add(ME.getDesignacao());
                P2.add("Telefone: " + Integer.toString(ME.telefone));
                P3.add(ME.pais + ", " + ME.getProvincia() + ", " + ME.rua + "");
                P4.add("NIF: " + ME.getNif());

                PdfPCell cel1 = new PdfPCell();
                cel1.addElement(P1);
                cel1.addElement(P2);
                cel1.addElement(P3);
                cel1.addElement(P4);
                cel1.setBorder(0);

//                tb.setWidthPercentage(200);
                tb.addCell(cel1);

                Paragraph P5 = new Paragraph();
                Paragraph P6 = new Paragraph();
                Paragraph P7 = new Paragraph();
                Paragraph P8 = new Paragraph();

                P5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P6.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P7.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                P8.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

                P5.add("ENTRADA EM STOCK Nº");
                P6.add("  Data");
                String NUM = "0";
                if (num > 9) {
                    NUM = Integer.toString(num);
                } else {
                    NUM = NUM + Integer.toString(num);
                }

                P7.add(NUM);
                P8.add(DATA_R + " " + HORA_R);

                PdfPTable TBin = new PdfPTable(2);
                PdfPCell cel3 = new PdfPCell();
                PdfPCell cel4 = new PdfPCell();
                PdfPCell cel5 = new PdfPCell(P7);
                PdfPCell cel6 = new PdfPCell(P8);

                cel3.setBorder(0);
                cel4.setBorder(0);
                cel5.setBorder(0);
                cel6.setBorder(0);

                cel3.setBackgroundColor(cor);
                cel3.addElement(P5);

                cel4.setBackgroundColor(cor);
                cel4.addElement(P6);

                TBin.addCell(cel3);
                TBin.addCell(cel4);
                TBin.addCell(cel5);
                TBin.addCell(cel6);

                PdfPCell celT = new PdfPCell(TBin);
                celT.setBorder(0);
                tb.addCell(celT);

                PdfPTable TBin2 = new PdfPTable(1);
                PdfPTable TBin3 = new PdfPTable(1);

                Paragraph P9 = new Paragraph();
                Paragraph P10 = new Paragraph();
                Paragraph P11 = new Paragraph();
                Paragraph P12 = new Paragraph();

                P9.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P10.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P11.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                P12.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

                P9.add("NOME DO FORNECEDOR");
                P10.add(" CONTRIBUINTE");
                P12.add("  " + MC.getNif());
                Paragraph dc1 = new Paragraph();
                Paragraph dc2 = new Paragraph();
                Paragraph dc3 = new Paragraph();
                Paragraph dc4 = new Paragraph();
                Paragraph dc5 = new Paragraph();
                dc1.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc2.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc3.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc4.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc1.add(MC.getIdCliente() + " " + MC.getDesignacao());
                dc2.add(" ");
                dc3.add(" ");
                dc4.add(" ");
                dc5.add(" ");

                PdfPCell cel7 = new PdfPCell();
                PdfPCell cel8 = new PdfPCell();
                PdfPCell cel9 = new PdfPCell();
                cel9.addElement(dc1);
                cel9.addElement(dc2);
                cel9.addElement(dc3);
                cel9.addElement(dc4);
                cel9.addElement(dc5);
                PdfPCell cel10 = new PdfPCell(P12);

                cel7.setBorder(0);
                cel8.setBorder(0);
                cel9.setBorder(0);
                cel10.setBorder(0);

                cel7.setBackgroundColor(cor);
                cel7.addElement(P9);

                cel8.setBackgroundColor(cor);
                cel8.addElement(P10);

                TBin2.addCell(cel7);
                TBin3.addCell(cel8);
                TBin2.addCell(cel9);
                TBin3.addCell(cel10);
                PdfPCell celT2 = new PdfPCell(TBin2);
                celT2.setBorder(0);
                PdfPCell celT3 = new PdfPCell(TBin3);
                celT3.setBorder(0);
                PdfPCell space = new PdfPCell(new Paragraph(" "));
                space.setBorder(0);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(celT2);
                tb.addCell(celT3);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                doc.add(tb);

//             SECOND HALF 
                PdfPTable tbMaterial = new PdfPTable(7);

                PdfPCell cab3 = new PdfPCell(new Paragraph("ID", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab3.setBackgroundColor(cor);
                cab3.setBorder(0);

                PdfPCell cab4 = new PdfPCell(new Paragraph("Descrição", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab4.setBackgroundColor(cor);
                cab4.setBorder(0);

                PdfPCell cab5 = new PdfPCell(new Paragraph("Cuto (kz)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab5.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab5.setBackgroundColor(cor);
                cab5.setBorder(0);

                PdfPCell cabVA = new PdfPCell(new Paragraph("QNT", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cabVA.setHorizontalAlignment(Element.ALIGN_CENTER);
                cabVA.setBackgroundColor(cor);
                cabVA.setBorder(0);

                PdfPCell cab6 = new PdfPCell(new Paragraph("Total(KZ)", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab6.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab6.setBackgroundColor(cor);
                cab6.setBorder(0);
                PdfPCell cab7 = new PdfPCell(new Paragraph("Lote", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab7.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab7.setBackgroundColor(cor);
                cab7.setBorder(0);
                PdfPCell cab8 = new PdfPCell(new Paragraph("Armazem", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab8.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab8.setBackgroundColor(cor);
                cab8.setBorder(0);

                tbMaterial.addCell(cab3);
                tbMaterial.addCell(cab4);
                tbMaterial.addCell(cab5);
                tbMaterial.addCell(cabVA);
                tbMaterial.addCell(cab6);
                tbMaterial.addCell(cab7);
                tbMaterial.addCell(cab8);

                double TOTAL = 0;

                for (int i = 0; i < this.tabla.getRowCount(); i++) {

                    PdfPCell cel11 = null;
                    PdfPCell cel21 = null;
                    PdfPCell cel31 = null;
                    PdfPCell cel41 = null;
                    PdfPCell cel51 = null;
                    PdfPCell cel61 = null;
                    PdfPCell cel71 = null;
                    PdfPCell cel81 = null;

                    if (null != GetData(tabla, i, 0)) {
                        cel11 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 0).toString()));

//                            System.out.println("tem valor 1:"+this.tabla.getModel().getValueAt(i, 0).toString() );
                    } else {
                        cel11 = new PdfPCell(new Paragraph(" "));
//                            System.out.println("Sem valor 1" );
                    }

                    cel11.setBorder(0);
                    cel11.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel11.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel11);

                    if (null != GetData(tabla, i, 1)) {
                        cel21 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 1).toString()));

//                            System.out.println("tem valor 2");
                    } else {
                        cel21 = new PdfPCell(new Paragraph(" "));

//                            System.out.println("sem valor 2");
                    }
                    cel21.setBorder(0);
                    cel21.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel21.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel21);

                    if (null != GetData(tabla, i, 2)) {
                        cel31 = new PdfPCell(new Paragraph((this.tabla.getModel().getValueAt(i, 2).toString())));

//                            System.out.println("tem valor 3");
                    } else {
                        cel31 = new PdfPCell(new Paragraph(" "));

//                            System.out.println("sem valor 3");
                    }
                    cel31.setBorder(0);
                    cel31.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel31.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel31);

                    if (null != GetData(tabla, i, 3)) {
//                        cel41 = new PdfPCell(new Paragraph(" "));

                        cel41 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 3).toString()));

//                            System.out.println("tem valor 4");
                    } else {
                        cel41 = new PdfPCell(new Paragraph(" "));

//                            System.out.println("sem valor 4");
                    }
                    cel41.setBorder(0);
                    cel41.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel41.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel41);

                    if (null != GetData(tabla, i, 4)) {
                        String stotal = this.tabla.getModel().getValueAt(i, 4).toString();
                        cel61 = new PdfPCell(new Paragraph((stotal)));
                        TOTAL += Double.parseDouble(Convert(stotal));
//                            System.out.println("7 tota: "+TOTAL);
                    } else {
                        cel61 = new PdfPCell(new Paragraph(" "));
//                        System.out.println("7 tota: "+TOTAL);
//                            System.out.println("sem valor");
                    }
                    cel61.setBorder(0);
                    cel61.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel61.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel61);

                    if (null != GetData(tabla, i, 5)) {
                        cel51 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 5).toString()));

//                            System.out.println("tem valor 5");
                    } else {
                        cel51 = new PdfPCell(new Paragraph(" "));

//                            System.out.println("sem valor 5");
                    }
                    cel51.setBorder(0);
                    cel51.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel51.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel51);

//                    if (null != GetData(tabla, i, 5)) {
//                        cel71 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 5).toString()));
//
//                            System.out.println("tem valor 8");
//                    }
//                    else {
//                        cel71 = new PdfPCell(new Paragraph(" "));
//
//                            System.out.println("sem valor 8");
//                    }
                    cel71 = new PdfPCell(new Paragraph(armazem));
                    cel71.setBorder(0);
                    cel71.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel71.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel71);

                }

                space.setBorder(0);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);

                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);

                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);

                doc.add(tbMaterial);

                //System.out.println("ADD MT");
                //            THIRD HALF 
                PdfPTable tbResumo = new PdfPTable(3);
                PdfPCell side1 = new PdfPCell(new Paragraph("Este documento não serve de factura.", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                side1.setHorizontalAlignment(Element.ALIGN_LEFT);
                side1.setBackgroundColor(BaseColor.WHITE);
                side1.setBorder(0);
                side1.getColumn().updateFilledWidth(8);
                tbResumo.addCell(side1);

                PdfPCell side2 = new PdfPCell(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side2.setHorizontalAlignment(Element.ALIGN_LEFT);
                side2.setBackgroundColor(new BaseColor(189, 215, 238));
                side2.setBorder(0);
                side2.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side2);
                PdfPCell side3 = new PdfPCell(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side3.setHorizontalAlignment(Element.ALIGN_LEFT);
                side3.setBackgroundColor(new BaseColor(221, 235, 247));
                side3.setBorder(0);
                side3.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side3);
                PdfPCell side12 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                side12.setHorizontalAlignment(Element.ALIGN_LEFT);
                side12.setBackgroundColor(BaseColor.WHITE);
                side12.setBorder(0);
                side12.getColumn().updateFilledWidth(8);
                tbResumo.addCell(side12);
                PdfPCell side22 = new PdfPCell(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side22.setHorizontalAlignment(Element.ALIGN_LEFT);
                side22.setBackgroundColor(new BaseColor(189, 215, 238));
                side22.setBorder(0);
                side22.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side22);
                PdfPCell side32 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side32.setHorizontalAlignment(Element.ALIGN_LEFT);
                side32.setBackgroundColor(new BaseColor(221, 235, 247));
                side32.setBorder(0);
                side32.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side32);
                PdfPCell side13 = new PdfPCell(new Paragraph("Entrada em stock.", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                side13.setHorizontalAlignment(Element.ALIGN_LEFT);
                side13.setBackgroundColor(BaseColor.WHITE);
                side13.setBorder(0);
                side13.getColumn().updateFilledWidth(8);
                tbResumo.addCell(side13);
                PdfPCell side23 = new PdfPCell(new Paragraph("Total ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side23.setHorizontalAlignment(Element.ALIGN_LEFT);
                side23.setBackgroundColor(new BaseColor(189, 215, 238));
                side23.setBorder(0);
                side23.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side23);
                double t = TOTAL;
                this.exten.setNumber(t);
                this.exten.setMeoda("Kwanza");
                PdfPCell side33 = new PdfPCell(new Paragraph(Chang(TOTAL) + exten.getMeoda() + "(s)\n", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLDITALIC, BaseColor.BLACK)));

                side33.setHorizontalAlignment(Element.ALIGN_LEFT);
                side33.setBackgroundColor(new BaseColor(221, 235, 247));
                side33.setBorder(0);
                side33.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side33);
                PdfPCell side14 = new PdfPCell(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, cor)));
//                side14.setHorizontalAlignment(Element.ALIGN_LEFT);
                side14.setBackgroundColor(BaseColor.WHITE);
                side14.setBorder(0);
                side14.getColumn().updateFilledWidth(8);
                tbResumo.addCell(side14);
                PdfPCell side24 = new PdfPCell(new Paragraph("Pagamento", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC, cor)));
                side24.setHorizontalAlignment(Element.ALIGN_LEFT);
                side24.setBackgroundColor(new BaseColor(189, 215, 238));
                side24.setBorder(0);
                side24.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side24);
                t = Double.parseDouble(Convert(pagamento));
                this.exten.setNumber(t);
//                this.exten.setMeoda("Kwanza");
//                System.out.println(t);
                System.out.println(exten.show());
                PdfPCell side34 = new PdfPCell(new Paragraph(pagamento + " " + exten.getMeoda() + "(s)\n" + this.exten.show() + " " + "\n", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLDITALIC, BaseColor.BLACK)));
                side34.setHorizontalAlignment(Element.ALIGN_LEFT);
                side34.setBackgroundColor(new BaseColor(221, 235, 247));
                side34.setBorder(0);
                side34.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side34);

                doc.add(tbResumo);
                PdfPTable tbCoordenadas = new PdfPTable(2);
                tbCoordenadas.setHorizontalAlignment(1);
                tbCoordenadas.setSpacingBefore(100);
                Paragraph P = new Paragraph();
                P.setFont(new Font(Font.FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK));
                this.exten.setMeoda("Kwanza");

                ControloBD liga = new ControloBD();
                liga.setCaminho(this.caminho);
                liga.conexao();
                Paragraph Cord = new Paragraph();
                ;

                P.add("Este documento não serve de fatura. \n" + this.licenca + "\nOperador(a) " + MU.getNome() + ".\n");

                tbCoordenadas.addCell(new PdfPCell(P));
                tbCoordenadas.addCell(new PdfPCell(Cord));

                doc.add(tbCoordenadas);
                doc.addAuthor(MU.getNome());
                //doc.addTitle("Baixa "+NUM);
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
//                doc.close();
////                Desktop.getDesktop().open(new File(this.file.getAbsolutePath().toString()));
                doc.close();
                    StampPageXofY stampPageXofY = new StampPageXofY();
                    stampPageXofY.setEstado(this.estadoLicenca);
                    String SRC = this.file.getAbsolutePath().toString();
                    String nome = this.file.getAbsolutePath().toString();
                    String DEST = this.file.getParent() + "/_" + this.file.getName();

                    File file = new File(DEST);
                    file.getParentFile().mkdirs();
                    try {

                        stampPageXofY.Paginar(SRC, DEST);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.file.delete();
                    file.renameTo(new File(nome));
//                    Desktop.getDesktop().open(new File(nome));
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }

        }
        
        String tb, coluna_tb;

            tb = "";
            coluna_tb = "";
        
//        this.ToByte(this.file, tb, coluna_tb);

    }

    public void exportA4Inventario(int num, String DATA_R, String HORA_R, String armazem) throws IOException {
        Document doc = null;
        OutputStream out = null;

        try {
//            doc = new Document(PageSize.A4, 30, 20, 20, 30);
             doc = new Document(PageSize.A4,30, 20, 50, 50);
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
//                Adicionar o logotipo da empresa
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 120);
                IMG.setAbsolutePosition(90, 720);
//                doc.add(IMG);

                BaseColor cor = new BaseColor(47, 117, 181);
                Paragraph P1Fatura = new Paragraph();
                P1Fatura.setFont(new Font(Font.FontFamily.HELVETICA, 20, 0, cor));
                P1Fatura.setAlignment(2);
//                P1Fatura.setSpacingAfter(40);
                P1Fatura.add(" ");
//                doc.add(P1Fatura);
                PdfPTable tb = new PdfPTable(2);

                PdfPCell celIMG = new PdfPCell();
                celIMG.setBorder(0);
                celIMG.addElement(IMG);
                tb.addCell(celIMG);

                PdfPCell celf = new PdfPCell(P1Fatura);
                celf.setBorder(0);
                tb.addCell(celf);

                tb.setSpacingBefore(30);
                Paragraph P1 = new Paragraph();
                Paragraph P2 = new Paragraph();
                Paragraph P3 = new Paragraph();
                Paragraph P4 = new Paragraph();

                P1.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P2.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P3.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P4.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                P1.add(ME.getDesignacao());
                P2.add("Telefone: " + Integer.toString(ME.telefone));
                P3.add(ME.pais + ", " + ME.getProvincia() + ", " + ME.rua + "");
                P4.add("NIF: " + ME.getNif());

                PdfPCell cel1 = new PdfPCell();
                cel1.addElement(P1);
                cel1.addElement(P2);
                cel1.addElement(P3);
                cel1.addElement(P4);
                cel1.setBorder(0);

//                tb.setWidthPercentage(200);
                tb.addCell(cel1);

                Paragraph P5 = new Paragraph();
                Paragraph P6 = new Paragraph();
                Paragraph P7 = new Paragraph();
                Paragraph P8 = new Paragraph();

                P5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P6.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P7.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                P8.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

                P5.add("Inventário Nº");
                P6.add("  Data");
                String NUM = "0";
                if (num > 9) {
                    NUM = Integer.toString(num);
                } else {
                    NUM = NUM + Integer.toString(num);
                }

                P7.add(NUM);
                P8.add(DATA_R + " " + HORA_R);

                PdfPTable TBin = new PdfPTable(2);
                PdfPCell cel3 = new PdfPCell();
                PdfPCell cel4 = new PdfPCell();
                PdfPCell cel5 = new PdfPCell(P7);
                PdfPCell cel6 = new PdfPCell(P8);

                cel3.setBorder(0);
                cel4.setBorder(0);
                cel5.setBorder(0);
                cel6.setBorder(0);

                cel3.setBackgroundColor(cor);
                cel3.addElement(P5);

                cel4.setBackgroundColor(cor);
                cel4.addElement(P6);

                TBin.addCell(cel3);
                TBin.addCell(cel4);
                TBin.addCell(cel5);
                TBin.addCell(cel6);

                PdfPCell celT = new PdfPCell(TBin);
                celT.setBorder(0);
                tb.addCell(celT);

                PdfPTable TBin2 = new PdfPTable(1);
                PdfPTable TBin3 = new PdfPTable(1);

                Paragraph P9 = new Paragraph();
                Paragraph P10 = new Paragraph();
                Paragraph P11 = new Paragraph();
                Paragraph P12 = new Paragraph();

                P9.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P10.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE));
                P11.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                P12.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));

//                P9.add("NOME DO FORNECEDOR");
//                P10.add(" CONTRIBUINTE");
//                P12.add("  " +MC.getNif());
                Paragraph dc1 = new Paragraph();
                Paragraph dc2 = new Paragraph();
                Paragraph dc3 = new Paragraph();
                Paragraph dc4 = new Paragraph();
                Paragraph dc5 = new Paragraph();
                dc1.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc2.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc3.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc4.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
                dc5.setFont(new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
//                dc1.add(" ");
//                dc2.add(" ");
//                dc3.add(" ");
//                dc4.add(" ");
//                dc5.add(" ");

                PdfPCell cel7 = new PdfPCell();
                PdfPCell cel8 = new PdfPCell();
                PdfPCell cel9 = new PdfPCell();
                cel9.addElement(dc1);
                cel9.addElement(dc2);
                cel9.addElement(dc3);
                cel9.addElement(dc4);
                cel9.addElement(dc5);
                PdfPCell cel10 = new PdfPCell(P12);

                cel7.setBorder(0);
                cel8.setBorder(0);
                cel9.setBorder(0);
                cel10.setBorder(0);

                cel7.setBackgroundColor(cor);
                cel7.addElement(P9);

                cel8.setBackgroundColor(cor);
                cel8.addElement(P10);

                TBin2.addCell(cel7);
                TBin3.addCell(cel8);
                TBin2.addCell(cel9);
                TBin3.addCell(cel10);
                PdfPCell celT2 = new PdfPCell(TBin2);
                celT2.setBorder(0);
                PdfPCell celT3 = new PdfPCell(TBin3);
                celT3.setBorder(0);
                PdfPCell space = new PdfPCell(new Paragraph(" "));
                space.setBorder(0);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(celT2);
                tb.addCell(celT3);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                tb.addCell(space);
                doc.add(tb);

//             SECOND HALF 
                PdfPTable tbMaterial = new PdfPTable(7);

                PdfPCell cab3 = new PdfPCell(new Paragraph("ID", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab3.setBackgroundColor(cor);
                cab3.setBorder(0);

                PdfPCell cab4 = new PdfPCell(new Paragraph("Descrição", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab4.setBackgroundColor(cor);
                cab4.setBorder(0);

                PdfPCell cab5 = new PdfPCell(new Paragraph("Stock ", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab5.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab5.setBackgroundColor(cor);
                cab5.setBorder(0);

                PdfPCell cabVA = new PdfPCell(new Paragraph("Físico", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cabVA.setHorizontalAlignment(Element.ALIGN_CENTER);
                cabVA.setBackgroundColor(cor);
                cabVA.setBorder(0);

                PdfPCell cab6 = new PdfPCell(new Paragraph("Diferença", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab6.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab6.setBackgroundColor(cor);
                cab6.setBorder(0);
                PdfPCell cab7 = new PdfPCell(new Paragraph("Info", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab7.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab7.setBackgroundColor(cor);
                cab7.setBorder(0);
                PdfPCell cab8 = new PdfPCell(new Paragraph("Armazem", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE)));
                cab8.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab8.setBackgroundColor(cor);
                cab8.setBorder(0);

                tbMaterial.addCell(cab3);
                tbMaterial.addCell(cab4);
                tbMaterial.addCell(cab5);
                tbMaterial.addCell(cabVA);
                tbMaterial.addCell(cab6);
                tbMaterial.addCell(cab7);
                tbMaterial.addCell(cab8);

                double TOTAL = 0;

                for (int i = 0; i < this.tabla.getRowCount(); i++) {

                    PdfPCell cel11 = null;
                    PdfPCell cel21 = null;
                    PdfPCell cel31 = null;
                    PdfPCell cel41 = null;
                    PdfPCell cel51 = null;
                    PdfPCell cel61 = null;
                    PdfPCell cel71 = null;
                    PdfPCell cel81 = null;

                    if (null != GetData(tabla, i, 0)) {
                        cel11 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 0).toString()));

//                            System.out.println("tem valor 1:"+this.tabla.getModel().getValueAt(i, 0).toString() );
                    } else {
                        cel11 = new PdfPCell(new Paragraph(" "));
//                            System.out.println("Sem valor 1" );
                    }

                    cel11.setBorder(0);
                    cel11.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel11.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel11);

                    if (null != GetData(tabla, i, 1)) {
                        cel21 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 1).toString()));

//                            System.out.println("tem valor 2");
                    } else {
                        cel21 = new PdfPCell(new Paragraph(" "));

//                            System.out.println("sem valor 2");
                    }
                    cel21.setBorder(0);
                    cel21.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel21.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel21);

                    if (null != GetData(tabla, i, 2)) {
                        cel31 = new PdfPCell(new Paragraph((this.tabla.getModel().getValueAt(i, 2).toString())));

//                            System.out.println("tem valor 3");
                    } else {
                        cel31 = new PdfPCell(new Paragraph(" "));

//                            System.out.println("sem valor 3");
                    }
                    cel31.setBorder(0);
                    cel31.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel31.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel31);

                    if (null != GetData(tabla, i, 3)) {
//                        cel41 = new PdfPCell(new Paragraph(" "));

                        cel41 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 3).toString()));

//                            System.out.println("tem valor 4");
                    } else {
                        cel41 = new PdfPCell(new Paragraph(" "));

//                            System.out.println("sem valor 4");
                    }
                    cel41.setBorder(0);
                    cel41.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel41.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel41);

                    if (null != GetData(tabla, i, 4)) {
                        String stotal = this.tabla.getModel().getValueAt(i, 4).toString();
                        cel61 = new PdfPCell(new Paragraph((stotal)));
                        TOTAL += 0;
//                            System.out.println("7 tota: "+TOTAL);
                    } else {
                        cel61 = new PdfPCell(new Paragraph(" "));
//                        System.out.println("7 tota: "+TOTAL);
//                            System.out.println("sem valor");
                    }
                    cel61.setBorder(0);
                    cel61.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel61.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel61);

                    if (null != GetData(tabla, i, 5)) {
                        cel51 = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, 5).toString()));

//                            System.out.println("tem valor 5");
                    } else {
                        cel51 = new PdfPCell(new Paragraph(" "));

//                            System.out.println("sem valor 5");
                    }
                    cel51.setBorder(0);
                    cel51.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel51.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel51);

//                   
                    cel71 = new PdfPCell(new Paragraph(armazem));
                    cel71.setBorder(0);
                    cel71.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel71.getColumn().updateFilledWidth(3);
                    tbMaterial.addCell(cel71);

                }

                space.setBorder(0);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);

                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);

                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);
                tbMaterial.addCell(space);

                doc.add(tbMaterial);

                //System.out.println("ADD MT");
                //            THIRD HALF 
                PdfPTable tbResumo = new PdfPTable(3);
                PdfPCell side1 = new PdfPCell(new Paragraph("Este documento não serve de factura.", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                side1.setHorizontalAlignment(Element.ALIGN_LEFT);
                side1.setBackgroundColor(BaseColor.WHITE);
                side1.setBorder(0);
                side1.getColumn().updateFilledWidth(8);
                tbResumo.addCell(side1);

                PdfPCell side2 = new PdfPCell(new Paragraph("Inventáio.", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side2.setHorizontalAlignment(Element.ALIGN_LEFT);
                side2.setBackgroundColor(new BaseColor(189, 215, 238));
                side2.setBorder(0);
                side2.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side2);
                PdfPCell side3 = new PdfPCell(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side3.setHorizontalAlignment(Element.ALIGN_LEFT);
                side3.setBackgroundColor(new BaseColor(221, 235, 247));
                side3.setBorder(0);
                side3.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side3);
                PdfPCell side12 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                side12.setHorizontalAlignment(Element.ALIGN_LEFT);
                side12.setBackgroundColor(BaseColor.WHITE);
                side12.setBorder(0);
                side12.getColumn().updateFilledWidth(8);
                tbResumo.addCell(side12);
                PdfPCell side22 = new PdfPCell(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side22.setHorizontalAlignment(Element.ALIGN_LEFT);
                side22.setBackgroundColor(new BaseColor(189, 215, 238));
                side22.setBorder(0);
                side22.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side22);
                PdfPCell side32 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side32.setHorizontalAlignment(Element.ALIGN_LEFT);
                side32.setBackgroundColor(new BaseColor(221, 235, 247));
                side32.setBorder(0);
                side32.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side32);
                PdfPCell side13 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                side13.setHorizontalAlignment(Element.ALIGN_LEFT);
                side13.setBackgroundColor(BaseColor.WHITE);
                side13.setBorder(0);
                side13.getColumn().updateFilledWidth(8);
                tbResumo.addCell(side13);
                PdfPCell side23 = new PdfPCell(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side23.setHorizontalAlignment(Element.ALIGN_LEFT);
                side23.setBackgroundColor(new BaseColor(189, 215, 238));
                side23.setBorder(0);
                side23.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side23);
                PdfPCell side33 = new PdfPCell(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
                side33.setHorizontalAlignment(Element.ALIGN_LEFT);
                side33.setBackgroundColor(new BaseColor(221, 235, 247));
                side33.setBorder(0);
                side33.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side33);
                PdfPCell side14 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, cor)));
                side14.setHorizontalAlignment(Element.ALIGN_LEFT);
                side14.setBackgroundColor(BaseColor.WHITE);
                side14.setBorder(0);
                side14.getColumn().updateFilledWidth(8);
                tbResumo.addCell(side14);
                PdfPCell side24 = new PdfPCell(new Paragraph("  ", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLDITALIC, cor)));
                side24.setHorizontalAlignment(Element.ALIGN_LEFT);
                side24.setBackgroundColor(new BaseColor(189, 215, 238));
                side24.setBorder(0);
                side24.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side24);
                double t = TOTAL;
                this.exten.setNumber(t);
                this.exten.setMeoda("Kwanza");
//                System.out.println(t);
//                System.out.println(exten.show());
                PdfPCell side34 = new PdfPCell(new Paragraph(" ", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLDITALIC, BaseColor.BLACK)));
                side34.setHorizontalAlignment(Element.ALIGN_LEFT);
                side34.setBackgroundColor(new BaseColor(221, 235, 247));
                side34.setBorder(0);
                side34.getColumn().updateFilledWidth(2);
                tbResumo.addCell(side34);

//                doc.add(tbResumo);
                PdfPTable tbCoordenadas = new PdfPTable(2);
                tbCoordenadas.setHorizontalAlignment(1);
                tbCoordenadas.setSpacingBefore(100);
                Paragraph P = new Paragraph();
                P.setFont(new Font(Font.FontFamily.HELVETICA, 9, Font.BOLDITALIC, BaseColor.BLACK));
                this.exten.setMeoda("Kwanza");

                ControloBD liga = new ControloBD();
                liga.setCaminho(this.caminho);
                liga.conexao();
                Paragraph Cord = new Paragraph();
                ;

                P.add("Este documento não serve de fatura. \n" + this.licenca + "\nOperador(a) " + MU.getNome() + ".\n");

                tbCoordenadas.addCell(new PdfPCell(P));
                tbCoordenadas.addCell(new PdfPCell(Cord));

                doc.add(tbCoordenadas);
                doc.addAuthor(MU.getNome());
                //doc.addTitle("Baixa "+NUM);
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
//                doc.close();
//                Desktop.getDesktop().open(new File(this.file.getAbsolutePath().toString()));
                doc.close();
                    StampPageXofY stampPageXofY = new StampPageXofY();
                    stampPageXofY.setEstado(this.estadoLicenca);
                    String SRC = this.file.getAbsolutePath().toString();
                    String nome = this.file.getAbsolutePath().toString();
                    String DEST = this.file.getParent() + "/_" + this.file.getName();

                    File file = new File(DEST);
                    file.getParentFile().mkdirs();
                    try {

                        stampPageXofY.Paginar(SRC, DEST);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.file.delete();
                    file.renameTo(new File(nome));
//                    Desktop.getDesktop().open(new File(nome));
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }

        }
        
        String tb, coluna_tb;

            tb = "";
            coluna_tb = "";
        
//        this.ToByte(this.file, tb, coluna_tb);

    }

    String Convert(String param) {
        param = param.replace('.', ' ');
        String[] split = param.split(" ");
        param = "0";
        for (String split1 : split) {
            param += split1;
        }
        param = param.replace(',', '.');
        return param;
    }

    public void txte() throws IOException {
        Document doc = null;
        OutputStream out = null;

        try {
//            doc = new Document(PageSize.A4, 30, 20, 20, 30);

            doc = new Document(PageSize.LETTER, 30, 20, 20, 30);
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

            PdfPTable pt = new PdfPTable(1);
            PdfPCell pc = new PdfPCell(new Paragraph(new Phrase("ddddddddddddd")));
            pt.addCell(pc);

            try {
                doc.open();
//                for (int i = 0; i < 10; i++) {
                doc.add(pt);
//                }

            } catch (DocumentException ex) {
                Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
            }

        } finally {
            if (doc != null) {
                doc.close();
//                Desktop.getDesktop().open(new File(this.file.getAbsolutePath().toString()));
//                Desktop.getDesktop().print(new File(this.file.getAbsolutePath().toString()));

            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }

        }

    }

    public void ExportarRel(String tex1, boolean print, ArrayList<String> colunas) {
        BaseColor cor = new BaseColor(47, 117, 181);
        Document doc = null;
        OutputStream out = null;

        try {
            doc = new Document();
            try {
                DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                out = outt;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }
            try {
                PdfWriter.getInstance(doc, out);
                doc.open();
                Image IMG = Image.getInstance(revert());
                IMG.scaleToFit(100, 100);
//                IMG.setAbsolutePosition(400, 700);
                IMG.setAlignment(1);
                doc.add(IMG);

//                Image imagem;
//                imagem = Image.getInstance("NetBeansProjects\\Somoil\\src\\Modelo\\download.png");
//                doc.add(imagem);
                Paragraph p1 = new Paragraph();
                p1.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                p1.setAlignment(1);
                p1.add("\n\n" + tex1 + "\nData de exportação: " + tempo.Date() + " " + tempo.Hours());
                doc.add(p1);
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                PdfPTable tb = new PdfPTable(this.tabla.getColumnCount());
//                Acrescentar se é visitante ou não como cabeçalho
                Font cab = new Font(Font.FontFamily.TIMES_ROMAN, 10);
                PdfPCell cab1 = new PdfPCell(new Paragraph("Nº", cab));

                cab1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cab1.setBackgroundColor(BaseColor.GRAY);

                for (String coluna : colunas) {
                    PdfPCell cab2 = new PdfPCell(new Paragraph(coluna, cab));
                    cab2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cab2.setBackgroundColor(BaseColor.GRAY);
                    tb.addCell(cab2);
                }

                for (int i = 0; i < this.tabla.getRowCount(); i++) {
                    PdfPCell cel1 = null;
                    cel1 = new PdfPCell(new Paragraph(Integer.toString(i + 1), cab));

                    cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cel1.getColumn().updateFilledWidth(3);
                    tb.addCell(cel1);

                    for (int j = 1; j < this.tabla.getColumnCount(); j++) {
                        PdfPCell cel = null;

                        if (null != GetData(tabla, i, j)) {
                            cel = new PdfPCell(new Paragraph(this.tabla.getModel().getValueAt(i, j).toString(), cab));
                            ;
                            // System.out.println("tem valor");
                        }

                        if (cel == null) {
                            cel = new PdfPCell(new Paragraph());
                            // System.out.println("sem valor");
                        }
                        cel.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        cel.getColumn().updateFilledWidth(3);
                        tb.addCell(cel);

                    }
                }

                doc.add(tb);
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Erro" + ex);
            }

        } finally {
            if (doc != null) {
                doc.close();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
            }

        }
        if (print) {
            try {
                Desktop.getDesktop().print(file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível imprimir.");
            }

        }
        String tb, coluna_tb;

            tb = "";
            coluna_tb = "";
        
//        this.ToByte(this.file, tb, coluna_tb);
    }

    public void Fecho(String Tamanho, int num, String Ordem, String estado, String data_de_abertura,
            String data_de_fecho,
            double fatura, double recibo, double fatura_recibo,
            double nota_de_credito, double valor_inicial, double saida_em_caixa,
            double banco, double numerario, double saldo, double saldo_informado, String dif, ArrayList dadosFT, String[] colunasFT, ArrayList dadosFR, String[] colunasFR, ArrayList dadosNC, String[] colunasNC, ArrayList dadosRC, String[] colunasRC,
            ArrayList dadosSD, String[] colunasSD, ArrayList dadosPR, String[] colunasPR) throws IOException {
        nota_de_credito *= -1;
        saida_em_caixa *= -1;

        Document doc = null;
        OutputStream out = null;
        if (Tamanho.equals("A4")) {
            try {
//                doc = new Document(PageSize.A4, 30, 20, 20, 30);
                 doc = new Document(PageSize.A4,30, 20, 50, 50);
                try {
                    DataOutputStream outt = new DataOutputStream(new FileOutputStream(file));
                    out = outt;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro" + ex);
                }
                try {
                    PdfWriter.getInstance(doc, out);
                    doc.open();
//                Adicionar o logotipo da empresa
                    Image IMG = Image.getInstance(revert());
                    IMG.scaleToFit(100, 120);
                    IMG.setAbsolutePosition(90, 720);
//                doc.add(IMG);

                    BaseColor cor = new BaseColor(47, 117, 181);
                    Paragraph P1Fatura = new Paragraph();
                    P1Fatura.setFont(new Font(Font.FontFamily.HELVETICA, 20, 0, cor));
                    P1Fatura.setAlignment(2);
//                P1Fatura.setSpacingAfter(40);
                    P1Fatura.add(" ");
//                doc.add(P1Fatura);
                    PdfPTable tb = new PdfPTable(2);

                    PdfPCell celIMG = new PdfPCell();
                    celIMG.setBorder(0);
                    celIMG.addElement(IMG);
                    tb.addCell(celIMG);

                    PdfPCell celf = new PdfPCell(P1Fatura);
                    celf.setBorder(0);
                    tb.addCell(celf);

                    tb.setSpacingBefore(30);
                    Paragraph P1 = new Paragraph();
                    Paragraph P2 = new Paragraph();
                    Paragraph P3 = new Paragraph();
                    Paragraph P4 = new Paragraph();

                    P1.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P2.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P3.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P4.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    P1.add(ME.getDesignacao());
                    P2.add("Telefone: " + Integer.toString(ME.telefone));
                    P3.add(ME.pais + ", " + ME.getProvincia() + ", " + ME.rua + "");
                    P4.add("NIF: " + ME.getNif());

                    PdfPCell cel1 = new PdfPCell();
                    cel1.addElement(P1);
                    cel1.addElement(P2);
                    cel1.addElement(P3);
                    cel1.addElement(P4);
                    cel1.setBorder(0);

//                tb.setWidthPercentage(200);
                    tb.addCell(cel1);

                    Paragraph P5 = new Paragraph();
                    Paragraph P6 = new Paragraph();
                    Paragraph P7 = new Paragraph();
                    Paragraph P8 = new Paragraph();

                    P5.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE));
                    P6.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE));
                    P7.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    P8.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));

                    P5.add("TURNO Nº");
                    P6.add("  DATA ");
                    String NUM = "0";
                    if (num > 9) {
                        NUM = Integer.toString(num);
                    } else {
                        NUM = NUM + Integer.toString(num);
                    }

                    P7.add(" " + NUM + "\n\n" + Ordem);
                    P8.add(tempo.Date() + " " + tempo.Hours() + "\n");

                    PdfPTable TBin = new PdfPTable(2);
                    PdfPCell cel3 = new PdfPCell();
                    PdfPCell cel4 = new PdfPCell();
                    PdfPCell cel5 = new PdfPCell(P7);
                    PdfPCell cel6 = new PdfPCell(P8);

                    cel3.setBorder(0);
                    cel4.setBorder(0);
                    cel5.setBorder(0);
                    cel6.setBorder(0);

                    cel3.setBackgroundColor(cor);
                    cel3.addElement(P5);

                    cel4.setBackgroundColor(cor);
                    cel4.addElement(P6);

                    TBin.addCell(cel3);
                    TBin.addCell(cel4);
                    TBin.addCell(cel5);
                    TBin.addCell(cel6);

                    PdfPCell celT = new PdfPCell(TBin);
                    celT.setBorder(0);
                    tb.addCell(celT);

                    PdfPTable TBin2 = new PdfPTable(1);
                    PdfPTable TBin3 = new PdfPTable(1);

                    Paragraph P9 = new Paragraph();
                    Paragraph P10 = new Paragraph();
                    Paragraph P11 = new Paragraph();
                    Paragraph P12 = new Paragraph();

                    P9.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE));
                    P10.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE));
                    P11.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    P12.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));

                    P9.add("ITENS DE CAIXA ");
                    P10.add("  DETALHES");

                    Paragraph dc1 = new Paragraph();
                    Paragraph dc2 = new Paragraph();
                    Paragraph dc3 = new Paragraph();
                    Paragraph dc4 = new Paragraph();
                    Paragraph dc5 = new Paragraph();
                    dc1.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
                    dc2.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc3.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc4.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc5.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));

                    Paragraph dc6 = new Paragraph();
                    Paragraph dc7 = new Paragraph();
                    Paragraph dc8 = new Paragraph();
                    Paragraph dc9 = new Paragraph();
                    Paragraph dc10 = new Paragraph();
                    dc6.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc7.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc8.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc9.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc10.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));

                    Paragraph dcAux = new Paragraph();
                    Paragraph dcAux1 = new Paragraph();
                    dcAux.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dcAux1.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));

                    dc1.add("Turno Nº");
                    P12.add("" + num);

                    dc2.add("Utilizador ");
                    Paragraph P13 = new Paragraph();
                    P13.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    P13.add("" + MU.getNome());

                    dc3.add("Estado ");
                    Paragraph P14 = new Paragraph();
                    P14.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    P14.add("" + estado);

                    dc4.add("Data de abertura ");
                    Paragraph P15 = new Paragraph();
                    P15.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    P15.add("" + data_de_abertura);

                    // inicio data de fecho
                    dcAux.add("Data de fecho");
                    Paragraph PAux = new Paragraph();
                    PAux.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    PAux.add("" + data_de_fecho);

                    // fim data de fecho
                    dcAux1.add("Valor inicial");
                    Paragraph PAux1 = new Paragraph();
                    PAux1.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    PAux1.add("" + valor_inicial);

                    dc5.add("Fatura ");
                    Paragraph P16 = new Paragraph();
                    P16.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    P16.add("" + fatura);

                    dc6.add("Recibo (Venda) ");
                    Paragraph P17 = new Paragraph();
                    P17.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    P17.add("" + recibo);

                    dc7.add("Fatura recibo (Venda) ");
                    Paragraph P18 = new Paragraph();
                    P18.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    P18.add("" + fatura_recibo);

                    dc8.add("Nota de crédito (Devoluções) ");
                    Paragraph P19 = new Paragraph();
                    P19.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    P19.add("" + nota_de_credito);

                    //
                    PdfPCell cel7 = new PdfPCell();
                    PdfPCell cel8 = new PdfPCell();
                    PdfPCell cel9 = new PdfPCell();

                    cel9.addElement(dc1);
                    PdfPCell cel10 = new PdfPCell(P12);

                    cel7.setBorder(1);
                    cel8.setBorder(1);
                    cel9.setBorder(1);
                    cel10.setBorder(1);

                    cel7.setBackgroundColor(cor);
                    cel7.addElement(P9);

                    cel8.setBackgroundColor(cor);
                    cel8.addElement(P10);

                    TBin2.addCell(cel7);
                    TBin3.addCell(cel8);
                    TBin2.addCell(cel9);
                    TBin3.addCell(cel10);

                    //
                    cel9 = new PdfPCell(dc2);
                    cel10 = new PdfPCell(P13);
                    TBin2.addCell(cel9);
                    TBin3.addCell(cel10);

                    //
                    cel9 = new PdfPCell(dc3);
                    cel10 = new PdfPCell(P14);
                    TBin2.addCell(cel9);
                    TBin3.addCell(cel10);

                    //
                    cel9 = new PdfPCell(dc4);
                    cel10 = new PdfPCell(P15);
                    TBin2.addCell(cel9);
                    TBin3.addCell(cel10);

                    cel9 = new PdfPCell(dcAux);
                    cel10 = new PdfPCell(PAux);
                    TBin2.addCell(cel9);
                    TBin3.addCell(cel10);

                    cel9 = new PdfPCell(dcAux1);
                    cel10 = new PdfPCell(PAux1);
                    TBin2.addCell(cel9);
                    TBin3.addCell(cel10);

                    //
                    cel9 = new PdfPCell(dc5);
                    cel10 = new PdfPCell(P16);
                    TBin2.addCell(cel9);
                    TBin3.addCell(cel10);

                    //
                    cel9 = new PdfPCell(dc6);
                    cel10 = new PdfPCell(P17);
                    TBin2.addCell(cel9);
                    TBin3.addCell(cel10);
//                    
                    cel9 = new PdfPCell(dc7);
                    cel10 = new PdfPCell(P18);
                    TBin2.addCell(cel9);
                    TBin3.addCell(cel10);
//                    
                    cel9 = new PdfPCell(dc8);
                    cel10 = new PdfPCell(P19);
                    TBin2.addCell(cel9);
                    TBin3.addCell(cel10);
//                    
                    double venda_iliquida = recibo + fatura_recibo + nota_de_credito;
                    Paragraph dc = new Paragraph();
                    dc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc.add("Venda iliquida");
                    cel9 = new PdfPCell(dc);
                    TBin2.addCell(cel9);

                    Paragraph Pc = new Paragraph();
                    Pc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    Pc.add("" + venda_iliquida);
                    cel10 = new PdfPCell(Pc);
                    TBin3.addCell(cel10);

                    dc = new Paragraph();
                    dc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc.add("Saídas em caixa (Despesas) ");
                    cel9 = new PdfPCell(dc);
                    TBin2.addCell(cel9);

                    Pc = new Paragraph();
                    Pc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    Pc.add("" + saida_em_caixa);
                    cel10 = new PdfPCell(Pc);
                    TBin3.addCell(cel10);

                    dc = new Paragraph();
                    dc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc.add("Pagamentos em Banco ");
                    cel9 = new PdfPCell(dc);
                    TBin2.addCell(cel9);

                    Pc = new Paragraph();
                    Pc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    Pc.add("" + banco);
                    cel10 = new PdfPCell(Pc);
                    TBin3.addCell(cel10);

                    dc = new Paragraph();
                    dc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc.add("Numerário ");
                    cel9 = new PdfPCell(dc);
                    TBin2.addCell(cel9);

                    Pc = new Paragraph();
                    Pc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    Pc.add("" + numerario);
                    cel10 = new PdfPCell(Pc);
                    TBin3.addCell(cel10);

                    dc = new Paragraph();
                    dc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc.add("Saldo informado ");
                    cel9 = new PdfPCell(dc);
                    TBin2.addCell(cel9);

                    Pc = new Paragraph();
                    Pc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    Pc.add("" + saldo_informado);
                    cel10 = new PdfPCell(Pc);
                    TBin3.addCell(cel10);

                    dc = new Paragraph();
                    dc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc.add("Saldo ");
                    cel9 = new PdfPCell(dc);
                    TBin2.addCell(cel9);

                    Pc = new Paragraph();
                    Pc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    Pc.add("" + saldo);
                    cel10 = new PdfPCell(Pc);
                    TBin3.addCell(cel10);

                    dc = new Paragraph();
                    dc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    dc.add("Diferença ");
                    cel9 = new PdfPCell(dc);
                    TBin2.addCell(cel9);

                    Pc = new Paragraph();
                    Pc.setFont(new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
                    Pc.add(dif);
                    cel10 = new PdfPCell(Pc);
                    TBin3.addCell(cel10);

                    PdfPCell celT2 = new PdfPCell(TBin2);
                    celT2.setBorder(0);
                    PdfPCell celT3 = new PdfPCell(TBin3);
                    celT3.setBorder(0);
//                    PdfPCell space = new PdfPCell(new Paragraph(" "));
//                    space.setBorder(0);
//                    tb.addCell(space);
                    tb.addCell(celT2);
//                    tb.addCell(space);
                    tb.addCell(celT3);
                    doc.add(tb);
                    // INICIO  
                    for (int x = 1; x <= 6; x++) {
                        ArrayList dados;
                        String colunas[];
                        Paragraph p1 = new Paragraph();
                        p1.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12));
                        p1.setAlignment(1);
                        dados = dadosFT;
                        colunas = colunasFT;
                        if (x == 1) {
                            p1.add("Faturas");
                            doc.add(p1);
                            dados = dadosFT;
                            colunas = colunasFT;
                        } else if (x == 2) {
                            p1.add("Faturas recibo");
                            doc.add(p1);
                            dados = dadosFR;
                            colunas = colunasFR;
                        } else if (x == 3) {
                            p1.add("Recibos");
                            doc.add(p1);
                            dados = dadosRC;
                            colunas = colunasRC;
                        } else if (x == 4) {
                            p1.add("Devoluçoes");
                            doc.add(p1);
                            dados = dadosNC;
                            colunas = colunasNC;
                        } else if (x == 5) {
                            p1.add("Saídas (Despesas)");
                            doc.add(p1);
                            dados = dadosSD;
                            colunas = colunasSD;
                        } else {
                            p1.add("VENDAS");
                            doc.add(p1);
                            dados = dadosPR;
                            colunas = colunasPR;
                            System.out.println("x = " + x + " P " + colunas.length);
                        }

                        int Qlinhas = 0;
                        int QColunas = 1;
                        if (dados != null && colunas != null) {
                            Qlinhas = dados.size();
                            QColunas = colunas.length;
                        }
                        doc.add(new Paragraph(" "));
                        PdfPTable tb2 = new PdfPTable(QColunas);
//                Acrescentar se é visitante ou não como cabeçalho
                        Font cab = new Font(Font.FontFamily.TIMES_ROMAN, 10);
                        PdfPCell cab1 = new PdfPCell(new Paragraph("Nº", cab));

                        cab1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cab1.setBackgroundColor(BaseColor.GRAY);

                        for (String coluna : colunas) {
                            PdfPCell cab2 = new PdfPCell(new Paragraph(coluna, cab));
                            cab2.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cab2.setBackgroundColor(BaseColor.GRAY);
                            tb2.addCell(cab2);
                        }

                        for (int i = 0; i < Qlinhas; i++) {

                            Object OB[] = (Object[]) dados.get(i);
                            for (int j = 0; j < QColunas; j++) {
                                PdfPCell cel = null;
                                if (null != dados.get(i)) {

                                    cel = new PdfPCell(new Paragraph(OB[j].toString(), cab));
                                    ;
                                    // System.out.println("tem valor");
                                }

                                if (cel == null) {
                                    cel = new PdfPCell(new Paragraph());
                                    // System.out.println("sem valor");
                                }
                                cel.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        cel.getColumn().updateFilledWidth(3);
                                tb2.addCell(cel);

                            }
                        }
                        doc.add(tb2);
                        // fim
                    }

//                    
                } catch (DocumentException ex) {
                    JOptionPane.showMessageDialog(null, "Erro: \n" + ex, "Falha na emissão do fecho", JOptionPane.ERROR_MESSAGE);
                }

            } finally {
                if (doc != null) {
//                    doc.close();
//                    Desktop.getDesktop().open(new File(this.file.getAbsolutePath().toString()));
//                Desktop.getDesktop().print(new File(this.file.getAbsolutePath().toString()));
                    doc.close();
                    StampPageXofY stampPageXofY = new StampPageXofY();
                    stampPageXofY.setEstado(this.estadoLicenca);
                    String SRC = this.file.getAbsolutePath().toString();
                    String nome = this.file.getAbsolutePath().toString();
                    String DEST = this.file.getParent() + "/_" + this.file.getName();

                    File file = new File(DEST);
                    file.getParentFile().mkdirs();
                    try {

                        stampPageXofY.Paginar(SRC, DEST);
                    } catch (DocumentException ex) {
                        Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.file.delete();
                    file.renameTo(new File(nome));
//                    Desktop.getDesktop().open(new File(nome));

                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Erro" + ex);
                    }
                }

            }
        }
        String tb, coluna_tb;            
        tb = "";
        coluna_tb = "";
       
//        this.ToByte(this.file, tb, coluna_tb);
    }
   
      public byte[] ToByte(File ficheiro, String tb, String coluna) {
//        System.out.println("CABS "+ficheiro.getAbsolutePath());
        byte[] result = null;
        try {
            FileInputStream fis = new FileInputStream(new File(ficheiro.getAbsolutePath()));
            ByteArrayOutputStream bOs = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int redNum; (redNum = fis.read(buffer)) != -1;) {
                bOs.write(buffer, 0, redNum);
            }

            result = bOs.toByteArray();
            if (result != null) {
                UpdateFile(result, tb, coluna);
                
//                System.out.println(result.length);
                return result;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    void UpdateFile(byte[] sugunda_via, String tb, String coluna) {
        if (sugunda_via != null) {
            System.out.println("Tabela: "+tb + " = " +  MF.getIdfactra());
        } else {
            System.out.println("nulo");
        }
        try {
            this.conexao.conexao();
            PreparedStatement pst = this.conexao.con.prepareStatement("UPDATE " + tb + " set sugunda_via = ? "
                    + "WHERE " + coluna + "=" + MF.getIdfactra());
            pst.setBytes(1, sugunda_via);
            pst.execute();
//            System.out.println(tb);
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     void UpdateFile(byte[] sugunda_via, String tb, String coluna, int num) {
         System.out.println(tb+" = "+num);
         if (sugunda_via != null) {
            System.out.println(tb + " = " + MF.getIdfactra());
        } else {
            System.out.println("nulo");
        }
        try {
            this.conexao.conexao();
            PreparedStatement pst = this.conexao.con.prepareStatement("UPDATE " + tb + " set sugunda_via = ? "
                    + "WHERE " + coluna + "=" +num);
            pst.setBytes(1, sugunda_via);
            pst.execute();
//            System.out.println(tb);
        } catch (SQLException ex) {
            Logger.getLogger(ModeloPDF_2_via.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
     public byte[] ToByte(File ficheiro, String tb, String coluna, int num) {
//        System.out.println("CABS "+ficheiro.getAbsolutePath());
        byte[] result = null;
        try {
            FileInputStream fis = new FileInputStream(new File(ficheiro.getAbsolutePath()));
            ByteArrayOutputStream bOs = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int redNum; (redNum = fis.read(buffer)) != -1;) {
                bOs.write(buffer, 0, redNum);
            }

            result = bOs.toByteArray();
            if (result != null) {
                UpdateFile(result, tb, coluna,num);
                
//                System.out.println(result.length);
                return result;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

}

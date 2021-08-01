/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. client
 */
package Controle;

import Modelo.ModeloCliente;
import Modelo.ModeloEmpresa;
import Modelo.ModeloFatura;
import Modelo.ModeloProduto;
import Modelo.ModeloServico;
import Modelo.ModeloUtilizador;
import Modelo.ModeloVenda;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Gonga
 */
public class Saftao {

    ControloBD liga = new ControloBD();
    Tempo tmp = new Tempo();
    double Subtotal = 0.0;
    double Total = 0.0;
    double imposto = 0.0;
    String mov = "Isento nos termos da alínea a) nº1 do artigo 12º do CIVA, "
            + "Isento nos termos da alínea b) nº1 do artigo 12º do CIVA, "
            + "Isento nos termos da alínea c) nº1 do artigo 12º do CIVA, "
            + "Isento nos termos da alínea d) nº1 do artigo 12º do CIVA, "
            + "Isento nos termos da alínea e) nº1 do artigo 12º do CIVA, "
            + "Isento nos termos da alínea f) nº1 do artigo 12º do CIVA, "
            + "Isento nos termos da alínea g) nº1 do artigo 12º do CIVA, "
            + "Isento nos termos da alínea h) nº1 do artigo 12º do CIVA, "
            + "Isento nos termos da alínea i) nº1 do artigo 12º do CIVA, "
            + "Isento nos termos da alínea j) nº1 do artigo 12º do CIVA, "
            + "Isento nos termos da alínea k) nº1 do artigo 12º do CIVA, "
            + "Isento nos termos da alínea l) nº1 do artigo 12º do CIVA, "
            + "Isento nos termos da alínea m) do artigo 12º do CIVA, "
            + "Isento nos termos da alínea n) do artigo 12º do CIVA, "
            + "Isento nos termos da alínea o) do artigo 12º do CIVA, "
            + "Isento nos termos da alínea a) do artigo 15º do CIVA, "
            + "Isento nos termos da alínea b) do artigo 15º do CIVA, "
            + "Isento nos termos da alínea c) do artigo 15º do CIVA, "
            + "Isento nos termos da alínea d) do artigo 15º do CIVA, "
            + "Isento nos termos da alínea e) do artigo 15º do CIVA, "
            + "Isento nos termos da alínea f) do artigo 15º do CIVA, "
            + "Isento nos termos da alínea g) do artigo 15º do CIVA, "
            + "Isento nos termos da alínea h) do artigo 15º do CIVA, "
            + "Isento nos termos da alínea i) do artigo 15º do CIVA, "
            + "Isento nos termos da alínea a) do artigo 14º, "
            + "Isento nos termos da alínea b) do artigo 14º, "
            + "Isento nos termos da alínea c) do artigo 14º, "
            + "Isento nos termos da alínea d) do artigo 14º, "
            + "Isento nos termos da alínea e) do artigo 14º, "
            + "Isento nos termos da alínea a) nº2 do artigo 14º, "
            + "Isento nos termos da alínea b) nº2 do artigo 14º, "
            + "Isento nos termos da alínea a) nº1 do artigo 16º, "
            + "Isento nos termos da alínea b) nº1 do artigo 16º, "
            + "Isento nos termos da alínea c) nº1 do artigo 16º, "
            + "Isento nos termos da alínea d) nº1 do artigo 16º, "
            + "Isento nos termos da alínea e) nº1 do artigo 16º";
    String motivos[] = mov.split(",");

    public Saftao(String caminho) {
        this.liga.setCaminho(caminho);
    }

    
    
// MasterFiles
    //Fim MasterFiles
    
    
    

    public void exportar(File ficheiro, String dataInicial, String dataFinal) throws SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document DocumentoXML = documentBuilder.newDocument();
            Element AuditFile = DocumentoXML.createElement("AuditFile");
            //   Attr id = DocumentoXML.createAttribute("Id");
//            id.setValue("1");
//            AuditFile.setAttributeNode(id);
// Inicio Tabela HEADER
            Element Header = DocumentoXML.createElement("Header");

            AuditFile.appendChild(Header);

            //TAG
            Element AuditFileVersion = DocumentoXML.createElement("AuditFileVersion");
            // VALOR
            String vesion = "1.0";
            AuditFileVersion.appendChild(DocumentoXML.createTextNode(vesion));
            // AGREGAR EM PAI
            Header.appendChild(AuditFileVersion);

            ModeloEmpresa ME = TakeEMP();

            //TAG ID EMPRESA
            Element CompanyID = DocumentoXML.createElement("CompanyID");
            // Registo comercial
            CompanyID.appendChild(DocumentoXML.createTextNode(ME.getRegisto_comercial()));
            // AGREGAR EM PAI
            Header.appendChild(CompanyID);

            //TAG nif 
            Element TaxRegistrationNumber = DocumentoXML.createElement("TaxRegistrationNumber");
            // VALOR
            TaxRegistrationNumber.appendChild(DocumentoXML.createTextNode(ME.getNif()));
            // AGREGAR EM PAI
            Header.appendChild(TaxRegistrationNumber);

            //TAG sistema contabilístico
            Element TaxAccountingBasis = DocumentoXML.createElement("TaxAccountingBasis");
            // VALOR
            TaxAccountingBasis.appendChild(DocumentoXML.createTextNode("F"));
            // AGREGAR EM PAI
            Header.appendChild(TaxAccountingBasis);

            // CompanyName
            //TAG 
            Element CompanyName = DocumentoXML.createElement("CompanyName");
            // VALOR
            CompanyName.appendChild(DocumentoXML.createTextNode(ME.getDesignacao()));
            // AGREGAR EM PAI
            Header.appendChild(CompanyName);

            //TAG sistema contabilístico
            Element CompanyAddress = DocumentoXML.createElement("CompanyAddress");
            // VALOR

            Element StreetName = DocumentoXML.createElement("StreetName");
            // VALOR
            StreetName.appendChild(DocumentoXML.createTextNode(ME.getRua()));

            CompanyAddress.appendChild(StreetName);

            Element AddressDetail = DocumentoXML.createElement("AddressDetail");
            // Nº casa, Rua, Município, Província, País
            AddressDetail.appendChild(DocumentoXML.createTextNode(ME.getEdificio() + ", " + ME.getRua()
                    + ", " + ME.getProvincia() + ", " + ME.getPais()));

            CompanyAddress.appendChild(AddressDetail);

            Element City = DocumentoXML.createElement("City");
            // VALOR
            City.appendChild(DocumentoXML.createTextNode(ME.getProvincia()));

            CompanyAddress.appendChild(City);

            Element PostalCode = DocumentoXML.createElement("PostalCode");
            // VALOR
            PostalCode.appendChild(DocumentoXML.createTextNode(ME.getCodigo_postal()));

            CompanyAddress.appendChild(PostalCode);

            Element Province = DocumentoXML.createElement("Province");
            // VALOR
            Province.appendChild(DocumentoXML.createTextNode(ME.getProvincia()));

            CompanyAddress.appendChild(Province);

            Element Country = DocumentoXML.createElement("Country");
            // VALOR
            Province.appendChild(DocumentoXML.createTextNode("AO"));

            CompanyAddress.appendChild(Country);

            //FiscalYear
            Element FiscalYear = DocumentoXML.createElement("FiscalYear");
            // VALOR
            String anoAtual = tmp.Date("YYYY");
            FiscalYear.appendChild(DocumentoXML.createTextNode(anoAtual));
            // AGREGAR EM PAI
            Header.appendChild(FiscalYear);

            Element StartDate = DocumentoXML.createElement("StartDate");
            // Data de inicio de exportação do SAFT
            String DataDeinicio = dataInicial;
            StartDate.appendChild(DocumentoXML.createTextNode(DataDeinicio));
            // AGREGAR EM PAI
            Header.appendChild(StartDate);

            Element EndDate = DocumentoXML.createElement("EndDate");
            // Data de fim de exportação do SAFT
            String DataDefim = dataFinal;
            EndDate.appendChild(DocumentoXML.createTextNode(DataDefim));
            // AGREGAR EM PAI
            Header.appendChild(EndDate);

            Element CurrencyCode = DocumentoXML.createElement("CurrencyCode");
            CurrencyCode.appendChild(DocumentoXML.createTextNode("AOA"));
            // AGREGAR EM PAI
            Header.appendChild(CurrencyCode);

            Element DateCreated = DocumentoXML.createElement("DateCreated");
            // VALOR
            DateCreated.appendChild(DocumentoXML.createTextNode(tmp.Date("YYYY-MM-DD")));
            // AGREGAR EM PAI
            Header.appendChild(DateCreated);

            //
            Element TaxEntity = DocumentoXML.createElement("TaxEntity");
            // VALOR
            TaxEntity.appendChild(DocumentoXML.createTextNode("Global"));
            // AGREGAR EM PAI
            Header.appendChild(TaxEntity);

            //
            Element ProductCompanyTaxID = DocumentoXML.createElement("ProductCompanyTaxID");
            // NIF DO PRODUTOR DO SOFTWARE
            ProductCompanyTaxID.appendChild(DocumentoXML.createTextNode("006675055LA048"));
            // AGREGAR EM PAI
            Header.appendChild(ProductCompanyTaxID);

            //
            Element SoftwareValidationNumber = DocumentoXML.createElement("SoftwareValidationNumber");
            // Número de validação software
            int num = 0;
            SoftwareValidationNumber.appendChild(DocumentoXML.createTextNode(Integer.toString(num)));
            // AGREGAR EM PAI
            Header.appendChild(SoftwareValidationNumber);

            //
            Element ProductID = DocumentoXML.createElement("ProductID");
            // Nome da aplicação
            ProductID.appendChild(DocumentoXML.createTextNode("VanSoft"));
            // AGREGAR EM PAI
            Header.appendChild(ProductID);

            //
            Element ProductVersion = DocumentoXML.createElement("ProductVersion");
            // VALOR
            String versao = "1.0";
            ProductVersion.appendChild(DocumentoXML.createTextNode(versao));
            // AGREGAR EM PAI
            Header.appendChild(ProductVersion);

            //
            Element Telephone = DocumentoXML.createElement("Telephone");
            // VALOR
            String Ptelefone = "+244 942719233";
            Telephone.appendChild(DocumentoXML.createTextNode(Ptelefone));
            // AGREGAR EM PAI
            Header.appendChild(Telephone);

            String Pemail = "vaniked100596@gmail.com";
            Element Email = DocumentoXML.createElement("Email");
            // email do produtor do software
            Email.appendChild(DocumentoXML.createTextNode(Pemail));
            // AGREGAR EM PAI
            Header.appendChild(Email);

            Element Website = DocumentoXML.createElement("Website");
            // VALOR
            String PwebSite = "http://vansoft.rf.gd/";
            Website.appendChild(DocumentoXML.createTextNode(PwebSite));
            // AGREGAR EM PAI
            Header.appendChild(Website);

//            FIM Tabela Header
//            Inicio tabela master
            Element MasterFiles = DocumentoXML.createElement("MasterFiles");

            AuditFile.appendChild(MasterFiles);
            // INICIO TABELAS CLIENTES

            Element Customer = null;

            ArrayList<ModeloCliente> MCs = TakeClientes();

            for (int i = 0; i < MCs.size(); i++) {
                ModeloCliente MC = MCs.get(i);
                Customer = DocumentoXML.createElement("Customer");

                Element CustomerID = DocumentoXML.createElement("CustomerID");
                // VALOR
                CustomerID.appendChild(DocumentoXML.createTextNode(Integer.toString(MC.getIdCliente())));
                // AGREGAR EM PAI
                Customer.appendChild(CustomerID);

                Element AccountId = DocumentoXML.createElement("AccountId");
                // VALOR
                AccountId.appendChild(DocumentoXML.createTextNode("DESCONHECIDO"));
                // id da conta corrente
                Customer.appendChild(AccountId);

                Element TaxAccountC = DocumentoXML.createElement("TaxAccount");
                // VALOR
                if (MC.getNif().toLowerCase().equals("consumidor final"))
                    TaxAccountC.appendChild(DocumentoXML.createTextNode(MC.getNif()));
                else
                   TaxAccountC.appendChild(DocumentoXML.createTextNode("999999999"));
                // nif do cliente
                Customer.appendChild(TaxAccountC);

                Element BillingAddress = DocumentoXML.createElement("BillingAddress");
                // VALOR

                Element AddressDetail1 = DocumentoXML.createElement("AddressDetail");
                // "Casa Nº, Rua 12 de Hulho, Luanda, Angola"
                AddressDetail1.appendChild(DocumentoXML.createTextNode(MC.getEndereco()));
                BillingAddress.appendChild(AddressDetail1);

                Element City1 = DocumentoXML.createElement("City");
                City1.appendChild(DocumentoXML.createTextNode(MC.getCidade()));
                BillingAddress.appendChild(City1);

//                Element PostalCode1 = DocumentoXML.createElement("PostalCode");
//                PostalCode1.appendChild(DocumentoXML.createTextNode("244"));
//                BillingAddress.appendChild(PostalCode1);
                Element Country1 = DocumentoXML.createElement("Country");
                Country1.appendChild(DocumentoXML.createTextNode("AO"));
                BillingAddress.appendChild(Country1);

                Customer.appendChild(BillingAddress);

//                Element Telephone1 = DocumentoXML.createElement("Telephone");
//                // VALOR
//                Telephone1.appendChild(DocumentoXML.createTextNode("2212121212"));
//                // AGREGAR EM PAI
//                Customer.appendChild(Telephone1);
                Element SelfBillingIndicator = DocumentoXML.createElement("SelfBillingIndicator");
                // 1 Se existe acordo de autofaturação 0 se nao

                SelfBillingIndicator.appendChild(DocumentoXML.createTextNode("0"));
                Customer.appendChild(SelfBillingIndicator);

                MasterFiles.appendChild(Customer);

            }
////            FIM TABELAS CLIENTE

//            Inicio tabelas produtos/serviços
            Element Product = null;
            ArrayList<ModeloProduto> MPs = TakeProdutos();
            System.out.println("P " + MPs.size());
            for (int i = 0; i < MPs.size(); i++) {
                ModeloProduto MP = MPs.get(i);
                System.out.println("P " + MP.getIdproduto());

                Product = DocumentoXML.createElement("Product");
                // VALOR

                Element ProductType = DocumentoXML.createElement("ProductType");
                // P, S ou O 
                ProductType.appendChild(DocumentoXML.createTextNode("P"));
                Product.appendChild(ProductType);

                Element ProductCode = DocumentoXML.createElement("ProductCode");
                ProductCode.appendChild(DocumentoXML.createTextNode(Integer.toString(MP.getIdproduto())));
                Product.appendChild(ProductCode);

                Element ProductDescription = DocumentoXML.createElement("ProductDescription");
                ProductDescription.appendChild(DocumentoXML.createTextNode(MP.getDescricao()));
                Product.appendChild(ProductDescription);

                Element ProductNumberCode = DocumentoXML.createElement("ProductNumberCode");
                ProductNumberCode.appendChild(DocumentoXML.createTextNode(ProductCode.getTextContent()));
                Product.appendChild(ProductNumberCode);
                MasterFiles.appendChild(Product);
            }
            ArrayList<ModeloServico> MSs = TakeServicos();
            System.out.println("S " + MSs.size());
            for (int i = 0; i < MSs.size(); i++) {
                ModeloServico MP = MSs.get(i);
                Product = DocumentoXML.createElement("Product");
                // VALOR

                Element ProductType = DocumentoXML.createElement("ProductType");
                // P, S ou O 
                ProductType.appendChild(DocumentoXML.createTextNode("S"));
                Product.appendChild(ProductType);

                Element ProductCode = DocumentoXML.createElement("ProductCode");
                ProductCode.appendChild(DocumentoXML.createTextNode(Integer.toString(MP.getIdservico())));
                Product.appendChild(ProductCode);

                Element ProductDescription = DocumentoXML.createElement("ProductDescription");
                ProductDescription.appendChild(DocumentoXML.createTextNode(MP.getDescricao()));
                Product.appendChild(ProductDescription);

                Element ProductNumberCode = DocumentoXML.createElement("ProductNumberCode");
                ProductNumberCode.appendChild(DocumentoXML.createTextNode(ProductCode.getTextContent()));
                Product.appendChild(ProductNumberCode);

                MasterFiles.appendChild(Product);
            }

//            Fim tabelas produtos/serviços
            //            Inicio tabelas de imposto
            Element TaxTable = DocumentoXML.createElement("TaxTable");
            Element TaxTableEntry = null;
            for (int i = 0; i < 2; i++) {
                TaxTableEntry = DocumentoXML.createElement("TaxTableEntry");
                // VALOR

                Element TaxType = DocumentoXML.createElement("TaxType");
                TaxType.appendChild(DocumentoXML.createTextNode("IVA"));
                TaxTableEntry.appendChild(TaxType);

                Element TaxCountryRegion = DocumentoXML.createElement("TaxCountryRegion");
                TaxCountryRegion.appendChild(DocumentoXML.createTextNode("AO"));
                TaxTableEntry.appendChild(TaxCountryRegion);

                Element TaxCode = DocumentoXML.createElement("TaxCode");
                // P, S ou O
                if (i == 0) {
                    TaxCode.appendChild(DocumentoXML.createTextNode("NOR"));
                } else if (i == 1) {
                    TaxCode.appendChild(DocumentoXML.createTextNode("ISE"));
                } else {
                    TaxCode.appendChild(DocumentoXML.createTextNode("OUT"));
                }

                TaxTableEntry.appendChild(TaxType);

                Element Description = DocumentoXML.createElement("Description");
                // P, S ou O
                if (i == 0) {
                    Description.appendChild(DocumentoXML.createTextNode("NORMAL IVA"));
                } else if (i == 1) {
                    Description.appendChild(DocumentoXML.createTextNode("ISENTO"));
                } else {
                    Description.appendChild(DocumentoXML.createTextNode(""));
                }

                TaxTableEntry.appendChild(Description);

                Element TaxPercentage = DocumentoXML.createElement("TaxPercentage");
                // P, S ou O
                if (i == 0) {
                    TaxPercentage.appendChild(DocumentoXML.createTextNode("14"));
                } else if (i == 1) {
                    TaxPercentage.appendChild(DocumentoXML.createTextNode("0"));
                } else {
                    TaxPercentage.appendChild(DocumentoXML.createTextNode(""));
                }

                TaxTableEntry.appendChild(TaxPercentage);

                TaxTable.appendChild(TaxTableEntry);
            }
            MasterFiles.appendChild(TaxTable);
//            Fim 

            Element SourceDocuments = DocumentoXML.createElement("SourceDocuments");
            // Num de doc's
            Element NumberOfEntries = DocumentoXML.createElement("NumberOfEntries");
            NumberOfEntries.appendChild(DocumentoXML.createTextNode("2"));
            SourceDocuments.appendChild(NumberOfEntries);

            Element TotalDebit = DocumentoXML.createElement("TotalDebit");
            TotalDebit.appendChild(DocumentoXML.createTextNode("0,00"));
            SourceDocuments.appendChild(TotalDebit);

            Element TotalCredit = DocumentoXML.createElement("TotalDebit");
            TotalCredit.appendChild(DocumentoXML.createTextNode("2000,00"));
            SourceDocuments.appendChild(TotalCredit);

//            inicio
            Element Invoice = null;
            // Faturas
            ArrayList<ModeloFatura> MFs;
            MFs = TakeFaturas(DataDeinicio,dataFinal);

            for (int i = 0; i < MFs.size(); i++) {
                Invoice = DocumentoXML.createElement("Invoice");
                // VALOR
                ModeloFatura MF = MFs.get(i);
                Element InvoiceNo = DocumentoXML.createElement("InvoiceNo");
                if (MF.getIndicativo()==null)
                    MF.setIndicativo("");
                InvoiceNo.appendChild(DocumentoXML.createTextNode(MF.getTitulo()+" " + MF.getIndicativo() + " "+MF.getSerie() + Integer.toString(MF.getIdfactra())));
                Invoice.appendChild(InvoiceNo);

                Element DocumentStatus = DocumentoXML.createElement("DocumentStatus");

                Element InvoiceStatus = DocumentoXML.createElement("InvoiceStatus");
                // N (Normal) A (Anulado) F (Faturado)
                InvoiceStatus.appendChild(DocumentoXML.createTextNode("N"));
                DocumentStatus.appendChild(InvoiceStatus);

                Element InvoiceStatusDate = DocumentoXML.createElement("InvoiceStatusDate");
                // 2019-09-23T15:01:36
                String TakeDate[] = MF.getData().split("-");
                InvoiceStatusDate.appendChild(DocumentoXML.createTextNode(TakeDate[2] + "-" + TakeDate[1] + "-" + TakeDate[0] + "T" + MF.getHora()));
                DocumentStatus.appendChild(InvoiceStatusDate);

                Element SourceID = DocumentoXML.createElement("SourceID");
                // Utilizador
                String Utilizador = BuscarID(MF.getMu().getIdUtilizador());
                SourceID.appendChild(DocumentoXML.createTextNode(Utilizador));
                DocumentStatus.appendChild(SourceID);

                Element SourceBilling = DocumentoXML.createElement("SourceBilling");
                // P (Documento produzido na própria aplicação).
                SourceBilling.appendChild(DocumentoXML.createTextNode("P"));
                DocumentStatus.appendChild(SourceBilling);

                Invoice.appendChild(DocumentStatus);

                Element Hash = DocumentoXML.createElement("Hash");
                // chave hash ou 0, se não haja obrigação de validação ou 8cb6bf1e559ae754e62cae07519531af49b65451
                 String chaveH  ="MIICcgIBADANBgkqhkiG9w0BAQEFAASCAlwwggJYAgEAAoGBAJZSFXGw6SoX0/komP2WfYqmrIhsUpwyISFOVKcA0WRxq6f/2JViCsOFzA94R3YKkVXXkpVggM5JvB0TuK2LqlnAi2kHbhwdharJOnSkXTcqSg+i4gIV61cem0SAG5Vw4ZizxH/15HTxekRLAuS7S9xhb4fYyTKXGiux9a39T7RhAgMBAAECfzYFSa+R5Dk8VvEzpcd8TaGGRHrCcu2vFjjh7YoWwSlXsFivmSYWFcguTSdCEmxZX+F5lzQXejKoTPImJDg5+P26znyk8WII8cFkWDHUPmyYxPnzrmmk349OVyKMuMw9MUsPA7mdgRLnD6LsGPunNuS7q05uD4SJQr1eP1HYhUECQQDTjJN/SpHaVzOWq1qY0vVqG3SDb3uRrfhU4x6RvQ9713pHV3K0E1yhk9B0Bo+2l6RMMTfN0D27PZXL8PJZW1M1AkEAtef4c3bxR9YBZF4qQfjbPNxQxJLnAKlTKJsYUvEI2T2B5gEhw/YiT4ZYD2DFBCeRRcPEov6q2bIUJg3zCJK1/QI/Qu83vA7cFzmAGSwefjxBalFFjFC5yF5DNJioE3S+cXC8P9ZrdI6rwHQEgLGSxzMzyOfJwFUjMxtJSj3ycFN1AkB0xq4gscLDMwdBv5GxOGjF8UmIaCYTrp/L+YTr+gCMNYgmd6ONgX0VQFFXze+scxo3hy281XAowMFejKXwYv6hAkB2YWUaMwuKajuxY58PGSnoEqgNuRGf2vIzOSuaFhD1gJ9C1hs2UMGn9NyWrLbWVyM1vad3ewW7EURv2tXI19Ut";
                
                Hash.appendChild(DocumentoXML.createTextNode(chaveH));
                Invoice.appendChild(Hash);

                Element HashControl = DocumentoXML.createElement("HashControl");
                // 0 se o programa usado não está validado.
                String hashValue  ="0";
                if (!MF.getHash().isEmpty() &&  MF.getHash()!=null)
                    hashValue = MF.getHash();
                HashControl.appendChild(DocumentoXML.createTextNode(hashValue));
                Invoice.appendChild(HashControl);

                Element InvoiceDate = DocumentoXML.createElement("InvoiceDate");
                // data da emissão ou conclusão do documento
                InvoiceDate.appendChild(DocumentoXML.createTextNode(TakeDate[2] + "-" + TakeDate[1] + "-" + TakeDate[0]));
                Invoice.appendChild(InvoiceDate);

                Element InvoiceType = DocumentoXML.createElement("InvoiceType");
                // FT, FR, RE, NC, ND
                InvoiceType.appendChild(DocumentoXML.createTextNode(MF.getTitulo()));
                Invoice.appendChild(InvoiceType);

                Element SpecialRegimes = DocumentoXML.createElement("SpecialRegimes");

                Element SelfBillingIndicator = DocumentoXML.createElement("SelfBillingIndicator");
                // 1 se respeita a autofaturação, 0 se não respeita.
                SelfBillingIndicator.appendChild(DocumentoXML.createTextNode("0"));
                SpecialRegimes.appendChild(SelfBillingIndicator);

                Element CashVATSchemeIndicator = DocumentoXML.createElement("CashVATSchemeIndicator");
                // 1 se há adesão ao regime de IVA de caixa, 0 se não há.
                CashVATSchemeIndicator.appendChild(DocumentoXML.createTextNode("0"));
                SpecialRegimes.appendChild(CashVATSchemeIndicator);

                Element ThirdPartiesBillingIndicator = DocumentoXML.createElement("ThirdPartiesBillingIndicator");
                // 1 se respeita a faturação em nome de terceiros, 0 se não respeita.
                ThirdPartiesBillingIndicator.appendChild(DocumentoXML.createTextNode("0"));
                SpecialRegimes.appendChild(ThirdPartiesBillingIndicator);

                Invoice.appendChild(SpecialRegimes);

                Element SourceID2 = DocumentoXML.createElement("SourceID");
                // Nome do utilizador
                SourceID2.appendChild(DocumentoXML.createTextNode(Utilizador));
                Invoice.appendChild(SourceID2);

                Element SystemEntryDate = DocumentoXML.createElement("SystemEntryDate");
                // Data da gravação do registro ao segundo
                SystemEntryDate.appendChild(DocumentoXML.createTextNode(TakeDate[2] + "-" + TakeDate[1] + "-" + TakeDate[0] + "T" + MF.getHora()));
                Invoice.appendChild(SystemEntryDate);

                Element CustomerID = DocumentoXML.createElement("CustomerID");
                // codigo do cliente
                CustomerID.appendChild(DocumentoXML.createTextNode("" + MF.getMc().getIdCliente()));
                Invoice.appendChild(CustomerID);

               
                ArrayList<ModeloVenda> Lista = TakeVendas(MF.getTitulo(), MF.getIdfactra());
                Element DocumentTotals = DocumentoXML.createElement("DocumentTotals");

                for (int j = 0; j < Lista.size(); j++) {
                    int number_ = j + 1;
                    ModeloVenda MV = Lista.get(j);
                     Element Line = DocumentoXML.createElement("Line");
                     Invoice.appendChild(Line);
                    Element LineNumber = DocumentoXML.createElement("LineNumber");
                    // 
                    SelfBillingIndicator.appendChild(DocumentoXML.createTextNode(MF.getTitulo()+" " + number_));
                    Line.appendChild(LineNumber);

                    Element ProductCode = DocumentoXML.createElement("ProductCode");
                    // codigo do produto.
                    ProductCode.appendChild(DocumentoXML.createTextNode(MV.getCodigo()));
                    Line.appendChild(ProductCode);

                    Element Quantity = DocumentoXML.createElement("Quantity");
                    // quantidade
                    Quantity.appendChild(DocumentoXML.createTextNode("" + MV.getQuant()));
                    Line.appendChild(Quantity);

                    Element UnitOfMeasure = DocumentoXML.createElement("UnitOfMeasure");
                    // Unidade da quantidade
                    UnitOfMeasure.appendChild(DocumentoXML.createTextNode("unidade"));
                    Line.appendChild(UnitOfMeasure);

                    Element UnitPrice = DocumentoXML.createElement("UnitPrice");
                    // Preço unitário
                    UnitPrice.appendChild(DocumentoXML.createTextNode("" + MV.getPreco_unit()));
                    Line.appendChild(UnitPrice);

                    Element TaxPointDate = DocumentoXML.createElement("TaxPointDate");
                    // Data de envio da mercadoria (Da guia de remessa se existir) ou da prestação de serviço.
                    TaxPointDate.appendChild(DocumentoXML.createTextNode(TakeDate[2] + "-" + TakeDate[1] + "-" + TakeDate[0]));
                    Line.appendChild(TaxPointDate);

                    Element Description = DocumentoXML.createElement("Description");
                    // Observação do produto
                    Description.appendChild(DocumentoXML.createTextNode(MV.getDescricao()));
                    Line.appendChild(Description);

                    Element CreditAmount = DocumentoXML.createElement("CreditAmount");
                    // PREÇO DE VENDA do produto
                    double precoVenda = MV.getPreco_unit();
                    boolean iva = false;
                    if (MV.getIva().equals("14")) {
                        precoVenda += MV.getPreco_unit() * 0.14;
                        iva = true;

                    }

                    CreditAmount.appendChild(DocumentoXML.createTextNode("" + precoVenda));
                    Line.appendChild(CreditAmount);

                    Element Tax = DocumentoXML.createElement("Tax");
                    //
                    Element TaxType = DocumentoXML.createElement("TaxType");
                    // 
                    TaxType.appendChild(DocumentoXML.createTextNode("IVA"));
                    Tax.appendChild(TaxType);

                    Element TaxCountryRegion = DocumentoXML.createElement("TaxType");
                    // 
                    TaxCountryRegion.appendChild(DocumentoXML.createTextNode("AO"));
                    Tax.appendChild(TaxCountryRegion);

                    // 
                    String Tcodigo = "ISE";
                    String PERCENTAGEM = "0";

                    if (iva) {
                        Tcodigo = "NOR";
                        PERCENTAGEM = "14";
                    }
                    Element TaxCode = DocumentoXML.createElement("TaxCode");
                    TaxCode.appendChild(DocumentoXML.createTextNode(Tcodigo));

                    Tax.appendChild(TaxCode);

                    Element TaxPercentage = DocumentoXML.createElement("TaxPercentage");
                    TaxPercentage.appendChild(DocumentoXML.createTextNode(PERCENTAGEM));

                    Tax.appendChild(TaxPercentage);

                    Line.appendChild(Tax);

                    if (!iva) {
                        Element TaxExemptionReason = DocumentoXML.createElement("TaxExemptionReason");
                        // Motivo legal de isenção.
                        TaxExemptionReason.appendChild(DocumentoXML.createTextNode(MV.getMotivo()));
                        Line.appendChild(TaxExemptionReason);

                        Element TaxExemptionCode = DocumentoXML.createElement("TaxExemptionCode");
                        TaxExemptionCode.appendChild(DocumentoXML.createTextNode(MV.getMotivo()));
                        Line.appendChild(TaxExemptionCode);

                    }

                    Element SettlementAmount = DocumentoXML.createElement("SettlementAmount");
                    SettlementAmount.appendChild(DocumentoXML.createTextNode("0"));
                    Line.appendChild(SettlementAmount);
//                    Invoice.appendChild(Line);

                    
                    

                    
                }
                    Element TaxPayable = DocumentoXML.createElement("TaxPayable");
                    // Imposto a pagar Total do IVA
                    TaxPayable.appendChild(DocumentoXML.createTextNode("" + MF.getTotal_doIVA()));
                    DocumentTotals.appendChild(TaxPayable);

                    Element NetTotal = DocumentoXML.createElement("NetTotal");
                    // Total sem Imposto
                    NetTotal.appendChild(DocumentoXML.createTextNode("" + MF.getSubTotal()));
                    DocumentTotals.appendChild(NetTotal);

                    Element GrossTotal = DocumentoXML.createElement("GrossTotal");
                    // Total com Imposto
                    GrossTotal.appendChild(DocumentoXML.createTextNode("" + MF.getTotal()));
                    DocumentTotals.appendChild(GrossTotal);
                Invoice.appendChild(DocumentTotals);
                SourceDocuments.appendChild(Invoice);
            }
            AuditFile.appendChild(SourceDocuments);

            DocumentoXML.appendChild(AuditFile);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource documentFonte = new DOMSource(DocumentoXML);
            StreamResult documentoFinal = new StreamResult(ficheiro);
            transformer.transform(documentFonte, documentoFinal);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Saftao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Saftao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void importar() {
        try {
            //objetos para construir e fazer a leitura do documento
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //abre e faz o parser de um documento xml de acordo com o nome passado no parametro
            Document doc = builder.parse("C:\\Users\\Gonga\\Desktop\\SAF-T(AO).xml");

            //cria uma lista de pessoas. Busca no documento todas as tag pessoa
            NodeList listaDePessoas = doc.getElementsByTagName("pessoa");

            //pego o tamanho da lista de pessoas
            int tamanhoLista = listaDePessoas.getLength();

            //varredura na lista de pessoas
            for (int i = 0; i < tamanhoLista; i++) {

                //pego cada item (pessoa) como um nó (node)
                Node noPessoa = listaDePessoas.item(i);

                //verifica se o noPessoa é do tipo element (e não do tipo texto etc)
                if (noPessoa.getNodeType() == Node.ELEMENT_NODE) {

                    //caso seja um element, converto o no Pessoa em Element pessoa
                    Element elementoPessoa = (Element) noPessoa;

                    //já posso pegar o atributo do element
                    String id = elementoPessoa.getAttribute("id");

                    //imprimindo o id
                    System.out.println("ID = " + id);

                    //recupero os nos filhos do elemento pessoa (nome, idade e peso)
                    NodeList listaDeFilhosDaPessoa = elementoPessoa.getChildNodes();

                    //pego o tamanho da lista de filhos do elemento pessoa
                    int tamanhoListaFilhos = listaDeFilhosDaPessoa.getLength();

                    //varredura na lista de filhos do elemento pessoa
                    for (int j = 0; j < tamanhoListaFilhos; j++) {

                        //crio um no com o cada tag filho dentro do no pessoa (tag nome, idade e peso)
                        Node noFilho = listaDeFilhosDaPessoa.item(j);

                        //verifico se são tipo element
                        if (noFilho.getNodeType() == Node.ELEMENT_NODE) {

                            //converto o no filho em element filho
                            Element elementoFilho = (Element) noFilho;

                            //verifico em qual filho estamos pela tag
                            switch (elementoFilho.getTagName()) {
                                case "nome":
                                    //imprimo o nome
                                    System.out.println("NOME=" + elementoFilho.getTextContent());
                                    break;

                                case "idade":
                                    //imprimo a idade
                                    System.out.println("IDADE=" + elementoFilho.getTextContent());
                                    break;

                                case "peso":
                                    //imprimo o peso
                                    System.out.println("PESO=" + elementoFilho.getTextContent());
                                    break;
                            }
                        }
                    }
                }
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Saftao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Saftao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Saftao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private double percentImpostoIVA;

    private ModeloEmpresa TakeEMP() {
        ModeloEmpresa ME = new ModeloEmpresa();

        liga.conexao();
        liga.executeSql("select * from empresa limit 1");
        String tamanho = "A4";
        try {
            if (liga.rs.first()) {
                ME.setIdempresa(liga.rs.getInt(1));
                ME.setDesignacao(liga.rs.getString("designacao"));
                ME.setNif(liga.rs.getString("nif"));
                ME.setRegisto_comercial(liga.rs.getString("registo_comercial"));
                ME.setRazao_social(liga.rs.getString("razao_social"));
                ME.setTelefone((liga.rs.getInt("telefone")));
                ME.setEmail(liga.rs.getString("email"));
                ME.setWeb_site(liga.rs.getString("web_site"));
                ME.setPais(liga.rs.getString("pais"));
                ME.setProvincia(liga.rs.getString("provincia"));
                ME.setRua(liga.rs.getString("rua"));
                ME.setEdificio(liga.rs.getString("edificio"));
                ME.setCodigo_postal(liga.rs.getString("codigo_postal"));
                ME.setImagem_logotipo(liga.rs.getString("imagem_logotipo"));
//                jLabel_NomeDaEmp.setText(ME.getDesignacao());
//                jLabel_nif.setText(ME.getNif());
                percentImpostoIVA = liga.rs.getDouble("valor_imposto_iva");
                ME.setRegime_de_iva(liga.rs.getString("regime_de_iva"));
//                tamanho = liga.rs.getString("tamanho_doc");
                ME.setTamanho_do_doc(tamanho);
                ME.setIndicativo(liga.rs.getString("indicativo"));
                ME.setTaxa_de_retencao(liga.rs.getDouble("retencao_na_fonte") / 100);
                ME.setPagador(liga.rs.getString("pagador_retencao"));

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Os daods da empresa estão incompletos\n" + ex, "Erro na elaboração do SAFT ", JOptionPane.WARNING_MESSAGE);

        }
        liga.deconecta();

        return ME;
    }

    private ArrayList<ModeloCliente> TakeClientes() {
        ArrayList<ModeloCliente> MCs = new ArrayList<>();

        liga.conexao();
        liga.executeSql("select * from cliente order by idcliente,designacao ");
        try {
            if (liga.rs.first()) {
                do {
                    ModeloCliente MC = new ModeloCliente();

                    MC.setIdCliente(liga.rs.getInt("idcliente"));
                    MC.setDesignacao(liga.rs.getString("designacao"));
                    MC.setBi(liga.rs.getString("bi"));
                    MC.setNif(liga.rs.getString("nif"));
                    MC.setEndereco(liga.rs.getString("email"));
                    MC.setEndereco(liga.rs.getString("endereco"));
                    MC.setTelefone(liga.rs.getString("telefone"));
                    MC.setCidade(liga.rs.getString("cidade"));
//                this.jTextField_nomeDaFoto.setText(liga.rs.getString("dados_do_servico"));
//            this.jTextField_nomeDaFoto.setText(liga.rs.getString("nome_da_foto"));
//            this.revert(liga.rs.getBytes("foto"));
                    String s = liga.rs.getString("sexo");
                    String pessoa = liga.rs.getString("tipo_de_pessoa");

                    if (s != null && s.equals("F")) {
                        MC.setSexo(s);
                    } else if (s != null && s.equals("M")) {
                        MC.setSexo(s);
                    } else {
                        MC.setSexo("DESCONHECIDO");
                    }

                    MC.setTipo_de_pessoa(pessoa);
                    System.out.println(MC.getIdCliente());
                    MCs.add(MC);
                } while (liga.rs.next());
            }
            liga.deconecta();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Contacte o Suporte do aplicativo\nClientes não encontrados\n" + ex, "Erro na elaboração do SAFT ", JOptionPane.WARNING_MESSAGE);

        }
        return MCs;
    }

    private ArrayList<ModeloProduto> TakeProdutos() {
        ArrayList<ModeloProduto> MPs = new ArrayList<>();

        liga.conexao();
        liga.executeSql("select * from produto order by idproduto, codigo, descricao ");
        try {

            if (liga.rs.first()) {
                do {
                    ModeloProduto MP = new ModeloProduto();
                    MP.setIdproduto(liga.rs.getInt("idproduto"));
                    MP.setCodigo(liga.rs.getString("codigo"));
                    MP.setCodigo_de_barra(liga.rs.getString("codigo_de_barra"));
                    MP.setDescricao(liga.rs.getString("descricao"));
//                this.jTextField_existencia.setText(liga.rs.getString("existencia"));
                    MPs.add(MP);
                } while (liga.rs.next());
            }

            liga.deconecta();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return MPs;
    }

    private ArrayList<ModeloServico> TakeServicos() {
        ArrayList<ModeloServico> MPs = new ArrayList<>();

        liga.conexao();
        liga.executeSql("select * from servico order by idservico, descricao ");
        try {
            if (liga.rs.first()) {
                do {
                    ModeloServico MP = new ModeloServico();

                    MP.setIdservico(liga.rs.getInt("idservico"));
//                MP.setCodigo(liga.rs.getString("codigo"));
//                MP.setCodigo_de_barra(liga.rs.getString("codigo_de_barra"));
                    MP.setDescricao(liga.rs.getString("descricao"));
//                this.jTextField_existencia.setText(liga.rs.getString("existencia"));
                    MPs.add(MP);
                } while (liga.rs.next());
            }

            liga.deconecta();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex + "11", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return MPs;
    }

    private ArrayList<ModeloFatura> TakeFaturas(String DataDeinicio, String dataFinal) {
        ArrayList<ModeloFatura> MFs = new ArrayList<>();
        liga.conexao();
        liga.executeSql("select * from fatura where "
                + " TO_DATE(data,'DD-MM-YYYY') >='" +DataDeinicio+ "' AND TO_DATE(data,'DD-MM-YYYY') <= '" + dataFinal + "' "
                + "order by idfactura ");
        try {
            if (liga.rs.first()) {
                do {
                    ModeloFatura MP = new ModeloFatura();

                    MP.setIdfactra(liga.rs.getInt("idfactura"));
                    //MP.setIdfactra(liga.rs.getInt("idfatura_recibo"));
                    MP.setData(liga.rs.getString("data"));
                    System.out.println("FT"+liga.rs.getString("data"));
                    MP.setHora(liga.rs.getString("hora"));
                    MP.setSubTotal(liga.rs.getDouble("subtotal"));
                    MP.setTotal(liga.rs.getDouble("total"));
                    MP.setTotal_doIVA(liga.rs.getDouble("imposto_total"));
                    MP.setSerie(liga.rs.getString("serie"));
                    MP.setHash(liga.rs.getString("hash"));
                    MP.setTitulo("FT");
                    MP.setMc(new ModeloCliente(liga.rs.getInt("idcliente")));
                    MP.setMu(new ModeloUtilizador(liga.rs.getInt("idutilizador")));
                    
                    MFs.add(MP);
                } while (liga.rs.next());
            }

            liga.deconecta();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro na exportação de fatura", JOptionPane.ERROR_MESSAGE);
        }
        liga.conexao();
        liga.executeSql("select * from recibo "
                + "where TO_DATE(data,'DD-MM-YYYY') >='" +DataDeinicio+ "' AND TO_DATE(data,'DD-MM-YYYY') <= '" + dataFinal + "'"
                + " order by idfatura_recibo ");
        try {
            if (liga.rs.first()) {
                do {
                    ModeloFatura MP = new ModeloFatura();

                    MP.setIdfactra(liga.rs.getInt("idfatura_recibo"));
                    MP.setData(liga.rs.getString("data"));
                    System.out.println("FR "+liga.rs.getString("data"));
                    MP.setHora(liga.rs.getString("hora"));
                    MP.setSubTotal(liga.rs.getDouble("subtotal"));
                    MP.setTotal(liga.rs.getDouble("valor_pago"));
                    MP.setTotal_doIVA(liga.rs.getDouble("imposto_total"));
                    MP.setSerie(liga.rs.getString("serie"));
                    MP.setHash(liga.rs.getString("hash"));
                    MP.setTitulo("FR");
                    MP.setMc(new ModeloCliente(liga.rs.getInt("idcliente")));
                    MP.setMu(new ModeloUtilizador(liga.rs.getInt("idutilizador")));
//                this.jTextField_existencia.setText(liga.rs.getString("existencia"));
                    MFs.add(MP);
                    this.Total += MP.getTotal();
                    if (MP.getSubTotal() != null) {
                        this.Subtotal += MP.getSubTotal();
                    }

                    this.imposto += MP.getTotal_doIVA();
                } while (liga.rs.next());
            }

            liga.deconecta();
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex, "Erro na exportação de fatura recibo", JOptionPane.ERROR_MESSAGE);
        }
        liga.conexao();
        liga.executeSql("select liquidacao.*, fatura.* from liquidacao " +
"INNER JOIN fatura  ON liquidacao.idfatura = fatura.idfactura " +
"where TO_DATE(liquidacao.data,'DD-MM-YYYY') >='" +DataDeinicio+ "' AND TO_DATE(liquidacao.data,'DD-MM-YYYY') <= '" + dataFinal + "' "
                + " order by idliquidacao ");
        try {
            if (liga.rs.first()) {
                do {
                    ModeloFatura MP = new ModeloFatura();

                    MP.setIdfactra(liga.rs.getInt("idliquidacao"));
                    MP.setData(liga.rs.getString("data"));
                    MP.setHora(liga.rs.getString("hora"));
                    MP.setSubTotal(liga.rs.getDouble("valor"));
                    MP.setTotal(liga.rs.getDouble("valor"));
                    MP.setTotal_doIVA(0.0);
                    MP.setTitulo("RE");
                    MP.setSerie(liga.rs.getString("serie"));
                    MP.setHash(liga.rs.getString("hash"));
                    MP.setMc(new ModeloCliente(liga.rs.getInt("idcliente")));
                    MP.setMu(new ModeloUtilizador(liga.rs.getInt("idutilizador")));
//                
                    MFs.add(MP);
//                    this.Total += MP.getTotal();
                    this.Subtotal += MP.getSubTotal();
//                    this.imposto += MP.getTotal_doIVA();
                } while (liga.rs.next());
            }

            liga.deconecta();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro na exportação de recibo", JOptionPane.ERROR_MESSAGE);
        }

        return MFs;
    }

    private String BuscarID(int idUtilizador) {
        //System.out.println("user"+idUtilizador);
        liga.conexao();
        liga.executeSql("select nome  from utilizador where idutilizador = " + idUtilizador + " ");

        try {
            if (liga.rs.last()) {
                return liga.rs.getString("nome");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Saftao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    private ArrayList<ModeloVenda> TakeVendas(String titulo, int num) {
        int origem = -1;
        if (titulo.equals("FT")) {
            origem = 2;
        } else if (titulo.equals("FR")) {
            origem = 1;
        }
        ArrayList<ModeloVenda> Lista = new ArrayList<ModeloVenda>();
        if (origem != -1) {

            liga.conexao();
            liga.executeSql("select * from venda where  origem = " + origem + " AND idfactura = " + num + " Order by idvenda asc");

            try {
                if (liga.rs.last()) {
                    do {
                        ModeloVenda MV = new ModeloVenda();
                        MV.setIdFatura(num);
                        MV.setDescricao(liga.rs.getString("descricao"));
                        String sql = " ";

                        if (liga.rs.getString("tipo").equals("Produto")) {
                            MV.setTipo("P");
                            sql = "select idproduto, preco_unitario, imposto_iva from produto where descricao= '" + MV.getDescricao() + "' ";

                        }
                        if (liga.rs.getString("tipo").equals("Serviço")) {
                            MV.setTipo("select * ");
                            sql = "select idservico, preco_unitario, imposto_iva from servico where descricao= '" + MV.getDescricao() + "' ";
                        }

                        MV.setQuant(liga.rs.getInt("quantidade"));
                        MV.setOrigem(origem);
                        MV.setPreco_unit(liga.rs.getDouble("preco_unitario"));
                        MV.setMotivo(liga.rs.getString("motivo"));
                        if (liga.rs.getString("iva").equals("14%")) {
                            MV.setIva("14");
                        } else {
                            MV.setIva("0");
                        }
                        ;
                        MV.setCodigo(""+OderData(MV, sql));
                        System.out.println("ID " + MV.getCodigo());

                        Lista.add(MV);

                    } while (liga.rs.next());
                }
            } catch (SQLException ex) {
                Logger.getLogger(Saftao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return Lista;
    }

    private int OderData(ModeloVenda MV, String sql) {
        liga.conexao();
        liga.executeSql(sql);
        try {

            if (liga.rs.first()) {
               return liga.rs.getInt(1);
//                MV.setPreco_unit(liga.rs.getDouble(2));

//                liga.deconecta();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.WARNING_MESSAGE);
        }
        return  -1;
                
    }

}

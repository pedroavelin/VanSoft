/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


/**
 *
 * @author Gonga
 */
public class xmldoc {
    
    public static void exportar () throws SAXException{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            
            Document DocumentoXML = documentBuilder.newDocument();
            Element root = DocumentoXML.createElement("root");
            
            Element pessoa = DocumentoXML.createElement("pessoa");
//            Attr id = DocumentoXML.createAttribute("Id");
//            id.setValue("1");
//            pessoa.setAttributeNode(id);
            root.appendChild(pessoa);
            
            Element nome = DocumentoXML.createElement("Nome");
            nome.appendChild(DocumentoXML.createTextNode("Doroteio"));
            pessoa.appendChild(nome);
            
            
            Element idade = DocumentoXML.createElement("Idade");
            idade.appendChild(DocumentoXML.createTextNode("13"));
            pessoa.appendChild(idade);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource documentFonte = new DOMSource(DocumentoXML);
            StreamResult documentoFinal = new StreamResult(new File("C:\\Users\\Gonga\\Desktop\\pessoa.xml"));
            transformer.transform(documentFonte, documentoFinal);
                   } catch (ParserConfigurationException ex) {
            Logger.getLogger(xmldoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (TransformerException ex) {
            Logger.getLogger(xmldoc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void importar (){
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
                if(noPessoa.getNodeType() == Node.ELEMENT_NODE){
                    
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
                        if(noFilho.getNodeType() == Node.ELEMENT_NODE){
                            
                            //converto o no filho em element filho
                            Element elementoFilho = (Element) noFilho;
                            
                            //verifico em qual filho estamos pela tag
                            switch(elementoFilho.getTagName()){
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
            Logger.getLogger(xmldoc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(xmldoc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(xmldoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

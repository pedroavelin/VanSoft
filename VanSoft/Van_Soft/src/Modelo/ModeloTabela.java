/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Octávio Mfr
 */
public class ModeloTabela extends AbstractTableModel{
    
     public ModeloTabela(ArrayList lin, String[] col) {
        setColunas(col);
        setLinhas(lin);
    }
     
    private ArrayList linhas = null;
    private String[] colunas = null;

    public ArrayList getLinhas() {
        return linhas;
    }

    public void setLinhas(ArrayList linhas) {
        this.linhas = linhas;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void setColunas(String[] colunas) {
        this.colunas = colunas;
    }

   


     @Override
   public int getColumnCount() {
        return colunas.length;
    }

 
     @Override
    public int getRowCount() {
        return linhas.size();
    }

     @Override
    public String getColumnName(int numcol) {
        return colunas[numcol];
    }
  
     @Override
    public Object getValueAt(int numLin,int numCol){
        Object[] linha = (Object[])getLinhas().get(numLin);
        return linha[numCol];
    }
     @Override
    public void setValueAt(Object newVal, int numLin,int numCol){
        Object[] linha = (Object[])getLinhas().get(numLin);
        linha[numCol]= newVal;
    }
    
    
 
 
}

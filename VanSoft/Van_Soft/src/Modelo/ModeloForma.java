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
public class ModeloForma {
    private int idForma;
    private String descricao;

    public ModeloForma(int idForma, String descricao) {
        this.idForma = idForma;
        this.descricao = descricao;
    }

    public ModeloForma(int idForma) {
        this.idForma = idForma;
    }

    public ModeloForma() {
    }

    public int getIdForma() {
        return idForma;
    }

    public void setIdForma(int idForma) {
        this.idForma = idForma;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
    
}

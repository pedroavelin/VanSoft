/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Gonga
 */
public class ModeloContaForma {
    
    private int idconta;
    private String nome_banco, abreviacao, numero_da_conta, ibam;
    private ArrayList <Integer> formasId;

    public ModeloContaForma() { }

    public ModeloContaForma(int idconta) {
        this.idconta = idconta;
    }

    public ModeloContaForma(int idconta, String nome_banco, String abreviacao, String numero_da_conta, String ibam, ArrayList<Integer> formasId) {
        this.idconta = idconta;
        this.nome_banco = nome_banco;
        this.abreviacao = abreviacao;
        this.numero_da_conta = numero_da_conta;
        this.ibam = ibam;
        this.formasId = formasId;
    }

    public int getIdconta() {
        return idconta;
    }

    public void setIdconta(int idconta) {
        this.idconta = idconta;
    }

    public String getNome_banco() {
        return nome_banco;
    }

    public void setNome_banco(String nome_banco) {
        this.nome_banco = nome_banco;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public String getNumero_da_conta() {
        return numero_da_conta;
    }

    public void setNumero_da_conta(String numero_da_conta) {
        this.numero_da_conta = numero_da_conta;
    }

    public String getIbam() {
        return ibam;
    }

    public void setIbam(String ibam) {
        this.ibam = ibam;
    }

    public ArrayList <Integer> getFormasId() {
        return formasId;
    }

    public void setFormasId(ArrayList <Integer> formasId) {
        this.formasId = formasId;
    }
    
    
    
    
    
}

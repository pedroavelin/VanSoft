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
public class ModeloServico {
    private int idservico;
    private boolean imposto_iva,retencao_na_fonte;
    private double custo, preco, preco_venda,valor_iva;
    private String descricao,codigo, 
            motivo, 
            observacao, 
            foto; 

    public ModeloServico(int idservico, boolean imposto_iva, double custo, double preco, String descricao, String motivo, String observacao, String foto) {
        this.idservico = idservico;
        this.imposto_iva = imposto_iva;
        this.custo = custo;
        this.preco = preco;
        this.descricao = descricao;
        this.motivo = motivo;
        this.observacao = observacao;
        this.foto = foto;
    }

    public ModeloServico(int idservico) {
        this.idservico = idservico;
    }

    public ModeloServico() {}

    public int getIdservico() {
        return idservico;
    }

    public void setIdservico(int idservico) {
        this.idservico = idservico;
    }

    public boolean isImposto_iva() {
        return imposto_iva;
    }

    public void setImposto_iva(boolean imposto_iva) {
        this.imposto_iva = imposto_iva;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public double getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(double preco_venda) {
        this.preco_venda = preco_venda;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getValor_iva() {
        return valor_iva;
    }

    public void setValor_iva(double valor_iva) {
        this.valor_iva = valor_iva;
    }

    public boolean isRetencao_na_fonte() {
        return retencao_na_fonte;
    }

    public void setRetencao_na_fonte(boolean retencao_na_fonte) {
        this.retencao_na_fonte = retencao_na_fonte;
    }

    
    
}

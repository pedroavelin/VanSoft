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
public class ModeloProduto {
    private int idproduto, existencia;
    private double preco, custo,preco_venda,valor_iva;
    private boolean imposto_iva;
    private String codigo, 
            codigo_de_barra, 
            descricao, marca, 
            modelo, origem, 
            nome_foto,
            motivo,lote;

    public ModeloProduto() {
    }

    public ModeloProduto(int idproduto) {
        this.idproduto = idproduto;
    }

    public ModeloProduto(int idproduto, int existencia, double preco, boolean imposto_iva, String codigo, String codigo_de_barra, String descricao, String marca, String modelo, String origem, String nome_foto, String motivo) {
        this.idproduto = idproduto;
        this.existencia = existencia;
        this.preco = preco;
        this.imposto_iva = imposto_iva;
        this.codigo = codigo;
        this.codigo_de_barra = codigo_de_barra;
        this.descricao = descricao;
        this.marca = marca;
        this.modelo = modelo;
        this.origem = origem;
        this.nome_foto = nome_foto;
        this.motivo = motivo;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }
    
    public int getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(int idproduto) {
        this.idproduto = idproduto;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isImposto_iva() {
        return imposto_iva;
    }

    public void setImposto_iva(boolean imposto_iva) {
        this.imposto_iva = imposto_iva;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo_de_barra() {
        return codigo_de_barra;
    }

    public void setCodigo_de_barra(String codigo_de_barra) {
        this.codigo_de_barra = codigo_de_barra;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getNome_foto() {
        return nome_foto;
    }

    public void setNome_foto(String nome_foto) {
        this.nome_foto = nome_foto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public double getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(double preco_venda) {
        this.preco_venda = preco_venda;
    }

    public double getValor_iva() {
        return valor_iva;
    }

    public void setValor_iva(double valor_iva) {
        this.valor_iva = valor_iva;
    }
    
    
    
}

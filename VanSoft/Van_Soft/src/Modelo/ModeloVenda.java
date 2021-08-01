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
public class ModeloVenda {
    private int idVenda, idFatura, idServico, idcliente, quant, origem;
    private String tipo, descricao, data, codigo, motivo,iva, unidade;
    private Double preco_unit,desconto, total, rentcao;
    

    public ModeloVenda() {
    }
    
    
    public ModeloVenda(int idVenda, int idFatura) {
        this.idVenda = idVenda;
        this.idFatura = idFatura;
    }

    public ModeloVenda(int idFatura) {
        this.idFatura = idFatura;
    }

    public ModeloVenda(int idVenda, int idFatura, String codigo, int idServico,int idcliente, int quant, String tipo, String descricao, String data, Double preco_unit, Double desconto, Double total) {
        this.idVenda = idVenda;
        this.idcliente = idcliente;
        this.idFatura = idFatura;
        this.codigo = codigo;
        this.idServico = idServico;
        this.quant = quant;
        this.tipo = tipo;
        this.descricao = descricao;
        this.data = data;
        this.preco_unit = preco_unit;
        this.desconto = desconto;
        this.total = total;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdFatura() {
        return idFatura;
    }

    public void setIdFatura(int idFatura) {
        this.idFatura = idFatura;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getPreco_unit() {
        return preco_unit;
    }

    public void setPreco_unit(Double preco_unit) {
        this.preco_unit = preco_unit;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Double getRentcao() {
        return rentcao;
    }

    public void setRentcao(Double rentcao) {
        this.rentcao = rentcao;
    }

    public int getOrigem() {
        return origem;
    }

    public void setOrigem(int origem) {
        this.origem = origem;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
    
    
    
    
    
    
}

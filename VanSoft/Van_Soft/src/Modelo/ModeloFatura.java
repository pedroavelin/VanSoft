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
public class ModeloFatura {
    private int idfactra;
    private String titulo,data, hora, modeda, armazem, localizacao_armazem, indicativo, hash,serie;
    private Double preco, desconto,retencao_na_fonte,total, pagamento, troco;
    private Double entrada, taxa, total_doIVA, subTotal;
    private ModeloEmpresa me;
    private ModeloUtilizador mu;
    private ModeloCliente mc;

    public String getModeda() {
        return modeda;
    }

    public void setModeda(String modeda) {
        this.modeda = modeda;
    }

    public ModeloFatura(int idfactra, String titulo, String data, String hora, String modeda, Double preco, Double desconto, Double retencao_na_fonte, Double total, Double pagamento, Double troco, Double entrada, Double taxa, Double total_doIVA, ModeloEmpresa me, ModeloUtilizador mu, ModeloCliente mc) {
        this.idfactra = idfactra;
        this.titulo = titulo;
        this.data = data;
        this.hora = hora;
        this.modeda = modeda;
        this.preco = preco;
        this.desconto = desconto;
        this.retencao_na_fonte = retencao_na_fonte;
        this.total = total;
        this.pagamento = pagamento;
        this.troco = troco;
        this.entrada = entrada;
        this.taxa = taxa;
        this.total_doIVA = total_doIVA;
        this.me = me;
        this.mu = mu;
        this.mc = mc;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public Double getTotal_doIVA() {
        return total_doIVA;
    }

    public void setTotal_doIVA(Double total_doIVA) {
        this.total_doIVA = total_doIVA;
    }

   
    public ModeloFatura() {
    }

    public ModeloFatura(int idfactra) {
        this.idfactra = idfactra;
    }
    
    public ModeloFatura(int idfactra, String titulo, Double preco, Double desconto, Double retencao_na_fonte, Double total, String data, String hora) {
        this.idfactra = idfactra;
        this.titulo = titulo;
        this.preco = preco;
        this.desconto = desconto;
        this.retencao_na_fonte = retencao_na_fonte;
        this.total = total;
        this.data = data;
        this.hora = hora;
    }

    public ModeloFatura(int idfactra, String titulo, Double preco, Double desconto, Double retencao_na_fonte, Double total, String data, String hora, ModeloEmpresa me, ModeloUtilizador mu, ModeloCliente mc) {
        this.idfactra = idfactra;
        this.titulo = titulo;
        this.preco = preco;
        this.desconto = desconto;
        this.retencao_na_fonte = retencao_na_fonte;
        this.total = total;
        this.data = data;
        this.hora = hora;
        this.me = me;
        this.mu = mu;
        this.mc = mc;
    }

    public int getIdfactra() {
        return idfactra;
    }

    public void setIdfactra(int idfactra) {
        this.idfactra = idfactra;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getRetencao_na_fonte() {
        return retencao_na_fonte;
    }

    public void setRetencao_na_fonte(Double retencao_na_fonte) {
        this.retencao_na_fonte = retencao_na_fonte;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public ModeloEmpresa getMe() {
        return me;
    }

    public void setMe(ModeloEmpresa me) {
        this.me = me;
    }

    public ModeloUtilizador getMu() {
        return mu;
    }

    public void setMu(ModeloUtilizador mu) {
        this.mu = mu;
    }

    public ModeloCliente getMc() {
        return mc;
    }

    public void setMc(ModeloCliente mc) {
        this.mc = mc;
    }

    public Double getEntrada() {
        return entrada;
    }

    public void setEntrada(Double entrada) {
        this.entrada = entrada;
    }

    public Double getPagamento() {
        return pagamento;
    }

    public void setPagamento(Double pagamento) {
        this.pagamento = pagamento;
    }

    public Double getTroco() {
        return troco;
    }

    public void setTroco(Double troco) {
        this.troco = troco;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getArmazem() {
        return armazem;
    }

    public void setArmazem(String armazem) {
        this.armazem = armazem;
    }

    public String getLocalizacao_armazem() {
        return localizacao_armazem;
    }

    public void setLocalizacao_armazem(String localizacao_armazem) {
        this.localizacao_armazem = localizacao_armazem;
    }

    public String getIndicativo() {
        return indicativo;
    }

    public void setIndicativo(String indicativo) {
        this.indicativo = indicativo;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }
    
    
    
}

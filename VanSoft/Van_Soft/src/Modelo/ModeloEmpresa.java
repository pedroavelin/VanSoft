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
public class ModeloEmpresa {
    int idempresa, telefone;
    String designacao, nif, registo_comercial, razao_social, email, 
            web_site, pais, provincia, rua, edificio, codigo_postal, 
            imagem_logotipo, regime_de_iva, tamanho_do_doc, indicativo,pagador, serie;
    double taxa_de_retencao;
    
    public ModeloEmpresa() {
    }

    public double getTaxa_de_retencao() {
        return taxa_de_retencao;
    }

    public void setTaxa_de_retencao(double taxa_de_retencao) {
        this.taxa_de_retencao = taxa_de_retencao;
    }

    public String getPagador() {
        return pagador;
    }

    public void setPagador(String pagador) {
        this.pagador = pagador;
    }
    
    public ModeloEmpresa(int idempresa, int telefone, String designacao, String nif, String registo_comercial, String razao_social, String email, String web_site, String pais, String provincia, String rua, String edificio, String codigo_postal, String imagem_logotipo) {
        this.idempresa = idempresa;
        this.telefone = telefone;
        this.designacao = designacao;
        this.nif = nif;
        this.registo_comercial = registo_comercial;
        this.razao_social = razao_social;
        this.email = email;
        this.web_site = web_site;
        this.pais = pais;
        this.provincia = provincia;
        this.rua = rua;
        this.edificio = edificio;
        this.codigo_postal = codigo_postal;
        this.imagem_logotipo = imagem_logotipo;
    }

    public ModeloEmpresa(int idempresa, int telefone, String designacao, String nif, String registo_comercial, String razao_social, String email, String web_site, String pais, String provincia, String rua, String edificio, String codigo_postal, String imagem_logotipo, String regime_de_iva, String tamanho_do_doc) {
        this.idempresa = idempresa;
        this.telefone = telefone;
        this.designacao = designacao;
        this.nif = nif;
        this.registo_comercial = registo_comercial;
        this.razao_social = razao_social;
        this.email = email;
        this.web_site = web_site;
        this.pais = pais;
        this.provincia = provincia;
        this.rua = rua;
        this.edificio = edificio;
        this.codigo_postal = codigo_postal;
        this.imagem_logotipo = imagem_logotipo;
        this.regime_de_iva = regime_de_iva;
        this.tamanho_do_doc = tamanho_do_doc;
    }

    public ModeloEmpresa(int idempresa, int telefone, String designacao, String nif, String registo_comercial, String razao_social, String email, String web_site, String pais, String provincia, String rua, String edificio, String codigo_postal, String imagem_logotipo, String regime_de_iva, String tamanho_do_doc, String indicativo) {
        this.idempresa = idempresa;
        this.telefone = telefone;
        this.designacao = designacao;
        this.nif = nif;
        this.registo_comercial = registo_comercial;
        this.razao_social = razao_social;
        this.email = email;
        this.web_site = web_site;
        this.pais = pais;
        this.provincia = provincia;
        this.rua = rua;
        this.edificio = edificio;
        this.codigo_postal = codigo_postal;
        this.imagem_logotipo = imagem_logotipo;
        this.regime_de_iva = regime_de_iva;
        this.tamanho_do_doc = tamanho_do_doc;
        this.indicativo = indicativo;
    }

    public String getIndicativo() {
        return indicativo;
    }

    public void setIndicativo(String indicativo) {
        this.indicativo = indicativo;
    }
    
    
    

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getRegisto_comercial() {
        return registo_comercial;
    }

    public void setRegisto_comercial(String registo_comercial) {
        this.registo_comercial = registo_comercial;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb_site() {
        return web_site;
    }

    public void setWeb_site(String web_site) {
        this.web_site = web_site;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getImagem_logotipo() {
        return imagem_logotipo;
    }

    public void setImagem_logotipo(String imagem_logotipo) {
        this.imagem_logotipo = imagem_logotipo;
    }

    public String getRegime_de_iva() {
        return regime_de_iva;
    }

    public void setRegime_de_iva(String regime_de_iva) {
        this.regime_de_iva = regime_de_iva;
    }

    public String getTamanho_do_doc() {
        return tamanho_do_doc;
    }

    public void setTamanho_do_doc(String tamanho_do_doc) {
        this.tamanho_do_doc = tamanho_do_doc;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }
    
}

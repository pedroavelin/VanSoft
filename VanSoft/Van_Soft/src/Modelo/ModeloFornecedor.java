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
public class ModeloFornecedor {
    private int idFornecedor; 
    private String designacao, endereco, bi, cidade, nif, tipo_de_pessoa, email, dados_da_oferta, foto, telefone, sexo, tipo_de_oferta;
    
    public ModeloFornecedor() {  }

    public ModeloFornecedor(int idterceirizador) {
        this.idFornecedor = idterceirizador;
    }

    public ModeloFornecedor(int idFornecedor, String designacao, String endereco, String bi, String cidade, String nif, String tipo_de_pessoa, String email, String dados_da_oferta, String foto, String telefone, String sexo, String tipo_de_oferta) {
        this.idFornecedor = idFornecedor;
        this.designacao = designacao;
        this.endereco = endereco;
        this.bi = bi;
        this.cidade = cidade;
        this.nif = nif;
        this.tipo_de_pessoa = tipo_de_pessoa;
        this.email = email;
        this.dados_da_oferta = dados_da_oferta;
        this.foto = foto;
        this.telefone = telefone;
        this.sexo = sexo;
        this.tipo_de_oferta = tipo_de_oferta;
    }

    
    
    

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getTipo_de_pessoa() {
        return tipo_de_pessoa;
    }

    public void setTipo_de_pessoa(String tipo_de_pessoa) {
        this.tipo_de_pessoa = tipo_de_pessoa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDados_da_oferta() {
        return dados_da_oferta;
    }

    public void setDados_da_oferta(String dados_da_oferta) {
        this.dados_da_oferta = dados_da_oferta;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipo_de_oferta() {
        return tipo_de_oferta;
    }

    public void setTipo_de_oferta(String tipo_de_oferta) {
        this.tipo_de_oferta = tipo_de_oferta;
    }
    
    
    
}

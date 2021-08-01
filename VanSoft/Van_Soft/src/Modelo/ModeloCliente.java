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
public class ModeloCliente {
    private int idCliente; 
    private String designacao, endereco, bi, cidade, nif, tipo_de_pessoa, email, foto, telefone, sexo;
    // Desconhecido
    
    public ModeloCliente() {
    }

    public ModeloCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public ModeloCliente(int idCliente, String designacao, String endereco, String bi, String cidade, String nif, String tipo_de_pessoa, String email, String foto, String telefone, String sexo) {
        this.idCliente = idCliente;
        this.designacao = designacao;
        this.endereco = endereco;
        this.bi = bi;
        this.cidade = cidade;
        this.nif = nif;
        this.tipo_de_pessoa = tipo_de_pessoa;
        this.email = email;
        this.foto = foto;
        this.telefone = telefone;
        this.sexo = sexo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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
    
    
    
}

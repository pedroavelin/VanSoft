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
public class ModeloTerceirizador {
    private int idterceirizador; 
    private String designacao, 
            endereco, 
            bairro, 
            cidade, 
            nif, 
            tipo_de_pessoa, 
            email, 
            dados_do_servico, 
            foto, 
            telefone, 
            sexo;
    
    public ModeloTerceirizador() {  }

    public ModeloTerceirizador(int idterceirizador) {
        this.idterceirizador = idterceirizador;
    }

    public ModeloTerceirizador(int idterceirizador, String designacao, String endereco, String bairro, String cidade, String nif, String tipo_de_pessoa, String email, String dados_do_servico, String foto, String telefone, String sexo) {
        this.idterceirizador = idterceirizador;
        this.designacao = designacao;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.nif = nif;
        this.tipo_de_pessoa = tipo_de_pessoa;
        this.email = email;
        this.dados_do_servico = dados_do_servico;
        this.foto = foto;
        this.telefone = telefone;
        this.sexo = sexo;
    }
    
    

    public int getIdterceirizador() {
        return idterceirizador;
    }

    public void setIdterceirizador(int idterceirizador) {
        this.idterceirizador = idterceirizador;
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

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
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

    public String getDados_do_servico() {
        return dados_do_servico;
    }

    public void setDados_do_servico(String dados_do_servico) {
        this.dados_do_servico = dados_do_servico;
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

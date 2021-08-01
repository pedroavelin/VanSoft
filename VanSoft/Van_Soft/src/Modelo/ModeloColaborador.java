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
public class ModeloColaborador {
    
    private int id;
    private String telefone1, telefone2, nome_completo, bi, nif, email, sexo, foto, ibam, funcao;
    
    public ModeloColaborador() {
    }

    public ModeloColaborador(int id) {
        this.id = id;
    }

    public ModeloColaborador(String telefone1, String telefone2, String nome_completo, String bi, String nif, String email, String sexo, String foto, String ibam, String funcao) {
        
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.nome_completo = nome_completo;
        this.bi = bi;
        this.nif = nif;
        this.email = email;
        this.sexo = sexo;
        this.foto = foto;
        this.ibam = ibam;
        this.funcao = funcao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    

    

    public String getNome_completo() {
        return nome_completo;
    }

    public void setNome_completo(String nome_completo) {
        this.nome_completo = nome_completo;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getIbam() {
        return ibam;
    }

    public void setIbam(String ibam) {
        this.ibam = ibam;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
    
    
    
}

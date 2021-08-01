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
public class ModeloUtilizador {
    private String nome_utilizador, nome, nif, email, sexo, perfil, senha;
    private int telefone, idUtilizador;

    public ModeloUtilizador(int idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    
    public String getNome_utilizador() {
        return nome_utilizador;
    }

    public void setNome_utilizador(String nome_utilizador) {
        this.nome_utilizador = nome_utilizador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public int getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(int idUtilizador) {
        this.idUtilizador = idUtilizador;
    }
    
    
    
    public ModeloUtilizador() {
    }

    public ModeloUtilizador(String nome_utilizador, String nome, String nif, String email, String sexo, String perfil, String senha, int telefone) {
        this.nome_utilizador = nome_utilizador;
        this.nome = nome;
        this.nif = nif;
        this.email = email;
        this.sexo = sexo;
        this.perfil = perfil;
        this.senha = senha;
        this.telefone = telefone;
    }
    

    
}

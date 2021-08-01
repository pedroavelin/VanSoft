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
public class ModeloNota {
    private int idNota, idcliente;
    private String objectivo, obs, data;
    private Double valor;
    
    public ModeloNota() {
    }

    public ModeloNota(int idNota) {
        this.idNota = idNota;
    }
    
    public ModeloNota(int idNota, int idcliente, String objectivo, String obs, Double valor, String data) {
        this.idNota = idNota;
        this.idcliente = idcliente;
        this.objectivo = objectivo;
        this.obs = obs;
        this.valor = valor;
        this.data = data;
    }

    public ModeloNota(int idNota, int idcliente) {
        this.idNota = idNota;
        this.idcliente = idcliente;
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getObjectivo() {
        return objectivo;
    }

    public void setObjectivo(String objectivo) {
        this.objectivo = objectivo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    
}

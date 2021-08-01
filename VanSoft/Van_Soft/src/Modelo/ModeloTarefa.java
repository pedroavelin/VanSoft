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
public class ModeloTarefa {
    private int Id;
    private String tarefa, 
            data, 
            local, 
            responsavel;
    
    
    
    public ModeloTarefa() {
    }

    public ModeloTarefa(int Id, String tarefa, String data, String local, String responsavel) {
        this.Id = Id;
        this.tarefa = tarefa;
        this.data = data;
        this.local = local;
        this.responsavel = responsavel;
    }

    public ModeloTarefa(int Id) {
        this.Id = Id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getTarefa() {
        return tarefa;
    }

    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
    
    
}

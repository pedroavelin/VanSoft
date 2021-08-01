/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

/**
 *
 * @author Gonga
 */



public class Dados{
	private String nomeUsuario;
	private String serial;
	
	
	
	public Dados(String nomeUsuario, String serial) {
		super();
		this.nomeUsuario = nomeUsuario;
		this.serial = serial;
	}
	@Override
	public String toString() {	
		return nomeUsuario + " - " + serial;
	}
	public String getNomeUsuario(){
		return this.nomeUsuario;
	}
	
	public String getSerial(){
		return this.serial;
	}
	
	public void setNomeUsuario(String nomeUsuario){
		this.nomeUsuario = nomeUsuario;
	}

	public void setSerial(String serial){
		this.serial = serial;
	}	
}
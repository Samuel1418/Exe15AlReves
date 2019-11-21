/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exa15_alreves;

import java.io.Serializable;

/**
 *
 * @author oracle
 */
public class Platos implements Serializable{
   private String codigop;
   private String nomep;
   private int graxatotal;


        public Platos(String codigo, String nome, int graxatotal)
	{
		this.codigop = codigo;
		this.nomep = nome;
                this.graxatotal=graxatotal;
	}

    public String getCodigop() {
        return codigop;
    }

    public void setCodigop(String codigop) {
        this.codigop = codigop;
    }

    public String getNomep() {
        return nomep;
    }

    public void setNomep(String nomep) {
        this.nomep = nomep;
    }

    public int getGraxatotal() {
        return graxatotal;
    }

    public void setGraxatotal(int graxatotal) {
        this.graxatotal = graxatotal;
    }

    @Override
    public String toString() {
        return "Platos{" + "codigop=" + codigop + ", nomep=" + nomep + ", graxatotal=" + graxatotal + '}';
    }

	
 
   
}

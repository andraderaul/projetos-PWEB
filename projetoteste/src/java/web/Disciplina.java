/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

/**
 *
 * @author raul
 */
public class Disciplina {
    
    private String cod_disc;
    private String nome_disc;
    private int carga_hor;
    
    public Disciplina(){
        cod_disc = null;
        nome_disc = null;
        carga_hor = 0;
    }

    /**
     * @return the cod_disc
     */
    public String getCod_disc() {
        return cod_disc;
    }

    /**
     * @param cod_disc the cod_disc to set
     */
    public void setCod_disc(String cod_disc) {
        this.cod_disc = cod_disc;
    }

    /**
     * @return the nome_disc
     */
    public String getNome_disc() {
        return nome_disc;
    }

    /**
     * @param nome_disc the nome_disc to set
     */
    public void setNome_disc(String nome_disc) {
        this.nome_disc = nome_disc;
    }

    /**
     * @return the carga_hor
     */
    public int getCarga_hor() {
        return carga_hor;
    }

    /**
     * @param carga_hor the carga_hor to set
     */
    public void setCarga_hor(int carga_hor) {
        this.carga_hor = carga_hor;
    }
}
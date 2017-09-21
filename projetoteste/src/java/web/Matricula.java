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
public class Matricula {
    private String nomeDisciplina;
    private String codDisciplina; //codigo da disciplina
    private String codTurma;
    private String diaHora;
    
    public Matricula(){
        nomeDisciplina = null;
        codDisciplina = null;
        codTurma = null;
        diaHora = null;
    }

    /**
     * @return the nomeDisciplina
     */
    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    /**
     * @param nomeDisciplina the nomeDisciplina to set
     */
    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    /**
     * @return the codDisciplina
     */
    public String getCodDisciplina() {
        return codDisciplina;
    }

    /**
     * @param codDisciplina the codDisciplina to set
     */
    public void setCodDisciplina(String codDisciplina) {
        this.codDisciplina = codDisciplina;
    }

    /**
     * @return the codTurma
     */
    public String getCodTurma() {
        return codTurma;
    }

    /**
     * @param codTurma the codTurma to set
     */
    public void setCodTurma(String codTurma) {
        this.codTurma = codTurma;
    }

    /**
     * @return the diaHora
     */
    public String getDiaHora() {
        return diaHora;
    }

    /**
     * @param diaHora the diaHora to set
     */
    public void setDiaHora(String diaHora) {
        this.diaHora = diaHora;
    }    
}

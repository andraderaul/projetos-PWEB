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
public class Turma {
    private String cod_disc; //codigo disciplina
    private String cod_turma; //codigo turma
    private String dia_hor; //horario
    private String loc_dida; //local didatica 
    
    public Turma(){
        cod_disc = null;
        cod_turma = null;
        dia_hor = null;
        loc_dida = null;
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
     * @return the cod_turma
     */
    public String getCod_turma() {
        return cod_turma;
    }

    /**
     * @param cod_turma the cod_turma to set
     */
    public void setCod_turma(String cod_turma) {
        this.cod_turma = cod_turma;
    }

    /**
     * @return the dia_hor
     */
    public String getDia_hor() {
        return dia_hor;
    }

    /**
     * @param dia_hor the dia_hor to set
     */
    public void setDia_hor(String dia_hor) {
        this.dia_hor = dia_hor;
    }
    
    public String getLoc_dida() {
        return loc_dida;
    }

    public void setLoc_dida(String loc_dida) {
        this.loc_dida = loc_dida;
    }
    
    public String getHoraioFormtado(){
        String str =  getDia_hor();
        String parts[] = str.split("");
        String str2 = new String();
        Integer aux = 0;
        switch (parts.length) {
            case 4:
                str2 =  parts[1] + parts[2];
                aux =  Integer.parseInt(str2) + Integer.parseInt(parts[3]);
                break;
            case 5:
                str2 =  parts[2] + parts[3];
                aux =  Integer.parseInt(str2) + Integer.parseInt(parts[4]);
                break;
            case 6:
                str2 =  parts[3] + parts[4];
                aux =  Integer.parseInt(str2) + Integer.parseInt(parts[5]);
                break;
            default:
                break;
        }
        str = "";
         
        switch(Integer.parseInt(parts[0])){
            case 2:
                str = "Seg " + str2 + " - " + aux.toString();
                break;
            case 3:
                 str = "Ter " + str2 +" - " + aux.toString();
                break;
            case 4:
                 str = "Qua " + str2 +" - " + aux.toString();
                break;
            case 5:
                 str = "Qui " + str2 +" - " + aux.toString();
                break;
            case 6:
                 str = "Sex " + str2 +" - " + aux.toString();
                break;
            default:
        }
        
        if(parts.length>=4){
         switch(Integer.parseInt(parts[1])){
            case 3:
                 str = str + "<br>Ter " + str2 +" - " + aux.toString();
                break;
            case 4:
                 str = str + "<br>Qua " + str2 +" - " + aux.toString();
                break;
            case 5:
                 str = str + "<br>Qui " + str2 +" - " + aux.toString();
                break;
            case 6:
                 str = str + "<br>Sex " + str2 +" - " + aux.toString();
                break;
            default:
        }
        }
         
        if(parts.length == 6){
          switch(Integer.parseInt(parts[2])){
            case 4:
                 str = str + "<br>Qua " + str2 +" - " + aux.toString();
                break;
            case 5:
                 str = str + "<br>Qui " + str2 +" - " + aux.toString();
                break;
            case 6:
                 str = str + "<br>Sex " + str2 +" - " + aux.toString();
                break;
            default:
        }
        }
        return str;
    }
}
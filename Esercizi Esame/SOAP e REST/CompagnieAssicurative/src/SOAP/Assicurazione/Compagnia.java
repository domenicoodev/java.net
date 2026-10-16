package SOAP.Assicurazione;

import java.io.Serializable;

public class Compagnia implements Serializable {
    private int codice;
    private String nome;
    private String sede;
    private ListPol polizze = new ListPol();

    public Compagnia(){}
    public Compagnia(int codice, String nome, String sede, ListPol polizze){
        this.codice = codice;
        this.nome = nome;
        this.sede = sede;
        this.polizze = polizze;
    }

    public int getCodice(){
        return codice;
    }
    public void setCodice(int codice){
        this.codice = codice;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getSede(){
        return sede;
    }

    public void setSede(String sede){
        this.sede = sede;
    }

    public ListPol getPolizze(){
        return polizze;
    }

    public void setPolizze(ListPol polizze){
        this.polizze = polizze;
    }
}

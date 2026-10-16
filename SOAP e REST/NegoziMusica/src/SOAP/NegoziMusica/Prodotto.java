package SOAP.NegoziMusica;

import java.io.Serializable;

public class Prodotto implements Serializable {
    private String codice;
    private double prezzo;
    private int quantita;

    public Prodotto(){}
    public Prodotto(String codice,double prezzo, int quantita){
        this.codice = codice;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    public String getCodice(){
        return codice;
    }

    public void setCodice(String codice){
        this.codice = codice;
    }

    public double getPrezzo(){
        return prezzo;
    }

    public void setPrezzo(double prezzo){
        this.prezzo = prezzo;
    }

    public int getQuantita(){
        return quantita;
    }

    public void setQuantita(int quantita){
        this.quantita = quantita;
    }

}

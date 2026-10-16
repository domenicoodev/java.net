package SOAP.Assicurazione;

import java.io.Serializable;

public class Polizza implements Serializable {
    private String codice;
    private double premio;
    private double copertura;
    public Polizza(){}
    public Polizza(String codice, double premio, double copertura){
        this.codice = codice;
        this.premio = premio;
        this.copertura = copertura;
    }

    public String getCodice(){
        return codice;
    }

    public void setCodice(String codice){
        this.codice = codice;
    }

    public double getPremio(){
        return premio;
    }

    public void setPremio(double premio){
        this.premio = premio;
    }

    public double getCopertura(){
        return copertura;
    }

    public void setCopertura(double copertura){
        this.copertura = copertura;
    }
}

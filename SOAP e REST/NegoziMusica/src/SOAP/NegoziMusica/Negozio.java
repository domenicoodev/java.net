package SOAP.NegoziMusica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Negozio implements Serializable {
    private int codice;
    private String nome;
    private String ubicazione;
    List<Prodotto> prodotti = new ArrayList<>();

    public Negozio(){}
    public Negozio(int codice,String nome,String ubicazione,List<Prodotto> prodotti){
        this.codice = codice;
        this.nome = nome;
        this.ubicazione = ubicazione;
        this.prodotti = prodotti;
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

    public String getUbicazione(){
        return ubicazione;
    }

    public void setUbicazione(String ubicazione){
        this.ubicazione = ubicazione;
    }

    public List<Prodotto> getProdotti(){
        return prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti){
        this.prodotti = prodotti;
    }

    public void aggiungiProdotto(Prodotto p){
        prodotti.add(p);
    }
}

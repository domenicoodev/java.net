package java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//MODEL: Negozio
class Negozio implements Serializable{
    private int codice;
    private String nome;
    private String ubicazione;
    private List<Prodotto> prodotti;

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

    public void setNome(){
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
}


//MODEL: Prodotto
class Prodotto implements Serializable{
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


//DTONegozio
class DTONegozio implements Serializable{
    private int codice;
    private String nome;
    private String ubicazione;
    public DTONegozio(int codice,String nome, String ubicazione){
        this.codice = codice;
        this.nome = nome;
        this.ubicazione = ubicazione;
    }

    public int getCodice(){
        return codice;
    }

    public String getNome(){
        return nome;
    }

    public String getUbicazione(){
        return ubicazione;
    }
}

//DTOValore
class DTOValore implements Serializable{
    private double valoreTot;
    public DTOValore(double valoreTot){
        this.valoreTot = valoreTot;
    }

    public double getValoreTot(){
        return valoreTot;
    }
}


//SERVICE
@Service
class NegoziMusicaService implements Serializable{
    private List<Negozio> negozi = new ArrayList<>();

    public Negozio getNegozioMinimo(String codiceProdotto){
        Negozio minore = null;
        double prezzoMin = Double.MAX_VALUE;
        int quantitaNegozio = -1;
        for(Negozio n : negozi){
            for(Prodotto p : n.getProdotti()){
                if(p.getCodice().equals(codiceProdotto) && (p.getPrezzo() < prezzoMin ||
                        p.getPrezzo() == prezzoMin && p.getQuantita() > quantitaNegozio ||
                        p.getPrezzo() == prezzoMin && p.getQuantita() == quantitaNegozio && n.getCodice() < minore.getCodice())){
                    minore = n;
                    prezzoMin = p.getPrezzo();
                    quantitaNegozio = p.getQuantita();
                }
            }
        }
        return minore;
    }

    public double getValoreTotale(Negozio n){
        double totale = 0.0;
        for(Prodotto p : n.getProdotti()){
            totale += (p.getPrezzo() * p.getQuantita());
        }
        return totale;
    }
}

//CONTROLLER
@RestController
@RequestMapping
class NegozioMusicaController implements Serializable{
    @Autowired
    NegoziMusicaService service;

    @GetMapping("/prodotti/{codiceProdotto}/negozio-minimo")
    public DTONegozio getNegozioMinimo(@PathVariable String codiceProdotto){
        Negozio minimo = service.getNegozioMinimo(codiceProdotto);
        return new DTONegozio(minimo.getCodice(), minimo.getNome(), minimo.getUbicazione());
    }

    @PostMapping("/negozi/scorte")
    public DTOValore getValoreTotale(@RequestBody Negozio n){
        return new DTOValore(service.getValoreTotale(n));
    }
}


@SpringBootApplication
public class NegoziMusicaApplication implements Serializable{

    public static void main(String[] args){
        SpringApplication.run(NegoziMusicaApplication.class,args);
    }
}

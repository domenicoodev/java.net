package java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//MODEL: Polizza
class Polizza implements Serializable{
    String codice;
    double premio;
    double copertura;
    public Polizza(){}
    public Polizza(String codice,double premio, double copertura){
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

//MODEL: Compagnia
class Compagnia implements Serializable{
    private int codice;
    private String nome;
    private String sede;
    private List<Polizza> polizze = new ArrayList<>();
    public Compagnia(){}
    public Compagnia(int codice, String nome, String sede, List<Polizza> polizze){
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
    public List<Polizza> getPolizze(){
        return polizze;
    }
    public void setPolizze(List<Polizza> polizze){
        this.polizze = polizze;
    }
    public void aggiungiPollizza(Polizza p){
        polizze.add(p);
    }
}

//DTO: ValoreTotaleResponse
class ValoreTotaleResponse implements Serializable {
    private double valoreTotale;
    public ValoreTotaleResponse(double valoreTotale){
        this.valoreTotale = valoreTotale;
    }
    public double getValoreTotale(){
        return valoreTotale;
    }
}

//SERVICE
@Service
class CompagniaService implements Serializable {
    private List<Compagnia> compagnie = new ArrayList<>();

    public CompagniaService() {
        Compagnia c1 = new Compagnia(101, "Sicura S.p.A", "Milano", new ArrayList<>());
        c1.aggiungiPollizza(new Polizza("AUTO123", 320.0, 15000.0));
        c1.aggiungiPollizza(new Polizza("CASA456", 500.0, 30000.0));
        Compagnia c2 = new Compagnia(102, "Fiducia Assicurazioni", "Roma", new ArrayList<>());
        c2.aggiungiPollizza(new Polizza("AUTO123", 320.0, 14000.0));
        c2.aggiungiPollizza(new Polizza("CASA456", 500.0, 31000.0));
        compagnie.add(c1);
        compagnie.add(c2);
    }

    public Compagnia findCompagniaConPremioMinimo(String codicePolizza) {
        Compagnia minCompagnia = null;
        double maxCopertura = Double.MIN_VALUE;
        double minPremio = Double.MAX_VALUE;
        int minCodice = -1;
        for (Compagnia c : compagnie) {
            List<Polizza> listaPolizze = c.getPolizze();
            for (Polizza p : listaPolizze) {
                if (p.getCodice().equals(codicePolizza) &&
                        (p.getPremio() < minPremio ||
                                p.getPremio() == minPremio && p.getCopertura() > maxCopertura ||
                                p.getPremio() == minPremio && p.getCopertura() == maxCopertura &&
                                        c.getCodice() < minCodice)) {
                    minCompagnia = c;
                    minPremio = p.getPremio();
                    maxCopertura = p.getCopertura();
                    minCodice = c.getCodice();
                }
            }
        }
        return minCompagnia;
    }

    public double calcolaValoreTotale(String nome, String sede) {
        double valoreTot = 0.0;
        for (Compagnia c : compagnie) {
            if (c.getNome().equals(nome) && c.getSede().equals(sede)) {
                for (Polizza p : c.getPolizze()) {
                    valoreTot += p.getPremio();
                }
            }
        }
        return valoreTot;
    }
}

    //CONTROLLER
    @RestController
    @RequestMapping
    class CompagniaController implements Serializable {
        @Autowired
        private CompagniaService service;

        @GetMapping("/polizze/{codicePolizza}/compagnia-minima")
        public Compagnia getCompagniaMinima(@PathVariable String codicePolizza) {
            return service.findCompagniaConPremioMinimo(codicePolizza);
        }

        @GetMapping("/compagnie/valore")
        public ValoreTotaleResponse getValoreTotale(@RequestParam String nome, @RequestParam String sede) {
            double totale = service.calcolaValoreTotale(nome, sede);
            return new ValoreTotaleResponse(totale);
        }
    }

    //MAIN
    @SpringBootApplication
    public class AssicurazioniApplication implements Serializable {
        public static void main(String[] args){
            SpringApplication.run(AssicurazioniApplication.class,args);
        }
    }
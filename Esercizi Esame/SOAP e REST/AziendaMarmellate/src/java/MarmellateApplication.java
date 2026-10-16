package java;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/*
L'identificazione avviene tramite l'URL:
In REST, l'indirizzo web serve proprio a identificare la risorsa su cui stai lavorando.
L'endpoint richiesto è POST /aziende/{idAzienda}/vendite.
Inviando la richiesta a /aziende/5/vendite, stai già dicendo al server che stai
operando sull'azienda numero 5.

Oggetti in ingresso (Request): Li crea Spring Boot.
Non serve il costruttore (usa quello di default invisibile),
ma sono obbligatori i metodi Setter.

Oggetti in uscita (Response): Li creiamo noi. È comodissimo avere il costruttore con i parametri,
mentre i metodi Setter si possono anche omettere perché i dati non verranno più modificati.
*/


//DTO IncassoRequest
class IncassoRequest implements Serializable{
    private String tipoMarmellata;
    private double importo;

    public String getTipoMarmellata(){
        return tipoMarmellata;
    }

    public void setTipoMarmellata(){
        this.tipoMarmellata = tipoMarmellata;
    }

    public double getImporto(){
        return importo;
    }

    public void setImporto(double importo){
        this.importo = importo;
    }

}

//DTO: IncassoMiglioreResponse
class IncassoMiglioreResponse implements Serializable{
    private String tipoMarmellata;
    private double importo;
    public IncassoMiglioreResponse(String tipoMarmellata,double importo){
        this.tipoMarmellata = tipoMarmellata;
        this.importo = importo;
    }

    public String getTipoMarmellata(){
        return tipoMarmellata;
    }

    public double getImporto(){
        return importo;
    }
}

//SERVICE: AziendaService
@Service
class AziendaService implements Serializable{
    private Map<Integer, Map<String,Double>> aziende = new HashMap<>();

    public void registraIncasso(int idAzienda,IncassoRequest request){
        aziende.putIfAbsent(idAzienda,new HashMap<>());
        Map<String,Double> vendite = aziende.get(idAzienda);
        vendite.put(request.getTipoMarmellata(),
                vendite.getOrDefault(request.getTipoMarmellata(),0.0)+request.getImporto());
    }

    public IncassoMiglioreResponse getMiglioreIncasso(int idAzienda){
        Map<String,Double> vendite = aziende.get(idAzienda);
        if(vendite == null || vendite.isEmpty()){
            return new IncassoMiglioreResponse("", 0.0);
        }
        String migliore = "";
        double max = -1;
        for(Map.Entry<String,Double> entry : vendite.entrySet()){
            if(entry.getValue() > max){
                migliore = entry.getKey();
                max = entry.getValue();
            }
        }
        return new IncassoMiglioreResponse(migliore,max);
    }
}

//CONTROLLER:AziendaController
@RestController
@RequestMapping("/aziende")
class AziendaController implements Serializable{
    @Autowired private AziendaService service;
    @PostMapping("/{idAzienda}/vendite")
    public Map<String,String> salvaIncasso(@PathVariable int idAzienda,
                                           @RequestBody IncassoRequest request){
        service.registraIncasso(idAzienda,request);
        return Map.of("message","Incasso registrato con successo");
    }
    @GetMapping("/{idAzienda}/migliore")
    public IncassoMiglioreResponse miglioreVendita(@PathVariable int idAzienda){
        return service.getMiglioreIncasso(idAzienda);
    }
}

//MAIN APPLICATION
@SpringBootApplication
public class MarmellateApplication implements Serializable{
    public static void main(String[] args){
        SpringApplication.run(MarmellateApplication.class,args);
    }
}

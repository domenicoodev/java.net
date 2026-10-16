package SOAP.NegoziMusica;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "negozi.NegoziMusicaService")
public class NegoziMusicaServiceImpl implements NegoziMusicaService{
    List<Negozio> negozi = new ArrayList<>();


    @Override
    public Negozio MinPrezzoProdotto(String codice){
        Negozio minore = null;
        double prezzoMin = Double.MAX_VALUE;
        int quantitaNegozio = -1;
        for(Negozio n : negozi){
            for(Prodotto p : n.getProdotti()){
                if(p.getCodice().equals(codice) && (p.getPrezzo() < prezzoMin ||
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

    @Override
    public double ValoreScorte(Negozio n){
        double totale = 0.0;
        for(Prodotto p : n.getProdotti()){
            totale += (p.getPrezzo() * p.getQuantita());
        }
        return totale;
    }
}
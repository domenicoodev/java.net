package SOAP.Assicurazione;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "assicurazioni.AssicurazioniService")
public class AssicurazioniServiceImpl implements AssicurazioniService{
    private List<Compagnia> compagnie = new ArrayList<>();

    @Override
    public Compagnia MinPremioPolizza(String codice) {
        Compagnia minCompagnia = null;
        double maxCopertura = Double.MIN_VALUE;
        double minPremio = Double.MAX_VALUE;
        int minCodice = -1;
        for (Compagnia c : compagnie) {
            List<Polizza> listaPolizze = c.getPolizze().getListPol();
            for (Polizza p : listaPolizze) {
                if (p.getCodice().equals(codice) &&
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

    @Override
    public double ValorePolizze(String nome, String sede){
        double valoreTot = 0.0;
        for(Compagnia c : compagnie){
            if(c.getNome().equals(nome) && c.getSede().equals(sede)){
                for(Polizza p : c.getPolizze().getListPol()){
                    valoreTot += p.getPremio();
                }
            }
        }
        return valoreTot;
    }
}

package SOAP.azienda;

import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;

@WebService(endpointInterface = "azienda.AziendaService")
public class AziendaServiceImpl implements AziendaService{
    private Map<Integer, Map<String,Double>> database = new HashMap<>();


    @Override
    public void Vendita(int idAzienda, String tipoMarmellata, double importo){
        database.putIfAbsent(idAzienda, new HashMap<>());
        Map<String,Double> vendite = database.get(idAzienda);
        vendite.put(tipoMarmellata, vendite.getOrDefault(tipoMarmellata,0.0)+importo);
    }

    @Override
    public IncassoProdotto MaggioreIncasso(int idAzienda){
        Map<String,Double> vendite = database.get(idAzienda);
        if (vendite == null || vendite.isEmpty()){
            return new IncassoProdotto("", 0.0);
        }
        String tipoTop = "";
        double max = 0.0;
        for(Map.Entry<String,Double> entry : vendite.entrySet()){
            if(entry.getValue() > max){
                max = entry.getValue();
                tipoTop = entry.getKey();
            }
        }
        return new IncassoProdotto(tipoTop,max);
    }
}
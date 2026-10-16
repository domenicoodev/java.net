package SOAP.azienda;

import javax.jws.WebService;
import javax.jws.WebMethod;


@WebService
public interface AziendaService{
    @WebMethod
    public void Vendita(int idAzienda, String tipoMarmellata, double importo);
    @WebMethod
    public IncassoProdotto MaggioreIncasso(int idAzienda);
}
package SOAP.Assicurazione;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface AssicurazioniService {
    @WebMethod
    public Compagnia MinPremioPolizza(String codice);
    @WebMethod
    public double ValorePolizze(String nome, String sede);
}

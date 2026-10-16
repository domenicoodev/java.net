package SOAP.NegoziMusica;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface NegoziMusicaService {
    @WebMethod
    public Negozio MinPrezzoProdotto(String codice);
    @WebMethod
    public double ValoreScorte(Negozio n);
}

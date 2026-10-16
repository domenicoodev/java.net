import java.net.InetAddress;



public class Sottoscrizione {

    private String idSensore;

    private InetAddress indirizzo;



    public Sottoscrizione(String idSensore, InetAddress indirizzo) {

        this.idSensore = idSensore;

        this.indirizzo = indirizzo;

    }



    public String getIdSensore() { return idSensore; }

    public InetAddress getIndirizzo() { return indirizzo; }

} 
package SOAP.azienda;

public class IncassoProdotto{
    private String tipoMarmellata;
    private double importo;

    public IncassoProdotto(){}

    public IncassoProdotto(String tipoMarmellata, double importo){
        this.tipoMarmellata = tipoMarmellata;
        this.importo = importo;
    }

    public String getTipoMarmellata(){
        return tipoMarmellata;
    }

    public void setTipoMarmellata(String tipoMarmellata){
        this.tipoMarmellata = tipoMarmellata;
    }

    public double getImporto(){
        return importo;
    }

    public void setImporto(double importo){
        this.importo = importo;
    }
}

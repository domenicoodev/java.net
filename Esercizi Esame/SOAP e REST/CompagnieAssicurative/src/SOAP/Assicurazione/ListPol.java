package SOAP.Assicurazione;

import java.util.ArrayList;
import java.util.List;

public class ListPol {
    private List<Polizza> listPol = new ArrayList<>();
    public List<Polizza> getListPol(){
        return listPol;
    }

    public void setListPol(List<Polizza> listPol){
        this.listPol = listPol;
    }

    public void aggiungiPolizza(Polizza p){
        listPol.add(p);
    }
}

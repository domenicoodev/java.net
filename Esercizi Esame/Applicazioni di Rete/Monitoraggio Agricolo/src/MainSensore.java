public class MainSensore {

    public static void main(String[] args) {

        Sensore sensore = new Sensore("S1", "localhost");

        sensore.registraNotifiche();

        sensore.avviaRicezioneNotifiche();

        sensore.inviaStato(25.0, 40.0);

    }

}
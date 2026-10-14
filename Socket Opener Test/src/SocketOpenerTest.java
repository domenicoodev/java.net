/*
In questo codice utilizziamo un thread lato client per creare un meccanismo di timeout personalizzato
ed evitare che l'intero programma si blocchi all'infinito se il server non risponde.
Quando invochi direttamente il costruttore new Socket(host, port), il tuo programma si ferma
e aspetta che il server accetti la connessione. Se il server è irraggiungibile o c'è un problema di rete
(es. un firewall che scarta i pacchetti senza avvisare), l'istruzione può rimanere bloccata per minuti,
 congelando la tua applicazione.

Spostando la creazione della Socket in un thread separato, il programma principale può delegare il lavoro sporco
a un "aiutante" e dirgli: Aspetto che tu finisca per un massimo di 10 secondi. Se entro 10 secondi non mi hai dato la Socket,
 io vado avanti senza di te.

Ecco come funziona il codice, passo dopo passo:
Il Thread Principale (SocketOpenerTest): Definisce l'host universitario, la porta 80 (web) e un timeout di 10.000 millisecondi.
 Invece di creare direttamente la Socket, invoca il metodo statico SocketOpener.openSocket.

La Creazione dell'Aiutante (openSocket): Questo metodo istanzia SocketOpener (che estende Thread)
e lo avvia chiamando opener.start(). In questo preciso istante, il programma si divide in due percorsi paralleli.

Il Lavoro in Background (run): Il nuovo thread entra nel metodo run().
Lì dentro esegue l'istruzione bloccante socket = new Socket(host, port).
Se la connessione ha successo in frazioni di secondo, la variabile socket viene popolata.

L'Attesa a Tempo (join): Nel frattempo, il thread principale è sceso alla riga opener.join(timeout);.
Il metodo join significa "Mettiti in pausa e aspetta che il thread 'opener' muoia (finisca il suo lavoro)".
Passando il parametro timeout, gli stiamo dicendo di aspettare al massimo 10 secondi.

I due scenari possibili:
Connessione veloce: Il server risponde in 1 secondo. Il thread secondario finisce il suo run(),
popola la variabile socket e si chiude. Il join nel thread principale si sblocca immediatamente
(non aspetta tutti i 10 secondi) e getSocket() restituisce la Socket perfettamente funzionante.

Server bloccato (Timeout): Passano 10 secondi e il thread secondario è ancora bloccato nel tentativo di connessione.
Il join scade e forza il thread principale a svegliarsi e andare avanti. Il thread principale chiama getSocket(),
ma poiché il thread secondario non è riuscito a finire, la variabile è ancora null.
Verrà stampato "The socket could not be opened". (Nota: il thread secondario rimarrà bloccato in background
fino a quando il sistema operativo non taglierà la connessione,
ma almeno la tua applicazione principale è rimasta reattiva).

Questo pattern era molto comune nelle vecchie versioni di Java che non supportavano i timeout nativi.
Oggi, per ottenere lo stesso identico risultato senza usare thread aggiuntivi,
si utilizza questo approccio più moderno:

Socket s = new Socket();
// Tenta la connessione bloccandosi per un massimo di 10.000 ms
s.connect(new InetSocketAddress("www.dimes.unical.it", 80), 10000);
*/

import java.io.*;
import java.net.*;
public class SocketOpenerTest {
    public static void main(String[] args) {
        String host = "www.dimes.unical.it";
        int port = 80;
        int timeout = 10000; // dieci secondi
        Socket s = SocketOpener.openSocket(host, port, timeout);
        if (s == null)
            System.out.println("The socket could not be opened.");
        else
            System.out.println(s);
    }
}
class SocketOpener extends Thread {
    private String host;
    private int port;
    private Socket socket;
    public static Socket openSocket (String host, int port, int timeout) {
        SocketOpener opener = new SocketOpener(host, port);
        opener.start();
        try {
            opener.join(timeout); // it will wait at most timeout ms for the thread to die
        } catch (InterruptedException e) { System.err.println (e); }
        return opener.getSocket();
    }
    public SocketOpener(String host, int port) {
        this.host = host; this.port = port; socket = null;
    }
    public Socket getSocket() { return socket; }
    public void run() {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) { System.err.println (e); }
    }
}
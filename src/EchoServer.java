/*
Istanziamo un echo server o come lo chiamo io server pappagallo
che starà in ascolto sulla porta indicata.
Questo server però gestisce una singola connessione per volta.
Per gestire connessioni multiple dobbiamo spostare le richieste su diversi thread.
Vedi: ThreadedEchoServer.
*/

import java.io.*;
import java.net.*;
public class EchoServer {
    public static void main(String[] args ) {
        try {
            ServerSocket s = new ServerSocket(8189);
            Socket incoming = s.accept( ); // stop in attesa di una connessione
            BufferedReader in = new BufferedReader
                    (new InputStreamReader(incoming.getInputStream()));
            PrintWriter out = new PrintWriter
                    (incoming.getOutputStream(), true /* autoFlush */);
            out.println( "Ciao!" );
            out.println( "Benvenuto sull'Echo server." );
            out.println( "Scrivi ADDIO per uscire." );
            boolean done = false;
            while (!done) {
                String line = in.readLine();
                if (line == null)
                    done = true;
                else {
                    out.println("Echo: " + line);
                    if (line.trim().equals("ADDIO"))
                        done = true;
                }
            } // while
            incoming.close();
        } catch (Exception e) { System.err.println(e); }
    } // main
} // class
/*
Connessione ad un time-server.
*/

import java.io.*;
import java.net.*;
public class ConnessioneATimeServer {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("time.nist.gov", 13);
            BufferedReader lettore = new BufferedReader(new InputStreamReader(s.getInputStream()));
            boolean more = true;
            while (more){
                String line = lettore.readLine();
                if(line == null){
                    more = false;
                }
                else{
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
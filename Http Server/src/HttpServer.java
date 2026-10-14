/*
La classe DataOutputStream consente di
scrivere tipi primitivi Java su un flusso di output.
Successivamente i dati scritti su questo stream
possono essere letti usando un oggetto istanza
della classe DataInputStream. I tipi che possono
essere scritti includo:
byte, short, int, float, long, double, String.

Aggiungiamo alcuni campi nell’HEADER come la content-length e
il content-type (nel nostro caso impostato a text/html). Il tipo di
contenuto si stabilisce in accordo al MIME type (Multipurpose
Internet Mail Extensions), o tipo di supporto, che è un
identificatore formato da due parti per formati di file e contenuti
di formato trasmessi su Internet (es. text/html, application/pdf,
image/jpeg, text/csv). Separiamo l’header dal body con due
ritorni a capo aggiuntivi. r\n\r\n

*/

import java.io.*;
import java.net.*;
import java.util.*;
public class HttpServer {
    private static final int port = 3575;
    public static void main (String args[]) throws IOException {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("HTTP server running on port: "+port);
            while(true) {
                Socket client = server.accept();
                ThreadedServer cc = new ThreadedServer(client);
            }
        } catch(IOException e) {
            System.err.println(e);
        }
    }
}
class ThreadedServer extends Thread {
    private Socket client;
    private BufferedReader is;
    private DataOutputStream os;
    public ThreadedServer (Socket s) {
        client = s;
        try {
            is = new BufferedReader
                    (new InputStreamReader(client.getInputStream()));
            os = new DataOutputStream (client.getOutputStream());
        } catch (IOException e) {
            try {
                client.close();
            } catch (IOException ex) { System.err.println(""+ex); }
            return;
        }
        this.start();
    }
    public void run() {
        try { // get a request and parse it.
            String request = is.readLine();
            System.out.println("Request: "+request);
            StringTokenizer st = new StringTokenizer(request);
            if ((st.countTokens()>=2) && st.nextToken().equals("GET")) {
                if ((request = st.nextToken()).startsWith("/"))
                    request = request.substring( 1 );
                if (request.endsWith("/") || request.equals(""))
                    request = request + "index.html";
                // per impedire che si possa scrivere: http://hostname/../../../etc/passwd
                // oppure: http://hostname//etc/passwd
                if ((request.indexOf("..") != -1) || (request.startsWith("/"))) {
                    os.writeBytes("403 Forbidden. "+
                            "You do not have enough privileges to read: "+request+"\r\n");
                } else {
                    File f = new File(request); reply (os, f);
                }
            }
            else {
                os.writeBytes("400 Bad Request\r\n");
            }
            client.close();
        } catch (IOException e1) { System.err.println("I/O error: "+e1); }
        catch (Exception e2) { System.err.println("Exception: "+e2); }
    }
    public static void reply (DataOutputStream out, File f) throws Exception {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(f));
            int len = (int)f.length(); byte buf[] = new byte[len];
            in.readFully(buf);
            out.writeBytes("HTTP/1.0 200 OK\r\n");
            out.writeBytes("Content-Length: "+buf.length+"\r\n");
            out.writeBytes("Content-Type: text/html\r\n\r\n");
            out.write(buf); out.flush(); in.close();
        } catch (FileNotFoundException e) { out.writeBytes("404 Not Found\r\n"); }
    } } // class ThreadedServer
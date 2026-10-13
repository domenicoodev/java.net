import java.io.*;
import java.net.*;
public class ThreadedEchoServer {
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(8189);
            while (true) {
                Socket incoming = s.accept();
                new ThreadedEchoHandler(incoming).start();
            }
        } catch (Exception e) { System.out.println(e); }
    }
}
class ThreadedEchoHandler extends Thread {
    private Socket incoming;
    public ThreadedEchoHandler(Socket i) {
        incoming = i;
    }
    public void run() {
        try {
            BufferedReader in = new BufferedReader
                    (new InputStreamReader(incoming.getInputStream()));
            PrintWriter out = new PrintWriter (incoming.getOutputStream(),true);
            out.println( "Ciao!" );
            out.println( "Benvenuto sull'Echo server." );
            out.println( "Scrivi ADDIO per uscire." );
            boolean done = false;
            while (!done) {
                String str = in.readLine();
                if (str == null)
                    done = true;
                else {
                    out.println("Echo: "+str);
                    if (str.trim().equals("ADDIO"))
                        done = true;
                }
            }
            incoming.close();
        } catch (Exception e) { System.out.println(e); }
    }
}
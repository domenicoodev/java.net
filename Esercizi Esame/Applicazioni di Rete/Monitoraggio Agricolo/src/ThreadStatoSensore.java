import java.net.Socket;



public class ThreadStatoSensore extends Thread {

    private Socket socket;

    private Server server;



    public ThreadStatoSensore(Socket socket, Server server) {

        this.socket = socket;

        this.server = server;

    }



    public void run() {

        server.gestisciStato(socket);

    }

} 
import java.net.DatagramPacket;

import java.net.DatagramSocket;



public class ThreadRicezioneNotifiche extends Thread {

    public void run() {

        try {

            DatagramSocket socket = new DatagramSocket(Server.PORTA_UDP_CLIENT);

            while (true) {

                byte[] buffer = new byte[1024];

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                socket.receive(packet);

                String msg = new String(packet.getData(), 0, packet.getLength());

                System.out.println("[Notifica UDP] " + msg);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

} 
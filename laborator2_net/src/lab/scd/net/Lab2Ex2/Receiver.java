package lab.scd.net.Lab2Ex2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receiver extends Thread {
    int port;

    Receiver(int port) {
        this.port = port;
    }

    // transform bytes arrays received from network to characters
    private static String normalizeString(String string) {
        String result = new String();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) >= 32 && string.charAt(i) <= 126) {
                result += string.charAt(i);
            }
        }
        return result;
    }

    public void run() {
        String message = new String();
        try {
            // initialize socket
            DatagramSocket socket = new DatagramSocket(port);
            while (true) {
                // create buffer
                byte[] buffer = new byte[256];
                // set buffer content to package
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                // receive responses
                socket.receive(packet);
                // transform from byte array to string
                message = normalizeString(new String(packet.getData()));
                // display message
                System.out.println("Message received: " + message);
            }
        } catch (Exception e) {
            System.out.println("No message!");
            e.printStackTrace();
        }
    }
}

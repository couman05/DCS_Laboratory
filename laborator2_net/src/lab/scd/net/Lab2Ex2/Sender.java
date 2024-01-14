package lab.scd.net.Lab2Ex2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Sender extends Thread {
    int port;
    String ip;

    Sender(String ip, int port) {
        this.port = port;
        this.ip = ip;
    }

    public void run() {
        String message = new String();
        while (true) {
            try {
                // initialize socket
                DatagramSocket socket = new DatagramSocket();
                // create buffer
                byte[] buffer;
                InetAddress address = InetAddress.getByName(ip);
                // read message from keyboard
                Scanner in = new Scanner(System.in);
                message = in.nextLine();
                // translate string to buffer in bytes
                buffer = message.getBytes(StandardCharsets.UTF_8);
                System.out.println("Message <" + new String(buffer) + "> sent!");
                // specify the packet, the address and the port
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                // send message
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

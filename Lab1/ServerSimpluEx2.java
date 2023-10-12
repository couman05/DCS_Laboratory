// ServerSimplu.java
package lab.scd.net.socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSimpluEx2 {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = null;
        Socket s = null;

        try {
            ss = new ServerSocket(1900);
            System.out.println("Serverul asteapta conexiuni...");

            while (true) {
                s = ss.accept(); // Continuously accept new connections

                InetSocketAddress remoteadr = (InetSocketAddress) s.getRemoteSocketAddress();
                String remotehost = remoteadr.getHostName();
                int remoteport = remoteadr.getPort();

                System.out.println("Client nou conectat: " + remotehost + ":" + remoteport);

                // Create input and output streams
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

                String line = "";

                while (true) {
                    line = in.readLine();

                    if (line.equals("END")) {
                        System.out.println("Client disconnected.");
                        s.close();
                        break; // Exit the inner loop when "END" is received
                    }

                    // Read two numbers from the client
                    int x = Integer.parseInt(line);
                    int y = Integer.parseInt(in.readLine());

                    // Calculate the percentage and send it back to the client
                    double percentage = (double) y / x * 100;
                    out.println("Percentage: " + percentage);
                    out.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ss != null) ss.close();
        }
    }
}

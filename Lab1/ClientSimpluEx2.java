// ClientSimplu.java
package lab.scd.net.socket;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSimpluEx2 {

    public static void main(String[] args) throws Exception {
        Socket socket = null;

        try {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            socket = new Socket(serverAddress, 1900);

            // Create input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            for (int i = 0; i < 10; i++) {
                // Send two numbers to the server
                out.println(i); // x
                out.println(i + 1); // y
                out.flush();

                // Receive and print the server's response
                String response = in.readLine();
                System.out.println("Server response: " + response);
            }

            out.println("END"); // Send a message to terminate the connection

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null) socket.close();
        }
    }
}

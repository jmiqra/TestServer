import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

/**
 * A simple TCP server. When a client connects, it sends the client the current
 * datetime, then closes the connection. This is arguably the simplest server
 * you can write. Beware though that a client has to be completely served its
 * date before the server will be able to handle another client.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        try (var listener = new ServerSocket(8080)) {
            System.out.println("The date server is running...");
            while (true) {
                try (Socket socket = listener.accept()) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("yoo bro!!!");

                    Scanner in = new Scanner(socket.getInputStream());
                    String receivedMessage = in.nextLine();
                    /*System.out.println("Server response: " + receivedMessage);
                    out.println("here is your file you dummy!!!");*/




                    //send file
                    /*if(receivedMessage.equals("sendFile"))*/ {
                        System.out.println("inside if");
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        FileInputStream fis = new FileInputStream("cat.png");
                        byte[] buffer = new byte[40000];

                        while (fis.read(buffer) > 0) {
                            dos.write(buffer);
                        }

                        fis.close();
                        dos.close();
                    }
                }
            }
        }
    }
}
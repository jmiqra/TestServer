import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.net.Socket;
import java.io.IOException;

/**
 * A command line client for the date server. Requires the IP address of
 * the server as the sole argument. Exits after printing the response.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        /*if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }*/
        String IP = "192.168.1.3";
        int port = 8080;
        Socket socket = new Socket(IP, port);
        int filesize = 0;
        Scanner in = new Scanner(socket.getInputStream());
        System.out.println("Server response: " + in.nextLine());
        while (true) {



            Scanner systemInput = new Scanner(System.in);
            String request = systemInput.nextLine();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);



            out.println(request);
            filesize = in.nextInt();

            System.out.println("File size is " + filesize + " bytes");
            if(filesize == 0) {

                System.out.println("server said" + in.nextLine());
                continue;
            }
            System.out.println("done");


            //save file send by server
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            FileOutputStream fos = new FileOutputStream(request.split(" ")[1]);
            byte[] buffer = new byte[409600];

            //filesize = 1512300000; // make dynamic
            int read = 0;
            int totalRead = 0;
            int remaining = filesize;
            while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                System.out.println("read " + totalRead + " bytes.");
                fos.write(buffer, 0, read);
            }

            fos.close();
            dis.close();


        }

        /*String receivedMessage = in.nextLine();
        System.out.println("Server response: " + receivedMessage);*/

    }
}
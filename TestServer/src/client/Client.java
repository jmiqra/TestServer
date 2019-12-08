package client;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.net.Socket;
import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        String IP = "192.168.1.3";
        int port = 8080;
        Socket socket = new Socket(IP, port);
        int filesize = 0;
        //Scanner in = new Scanner(socket.getInputStream());
        while (true) {
            if(socket.isClosed()) {
                socket = new Socket(IP, port);
            }
            Scanner in = new Scanner(socket.getInputStream());
            System.out.println("Server response: " + in.nextLine());
            //socket = new Socket(IP, port);
            System.out.println("write commnd...");
            Scanner systemInput = new Scanner(System.in);
            String request = systemInput.nextLine();
            String fileName = request.split(" ")[1];
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(request);
            filesize = in.nextInt();
            System.out.println("File size is " + filesize + " bytes");
            if(filesize == 0) {
                System.out.println("server said" + in.nextLine());
                continue;
            }
            System.out.println("done");
            FileReceiveThread fileReceiveThread = new FileReceiveThread(socket, filesize, fileName);
            Thread fileReceiving = new Thread(fileReceiveThread);
            fileReceiving.start();
            fileReceiving.join();
        }
    }
}
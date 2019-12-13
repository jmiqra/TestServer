package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class RemoteServer {
    public static void main(String[] args) throws IOException {
        try (var listener = new ServerSocket(8082)) {
            System.out.println("The Remote server is running...");

            while (true) {
                System.out.println("Hello! from remote server");
                try (Socket socket = listener.accept()) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("You are connected to remote server...");

                    Scanner in = new Scanner(socket.getInputStream());
                    String receivedMessage = in.nextLine();
                    System.out.println("Edge server request: " + receivedMessage);
                    String[] rcv = receivedMessage.split(" ");

                    if(rcv[0].equals("fr")) {
                        File file = new File("remote/" + rcv[1]);

                        try {
                            System.out.println("sending file size from remote server");
                            out.println(file.length());
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            FileInputStream fis = new FileInputStream("remote/" + rcv[1]);
                            byte[] buffer = new byte[409600];

                            while (fis.read(buffer) > 0) {
                                dos.write(buffer);
                            }
                            fis.close();
                            dos.close();
                            System.out.println("sending done from remote server");
                        } catch (IOException e) {
                            out.println("File not found in remote");
                        }
                    }
                }
                System.out.println("File sharing done from remote server");
            }
        }
    }
}
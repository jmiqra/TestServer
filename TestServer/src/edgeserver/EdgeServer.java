package edgeserver;

import client.FileReceiveThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;


public class EdgeServer {

    public static void callRemoteServer(String fileName) throws IOException{
        String IP = "localhost";
        int port = 8082;
        Socket socket = new Socket(IP, port);
        int filesize = 0;
        Scanner in = new Scanner(socket.getInputStream());
        System.out.println("Remote Server response: " + in.nextLine());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println("fr " + fileName);
        filesize = in.nextInt();
        System.out.println("File size is " + filesize + " bytes");
        EdgeServerFileReceiveThread fileReceiveThread = new EdgeServerFileReceiveThread(socket, filesize, fileName);
        Thread fileReceiving = new Thread(fileReceiveThread);
        fileReceiving.start();
        try {
            fileReceiving.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("yyy");
    }

    public static void fetchFile( PrintWriter out, String fileName, Socket socket) {
        try {
            System.out.println("filename in fetch file " + fileName);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            FileInputStream fis = new FileInputStream(fileName);
            File file = new File(fileName);
            System.out.println("sending file size");
            out.println(file.length());
            /*DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            FileInputStream fis = new FileInputStream(fileName);*/
            byte[] buffer = new byte[409600];
            while (fis.read(buffer) > 0) {
                dos.write(buffer);
            }
            fis.close();
            dos.close();
            System.out.println("sending done");
        } catch (IOException e) {
            try {
                callRemoteServer(fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //out.println("File not found");
            fetchFile(out, fileName, socket);
        }
    }

    public static void main(String[] args) throws IOException {
        try (var listener = new ServerSocket(8080)) {
            System.out.println("The edge server is running...");
            while (true) {
                System.out.println("hello from while");
                try (Socket socket = listener.accept()) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("You are connected to server...");
                    Scanner in = new Scanner(socket.getInputStream());
                    String receivedMessage = in.nextLine();
                    System.out.println("client.Client request: " + receivedMessage);
                    String[] rcv = receivedMessage.split(" ");
                    if(rcv[0].equals("fr")) {
                        //baje recursion
                        fetchFile(out, rcv[1], socket);
                    }
                }
                System.out.println("task done");
            }
        }
    }
}
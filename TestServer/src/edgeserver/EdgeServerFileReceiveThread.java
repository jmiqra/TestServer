package edgeserver;

import client.Client;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class EdgeServerFileReceiveThread implements Runnable {
    Socket socket;
    int fileSize;
    String fileName;

    EdgeServerFileReceiveThread(Socket socket, int fileSize, String fileName) {
        this.socket = socket;
        this.fileSize = fileSize;
        this.fileName = fileName;
    }
    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            System.out.println("filename is " + fileName);
            FileOutputStream fos = new FileOutputStream(fileName);
            byte[] buffer = new byte[409600];
            int read = 0;
            int totalRead = 0;
            int remaining = fileSize;
            System.out.println("before while");
            while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                System.out.println("inside while");
                totalRead += read;
                remaining -= read;
                System.out.println("read " + totalRead + " bytes.");
                fos.write(buffer, 0, read);
            }
            System.out.println("after while");
            fos.close();
            dis.close();
            Thread.currentThread().interrupt();
            return;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
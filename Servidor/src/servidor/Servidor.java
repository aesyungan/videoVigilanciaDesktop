/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Alex
 */
public class Servidor {

    public final static int SOCKET_PORT = 8888;  // you may change this
    public final static String FILE_TO_RECEIVED = "E:\\img\\assasing12.png";  // you may change this
    public final static int FILE_SIZE = 6022386; // file size temporary hard coded

    public static void main(String[] args) throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ServerSocket servsock = null;
        Socket sock = null;
        try {
            servsock = new ServerSocket(SOCKET_PORT);
            while (true) {
                System.out.println("Waiting...");
                try {
                    sock = servsock.accept();
                    System.out.println("Accepted connection : " + sock);
                    // send file

                    // receive file
                    byte[] mybytearray = new byte[FILE_SIZE];
                    InputStream is = sock.getInputStream();
                    fos = new FileOutputStream(FILE_TO_RECEIVED);
                    bos = new BufferedOutputStream(fos);
                    bytesRead = is.read(mybytearray, 0, mybytearray.length);
                    current = bytesRead;

                    do {
                        bytesRead
                                = is.read(mybytearray, current, (mybytearray.length - current));
                        if (bytesRead >= 0) {
                            current += bytesRead;
                        }
                    } while (bytesRead > -1);

                    bos.write(mybytearray, 0, current);
                    bos.flush();
                    System.out.println("File " + FILE_TO_RECEIVED
                            + " downloaded (" + current + " bytes read)");
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                    if (sock != null) {
                        sock.close();
                    }
                }
            }
        } finally {
            if (servsock != null) {
                servsock.close();
            }
        }
    }

}

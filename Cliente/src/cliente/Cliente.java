/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author Alex
 */
public class Cliente {

    static {
        try {
            // System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");
            // System.setProperty("org.slf4j.simpleLogger.log.com.github.sarxos.webcam.ds.v4l4j", "trace");
             Webcam.setDriver(new V4l4jDriver());
        } catch (Exception e) {
            System.out.println("error ->" + e.getMessage());
        }

    }

    // public final static int SOCKET_PORT = 8888;      // you may change this
    // public final static String SERVER = "127.0.0.1";  // localhost
    // public final static String FILE_TO_SEND = "E:\\img\\assasing.png";  // you may change this, I give a
    public static void main(String[] args) throws IOException {
        int bytesRead;
        int current = 0;

        OutputStream os = null;

        Socket sock = null;
        try {

            System.out.println("Ingrese la ip del servidor Socket  ejemplo: 192.168.43.105 ");
            Scanner s = new Scanner(System.in);
            String host = s.next();
            System.out.println("Puerto del servidor socket Ejemplo 8888 ");
            Scanner s2 = new Scanner(System.in);
            int puerto = s2.nextInt();
            System.out.println("Connecting...");

            Webcam webcam = Webcam.getDefault();
            //webcam.setViewSize(new Dimension(176, 144));//windows
             webcam.setViewSize(new Dimension(160, 120));//raspberry
            webcam.open();
            while (true) {
                try {
                    current++;
                    sock = new Socket(host, puerto);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(webcam.getImage(), "jpg", baos);
                    byte[] mybytearray = baos.toByteArray();
                    System.out.println("Sending " + current + "(" + mybytearray.length + " bytes)");
                    os = sock.getOutputStream();
                    os.write(mybytearray, 0, mybytearray.length);
                    os.flush();
                    System.out.println("Done.");
                    if (os != null) {
                        os.close();
                    }
                    if (sock != null) {
                        sock.close();
                    }
                    Thread.sleep(30);
                } catch (Exception e) {
                    System.out.println("Error al capturar");
                }

            }

        } finally {
//
//            if (os != null) {
//                os.close();
//            }
//            if (sock != null) {
//                sock.close();
//            }
        }
    }

}

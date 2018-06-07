/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import sun.misc.IOUtils;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import javax.imageio.ImageIO;
import org.jcodec.api.awt.AWTSequenceEncoder;

/**
 *
 * @author Alex
 */
public class ServerThread implements Runnable {

    public final int SOCKET_PORT = 8888;  // you may change this
    // public final String FILE_TO_RECEIVED = "E:\\img\\assasing18.png";  // you may change this
    // public final int FILE_SIZE = 6022386; // file size temporary hard coded    
    public final int FILE_SIZE = 58087; // file size temporary hard coded    
    JLabel labenCapture = null;
//others
    int bytesRead;
    int current = 0;
    //FileOutputStream fos = null;
    //BufferedOutputStream bos = null;
    ServerSocket servsock = null;
    Socket sock = null;
    //video

//stop
    boolean stop = false;

    public ServerThread(JLabel labenCapture) {
        this.labenCapture = labenCapture;
    }

    @Override
    public void run() {
        try {

            try {
                servsock = new ServerSocket(SOCKET_PORT);
                while (true) {
                    System.out.println("Waiting...");
                    current++;
                    try {
                        sock = servsock.accept();
                        System.out.println("Accepted connection : " + sock);
                        // send file

                        // receive file
                        byte[] mybytearray = new byte[FILE_SIZE];
                        InputStream is = sock.getInputStream();
                        byte[] bytes = IOUtils.readFully(is, -1, false);
                        mostrarImagenDesktop(bytes);
                        crearVideo(bytes);
                        System.out.println("File "
                                + " downloaded (" + current + " bytes read)");
                    } finally {
                        /*
                        if (fos != null) {
                            fos.close();
                        }
                        if (bos != null) {
                            bos.close();
                        }*/
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error al iniciar el servidor socket",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void mostrarImagenDesktop(byte[] imageData) {
        Thread thread = new Thread() {
            public void run() {
                ImageIcon icon = new ImageIcon(imageData);
                int w = icon.getIconWidth() + icon.getIconWidth();
                int h = icon.getIconHeight() + icon.getIconHeight();
                labenCapture.setIcon(scaleImage(icon, w, h));
            }
        };
        thread.start();

    }

    public ImageIcon scaleImage(ImageIcon icon, int w, int h) {

        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    public void crearVideo(byte[] imageData) {
        Thread thread = new Thread() {
            public void run() {
                try {

                    if (Utils.recording) {
                        InputStream in = new ByteArrayInputStream(imageData);
                        BufferedImage bi = ImageIO.read(in);
                        Utils.enc.encodeImage(bi);
                        Utils.enc.encodeImage(bi);
                        System.out.println("se añadio  imagen" + current + " a la grabacion");
                    }

                } catch (Exception ex) {
                    System.out.println("Error al añadir la imgagen -> " + current + " a la grabacion ->" + ex.getMessage());
                }

            }
        };
        thread.start();
    }
}

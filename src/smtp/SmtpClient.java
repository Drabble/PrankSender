/**
 * Project : Prank Sender
 * File    : SmtpClient.java
 * Author  : Antoine Drabble & Guillaume Serneels
 * Date    : 16.04.2016
 */
package smtp;

import model.mail.Message;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * SMTP client for sending emails
 */
public class SmtpClient {
    DataOutputStream out;             // Socket writer
    BufferedReader in;                // Socket reader
    private String smtpServerAddress; // SMTP server address
    private int smtpServerPort;       // SMTP server port
    private Socket socket;            // Socket for the connection to the SMTP server

    /**
     * Constructs a SMTP client with the specified address and port
     *
     * @param smtpServerAddress
     * @param smtpServerPort
     */
    public SmtpClient(String smtpServerAddress, int smtpServerPort) {
        this.smtpServerAddress = smtpServerAddress;
        this.smtpServerPort = smtpServerPort;
    }

    /**
     * Send the list of messages to the SMTP server
     *
     * @param messages
     * @throws IOException
     */
    public void sendMessages(List<Message> messages) throws IOException {
        // Create and connect the socket
        socket = new Socket(smtpServerAddress, smtpServerPort);
        out = new DataOutputStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

        // Write EHLO command
        //System.out.println(in.readLine());
        out.writeBytes("EHLO pranker" + "\r\n");
        //System.out.println("EHLO pranker");
        String s;
        do {
            s = in.readLine();
            System.out.println(s);
        } while (!s.equals("250 Ok"));

        for (Message message : messages) {
            // Set mail sender
            out.writeBytes("MAIL FROM: " + message.getFrom() + "\r\n");
            System.out.println(in.readLine());

            // Set recipients
            for (String recipient : message.getTo()) {
                out.writeBytes("RCPT TO: " + recipient + "\r\n");
                System.out.println(in.readLine());
            }

            // Set email content
            out.writeBytes("DATA" + "\r\n");
            System.out.println(in.readLine());
            out.writeBytes("From: " + message.getFrom() + "\r\n");
            out.writeBytes("To: ");
            for (String recipient : message.getTo()) {
                out.writeBytes(recipient + "," );
            }
            out.writeBytes("\r\n");
            out.writeBytes(message.getContent()+ "\r\n");
            out.writeBytes("." + "\r\n");
            System.out.println(in.readLine());
        }

        // Quit smtp server
        out.writeBytes("quit"+ "\r\n");
        System.out.println(in.readLine());

        // Close socket
        out.close();
        in.close();
        socket.close();
    }
}

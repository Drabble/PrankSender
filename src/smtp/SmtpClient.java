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
    PrintWriter out;                  // Socket writer
    BufferedReader in;                // Socket reader
    private String smtpServerAddress; // SMTP server address
    private int smtpServerPort = 25;  // SMTP server port
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
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

        // Write EHLO command
        out.println("EHLO pranker");
        String s;
        do {
            s = in.readLine();
            System.out.println(s);
        } while (!s.equals("250 Ok"));

        for (Message message : messages) {
            // Set mail sender
            out.println("MAIL FROM: antoine.drabble@gmail.com");
            System.out.println(in.readLine());

            // Set recipients
            for (String recipient : message.getTo()) {
                out.println("RCPT TO: " + recipient);
                System.out.println(in.readLine());
            }

            // Set email content
            out.println("DATA");
            System.out.println(in.readLine());
            out.println("From: " + message.getFrom());
            out.print("To: ");
            for (String recipient : message.getTo()) {
                out.print(recipient + ",");
            }
            out.print("\n");
            out.print(message.getContent());
            out.println(".");
            System.out.println(in.readLine());
        }

        // Quit smtp server
        out.println("quit");
        System.out.println(in.readLine());

        // Close socket
        out.close();
        in.close();
        socket.close();
    }
}

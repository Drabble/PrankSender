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
    private int smtpServerPort;       // SMTP server port
    private Socket socket;            // Socket for the connection to the SMTP server

    //Constant for printing the appropriate End of line sympbol when writing
    public final static String CRLF = "\r\n";

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
        System.out.println(in.readLine());
        out.printf("EHLO pranker");
        out.printf(CRLF);
        String s;
        do {
            s = in.readLine();
            System.out.println(s);
        } while (!s.startsWith("250 "));

        for (Message message : messages) {
            // Set mail sender
            out.printf("MAIL FROM: %s", message.getFrom());
            out.printf(CRLF);
            System.out.println(in.readLine());

            // Set recipients
            for (String recipient : message.getTo()) {
                out.printf("RCPT TO: ", recipient);
                out.printf(CRLF);
                System.out.println(in.readLine());
            }

            // Set email content
            out.printf("DATA");
            out.printf(CRLF);
            System.out.println(in.readLine());
            out.printf("From: " + message.getFrom());
            out.printf(CRLF);
            out.printf("To: ");
            for (String recipient : message.getTo()) {
                out.printf("%s,", recipient );
            }
            out.printf(CRLF);
            out.printf(message.getContent());
            out.printf(CRLF);
            out.printf(".");
            out.printf(CRLF);
            System.out.println(in.readLine());
        }

        // Quit smtp server
        out.printf("quit");
        out.printf(CRLF);
        System.out.println(in.readLine());

        // Close socket
        out.close();
        in.close();
        socket.close();
    }
}

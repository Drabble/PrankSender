/**
 * Project : Prank Sender
 * File    : Message.java
 * Author  : Antoine Drabble & Guillaume Serneels
 * Date    : 16.04.2016
 */
package model.mail;

/**
 * Defines a message :
 * - The sender
 * - The recipients
 * - The content of the message
 */
public class Message {

    private String from;    // Sender
    private String[] to;    // Recipients
    private String content; // Message content

    /**
     * Constructs a message with the specified parameters
     *
     * @param from
     * @param to
     * @param content
     */
    public Message(String from, String[] to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    /**
     * @return the sender
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the recipients
     */
    public String[] getTo() {
        return to;
    }

    /**
     * @param to
     */
    public void setTo(String[] to) {
        this.to = to;
    }

    /**
     * @return the content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}

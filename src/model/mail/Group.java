/**
 * Project : Prank Sender
 * File    : Group.java
 * Author  : Antoine Drabble & Guillaume Serneels
 * Date    : 16.04.2016
 */
package model.mail;

/**
 * Defines a group with a list of emails
 */
public class Group {
    private String[] emails; // List of emails

    /**
     * Constructs a message with the specified parameters
     *
     * @param emails
     */
    public Group(String[] emails) {
        this.emails = emails;
    }

    /**
     * @return the emails addresses
     */
    public String[] getEmails() {
        return emails;
    }

    /**
     * @param emails
     */
    public void setEmails(String[] emails) {
        this.emails = emails;
    }
}

/**
 * Project : Prank Sender
 * File    : ConfigurationManager.java
 * Author  : Antoine Drabble & Guillaume Serneels
 * Date    : 16.04.2016
 */
package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Loads configuration
 * - The list of vitcims
 * - The message templates
 * - The smtp server informations
 * - The number of groups
 */
public class ConfigurationManager {

    private final List<String> victims;  // List of victims
    private final List<String> messages; // List of messages
    private String smtpServerAddress;    // Smtp server address
    private int smtpServerPort;          // Smtp server port
    private int numberOfGroups;          // Number of groups

    /**
     * Constructs the configuration manager
     *
     * @throws IOException
     */
    public ConfigurationManager() throws IOException {
        loadProperties("config/config.properties");
        victims = loadAddressesFromFile("config/victims.utf8");
        messages = loadMessagesFromFile("config/messages.utf8");
    }

    /**
     * Load the properties from the properties file
     *
     * @param fileName
     * @throws IOException
     */
    private void loadProperties(String fileName) throws IOException {
        // Load properties
        FileInputStream propertiesFileInputStream = new FileInputStream(fileName);
        Properties properties = new Properties();
        properties.load(propertiesFileInputStream);

        // Save properties
        this.smtpServerAddress = properties.getProperty("smtpServerAddress");
        this.smtpServerPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
        this.numberOfGroups = Integer.parseInt(properties.getProperty("numberOfGroups"));
    }

    /**
     * Read the email addresse from the configuration file
     *
     * @param fileName
     * @return the list of email addresses from the configuration file
     * @throws IOException
     */
    private List<String> loadAddressesFromFile(String fileName) throws IOException {
        List<String> result = new ArrayList<>();

        // Add every line of the file to the victim list
        try (Stream<String> lines = Files.lines(Paths.get(fileName), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> {
                result.add(line);
            });
        }
        return result;
    }

    /**
     * Load the email templates from the configuration file
     *
     * @param fileName
     * @return the list of message from the configuration file
     * @throws IOException
     */
    private List<String> loadMessagesFromFile(String fileName) throws IOException {
        List<String> result = new ArrayList<>();
        StringBuilder content = new StringBuilder();

        // Read each line of the file and retrieve the messages separated by "=="
        try (Stream<String> lines = Files.lines(Paths.get(fileName), Charset.defaultCharset())) {
            for (String line : lines.collect(Collectors.toList())) {
                // If it's the end of the message add it to the list
                if (line.equals("==")) {
                    result.add(content.toString());
                    content = new StringBuilder();
                // Else append the line
                } else {
                    content.append(line);
                    content.append("\r\n");
                }
            }
        }
        return result;
    }

    /**
     * @return the smtp server address
     */
    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    /**
     * @param smtpServerAddress
     */
    public void setSmtpServerAddress(String smtpServerAddress) {
        this.smtpServerAddress = smtpServerAddress;
    }

    /**
     * @return the smtp server port
     */
    public int getSmtpServerPort() {
        return smtpServerPort;
    }

    /**
     * @param smtpServerPort
     */
    public void setSmtpServerPort(int smtpServerPort) {
        this.smtpServerPort = smtpServerPort;
    }

    /**
     * @return the list of victims
     */
    public List<String> getVictims() {
        return victims;
    }

    /**
     * @return the list of messages
     */
    public List<String> getMessages() {
        return messages;
    }

    /**
     * @return the number of groups
     */
    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    /**
     * @param numberOfGroups
     */
    public void setNumberOfGroups(int numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }
}

/**
 * Project : Prank Sender
 * File    : PrankGenerator.java
 * Author  : Antoine Drabble & Guillaume Serneels
 * Date    : 16.04.2016
 */
package prank;

import config.ConfigurationManager;
import model.mail.Group;
import model.mail.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Generate the pranks using the configuration
 */
public class PrankGenerator {

    ConfigurationManager config; // Configuration

    /**
     * Constructs a PrankGenerator with the specified configuration
     *
     * @param config
     */
    public PrankGenerator(ConfigurationManager config) {
        this.config = config;
    }

    /**
     * Generate one message for each group
     *
     * @return the list of messages
     */
    public List<Message> generateMessages() throws IllegalArgumentException {
        List<Message> messages = new ArrayList<>();
        List<Group> groups = generateGroups();

        // Check if there are message templates in the configuration file
        if (config.getMessages().size() == 0) {
            throw new IllegalArgumentException("There is no message template in the configuration file");
        }

        // For each group create a message
        for (Group group : groups) {
            int randomMessage = (int) (Math.random() * (config.getMessages().size()));
            messages.add(new Message(group.getEmails()[0],
                    Arrays.copyOfRange(group.getEmails(), 1, group.getEmails().length),
                    config.getMessages().get(randomMessage)));
        }

        return messages;
    }

    /**
     * Generate the groups using the configuration properties
     *
     * @return
     * @throws IllegalArgumentException
     */
    public List<Group> generateGroups() throws IllegalArgumentException {
        List<Group> groups = new ArrayList<>();

        // Check if there are enough emails for the number of groups
        if (config.getNumberOfGroups() * 3 > config.getVictims().size()) {
            throw new IllegalArgumentException("There isn't enough emails for this number of groups. The ratio must be 3:1");
        }

        // Get victims and shuffle their positions
        List<String> victims = config.getVictims();
        Collections.shuffle(victims);

        // Select how many emails will be in each group
        int numberOfEmailPerGroup = victims.size() / config.getNumberOfGroups();
        int numberOfGroupsWithOneMoreEmail = victims.size() % config.getNumberOfGroups();

        // Generate the groups
        int emailPosition = 0;
        for (int i = 0; i < config.getNumberOfGroups(); i++) {
            String[] emails = new String[numberOfEmailPerGroup + (i < numberOfGroupsWithOneMoreEmail ? 1 : 0)];
            for (int j = 0; j < numberOfEmailPerGroup + (i < numberOfGroupsWithOneMoreEmail ? 1 : 0); j++) {
                emails[j] = victims.get(emailPosition++);
            }
            groups.add(new Group(emails));
        }

        return groups;
    }
}

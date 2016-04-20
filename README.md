# PrankSender

## Introduction

PrankSender allows you to send prank emails to groups of people with template messages. You define a SMTP server, a list of message template, a list of emails and the number of groups, then it will generate the groups randomly with the list of emails and select a random template message for each group. The messages will be sent with one email as the sender and the others will be the recipients.

## Project

There are 3 configuration files inside the config folder.

* messages.utf8
* victims.utf8
* config.properties

messages.utf8 contains the list of email templates that will be randomly selected and sent for the pranks. The emails must be separated by a line containing the string "==".

victims.utf8 contains the list of email adresses used for generating the groups. The email addresses must be separated by a new line separator.

Inside config.properties you must define the SMTP server address, the SMTP server port and the number of groups you want to generate. There must be at least 3 times more emails than the number of groups.

## Implementation

![Class diagram](https://github.com/servietsky777/PrankSender/raw/master/figures/ClassDiagram.jpg)

Here is a description of each class of the diagram

* ConfigurationManager

	It loads the configuration from the 3 config files

* Group

	Model for a group. Contains the list of emails.

* Message

	Model for a message. Contains the list of recipients, the sender and the content of the message.

* PrankGenerator

	It uses the three classes above to split the email adresses between each group, determine the unique sender and all the recipients  and finally create each message with the randomly chosed content.

* SmtpClient

	It creates the connection to the SMTP server and allows email sending.

* PrankSender 

	It contains the main method. It sends the prank emails.

Here is a the content of a discussion with the SMTP server of HEIG-VD :

![Discussion 1](https://github.com/servietsky777/PrankSender/raw/master/figures/chat1.png)
![Discussion 2](https://github.com/servietsky777/PrankSender/raw/master/figures/chat2.png)
![Discussion 3](https://github.com/servietsky777/PrankSender/raw/master/figures/chat3.png)


## Installing the Mock server

In order to experiment with this tool, it is a good idea to use a mock SMTP server to avoid sending pranks to actual mail adresses. During the developpement we have used the MockMock, a mock SMTP server built with Java. It allows you to test if outgoing emails are sent (without actually sending them) and to see what they look like through a browser interface.

In order to get the server running you need to:
 
* Clone the MockMock repo (https://github.com/tweakers-dev/MockMock) inside yout machine.

* Build the software, if you are using Maven (https://maven.apache.org/) just like us, running the command 	'mvn clean install' from the MockMock folder will do it. This operation might take a few minutes the                                            first time it's executed, since all the required librairies and files will have to be downloaded.

* Start the server, to achieve this we are using the "MockMock-1.4.0.one-jar.jar" java application resulting from the build. Simply go to the target folder in your MockMock folder and run the command:

		java -jar MockMock-1.4.0.one-jar.jar -p 2525 -h 8080

	The first parameter (after the -p) represent the SMTP port that will be used. Note that the default SMTP 		port for SMTP is 25, but listening to this port is usually forbiden on most sytems so we are using port 	2525 instead.
	
	The second parameter (after the -h) represent the http port you will need to view the graphic interface 		of MockMock and check the mails that have been sent.
	
* Once your server is up and running, you can run our application or you can simply connect to it using telnet with the command:
		telnet localhost 2525


-- To access the graphical interface and check the mails received by the MockMock server, simply open a web 		browser and go to:

	localhost:8080


Guillaume Serneels & Antoine Drabble
Labo SMTP		Guillaume Serneels & Antoine Drabble

// Consignes pour le rapport //
A brief description of your project: if people exploring GitHub find your repo, without a prior knowledge of the RES course, they should be able to understand what your repo is all about and whether they should look at it more closely.

Clear and simple instructions for configuring your tool and running a prank campaign. If you do a good job, an external user should be able to clone your repo, edit a couple of files and send a batch of e-mails in less than 10 minutes.

A concise description of your implementation: document the key aspects of your code. It is probably a good idea to start with a class diagram. Decide which classes you want to show (focus on the important ones) and describe their responsibilities in text. It is also certainly a good idea to include examples of dialogues between your client and a SMTP server (maybe you also want to include some screenshots here).

Instructions for installing and using a mock SMTP server. The user who wants to experiment with your tool but does not really want to send pranks immediately should be able to use a mock SMTP server. For people who are not familiar with this concept, explain it to them in simple terms. Explain which mock server you have used and how you have set it up.

----- RAPPORT -----

PROJECT

CONFIGURATION

IMPLEMENTATION

INSTALLING THE MOCK SERVER

In order to experiment with this tool, it is a good idea to use a mock SMTP server to avoid sending pranks to actual mail adresses. During the developpement we have used the MockMock, a mock SMTP server built with Java. It allows you to test if outgoing emails are sent (without actually sending them) and to see what they look like through a browser interface.

In order to get the server running you need to:
 
-- Clone the MockMock repo (https://github.com/tweakers-dev/MockMock) inside yout machine.
-- Build the software, if you are using Vagrant (https://www.vagrantup.com/) just like us, running the command 		'mvn clean install' from the MockMock folder will do it. This operation might take a few minutes the first 		time it's executed, since all the required librairies and files will have to be downloaded.
-- Start the server, to achieve this we are using the "MockMock-1.4.0.one-jar.jar" java application resulting 		from the build. Simply go to the target folder in your MockMock folder and run the command:

		 	java -jar MockMock-1.4.0.one-jar.jar -p 2525 -h 8080

	- The first parameter (after the -p) represent the SMTP port that will be used. Note that the default SMTP 		port for SMTP is 25, but listening to this port is usually forbiden on most sytems so we are using port 	2525 instead.
	- The second parameter (after the -h) represent the http port you will need to view the graphic interface 		of MockMock and check the mails that have been sent.
-- Once your server is up and running, you can run our application or you can simply connect to it using telnet 		with the command:

			telnet localhost 2525

-- To access the graphical interface and check the mails received by the MockMock server, simply open a web 		browser and go to:

			localhost:8080



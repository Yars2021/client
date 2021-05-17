package ru.itmo.p3114.s312198.authorization;

import ru.itmo.p3114.s312198.client_command.ClientCommandLineProcessor;
import ru.itmo.p3114.s312198.util.ConsoleReader;

import java.net.Socket;
import java.net.SocketException;

public class Authorizer {
    private final Socket clientSocket;

    public Authorizer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public UserSignature authorize() throws SocketException {
        UserSignatureSender signatureSender = new UserSignatureSender(clientSocket);
        FeedbackReceiver feedbackReceiver = new FeedbackReceiver(clientSocket);
        ClientCommandLineProcessor commandLineProcessor = new ClientCommandLineProcessor();
        AuthorizationFeedback authorizationFeedback;
        UserSignature userSignature = null;
        boolean authorized = false;

        System.out.println("Use \"login\" to log in or \"register\" to create an account");
        while (!authorized) {
            System.out.print("> ");
            userSignature = new UserSignature(commandLineProcessor.parseUserInput(ConsoleReader.readLine()).execute());
            signatureSender.send(userSignature);
            authorizationFeedback = feedbackReceiver.receive();
            authorized = authorizationFeedback.isAllowed();
            System.out.println(authorizationFeedback.getServerMessage());
        }

        return userSignature;
    }
}

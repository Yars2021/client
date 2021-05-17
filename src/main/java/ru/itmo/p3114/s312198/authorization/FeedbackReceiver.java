package ru.itmo.p3114.s312198.authorization;

import ru.itmo.p3114.s312198.util.CommandOutput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class FeedbackReceiver {
    private ObjectInputStream reader;

    public FeedbackReceiver(Socket clientSocket) {
        try {
            reader = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public AuthorizationFeedback receive() {
        if (reader == null) {
            System.out.println("Unable to read");
            return null;
        } else {
            try {
                return (AuthorizationFeedback) reader.readObject();
            } catch (IOException ioe) {
                return null;
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
                return null;
            }
        }
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException ignored) {
        }
    }
}

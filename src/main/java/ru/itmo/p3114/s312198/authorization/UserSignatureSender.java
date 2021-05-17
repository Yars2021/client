package ru.itmo.p3114.s312198.authorization;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class UserSignatureSender {
    private ObjectOutputStream outputStream;

    public UserSignatureSender(Socket socket) {
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ioe) {
            outputStream = null;
        }
    }

    public void send(UserSignature userSignature) throws SocketException {
        if (outputStream == null) {
            System.out.println("Unable to send user signature");
        } else {
            if (userSignature != null) {
                try {
                    outputStream.writeObject(userSignature);
                    outputStream.flush();
                } catch (IOException ioe) {
                    throw new SocketException("Lost connection to the server");
                }
            }
        }
    }
}

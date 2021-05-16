package ru.itmo.p3114.s312198.server_interaction;

import ru.itmo.p3114.s312198.util.command.actions.AbstractCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientCommandWriter {
    private ObjectOutputStream writer;

    public ClientCommandWriter(Socket clientSocket) {
        try {
            writer = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException ioe) {
            // todo
            ioe.printStackTrace();
        }
    }

    public void send(AbstractCommand command) throws SocketException {
        if (writer == null) {
            System.out.println("Unable to write");
        } else {
            if (command != null) {
                try {
                    writer.writeObject(command);
                    writer.flush();
                } catch (IOException ioe) {
                    throw new SocketException("Lost connection to the server");
                }
            }
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException ignored) {
        }
    }
}

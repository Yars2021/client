package ru.itmo.p3114.s312198;

import ru.itmo.p3114.s312198.util.command.actions.AbstractCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    public void send(AbstractCommand command) {
        if (writer == null) {
            System.out.println("Unable to write");
        } else {
            if (command != null) {
                try {
                    writer.writeObject(command);
                    writer.flush();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

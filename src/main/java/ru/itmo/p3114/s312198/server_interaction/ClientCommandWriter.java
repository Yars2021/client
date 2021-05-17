package ru.itmo.p3114.s312198.server_interaction;

import ru.itmo.p3114.s312198.util.DataPacket;

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
            ioe.printStackTrace();
        }
    }

    public void send(DataPacket dataPacket) throws SocketException {
        if (writer == null) {
            System.out.println("Unable to write");
        } else {
            if (dataPacket != null) {
                try {
                    writer.writeObject(dataPacket);
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

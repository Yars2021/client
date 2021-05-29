package ru.itmo.p3114.s312198.transmission;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CSChannel implements AutoCloseable {
    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    public CSChannel(Socket socket) throws IOException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void write(Object pack) throws IOException {
        objectOutputStream.writeObject(pack);
    }

    public Object read() throws IOException, ClassNotFoundException {
        return objectInputStream.readObject();
    }

    @Override
    public void close() throws IOException {
        socket.shutdownInput();
        socket.shutdownOutput();
        objectInputStream.close();
        objectOutputStream.close();
        socket.close();
    }
}

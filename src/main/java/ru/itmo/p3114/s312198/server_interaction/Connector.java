package ru.itmo.p3114.s312198.server_interaction;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connector {
    public Socket requestConnection(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            socket.setReuseAddress(true);
            return socket;
        } catch (UnknownHostException uhe) {
            System.out.println("Unknown host: " + host);
            return null;
        } catch (IOException ioe) {
            System.out.println("Server might be offline");
            return null;
        }
    }
}

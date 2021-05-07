package ru.itmo.p3114.s312198;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connector {
    Socket requestConnection(String host, int port) {
        try {
            return new Socket("", port);
        } catch (UnknownHostException uhe) {
            System.out.println("Unknown host: " + host);
            return null;
        } catch (IOException ioe) {
            System.out.println("Unable to connect");
            return null;
        }
    }
}

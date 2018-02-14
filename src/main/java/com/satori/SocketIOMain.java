package com.satori;


import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class SocketIOMain {

    public static void main(String[] args) throws URISyntaxException {

        final IO.Options options = configureConnectionOptions();

        final Socket socket = IO.socket(args[0], options);
        configureSocketListeners(socket);
        try {
            socket.connect();
        } finally {
            socket.disconnect();
        }

    }

    private static void configureSocketListeners(Socket socket) {
        socket.on(Socket.EVENT_CONNECT, e -> {
            System.out.println("Socket IO client successfully connected...");
        });

        socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
            System.out.println("Socket IO connection error... ");
        });

        socket.on(Socket.EVENT_RECONNECT, e -> {
            System.out.println("Socket IO client successfully reconnected...");
        });


        socket.on(Socket.EVENT_MESSAGE, args -> {
            for (Object arg : args) {
                System.out.println(arg);
            }
        });
    }


    private static IO.Options configureConnectionOptions() {
        final IO.Options options = new IO.Options();
        options.upgrade = true;
        options.forceNew = false;
        options.transports = new String[]{"websocket"};
        options.reconnection = true;
        options.reconnectionAttempts = 3;
        options.reconnectionDelay = 5000;
        return options;
    }
}

package ru.cft.focusstart.common;

import ru.cft.focusstart.common.message.*;

import java.io.*;
import java.net.Socket;

public class Connection {
    private Socket socket;
    ConnectionListener listener;
    private BufferedReader in;
    private BufferedWriter out;
    private Thread readerThread;
    public boolean onConnection = false;

    public Connection(ConnectionListener listener, String ipAddress, int port) throws IOException {
        this(listener, new Socket(ipAddress, port));
    }

    public Connection(ConnectionListener listener, Socket socket) throws IOException {
        this.listener = listener;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        readerThread = new Thread(() -> {
            try {
                listener.connectIsReady(this);
                while (!onConnection) {
                    listener.checkUserName(this);
                }
                while (!readerThread.isInterrupted()) {
                    listener.receiveMessage(this);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                listener.disconnect(this);
            }
        });
        readerThread.start();
    }

    public synchronized void sendMessage(String message) {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException e) {
            try {
                listener.getException(Connection.this);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized Message getMessage() throws IOException {
        Message message = ConverterMessage.toObjectMessage(in.readLine());
        return message;
    }

    public void close() {
        readerThread.interrupt();
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public BufferedReader getIn() {
        return in;
    }
}


package ru.AlexZmeu.java.chat.client.server;

public class ServerApplication {
    public static void main(String[] args) {
        Server server = new Server(8189);
        server.start();
    }
}

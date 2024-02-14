package ru.AlexZmeu.java.chat.client.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private List<ClientHandler> clients;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.printf("Сервер запущен на порту %d. Ожидание подключения клиентов\n", port);
            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    subscribe(new ClientHandler(this, socket));
                } catch (IOException e) {
                    System.out.println("Не удалось подключить клиента");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(message);
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        System.out.println("Подключился новый клиент " + clientHandler.getUserName());
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println("Отключился клиент " + clientHandler.getUserName());
    }

    public synchronized void sendPrivateMessage(ClientHandler sender, String receiverUsername, String message) {
        ClientHandler recClient = clients.stream().filter(client -> receiverUsername.equals(client.getUserName())).findFirst().orElse(null);
        if (recClient == null) {
            sender.sendMessage("Получатель " + receiverUsername + " не найден");
            return;
        }
        recClient.sendMessage("От " + sender.getUserName() + " личное сообщение: " + message);
        sender.sendMessage("Личное сообщение для " + receiverUsername + ": " + message);
    }
}

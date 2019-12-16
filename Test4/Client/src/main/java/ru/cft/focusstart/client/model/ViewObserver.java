package ru.cft.focusstart.client.model;

public interface ViewObserver {
    String onConnect(String ip, int port);
    void onDisconnect();
    void sendUserName(String userName);
    void sendMessage(String message);
}

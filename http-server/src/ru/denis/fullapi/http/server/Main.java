package ru.denis.fullapi.http.server;

public class Main {
    public static void main(String[] args) {
        new Server(
                (req, resp) -> "<html><body><h1>Hello</h1></body></html>"
        ).bootstrap();
    }
}

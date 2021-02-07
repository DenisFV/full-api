package ru.denis.fullapi.http.server;

public interface HttpHandler {
    String handle(HttpRequest request, HttpResponse response);
}

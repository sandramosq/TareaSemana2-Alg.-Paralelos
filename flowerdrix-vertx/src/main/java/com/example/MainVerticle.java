package com.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {
    public static void main(String[] args) {
        // Crea una instancia de Vert.x
        Vertx vertx = Vertx.vertx();
        // Despliega el verticle que maneja el servidor HTTP
        vertx.deployVerticle(new MainVerticle());
    }

    @Override
    public void start() {
        // Crea un servidor HTTP que responde con "Hello from Vert.x!"
        vertx.createHttpServer().requestHandler(req -> {
            req.response()
                .putHeader("content-type", "text/plain")
                .end("Hello from Vert.x!");
        }).listen(9090); // El servidor escucha en el puerto 8080
    }
}


package com.distribuida;

import com.distribuida.services.ServicioBook;
import io.helidon.webserver.WebServer;
import jakarta.enterprise.inject.spi.CDI;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;

public class Main {

    private static ContainerLifecycle lifecycle = null;

    public static void main(String[] args) {
        lifecycle = WebBeansContext.currentInstance().getService(ContainerLifecycle.class);
        lifecycle.startApplication(null);

        ServicioBook servicio = CDI.current().select(ServicioBook.class).get();

        WebServer server = WebServer.builder()
                .port(8080)
                .routing(builder -> builder
                        .get("/books", (req, res) ->BookServer.obtenerBooks(req, res, servicio))
                        .get("/books/{id}",(req, res) ->  BookServer.obtenerBooksporID(req,res, servicio))
                        .post("/books",(req, res) ->  BookServer.ingresarBook(req,res, servicio))
                        .put("/books/{id}",(req, res) ->  BookServer.actualizarBook(req,res, servicio))
                        .delete("/books/{id}", (req, res) -> BookServer.eliminarBook(req,res, servicio))
                )
                .build();

        server.start();
}
}

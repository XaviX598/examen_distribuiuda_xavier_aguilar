package com.distribuida;

import com.distribuida.db.Book;
import com.distribuida.services.ServicioBook;
import com.google.gson.Gson;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;

import java.util.List;

public class BookServer {

    public static void obtenerBooks(ServerRequest req, ServerResponse res, ServicioBook servicio) {

        List<Book> books = servicio.findAll();
        res.send(new Gson().toJson(books));
    }

    public static void obtenerBooksporID(ServerRequest req, ServerResponse res, ServicioBook servicio) {
        int id = Integer.parseInt(req.path().pathParameters().get("id"));
        Book book = servicio.findById(id);
        if (book == null) {
            res.status(404).send("Libro no encontrado");
        } else {
            res.send(new Gson().toJson(book));
        }
    }

    public static void ingresarBook(ServerRequest req, ServerResponse res, ServicioBook servicio) {
        Gson gson = new Gson();
        String body  = req.content().as(String.class);
        Book book = gson.fromJson(body, Book.class);
        servicio.insert(book);
        res.send("Se ha insertado el libro");
    }

    public static void actualizarBook(ServerRequest req, ServerResponse res, ServicioBook servicio) {
        Gson gson = new Gson();
        String body  = req.content().as(String.class);
        Book book = gson.fromJson(body, Book.class);
        Integer id = Integer.valueOf(req.path().pathParameters().get("id"));
        book.setId(id);
        servicio.update(book);
        res.send("Se ha actualizado el libro con id " + id);
    }

    public static void eliminarBook(ServerRequest req, ServerResponse res, ServicioBook servicio) {
        Integer id = Integer.valueOf(req.path().pathParameters().get("id"));
        servicio.delete(id);
        res.send("Se ha eliminado el libro con id " + id);
    }

}

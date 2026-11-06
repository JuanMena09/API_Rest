package com.Mena.ClienteAPIProductos.cliente;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.Mena.ClienteAPIProductos.dto.ProductoDTO;

public class ProductoCliente {

    private static final String URL =
            "http://localhost:8080/api/productos";

    private final HttpClient cliente;

    public ProductoCliente() {
        cliente = HttpClient.newHttpClient();
    }
    public String registrarProducto(ProductoDTO producto) throws Exception {

        String json = "{"
                + "\"codigo\":\"" + producto.getCodigo() + "\","
                + "\"nombre\":\"" + producto.getNombre() + "\","
                + "\"descripcion\":\"" + producto.getDescripcion() + "\","
                + "\"precio\":" + producto.getPrecio()
                + "}";

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> respuesta = cliente.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        return respuesta.body();
    }

    public String consultarProducto(String codigo) throws Exception {

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL + "/" + codigo))
                .GET()
                .build();

        HttpResponse<String> respuesta = cliente.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        return respuesta.body();
    }

    public String listarProductos() throws Exception {

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET()
                .build();

        HttpResponse<String> respuesta = cliente.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        return respuesta.body();
    }

    public String eliminarProducto(String codigo) throws Exception {

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL + "/" + codigo))
                .DELETE()
                .build();

        HttpResponse<String> respuesta = cliente.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        return "Código de respuesta: " + respuesta.statusCode();
    }

    public String actualizarProducto(String codigo, ProductoDTO producto)
            throws Exception {

        String json = "{"
                + "\"codigo\":\"" + producto.getCodigo() + "\","
                + "\"nombre\":\"" + producto.getNombre() + "\","
                + "\"descripcion\":\"" + producto.getDescripcion() + "\","
                + "\"precio\":" + producto.getPrecio()
                + "}";

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(URL + "/" + codigo))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> respuesta = cliente.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        return respuesta.body();
    }

}
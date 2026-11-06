package com.Mena.API.Rest.controller;

import com.Mena.API.Rest.entity.Producto;
import com.Mena.API.Rest.service.ProductoService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Registrar producto
    @PostMapping
    public Producto guardar(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    // Consultar producto por codigo
    @GetMapping("/{codigo}")
    public Producto buscar(@PathVariable String codigo) {
        return productoService.buscarProducto(codigo).orElse(null);
    }

    // Listar todos los productos
    @GetMapping
    public List<Producto> listar() {
        return productoService.listarProductos();
    }

    // Actualizar producto
    @PutMapping("/{codigo}")
    public Producto actualizar(
            @PathVariable String codigo,
            @RequestBody Producto producto) {
        return productoService.actualizarProducto(codigo, producto);
    }

    // Eliminar producto
    @DeleteMapping("/{codigo}")
    public void eliminar(@PathVariable String codigo) {
        productoService.eliminarProducto(codigo);
    }
}
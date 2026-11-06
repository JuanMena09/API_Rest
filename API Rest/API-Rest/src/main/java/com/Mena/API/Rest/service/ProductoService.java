package com.Mena.API.Rest.service;

import com.Mena.API.Rest.entity.Producto;
import com.Mena.API.Rest.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> buscarProducto(String codigo) {
        return productoRepository.findById(codigo);
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto actualizarProducto(String codigo, Producto producto) {
        producto.setCodigo(codigo);
        return productoRepository.save(producto);
    }

    public void eliminarProducto(String codigo) {
        productoRepository.deleteById(codigo);
    }
}
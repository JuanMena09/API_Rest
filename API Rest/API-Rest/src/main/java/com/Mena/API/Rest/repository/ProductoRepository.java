package com.Mena.API.Rest.repository;

import com.Mena.API.Rest.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, String> {

}
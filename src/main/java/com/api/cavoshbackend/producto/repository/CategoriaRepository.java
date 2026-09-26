package com.api.cavoshbackend.producto.repository;

import com.api.cavoshbackend.producto.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

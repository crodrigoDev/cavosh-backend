package com.api.cavoshbackend.producto.repository;

import com.api.cavoshbackend.producto.model.ProductoSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductoSizeRepository extends JpaRepository<ProductoSize, Long> {

    List<ProductoSize> findByProductoIdInAndPredeterminadoTrue(Collection<Long> productoIds);

    List<ProductoSize> findByProductoIdOrderByIdAsc(Long productoId);
}

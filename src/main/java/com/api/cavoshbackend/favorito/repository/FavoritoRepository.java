package com.api.cavoshbackend.favorito.repository;

import com.api.cavoshbackend.favorito.model.Favorito;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    @EntityGraph(attributePaths = "producto")
    List<Favorito> findByUsuarioIdOrderByFechaCreacionDescIdDesc(Long usuarioId);

    @Modifying
    @Query(value = """
            INSERT INTO favoritos (usuario_id, producto_id, fecha_creacion)
            VALUES (:usuarioId, :productoId, CURRENT_TIMESTAMP)
            ON CONFLICT (usuario_id, producto_id) DO NOTHING
            """, nativeQuery = true)
    int agregar(@Param("usuarioId") Long usuarioId, @Param("productoId") Long productoId);

    @Modifying
    @Query("DELETE FROM Favorito f WHERE f.usuario.id = :usuarioId AND f.producto.id = :productoId")
    int quitar(@Param("usuarioId") Long usuarioId, @Param("productoId") Long productoId);
}

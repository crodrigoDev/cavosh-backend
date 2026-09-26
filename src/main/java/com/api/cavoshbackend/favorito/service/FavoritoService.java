package com.api.cavoshbackend.favorito.service;

import com.api.cavoshbackend.favorito.dto.response.FavoritoResponse;
import com.api.cavoshbackend.favorito.exception.FavoritoInvalidoException;
import com.api.cavoshbackend.favorito.mapper.FavoritoMapper;
import com.api.cavoshbackend.favorito.model.Favorito;
import com.api.cavoshbackend.favorito.repository.FavoritoRepository;
import com.api.cavoshbackend.producto.exception.ProductoNoEncontradoException;
import com.api.cavoshbackend.producto.model.ProductoSize;
import com.api.cavoshbackend.producto.repository.ProductoRepository;
import com.api.cavoshbackend.producto.repository.ProductoSizeRepository;
import com.api.cavoshbackend.usuario.model.Usuario;
import com.api.cavoshbackend.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final ProductoRepository productoRepository;
    private final ProductoSizeRepository sizeRepository;
    private final UsuarioRepository usuarioRepository;
    private final FavoritoMapper favoritoMapper;

    @Transactional(readOnly = true)
    public List<FavoritoResponse> listar(Long usuarioId) {
        List<Favorito> favoritos = favoritoRepository.findByUsuarioIdOrderByFechaCreacionDescIdDesc(usuarioId);
        if (favoritos.isEmpty()) {
            return List.of();
        }
        List<Long> productoIds = favoritos.stream().map(favorito -> favorito.getProducto().getId()).toList();
        Map<Long, BigDecimal> precios = sizeRepository.findByProductoIdInAndPredeterminadoTrue(productoIds)
                .stream()
                .collect(Collectors.toMap(size -> size.getProducto().getId(), ProductoSize::getPrecio));
        return favoritos.stream()
                .map(favorito -> favoritoMapper.toResponse(favorito, precios.get(favorito.getProducto().getId())))
                .toList();
    }

    @Transactional
    public void agregar(Long usuarioId, Long productoId) {
        usuarioRepository.findById(usuarioId)
                .filter(Usuario::isActivo)
                .orElseThrow(() -> new FavoritoInvalidoException("El usuario no está disponible"));
        productoRepository.findByIdAndDisponibleTrue(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException(productoId));
        favoritoRepository.agregar(usuarioId, productoId);
    }

    @Transactional
    public void quitar(Long usuarioId, Long productoId) {
        favoritoRepository.quitar(usuarioId, productoId);
    }
}

package com.api.cavoshbackend.producto.service;

import com.api.cavoshbackend.producto.dto.response.CategoriaResponse;
import com.api.cavoshbackend.producto.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listar() {
        return categoriaRepository.findAll(Sort.by("orden").ascending().and(Sort.by("id")))
                .stream()
                .map(categoria -> new CategoriaResponse(
                        categoria.getId(), categoria.getNombre(), categoria.getOrden()))
                .toList();
    }
}

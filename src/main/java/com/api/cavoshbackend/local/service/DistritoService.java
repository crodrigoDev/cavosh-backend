package com.api.cavoshbackend.local.service;

import com.api.cavoshbackend.local.dto.response.DistritoResponse;
import com.api.cavoshbackend.local.repository.DistritoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistritoService {

    private final DistritoRepository distritoRepository;

    @Transactional(readOnly = true)
    public List<DistritoResponse> listar() {
        return distritoRepository.findAll(Sort.by("nombre", "id"))
                .stream()
                .map(distrito -> new DistritoResponse(distrito.getId(), distrito.getNombre()))
                .toList();
    }
}

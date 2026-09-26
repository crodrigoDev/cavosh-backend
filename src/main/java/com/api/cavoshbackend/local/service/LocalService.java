package com.api.cavoshbackend.local.service;

import com.api.cavoshbackend.local.dto.response.LocalResponse;
import com.api.cavoshbackend.local.model.Local;
import com.api.cavoshbackend.local.repository.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;

    @Transactional(readOnly = true)
    public List<LocalResponse> listarPorDistrito(Long distritoId) {
        return localRepository.findByDistritoIdAndActivoTrueOrderByNombreAscIdAsc(distritoId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<LocalResponse> listarFrecuentes() {
        return localRepository.findByActivoTrueAndFrecuenteTrueOrderByNombreAscIdAsc()
                .stream().map(this::toResponse).toList();
    }

    private LocalResponse toResponse(Local local) {
        return new LocalResponse(
                local.getId(), local.getNombre(), local.getDireccion(), local.getHorario(), local.isFrecuente(),
                local.getDistrito().getId(), local.getDistrito().getNombre());
    }
}

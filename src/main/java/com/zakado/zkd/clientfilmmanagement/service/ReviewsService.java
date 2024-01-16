package com.zakado.zkd.clientfilmmanagement.service;

import com.zakado.zkd.clientfilmmanagement.model.Reviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewsService {
    Page<Reviews> buscarTodas(Pageable pageable);

    Page<Reviews> buscarCriticaPorIdUsuario(Integer idUsuario, Pageable pageable);

    Reviews buscarCriticaPorId(Integer idMatricula);

    String guardarCritica(Reviews matricula);

    void eliminarCritica(Integer idMatricula);
}

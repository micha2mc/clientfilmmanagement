package com.zakado.zkd.clientfilmmanagement.service;

import com.zakado.zkd.clientfilmmanagement.model.Reviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewsService {
    Page<Reviews> buscarTodas(Pageable pageable);

    Page<Reviews> buscarCriticaPorIdUsuario(Integer idUsuario, Pageable pageable);

    Reviews buscarCriticaPorId(Integer idCrit);

    List<Reviews> buscarCriticasPorIdPeli(Integer idPel);

    Reviews guardarCritica(Reviews matricula);

    void eliminarCritica(Integer idMatricula);

    void actualizarCritica(Reviews reviews);
}

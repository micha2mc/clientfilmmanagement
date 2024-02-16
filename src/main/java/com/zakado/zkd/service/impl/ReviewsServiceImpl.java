package com.zakado.zkd.service.impl;

import com.zakado.zkd.model.Reviews;
import com.zakado.zkd.service.ReviewsService;
import com.zakado.zkd.utils.UtilsManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewsServiceImpl implements ReviewsService {

    private final RestTemplate template;
    String url = "http://localhost:9003/api/reviews";

    @Override
    public Page<Reviews> buscarTodas(Pageable pageable) {

        List<Reviews> reviewsList = Arrays.asList(Objects.requireNonNull(template.getForObject(url, Reviews[].class)));
        return UtilsManagement.getObjectsPagination(pageable, reviewsList);
    }

    @Override
    public Page<Reviews> buscarCriticaPorIdUsuario(Integer idUsuario, Pageable pageable) {
        List<Reviews> reviewsList = Arrays.asList(Objects.requireNonNull(template
                .getForObject(url + "/curso/" + idUsuario, Reviews[].class)));
        return UtilsManagement.getObjectsPagination(pageable, reviewsList);
    }

    @Override
    public Reviews buscarCriticaPorId(Integer idMatricula) {
        return template.getForObject(url + "/" + idMatricula, Reviews.class);
    }

    @Override
    public List<Reviews> buscarCriticasPorIdPeli(Integer idPel) {
        return Arrays
                .asList(Objects.requireNonNull(template.getForObject(url + "/movie/" + idPel, Reviews[].class)));
    }

    @Override
    public Reviews guardarCritica(Reviews reviews) {
        reviews.setDate(LocalDate.now());
        return template.postForObject(url, reviews, Reviews.class);
    }

    @Override
    public void eliminarCritica(Integer id) {
        template.delete(url + "/" + id);
    }

    @Override
    public void actualizarCritica(Reviews reviews) {
        template.put(url, reviews);
    }
}

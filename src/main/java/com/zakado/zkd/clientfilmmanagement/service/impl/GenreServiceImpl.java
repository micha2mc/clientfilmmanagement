package com.zakado.zkd.clientfilmmanagement.service.impl;

import com.zakado.zkd.clientfilmmanagement.model.Genero;
import com.zakado.zkd.clientfilmmanagement.service.GenreService;
import com.zakado.zkd.clientfilmmanagement.utils.UtilsManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private static final String URL = "http://localhost:8080/api/genres";
    private final RestTemplate template;

    @Override
    public Page<Genero> getAllGenre(Pageable pageable) {
        List<Genero> listG = getAllGenre();
        return UtilsManagement.getObjectsPagination(pageable, listG);
    }

    @Override
    public List<Genero> getAllGenre() {
        return Arrays.asList(Objects.requireNonNull(template.getForObject(URL, Genero[].class)));
    }

    @Override
    public void saveGenre(Genero genre) {
        template.postForObject(URL + "/new", genre, Genero.class);
    }

    @Override
    public Genero getGenreById(Integer id) {
        return template.getForObject(URL + "/" + id, Genero.class);
    }

    @Override
    public void eliminarGenero(Integer id) {
        template.delete(URL + "/" + id);
    }
}

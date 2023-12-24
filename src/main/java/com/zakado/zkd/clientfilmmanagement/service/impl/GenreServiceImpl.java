package com.zakado.zkd.clientfilmmanagement.service.impl;

import com.zakado.zkd.clientfilmmanagement.model.Genero;
import com.zakado.zkd.clientfilmmanagement.repository.GeneroRepositorio;
import com.zakado.zkd.clientfilmmanagement.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GeneroRepositorio generoRepositorio;
    private static final String URL = "http://localhost:8080/api/genres";
    private final RestTemplate template;
    @Override
    public List<Genero> getAllGenre() {
        return generoRepositorio.findAll(Sort.by("description"));
    }

    @Override
    public void saveGenre(Genero genre) {
        //template.postForObject(URL + "/new", genre, Genero.class);
        generoRepositorio.save(genre);
    }
}

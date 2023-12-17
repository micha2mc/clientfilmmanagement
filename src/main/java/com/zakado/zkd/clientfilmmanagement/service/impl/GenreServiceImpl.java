package com.zakado.zkd.clientfilmmanagement.service.impl;

import com.zakado.zkd.clientfilmmanagement.model.Genero;
import com.zakado.zkd.clientfilmmanagement.repository.GeneroRepositorio;
import com.zakado.zkd.clientfilmmanagement.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GeneroRepositorio generoRepositorio;

    @Override
    public List<Genero> getAllGenre() {
        return generoRepositorio.findAll(Sort.by("titulo"));
    }
}

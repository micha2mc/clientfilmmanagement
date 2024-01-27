package com.zakado.zkd.service;

import com.zakado.zkd.model.Genero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreService {
    Page<Genero> getAllGenre(Pageable pageable);
    List<Genero> getAllGenre();

    void saveGenre(Genero genre);

    Genero getGenreById(Integer id);

    void eliminarGenero(Integer id);
}

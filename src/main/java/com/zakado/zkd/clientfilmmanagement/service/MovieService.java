package com.zakado.zkd.clientfilmmanagement.service;

import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<Pelicula> getAllMovies(Pageable pageable);

    void saveMovie(Pelicula movie);

    Pelicula findById(Integer id);

    void deleteMovie(Integer id);

    Page<Pelicula> searchMoviesMethod(Pageable pageable, Object obj, String type);
}

package com.zakado.zkd.service;

import com.zakado.zkd.model.Pelicula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<Pelicula> getAllMovies(Pageable pageable);

    void saveMovie(Pelicula movie);

    Pelicula findById(Integer id);

    void deleteMovie(Pelicula movie);

    Page<Pelicula> searchMoviesMethod(Pageable pageable, Object obj, String type);

    void updateMovie(Integer id, Pelicula pelicula);
}

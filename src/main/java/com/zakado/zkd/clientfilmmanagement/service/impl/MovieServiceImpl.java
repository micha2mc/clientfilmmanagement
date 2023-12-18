package com.zakado.zkd.clientfilmmanagement.service.impl;

import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import com.zakado.zkd.clientfilmmanagement.repository.PeliculaRepositorio;
import com.zakado.zkd.clientfilmmanagement.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final PeliculaRepositorio peliculaRepositorio;

    @Override
    public Page<Pelicula> getAllMovies(Pageable pageable) {
        List<Pelicula> listMovies = peliculaRepositorio.findAll();
        return getMoviesPagination(pageable, listMovies);
    }


    @Override
    public void saveMovie(Pelicula movie) {
        peliculaRepositorio.save(movie);
    }

    @Override
    public Pelicula findById(Integer id) {
        return peliculaRepositorio.findById(id).orElse(new Pelicula());
    }

    @Override
    public void deleteMovie(Pelicula movie) {
        peliculaRepositorio.delete(movie);
    }

    @Override
    public Page<Pelicula> searchMoviesMethod(Pageable pageable, Object obj, String type) {
        Page<Pelicula> listado;
        if (Objects.isNull(obj)) {
            listado = getAllMovies(pageable);
        } else {
            listado = switch (type.toUpperCase()) {
                case "TITLE" -> {
                    List<Pelicula> byTitulo = peliculaRepositorio.findByTitleContainingIgnoreCase(String.valueOf(obj));
                    yield getMoviesPagination(pageable, byTitulo);
                }
                case "NAME" -> getMoviesPagination(pageable, List.of(new Pelicula(), new Pelicula()));
                case "GENRE" -> {
                    yield getMoviesPagination(pageable, List.of(new Pelicula()));
                }
                case "YEAR" -> {
                    List<Pelicula> byYear = peliculaRepositorio.findByYear(Integer.valueOf((String) obj));
                    yield getMoviesPagination(pageable, List.of(new Pelicula(), new Pelicula()));
                }
                default -> throw new IllegalStateException("Unexpected value: " + type.toUpperCase());
            };
        }
        return listado;
    }

    private static PageImpl<Pelicula> getMoviesPagination(Pageable pageable, List<Pelicula> listMovies) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Pelicula> list;
        if (listMovies.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listMovies.size());
            list = listMovies.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), listMovies.size());
    }
}

package com.zakado.zkd.clientfilmmanagement.service.impl;

import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import com.zakado.zkd.clientfilmmanagement.service.GenreService;
import com.zakado.zkd.clientfilmmanagement.service.MovieService;
import com.zakado.zkd.clientfilmmanagement.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final UploadFileService uploadFileService;
    private static final String URL = "http://localhost:8080/api/movies";

    private final RestTemplate template;

    @Override
    public Page<Pelicula> getAllMovies(Pageable pageable) {
        List<Pelicula> listMovies = Arrays.asList(Objects.requireNonNull(template.getForObject(URL, Pelicula[].class)));
        return getMoviesPagination(pageable, listMovies);
    }


    @Override
    public void saveMovie(Pelicula movie) {
        template.postForObject(URL, movie, Pelicula.class);
    }

    @Override
    public Pelicula findById(Integer id) {
        return template.getForObject(URL + "/" + id, Pelicula.class);
    }

    @Override
    public void deleteMovie(Pelicula movie) {
        Pelicula pelicula = findById(movie.getNid());
        template.delete(URL + "/" + pelicula.getNid());
        uploadFileService.deleteImage(pelicula.getImage());
    }

    @Override
    public Page<Pelicula> searchMoviesMethod(Pageable pageable, Object obj, String type) {
        Page<Pelicula> listado;
        if (Objects.isNull(obj)) {
            listado = getAllMovies(pageable);
        } else {
            listado = switch (type.toUpperCase()) {
                case "TITLE" -> {
                    List<Pelicula> byTitulo = Arrays.asList(Objects
                            .requireNonNull(template
                                    .getForObject(URL + "/title/" + obj, Pelicula[].class)));
                    yield getMoviesPagination(pageable, byTitulo);
                }
                case "NAME" -> {
                    List<Pelicula> byName = Arrays.asList(Objects
                            .requireNonNull(template
                                    .getForObject(URL + "/actor/" + obj, Pelicula[].class)));
                    yield getMoviesPagination(pageable, byName);
                }
                case "GENRE" -> {
                    List<Pelicula> byGenre = Arrays.asList(Objects
                            .requireNonNull(template
                                    .getForObject(URL + "/genre/" + obj, Pelicula[].class)));
                    yield getMoviesPagination(pageable, byGenre);
                }
                case "YEAR" -> {
                    List<Pelicula> byYear = Arrays.asList(Objects
                            .requireNonNull(template
                                    .getForObject(URL + "/year/" + obj, Pelicula[].class)));
                    yield getMoviesPagination(pageable, byYear);
                }
                default -> throw new IllegalStateException("Unexpected value: " + type.toUpperCase());
            };
        }
        return listado;
    }

    @Override
    public void updateMovie(Integer id, Pelicula pelicula) {
        if (Objects.nonNull(id)) {
            pelicula.setNid(id);
            template.put(URL, pelicula);
        }
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

package com.zakado.zkd.service.impl;

import com.zakado.zkd.model.Pelicula;
import com.zakado.zkd.service.MovieService;
import com.zakado.zkd.service.UploadFileService;
import com.zakado.zkd.utils.UtilsManagement;
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
public class MovieServiceImpl implements MovieService {
    private final UploadFileService uploadFileService;
    private static final String URL = "http://localhost:8080/api/movies";

    private final RestTemplate template;

    @Override
    public Page<Pelicula> getAllMovies(Pageable pageable) {
        List<Pelicula> listMovies = Arrays.asList(Objects.requireNonNull(template.getForObject(URL, Pelicula[].class)));
        return UtilsManagement.getObjectsPagination(pageable, listMovies);
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
                    yield UtilsManagement.getObjectsPagination(pageable, byTitulo);
                }
                case "NAME" -> {
                    List<Pelicula> byName = Arrays.asList(Objects
                            .requireNonNull(template
                                    .getForObject(URL + "/actor/" + obj, Pelicula[].class)));
                    yield UtilsManagement.getObjectsPagination(pageable, byName);
                }
                case "GENRE" -> {
                    List<Pelicula> byGenre = Arrays.asList(Objects
                            .requireNonNull(template
                                    .getForObject(URL + "/genre/" + obj, Pelicula[].class)));
                    yield UtilsManagement.getObjectsPagination(pageable, byGenre);
                }
                case "YEAR" -> {
                    List<Pelicula> byYear = Arrays.asList(Objects
                            .requireNonNull(template
                                    .getForObject(URL + "/year/" + obj, Pelicula[].class)));
                    yield UtilsManagement.getObjectsPagination(pageable, byYear);
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


}

package com.zakado.zkd.clientfilmmanagement.service.impl;

import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import com.zakado.zkd.clientfilmmanagement.model.Reviews;
import com.zakado.zkd.clientfilmmanagement.model.User;
import com.zakado.zkd.clientfilmmanagement.service.MovieService;
import com.zakado.zkd.clientfilmmanagement.service.ReviewsService;
import com.zakado.zkd.clientfilmmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewsServiceImpl implements ReviewsService {

    private final RestTemplate template;
    private final UserService userService;
    private final MovieService movieService;
    String url = "http://localhost:8080/api/reviews";

    @Override
    public Page<Reviews> buscarTodas(Pageable pageable) {
        Reviews[] reviews = template.getForObject(url, Reviews[].class);
        List<Reviews> reviewsList = Arrays.asList(reviews);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Reviews> list;

        if (reviewsList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, reviewsList.size());
            list = reviewsList.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), reviewsList.size());
    }

    @Override
    public Page<Reviews> buscarCriticaPorIdUsuario(Integer idUsuario, Pageable pageable) {
        Reviews[] reviews = template.getForObject(url + "/curso/" + idUsuario, Reviews[].class);
        List<Reviews> reviewsList = Arrays.asList(reviews);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Reviews> list;

        if (reviewsList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, reviewsList.size());
            list = reviewsList.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), reviewsList.size());
    }

    @Override
    public Reviews buscarCriticaPorId(Integer idMatricula) {
        return template.getForObject(url + "/" + idMatricula, Reviews.class);
    }

    @Override
    public List<Reviews> buscarCriticasPorIdPeli(Integer idPel) {
        Reviews[] reviews = template.getForObject(url + "/movie/" + idPel, Reviews[].class);
        return Arrays.asList(reviews);
    }

    @Override
    public Reviews guardarCritica(Reviews reviews) {
        if (reviews.getId() != null && reviews.getId() > 0) {
            //template.put(url, matricula); //peligroso, habría que comprobar la inscripción anterior
            //return "No se puede modificar una matrícula.";
            return null;
        } else {
            //Inscribimos al alumno en el curso
            User user = userService.buscarUsuarioPorCorreo(reviews.getUser().getEmail());
            Pelicula pelicula = movieService.findById(reviews.getIdMovie());
            if (user.getReviews().stream().anyMatch(rev -> pelicula.getNid().equals(rev.getIdMovie()))) {
                //return "Pelicula con crítica por el usuario";
                return null;
            }
            reviews.setDate(LocalDate.now());
            return template.postForObject(url, reviews, Reviews.class);
        }
    }

    @Override
    public void eliminarCritica(Integer idMatricula) {

    }

    @Override
    public void actualizarCritica(Reviews reviews) {
        template.put(url, reviews);
    }
}

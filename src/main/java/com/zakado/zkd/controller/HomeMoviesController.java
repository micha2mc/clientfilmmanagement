package com.zakado.zkd.controller;


import com.zakado.zkd.model.Pelicula;
import com.zakado.zkd.model.Reviews;
import com.zakado.zkd.model.User;
import com.zakado.zkd.paginator.PageRender;
import com.zakado.zkd.service.MovieService;
import com.zakado.zkd.service.ReviewsService;
import com.zakado.zkd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class HomeMoviesController {

    private final MovieService movieService;
    private final UserService userService;
    private final ReviewsService reviewsService;

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model, @RequestParam(name = "page", defaultValue = "0") int page, Principal principal) {
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        Pageable pageable = PageRequest.of(page, 8);
        Page<Pelicula> listMovies = movieService.getAllMovies(pageable);

        PageRender<Pelicula> pageRender = new PageRender<>("", listMovies);
        model.addAttribute("listMovies", listMovies);
        model.addAttribute("page", pageRender);
        model.addAttribute("username", usuario.getUsername());
        return "home";
    }


    @GetMapping("/peliculas/{id}")
    public String mostrarDetallesDePelicula(Model model, @PathVariable Integer id, Principal principal) {

        Pelicula movie = movieService.findById(id);
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        List<Reviews> listCriticas = reviewsService.buscarCriticasPorIdPeli(id);
        double notaMedia = notaMedia(listCriticas);
        model.addAttribute("username", usuario.getUsername());
        model.addAttribute("movie", movie);
        model.addAttribute("criticas", listCriticas);
        model.addAttribute("notemedia", notaMedia);
        return "movie";
    }

    private double notaMedia(List<Reviews> listCriticas) {
        int notesum = 0;
        Integer numNote = 0;
        for (Reviews reviews : listCriticas) {
            if (Objects.nonNull(reviews.getNote())) {
                notesum = notesum + reviews.getNote();
                numNote = numNote + 1;
            }
        }
        return numNote.equals(0) ? 0 : (double) notesum / numNote;
    }
}

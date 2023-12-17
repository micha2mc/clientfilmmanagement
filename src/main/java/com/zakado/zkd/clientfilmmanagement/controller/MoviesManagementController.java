package com.zakado.zkd.clientfilmmanagement.controller;


import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import com.zakado.zkd.clientfilmmanagement.paginator.PageRender;
import com.zakado.zkd.clientfilmmanagement.service.MovieService;
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


@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MoviesManagementController {

    private final MovieService movieService;

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Pelicula> listMovies = movieService.getAllMovies(pageable);

        PageRender<Pelicula> pageRender = new PageRender<>("", listMovies);
        model.addAttribute("listMovies", listMovies);
        model.addAttribute("page", pageRender);
        return "home";
    }


    @GetMapping("/peliculas/{id}")
    public String mostrarDetallesDePelicula(Model model, @PathVariable Integer id) {
        Pelicula movie = movieService.findById(id);
        model.addAttribute("movie", movie);
        return "movie";
    }
}

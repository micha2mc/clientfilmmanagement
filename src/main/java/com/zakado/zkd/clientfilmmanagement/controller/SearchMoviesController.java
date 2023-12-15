package com.zakado.zkd.clientfilmmanagement.controller;

import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import com.zakado.zkd.clientfilmmanagement.paginator.PageRender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchMoviesController {

    @GetMapping
    public String buscadorPeliculas() {
        return "search/search";
    }

    @GetMapping("/title")
    public String searchMovieByTitle(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam("title") String title) {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Pelicula> listado = null;
        if (!StringUtils.hasText(title)) {
            //listado = moviesService.searchAllMovies(pageable);
        } else {
            //listado = moviesService.searchMovieByTitle(title, pageable);
        }
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/movies/home", listado);
        model.addAttribute("listMovies", listado);
        model.addAttribute("page", pageRender);
        return "home";
    }

    @GetMapping("/name")
    public String searchMoviesByNameActor(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam("name") String name) {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Pelicula> listado = null;
        if (!StringUtils.hasText(name)) {
            //listado = moviesService.searchAllMovies(pageable);
        } else {
            //listado = moviesService.searchMoviesByActor(name, pageable);
        }
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/movies/home", listado);
        model.addAttribute("listMovies", listado);
        model.addAttribute("page", pageRender);
        return "home";
    }

    @GetMapping("/genre")
    public String searchMoviesByGenre(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam("genre") String genre) {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Pelicula> listado = null;
        if (!StringUtils.hasText(genre)) {
            //listado = moviesService.searchAllMovies(pageable);
        } else {
            //listado = moviesService.searchMoviesByGenre(genre, pageable);
        }
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/movies/home", listado);
        model.addAttribute("listMovies", listado);
        model.addAttribute("page", pageRender);
        return "home";
    }

    @GetMapping("/year")
    public String searchMoviesByYear(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam("year") Integer year) {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Pelicula> listado = null;
        if (Objects.isNull(year)) {
            //listado = moviesService.searchAllMovies(pageable);
        } else {
            //listado = moviesService.searchMoviesByYear(year, pageable);
        }
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/movies/home", listado);
        model.addAttribute("listMovies", listado);
        model.addAttribute("page", pageRender);
        return "home";
    }
}

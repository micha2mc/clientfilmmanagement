package com.zakado.zkd.clientfilmmanagement.controller;


import com.zakado.zkd.clientfilmmanagement.model.*;
import com.zakado.zkd.clientfilmmanagement.paginator.PageRender;
import com.zakado.zkd.clientfilmmanagement.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {


    private final UploadFileService uploadFileService;
    private final GenreService genreService;
    private final MovieService movieService;
    private final ActorService actorService;
    private final UserService userService;
    private final ReviewsService reviewsService;


    @GetMapping("")
    public String homeAdmin(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                            Principal principal) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listMovies = movieService.getAllMovies(pageable);
        PageRender<Pelicula> pageRender = new PageRender<>("/admin", listMovies);
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        model.addAttribute("username", usuario.getUsername());
        model.addAttribute("listMovies", listMovies);
        model.addAttribute("page", pageRender);
        return "admin/home-admin";
    }

    @GetMapping("/new")
    public String showCreateNewMovieForm(Model model, Principal principal) {
        List<Genero> generos = genreService.getAllGenre();
        List<Actor> allActors = actorService.getAllActors();
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        model.addAttribute("username", usuario.getUsername());
        model.addAttribute("movie", new Pelicula());
        model.addAttribute("generos", generos);
        model.addAttribute("allActors", allActors);
        return "admin/nueva-pelicula";
    }

    @PostMapping("/new")
    public String saveMovie(@ModelAttribute("movie") Pelicula movie, @RequestParam("file") MultipartFile foto, RedirectAttributes attributes) {

        if (!foto.isEmpty()) {
            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("Has subido correctamente {}", uniqueFilename);
            movie.setImage(uniqueFilename);
        }
        movie.setGenres(new HashSet<>());
        movie.setActors(new HashSet<>());
        movieService.saveMovie(movie);
        attributes.addFlashAttribute("msg", "Película registrada correctamente!");
        return "redirect:/admin";
    }

    @GetMapping("/peliculas/{id}/editar")
    public ModelAndView mostrarFormilarioDeEditarPelicula(@PathVariable Integer id, Principal principal) {
        Pelicula pelicula = movieService.findById(id);
        List<Genero> generos = genreService.getAllGenre();
        List<Actor> allActors = actorService.getAllActors();
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());

        return new ModelAndView("admin/editar-pelicula")
                .addObject("movie", pelicula)
                .addObject("generos", generos)
                .addObject("username", usuario.getUsername())
                .addObject("allActors", allActors);
    }

    @PostMapping("/peliculas/{id}/editar")
    public ModelAndView actualizarPelicula(@PathVariable Integer id, @Validated Pelicula pelicula,
                                           BindingResult bindingResult, @RequestParam("file") MultipartFile foto,
                                           RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            List<Genero> generos = genreService.getAllGenre();
            return new ModelAndView("admin/editar-pelicula")
                    .addObject("pelicula", pelicula)
                    .addObject("generos", generos);
        }

        if (!foto.isEmpty()) {
            String uniqueFilename = null;
            try {
                Pelicula peliculaDB = movieService.findById(id);
                if (Objects.nonNull(peliculaDB.getImage())) {
                    uploadFileService.deleteImage(peliculaDB.getImage());
                }
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("Has subido correctamente {}", uniqueFilename);
            pelicula.setImage(uniqueFilename);
        }
        movieService.updateMovie(id, pelicula);
        return new ModelAndView("redirect:/admin");
    }

    @PostMapping("/peliculas/{id}/eliminar")
    public String eliminarPelicula(@PathVariable Integer id) {
        List<Reviews> reviews = reviewsService.buscarCriticasPorIdPeli(id);
        for (Reviews reviews1 : reviews) {
            reviewsService.eliminarCritica(reviews1.getId());
        }
        Pelicula pelicula = movieService.findById(id);
        movieService.deleteMovie(pelicula);
        return "redirect:/admin";
    }
}

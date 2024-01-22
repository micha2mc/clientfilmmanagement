package com.zakado.zkd.clientfilmmanagement.controller;

import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import com.zakado.zkd.clientfilmmanagement.model.User;
import com.zakado.zkd.clientfilmmanagement.paginator.PageRender;
import com.zakado.zkd.clientfilmmanagement.service.MovieService;
import com.zakado.zkd.clientfilmmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final MovieService movieService;
    private final UserService userService;

    @GetMapping("/movies")
    public String buscadorPeliculas(Model model, Principal principal) {
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        model.addAttribute("username", usuario.getUsername());
        return "search/searchMovie";
    }

    @GetMapping("/title")
    public String searchMovieByTitle(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam("title") String title, Principal principal) {
        return searchMoviesMethod(model, page, title, "TITLE", principal);

    }

    @GetMapping("/name")
    public String searchMoviesByNameActor(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam("name") String name, Principal principal) {

        return searchMoviesMethod(model, page, name, "NAME", principal);
    }

    @GetMapping("/genre")
    public String searchMoviesByGenre(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam("genre") String genre, Principal principal) {
        return searchMoviesMethod(model, page, genre, "GENRE", principal);
    }

    @GetMapping("/year")
    public String searchMoviesByYear(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam("year") Integer year, Principal principal) {
        return searchMoviesMethod(model, page, year, "YEAR", principal);
    }


    @GetMapping("/users")
    public String buscadorUsuarios(Model model, Principal principal) {
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        model.addAttribute("username", usuario.getUsername());
        return "search/searchUsers";
    }

    @GetMapping("/users/username")
    public String buscarVariosPorNombre(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam("username") String name, Principal principal) {
        Pageable pageable = PageRequest.of(page, 8);
        Page<User> users = userService.buscarVariosPorNombre(pageable, name);
        return searchUsersMethod(model, principal, users, "nombre");
    }

    @GetMapping("/users/email")
    public String buscarUsuarioPorCorreo(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam("email") String email, Principal principal) {
        Pageable pageable = PageRequest.of(page, 1);
        Page<User> users = userService.buscarUsuarioPorCorreo(pageable, email);
        return searchUsersMethod(model, principal, users, "correo");
    }

    private String searchUsersMethod(Model model, Principal principal, Page<User> users, String paramBusq) {
        PageRender<User> pageRender = new PageRender<>("/users", users);
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());

        model.addAttribute("username", usuario.getUsername());
        model.addAttribute("listadoUsuarios", users);
        model.addAttribute("titulo", "Resultado de la búsqueda de usuarios por " + paramBusq);
        model.addAttribute("page", pageRender);
        return "usuarios/list-usuario";
    }

    private String searchMoviesMethod(Model model, int page, Object obj, String type, Principal principal) {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Pelicula> listado = movieService.searchMoviesMethod(pageable, obj, type);
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        model.addAttribute("username", usuario.getUsername());
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/movies/home", listado);
        String paramB = typePattern(type);
        model.addAttribute("listMovies", listado);
        model.addAttribute("page", pageRender);
        model.addAttribute("titulo", "Resultado de la búsqueda de pelis por "+ paramB);
        return "admin/home-admin";
    }

    private String typePattern(String type) {

        return switch (type){
            case "TITLE" -> "título";
            case "NAME" -> "nombre del actor";
            case "GENRE" -> "género de la peli";
            case "YEAR" -> "año";
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}

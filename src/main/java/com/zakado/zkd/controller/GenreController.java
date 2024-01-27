package com.zakado.zkd.controller;

import com.zakado.zkd.model.Genero;
import com.zakado.zkd.model.User;
import com.zakado.zkd.paginator.PageRender;
import com.zakado.zkd.service.GenreService;
import com.zakado.zkd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;
    private final UserService userService;

    @GetMapping
    public String getAllGenres(Model model, @RequestParam(name = "page", defaultValue = "0") int page, Principal principal) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Genero> allGenre = genreService.getAllGenre(pageable);
        PageRender<Genero> pageRender = new PageRender<>("/genres", allGenre);
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        model.addAttribute("allGenre", allGenre);
        model.addAttribute("username", usuario.getUsername());
        model.addAttribute("page", pageRender);
        return "admin/home-genres";
    }

    @GetMapping("/new")
    public String getFormGenre(Model model, Principal principal) {
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        model.addAttribute("genre", new Genero());
        model.addAttribute("username", usuario.getUsername());
        return "admin/new-genre";
    }

    @PostMapping("/new")
    public String saveGenre(Genero genre, RedirectAttributes attributes) {
        List<Genero> allGenre = genreService.getAllGenre();
        if (allGenre.stream().anyMatch(gen -> gen.getDescription().equalsIgnoreCase(genre.getDescription()))) {
            attributes.addFlashAttribute("msga", "Género ya existe");
        } else {
            genreService.saveGenre(genre);
        }
        return "redirect:/genres";
    }

    @GetMapping("/editar/{id}")
    public String editarGenero(Model model, @PathVariable("id") Integer id, Principal principal) {
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        ;
        Genero genero = genreService.getGenreById(id);
        model.addAttribute("titulo", "Editar Genero");
        model.addAttribute("genre", genero);
        model.addAttribute("username", usuario.getUsername());
        return "admin/new-genre";
    }

    @PostMapping("/editar/{id}")
    public String editarGenero(Model model, @PathVariable("id") Integer id, Genero genre) {
        Genero genero = genreService.getGenreById(id);
        genero.setDescription(genre.getDescription());
        genreService.saveGenre(genero);
        model.addAttribute("titulo", "Editar Genero");
        return "redirect:/genres";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarGenero(@PathVariable("id") Integer id) {
        genreService.eliminarGenero(id);
        return "redirect:/genres";
    }
}

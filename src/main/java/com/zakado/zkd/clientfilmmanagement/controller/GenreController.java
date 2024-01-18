package com.zakado.zkd.clientfilmmanagement.controller;

import com.zakado.zkd.clientfilmmanagement.model.Genero;
import com.zakado.zkd.clientfilmmanagement.model.User;
import com.zakado.zkd.clientfilmmanagement.service.GenreService;
import com.zakado.zkd.clientfilmmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;
    private final UserService userService;

    @GetMapping("/new")
    public String getFormGenre(Model model, Principal principal) {
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        model.addAttribute("genre", new Genero());
        model.addAttribute("username", usuario.getUsername());
        return "admin/new-genre";
    }

    @PostMapping("/new")
    public String saveGenre(Genero genre) {
        genreService.saveGenre(genre);
        return "redirect:/admin";
    }
}

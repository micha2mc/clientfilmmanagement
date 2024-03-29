package com.zakado.zkd.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class LoginController {
    String message = """
            Error al iniciar sesión: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!
            """;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/movies";
        }
        if (error != null) {
            model.addAttribute("msga", message);
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}

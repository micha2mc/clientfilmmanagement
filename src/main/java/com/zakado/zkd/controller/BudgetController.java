package com.zakado.zkd.controller;

import com.zakado.zkd.model.Budget;
import com.zakado.zkd.model.Pelicula;
import com.zakado.zkd.model.User;
import com.zakado.zkd.service.BudgetService;
import com.zakado.zkd.service.MovieService;
import com.zakado.zkd.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private final UserService userService;
    private final MovieService movieService;

    @GetMapping("/{id}")
    public String showBudget(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes,
                               Principal principal) {
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        Budget budget = budgetService.searchBudgetByIdMovie(id);
        if (Objects.isNull(budget)) {
            attributes.addFlashAttribute("msga", "Sin datos del presupuesto");
            return "redirect:/movies/peliculas/" + id;
        }
        Pelicula pelicula = movieService.findById(id);
        model.addAttribute("titulo", "Presupuesto");
        model.addAttribute("budget", budget);
        model.addAttribute("movie", pelicula);
        model.addAttribute("username", usuario.getUsername());
        return "budget/budget";
    }
    @GetMapping("/guardar/{idMatricula}")
    public String crearOpinion(@PathVariable("idMatricula") Integer id, Model model, RedirectAttributes attributes,
                               Principal principal) {
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        Budget budget = budgetService.searchBudgetByIdMovie(id);
        if (Objects.nonNull(budget)) {
            attributes.addFlashAttribute("msga", "Presupuesto ya existe");
            return "redirect:/movies/peliculas/" + id;
        }
        Budget budgetTem = new Budget();
        budgetTem.setIdmov(id);
        Budget budgetSaved = budgetService.saveBudget(budgetTem);
        model.addAttribute("titulo", "Presupuesto");
        model.addAttribute("budget", budgetSaved);
        model.addAttribute("username", usuario.getUsername());
        return "budget/form-budget";
    }

    @PostMapping
    public String guardarCritica(Budget budget, RedirectAttributes attributes) {
        budgetService.saveBudget(budget);
        attributes.addFlashAttribute("msg", "Opinión realizada con éxito");
        return "redirect:/movies/peliculas/" + budget.getIdmov();
    }
}

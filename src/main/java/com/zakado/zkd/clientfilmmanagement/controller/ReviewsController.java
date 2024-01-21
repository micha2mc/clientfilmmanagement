package com.zakado.zkd.clientfilmmanagement.controller;

import com.zakado.zkd.clientfilmmanagement.model.Reviews;
import com.zakado.zkd.clientfilmmanagement.model.User;
import com.zakado.zkd.clientfilmmanagement.service.ReviewsService;
import com.zakado.zkd.clientfilmmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewsController {

    private final UserService userService;
    private final ReviewsService reviewsService;

    @GetMapping("/{id}")
    public String verOpinion(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes,
                             Principal principal) {
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        Reviews reviews = reviewsService.buscarCriticaPorId(id);
        if (!isCorrectUser(reviews, usuario)) {
            attributes.addFlashAttribute("msga", "Usuario sin permiso");
            return "redirect:/movies/peliculas/" + reviews.getIdMovie();
        }
        model.addAttribute("critica", reviews);
        model.addAttribute("username", usuario.getUsername());
        return "usuarios/critica";
    }

    @GetMapping("/guardar/{idMatricula}")
    public String crearOpinion(@PathVariable("idMatricula") Integer id, Model model, RedirectAttributes attributes,
                               Principal principal) {
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        List<Reviews> reviews = reviewsService.buscarCriticasPorIdPeli(id);
        if (reviews.stream().anyMatch(rev -> usuario.getId().equals(rev.getUser().getId()))) {
            attributes.addFlashAttribute("msga", "Usuario ya puntuó la película");
            return "redirect:/movies/peliculas/" + id;
        }
        Reviews critica = new Reviews(id, usuario);
        Reviews criticaSaved = reviewsService.guardarCritica(critica);
        model.addAttribute("titulo", "Nueva Crítica");
        model.addAttribute("critica", criticaSaved);
        return "usuarios/form-criticas";
    }

    @GetMapping("/editar/{id}")
    public String editarOpinion(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes,
                                Principal principal) {
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());

        Reviews reviews = usuario.getReviews().stream()
                .filter(re -> re.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("critica", reviews);
        model.addAttribute("username", usuario.getUsername());
        return "usuarios/form-criticas";
    }

    @PostMapping("/critica")
    public String guardarCritica(Reviews critica, RedirectAttributes attributes) {
        Reviews reviews = reviewsService.buscarCriticaPorId(critica.getId());
        if (!StringUtils.hasText(critica.getAssessment())) {
            reviewsService.eliminarCritica(critica.getId());
            return "redirect:/movies/peliculas/" + reviews.getIdMovie();
        }
        reviews.setAssessment(critica.getAssessment());
        reviews.setNote(critica.getNote());
        reviewsService.actualizarCritica(reviews);
        attributes.addFlashAttribute("msg", "Opinión realizada con éxito");
        return "redirect:/movies/peliculas/" + reviews.getIdMovie();
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarOpinion(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        Reviews reviews = reviewsService.buscarCriticaPorId(id);
        reviewsService.eliminarCritica(id);
        attributes.addFlashAttribute("msga", "Opinión eliminada con éxito.");
        return "redirect:/movies/peliculas/" + reviews.getIdMovie();
    }

    private static boolean isCorrectUser(Reviews reviews1, User usuario) {
        if (usuario.getRoles().stream().noneMatch(rolU -> "ROLE_ADMIN".equalsIgnoreCase(rolU.getAuthority()))) {
            return reviews1.getUser().getId().equals(usuario.getId());
        }
        return true;
    }
}

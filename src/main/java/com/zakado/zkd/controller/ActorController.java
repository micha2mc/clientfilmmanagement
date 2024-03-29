package com.zakado.zkd.controller;

import com.zakado.zkd.model.Actor;
import com.zakado.zkd.model.User;
import com.zakado.zkd.paginator.PageRender;
import com.zakado.zkd.service.ActorService;
import com.zakado.zkd.service.UploadFileService;
import com.zakado.zkd.service.UserService;
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

@Controller
@RequestMapping("/actors")
@RequiredArgsConstructor
@Slf4j
public class ActorController {

    private final UploadFileService uploadFileService;
    private final ActorService actorService;
    private final UserService userService;

    @GetMapping
    public String homeActors(Model model, @RequestParam(name = "page", defaultValue = "0") int page, Principal principal) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> listActors = actorService.getAllActors(pageable);
        PageRender<Actor> pageRender = new PageRender<>("/actors", listActors);
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        model.addAttribute("username", usuario.getUsername());
        model.addAttribute("listActors", listActors);
        model.addAttribute("page", pageRender);
        return "actor/home-actors";
    }

    @GetMapping("/actor/{id}")
    public String showActorDetails(Model model, @PathVariable Integer id, Principal principal) {
        Actor actor = actorService.getActorById(id);
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        model.addAttribute("username", usuario.getUsername());
        model.addAttribute("actor", actor);
        return "actor/actor";
    }

    @GetMapping("/new")
    public String showCreateNewActorForm(Model model, Principal principal) {
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        model.addAttribute("username", usuario.getUsername());
        model.addAttribute("actor", new Actor());
        return "actor/new-actor";
    }

    @PostMapping("/new")
    public String saveActor(Actor actor, @RequestParam("file") MultipartFile foto, RedirectAttributes attributes) {

        if (!foto.isEmpty()) {
            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                log.info("Error: {}", e.getMessage());
            }
            attributes.addFlashAttribute("msg", "Has subido correctamente '" + uniqueFilename + "'");
            actor.setImage(uniqueFilename);
        }

        actorService.saveActor(actor);
        attributes.addFlashAttribute("msg", "Actor registrado correctamente!");
        return "redirect:/actors";
    }

    @GetMapping("/actor/{id}/editar")
    public ModelAndView showFromUpdate(Model model, @PathVariable Integer id, Principal principal) {
        Actor actor = actorService.getActorById(id);
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());

        return new ModelAndView("actor/edit-actor")
                .addObject("actor", actor)
                .addObject("username", usuario.getUsername());
    }

    @PostMapping("/actor/{id}/editar")
    public ModelAndView updateActor(@PathVariable Integer id, @Validated Actor actor,
                                    BindingResult bindingResult, @RequestParam("file") MultipartFile foto,
                                    RedirectAttributes attributes, Principal principal) {

        if (bindingResult.hasErrors()) {
            User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
            return new ModelAndView("actor/edit-actor")
                    .addObject("actor", actor)
                    .addObject("username", usuario.getUsername());
        }
        Actor actorBBDD = actorService.getActorById(id);
        actorBBDD.setName(actor.getName());
        actorBBDD.setDob(actor.getDob());
        actorBBDD.setCob(actor.getCob());
        actorBBDD.setGenre(actor.getGenre());

        if (!foto.isEmpty()) {
            String uniqueFilename = null;
            try {
                uploadFileService.deleteImage(actorBBDD.getImage());
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                log.info("Error: {}", e.getMessage());
            }
            attributes.addFlashAttribute("msg", "Has subido correctamente '" + uniqueFilename + "'");
            actorBBDD.setImage(uniqueFilename);
        }
        actorService.saveActor(actorBBDD);
        return new ModelAndView("redirect:/actors");
    }

    @PostMapping("/actor/{id}/eliminar")
    public String deleteActor(@PathVariable Integer id) {
        actorService.deleteActor(id);
        return "redirect:/actors";
    }
}

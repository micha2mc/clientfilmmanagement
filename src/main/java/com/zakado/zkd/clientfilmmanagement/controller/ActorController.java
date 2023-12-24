package com.zakado.zkd.clientfilmmanagement.controller;

import com.zakado.zkd.clientfilmmanagement.model.Actor;
import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import com.zakado.zkd.clientfilmmanagement.paginator.PageRender;
import com.zakado.zkd.clientfilmmanagement.service.ActorService;
import com.zakado.zkd.clientfilmmanagement.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/actors")
@RequiredArgsConstructor
public class ActorController {

    private final UploadFileService uploadFileService;
    private final ActorService actorService;

    @GetMapping
    public String homeActors(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 5);

        Page<Actor> listActors = actorService.getAllActors(pageable);
        PageRender<Actor> pageRender = new PageRender<>("/actor", listActors);
        model.addAttribute("listActors", listActors);
        model.addAttribute("page", pageRender);
        return "actor/home-actors";
    }
    @GetMapping("/actor/{id}")
    public String showActorDetails(Model model, @PathVariable Integer id) {
        Actor actor = actorService.getActorById(id);
        model.addAttribute("actor", actor);
        return "actor/actor";
    }
    @GetMapping("/list")
    public String showAllActors(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 5);

        Page<Actor> listActors = actorService.getAllActors(pageable);
        PageRender<Actor> pageRender = new PageRender<>("/actor", listActors);
        model.addAttribute("listActors", listActors);
        model.addAttribute("page", pageRender);
        return "actor/showactors";
    }

    @GetMapping("/new")
    public String showCreateNewActorForm(Model model) {
        //List<Genero> generos = genreService.getAllGenre();
        model.addAttribute("actor", new Actor());
        // model.addAttribute("generos", generos);
        return "actor/new-actor";
    }

    @PostMapping("/new")
    public String saveActor(Model model, Actor actor, @RequestParam("file") MultipartFile foto,
                            RedirectAttributes attributes) {

        if (!foto.isEmpty()) {
            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            attributes.addFlashAttribute("msg", "Has subido correctamente '" + uniqueFilename + "'");
            //movie.setRutaPortada(uniqueFilename);
            actor.setImage(uniqueFilename);
        }

        actorService.saveActor(actor);
        attributes.addFlashAttribute("msg", "Película registrada correctamente!");
        return "redirect:/actors";
    }
}

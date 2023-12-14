package com.zakado.zkd.clientfilmmanagement.controller;


import com.zakado.zkd.clientfilmmanagement.model.Genero;
import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import com.zakado.zkd.clientfilmmanagement.paginator.PageRender;
import com.zakado.zkd.clientfilmmanagement.repository.GeneroRepositorio;
import com.zakado.zkd.clientfilmmanagement.repository.PeliculaRepositorio;
import com.zakado.zkd.clientfilmmanagement.service.AlmacenServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Autowired
    private GeneroRepositorio generoRepositorio;

    @Autowired
    private AlmacenServicioImpl servicio;


    @GetMapping("")
    public String verPaginaDeInicio(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 4);
        List<Pelicula> listMovies = peliculaRepositorio.findAll();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Pelicula> list;
        if (listMovies.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listMovies.size());
            list = listMovies.subList(startItem, toIndex);
        }
        Page<Pelicula> pageEnd = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), listMovies.size());
        PageRender<Pelicula> pageRender = new PageRender<>("/admin", pageEnd);
        model.addAttribute("listMovies", pageEnd);
        model.addAttribute("page", pageRender);
        return "admin/home-admin";
    }

    @GetMapping("/peliculas/nuevo")
    public ModelAndView mostrarFormularioDeNuevaPelicula() {
        List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));
        return new ModelAndView("admin/nueva-pelicula")
                .addObject("pelicula", new Pelicula())
                .addObject("generos", generos);
    }

    @PostMapping("/peliculas/nuevo")
    public ModelAndView registrarPelicula(@Validated Pelicula pelicula, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || pelicula.getPortada().isEmpty()) {
            if (pelicula.getPortada().isEmpty()) {
                bindingResult.rejectValue("portada", "MultipartNotEmpty");
            }

            List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));
            return new ModelAndView("admin/nueva-pelicula")
                    .addObject("pelicula", pelicula)
                    .addObject("generos", generos);
        }

        String rutaPortada = servicio.almacenarArchivo(pelicula.getPortada());
        pelicula.setRutaPortada(rutaPortada);

        peliculaRepositorio.save(pelicula);
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/peliculas/{id}/editar")
    public ModelAndView mostrarFormilarioDeEditarPelicula(@PathVariable Integer id) {
        Pelicula pelicula = peliculaRepositorio.findById(id).orElse(null);
        List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));

        return new ModelAndView("admin/editar-pelicula")
                .addObject("pelicula", pelicula)
                .addObject("generos", generos);
    }

    @PostMapping("/peliculas/{id}/editar")
    public ModelAndView actualizarPelicula(@PathVariable Integer id, @Validated Pelicula pelicula, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));
            return new ModelAndView("admin/editar-pelicula")
                    .addObject("pelicula", pelicula)
                    .addObject("generos", generos);
        }

        Pelicula peliculaDB = peliculaRepositorio.getOne(id);
        peliculaDB.setTitulo(pelicula.getTitulo());
        peliculaDB.setSinopsis(pelicula.getSinopsis());
        peliculaDB.setFechaEstreno(pelicula.getFechaEstreno());
        peliculaDB.setYoutubeTrailerId(pelicula.getYoutubeTrailerId());
        peliculaDB.setGeneros(pelicula.getGeneros());

        if (!pelicula.getPortada().isEmpty()) {
            servicio.eliminarArchivo(peliculaDB.getRutaPortada());
            String rutaPortada = servicio.almacenarArchivo(pelicula.getPortada());
            peliculaDB.setRutaPortada(rutaPortada);
        }

        peliculaRepositorio.save(peliculaDB);
        return new ModelAndView("redirect:/admin");
    }

    @PostMapping("/peliculas/{id}/eliminar")
    public String eliminarPelicula(@PathVariable Integer id) {
        Pelicula pelicula = peliculaRepositorio.getOne(id);
        peliculaRepositorio.delete(pelicula);
        servicio.eliminarArchivo(pelicula.getRutaPortada());

        return "redirect:/admin";
    }
}

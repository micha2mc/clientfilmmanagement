package com.zakado.zkd.clientfilmmanagement.controller;


import com.zakado.zkd.clientfilmmanagement.model.Genero;
import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import com.zakado.zkd.clientfilmmanagement.paginator.PageRender;
import com.zakado.zkd.clientfilmmanagement.service.GenreService;
import com.zakado.zkd.clientfilmmanagement.service.MovieService;
import com.zakado.zkd.clientfilmmanagement.service.UploadFileService;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {


    private final UploadFileService uploadFileService;
    private final GenreService genreService;
    private final MovieService movieService;


    @GetMapping("")
    public String homeAdmin(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listMovies = movieService.getAllMovies(pageable);
        PageRender<Pelicula> pageRender = new PageRender<>("/admin", listMovies);
        model.addAttribute("listMovies", listMovies);
        model.addAttribute("page", pageRender);
        return "admin/home-admin";
    }

    @GetMapping("/new")
    public String showCreateNewMovieForm(Model model) {
        List<Genero> generos = genreService.getAllGenre();
        model.addAttribute("movie", new Pelicula());
        model.addAttribute("generos", generos);
        return "admin/nueva-pelicula";
    }

    @PostMapping("/new")
    public String saveMovie(Model model, Pelicula movie, @RequestParam("file") MultipartFile foto,
                            RedirectAttributes attributes) {

        if (!foto.isEmpty()) {
            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            attributes.addFlashAttribute("msg", "Has subido correctamente '" + uniqueFilename + "'");
            movie.setRutaPortada(uniqueFilename);
        }

        movieService.saveMovie(movie);
        attributes.addFlashAttribute("msg", "Película registrada correctamente!");
        return "redirect:/admin";
    }

    @GetMapping("/peliculas/{id}/editar")
    public ModelAndView mostrarFormilarioDeEditarPelicula(@PathVariable Integer id) {
        Pelicula pelicula = movieService.findById(id);
        List<Genero> generos = genreService.getAllGenre();

        return new ModelAndView("admin/editar-pelicula")
                .addObject("movie", pelicula)
                .addObject("generos", generos);
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

        Pelicula peliculaDB = movieService.findById(id);
        peliculaDB.setTitulo(pelicula.getTitulo());
        peliculaDB.setSinopsis(pelicula.getSinopsis());
        peliculaDB.setFechaEstreno(pelicula.getFechaEstreno());
        peliculaDB.setYoutubeTrailerId(pelicula.getYoutubeTrailerId());
        peliculaDB.setGeneros(pelicula.getGeneros());
        peliculaDB.setYear(pelicula.getYear());
        peliculaDB.setDuration(pelicula.getDuration());

        if (!foto.isEmpty()) {
            String uniqueFilename = null;
            try {
                uploadFileService.deleteImage(peliculaDB.getRutaPortada());
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            attributes.addFlashAttribute("msg", "Has subido correctamente '" + uniqueFilename + "'");
            peliculaDB.setRutaPortada(uniqueFilename);
        }
        movieService.saveMovie(peliculaDB);
        return new ModelAndView("redirect:/admin");
    }

    @PostMapping("/peliculas/{id}/eliminar")
    public String eliminarPelicula(@PathVariable Integer id) {
        Pelicula pelicula = movieService.findById(id);
        movieService.deleteMovie(pelicula);
        uploadFileService.deleteImage(pelicula.getRutaPortada());
        return "redirect:/admin";
    }
}

package com.zakado.zkd.clientfilmmanagement.controller;


import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import com.zakado.zkd.clientfilmmanagement.paginator.PageRender;
import com.zakado.zkd.clientfilmmanagement.repository.PeliculaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MoviesManagementController {

	private final PeliculaRepositorio peliculaRepositorio;

	@GetMapping(value = {"/", "/home", ""})
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
		PageRender<Pelicula> pageRender = new PageRender<>("", pageEnd);
		model.addAttribute("listMovies", pageEnd);
		model.addAttribute("page", pageRender);
		return "home";
	}



	
	@GetMapping("/peliculas/{id}")
	public String mostrarDetallesDePelicula(Model model, @PathVariable Integer id) {
		Pelicula movie = peliculaRepositorio.findById(id).orElse(null);
		model.addAttribute("movie", movie);
		return "movie";
	}
}

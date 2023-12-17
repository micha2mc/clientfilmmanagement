package com.zakado.zkd.clientfilmmanagement.repository;


import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeliculaRepositorio extends JpaRepository<Pelicula, Integer> {
    List<Pelicula> findByTituloContainingIgnoreCase(String title);

    List<Pelicula> findByYear(Integer integer);
}

package com.zakado.zkd.clientfilmmanagement.repository;


import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepositorio extends JpaRepository<Pelicula, Integer> {

}

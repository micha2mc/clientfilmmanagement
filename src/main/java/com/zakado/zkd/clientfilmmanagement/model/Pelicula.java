package com.zakado.zkd.clientfilmmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    private String sinopsis;

    private Integer year;
    private Integer duration;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate fechaEstreno;

    private String youtubeTrailerId;

    private String rutaPortada;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "genero_pelicula", joinColumns = @JoinColumn(name = "id_pelicula"), inverseJoinColumns = @JoinColumn(name = "id_genero"))
    private List<Genero> generos;

}

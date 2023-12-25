package com.zakado.zkd.clientfilmmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nid;

    private String title;
    private Integer year;
    private Integer duration;

    private String country;
    private String synopsis;
    private String image;

    private String youtubeTrailerId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "genero_pelicula", joinColumns = @JoinColumn(name = "id_pelicula"),
            inverseJoinColumns = @JoinColumn(name = "id_genero"))
    private Set<Genero> genres;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "actor_pelicula", joinColumns = @JoinColumn(name = "id_pelicula"),
            inverseJoinColumns = @JoinColumn(name = "id_actor"))
    private Set<Actor> actors;

}

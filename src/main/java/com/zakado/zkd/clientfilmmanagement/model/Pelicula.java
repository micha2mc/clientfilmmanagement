package com.zakado.zkd.clientfilmmanagement.model;

import lombok.*;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pelicula {

    private Integer nid;

    private String title;
    private Integer year;
    private Integer duration;

    private String country;
    private String synopsis;
    private String image;

    private String youtubeTrailerId;

    private Set<Genero> genres;

    private List<Integer> idsGenero;

    private Set<Actor> actors;

    private List<Integer> idsActors;

}

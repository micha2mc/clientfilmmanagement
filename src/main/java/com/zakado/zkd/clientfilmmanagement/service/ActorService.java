package com.zakado.zkd.clientfilmmanagement.service;

import com.zakado.zkd.clientfilmmanagement.model.Actor;
import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActorService {
    Page<Actor> getAllActors(Pageable pageable);

    void saveActor(Actor actor);

    void deleteActor(Integer id);

    Actor findById(Integer id);
}

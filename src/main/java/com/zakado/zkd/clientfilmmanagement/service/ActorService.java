package com.zakado.zkd.clientfilmmanagement.service;

import com.zakado.zkd.clientfilmmanagement.model.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActorService {
    Page<Actor> getAllActors(Pageable pageable);

    List<Actor> getAllActors();

    void saveActor(Actor actor);

    Actor getActorById(Integer id);
}

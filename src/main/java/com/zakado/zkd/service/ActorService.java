package com.zakado.zkd.service;

import com.zakado.zkd.model.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActorService {
    Page<Actor> getAllActors(Pageable pageable);

    List<Actor> getAllActors();

    void saveActor(Actor actor);

    Actor getActorById(Integer id);

    void deleteActor(Integer id);
}

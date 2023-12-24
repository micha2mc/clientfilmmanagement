package com.zakado.zkd.clientfilmmanagement.service.impl;

import com.zakado.zkd.clientfilmmanagement.model.Actor;
import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import com.zakado.zkd.clientfilmmanagement.repository.ActorRepo;
import com.zakado.zkd.clientfilmmanagement.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepo actorRepo;
    @Override
    public Page<Actor> getAllActors(Pageable pageable) {
        List<Actor> listActors = getAllActors();
        return getMoviesPagination(pageable, listActors);
    }
    @Override
    public List<Actor> getAllActors(){
        return actorRepo.findAll();
    }

    @Override
    public void saveActor(Actor actor) {
        actorRepo.save(actor);
    }

    @Override
    public Actor getActorById(Integer id) {
        return actorRepo.findById(id).orElse(new Actor());
    }

    private static PageImpl<Actor> getMoviesPagination(Pageable pageable, List<Actor> listActors) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Actor> list;
        if (listActors.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listActors.size());
            list = listActors.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), listActors.size());
    }
}

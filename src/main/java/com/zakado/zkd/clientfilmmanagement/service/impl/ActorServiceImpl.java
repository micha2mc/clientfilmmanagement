package com.zakado.zkd.clientfilmmanagement.service.impl;

import com.zakado.zkd.clientfilmmanagement.model.Actor;
import com.zakado.zkd.clientfilmmanagement.model.Genero;
import com.zakado.zkd.clientfilmmanagement.model.Pelicula;
import com.zakado.zkd.clientfilmmanagement.repository.ActorRepo;
import com.zakado.zkd.clientfilmmanagement.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private static final String URL = "http://localhost:8080/api/actors";
    private final ActorRepo actorRepo;
    private final RestTemplate template;
    @Override
    public Page<Actor> getAllActors(Pageable pageable) {
        List<Actor> listActors = Arrays.asList(Objects.requireNonNull(template.getForObject(URL, Actor[].class)));
        return getMoviesPagination(pageable, listActors);
    }

    @Override
    public void saveActor(Actor actor) {
        //actorRepo.save(actor);
        template.postForObject(URL, actor, Actor.class);
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

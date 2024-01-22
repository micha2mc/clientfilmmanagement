package com.zakado.zkd.clientfilmmanagement.service.impl;

import com.zakado.zkd.clientfilmmanagement.model.Actor;
import com.zakado.zkd.clientfilmmanagement.service.ActorService;
import com.zakado.zkd.clientfilmmanagement.service.UploadFileService;
import com.zakado.zkd.clientfilmmanagement.utils.UtilsManagement;
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
    private final RestTemplate template;
    private final UploadFileService uploadFileService;

    @Override
    public Page<Actor> getAllActors(Pageable pageable) {
        List<Actor> listActors = getAllActors();
        return UtilsManagement.getObjectsPagination(pageable, listActors);
    }

    @Override
    public List<Actor> getAllActors() {
        return Arrays.asList(Objects.requireNonNull(template.getForObject(URL, Actor[].class)));
    }

    @Override
    public void saveActor(Actor actor) {
        template.postForObject(URL, actor, Actor.class);
    }

    @Override
    public Actor getActorById(Integer id) {
        return template.getForObject(URL + "/" + id, Actor.class);
    }

    @Override
    public void deleteActor(Integer id) {
        Actor actor = getActorById(id);
        template.delete(URL + "/" + actor.getNid());
        uploadFileService.deleteImage(actor.getImage());
    }
}

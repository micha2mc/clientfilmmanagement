package com.zakado.zkd.clientfilmmanagement.service.impl;

import com.zakado.zkd.clientfilmmanagement.model.User;
import com.zakado.zkd.clientfilmmanagement.service.UserService;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String URL = "http://localhost:8080/api/users";

    private final RestTemplate template;

    @Override
    public Page<User> buscarTodos(Pageable pageable) {

        User[] users = template.getForObject(URL, User[].class);
        List<User> usuariosList = Arrays.asList(users);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> list;
        if (usuariosList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, usuariosList.size());
            list = usuariosList.subList(startItem, toIndex);
        }
        Page<User> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), usuariosList.size());
        return page;
    }

    @Override
    public User buscarUsuarioPorId(Integer idUsuario) {
        return template.getForObject(URL + "/" + idUsuario, User.class);
    }

    @Override
    public User buscarUsuarioPorNombre(String nombre) {
        return template.getForObject(URL + "/nombre/" + nombre, User.class);
    }

    @Override
    public User buscarUsuarioPorCorreo(String correo) {
        return template.getForObject(URL + "/correo/" + correo, User.class);
    }

    @Override
    public User login(String correo, String clave) {
        return template.getForObject(URL + "/login/" + correo + "/" + clave, User.class);
    }

    @Override
    public void guardarUsuario(User usuario) {
        if (usuario.getId() != null && usuario.getId() > 0) {
            template.put(URL, usuario);
        } else {
            //usuario.setId(0);
            template.postForObject(URL, usuario, String.class);
        }
    }

    @Override
    public void eliminarUsuario(Integer idUsuario) {
        template.delete(URL + "/" + idUsuario);
    }
}

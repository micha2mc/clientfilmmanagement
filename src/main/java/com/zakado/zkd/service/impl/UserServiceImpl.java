package com.zakado.zkd.service.impl;

import com.zakado.zkd.model.User;
import com.zakado.zkd.service.UserService;
import com.zakado.zkd.utils.UtilsManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String URL = "http://localhost:9003/api/users";

    private final RestTemplate template;

    @Override
    public Page<User> buscarTodos(Pageable pageable) {

        List<User> usuariosList = Arrays.asList(Objects.requireNonNull(template.getForObject(URL, User[].class)));
        return UtilsManagement.getObjectsPagination(pageable, usuariosList);
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
    public Page<User> buscarVariosPorNombre(Pageable pageable, String nombre) {
        List<User> usuariosList = Arrays.asList(Objects
                .requireNonNull(template.getForObject(URL + "/search/" + nombre, User[].class)));
        return UtilsManagement.getObjectsPagination(pageable, usuariosList);
    }

    @Override
    public Page<User> buscarUsuarioPorCorreo(Pageable pageable, String correo) {
        User user = buscarUsuarioPorCorreo(correo);
        return UtilsManagement.getObjectsPagination(pageable, List.of(Objects.requireNonNull(user)));
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
            template.postForObject(URL, usuario, String.class);
        }
    }

    @Override
    public void eliminarUsuario(Integer idUsuario) {
        template.delete(URL + "/" + idUsuario);
    }
}

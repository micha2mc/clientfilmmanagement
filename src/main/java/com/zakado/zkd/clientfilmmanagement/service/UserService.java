package com.zakado.zkd.clientfilmmanagement.service;

import com.zakado.zkd.clientfilmmanagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> buscarTodos(Pageable pageable);

    User buscarUsuarioPorId(Integer idUsuario);

    User buscarUsuarioPorNombre(String nombre);

    User buscarUsuarioPorCorreo(String correo);

    User login(String correo, String clave);

    void guardarUsuario(User usuario);

    void eliminarUsuario(Integer idUsuario);
}

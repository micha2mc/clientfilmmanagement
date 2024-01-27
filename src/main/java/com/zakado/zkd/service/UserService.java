package com.zakado.zkd.service;

import com.zakado.zkd.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> buscarTodos(Pageable pageable);

    User buscarUsuarioPorId(Integer idUsuario);

    User buscarUsuarioPorNombre(String nombre);

    Page<User> buscarVariosPorNombre(Pageable pageable, String nombre);

    Page<User>  buscarUsuarioPorCorreo(Pageable pageable, String correo);
    User  buscarUsuarioPorCorreo(String correo);

    User login(String correo, String clave);

    void guardarUsuario(User usuario);

    void eliminarUsuario(Integer idUsuario);
}

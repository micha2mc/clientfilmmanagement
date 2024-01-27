package com.zakado.zkd.service.impl;

import com.zakado.zkd.model.Rol;
import com.zakado.zkd.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RestTemplate template;

    private static final String URL = "http://localhost:8080/api/roles";

    @Override
    public List<Rol> buscarTodos() {
        return Arrays.asList(Objects.requireNonNull(template.getForObject(URL, Rol[].class)));
    }

    @Override
    public Rol buscarRolPorId(Integer idRol) {
        return template.getForObject(URL + "/" + idRol, Rol.class);
    }

    @Override
    public void guardarRol(Rol rol) {
        if (rol.getId() != null && rol.getId() > 0) {
            template.put(URL, rol);
        } else {
            rol.setId(0);
            template.postForObject(URL, rol, String.class);
        }
    }

    @Override
    public void eliminarRol(Integer idRol) {
        template.delete(URL + "/" + idRol);
    }
}

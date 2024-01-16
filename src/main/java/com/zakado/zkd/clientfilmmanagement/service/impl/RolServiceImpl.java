package com.zakado.zkd.clientfilmmanagement.service.impl;

import com.zakado.zkd.clientfilmmanagement.model.Rol;
import com.zakado.zkd.clientfilmmanagement.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RestTemplate template;

    String url = "http://localhost:8080/api/roles";
    @Override
    public List<Rol> buscarTodos() {
        Rol[] roles = template.getForObject(url, Rol[].class);
        return Arrays.asList(roles);
    }

    @Override
    public Rol buscarRolPorId(Integer idRol) {
        return template.getForObject(url+"/"+idRol, Rol.class);
    }

    @Override
    public void guardarRol(Rol rol) {
        if (rol.getId() != null && rol.getId() > 0) {
            template.put(url, rol);
        } else {
            rol.setId(0);
            template.postForObject(url, rol, String.class);
        }
    }

    @Override
    public void eliminarRol(Integer idRol) {
        template.delete(url + "/" + idRol);
    }
}

package com.zakado.zkd.clientfilmmanagement.service.impl;

import com.zakado.zkd.clientfilmmanagement.model.Reviews;
import com.zakado.zkd.clientfilmmanagement.model.User;
import com.zakado.zkd.clientfilmmanagement.service.ReviewsService;
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
public class ReviewsServiceImpl implements ReviewsService {

    private final RestTemplate template;
    private final UserService userService;
    String url = "http://localhost:8080/api/reviews";

    @Override
    public Page<Reviews> buscarTodas(Pageable pageable) {
        Reviews[] reviews = template.getForObject(url, Reviews[].class);
        List<Reviews> reviewsList = Arrays.asList(reviews);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Reviews> list;

        if (reviewsList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, reviewsList.size());
            list = reviewsList.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), reviewsList.size());
    }

    @Override
    public Page<Reviews> buscarCriticaPorIdUsuario(Integer idUsuario, Pageable pageable) {
        Reviews[] reviews = template.getForObject(url + "/curso/" + idUsuario, Reviews[].class);
        List<Reviews> reviewsList = Arrays.asList(reviews);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Reviews> list;

        if (reviewsList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, reviewsList.size());
            list = reviewsList.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), reviewsList.size());
    }

    @Override
    public Reviews buscarCriticaPorId(Integer idMatricula) {
        return template.getForObject(url + "/" + idMatricula, Reviews.class);
    }

    @Override
    public String guardarCritica(Reviews reviews) {
        //template.postForObject(url, reviews, String.class);
        /*if (reviews.getId() != null && reviews.getId() > 0) {
            //template.put(url, matricula); //peligroso, habría que comprobar la inscripción anterior
            return "No se puede modificar una matrícula.";
        } else {
            //Inscribimos al alumno en el curso
            User usuario = userService.buscarUsuarioPorId(reviews.getUser().getId());
            Alumno alumno = alumnosService.buscarAlumnoPorCorreo(usuario.getCorreo());
            Curso curso = cursosService.buscarCursoPorId(matricula.getIdCurso());
            String resultado="";
            //si no existe el alumno, lo creamos
            if(alumno == null) {
                alumno = new Alumno(usuario.getNombre(), usuario.getCorreo());
                alumnosService.guardarAlumno(alumno);
                resultado = "Alumno creado. ";
            } else { //si existe, comprobamos que no se matricula dos veces en el mismo curso
                resultado = "Alumno encontrado. ";
                List<Curso> cursos = alumno.getCursos();
                if (cursos.contains(curso)) {
                    return "El alumno ya existe en el curso!";
                }
            }
            alumnosService.inscribirCurso(alumno.getIdAlumno(), matricula.getIdCurso());
            //Guardamos la matrícula
            matricula.setPrecio(curso.getPrecio());
            matricula.setIdMatricula(0);
            matricula.setFecha(new Date());
            template.postForObject(url, reviews, String.class);
            return resultado + "Los datos de la matricula fueron guardados!";
        }*/
        return "Los datos de la matricula fueron guardados!";
    }

    @Override
    public void eliminarCritica(Integer idMatricula) {

    }
}

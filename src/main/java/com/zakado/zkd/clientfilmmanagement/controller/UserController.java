package com.zakado.zkd.clientfilmmanagement.controller;

import com.zakado.zkd.clientfilmmanagement.model.Rol;
import com.zakado.zkd.clientfilmmanagement.model.User;
import com.zakado.zkd.clientfilmmanagement.paginator.PageRender;
import com.zakado.zkd.clientfilmmanagement.service.RolService;
import com.zakado.zkd.clientfilmmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RolService rolService;


    @GetMapping("/listado")
    public String listadoUsuarios(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                  Principal principal) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<User> listado = userService.buscarTodos(pageable);
        PageRender<User> pageRender = new PageRender<>("/users/listado", listado);
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        model.addAttribute("username", usuario.getUsername());
        model.addAttribute("titulo", "Listado de todos los usuarios");
        model.addAttribute("listadoUsuarios", listado);
        model.addAttribute("page", pageRender);
        return "usuarios/listUsuario";
    }

    @GetMapping("/new")
    public String nuevo(Model model, Principal principal) {
        List<Rol> roles = rolService.buscarTodos();
        User usuario = userService.buscarUsuarioPorCorreo(principal.getName());
        model.addAttribute("username", usuario.getUsername());
        model.addAttribute("titulo", "Nuevo usuario");
        model.addAttribute("allRoles", roles);
        model.addAttribute("usuario", new User());
        return "usuarios/formUsuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(Model model, User usuario, RedirectAttributes attributes) {
        //si existe un usuario con el mismo correo no lo guardamos
        if (Objects.isNull(usuario.getId()) && userService.buscarUsuarioPorCorreo(usuario.getEmail()) != null) {
            attributes.addFlashAttribute("msga", "Error al guardar, ya existe el correo!");
            return "redirect:/users/listado";
        }
        List<Rol> roles = rolService.buscarTodos();
        model.addAttribute("allRoles", roles);
        userService.guardarUsuario(usuario);
        model.addAttribute("titulo", "Nuevo usuario");
        attributes.addFlashAttribute("msg", "Los datos del usuario fueron guardados!");
        return "redirect:/users/listado";
    }

    @GetMapping("/registrar")
    public String nuevoRegistro(Model model) {
        model.addAttribute("titulo", "Nuevo registro");
        model.addAttribute("usuario", new User());
        return "/registro";
    }

    @PostMapping("/registrar")
    public String registro(User usuario, RedirectAttributes attributes) {
        //si existe un usuario con el mismo correo no lo guardamos
        if (userService.buscarUsuarioPorCorreo(usuario.getEmail()) != null) {
            attributes.addFlashAttribute("msga", "Error al guardar, ya existe el correo!");
            return "redirect:/login";
        }
        usuario.setEnable(true);
        usuario.setRoles(List.of(rolService.buscarRolPorId(2)));
        userService.guardarUsuario(usuario);
        attributes.addFlashAttribute("msg", "Los datos del registro fueron guardados!");
        return "redirect:/login";
    }


    @GetMapping("/editar/{id}")
    public String editarUsuario(Model model, @PathVariable("id") Integer id) {
        User usuario = userService.buscarUsuarioPorId(id);
        List<Rol> roles = rolService.buscarTodos();
        model.addAttribute("titulo", "Editar usuario");
        model.addAttribute("usuario", usuario);
        model.addAttribute("allRoles", roles);
        return "usuarios/formUsuario";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarUsuario(@PathVariable("id") Integer id, Principal principal, RedirectAttributes attributes) {
        User usuario = userService.buscarUsuarioPorId(id);
        User user = userService.buscarUsuarioPorCorreo(principal.getName());

        if (usuario != null && !usuario.getEmail().equalsIgnoreCase(user.getEmail())) {
            userService.eliminarUsuario(id);
            attributes.addFlashAttribute("msg", "Los datos del usuario fueron borrados!");
        } else {
            attributes.addFlashAttribute("msg", "Usuario no encontrado o es el usuario actual!");
        }

        return "redirect:/users/listado";
    }
}

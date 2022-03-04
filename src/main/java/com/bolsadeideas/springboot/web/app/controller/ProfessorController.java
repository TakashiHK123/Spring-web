package com.bolsadeideas.springboot.web.app.controller;
import java.sql.SQLException;
import java.util.*;
import com.bolsadeideas.springboot.web.app.models.Profesor;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/profesores")
public class ProfessorController {

    @GetMapping("/listar")
    public String listaProfe(Model model) {
        ProfessorManager professorManager = new ProfessorManager();
        List<Profesor> profesores = new ArrayList<>();
        profesores = professorManager.getAllProfessor();
        model.addAttribute("titulo", "Lista de Profesores");
        model.addAttribute("mensaje", "Lista de profesores");
        model.addAttribute("idprofesor", "idProfesor");
        model.addAttribute("nombre", "Nombre");
        model.addAttribute("apellido", "Apellido");
        model.addAttribute("profesores", profesores);
        return "profesor-template/listarProfesores";
    }

    @GetMapping("/agregar")
    public String agregarProfe(Model model) {
        Profesor profesor = new Profesor();
        model.addAttribute("titulo", "Agregar profesor");
        model.addAttribute("profesor", profesor);
        model.addAttribute("error", new HashMap<>());
        return "profesor-template/agregar";
    }

    @PostMapping("/agregar")
    public String agregarProfeProc(@Valid Profesor profesor, BindingResult result, Model model,
                                 @RequestParam(name="nombre") String nombre,
                                 @RequestParam(name="apellido") String apellido) throws SQLException {
        model.addAttribute("titulo", "Falta datos");
        if(result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->{
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("error", errores);
            return "profesor-template/agregar";
        }
        ProfessorManager profesorManager = new ProfessorManager();
        profesor = profesorManager.add(nombre,apellido);
        model.addAttribute("idprofesor", "ID Profesor");
        model.addAttribute("nombre", "Nombre");
        model.addAttribute("apellido", "Apellido");
        model.addAttribute("titulo", "Profesor Agregado");
        model.addAttribute("profesor", profesor);
        return "profesor-template/resultadoProfe";
    }
    @GetMapping("/buscar")
    public String buscarProfe(Model model) {
        Profesor profesor = new Profesor();
        model.addAttribute("titulo", "Buscar Profesor");
        model.addAttribute("profesor", profesor);
        model.addAttribute("error", new HashMap<>());
        return "profesor-template/buscar";
    }

    @PostMapping("/buscar")
    public String buscarProfePro(@Valid Profesor profesor, BindingResult result, Model model,
                            @RequestParam(name= "idProfesor") int idProfesor) throws SQLException {

        if(result.hasGlobalErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->{
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("titulo", "Debe ser numero entero");
            model.addAttribute("error", errores);
            return "profesor-template/buscar";
        }
        ProfessorManager profesorManager = new ProfessorManager();
        profesor = profesorManager.getByid(idProfesor);
        model.addAttribute("idprofesor", "IdProfesor");
        model.addAttribute("nombre", "Nombre");
        model.addAttribute("apellido", "Apellido");
        model.addAttribute("titulo", "Profesor Encontrado");
        model.addAttribute("profesor", profesor);
        return "profesor-template/resultadoAlu";

    }
    @GetMapping("/modificar")
    public String modificar(Model model) {
        Profesor profesor = new Profesor();
        model.addAttribute("titulo", "Modificar Profesor");
        model.addAttribute("profesor", profesor);
        model.addAttribute("error", new HashMap<>());
        return "profesor-template/modificar";
    }

    @PostMapping("/modificar")
    public String modificar(@Valid Profesor profesor, BindingResult result, Model model,
                            @RequestParam(name="idProfesor") int idProfesor,
                            @RequestParam(name="nombre") String nombre,
                            @RequestParam String apellido) {
        model.addAttribute("titulo", "Falta datos");
        if(result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err ->{
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("titulo", "Modificar Profesor");
            model.addAttribute("error", errores);
            return "profesor-template/modificar";
        }
        ProfessorManager profesorManager = new ProfessorManager();
        profesorManager.modify(idProfesor, nombre, apellido);
        profesor.setidProfesor(idProfesor);
        profesor.setNombre(nombre);
        profesor.setApellido(apellido);
        model.addAttribute("idprofesor", "IdProfesor");
        model.addAttribute("nombre", "Nombre");
        model.addAttribute("apellido", "Apellido");
        model.addAttribute("profesor", profesor);
        return "profesor-template/resultadoAlu";
    }

}

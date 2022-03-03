package com.bolsadeideas.springboot.web.app.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bolsadeideas.springboot.web.app.models.Alumno;

import jakarta.validation.Valid;

@Controller
//@RequestMapping("/alumnos")
public class StudentController {
	
	@GetMapping({"/index","/","/home"})
	public String index(Model model) {
		model.addAttribute("titulo","Index"); 
		model.addAttribute("mensaje","Index");
		return "index";
	}
	
	
	@RequestMapping("/listarAlumno")
	public String listaAlu(Model model) {
		StudentManager studentManager = new StudentManager();
		List<Alumno> alumnos = new ArrayList<>();
		alumnos = studentManager.getAllStudents();
		model.addAttribute("titulo", "Lista de Alumnos");
		model.addAttribute("mensaje", "Lista de Alumnos");
		model.addAttribute("idalumno", "IdAlumno");
		model.addAttribute("nombre", "Nombre");
		model.addAttribute("apellido", "Apellido");
		model.addAttribute("alumnos", alumnos);
		return "listar";
	}
	
	@GetMapping("/agregarAlumno")
	public String formAlu(Model model) {
		Alumno alumno = new Alumno();
		model.addAttribute("titulo", "Agregar Alumno");
		model.addAttribute("alumno", alumno); 
		return "agregarAlumno"; 
	}
	
	@PostMapping("/agregarAlumno")
	public String agregarAlu(@Valid Alumno alumno, BindingResult result, Model model,
			@RequestParam(name="nombre") String nombre,
			@RequestParam String apellido) throws SQLException { 
		if(result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err ->{
				errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});

			model.addAttribute("titulo", "Agregado Alumno");
			model.addAttribute("error", errores); 
			return "agregarAlumno"; 
		}
		StudentManager studentManager = new StudentManager();
		alumno = studentManager.add(nombre, apellido);
		model.addAttribute("titulo", "Alumno Agregado"); 
		model.addAttribute("alumno", alumno); 
		return "resultadoAlu";
	}
	
	
	@GetMapping("/modificarAlumno")
	public String modificar(Model model) {
		Alumno alumno = new Alumno();
		model.addAttribute("titulo", "Modificar Alumno");
		model.addAttribute("alumno", alumno); 
		return "modificarAlumno"; 
	}
	
	@PostMapping("/modificarAlumno")
	public String modificar(@Valid Alumno alumno, BindingResult result, Model model,
			@RequestParam(name= "idAlumno") int idAlumno, 
			@RequestParam(name="nombre") String nombre,
			@RequestParam String apellido) { 
		if(result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err ->{
				errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			model.addAttribute("titulo", "Modificar Alumno");
			model.addAttribute("error", errores); 
			return "modificarAlumno"; 
		}
		StudentManager studentManager = new StudentManager();
		studentManager.modify(idAlumno, nombre, apellido); 
		alumno.setIdAlumno(idAlumno);
		alumno.setNombre(nombre);
		alumno.setApellido(apellido); 
		model.addAttribute("titulo", "Alumno Modificado"); 
		model.addAttribute("alumno", alumno); 
		return "resultadoAlu";
	}
	
	@GetMapping("/buscarAlumno")
	public String buscar(Model model) {
		Alumno alumno = new Alumno(); 
		model.addAttribute("titulo", "Buscar Alumno");
		model.addAttribute("alumno", alumno);
		return "buscarAlumno"; 
	}
	
	@PostMapping("/buscarAlumno")
	public String buscar(@Valid Alumno alumno, BindingResult result, Model model,
			@RequestParam(name= "idAlumno") int idAlumno) { 
		
		if(result.hasErrors()) {
			Map<String, String> errores = new HashMap<>();
			result.getFieldErrors().forEach(err ->{
				errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
			});
			StudentManager studentManager = new StudentManager();
			alumno = studentManager.getByid(idAlumno);  
			model.addAttribute("alumno", alumno);
			model.addAttribute("titulo", "Encontrado");
			model.addAttribute("error", errores); 
			return "resultadoAlu"; 
		}
		
		model.addAttribute("titulo", "Alumno Encontrado"); 
		model.addAttribute("alumno", alumno); 
		return "resultadoAlu";
		
	}
	
	
}

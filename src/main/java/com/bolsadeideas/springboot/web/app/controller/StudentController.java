package com.bolsadeideas.springboot.web.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bolsadeideas.springboot.web.app.models.Alumno;

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
	
	@GetMapping("/alumno"+"/agregarAlumno")
	public String formAlu(Model model) {
		
		model.addAttribute("titulo", "Agregar Alumnos"); 
		return "agregarAlumno"; 
	}
	
	@PostMapping("/alumno"+"/agregarAlumno")
	public String agregarAlu(Model model,
			@RequestParam(name="nombre") String nombre,
			@RequestParam String apellido) {
		StudentManager studentManager = new StudentManager();
		studentManager.add(nombre, apellido);  
		Alumno alumno = new Alumno(); 
		alumno.setNombre(nombre);
		alumno.setApellido(apellido); 
		model.addAttribute("titulo", "Alumno Agregado"); 
		model.addAttribute("alumno", alumno); 
		return "resultadoAlu";
	}
	
}

package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.AreaEntity;
import com.example.demo.entity.EmpleadoEntity;
import com.example.demo.repository.AreaRepository;
import com.example.demo.repository.EmpleadoRepository;

@Controller
public class EmpleadoController {

	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Autowired
	private AreaRepository areaRepository;
	
	@GetMapping({"/lst"})
    public String listarEmpleados(Model model) {
        List<EmpleadoEntity> empleados = empleadoRepository.findAll();
        model.addAttribute("empleados", empleados);
        return "lstEmpleados"; 
    }
	
	@GetMapping("/reg_emp")
	public String showRegistrarEmpleado(Model model) {
	    List<AreaEntity> areas = areaRepository.findAll();
	    model.addAttribute("areas", areas);
	    model.addAttribute("empleado", new EmpleadoEntity());
	    return "reg_emp";
	}


	@PostMapping("/reg_emp")
	public String registrarEmpleado(Model model, @ModelAttribute EmpleadoEntity empleado) {
	    empleadoRepository.save(empleado);
	    return "redirect:/lst"; 
	}
	
	
	@GetMapping("/eliminar_empleado/{dni}")
    public String eliminarEmpleado(@PathVariable("dni") String dni) {
        empleadoRepository.deleteById(dni);
        return "redirect:/lst";
    }
	
	@GetMapping("/edit/{dni}")
	public String showEditarEmpleado(@PathVariable("dni") String dni, Model model) {
	    Optional<EmpleadoEntity> optionalEmpleado = empleadoRepository.findById(dni);
	    if (optionalEmpleado.isPresent()) {
	        EmpleadoEntity empleado = optionalEmpleado.get();
	        List<AreaEntity> areas = areaRepository.findAll();
	        model.addAttribute("areas", areas);
	       
	        model.addAttribute("empleado", empleado);
	        return "edit";
	    } else {
	        return "redirect:/lst";
	    }
	}


    @PostMapping("/edit")
    public String editarEmpleado(@ModelAttribute EmpleadoEntity empleado) {
        empleadoRepository.save(empleado);
        return "redirect:/lst";
    }
	
}

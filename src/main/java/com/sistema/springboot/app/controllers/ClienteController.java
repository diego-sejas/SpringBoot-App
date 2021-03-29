package com.sistema.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.springboot.app.models.entity.Cliente;
import com.sistema.springboot.app.models.service.IClienteService;
import com.sistema.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	
	private IClienteService clienteService;
	
	@RequestMapping(value = "/listarCliente", method = RequestMethod.GET)
	public String listarCliente(@RequestParam(name = "page",defaultValue = "0") int page, Model model) {
		
		Pageable pageRequest = PageRequest.of(page, 5);
		
		Page <Cliente> clientes = clienteService.findAll(pageRequest); 
		
		PageRender<Cliente> pageRender = new PageRender<>("/listarCliente", clientes);
		
		model.addAttribute("titulo", "Listado de Clientes");	
		model.addAttribute("clientes",clientes);
		model.addAttribute("page",pageRender);
		
		return "listarCliente";
	}
	
	@RequestMapping(value = "/crearCliente", method = RequestMethod.GET)
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Registro de Cliente");
		return"crearCliente";
	}
	
	@RequestMapping(value = "/guardarCliente", method = RequestMethod.POST)
	public String crear(@Valid Cliente cliente, BindingResult result, Model model,RedirectAttributes flash,SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Registro de Clientes");
			return"crearCliente";
		}
		String mensajeflash= (cliente.getId()!=null)? "Cliente editado con exito!" :"Cliente creado con exito!";
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("succes", mensajeflash);
		return"redirect:listarCliente";
	}
	
	@RequestMapping(value = "/crearCliente/{id}")
	public String editar(@PathVariable(value = "id") Long id ,Map<String,Object> model , RedirectAttributes flash) {
		Cliente cliente = null;
		if(id>0) {
			cliente = clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la Base de Datos");
				return "redirect:/listarCliente";
			}
		}else{
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/listarCliente";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		return "crearCliente";
	}
	
	@RequestMapping(value = "/eliminarCliente/{id}")
	public String eliminar(@PathVariable(value ="id") Long id , RedirectAttributes flash) {
		if(id > 0) {
			clienteService.delete(id);
			flash.addFlashAttribute("succes", "Cliente eliminado con exito!");
		}
		return "redirect:/listarCliente";
	}
	
}


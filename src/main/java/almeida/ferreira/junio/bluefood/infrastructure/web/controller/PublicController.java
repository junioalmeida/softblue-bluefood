package almeida.ferreira.junio.bluefood.infrastructure.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import almeida.ferreira.junio.bluefood.application.CustumerService;
import almeida.ferreira.junio.bluefood.domain.custumer.Custumer;

@Controller
@RequestMapping(path = "/public")
public class PublicController {
	
	@Autowired
	CustumerService custumerService;
	
	@GetMapping(path = "/custumer/new")
	public String newCustumer(Model model) {
		
		model.addAttribute("custumer", new Custumer());
		ControllerHelper.saveEditMode(model, false);
		return "cliente-cadastro";
	}
	
	@PostMapping(path = "/custumer/save")
	public String saveCustumer(
			@ModelAttribute(name = "custumer") @Valid Custumer custumer,
			Errors erros,
			Model model) {
		
		if(!erros.hasErrors()) {
			custumerService.save(custumer);
			model.addAttribute("msg", "Cliente cadastrado com sucesso!");
		}
		
		ControllerHelper.saveEditMode(model, false);
		
		return "cliente-cadastro";
	}
}

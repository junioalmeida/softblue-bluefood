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

import almeida.ferreira.junio.bluefood.application.service.CustumerService;
import almeida.ferreira.junio.bluefood.application.service.RestaurantService;
import almeida.ferreira.junio.bluefood.application.service.ValidationException;
import almeida.ferreira.junio.bluefood.domain.custumer.Custumer;
import almeida.ferreira.junio.bluefood.domain.restaurant.Restaurant;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantCategoryRepository;

@Controller
@RequestMapping(path = "/public")
public class PublicController {
	
	@Autowired
	private CustumerService custumerService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private RestaurantCategoryRepository categoryRepository;
	
	@GetMapping(path = "/custumer/new")
	public String newCustumer(Model model) {
		
		model.addAttribute("custumer", new Custumer());
		ControllerHelper.saveEditMode(model, false);
		return "cliente-cadastro";
	}
		
	@PostMapping(path = "/custumer/save")
	public String saveCustumer(
			@ModelAttribute(name = "custumer") @Valid Custumer custumer,
			Errors errors,
			Model model) {
		
		if(!errors.hasErrors()) {
			try {
				custumerService.save(custumer);
				model.addAttribute("msg", "Cliente cadastrado com sucesso!");
			} catch (ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		
		ControllerHelper.saveEditMode(model, false);
		
		return "cliente-cadastro";
	}
	
	@GetMapping(path = "/restaurant/new")
	public String newRestaurant(Model model) {
		
		model.addAttribute("restaurant", new Restaurant());
		
		ControllerHelper.saveEditMode(model, false);
		ControllerHelper.addCategoriesToRequest(categoryRepository, model);
		
		return "restaurante-cadastro";
	}
	
	@PostMapping(path = "/restaurant/save")
	public String saveRestaurant(
			@ModelAttribute(name = "restaurant") @Valid Restaurant restaurant,
			Errors errors,
			Model model) {
		
		if(!errors.hasErrors()) {
			try {
				restaurantService.saveRestaurant(restaurant);
				model.addAttribute("msg", "Restaurante cadastrado com sucesso!");
			} catch (ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		
		ControllerHelper.saveEditMode(model, false);
		ControllerHelper.addCategoriesToRequest(categoryRepository, model);
		
		return "restaurante-cadastro";
	}
}

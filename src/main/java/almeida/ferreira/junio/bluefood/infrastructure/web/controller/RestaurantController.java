package almeida.ferreira.junio.bluefood.infrastructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import almeida.ferreira.junio.bluefood.application.service.ReportService;
import almeida.ferreira.junio.bluefood.application.service.RestaurantService;
import almeida.ferreira.junio.bluefood.application.service.ValidationException;
import almeida.ferreira.junio.bluefood.domain.order.Order;
import almeida.ferreira.junio.bluefood.domain.restaurant.Restaurant;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantCategoryRepository;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantRepository;
import almeida.ferreira.junio.bluefood.utils.SecurityUtils;

@Controller
@RequestMapping(path = "/restaurant")
public class RestaurantController {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private RestaurantCategoryRepository categoryRepository;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	
	@GetMapping(path = "/home")
	public String homeRestaurant(Model model) {
		
		List<Order> orders = reportService.listOrders(SecurityUtils.getLoggedRestaurant().getId(), null);
		model.addAttribute("orders", orders);

		return "restaurante-home";
	}
	
	@GetMapping(path = "/edit")
	public String editRestaurant(Model model) {
		
		Integer restaurantId = SecurityUtils.getLoggedRestaurant().getId();
		
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
		
		model.addAttribute("restaurant", restaurant);
		
		ControllerHelper.saveEditMode(model, true);
		ControllerHelper.addCategoriesToRequest(categoryRepository, model);
		
		return "restaurante-cadastro";
	}
	
	@PostMapping(path = "/save")
	public String saveRestaurant(
			@ModelAttribute(name = "restaurant") @Valid Restaurant restaurant,
			Errors errors,
			Model model) {
		
		if(!errors.hasErrors()) {
			try {
				restaurantService.saveRestaurant(restaurant);
				model.addAttribute("msg", "Restaurante alterado com sucesso!");
			} catch (ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		
		ControllerHelper.saveEditMode(model, true);
		ControllerHelper.addCategoriesToRequest(categoryRepository, model);
		
		return "restaurante-cadastro";
	}
}

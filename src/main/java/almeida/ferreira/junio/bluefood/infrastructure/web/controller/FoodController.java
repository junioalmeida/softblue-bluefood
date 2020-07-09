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
import org.springframework.web.bind.annotation.RequestParam;

import almeida.ferreira.junio.bluefood.application.service.MenuItemService;
import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItem;
import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItemRepository;
import almeida.ferreira.junio.bluefood.domain.restaurant.Restaurant;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantRepository;
import almeida.ferreira.junio.bluefood.utils.SecurityUtils;

@Controller
@RequestMapping(path = "/restaurant/foods")
public class FoodController {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Autowired
	private MenuItemService menuItemService;
	
	@GetMapping(path = "/")
	public String view(Model model) {
		
		Integer restaurantId = SecurityUtils.getLoggedRestaurant().getId();
		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("newFood", new MenuItem());
		
		List<MenuItem> itens = menuItemRepository.findByRestaurant_IdOrderByName(restaurantId);
		model.addAttribute("foods", itens);
		
		return "restaurante-comidas";
	}
	
	@PostMapping(path = "/add")
	public String add(
				@ModelAttribute("newFood") @Valid MenuItem item,
				Errors errors,
				Model model) {
		
		if(!errors.hasErrors()) {
		
			menuItemService.saveMenuItem(item);
			
			return "redirect:/restaurant/foods/";
		} else {
			
			Integer restaurantId = SecurityUtils.getLoggedRestaurant().getId();
			Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
			
			model.addAttribute("restaurant", restaurant);
			
			List<MenuItem> itens = menuItemRepository.findByRestaurant_IdOrderByName(restaurantId);
			model.addAttribute("foods", itens);
			
			return "restaurante-comidas";
			
		}
	}
	
	@GetMapping(path = "/remove")
	public String remove(@RequestParam("itemId") Integer itemId) {
		
		menuItemService.deleteMenuItem(itemId);
		return "redirect:/restaurant/foods/";
	}

}

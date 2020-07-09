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

import almeida.ferreira.junio.bluefood.application.service.CustumerService;
import almeida.ferreira.junio.bluefood.application.service.RestaurantService;
import almeida.ferreira.junio.bluefood.application.service.ValidationException;
import almeida.ferreira.junio.bluefood.domain.custumer.Custumer;
import almeida.ferreira.junio.bluefood.domain.custumer.CustumerRepository;
import almeida.ferreira.junio.bluefood.domain.order.Order;
import almeida.ferreira.junio.bluefood.domain.order.OrderRepository;
import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItem;
import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItemRepository;
import almeida.ferreira.junio.bluefood.domain.restaurant.Restaurant;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantCategoryRepository;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantRepository;
import almeida.ferreira.junio.bluefood.domain.restaurant.SearchFilter;
import almeida.ferreira.junio.bluefood.utils.SecurityUtils;
import almeida.ferreira.junio.bluefood.utils.StringUtils;

@Controller
@RequestMapping(path = "/custumer")
public class CustumerController {
	
	@Autowired
	private CustumerService custumerService;
		
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private CustumerRepository custumerRepositoy;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private RestaurantCategoryRepository categoryRepository;
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping(path = "/home")
	public String homeCustumer(Model model) {
		
		ControllerHelper.addCategoriesToRequest(categoryRepository, model);
		
		model.addAttribute("searchFilter", new SearchFilter());	
		
		List<Order> orders = orderRepository.findOrderByCustumerId(SecurityUtils.getLoggedCustumer().getId());
		
		model.addAttribute("orders", orders);
				
		return "cliente-home";
	}
	
	@GetMapping(path = "/edit")
	public String editCustumer(Model model) {
		
		Integer custumerID = SecurityUtils.getLoggedCustumer().getId();
		
		Custumer custumer = custumerRepositoy.findById(custumerID).orElseThrow();
		
		model.addAttribute("custumer", custumer);
		
		ControllerHelper.saveEditMode(model, true);
		
		return "cliente-cadastro";
	}
	
	@PostMapping(path = "/save")
	public String saveCustumer(
			@ModelAttribute(name = "custumer") @Valid Custumer custumer,
			Errors errors,
			Model model) {
		
		if(!errors.hasErrors()) {
			try {
				custumerService.save(custumer);
				model.addAttribute("msg", "Cliente alterado com sucesso!");
			} catch (ValidationException e) {
				errors.rejectValue("email", null, e.getMessage());
			}
		}
		
		ControllerHelper.saveEditMode(model, true);
		
		return "cliente-cadastro";
	}
	
	@GetMapping(path = "/search")
	public String searchRestaurant(Model model,
								@RequestParam(value = "cmd", required = false) String commad,
								@ModelAttribute(name = "searchFilter") SearchFilter search) {
		
		search.processFilter(commad);
		
		List<Restaurant> restaurants = restaurantService.searchRestaurant(search);
		
		ControllerHelper.addCategoriesToRequest(categoryRepository, model);
		model.addAttribute("searchFilter", search);		
		model.addAttribute("restaurants", restaurants);
		model.addAttribute("cep", SecurityUtils.getLoggedCustumer().getCep());
		
		if(restaurants == null || restaurants.size() == 0) {
			model.addAttribute("msg", "Não foi encontrado nenhum restaurante.");
		}
		
		return "cliente-busca";
	}
	
	@GetMapping(path = "/restaurant")
	public String viewRestaurant(Model model,
								@RequestParam("restaurantId") Integer id,
								@RequestParam(value = "category", required = false) String category) {
		
		Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
		model.addAttribute("restaurant", restaurant);
		
		List<String> categories = menuItemRepository.findCategories(id);
		model.addAttribute("cep", SecurityUtils.getLoggedCustumer().getCep());
		model.addAttribute("categories", categories);
		
		List<MenuItem> itensSpotlight;
		List<MenuItem> itensNotSpotlight;
		
		if(!StringUtils.isEmpty(category)) {
			itensSpotlight = menuItemRepository.findByRestaurant_IdAndSpotlightAndCategoryOrderByName(id, true, category);
			itensNotSpotlight = menuItemRepository.findByRestaurant_IdAndSpotlightAndCategoryOrderByName(id, false, category);
			model.addAttribute("selectedCategory", category);
		} else {
			itensSpotlight = menuItemRepository.findByRestaurant_IdAndSpotlightOrderByName(id, true);
			itensNotSpotlight = menuItemRepository.findByRestaurant_IdAndSpotlightOrderByName(id, false);
		}
		
		model.addAttribute("itensSpotlight", itensSpotlight);
		model.addAttribute("itensNotSpotlight", itensNotSpotlight);
		
		return "cliente-restaurante";
	}
}

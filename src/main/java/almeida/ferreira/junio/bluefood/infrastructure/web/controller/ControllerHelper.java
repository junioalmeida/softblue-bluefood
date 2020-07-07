package almeida.ferreira.junio.bluefood.infrastructure.web.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantCategory;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantCategoryRepository;

public class ControllerHelper {

	public static void saveEditMode(Model model, boolean isEdit) {
		model.addAttribute("isEditMode", isEdit);
	}
	
	public static void addCategoriesToRegistry(RestaurantCategoryRepository repository, Model model) {
		List<RestaurantCategory> categories = repository.findAll(Sort.by("name"));
		model.addAttribute("categories", categories);	
	}
}

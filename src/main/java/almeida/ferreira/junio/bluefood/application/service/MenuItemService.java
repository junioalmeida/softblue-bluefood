package almeida.ferreira.junio.bluefood.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItem;
import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItemRepository;

@Service
public class MenuItemService {
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Autowired
	private ImageService imageService;
	
	@Transactional
	public void saveMenuItem(MenuItem item) {
		
		item = menuItemRepository.save(item);
		item.setImageFileName();
		imageService.uploadFoods(item.getImageFile(), item.getImage());
	}
	
	@Transactional
	public void deleteMenuItem(Integer menuItemId) {
		
		MenuItem item = menuItemRepository.findById(menuItemId).orElseThrow();
		imageService.deleteFoods(item.getImage());
		menuItemRepository.deleteById(menuItemId);		
	}

}

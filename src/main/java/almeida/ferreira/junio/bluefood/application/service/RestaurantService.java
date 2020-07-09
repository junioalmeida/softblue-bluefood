package almeida.ferreira.junio.bluefood.application.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import almeida.ferreira.junio.bluefood.domain.custumer.Custumer;
import almeida.ferreira.junio.bluefood.domain.custumer.CustumerRepository;
import almeida.ferreira.junio.bluefood.domain.restaurant.Restaurant;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantComparator;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantRepository;
import almeida.ferreira.junio.bluefood.domain.restaurant.SearchFilter;
import almeida.ferreira.junio.bluefood.domain.restaurant.SearchFilter.SeachType;
import almeida.ferreira.junio.bluefood.utils.SecurityUtils;

@Service
public class RestaurantService {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private CustumerRepository custumerRepository;
	
	@Autowired
	private ImageService imageService;
	
	@Transactional
	public void saveRestaurant(Restaurant restaurant) throws ValidationException{
		
		if(!validateEmail(restaurant.getEmail(), restaurant.getId())) {
			throw new ValidationException("O email informado já está sendo utilizado.");
		}
		
		if(restaurant.getId() != null) {
			Restaurant restaurantDB = restaurantRepository.findById(restaurant.getId()).orElseThrow();
			restaurant.setPassword(restaurantDB.getPassword());
			restaurant.setLogotipo(restaurantDB.getLogotipo());
			restaurantRepository.save(restaurant);
		} else {
			restaurant.encryptPassword();
			Restaurant restaurantDB = restaurantRepository.save(restaurant);
			restaurantDB.setLogotipoFileName();
			imageService.uploadLogotipo(restaurantDB.getLogotipoFile(), restaurantDB.getLogotipo());
		}
	}
	
	public List<Restaurant> searchRestaurant(SearchFilter search){
		
		List<Restaurant> restaurants;
		
		if(search.getSearchType() == SeachType.TEXT) {
			restaurants = restaurantRepository.findByNameIgnoreCaseContaining(search.getText());
		} else if(search.getSearchType() == SeachType.CATEGORY) {
			restaurants = restaurantRepository.findByCategories_Id(search.getCategoryId());
		} else {
			throw new IllegalStateException("O tipo de pesquisa " + search.getSearchType() + " não é suportado.");
		}
		
		Iterator<Restaurant> it = restaurants.iterator();
		
		while(it.hasNext()) {
			
			Restaurant r = it.next();
			
			if(search.isFreeDelivery() && r.getDeliveryRate().doubleValue() > 0.0) {
				it.remove();
			}
		}
		
		RestaurantComparator comparator = new RestaurantComparator(search, SecurityUtils.getLoggedCustumer().getCep());
		restaurants.sort(comparator);
		
		return restaurants;
	}
	
	private boolean validateEmail(String email, Integer id) {
		
		Custumer custumer = custumerRepository.findByEmail(email);
		
		if(custumer != null) {
			return false;
		}
		
		Restaurant restaurant = restaurantRepository.findByEmail(email);
		
		if(restaurant != null) {
			if(id == null) {
				return false;
			}
			
			if(!restaurant.getId().equals(id)) {
				return false;
			}
		}
		
		return true;
	}
	
}
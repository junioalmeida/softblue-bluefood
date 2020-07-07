package almeida.ferreira.junio.bluefood.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import almeida.ferreira.junio.bluefood.domain.restaurant.Restaurant;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantRepository;

@Service
public class RestaurantService{
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	public void save(Restaurant restaurant) throws ValidationException{
		
		if(!validateEmail(restaurant.getEmail(), restaurant.getId())) {
			throw new ValidationException("O email informado j� est� sendo utilizado.");
		}
		
		if(restaurant.getId() != null) {
			Restaurant restaurantDB = restaurantRepository.findById(restaurant.getId()).orElseThrow();
			restaurant.setPassword(restaurantDB.getPassword());
		} else {
			restaurant.encryptPassword();
		}
		
		restaurantRepository.save(restaurant);
	}
	
	private boolean validateEmail(String email, Integer id) {
		
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
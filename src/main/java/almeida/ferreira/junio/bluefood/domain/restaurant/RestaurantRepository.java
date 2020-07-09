package almeida.ferreira.junio.bluefood.domain.restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>{
	
	public Restaurant findByEmail(String email);
	
	public List<Restaurant> findByNameIgnoreCaseContaining(String name);
	
	public List<Restaurant> findByCategories_Id(Integer categoryId);
}

package almeida.ferreira.junio.bluefood.domain.restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer>{	
	
	public List<MenuItem> findByRestaurant_IdOrderByName(Integer restaurantId);
	
	public List<MenuItem> findByRestaurant_IdAndSpotlightOrderByName(Integer restaurantId, boolean spotlight);
	
	public List<MenuItem> findByRestaurant_IdAndSpotlightAndCategoryOrderByName(Integer restaurantId, boolean spotlight, String category);
	
	@Query("SELECT DISTINCT mi.category FROM MenuItem mi WHERE mi.restaurant.id = ?1 ORDER BY mi.category")
	public List<String> findCategories(Integer restaurantId);
}

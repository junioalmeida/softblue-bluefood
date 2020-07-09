package almeida.ferreira.junio.bluefood.domain.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	
	@Query("SELECT o FROM Order o WHERE o.custumer.id = ?1 ORDER BY o.date DESC")
	public List<Order> findOrderByCustumerId(Integer id);
	
	public List<Order> findByRestaurant_IdOrderByDateDesc(Integer id);

	public List<Order> findByIdAndRestaurant_Id(Integer orderId, Integer restaurant_Id);

	@Query("SELECT o FROM Order o WHERE o.restaurant.id = ?1 AND o.date BETWEEN ?2 AND ?3 ORDER BY o.date DESC")
	public List<Order> findOrderInterval(Integer restaurantId, LocalDateTime start, LocalDateTime end);
	
	@Query("SELECT i.item.name, SUM(i.amount), SUM(i.price * i.amount) "
		+ "FROM Order o INNER JOIN o.itens i "
		+ "WHERE o.restaurant.id = ?1 AND i.item.id = ?2 AND o.date BETWEEN ?3 AND ?4 "
		+ "GROUP BY i.item.name")
	public List<Object[]> findItensForBilling(Integer restaurantId, Integer itemId, LocalDateTime start, LocalDateTime end);
	
	@Query("SELECT i.item.name, SUM(i.amount), SUM(i.price * i.amount) "
			+ "FROM Order o INNER JOIN o.itens i "
			+ "WHERE o.restaurant.id = ?1 AND o.date BETWEEN ?2 AND ?3 "
			+ "GROUP BY i.item.name")
	public List<Object[]> findItensForBilling(Integer restaurantId, LocalDateTime start, LocalDateTime end);
}

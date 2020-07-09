package almeida.ferreira.junio.bluefood.application.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import almeida.ferreira.junio.bluefood.domain.custumer.Custumer;
import almeida.ferreira.junio.bluefood.domain.custumer.CustumerRepository;
import almeida.ferreira.junio.bluefood.domain.order.Order;
import almeida.ferreira.junio.bluefood.domain.order.Order.Status;
import almeida.ferreira.junio.bluefood.domain.order.OrderRepository;
import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItem;
import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItemRepository;
import almeida.ferreira.junio.bluefood.domain.restaurant.Restaurant;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantCategory;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantCategoryRepository;
import almeida.ferreira.junio.bluefood.domain.restaurant.RestaurantRepository;
import almeida.ferreira.junio.bluefood.utils.StringUtils;

@Component
public class FillElementsForTesting {
	
	@Autowired
	private CustumerRepository custumerRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private RestaurantCategoryRepository categoryRepository;
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Custumer[] custumers = custumer();
		Restaurant[] restaurants = restaurant();
		itensCardapio(restaurants);
		
		Order o = new Order();
		o.setDate(LocalDateTime.now());
		o.setCustumer(custumers[0]);
		o.setRestaurant(restaurants[0]);
		o.setDeliveryRate(BigDecimal.valueOf(3.5));
		o.setSubtotal(BigDecimal.valueOf(54));
		o.setStatus(Status.ENTREGA);
		o.setTotal(BigDecimal.valueOf(57.5));
		o.setId(1);
		orderRepository.save(o);
	}
	
	private Custumer[] custumer() {
		
		List<Custumer> custumers = new ArrayList<>(); 
		
		Custumer c = new Custumer();
		c.setName("João Silva");
		c.setEmail("joao@bluefood.com.br");
		c.setPassword(StringUtils.encrypt("c"));
		c.setCep("89300100");
		c.setCpf("03099887666");
		c.setPhone("99355430001");
		custumers.add(c);
		custumerRepository.save(c);
		
		c = new Custumer();
		c.setName("Maria Torres");
		c.setEmail("maria@bluefood.com.br");
		c.setPassword(StringUtils.encrypt("c"));
		c.setCep("89300101");
		c.setCpf("03099887677");
		c.setPhone("99355430002");
		custumers.add(c);
		custumerRepository.save(c);
		
		Custumer[] array = new Custumer[custumers.size()]; 
		return custumers.toArray(array);
	}
	
	private Restaurant[] restaurant() {
		
		List<Restaurant> restaurants = new ArrayList<>(); 
		
		RestaurantCategory categoriaPizza = categoryRepository.findById(1).orElseThrow(NoSuchElementException::new);
		RestaurantCategory categoriaSanduiche = categoryRepository.findById(2).orElseThrow(NoSuchElementException::new);
		RestaurantCategory categoriaSobremesa = categoryRepository.findById(5).orElseThrow(NoSuchElementException::new);
		RestaurantCategory categoriaJapones = categoryRepository.findById(6).orElseThrow(NoSuchElementException::new);
		
		Restaurant r = new Restaurant();
		r.setName("Bubger King");
		r.setEmail("r1@bluefood.com.br");
		r.setPassword(StringUtils.encrypt("r"));
		r.setCnpj("00000000000101");
		r.setDeliveryRate(BigDecimal.valueOf(3.2));
		r.setPhone("99876671010");
		r.getCategories().add(categoriaSanduiche);
		r.getCategories().add(categoriaSobremesa);
		r.setLogotipo("0001-logo.png");
		r.setBaseDeliveryTime(30);
		restaurantRepository.save(r);
		restaurants.add(r);
		
		r = new Restaurant();
		r.setName("Mc Naldo's");
		r.setEmail("r2@bluefood.com.br");
		r.setPassword(StringUtils.encrypt("r"));
		r.setCnpj("00000000000102");
		r.setDeliveryRate(BigDecimal.valueOf(4.5));
		r.setPhone("99876671011");
		r.getCategories().add(categoriaSanduiche);
		r.getCategories().add(categoriaSobremesa);
		r.setLogotipo("0002-logo.png");
		r.setBaseDeliveryTime(25);
		restaurantRepository.save(r);
		restaurants.add(r);
		
		r = new Restaurant();
		r.setName("Sbubby");
		r.setEmail("r3@bluefood.com.br");
		r.setPassword(StringUtils.encrypt("r"));
		r.setCnpj("00000000000103");
		r.setDeliveryRate(BigDecimal.valueOf(12.2));
		r.setPhone("99876671012");
		r.getCategories().add(categoriaSanduiche);
		r.getCategories().add(categoriaSobremesa);
		r.setLogotipo("0003-logo.png");
		r.setBaseDeliveryTime(38);
		restaurantRepository.save(r);
		restaurants.add(r);
		
		r = new Restaurant();
		r.setName("Pizza Brut");
		r.setEmail("r4@bluefood.com.br");
		r.setPassword(StringUtils.encrypt("r"));
		r.setCnpj("00000000000104");
		r.setDeliveryRate(BigDecimal.valueOf(9.8));
		r.setPhone("99876671013");
		r.getCategories().add(categoriaPizza);
		r.getCategories().add(categoriaSobremesa);
		r.setLogotipo("0004-logo.png");
		r.setBaseDeliveryTime(22);
		restaurantRepository.save(r);
		restaurants.add(r);
		
		r = new Restaurant();
		r.setName("Wiki Japa");
		r.setEmail("r5@bluefood.com.br");
		r.setPassword(StringUtils.encrypt("r"));
		r.setCnpj("00000000000105");
		r.setDeliveryRate(BigDecimal.valueOf(14.9));
		r.setPhone("99876671014");
		r.getCategories().add(categoriaJapones);
		r.getCategories().add(categoriaSobremesa);
		r.setLogotipo("0005-logo.png");
		r.setBaseDeliveryTime(19);
		restaurantRepository.save(r);
		restaurants.add(r);
		
		Restaurant[] array = new Restaurant[restaurants.size()]; 
		return restaurants.toArray(array);
	}
	
	private void itensCardapio(Restaurant[] restaurants) {
		MenuItem ic = new MenuItem();
		ic.setCategory("Sanduíche");
		ic.setDescription("Delicioso sanduíche com molho");
		ic.setName("Double Cheese Burger Special");
		ic.setPrice(BigDecimal.valueOf(23.8));
		ic.setRestaurant(restaurants[0]);
		ic.setSpotlight(true);
		ic.setImage("0001-comida.png");
		menuItemRepository.save(ic);
		
		ic = new MenuItem();
		ic.setCategory("Sanduíche");
		ic.setDescription("Sanduíche padrão que mata a fome");
		ic.setName("Cheese Burger Simples");
		ic.setPrice(BigDecimal.valueOf(17.8));
		ic.setRestaurant(restaurants[0]);
		ic.setSpotlight(false);
		ic.setImage("0006-comida.png");
		menuItemRepository.save(ic);
		
		ic = new MenuItem();
		ic.setCategory("Sanduíche");
		ic.setDescription("Sanduíche natural com peito de peru");
		ic.setName("Sanduíche Natural da Casa");
		ic.setPrice(BigDecimal.valueOf(11.8));
		ic.setRestaurant(restaurants[0]);
		ic.setSpotlight(false);
		ic.setImage("0007-comida.png");
		menuItemRepository.save(ic);
		
		ic = new MenuItem();
		ic.setCategory("Bebida");
		ic.setDescription("Refrigerante com gás");
		ic.setName("Refrigerante Tradicional");
		ic.setPrice(BigDecimal.valueOf(9));
		ic.setRestaurant(restaurants[0]);
		ic.setSpotlight(false);
		ic.setImage("0004-comida.png");
		menuItemRepository.save(ic);
		
		ic = new MenuItem();
		ic.setCategory("Bebida");
		ic.setDescription("Suco natural e docinho");
		ic.setName("Suco de Laranja");
		ic.setPrice(BigDecimal.valueOf(9));
		ic.setRestaurant(restaurants[0]);
		ic.setSpotlight(false);
		ic.setImage("0005-comida.png");
		menuItemRepository.save(ic);
		
		ic = new MenuItem();
		ic.setCategory("Pizza");
		ic.setDescription("Pizza saborosa com cebola");
		ic.setName("Pizza de Calabresa");
		ic.setPrice(BigDecimal.valueOf(38.9));
		ic.setRestaurant(restaurants[3]);
		ic.setSpotlight(false);
		ic.setImage("0002-comida.png");
		menuItemRepository.save(ic);
		
		ic = new MenuItem();
		ic.setCategory("Japonesa");
		ic.setDescription("Delicioso Uramaki tradicional");
		ic.setName("Uramaki");
		ic.setPrice(BigDecimal.valueOf(16.8));
		ic.setRestaurant(restaurants[4]);
		ic.setSpotlight(false);
		ic.setImage("0003-comida.png");
		menuItemRepository.save(ic);
	}

}
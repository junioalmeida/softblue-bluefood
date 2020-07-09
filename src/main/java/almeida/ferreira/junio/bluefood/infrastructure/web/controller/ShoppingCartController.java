package almeida.ferreira.junio.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import almeida.ferreira.junio.bluefood.domain.order.DifferentRestaurantException;
import almeida.ferreira.junio.bluefood.domain.order.ItemOrder;
import almeida.ferreira.junio.bluefood.domain.order.Order;
import almeida.ferreira.junio.bluefood.domain.order.OrderRepository;
import almeida.ferreira.junio.bluefood.domain.order.ShoppingCart;
import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItem;
import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItemRepository;

@Controller
@RequestMapping(path = "/custumer/shopping-cart")
@SessionAttributes("shoppingCart")
public class ShoppingCartController {

	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@ModelAttribute("shoppingCart")
	public ShoppingCart getShoppingCart() {
		return new ShoppingCart();
	}
	
	@GetMapping(path = "/")
	public String viewCart() {
		return "cliente-carrinho";
	}
	
	@GetMapping(path = "/add")
	public String addItem(Model model,
						@RequestParam("itemId") Integer itemId,
						@RequestParam("amount") Integer amount,
						@RequestParam("notes") String notes,
						@ModelAttribute("shoppingCart") ShoppingCart cart) {
		
		MenuItem menuItem = menuItemRepository.findById(itemId).orElseThrow();
		
		try {
			cart.addItem(menuItem, amount, notes);
		} catch (DifferentRestaurantException e) {
			model.addAttribute("msg", "Não é possível misturar comidas de restaurantes diferentes.");
		}
		
		model.addAttribute("shoppingCart", cart);
		
		return "cliente-carrinho";
	}
	
	@GetMapping(path = "/remove")
	public String removeItem(Model model,
						@RequestParam("itemId") Integer itemId,
						@ModelAttribute("shoppingCart") ShoppingCart cart,
						SessionStatus session) {
		
		MenuItem itemRemove = menuItemRepository.findById(itemId).orElseThrow();
		
		cart.removeItem(itemRemove);
		
		if(cart.isEmpty()) {
			session.setComplete();
		}
		
		return "cliente-carrinho";
	}
	
	@GetMapping(path = "/repeat")
	public String repeat(
					@RequestParam("orderId") Integer orderId,
					@ModelAttribute("shoppingCart") ShoppingCart cart) {
		
		Order order = orderRepository.findById(orderId).orElseThrow();
		
		cart.clear();
		
		for (ItemOrder item : order.getItens()) {
			cart.addItem(item);
		}
		
		return "redirect:/custumer/shopping-cart/";
	}
}

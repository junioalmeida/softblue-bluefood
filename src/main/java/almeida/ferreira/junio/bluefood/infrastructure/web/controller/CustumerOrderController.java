package almeida.ferreira.junio.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import almeida.ferreira.junio.bluefood.domain.order.Order;
import almeida.ferreira.junio.bluefood.domain.order.OrderRepository;

@Controller
@RequestMapping(path = "/custumer/order")
@SessionAttributes("shoppingCart")
public class CustumerOrderController {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping(path = "/view")
	public String view(
					@RequestParam("orderId") Integer orderId, 
					Model model) {
		
		Order order = orderRepository.findById(orderId).orElseThrow();
		
		model.addAttribute("order", order);
		
		return "cliente-pedido";
	}
	
}	
package almeida.ferreira.junio.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import almeida.ferreira.junio.bluefood.application.service.OrderService;
import almeida.ferreira.junio.bluefood.domain.order.Order;
import almeida.ferreira.junio.bluefood.domain.order.Order.Status;
import almeida.ferreira.junio.bluefood.domain.order.OrderRepository;

@Controller
@RequestMapping(path = "/restaurant/order")
@SessionAttributes("shoppingCart")
public class RestaurantOrderController {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping(path = "/view")
	public String view(
					@RequestParam("orderId") Integer orderId, 
					Model model) {
		
		Order order = orderRepository.findById(orderId).orElseThrow();
		
		model.addAttribute("order", order);
		
		return "restaurante-pedido";
	}
	
	@PostMapping(path = "/next-status")
	public String nextStatus(
				@RequestParam("orderId") Integer orderId,
				Model model) {
		
		Order order = orderRepository.findById(orderId).orElseThrow();		
		Status status = order.getStatus();
		order.setNextStatus();
		
		if(!status.equals(order.getStatus())) {
			orderService.saveOrder(order);
			model.addAttribute("order", order);
			model.addAttribute("msg", "Status alterado com sucesso!");
			return "restaurante-pedido";
		} else {
			return "redirect:/restaurant/order/view" + "?orderId=" + orderId;
		}

		
		
	}
}	
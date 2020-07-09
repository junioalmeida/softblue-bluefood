package almeida.ferreira.junio.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import almeida.ferreira.junio.bluefood.application.service.OrderService;
import almeida.ferreira.junio.bluefood.application.service.PaymentException;
import almeida.ferreira.junio.bluefood.domain.order.Order;
import almeida.ferreira.junio.bluefood.domain.order.ShoppingCart;

@Controller
@RequestMapping(path = "/custumer/payment")
@SessionAttributes("shoppingCart")
public class PaymentController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping(path = "/pay")
	public String pay(@RequestParam("numberCreditCard") String numberCreditCard,
					  @ModelAttribute("shoppingCart") ShoppingCart cart,
					  SessionStatus session,
					  Model model) {
		
		try {
			Order order = orderService.createAndPay(cart, numberCreditCard);
			session.setComplete();
			return "redirect:/custumer/order/view?orderId=" + order.getId();
		} catch (PaymentException e) {
			
			if(e.getCause() == null) {
				model.addAttribute("msg", e.getMessage());
			} else {
				model.addAttribute("msg", "Não foi possível conectar ao serviço de pagamento.");
			}
			return "cliente-carrinho";
		}
	}
}	

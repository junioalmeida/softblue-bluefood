package almeida.ferreira.junio.bluefood.application.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import almeida.ferreira.junio.bluefood.domain.order.ItemOrder;
import almeida.ferreira.junio.bluefood.domain.order.ItemOrderPK;
import almeida.ferreira.junio.bluefood.domain.order.ItemOrderRepository;
import almeida.ferreira.junio.bluefood.domain.order.Order;
import almeida.ferreira.junio.bluefood.domain.order.Order.Status;
import almeida.ferreira.junio.bluefood.domain.order.OrderRepository;
import almeida.ferreira.junio.bluefood.domain.order.ShoppingCart;
import almeida.ferreira.junio.bluefood.domain.payment.CardData;
import almeida.ferreira.junio.bluefood.domain.payment.Payment;
import almeida.ferreira.junio.bluefood.domain.payment.PaymentRepository;
import almeida.ferreira.junio.bluefood.domain.payment.StatusPayment;
import almeida.ferreira.junio.bluefood.utils.SecurityUtils;
import almeida.ferreira.junio.bluefood.utils.StringUtils;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ItemOrderRepository itemOrderRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;

	@Value("${bluefood.sbpay.url}")
	private String sbpayUrl;

	@Value("${bluefood.sbpay.token}")
	private String sbpayToken;
	
	
	public void saveOrder(Order order) {
		orderRepository.save(order);
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = PaymentException.class)
	public Order createAndPay(ShoppingCart cart, String numberCreditCard) throws PaymentException {

		Order order = new Order();
		order.setDate(LocalDateTime.now());
		order.setCustumer(SecurityUtils.getLoggedCustumer());
		order.setRestaurant(cart.getRestaurant());
		order.setStatus(Status.PRODUCAO);
		order.setSubtotal(cart.calculateCart(false));
		order.setDeliveryRate(order.getRestaurant().getDeliveryRate());
		order.setTotal(cart.calculateCart(true));

		order = orderRepository.save(order);

		int sequence = 1;

		for (ItemOrder item : cart.getItens()) {
			item.setId(new ItemOrderPK(order, sequence++));
			itemOrderRepository.save(item);
		}

		CardData cardData = new CardData();
		cardData.setNumber(numberCreditCard);

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Token", sbpayToken);

		HttpEntity<CardData> requestEntity = new HttpEntity<>(cardData, headers);

		RestTemplate restTemplate = new RestTemplate();

		Map<String, String> response;

		try {
			response = restTemplate.postForObject(sbpayUrl, requestEntity, Map.class);
		} catch (Exception e) {
			throw new PaymentException(e);
		}

		String error = response.get("error");

		if (!StringUtils.isEmpty(error)) {
			throw new PaymentException(error);
		}

		StatusPayment status = StatusPayment.valueOf(response.get("status"));

		if (status != StatusPayment.AUTHORIZED) {
			throw new PaymentException(status.getDescription());
		}
		
		Payment payment = new Payment();
		payment.setDate(LocalDateTime.now());
		payment.setOrder(order);
		payment.setEndAndBrand(numberCreditCard);
		
		paymentRepository.save(payment);

		return order;

	}
}

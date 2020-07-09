package almeida.ferreira.junio.bluefood.application.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import almeida.ferreira.junio.bluefood.domain.order.Order;
import almeida.ferreira.junio.bluefood.domain.order.OrderRepository;
import almeida.ferreira.junio.bluefood.domain.order.ReportFilter;
import almeida.ferreira.junio.bluefood.domain.order.ReportItemBilling;

@Service
public class ReportService {

	@Autowired
	private OrderRepository orderRepository;
	

	public List<Order> listOrders(Integer restaurantId, ReportFilter filter) {

		if (filter == null) {	
			return orderRepository.findByRestaurant_IdOrderByDateDesc(restaurantId);
		}
			
		if (filter.getId() != null) {
			return orderRepository.findByIdAndRestaurant_Id(filter.getId(), restaurantId);
		} 
		
		LocalDate startDate = filter.getStartDate();
		LocalDate endDate = filter.getEndDate();
		
		if (startDate == null) {
			
			return List.of();
		} else if (endDate == null) {
			
			endDate = LocalDate.now();
		}

		return orderRepository.findOrderInterval(restaurantId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
	}

	public List<ReportItemBilling> listReportItens(Integer restaurantId, ReportFilter filter) {
		
		Integer itemId = filter.getId();
		
		List<Object[]> itensObj;
		
		LocalDate startDate = filter.getStartDate();
		LocalDate endDate = filter.getEndDate();
		
		if (startDate == null) {
			
			return List.of();
		} else if (endDate == null) {
			
			endDate = LocalDate.now();
		}
		
		if(itemId != 0) {
			itensObj = orderRepository.findItensForBilling(restaurantId, itemId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
		} else {
			itensObj = orderRepository.findItensForBilling(restaurantId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
		}
		
		List<ReportItemBilling> itensBilling = new ArrayList<>();
		
		for (Object[] objects : itensObj) {
			String name = (String) objects[0];
			Long amount = (Long) objects[1];
			BigDecimal total = (BigDecimal) objects[2];
			
			itensBilling.add(new ReportItemBilling(name, amount, total));
		}
		
		return itensBilling;
		
	}

}

package almeida.ferreira.junio.bluefood.infrastructure.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import almeida.ferreira.junio.bluefood.application.service.ReportService;
import almeida.ferreira.junio.bluefood.domain.order.Order;
import almeida.ferreira.junio.bluefood.domain.order.ReportFilter;
import almeida.ferreira.junio.bluefood.domain.order.ReportItemBilling;
import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItem;
import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItemRepository;
import almeida.ferreira.junio.bluefood.utils.SecurityUtils;

@Controller
@RequestMapping(path = "/restaurant/reports")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@GetMapping(path = "/orders")
	public String reportsOrders(
					@ModelAttribute("orderFilter") ReportFilter filter,
					Model model) {
		
		Integer restaurantId = SecurityUtils.getLoggedRestaurant().getId();
		List<Order> orders = reportService.listOrders(restaurantId, filter);
				
		model.addAttribute("orders", orders);
		model.addAttribute("orderFilter", filter);
		
		return "restaurante-relatorio-pedidos";
	}
	
	@GetMapping(path = "/itens")
	public String reportsItens(
					@ModelAttribute("orderFilter") ReportFilter filter,
					Model model) {
		
		Integer restaurantId = SecurityUtils.getLoggedRestaurant().getId();
		
		List<MenuItem> itens = menuItemRepository.findByRestaurant_IdOrderByName(restaurantId);
		model.addAttribute("itens", itens);
		
		List<ReportItemBilling> reportItens = reportService.listReportItens(restaurantId, filter);
		model.addAttribute("reportItens", reportItens);
		
		model.addAttribute("orderFilter", filter);
		
		return "restaurante-relatorio-itens";
	}
}

package almeida.ferreira.junio.bluefood.domain.restaurant;

import java.util.Comparator;

import almeida.ferreira.junio.bluefood.domain.restaurant.SearchFilter.Order;
import lombok.Data;
import lombok.NonNull;

@Data
public class RestaurantComparator implements Comparator<Restaurant> {

	@NonNull
	private SearchFilter filter;
	
	@NonNull
	private String cep;
	
	@Override
	public int compare(Restaurant o1, Restaurant o2) {
		
		int compare = 0;
		
		if(filter.getOrder() == Order.RATE) {
			compare = o1.getDeliveryRate().compareTo(o2.getDeliveryRate());
		} else if (filter.getOrder() == Order.TIME) {
			compare = o1.calculateDeliveryTime(cep).compareTo(o2.calculateDeliveryTime(cep));
		} else {
			throw new IllegalStateException("O tipo de ordenação " + filter.getOrder() + " não é suportado.");
		}
		
		return filter.isAsc() ? compare : -compare;
	}

}

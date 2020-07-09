package almeida.ferreira.junio.bluefood.domain.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import almeida.ferreira.junio.bluefood.domain.restaurant.MenuItem;
import almeida.ferreira.junio.bluefood.domain.restaurant.Restaurant;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class ShoppingCart implements Serializable {

	private List<ItemOrder> itens = new ArrayList<>();
	private Restaurant restaurant;

	public void addItem(MenuItem item, Integer amount, String notes) throws DifferentRestaurantException {

		if (itens.size() == 0) {
			restaurant = item.getRestaurant();
		} else if (!item.getRestaurant().equals(restaurant)) {
			throw new DifferentRestaurantException();
		}

		if (!existis(item)) {
			ItemOrder it = new ItemOrder();
			it.setItem(item);
			it.setAmount(amount);
			it.setNotes(notes);
			it.setPrice(item.getPrice());

			itens.add(it);
		}
	}
	
	public void addItem(ItemOrder item) {
		try {
			addItem(item.getItem(), item.getAmount(), item.getNotes());
		} catch (DifferentRestaurantException e) {
			throw new IllegalCallerException(e);
		}
	}

	public void removeItem(MenuItem menuItem) {

		for (Iterator<ItemOrder> it = itens.iterator(); it.hasNext();) {
			if (it.next().getItem().equals(menuItem)) {
				it.remove();
				break;
			}
		}
	}

	public BigDecimal calculateCart(boolean sumWithDeliveryRate) {
		
		BigDecimal total;
		
		if(sumWithDeliveryRate) {
			total = restaurant.getDeliveryRate();
		} else {
			total = BigDecimal.ZERO;
		}
		
		for (ItemOrder itemOrder : itens) {
			total = total.add(itemOrder.getTotalPrice());
		}
		
		return total;
	}
	
	public void clear() {
		itens.clear();
		restaurant = null;
	}
	
	public boolean isEmpty() {
		return itens.size() == 0;
	}
	
	private boolean existis(MenuItem menuItem) {
		for (ItemOrder itemOrder : itens) {
			if (itemOrder.getItem().equals(menuItem)) {
				return true;
			}
		}

		return false;
	}
}

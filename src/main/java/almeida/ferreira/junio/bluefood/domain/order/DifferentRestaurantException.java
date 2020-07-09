package almeida.ferreira.junio.bluefood.domain.order;

@SuppressWarnings("serial")
public class DifferentRestaurantException extends Exception {

	public DifferentRestaurantException() {
		super();
	}

	public DifferentRestaurantException(String message) {
		super(message);
	}

}

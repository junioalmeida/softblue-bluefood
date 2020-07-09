package almeida.ferreira.junio.bluefood.domain.restaurant;

import almeida.ferreira.junio.bluefood.utils.StringUtils;
import lombok.Data;

@Data
public class SearchFilter {

	public enum SeachType {
		TEXT, CATEGORY;
	}

	public enum Order {
		RATE, TIME;
	}

	public enum Command {
		FREE_DELIVERY, BEST_RATE, WORST_RATE, BEST_TIME, WORST_TIME;
	}

	private String text;
	private SeachType searchType;
	private Integer categoryId;
	private Order order = Order.RATE;
	private boolean asc;
	private boolean freeDelivery;
	private Command cmd;

	public void processFilter(String command) {

		if (!StringUtils.isEmpty(command)) {

			cmd = Command.valueOf(command);
			
			if (cmd == Command.FREE_DELIVERY) {
				freeDelivery = !freeDelivery;

			} else if (cmd == Command.BEST_RATE) {
				order = Order.RATE;
				asc = true;
			} else if (cmd == Command.WORST_RATE) {
				order = Order.RATE;
				asc = false;
			} else if (cmd == Command.BEST_TIME) {
				order = Order.TIME;
				asc = true;
			} else if (cmd == Command.WORST_TIME) {
				order = Order.TIME;
				asc = false;
			}
		}

		if (searchType == SeachType.TEXT) {
			categoryId = null;
		} else if (searchType == SeachType.CATEGORY) {
			text = null;
		}
	}

}

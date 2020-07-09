package almeida.ferreira.junio.bluefood.domain.order;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportItemBilling {
	
	private String name;
	private Long amount;
	private BigDecimal total;

}


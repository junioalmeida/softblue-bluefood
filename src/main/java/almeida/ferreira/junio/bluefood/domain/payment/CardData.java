package almeida.ferreira.junio.bluefood.domain.payment;

import javax.validation.constraints.Pattern;

public class CardData {

	@Pattern(regexp = "\\d{16}", message = "O cartão informado não é válido.")
	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}

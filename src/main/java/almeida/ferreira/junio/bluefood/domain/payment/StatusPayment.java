package almeida.ferreira.junio.bluefood.domain.payment;

public enum StatusPayment {
	AUTHORIZED("Autorizado"),
	NOT_AUTHORIZED("Não autorizado pela instituição financeira"),
	INVALID_CARD("Cartão inválido ou bloqueado");
	
	private String description;

	private StatusPayment(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}

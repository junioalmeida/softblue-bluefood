package almeida.ferreira.junio.bluefood.application.service;

@SuppressWarnings("serial")
public class PaymentException extends Exception {

	public PaymentException(String message) {
		super(message);
	}

	public PaymentException(Throwable cause) {
		super(cause);
	}

	public PaymentException(String message, Throwable cause) {
		super(message, cause);
	}
}

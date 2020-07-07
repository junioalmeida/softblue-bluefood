package almeida.ferreira.junio.bluefood.application;

@SuppressWarnings("serial")
public class ApplicationServiceException extends RuntimeException {

	public ApplicationServiceException(String message) {
		super(message);
	}

	public ApplicationServiceException(Throwable cause) {
		super(cause);
	}

	public ApplicationServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}

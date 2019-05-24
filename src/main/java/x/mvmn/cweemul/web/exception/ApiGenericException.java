package x.mvmn.cweemul.web.exception;

public class ApiGenericException extends RuntimeException {
	private static final long serialVersionUID = -7638708707020131912L;

	protected final int statusCode;

	public ApiGenericException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}

	public ApiGenericException(int statusCode, String message, Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}
}

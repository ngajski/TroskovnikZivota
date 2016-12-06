package jaxrs.resource;

/**
 * Class which extends {@linkplain RuntimeException}. This class is thrown when
 * any error occurs during one of {@link DAO} methods.
 *
 * @author Filip
 * @version 1.0
 */
public class DAOException extends RuntimeException {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The default constructor.
	 */
	public DAOException() {
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            message
	 * @param cause
	 *            cause
	 * @param enableSuppression
	 *            enable suppression
	 * @param writableStackTrace
	 *            writable stack trace
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            message
	 * @param cause
	 *            cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}
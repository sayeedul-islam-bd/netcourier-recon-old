package com.metafour.netcourier.recon.exception;

/**
 * Supplier invoice reconciliation related {@link Exception}.
 * 
 * @author Sayeedul Islam
 */
public class ReconcileInvoiceException extends Exception {
	
	private static final long serialVersionUID = -3387516993124229958L;
	
	/** Short code or id of the error, exception */
	private String code;

	public ReconcileInvoiceException() {
		super();
	}

	/**
	 * Constructs a new invoice reconciliation exception with the specified code and detail message. <br>
	 * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
	 * 
	 * @param code Error code, can be retrieved later by the {@link #getCode()} method
	 * @param message Detail message, can be retrieved later by the {@link #getMessage()} method.
	 */
	public ReconcileInvoiceException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ReconcileInvoiceException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	/**
	 * Returns the error code.
	 * 
	 * @return Error code
	 */
	public String getCode() {
		return code;
	}

}

package com.metafour.netcourier.recon.exception;

public class SupplierInvoiceFileException extends Exception{
	
	private static final long serialVersionUID = 3238165920422066743L;
	
	/** Short code or id of the error, exception */
	private String code;

	public SupplierInvoiceFileException() {
		super();
	}
	/**
	 * Constructs a new invoice reconciliation exception with the specified code and detail message. <br>
	 * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
	 * 
	 * @param code Error code, can be retrieved later by the {@link #getCode()} method
	 * @param message Detail message, can be retrieved later by the {@link #getMessage()} method.
	 */
	public SupplierInvoiceFileException(String code, String message) {
		super(message);
		this.code = code;
	}

	public SupplierInvoiceFileException(String code, String message, Throwable cause) {
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

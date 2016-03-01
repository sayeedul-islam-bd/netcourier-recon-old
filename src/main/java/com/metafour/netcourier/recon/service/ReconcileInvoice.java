/**
 * 
 */
package com.metafour.netcourier.recon.service;

import com.metafour.netcourier.recon.exception.ReconcileInvoiceException;

/**
 * @author Sayeedul
 */
public interface ReconcileInvoice {
	
	public String getView() throws ReconcileInvoiceException;
	
	public String uploadFiles() throws ReconcileInvoiceException;
	
}

/**
 * 
 */
package com.metafour.netcourier.recon.service;

import java.util.Locale;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.metafour.netcourier.model.PurchaseInvoice;
import com.metafour.netcourier.recon.exception.ReconcileInvoiceException;

/**
 * @author Sayeedul
 */
@Component
public interface SupplierInvoice {
	
	public String getSupplierInvoiceDetails(String id, ModelMap model);
	
	public Object saveSupplierInvoiceDetails(PurchaseInvoice purchaseInvoice, BindingResult bindingResult, Locale locale) throws ReconcileInvoiceException;
	
	public String uploadFiles() throws ReconcileInvoiceException;

	
}

/**
 * 
 */
package com.metafour.netcourier.recon.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.metafour.netcourier.model.JobCost;
import com.metafour.netcourier.model.PurchaseInvoice;
import com.metafour.netcourier.recon.exception.ReconcileInvoiceException;
import com.metafour.netcourier.recon.models.SupplierInvoiceLine;

/**
 * @author Sayeedul
 */
@Component
public interface SupplierInvoice {
	
	public String getSupplierInvoiceDetails(String id, ModelMap model);
	
	public Map<String, SupplierInvoiceLine> getJobsForReconciliation(PurchaseInvoice purchaseInvoice) throws ReconcileInvoiceException;

	public Map<String, SupplierInvoiceLine> getJobsForReconciliation(String supplierId, Date fromDate, Date toDate, Double surcharge) throws ReconcileInvoiceException;
	
	public Map<String, SupplierInvoiceLine> getReconciledJobs(PurchaseInvoice purchaseInvoice) throws ReconcileInvoiceException;

	public Map<String, SupplierInvoiceLine> getReconciledJobs(String purchaseInvoiceId) throws ReconcileInvoiceException;

	public JobCost getCostLineDetails(String costLineId, ModelMap model) throws ReconcileInvoiceException;
	
	public boolean reconcileInputFile (PurchaseInvoice pInvoice) throws ReconcileInvoiceException;
	
	public boolean reconcileCostLine(String costLineId, String purchaseInvoiceId, BindingResult errors, Locale locale)
			throws ReconcileInvoiceException;
	
	public boolean reconcileCostLine(String costLineId, PurchaseInvoice pInvoice, BindingResult errors, Locale locale)
			throws ReconcileInvoiceException;
	
	public boolean unReconcileCostLine (String costLineId) throws ReconcileInvoiceException;

	public String uploadFiles() throws ReconcileInvoiceException;
	
	public List<String> validateInputFile() throws ReconcileInvoiceException;
	
	public Object saveSupplierInvoiceDetails(PurchaseInvoice purchaseInvoice, BindingResult bindingResult, Locale locale) throws ReconcileInvoiceException;
	
	public Object saveCostLineDetails(JobCost jobCost, BindingResult bindingResult, Locale locale) throws ReconcileInvoiceException;
	

}

/**
 * 
 */
package com.metafour.netcourier.recon.service.impl;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.metafour.netcourier.model.PurchaseInvoice;
import com.metafour.netcourier.model.RecordStatus;
import com.metafour.netcourier.model.Supplier;
import com.metafour.netcourier.recon.exception.ReconcileInvoiceException;
import com.metafour.netcourier.recon.service.SupplierInvoice;
import com.metafour.netcourier.service.AppConfig;
import com.metafour.netcourier.service.NumberSeriesService;
import com.metafour.netcourier.validation.NCBeanValidator;
import com.metafour.orm.Session;
import com.metafour.orm.SessionFactory;
import com.metafour.util.StringsM4;

/**
 * @author Sayeedul
 */
@Service
public class SupplierInvoiceImpl implements SupplierInvoice {
	private static final Logger logger = LoggerFactory.getLogger(SupplierInvoiceImpl.class);

	private static final String SUPPLIERINVOICETEMPLATE = "invoicereconciliation";

	@Autowired AppConfig appConfig;
    @Autowired Validator validator;
	@Autowired SessionFactory sessionFactory;	
	@Autowired NumberSeriesService numberSeries;

	@Override
	public String uploadFiles() throws ReconcileInvoiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSupplierInvoiceDetails(String id, ModelMap model) {
		// load purchase invoice details
		PurchaseInvoice pi = null;		
		if (StringsM4.isNotBlank(id)) {
			pi = sessionFactory.getSession(PurchaseInvoice.class).getById(id);			
			if (pi != null && StringsM4.isNotBlank(pi.getSupplierId())) {
				Supplier su = sessionFactory.getSession(Supplier.class).getById(pi.getSupplierId());
				model.addAttribute("supplierCode", StringsM4.isBlank(su.getName()) ? su.getCode() : su.getCode() + " - " + su.getName());
			} else {
				model.addAttribute("invalidId", true);
			}
		}

		model.addAttribute("purchaseInvoice", pi == null ? new PurchaseInvoice() : pi);
		return SUPPLIERINVOICETEMPLATE;
	}

	@Override
	public Object saveSupplierInvoiceDetails(PurchaseInvoice purchaseInvoice, BindingResult errors, Locale locale) throws ReconcileInvoiceException {
		
		if (purchaseInvoice == null) {
			throw new ReconcileInvoiceException("Invalid data", "Requst object is null");
		}

		// sets properties which are auto, not come from server side
		if(StringsM4.isBlank(purchaseInvoice.getId())) purchaseInvoice.setId("TMP");		// if no id, set a temporary one
		purchaseInvoice.setCourierCode(appConfig.getCourierCode());

		new NCBeanValidator(appConfig.getCourierCode()).validate(purchaseInvoice, errors, validator);		
		if (errors.hasErrors()) {
			return errors;
		}

		Map<String, Object> responseBody = new LinkedHashMap<String, Object>();

		Session<PurchaseInvoice> session = sessionFactory.getSession(PurchaseInvoice.class);
		logger.debug("Purchase invoice submitted: " + purchaseInvoice);

		PurchaseInvoice pi = session.getById(purchaseInvoice.getId());        
		if (pi == null) { 	// new country
			purchaseInvoice.setCourierCode(appConfig.getCourierCode());		// just to double confirm, it should be set earlier
			purchaseInvoice.setStatus(RecordStatus.L);
			purchaseInvoice.setVersionNo(1);
			
			String nextNumber = numberSeries.getNextNumber(new PurchaseInvoice());
			if (StringsM4.isBlank(nextNumber))	{
				logger.error("Failed to add purchase invoice due to duplicate Id generated");
				errors.rejectValue("id", "numberseries.duplicateuniqueid.error");
				return errors;
			}
			purchaseInvoice.setId(nextNumber);
			session.add(purchaseInvoice);

		} else {	// update purchase invoice					
			session.bind(purchaseInvoice, purchaseInvoice.getId());
			purchaseInvoice.setVersionNo(pi.getVersionNo() + 1);						
			session.update(purchaseInvoice);
		}

		responseBody.put("redirect", "/supplierinvoice/"+ purchaseInvoice.getId());
		
		return responseBody;		
	}

}

/**
 * 
 */
package net.m4.netcourier.recon.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import com.metafour.netcourier.model.PurchaseInvoice;
import com.metafour.netcourier.recon.exception.ReconcileInvoiceException;
import com.metafour.netcourier.recon.models.SupplierInvoiceLine;
import com.metafour.netcourier.recon.service.SupplierInvoice;
import com.metafour.orm.SessionFactory;

import net.m4.netcourier.spring.MockJNDISupport;

/**
 * @author Sayeedul
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:netc-reconcile-test-context.xml")
public class ReconcileInvoiceServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(ReconcileInvoiceServiceTest.class);

	private static final String PIID = "10000275";
	
	@Autowired SessionFactory sessionFactory;
	@Autowired SupplierInvoice invoiceService;
	
	
	@BeforeClass
	public static final void intialSetup() throws IllegalStateException, NamingException {
		MockJNDISupport.initialize();
	}
	
	@Test
	public void testGetSupplierInvoiceDetails() {
		PurchaseInvoice pi = sessionFactory.getSession(PurchaseInvoice.class).getById(PIID);
		assertNotNull(pi);
		logger.debug(pi.toString());
	}
	
	@Test
	public void testGetReconciledJobs() throws ReconcileInvoiceException {
		Map<String, SupplierInvoiceLine> map = invoiceService.getReconciledJobs(PIID);
		assertNotNull(map);
		
		for (Map.Entry<String, SupplierInvoiceLine> e : map.entrySet()) {
			logger.info(e.getKey() + "=>" + e.getValue());
		}
	}
	
	@Test
	public void testGetJobsForReconciliation() throws ReconcileInvoiceException {
		PurchaseInvoice pi = sessionFactory.getSession(PurchaseInvoice.class).getById(PIID);
		
		Map<String, SupplierInvoiceLine> map = invoiceService.getJobsForReconciliation(pi);
		assertNotNull(map);
		
		for (Map.Entry<String, SupplierInvoiceLine> e : map.entrySet()) {
			logger.info(e.getKey() + "=>" + e.getValue());
		}
	}
	
	
	@Test
	public void testSaveSupplierInvoiceDetails() throws ReconcileInvoiceException {
		PurchaseInvoice pi = sessionFactory.getSession(PurchaseInvoice.class).getById(PIID);
		
		pi.setSupplierInvoiceNo("TEST001");
		
		/*Object obj = invoiceService.saveSupplierInvoiceDetails(pi, error, null);
		assertNotNull(obj);
		
		if (obj instanceof BindingResult) {
			
		}*/
	}
	
	@Test
	public void testDateAfter() {
		/*
		Calendar fCal = Calendar.getInstance();
		Calendar tCal = Calendar.getInstance();
		fCal.setTime(new Date(116, 2, 1));
		tCal.setTime(new Date(116, 2, 2));
		
		System.out.println(fCal.getTime());
		System.out.println(tCal.getTime());
		
		System.out.println(tCal.after(fCal));
		
		Date sdat = fCal.getTime();
		fCal.add(Calendar.DAY_OF_MONTH, 7);
		System.out.println(fCal.getTime());
		*/
		
		Map<String, SupplierInvoiceLine> notReconciledJobs = null;
		
		if (notReconciledJobs.isEmpty()) {
			System.out.println("empty");
		}
	}

}

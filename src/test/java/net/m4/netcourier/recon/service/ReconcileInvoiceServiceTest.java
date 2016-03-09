/**
 * 
 */
package net.m4.netcourier.recon.service;

import java.util.Calendar;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Sayeedul
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class ReconcileInvoiceServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(ReconcileInvoiceServiceTest.class);
//
//	@Autowired AppConfig appConfig;
//	@Autowired SessionFactory sessionFactory;
	
	/**
	 * @throws java.lang.Exception
	 */

//	@Test
//	public void testGetSupplierInvoiceDetails() {		
//		PurchaseInvoice pi = sessionFactory.getSession(PurchaseInvoice.class).getById("123456");
//		assertNotNull(pi);		
//	}
	
	@Test
	public void testDateAfter() {
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
	}

}

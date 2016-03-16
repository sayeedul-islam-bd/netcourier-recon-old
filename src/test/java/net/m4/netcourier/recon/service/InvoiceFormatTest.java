package net.m4.netcourier.recon.service;

import org.junit.Assert;
import org.junit.Test;

import com.metafour.netcourier.model.DataList;
import com.metafour.netcourier.recon.models.InvoiceFormat;

public class InvoiceFormatTest {

	@Test
	public void getListToFormat() {		
		
		DataList list = new DataList();
		list.setListValue2("AWB Number");
		list.setListValue3("DHL Product Description");
		list.setListValue4("Nett Charge");
		list.setListValue5("ROWS");
		list.setListValue6("COMMA");
		
		list.setListValue9("WEIGHT CHARGE:1~FUEL SURCHARGE:3~INSURANCE:2~DUTIES & TAXES PAID:4~REMOTE AREA DELIVERY:4~ADDRESS CORRECTION:4");
		
		InvoiceFormat format = new InvoiceFormat(list);
		System.out.println(format);
		System.out.println(list);
		System.out.println(format.getAsList());
		
		Assert.assertEquals(format.getAsList().toString().equals(list.toString()), true);
		
	}
	
}

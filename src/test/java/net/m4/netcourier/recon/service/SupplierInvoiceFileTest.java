package net.m4.netcourier.recon.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.metafour.netcourier.recon.SupplierInvoiceFile;
import com.metafour.netcourier.recon.SupplierInvoiceFile.CostInFile;
import com.metafour.netcourier.recon.SupplierInvoiceFile.Delimiter;
import com.metafour.netcourier.recon.SupplierInvoiceFile.LimitType;
import com.metafour.netcourier.recon.exception.SupplierInvoiceFileException;
import com.metafour.netcourier.recon.models.InvoiceFormat;
import com.metafour.netcourier.recon.models.InvoicePriceLine;

public class SupplierInvoiceFileTest {
	static String FEDEX_INVOICE_CSV = "BP_FedEx_151116.csv";
	static String DHL_INVOICE_CSV = "BNYM_DHL_LHR9092226.csv";
	
	@Test
	public void getDHLInvoiceHeadres() throws IOException {		
		String path = System.getProperty("user.dir") + "\\src\\test\\resources\\" +  DHL_INVOICE_CSV ;			
		List<String> headres = SupplierInvoiceFile.getCsvHeadres(path, Delimiter.COMMA);
		for (String header : headres) {
			System.out.println(header);
		}
		
	}
	
	@Test
	public void parseDHLInvoice() throws SupplierInvoiceFileException {		
		String path = System.getProperty("user.dir") + "\\src\\test\\resources\\" +  DHL_INVOICE_CSV;
		
		
		// load the csv format
		InvoiceFormat format = new InvoiceFormat();
		format.setCostInFile(CostInFile.In_Row);
		format.setAwbColumnName("AWB Number");
		format.setCostDescriptionColumnName("DHL Product Description");
		format.setAmountColumnName("Nett Charge");
		Map<String, String> valueMap = format.getValueMap();		
		valueMap.put("WEIGHT CHARGE", "1");
		valueMap.put("FUEL SURCHARGE", "3");
		valueMap.put("INSURANCE", "2");
		valueMap.put("DUTIES & TAXES PAID", "4");
		valueMap.put("REMOTE AREA DELIVERY", "4");
		valueMap.put("ADDRESS CORRECTION", "4");
				
		
		SupplierInvoiceFile invFile = new SupplierInvoiceFile(path, format);
		
		
		
		invFile.setMinLimit(BigDecimal.valueOf(0.50));
		invFile.setMaxLimit(BigDecimal.valueOf(0.50));
		invFile.setLimitType(LimitType.FIXED);
		
		List<InvoicePriceLine> priceLines = invFile.getInvoiceLines();
		
		for (InvoicePriceLine invLine : priceLines) {
			if(invLine.isMatched(BigDecimal.valueOf(13.00), "1")){
				invLine.setStatus(InvoicePriceLine.Status.MATCHED);
			}else{
				invLine.setStatus(InvoicePriceLine.Status.NOT_MATCHED);
			}
		}
		String csv = invFile.getStatusInCsv();
		
		System.out.println(csv);
		
	}
	
	
	@Test
	public void getFedexInvoiceHeadres() throws IOException {		
		String path = System.getProperty("user.dir") + "\\src\\test\\resources\\" +  FEDEX_INVOICE_CSV ;			
		List<String> headres = SupplierInvoiceFile.getCsvHeadres(path, Delimiter.COMMA);
		for (String header : headres) {
			System.out.println(header);
		}
		
	}
	
	@Test
	public void parseFedexInvoice() throws SupplierInvoiceFileException {		
		String path = System.getProperty("user.dir") + "\\src\\test\\resources\\" +  FEDEX_INVOICE_CSV ;
		
		
		// load the csv format
		InvoiceFormat format = new InvoiceFormat();
		format.setCostInFile(CostInFile.In_Column);
		format.setAwbColumnName("Tracking Number");
		
		Map<String, String> valueMap = format.getValueMap();		
		valueMap.put("Freight Amt", "1");
		valueMap.put("Vol Disc Amt", "1");
		valueMap.put("Fuel Amt", "3");
		valueMap.put("Misc 1 Amt", "4");
		
		SupplierInvoiceFile invFile = new SupplierInvoiceFile(path, format);				
		
		
		invFile.setMinLimit(BigDecimal.valueOf(0.50));
		invFile.setMaxLimit(BigDecimal.valueOf(0.50));
		invFile.setLimitType(LimitType.FIXED);
		
		List<InvoicePriceLine> priceLines = invFile.getInvoiceLines();
		
		for (InvoicePriceLine invLine : priceLines) {
			if(invLine.isMatched(BigDecimal.valueOf(15.14), "1")){
				invLine.setStatus(InvoicePriceLine.Status.MATCHED);
			}else{
				invLine.setStatus(InvoicePriceLine.Status.NOT_MATCHED);
			}
		}
		String csv = invFile.getStatusInCsv();
		
		System.out.println(csv);
	}
	
}

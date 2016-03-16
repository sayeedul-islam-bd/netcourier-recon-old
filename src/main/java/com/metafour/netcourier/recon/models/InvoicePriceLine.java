package com.metafour.netcourier.recon.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.metafour.netcourier.recon.SupplierInvoiceFile.LimitType;
import com.metafour.util.StringsM4;

public class InvoicePriceLine {
	
	public enum Status {
		MATCHED , NOT_MATCHED, NOT_FOUND, OK;
	}
	
	private String awbNumber;
	private String analysisCode;
	private List<CSVRecord> csvLine;
	private BigDecimal amount;
	private BigDecimal maxLimit;
	private BigDecimal minLimit;
	private LimitType limitType;
	private Status status;
	
	
	public boolean isMatched(BigDecimal amt, String analysisCode){
		if(StringsM4.isBlank(analysisCode) || StringsM4.isBlank(this.analysisCode)) return false;
		if(!this.analysisCode.equals(analysisCode)) return false;
		BigDecimal max = amt.add(maxLimit);
		BigDecimal min = amt.subtract(minLimit);
		if(amount == null) return false;;
		if(amount.doubleValue() >= min.doubleValue() && amount.doubleValue() <= max.doubleValue()){
			return true;
		}
		return false;
	}
	
	public InvoicePriceLine() {
		super();
		csvLine = new ArrayList<CSVRecord>();		
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getAwbNumber() {
		return awbNumber;
	}
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}
	public String getAnalysisCode() {
		return analysisCode;
	}
	public void setAnalysisCode(String analysisCode) {
		this.analysisCode = analysisCode;
	}
	public List<CSVRecord> getCsvLine() {
		return csvLine;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getMaxLimit() {
		return maxLimit;
	}
	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}
	public BigDecimal getMinLimit() {
		return minLimit;
	}
	public void setMinLimit(BigDecimal minLimit) {
		this.minLimit = minLimit;
	}
	public LimitType getLimitType() {
		return limitType;
	}
	public void setLimitType(LimitType limitType) {
		this.limitType = limitType;
	}
	
	
}

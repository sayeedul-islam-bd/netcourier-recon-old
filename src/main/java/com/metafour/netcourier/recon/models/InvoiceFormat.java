package com.metafour.netcourier.recon.models;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.metafour.netcourier.model.DataList;
import com.metafour.netcourier.recon.SupplierInvoiceFile;
import com.metafour.netcourier.recon.SupplierInvoiceFile.CostInFile;
import com.metafour.netcourier.recon.SupplierInvoiceFile.Delimiter;
import com.metafour.util.StringsM4;

public class InvoiceFormat {
	private CostInFile costInFile;
	private Delimiter delimiter;
	
	private String costDescriptionColumnName; 	// only needed for IN_Row type
	private String awbColumnName;
	private String amountColumnName;

	private Map<String,String> valueMap;
	
	public InvoiceFormat() {
		super();
		delimiter = Delimiter.COMMA;
		valueMap = new LinkedHashMap<String, String>();
	}
	
	public InvoiceFormat(DataList list) {
		super();
		delimiter = Delimiter.COMMA;
		valueMap = new LinkedHashMap<String, String>();
		
		awbColumnName = list.getListValue2();
		costDescriptionColumnName = list.getListValue3();
		amountColumnName = list.getListValue4();
		
		costInFile = ("ROWS".equalsIgnoreCase(list.getListValue5())) ? CostInFile.In_Row: CostInFile.In_Column;
		if("TILDE".equalsIgnoreCase(list.getListValue6())){
			delimiter = Delimiter.TILDE;
		}else if("TAB".equalsIgnoreCase(list.getListValue4())){
			delimiter = Delimiter.TAB;
		}else {
			delimiter = Delimiter.COMMA;
		}
		
		String mapString = list.getListValue9();
		if(StringsM4.isNotBlank(mapString)){
			String[] tokens = mapString.split("~");
			for (int i = 0; i < tokens.length; i++) {
				String[] token = tokens[i].split(":");
				if(token.length < 1) continue;
				String key = token[0];
				String value = (token.length > 1) ? token[token.length - 1]: "1" ;
				valueMap.put(key, value);
			}
		}
	}
	
	
	public CostInFile getCostInFile() {
		return costInFile;
	}

	public void setCostInFile(CostInFile costInFile) {
		this.costInFile = costInFile;
	}

	public Delimiter getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(Delimiter delimiter) {
		this.delimiter = delimiter;
	}

	public String getCostDescriptionColumnName() {
		return costDescriptionColumnName;
	}

	public void setCostDescriptionColumnName(String costDescriptionColumnName) {
		this.costDescriptionColumnName = costDescriptionColumnName;
	}

	public String getAwbColumnName() {
		return awbColumnName;
	}

	public void setAwbColumnName(String awbColumnName) {
		this.awbColumnName = awbColumnName;
	}

	public String getAmountColumnName() {
		return amountColumnName;
	}

	public void setAmountColumnName(String amountColumnName) {
		this.amountColumnName = amountColumnName;
	}

	public Map<String, String> getValueMap() {
		return valueMap;
	}	
	
	public DataList getAsList(){

		DataList list = new DataList();
		list.setListValue2(getAwbColumnName());
		list.setListValue3(getCostDescriptionColumnName());
		list.setListValue4(getAmountColumnName());
		list.setListValue5("COLUMNS");
		if(getCostInFile().equals(CostInFile.In_Row)){
			list.setListValue5("ROWS");
		}
		list.setListValue6("COMMA");
		if(getDelimiter().equals(Delimiter.TAB)) list.setListValue6("TAB");
		if(getDelimiter().equals(Delimiter.TILDE)) list.setListValue6("TILDE");
		
		StringBuffer mapString = new StringBuffer();		
		for (Map.Entry<String, String> entry : valueMap.entrySet()){
			if(mapString.length() > 0) mapString.append("~");
			mapString.append(entry.getKey() + ":" + entry.getValue());
		}		
		list.setListValue9(mapString.toString());
		
		return list;
	}
	
	@Override
	public String toString(){
		StringBuffer mapString = new StringBuffer();
		
		for (Map.Entry<String, String> entry : valueMap.entrySet()){
			if(mapString.length() > 0) mapString.append("~");
			mapString.append(entry.getKey() + ":" + entry.getValue());
		}
		
		return new StringBuilder(this.getClass().getName()).append("[")
					.append("awbColumnName=" + awbColumnName).append(",")
					.append("costDescriptionColumnName=" + costDescriptionColumnName).append(",")
					.append("amountColumnName=" + amountColumnName).append(",")
					.append("costInFile=" + costInFile.name()).append(",")
					.append("delimiter=" + delimiter.name()).append(",")
					.append("valueMap=" + mapString.toString())
					.append("]").toString();
	}
	
}

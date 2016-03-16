package com.metafour.netcourier.recon;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.metafour.netcourier.recon.exception.SupplierInvoiceFileException;
import com.metafour.netcourier.recon.models.InvoiceFormat;
import com.metafour.netcourier.recon.models.InvoicePriceLine;
import com.metafour.netcourier.recon.models.InvoicePriceLine.Status;
import com.metafour.util.StringsM4;

public class SupplierInvoiceFile {
	private static final Logger logger = LoggerFactory.getLogger(SupplierInvoiceFile.class);
	
	public static final String WEIGHT_CHARGE_DESCRIPTION = "WEIGHT CHARGE";
	
	public enum CostInFile {
		In_Column, In_Row;
	}

	public enum Delimiter {
		COMMA, TAB, TILDE;
	}

	public enum LimitType {
		PERCENTAGE, FIXED;
	}

	private String filePath;
	private Delimiter delimiter;
	private CostInFile costInFile;
	private String costDescriptionColumnName; 	// only needed for IN_Row type
	private String awbColumnName;
	private String amountColumnName;
	
	private Map<String,String> valueMap;
	
	private List<String> headres; 
	private List<String> originalHeadres; 
	
	private BigDecimal maxLimit;
	private BigDecimal minLimit;
	private LimitType limitType;

	private List<InvoicePriceLine> priceLines;
	private Map<String, InvoicePriceLine> mapPriceLines;
	
	/**
	 * 
	 * @param path
	 * @param format
	 */
	public SupplierInvoiceFile(String path, InvoiceFormat format ){		
		
		filePath = path;
		
		valueMap = format.getValueMap();
		awbColumnName = format.getAwbColumnName();
		costDescriptionColumnName = format.getCostDescriptionColumnName();
		amountColumnName = format.getAmountColumnName();
		delimiter = format.getDelimiter();
		costInFile = format.getCostInFile();
		
		maxLimit = BigDecimal.ZERO;
		minLimit = BigDecimal.ZERO;
		
		
	}
	
	
	/**
	public static SupplierInvoiceFile getInstanceForCostInColumn(String path, String awbColumnName){
		return SupplierInvoiceFile.getInstanceForCostInColumn(path, awbColumnName, Delimiter.COMMA, LimitType.FIXED); 
	}
	public static SupplierInvoiceFile getInstanceForCostInColumn(String path, String awbColumnName, Delimiter delimiter, LimitType limitType){
		
		SupplierInvoiceFile invFile = new SupplierInvoiceFile();
		invFile.setFilePath(path);
		invFile.setAwbColumnName(awbColumnName);
		invFile.setDelimiter(delimiter);
		invFile.setCostInFile(CostInFile.In_Column);
		invFile.setLimitType(limitType);
		
		return invFile;
	}
	public static SupplierInvoiceFile getInstanceForCostInRows(String path, String awbColumnName, String costDescriptionColumnName, String amountColumnName){
		return SupplierInvoiceFile.getInstanceForCostInRows(path, awbColumnName, costDescriptionColumnName, amountColumnName, Delimiter.COMMA, LimitType.FIXED);
	}
	
	public static SupplierInvoiceFile getInstanceForCostInRows(String path, String awbColumnName, String costDescriptionColumnName, String amountColumnName, Delimiter delimiter, LimitType limitType){
		SupplierInvoiceFile invFile = new SupplierInvoiceFile();
		invFile.setFilePath(path);
		invFile.setAwbColumnName(awbColumnName);
		invFile.setCostDescriptionColumnName(costDescriptionColumnName);
		invFile.setAmountColumnName(amountColumnName);
		invFile.setDelimiter(delimiter);
		invFile.setCostInFile(CostInFile.In_Row);		
		invFile.setLimitType(limitType);
		return invFile;
	}
	*/
	public static List<String> getCsvHeadres(String csvFilePath, Delimiter delimiter) throws IOException{
		char sep = ',';
		if (delimiter.equals(Delimiter.TAB)) {
			sep = '\t';
		} else if (delimiter.equals(Delimiter.TILDE)) {
			sep = '~';
		}
		Reader in = new FileReader(csvFilePath);
		
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader();	
		csvFileFormat.withRecordSeparator(sep);
		
		CSVParser  csvFileParser = new CSVParser(in, csvFileFormat);		
		Map<String, Integer> cHeadres = csvFileParser.getHeaderMap();
		List<String> csvHeadres = new ArrayList<String>();
		for (Map.Entry<String, Integer> entry : cHeadres.entrySet()){
			csvHeadres.add(entry.getKey());
		}
		csvFileParser.close();
		return csvHeadres;
	}
	
	
	public List<InvoicePriceLine> getInvoiceLines() throws SupplierInvoiceFileException {		
		if (priceLines == null) {
			priceLines = new ArrayList<>();
			mapPriceLines = new TreeMap<String, InvoicePriceLine>();
			// loads from file
			loadDataFromFile();
		}
		return priceLines;
	}

	private void loadDataFromFile() throws SupplierInvoiceFileException{
		char sep = ',';
		if (delimiter.equals(Delimiter.TAB)) {
			sep = '\t';
		} else if (delimiter.equals(Delimiter.TILDE)) {
			sep = '~';
		}

		Reader in;
		try {
			in = new FileReader(this.filePath);
			
			
			CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader();	
			csvFileFormat.withRecordSeparator(sep);
			
			CSVParser  csvFileParser = new CSVParser(in, csvFileFormat);
			
			Iterable<CSVRecord> records = csvFileParser.getRecords();
			Map<String, Integer> csvHeadres = csvFileParser.getHeaderMap();
			
			switch(costInFile){
				case In_Column:
					for (Map.Entry<String, Integer> entry : csvHeadres.entrySet()){
					    if(headres == null) headres = new ArrayList<String>();
					    if(originalHeadres == null) originalHeadres = new ArrayList<String>();		    
					    headres.add(entry.getKey());
					    originalHeadres.add(entry.getKey());
					}
					
					// process csv rows
					for (CSVRecord record : records) {
						String trackNo = record.get(getAwbColumnName());			
						for (Map.Entry<String, String> entry : valueMap.entrySet()){
							String amt = record.get(entry.getKey());
							String analysisCode = entry.getValue();						
							addPriceLine(trackNo, analysisCode, amt, record);
						}
					}
					
					break;
				case In_Row:
					for (Map.Entry<String, Integer> entry : csvHeadres.entrySet()){				    
					    if(originalHeadres == null) originalHeadres = new ArrayList<String>();
					    originalHeadres.add(entry.getKey());
					    //System.out.println(entry.getKey() + ":" + entry.getValue());
					}
					
					Map<String, String> mapTracking = new TreeMap<String, String>();
					Map<String, String> mapHeader = new TreeMap<String, String>();
					
					headres = new ArrayList<String>();
					
					headres.add(WEIGHT_CHARGE_DESCRIPTION);
					
					for (CSVRecord record : records) {					
						String trackingNo = record.get(getAwbColumnName());
						// set headers
						String header = record.get(getCostDescriptionColumnName());
						if(mapTracking.containsKey(trackingNo)){ // weight charge
							if(!mapHeader.containsKey(header)){ // weight charge
								headres.add(header);
								mapHeader.put(header, header);								
							}
						}else{
							header = WEIGHT_CHARGE_DESCRIPTION;
						}
						mapTracking.put(trackingNo, trackingNo);
						String amt = record.get(getAmountColumnName());
						if(valueMap.containsKey(header)){
							String code = valueMap.get(header);
							// add invoice line
							addPriceLine(trackingNo, code, amt, record);
						}
						
					}
					break;
				
			}
			csvFileParser.close();
			in.close();
		} catch (IOException e) {
			throw new SupplierInvoiceFileException("SUPF01", e.getMessage(), e);
		}
	}
	
	private void addPriceLine(String trackNo, String priceCode, String amt, CSVRecord record){
		
		InvoicePriceLine line ;
		String key = trackNo + "_" + priceCode;
		if(mapPriceLines.containsKey(key)){
			line = mapPriceLines.get(key);
		}else{
			line = new InvoicePriceLine();
			mapPriceLines.put(key, line);
			priceLines.add(line);
		}
		line.setAwbNumber(trackNo);
		line.setAnalysisCode(priceCode);
		if(StringsM4.isNotBlank(amt)){
			BigDecimal amount = new BigDecimal(amt);
			if(line.getAmount() != null) amount = amount.add(line.getAmount());		
			line.setAmount(amount);
		}
		line.setLimitType(getLimitType());
		line.setMaxLimit(getMaxLimit());
		line.setMinLimit(getMinLimit());
		line.getCsvLine().add(record);
	}
		
	public List<String> getHeaders(){
		try {
			getInvoiceLines();
		} catch (SupplierInvoiceFileException e) {			
			logger.error(e.getMessage(), e);;
		}
		return headres;
	}
	
	public List<String> getOriginalHeaders(){
		try {
			getInvoiceLines();
		} catch (SupplierInvoiceFileException e) {			
			logger.error(e.getMessage(), e);;
		}
		return originalHeadres;
	}
	
	
	public String getStatusInCsv(){
		StringBuffer csv = new StringBuffer();
		
		char sep = ',';
		if (delimiter.equals(Delimiter.TAB)) {
			sep = '\t';
		} else if (delimiter.equals(Delimiter.TILDE)) {
			sep = '~';
		}
		CSVFormat  csvFormater = CSVFormat.DEFAULT.withDelimiter(sep);
		
		try {
			List<InvoicePriceLine> invLines = getInvoiceLines();
			for (String header : originalHeadres) {
				if(csv.length() > 0) csv.append(sep);
				csv.append(csvFormater.format(header));
			}
			csv.append("\n");
			
			Map<Long, CSVRecord> mapLines = new TreeMap<Long, CSVRecord>();
			Map<Long, List<Status>> mapStatus = new TreeMap<Long, List<Status>>();
			for (InvoicePriceLine line : invLines) {
				List<CSVRecord> records = line.getCsvLine();
				List<Status> statusList ;
				for (CSVRecord record : records) {
					mapLines.put(record.getRecordNumber(), record);					
				}
				if(mapStatus.containsKey(records.get(0).getRecordNumber())){
					statusList = mapStatus.get(records.get(0).getRecordNumber());
				}else{
					statusList = new ArrayList<Status>();
				}
				statusList.add(line.getStatus());
				mapStatus.put(records.get(0).getRecordNumber(), statusList);
			}
			
			
			for (Map.Entry<Long, CSVRecord> entry : mapLines.entrySet()){
				CSVRecord record = entry.getValue();
				boolean isFirst = true;
				for (String header : originalHeadres) {
					if(!isFirst) csv.append(sep);
					String value = "";
					if(record.isSet(header)){
						value = record.get(header);
					}
					if(StringsM4.isNotBlank(value)){
						csv.append(csvFormater.format(value));
					}else{
						csv.append(value);
					}
					isFirst = false;					
				}
				
				
				// now add status
				List<Status> statusList = mapStatus.get(record.getRecordNumber());

				
				StringBuffer statText = new StringBuffer();
				for (Status status : statusList) {
					if(status == null) status = Status.NOT_FOUND;
					if(statText.length() > 0) statText.append(",");
					statText.append(status.name());					
				}				
				csv.append(csvFormater.format(statText));				
				csv.append("\n");
			}
			
			
		} catch (SupplierInvoiceFileException e) {
			logger.error(e.getMessage(), e);
		}
		
		return csv.toString();
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

	
	
	public String getAmountColumnName() {
		return amountColumnName;
	}

	public void setAmountColumnName(String amountColumnName) {
		this.amountColumnName = amountColumnName;
	}

	public void setLimitType(LimitType limitType) {
		this.limitType = limitType;
	}

	public String getAwbColumnName() {
		return awbColumnName;
	}

	public void setAwbColumnName(String awbColumn) {
		this.awbColumnName = awbColumn;
	}

	public Map<String, String> getValueMap() {
		return valueMap;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Delimiter getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(Delimiter delimiter) {
		this.delimiter = delimiter;
	}

	public CostInFile getCostInFile() {
		return costInFile;
	}

	public void setCostInFile(CostInFile costInFile) {
		this.costInFile = costInFile;
	}

	public String getCostDescriptionColumnName() {
		return costDescriptionColumnName;
	}

	public void setCostDescriptionColumnName(String costDescriptionColumnName) {
		this.costDescriptionColumnName = costDescriptionColumnName;
	}
	
	
	
}

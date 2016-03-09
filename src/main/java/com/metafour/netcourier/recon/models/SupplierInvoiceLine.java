package com.metafour.netcourier.recon.models;

import java.util.Date;

import com.metafour.netcourier.model.Client;

public class SupplierInvoiceLine {
	
	private String jobId;
	private String hawb;
	private String tref;
	
	private String pickupFrom;					// Country : Place - 
	private String deliveryTo;					// Country : Place
	
	private Client client;
	
	private Date bookingDate;
	
	private Double weight;						// CS.CWGT - chargeable weight
	private Double profit;
	private Double costBeforeSurcharge;		
	private Double totalCostInCourierCurrency;
	private Double totalCostInInvoiceCurrency;
	private Double salesPrice;
	

	public SupplierInvoiceLine() {	
		super();
	}

	public String getHawb() {
		return hawb;
	}

	public void setHawb(String hawb) {
		this.hawb = hawb;
	}

	public String getTref() {
		return tref;
	}
	
	public void setTref(String tref) {
		this.tref = tref;
	}

	public String getPickupFrom() {
		return pickupFrom;
	}

	public void setPickupFrom(String pickupFrom) {
		this.pickupFrom = pickupFrom;
	}

	public String getDeliveryTo() {
		return deliveryTo;
	}

	public void setDeliveryTo(String deliveryTo) {
		this.deliveryTo = deliveryTo;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Double getTotalCostInCourierCurrency() {
		return totalCostInCourierCurrency;
	}

	public void setTotalCostInCourierCurrency(Double totalCostInCourierCurrency) {
		this.totalCostInCourierCurrency = totalCostInCourierCurrency;
	}

	public Double getTotalCostInInvoiceCurrency() {
		return totalCostInInvoiceCurrency;
	}

	public void setTotalCostInInvoiceCurrency(Double totalCostInInvoiceCurrency) {
		this.totalCostInInvoiceCurrency = totalCostInInvoiceCurrency;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Double getCostBeforeSurcharge() {
		return costBeforeSurcharge;
	}

	public void setCostBeforeSurcharge(Double costBeforeSurcharge) {
		this.costBeforeSurcharge = costBeforeSurcharge;
	}

	@Override
	public String toString() {
		return "SupplierInvoiceLine [jobId=" + jobId + ", hawb=" + hawb + ", tref=" + tref + ", pickupFrom="
				+ pickupFrom + ", deliveryTo=" + deliveryTo + ", client=" + client + ", bookingDate=" + bookingDate
				+ ", weight=" + weight + ", profit=" + profit + ", surchargeCost=" + costBeforeSurcharge
				+ ", totalCostInCourierCurrency=" + totalCostInCourierCurrency + ", totalCostInInvoiceCurrency="
				+ totalCostInInvoiceCurrency + ", salesPrice=" + salesPrice + "]";
	}


}

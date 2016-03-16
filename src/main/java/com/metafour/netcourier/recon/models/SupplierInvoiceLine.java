package com.metafour.netcourier.recon.models;

import java.util.Date;

import com.metafour.netcourier.model.Client;

public class SupplierInvoiceLine {
	
	public String jobId;
	public String hawb;
	public String tref;
	
	public String pickupFrom;					// Country : Place - 
	public String deliveryTo;					// Country : Place
	
	public Client client;
	
	public Date bookingDate;
	
	public Double weight;						// CS.CWGT - chargeable weight
	public Double profit;
	public Double costBeforeSurcharge;
	public Double totalCostInCourierCurrency;
	public Double totalCostInSupplierCurrency;
	public Double salesPrice;
	
	private boolean isReconciled;

	public SupplierInvoiceLine() {	
		super();
	}

	public boolean getIsReconciled() {
		return isReconciled;
	}
	
	public boolean isReconciled() {
		return isReconciled;
	}

	public void setIsReconciled(boolean isReconciled) {
		this.isReconciled = isReconciled;
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

	public Double getTotalCostInSupplierCurrency() {
		return totalCostInSupplierCurrency;
	}

	public void setTotalCostInSupplierCurrency(Double totalCostInInvoiceCurrency) {
		this.totalCostInSupplierCurrency = totalCostInInvoiceCurrency;
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
				+ totalCostInSupplierCurrency + ", salesPrice=" + salesPrice + "]";
	}


}

package com.yong.vers;

public class PaymentInfo {
  
	String xref="";
	String frequency = "";
	String ongoing = "";
	String first = "";
  
	String recipient = "";
	String addr1 = "";
	String addr2 = "";
	String zip = "";
	String city = "";
	String state = "";
	
	public String getXref() {
		return xref;
	}
	public String getFrequency() {
		return frequency;
	}
	public String getOngoing() {
		return ongoing;
	}
	public String getFirst() {
		return first;
	}
	public String getRecipient() {
		return recipient;
	}
	public String getAddr1() {
		return addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public String getZip() {
		return zip;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public void setXref(String xref) {
		this.xref = xref;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public void setOngoing(String ongoing) {
		this.ongoing = ongoing;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
}

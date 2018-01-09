package com.ncd.xsx.Entity;

import java.sql.Timestamp;

public class Record {

	private Integer id;
	
	private java.sql.Timestamp testtime;
	
	private String sampleid;
	
	private String testtype;
	
	private String pihao;
	
	private String pinum;

	private String deviceid;
	
	private String tester;
	
	private String item;

	private String danwei;
	
	private String cankaozhi;
	
	private String value;

	private Boolean error;

	private Boolean printted;


	public Record() {

	}

	public Record(Timestamp testtime, String sampleid, String item, Boolean isprint) {
		super();
		this.testtime = testtime;
		this.sampleid = sampleid;
		this.item = item;
		this.printted = isprint;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public java.sql.Timestamp getTesttime() {
		return testtime;
	}

	public void setTesttime(java.sql.Timestamp testtime) {
		this.testtime = testtime;
	}

	public String getSampleid() {
		return sampleid;
	}

	public void setSampleid(String sampleid) {
		this.sampleid = sampleid;
	}

	public String getTesttype() {
		return testtype;
	}

	public void setTesttype(String testtype) {
		this.testtype = testtype;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	public String getCankaozhi() {
		return cankaozhi;
	}

	public void setCankaozhi(String cankaozhi) {
		this.cankaozhi = cankaozhi;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getTester() {
		return tester;
	}

	public void setTester(String tester) {
		this.tester = tester;
	}

	public Boolean getPrintted() {
		return printted;
	}

	public void setPrintted(Boolean printted) {
		this.printted = printted;
	}

	public String getPihao() {
		return pihao;
	}

	public void setPihao(String pihao) {
		this.pihao = pihao;
	}

	public String getPinum() {
		return pinum;
	}

	public void setPinum(String pinum) {
		this.pinum = pinum;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

}

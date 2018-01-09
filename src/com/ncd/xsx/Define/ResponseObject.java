package com.ncd.xsx.Define;

import java.util.List;

import com.ncd.xsx.Entity.Record;
import com.ncd.xsx.Entity.Report;

public class ResponseObject {

	private String methodName = null;									//request method name
	
	private String responseId = null;									//which ui will be freshed by request id , can be null
	
	private Long totalPageNum = null;
	
	private Integer currentNum = null;
	
	private List<Record> records = null;
	
	private List<Report> reports = null;
	
	private String result = null;

	public ResponseObject() {
		super();
	}
	
	public ResponseObject(String methodName, String responseId, Long totalPageNum, Integer currentNum,
			List<Record> records, List<Report> reports, String result) {
		super();
		this.methodName = methodName;
		this.responseId = responseId;
		this.totalPageNum = totalPageNum;
		this.currentNum = currentNum;
		this.records = records;
		this.reports = reports;
		this.result = result;
	}
	
	public void fillResponseObject(String methodName, String responseId, Long totalPageNum, Integer currentNum,
			List<Record> records, List<Report> reports, String result) {
		this.methodName = methodName;
		this.responseId = responseId;
		this.totalPageNum = totalPageNum;
		this.currentNum = currentNum;
		this.records = records;
		this.reports = reports;
		this.result = result;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public Long getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(Long totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public Integer getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(Integer currentNum) {
		this.currentNum = currentNum;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}

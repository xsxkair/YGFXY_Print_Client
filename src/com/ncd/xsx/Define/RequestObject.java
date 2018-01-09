package com.ncd.xsx.Define;

import com.ncd.xsx.Entity.Record;
import com.ncd.xsx.Entity.Report;

public class RequestObject {
	
	private String methodName = null;									//request method name
	
	private String requestId = null;									//which ui will be freshed by request id , can be null
	
	private Record record = null;								//true query record ,false query report
	
	private Report report = null;								//query data by id(record and report)

	private Long requestPageIndex = null;							//page size 50, current request page index

	public RequestObject() {
		super();
	}

	public RequestObject(String methodName, String requestId, Record record, Report report,
			Long requestPageIndex) {
		super();
		this.methodName = methodName;
		this.requestId = requestId;
		this.record = record;
		this.report = report;
		this.requestPageIndex = requestPageIndex;
	}
	
	public void fillReport(String methodName, String requestId, Record record, Report report,
			Long requestPageIndex) {
		this.methodName = methodName;
		this.requestId = requestId;
		this.record = record;
		this.report = report;
		this.requestPageIndex = requestPageIndex;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Long getRequestPageIndex() {
		return requestPageIndex;
	}

	public void setRequestPageIndex(Long requestPageIndex) {
		this.requestPageIndex = requestPageIndex;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

}

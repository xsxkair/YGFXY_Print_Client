package com.ncd.xsx.Define;

public class StringDefine {

	public static final String DeviceMessageStartStr = "AA";
	public static final String DeviceMessageEndStr = "BB";
	
	public static final String SuccessStr = "Success";
	public static final String FailStr = "Fail";
	
	public static final String ReportOutPutPdfFilePath = "temp.pdf";
	public static final String ReportMouldPdfFilePath = "mould.pdf";
	
	//client
	public static final String QueryRecordUrl = "QueryRecordAction";					//query record list
	public static final String QueryReportUrl = "QueryReportAction";					//query report list
	public static final String	 SaveReportUrl = "SaveReportAction";					//save one report	
	
	
	
	public static final String[][] ItemNameDefine = {
			{"NT-proBNP", "N-端脑利钠肽前体"}, 
			{"CK-MB", "肌酸激酶同工酶"}, 
			{"cTnI", "肌钙蛋白I"}, 
			{"Myo", "肌红蛋白"}, 
			{"D-Dimer", "D-二聚体"}, 
			{"CRP", "全程C-反应蛋白"}, 
			{"PCT", "降钙素原"}, 
			{"CysC", "胱抑素C"}, 
			{"β-HCG", "β-人绒毛膜促性腺激素"}, 
			{"NGAL", "中性粒细胞明胶酶相关脂质运载蛋白"}, 
	};
	
	public static String findItemChineseName(String itemEnName) {
		for (String[] strings : ItemNameDefine) {
			if(itemEnName.equals(strings[0]))
				return strings[1];
		}
		
		return null;
	}
}

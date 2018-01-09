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
			{"NT-proBNP", "N-����������ǰ��"}, 
			{"CK-MB", "���ἤøͬ��ø"}, 
			{"cTnI", "���Ƶ���I"}, 
			{"Myo", "���쵰��"}, 
			{"D-Dimer", "D-������"}, 
			{"CRP", "ȫ��C-��Ӧ����"}, 
			{"PCT", "������ԭ"}, 
			{"CysC", "������C"}, 
			{"��-HCG", "��-����ëĤ�����ټ���"}, 
			{"NGAL", "������ϸ������ø���֬�����ص���"}, 
	};
	
	public static String findItemChineseName(String itemEnName) {
		for (String[] strings : ItemNameDefine) {
			if(itemEnName.equals(strings[0]))
				return strings[1];
		}
		
		return null;
	}
}

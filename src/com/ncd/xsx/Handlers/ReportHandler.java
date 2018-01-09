package com.ncd.xsx.Handlers;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PageRanges;
import javax.print.attribute.standard.Sides;

import org.apache.fontbox.util.autodetect.FontFileFinder;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.tools.PrintPDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.ncd.xsx.Define.RequestObject;
import com.ncd.xsx.Define.ResponseObject;
import com.ncd.xsx.Define.StringDefine;
import com.ncd.xsx.Entity.Record;
import com.ncd.xsx.Entity.Report;
import com.ncd.xsx.Spring.ActivitySession;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

@Component
public class ReportHandler extends Activity {
	
	@FXML StackPane rootStackPane;
	
	@FXML JFXTextField nameTextField;
	@FXML JFXTextField sexTextField;
	@FXML JFXTextField ageTextField;
	@FXML JFXTextField sickIdTextField;
	@FXML JFXTextField departmentTextField;
	@FXML JFXTextField chuanghaoTextField;
	@FXML JFXTextField bloodTypeTextField;
	@FXML JFXTextField deviceTextField;
	@FXML JFXTextField barcodeTextField;
	@FXML JFXTextField sendDoctorTextField;
	@FXML JFXTextField sickdescTextField;
	@FXML JFXTextField gettimeTextField;
	
	@FXML JFXTextField descTextField;
	@FXML JFXTextField recvTimeTextField;
	@FXML JFXTextField reportTimeTextField;
	@FXML JFXTextField checkerTextField;
	@FXML JFXTextField recheckerTextField;
	
	@FXML Label itemNameLabel;
	@FXML Label itemNameSLabel;
	@FXML Label resultLabel;
	@FXML Label danweiLabel;
	@FXML Label normalLabel;
	
	@FXML JFXButton printfButton;
	@FXML JFXButton cancelButton;
	
	@FXML JFXDialog LogDialog;
	@FXML Label LogDialogHead;
	@FXML Label LogDialogContent;
	@FXML JFXButton acceptButton;
	
	
	private RequestObject requestObject = new RequestObject();
	private ListChangeListener<ResponseObject> myMessageListChangeListener = null;
	private Report currentReport = null;
	
	private static final String activityName = "NotPrintActivity";
	private static final String activitySaveReportUrl = "activitySaveReportUrl";
	
	@Autowired ActivitySession activitySession;
	
	@Override
	@PostConstruct
	public void onCreate() {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/com/ncd/xsx/Views/ReportPage.fxml"));
        InputStream in = this.getClass().getResourceAsStream("/com/ncd/xsx/Views/ReportPage.fxml");
        loader.setController(this);
        
        try {
        	setRootPane(loader.load(in));
        	in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        rootStackPane.getChildren().remove(LogDialog);
        acceptButton.setOnAction((e)->{
        	LogDialog.close();
        });
        
        myMessageListChangeListener = c ->{
        	while(c.next()){
				if(c.wasAdded()){
					for (ResponseObject responseObject : c.getAddedSubList()) {
						switch (responseObject.getResponseId()) {
							case activitySaveReportUrl:
								if(StringDefine.SuccessStr.equals(responseObject.getResult()))
									System.out.println("报告保存成功");
								else {
									System.out.println("报告保存失败");
								}
								break;
							
							default:
								break;
						}
					}
				}
			}
        };
        
        nameTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setName(newValue);
        });
        
		sexTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setSex(newValue);
        });
		ageTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setAge(newValue);
        });
		sickIdTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setSickId(newValue);
        });
		departmentTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setDepartment(newValue);
        });
		chuanghaoTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setChuanghao(newValue);
        });
		bloodTypeTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setBloodType(newValue);
        });
		deviceTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setDevice(newValue);
        });
		barcodeTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setBarcode(newValue);
        });
		sendDoctorTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setSendDoctor(newValue);
        });
		sickdescTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setSickdesc(newValue);
        });
		gettimeTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setGettime(newValue);
        });
		descTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setDesc(newValue);
        });
		recvTimeTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setRecvTime(newValue);
        });
		reportTimeTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setReportTime(newValue);
        });
		checkerTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setChecker(newValue);
        });
		recheckerTextField.textProperty().addListener((o, oldValue, newValue)->{
        	currentReport.setRechecker(newValue);
        });
		
		printfButton.setOnAction((e)->{
			if(currentReport == null || currentReport.getRecord() == null)
				return;
			else {
				try {
					makePdfFile();
				} catch (DocumentException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("创建检验报告失败");
					showLogDialog("error：创建检验报告失败", e1.getMessage());
				}

				try {
					printReport2();
				} catch (IOException | PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("打印检验报告失败");
					showLogDialog("error：打印检验报告失败", e1.getMessage());
				}
				
				this.saveOrUpdateReport();
			}
				
		});
		
		cancelButton.setOnAction((e)->{
			activitySession.backToFatherActivity();
		});
        
        AnchorPane.setTopAnchor(this.getRootPane(), 0.0);
        AnchorPane.setBottomAnchor(this.getRootPane(), 0.0);
        AnchorPane.setLeftAnchor(this.getRootPane(), 0.0);
        AnchorPane.setRightAnchor(this.getRootPane(), 0.0);
	}

	@Override
	public void onStart(Object object) {
		// TODO Auto-generated method stub
		super.onStart(object);
		
		if(object instanceof Record) {
			currentReport = new Report();
			currentReport.setRecord((Record) object);
		}
		else
			currentReport = (Report) object;
		
		getMyMessagesList().addListener(myMessageListChangeListener);

		showReport();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		getMyMessagesList().removeListener(myMessageListChangeListener);
	}
	
	private void showLogDialog(String head, String content) {
		LogDialogHead.setText(head);
		LogDialogContent.setText(content);
		LogDialog.show(rootStackPane);
	}
	
	private void showReport() {
		if(currentReport == null) {
			nameTextField.clear();
			sexTextField.clear();
			ageTextField.clear();
			sickIdTextField.clear();
			departmentTextField.clear();
			chuanghaoTextField.clear();
			bloodTypeTextField.clear();
			deviceTextField.clear();
			barcodeTextField.clear();

			sendDoctorTextField.clear();
			sickdescTextField.clear();
			gettimeTextField.clear();
			descTextField.clear();
			recvTimeTextField.clear();
			reportTimeTextField.clear();
			checkerTextField.clear();
			recheckerTextField.clear();
			
			itemNameLabel.setText(null);
			itemNameSLabel.setText(null);
			resultLabel.setText(null);
			danweiLabel.setText(null);
			normalLabel.setText(null);
			
			printfButton.setDisable(true);
		}
		else {
			nameTextField.setText(currentReport.getName());;
			sexTextField.setText(currentReport.getSex());
			ageTextField.setText(currentReport.getAge());
			sickIdTextField.setText(currentReport.getSickId());
			departmentTextField.setText(currentReport.getDepartment());
			chuanghaoTextField.setText(currentReport.getChuanghao());
			bloodTypeTextField.setText(currentReport.getBloodType());
			deviceTextField.setText(currentReport.getDevice());
			barcodeTextField.setText(currentReport.getBarcode());

			sendDoctorTextField.setText(currentReport.getSendDoctor());
			sickdescTextField.setText(currentReport.getSickdesc());
			gettimeTextField.setText(currentReport.getGettime());
			descTextField.setText(currentReport.getDesc());
			recvTimeTextField.setText(currentReport.getRecvTime());
			reportTimeTextField.setText(currentReport.getReportTime());
			checkerTextField.setText(currentReport.getChecker());
			recheckerTextField.setText(currentReport.getRechecker());
			
			String chineseName = StringDefine.findItemChineseName(currentReport.getRecord().getItem());
			if(chineseName != null)
				itemNameLabel.setText(chineseName);
			else
				itemNameLabel.setText(currentReport.getRecord().getItem());
			itemNameSLabel.setText(currentReport.getRecord().getItem());
			
			if(currentReport.getRecord().getError())
				resultLabel.setText("error");
			else
				resultLabel.setText(currentReport.getRecord().getValue());
			
			danweiLabel.setText(currentReport.getRecord().getDanwei());
			normalLabel.setText(currentReport.getRecord().getCankaozhi());
			
			printfButton.setDisable(false);
		}
	}
	
	private void makePdfFile() throws IOException, DocumentException { 
        FileOutputStream out = new FileOutputStream(StringDefine.ReportOutPutPdfFilePath);// 输出流  
        PdfReader reader = new PdfReader(StringDefine.ReportMouldPdfFilePath);// 读取pdf模板  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        PdfStamper stamper = new PdfStamper(reader, bos);  
        BaseFont bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); 
        
        AcroFields form = stamper.getAcroFields(); 
        
        java.util.Iterator<String> it = form.getFields().keySet().iterator();  
        while (it.hasNext()) {
        	String name = it.next().toString();
        	form.setFieldProperty(name, "textfont", bfChinese/* BaseFont.createFont() */, null);
        	
        	try {
            	Field field = Report.class.getDeclaredField(name);
            	field.setAccessible(true);

            	form.setField(name, (String) field.get(currentReport));  
			} catch (Exception e) {
				// TODO: handle exception
				continue;
			}
        }
        //测试信息
        form.setField("item", StringDefine.findItemChineseName(currentReport.getRecord().getItem()));
        form.setField("item_s", currentReport.getRecord().getItem());
        
        if(currentReport.getRecord().getError())
        	form.setField("value", "error");
		else
			form.setField("value", currentReport.getRecord().getValue());

        form.setField("danwei", currentReport.getRecord().getDanwei());
        form.setField("normal", currentReport.getRecord().getCankaozhi());
        
        stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true  
        stamper.close();  
  
        Document doc = new Document();  
        PdfCopy copy = new PdfCopy(doc, out);  
        doc.open();  
        PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);  
        copy.addPage(importPage);  
        doc.close();  
	}
	
	private void makePdfFile2() throws InvalidPasswordException, IOException { 

        // load the document
        PDDocument pdfDocument = PDDocument.load(new File(StringDefine.ReportMouldPdfFilePath));

        FontFileFinder fontFinder = new FontFileFinder();
        List<URI> fontURIs = fontFinder.find();

        File fontFile = null;

        for (URI uri : fontURIs) {
            File font = new File(uri);
            System.out.println(font.getName());
            if (font.getName().equals("simhei.ttf")) {
                fontFile = font;
            }
        }
        
        
        // get the document catalog
        PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
       // File fontFile = new File("simhei.ttf");
        // Add and set the resources and default appearance at the form level
        PDFont font = PDType0Font.load(pdfDocument, fontFile);
        PDResources res = new PDResources();
        COSName fontName = res.add(font);
        acroForm.setDefaultResources(res);
        String da = "/" + fontName.getName() + " 12 Tf 0 g";
        acroForm.setDefaultAppearance(da);
        // as there might not be an AcroForm entry a null check is necessary
        if (acroForm != null)
        {

        	 java.util.Iterator<PDField> it = acroForm.getFieldIterator();
        	 while (it.hasNext()) {
        		 PDTextField pdField = (PDTextField) it.next();

        		 String name = pdField.getPartialName(); 
             	try {
                 	Field field = Report.class.getDeclaredField(name);
                 	field.setAccessible(true);

                 	pdField.setValue((String) field.get(currentReport)); 
     			} catch (Exception e) {
     				// TODO: handle exception
     				continue;
     			}
             }

        	 //测试信息
        	 PDTextField pdField = (PDTextField) acroForm.getField( "item" );
        	 pdField.setValue(StringDefine.findItemChineseName(currentReport.getRecord().getItem()));
        	
        	 pdField = (PDTextField) acroForm.getField( "item_s" );
        	 pdField.setValue(currentReport.getRecord().getItem());
        	 
        	 pdField = (PDTextField) acroForm.getField( "value" );
        	 if(currentReport.getRecord().getError())
        		 pdField.setValue("error");
        	 else
        		 pdField.setValue(currentReport.getRecord().getValue());
        	 
        	 pdField = (PDTextField) acroForm.getField( "danwei" );
        	 pdField.setValue(currentReport.getRecord().getDanwei());
        	 
        	 pdField = (PDTextField) acroForm.getField( "normal" );
        	 pdField.setValue(currentReport.getRecord().getCankaozhi());
        }

        // Save and close the filled out form.
        pdfDocument.save(StringDefine.ReportOutPutPdfFilePath);
        pdfDocument.close();
	}
	
	private void printReport() throws PrinterException, IOException {
		PrintPDF.main(new String[]{
			       "-silentPrint",//静默打印
			       StringDefine.ReportOutPutPdfFilePath//打印PDF文档的路径
			    });

	}
	
	private void printReport2() throws InvalidPasswordException, IOException, PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();
		
		PDDocument document = PDDocument.load(new File(StringDefine.ReportOutPutPdfFilePath));
        job.setPageable(new PDFPageable(document));

        PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
        attr.add(new PageRanges(1, 1)); // pages 1 to 1
        
        PDViewerPreferences vp = document.getDocumentCatalog().getViewerPreferences();
        if (vp != null && vp.getDuplex() != null)
        {
            String dp = vp.getDuplex();
            if (PDViewerPreferences.DUPLEX.DuplexFlipLongEdge.toString().equals(dp))
            {
                attr.add(Sides.TWO_SIDED_LONG_EDGE);
            }
            else if (PDViewerPreferences.DUPLEX.DuplexFlipShortEdge.toString().equals(dp))
            {
                attr.add(Sides.TWO_SIDED_SHORT_EDGE);
            }
            else if (PDViewerPreferences.DUPLEX.Simplex.toString().equals(dp))
            {
                attr.add(Sides.ONE_SIDED);
            }
        }

        if (job.printDialog(attr))
        {
            job.print(attr);
        }
        
        document.close();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
	}
	
	private void saveOrUpdateReport() {
		
		currentReport.getRecord().setPrintted(true);
		requestObject.fillReport(StringDefine.SaveReportUrl, activitySaveReportUrl, null, currentReport, null);

		sendRequestToServer(requestObject , null);
	}
}

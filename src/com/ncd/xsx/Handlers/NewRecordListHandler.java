package com.ncd.xsx.Handlers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.ncd.xsx.Define.RequestObject;
import com.ncd.xsx.Define.ResponseObject;
import com.ncd.xsx.Define.StringDefine;
import com.ncd.xsx.Entity.Record;
import com.ncd.xsx.Spring.ActivitySession;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

@Component
public class NewRecordListHandler extends Activity {

	@FXML VBox FreshPane;
	
	@FXML TableView<Record> recordTableView;
	@FXML TableColumn<Record, Timestamp> TableColumn2;
	@FXML TableColumn<Record, String> TableColumn3;
	@FXML TableColumn<Record, String> TableColumn4;
	@FXML TableColumn<Record, String> TableColumn5;
	@FXML TableColumn<Record, String> TableColumn6;
	@FXML TableColumn<Record, String> TableColumn7;
	
	private ContextMenu myContextMenu;
	private MenuItem myMenuItem1 = new MenuItem("Ë¢ÐÂ");
	
	private RequestObject requestObject = new RequestObject();
	private Record requestRecord = new Record();
	private ListChangeListener<ResponseObject> myMessageListChangeListener = null;
	private List<Record> dataList = null;
	
	private static final String activityName = "NewReportActivity";
	private static final String activityUrl1 = "freshTableViewUrl";
	private static final String activityUrl3 = "setPrintStatusUrl";
	
	@Autowired ReportHandler reportHandler;
	@Autowired ActivitySession activitySession;
	
	@Override
	@PostConstruct
	public void onCreate() {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/com/ncd/xsx/Views/NewRecordListPage.fxml"));
        InputStream in = this.getClass().getResourceAsStream("/com/ncd/xsx/Views/NewRecordListPage.fxml");
        loader.setController(this);
        
        try {
        	setRootPane(loader.load(in));
        	in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        TableColumn2.setCellValueFactory(new PropertyValueFactory<Record, Timestamp>("testtime"));
        TableColumn3.setCellValueFactory(new PropertyValueFactory<Record, String>("sampleid"));
        TableColumn4.setCellValueFactory(new PropertyValueFactory<Record, String>("item"));
        TableColumn5.setCellValueFactory(new PropertyValueFactory<Record, String>("value"));
        TableColumn6.setCellValueFactory(new PropertyValueFactory<Record, String>("tester"));
        TableColumn7.setCellValueFactory(new PropertyValueFactory<Record, String>("deviceid"));

        recordTableView.setRowFactory(new Callback<TableView<Record>, TableRow<Record>>() {
			
			@Override
			public TableRow<Record> call(TableView<Record> param) {
				// TODO Auto-generated method stub
				TableRow<Record> row = new TableRow<>();
	            row.setOnMouseClicked((e)->{
	            	if(e.getClickCount() == 2){
	            		activitySession.startActivity(reportHandler, row.getItem());
	            	}
	            });

	            return row ;
			}
		});
        
        myMenuItem1.setOnAction((e)->{
        	freshRecordTableView();
        });
        myContextMenu = new ContextMenu(myMenuItem1);
        recordTableView.setOnMouseClicked((e)->{
        	if(e.getButton().equals(MouseButton.SECONDARY)){
        		myContextMenu.show(recordTableView, e.getScreenX(), e.getScreenY());
        	}
        	else {
        		myContextMenu.hide();
        	}
        });
        
        myMessageListChangeListener = c ->{
        	while(c.next()){
				if(c.wasAdded()){
					for (ResponseObject responseObject : c.getAddedSubList()) {
						
						FreshPane.setVisible(false);
						
						if(responseObject == null)
							continue;
						
						switch (responseObject.getResponseId()) {
							case activityUrl1:
								
								dataList = responseObject.getRecords();
								recordTableView.getItems().setAll(dataList);
								
								break;
							case activityUrl3:
								break;
							default:
								break;
						}
					}
				}
			}
        };
        
        AnchorPane.setTopAnchor(this.getRootPane(), 0.0);
        AnchorPane.setBottomAnchor(this.getRootPane(), 0.0);
        AnchorPane.setLeftAnchor(this.getRootPane(), 0.0);
        AnchorPane.setRightAnchor(this.getRootPane(), 0.0);
	}

	@Override
	public void onStart(Object object) {
		// TODO Auto-generated method stub
		super.onStart(object);
		
		getMyMessagesList().addListener(myMessageListChangeListener);
		
		freshRecordTableView();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		freshRecordTableView();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		getMyMessagesList().removeListener(myMessageListChangeListener);
	}
	
	private void freshRecordTableView() {
		requestRecord.setPrintted(false);

		requestObject.fillReport(StringDefine.QueryRecordUrl, activityUrl1, requestRecord, null, 0l);
		sendRequestToServer(requestObject , FreshPane);
	}

}

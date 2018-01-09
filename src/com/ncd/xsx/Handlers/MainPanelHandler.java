package com.ncd.xsx.Handlers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ncd.xsx.Spring.ActivitySession;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@Component
public class MainPanelHandler implements Observer{

	private Stage s_Stage = null;
	private AnchorPane root = null;
	private Scene s_Scene = null;
	
	@FXML AnchorPane MainContentPane;
	
	@Autowired ActivitySession activitySession;
	@Autowired NewRecordListHandler newReportListHandler;

	@PostConstruct
	public void onCreate() {
		
		FXMLLoader loader = new FXMLLoader();
		//loader.setLocation(this.getClass().getResource("/com/ncd/xsx/Views/MainPane.fxml"));
       // InputStream in = this.getClass().getResourceAsStream("/com/ncd/xsx/Views/MainPane.fxml");
		//loader.setLocation("/com/ncd/xsx/Views/MainPane.fxml");
	    InputStream in = null;
		try {
			in = new FileInputStream("MainPane.fxml");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}// this.getClass().getResourceAsStream("/com/ncd/xsx/Views/MainPane.fxml");
        loader.setController(this);
        
        try {
        	root = loader.load(in);
        	in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        s_Scene = new Scene(root);
        
        activitySession.addObserver(this);
        
        loader = null;
        in = null;
	}

	public void startMainActivity() {
		s_Stage = new Stage();
		s_Stage.setTitle("报告打印工具 V1.0");
		s_Stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/RES/logo.png")));
		s_Stage.setScene(s_Scene);
			
		s_Stage.setOnCloseRequest((e)->{System.exit(0);});

		activitySession.startActivity(newReportListHandler, null);
		
		s_Stage.show();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		MainContentPane.getChildren().clear();
		MainContentPane.getChildren().add(((Activity) arg).getRootPane());
	}
}

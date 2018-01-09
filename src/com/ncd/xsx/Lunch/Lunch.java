package com.ncd.xsx.Lunch;

import com.ncd.xsx.Handlers.MainPanelHandler;
import com.ncd.xsx.Spring.SpringFacktory;

import javafx.application.Application;
import javafx.stage.Stage;

public class Lunch extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		SpringFacktory.SpringFacktoryInit();
		
		SpringFacktory.GetBean(MainPanelHandler.class).startMainActivity();
	}
}

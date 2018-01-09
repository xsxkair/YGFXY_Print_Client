package com.ncd.xsx.Handlers;

import com.ncd.xsx.Define.RequestObject;
import com.ncd.xsx.Define.ResponseObject;
import com.ncd.xsx.Spring.SpringFacktory;
import com.ncd.xsx.Tools.MinaClient;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public abstract class Activity {
		
	private Pane rootPane;
	
	private String activityName;
	
	private ObservableList<ResponseObject> myMessagesList = null;

	public abstract void onCreate();

	public void onStart(Object object){
		myMessagesList = FXCollections.observableArrayList();
	}
	
	public abstract void onResume();
	
	public abstract void onDestroy();
	
	public Pane getRootPane() {
		return rootPane;
	}

	public void setRootPane(Pane rootPane) {
		this.rootPane = rootPane;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public ObservableList<ResponseObject> getMyMessagesList() {
		return myMessagesList;
	}

	public void sendRequestToServer(RequestObject requestObject, Pane freshPane) {
		if(SpringFacktory.GetBean(MinaClient.class).sendMessage(requestObject)) {
			if(freshPane != null)
				freshPane.setVisible(true);
		}
	}
	
	public void socketMessageCallBack(ResponseObject responseObject) {
		// TODO Auto-generated method stub
		Platform.runLater(()->{
			if(myMessagesList != null)
				myMessagesList.add(responseObject);
		});
	}
}

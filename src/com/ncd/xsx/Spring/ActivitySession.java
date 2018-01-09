package com.ncd.xsx.Spring;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Observable;

import org.springframework.stereotype.Component;

import com.ncd.xsx.Handlers.Activity;

@Component
public class ActivitySession extends Observable{
		
	private Deque<Activity> activityStack = new ArrayDeque<>();

	public Deque<Activity> getActivityStack() {
		return activityStack;
	}

	public void clearActivityStach(){
		while(!activityStack.isEmpty()){
			Activity activityTemplet = activityStack.pop();
			activityTemplet.onDestroy();
		}
	}
		
	public void clearAndSetOriginActivityAs(Activity activity, Object object){
			
		clearActivityStach();
			
		startActivity(activity, object);
	}
		
	public void pushActivity(Activity activity, Object object){
			
		activityStack.push(activity);
			
		setChanged();
			
		notifyObservers(object);
	}
		
	public void backToThisActivity(Activity activity){
		Activity tempActivity = null;

		while((tempActivity = activityStack.peek()) != null){
			if(tempActivity.equals(activity))
				break;
			else{
				finishActivity();
			}
		}
	}
	
	public void backToFatherActivity() {
		//old activity
		Activity tempActivity = activityStack.pop();
		
		if(tempActivity != null)
			tempActivity.onDestroy();
		
		//new activity
		tempActivity = activityStack.peek();
		tempActivity.onResume();
		
		setChanged();
		
		notifyObservers(tempActivity);
	}
	
	public Activity topActivity() {
		if(activityStack.isEmpty())
			return null;
		else
			return activityStack.peek();
	}
		
	public void startActivity(Activity activity, Object object){

		activityStack.push(activity);
		activity.onStart(object);
		
		setChanged();
		
		notifyObservers(activity);
	}
	
	public void finishActivity(){
		activityStack.peek().onDestroy();
		activityStack.pop();
		
		setChanged();
		
		notifyObservers();
	}
}

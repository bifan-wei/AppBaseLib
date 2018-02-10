package com.bifan.applib.main;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * AppActivityManager
 */
public class AppActivityManager {
	private static final AppActivityManager a = new AppActivityManager();
	private static final List<AppCompatActivity> mActivities = new ArrayList<AppCompatActivity>();

	public static AppActivityManager getInstance(){
		return a;
	}

	public void addActivity(AppCompatActivity activity) {
		if(activity!=null){
			mActivities.add(activity);
		}
	}

	public AppCompatActivity getTopActivity(){
		if(mActivities.size()==0){
			return null;
		}
		return mActivities.get(mActivities.size()-1);
	}


	public void removeActivity(AppCompatActivity activity) {
		if(activity!=null&& mActivities.contains(activity)){
			mActivities.remove(activity);
		}
	}


	public void finishActivity(AppCompatActivity activity) {
		if(activity!=null&& mActivities.contains(activity)){
			removeActivity(activity);
			activity.finish();
		}
	}


	public void finishAllActivity() {
		for(Activity a: mActivities){
			a.finish();
		}
		mActivities.clear();
	}

}

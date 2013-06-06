package com.giderosmobile.android.plugins;
import com.kokteyl.rankmost.RANKmost;
import com.kokteyl.rankmost.RANKmostScoreListener;
import com.kokteyl.rankmost.RANKmostTrophyListener;
import android.app.Activity;

public class Rankmost{
	/**
	* Use this status values to take action for event listeners arguements. 
	***
	* STATUS_OK = 0xe0;
	* STATUS_ERROR = 0xe1;
	* STATUS_CONNECTION_ERROR = 0xe2;
	* STATUS_USER_NOT_FOUND = 0xe3;
	**/
	public static final int STATUS_NOT_INITIALIZED = 0x194;
	
	public static final String MESSAGE = "Rankmost is not initialized";
	public static long sData;
	public static RANKmost sInstance;
	private static Activity currentActivity;
	
	public static void onCreate(Activity activity) {
		currentActivity = activity;
	}
	static public void init(long data)
	{
		sData = data;
	}
	static public void cleanup()
	{
		if (sInstance != null)
    	{
    		sData = 0;
    		sInstance = null;
    	}
	}
	//The first step is to initialize your Rankmost SDK with your application Guid in order to use other methods.
	private static void initialize(final String appGuid){
		sInstance = RANKmost.newInstance(currentActivity, appGuid);
	}
	
	private static boolean isInitialized(){
		return sInstance != null;
	}
	//This method opens the Portal as a webview and lets a user register or login. 
	// After logging in, the user is able to see their scoreboards and trophies. 
	// If there is no user guid data on the device, by logging in or registering, Rankmost saves it to the device.
	private static void startPortal(){
		if(!isInitialized()){
			onScoreResponse(STATUS_NOT_INITIALIZED, MESSAGE, sData);
			return;
		}
		sInstance.startPortal();
	}
	
	//This method is called to send the user's score with the parameters leaderboard guid and score.
	private static void sendScore(final String leaderBoardGuid, long score){
		if(!isInitialized()){
			onScoreResponse(STATUS_NOT_INITIALIZED, MESSAGE, sData);
			return;
		}
		sInstance.sendScore(leaderBoardGuid, score, new RANKmostScoreListener() {
			@Override
			public void onAction(int status, String message) {
				if (sData != 0)
				onScoreResponse(status, message, sData);
			}
		});
	}
	//This method is called with trophy guid in order to send the user's achievement of 100%
	private static void sendTrophy(final String trophyGuid) {
		if(!isInitialized()){
			onScoreResponse(STATUS_NOT_INITIALIZED, MESSAGE, sData);
			return;
		}
		sInstance.sendTrophy(trophyGuid, new RANKmostTrophyListener() {
			@Override
			public void onAction(int status,int value, String message) {
			//value is a completion percentage
				if (sData != 0)
				onTrophyResponse(status, value, message, sData);
			}
		});
	}
	// This method is called with trophy guid and percentage in order to send the user's partial achievement.
	// Each time this method is called, the new value will be added to the previously earned percentage value.
	private static void sendTrophy(final String trophyGuid, int percent) {
		
		if(!isInitialized()){
			onScoreResponse(STATUS_NOT_INITIALIZED, MESSAGE, sData);
			return;
		}
		sInstance.sendTrophy(trophyGuid, percent, new RANKmostTrophyListener() {
			@Override
			public void onAction(int status,int value, String message) {
			//value is a completion percentage
				if (sData != 0)
				onTrophyResponse(status, value, message, sData);
			}
		});
	}
	
	private static native void onScoreResponse(int status, String message, long data);
	private static native void onTrophyResponse(int status, int value, String message, long data);
	
	
	static public void onDestroy()
    {
    	cleanup(); 
    }
}

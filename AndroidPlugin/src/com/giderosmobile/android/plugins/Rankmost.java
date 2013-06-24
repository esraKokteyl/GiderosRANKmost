package com.giderosmobile.android.plugins;
import com.kokteyl.rankmost.RANKmost;
import com.kokteyl.rankmost.RANKmostScoreListener;
import com.kokteyl.rankmost.RANKmostTrophyListener;
import android.app.Activity;

public class Rankmost{
	
	public static final int STATUS_NOT_INITIALIZED = 0x194;
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
	private static void initialize(final String gameGuid){
		
		sInstance = RANKmost.newInstance(currentActivity, gameGuid);
		
	}
	
	private static boolean isInitialized(){
		return sInstance != null;
	}
	
	private static void startPortal(int portalPage){
		if(!isInitialized()){
			onScoreResponse(STATUS_NOT_INITIALIZED, -1,"Rankmost is not initialized", sData);
			return;
		}
		sInstance.startPortal(portalPage);
	}
	
	private static void startPortalWithLeaderBoard(String leaderBoardGuid){
		if(!isInitialized()){
			onScoreResponse(STATUS_NOT_INITIALIZED, -1,"Rankmost is not initialized", sData);
			return;
		}
		sInstance.startPortalWithLeaderBoard(leaderBoardGuid);
	}
	
	private static void sendScore(final String leaderBoardGuid, long score){
		if(!isInitialized()){
			onScoreResponse(STATUS_NOT_INITIALIZED, -1,"Rankmost is not initialized", sData);
			return;
		}
		sInstance.sendScore(leaderBoardGuid, score, new RANKmostScoreListener() {
			@Override
			public void onAction(int status, int recordBreak, String message) {
				if (sData != 0)
				onScoreResponse(status, recordBreak, message, sData);
			}
		});
	}
	
	private static void sendTrophy(final String trophyGuid) {
		if(!isInitialized()){
			onScoreResponse(STATUS_NOT_INITIALIZED, -1,"Rankmost is not initialized", sData);
			return;
		}
		sInstance.sendTrophy(trophyGuid, new RANKmostTrophyListener() {
			@Override
			public void onAction(int status,int value, String message) {
				if (sData != 0)
				onTrophyResponse(status, value, message, sData);
			}
		});
	}

	private static void sendTrophy(final String trophyGuid, int percent) {
		
		if(!isInitialized()){
			onScoreResponse(STATUS_NOT_INITIALIZED, -1, "Rankmost is not initialized", sData);
			return;
		}
		sInstance.sendTrophy(trophyGuid, percent, new RANKmostTrophyListener() {
			@Override
			public void onAction(int status,int value, String message) {
				if (sData != 0)
				onTrophyResponse(status, value, message, sData);
			}
		});
	}
	
	private static native void onScoreResponse(int status, int recordBreak, String message, long data);
	private static native void onTrophyResponse(int status, int value, String message, long data);
	
	static public void onDestroy()
    {
    	cleanup(); 
    }
}

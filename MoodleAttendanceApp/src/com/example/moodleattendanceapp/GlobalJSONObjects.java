package com.example.moodleattendanceapp;

import java.util.ArrayList;
import java.util.HashMap;

public class GlobalJSONObjects {
	
	private User user=null;
	
	private HashMap<String, ArrayList<PostAttendanceData>> attendanceDataMap=new HashMap<>();
	
	private static GlobalJSONObjects instance=null;
	
	public synchronized static GlobalJSONObjects getInstance()
	{
		
		if(instance==null)
		{
			instance=new GlobalJSONObjects();
		}
		
		return instance;
		
	}

	public void setUser(User user)
	{
		this.user=user;
	}
	
	public User getUser()
	{
		return this.user;
	}
	
	public void clean()
	{
		user=null;
	}
	
	public void addToAttendanceDataMap(String sessionId,ArrayList<PostAttendanceData> attendanceData)
	{
		attendanceDataMap.put(sessionId, attendanceData);
	}
	
}

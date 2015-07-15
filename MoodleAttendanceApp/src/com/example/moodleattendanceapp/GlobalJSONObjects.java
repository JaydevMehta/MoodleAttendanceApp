package com.example.moodleattendanceapp;

import org.json.JSONObject;

public class GlobalJSONObjects {
	
	private User user=null;
	
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
	
}

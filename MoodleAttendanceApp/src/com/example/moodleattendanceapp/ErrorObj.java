package com.example.moodleattendanceapp;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorObj
{
	String message,comment;
	
	public ErrorObj(String data) throws JSONException
	{
		try
		{
			JSONObject obj=new JSONObject(data).getJSONObject("error");
			message=obj.getString("message");
			comment=obj.getString("comment");
		}
		catch(JSONException e)
		{
			throw e;
		}
	}

	public String getMessage() {
		return message;
	}

	public String getComment() {
		return comment;
	}
	
}


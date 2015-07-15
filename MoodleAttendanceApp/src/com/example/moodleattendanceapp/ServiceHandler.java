package com.example.moodleattendanceapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;
 
@SuppressWarnings("deprecation")
public class ServiceHandler {
	
	static String response = null;
    
    private String host="http://192.168.1.100/webservice.php?";		//Local
    
    //private String host="http://rutvik.ddns.net/webservice.php?";		//Internet

    
	ServiceHandler()
	{
		
	}
	
	public String login(String userName, String password)
	{
		return executeCall(host+"method=login&user_name="+userName+"&password="+password);
	}
	
	public String getCourses(String toke, String userId)
	{
		return executeCall(host+"method=get_courses&token="+toke+"&user_id="+userId);
	}
	
	public String getAttendanceType(String courseId)
	{
		Log.i("MAA", "called course_id="+courseId);
		return executeCall(host+"method=get_attendance_type&course_id="+courseId);
	}
	
	public String getSessions(String courseId, String attendanceTypeId)
	{
		return executeCall(host+"method=get_sessions&course_id="+courseId+"&attendance_type_id="+attendanceTypeId);
	}
 
    		
	public String executeCall(String url)
	{
		
		try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            
            HttpGet httpGet = new HttpGet(url);
 
            httpResponse = httpClient.execute(httpGet);
 
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        return response;
	}



}

package com.example.moodleattendanceapp;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Attendance extends JSONObject implements Parcelable
{
    private String id;



    private ArrayList<Sessions> sessions=new ArrayList<Sessions>();


    private String name;

    private String grade;



    private ArrayList<Statuses> statuses=new ArrayList<Statuses>();

    
    public Attendance(Parcel p)
    {
    	id=p.readString();
    	
    	p.readTypedList(sessions,Sessions.CREATOR);
    	
    	name=p.readString();
    	grade=p.readString();
    	
    	p.readTypedList(statuses,Statuses.CREATOR);

    }
    
    public static final Parcelable.Creator<Attendance> CREATOR = new Parcelable.Creator<Attendance>() {

		@Override
		public Attendance createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Attendance(source);
		}

		@Override
		public Attendance[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Attendance[size];
		}
	};
	
	
    
    public Attendance(JSONObject obj) throws JSONException
    {
    	try
    	{
    		id=obj.getString("id");
    		name=obj.getString("name");
    		grade=obj.getString("grade");
    		
    		Log.i("moodle", "OKOKOK");
    		
    		JSONArray sessionsArr=obj.getJSONArray("sessions");
    		
    		JSONArray statusesArr=obj.getJSONArray("statuses");
    		
    		//sessions=new Sessions[sessionsArr.length()];
    		
    		//statuses=new Statuses[statusesArr.length()];
    		
    		for(int i=0;i<sessionsArr.length();i++)
    		{
    			Sessions s=new Sessions(sessionsArr.getJSONObject(i));
    			sessions.add(s);
    		}
    		
    		
    		
    		for(int i=0;i<statusesArr.length();i++)
    		{
    			Statuses s=new Statuses(statusesArr.getJSONObject(i));
    			statuses.add(s);
    		}
    		
    	}
    	catch(JSONException e)
    	{
    		throw e;
    	}
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public ArrayList<Sessions> getSessions ()
    {
        return sessions;
    }

    public void setSessions (ArrayList<Sessions> sessions)
    {
        this.sessions = sessions;
    }
    
    public void refreshSessions (JSONObject sessionObj)
    {
    	
		try {
			
			
			
			JSONArray sessionsArr = sessionObj.getJSONArray("sessions");
			
			sessions.clear();
			
			for(int i=0;i<sessionsArr.length();i++)
			{
				Log.i("MAA", "adding sessions "+i);
				Sessions s=new Sessions(sessionsArr.getJSONObject(i));
				sessions.add(s);
			}
			
			Log.i("MAA", "from attendance class size is "+sessions.size());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getGrade ()
    {
        return grade;
    }

    public void setGrade (String grade)
    {
        this.grade = grade;
    }

    public ArrayList<Statuses> getStatuses ()
    {
        return statuses;
    }

    public void setStatuses (ArrayList<Statuses> statuses)
    {
        this.statuses = statuses;
    }

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(id);
		dest.writeTypedList(sessions);
		dest.writeString(name);
		dest.writeString(grade);
		dest.writeTypedList(statuses);
		
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}

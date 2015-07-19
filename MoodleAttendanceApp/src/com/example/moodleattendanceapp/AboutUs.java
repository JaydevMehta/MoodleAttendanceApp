package com.example.moodleattendanceapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AboutUs extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		
		ArrayList<Sessions> sessions;
		
		Bundle b=getIntent().getExtras();
		sessions=b.getParcelableArrayList("sessions");
		
		Attendance a=getIntent().getParcelableExtra("att");
		
		Log.i("moodle",""+ sessions.size() +" and "+ a.getName());
		
	}

}

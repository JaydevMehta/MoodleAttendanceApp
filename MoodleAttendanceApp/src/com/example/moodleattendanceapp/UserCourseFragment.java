package com.example.moodleattendanceapp;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserCourseFragment extends Fragment{
	
	ListView CourseList;
	ImageView imgProPic;
	TextView tvUserFullName,tvRoleName;
	String user_id,user_fullname,user_role_name,user_propic_url;
	private String[] mPlanetTitles;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.user_course_fragment,
				container, false);
		imgProPic=(ImageView)rootView.findViewById(R.id.imgUserProPic);
		CourseList=(ListView)rootView.findViewById(R.id.lvCourseList);
		tvUserFullName=(TextView)rootView.findViewById(R.id.tvUserFullName);
		tvRoleName=(TextView)rootView.findViewById(R.id.tvUserRole);
		 int loader = R.drawable.ic_launcher;
		
		 
		getActivity().getActionBar().setTitle("Course");
		
		user_id=getArguments().getString("user_id");
		
		user_fullname=getArguments().getString("user_fullname");
		user_role_name = getArguments().getString("user_role_name");
		
		tvUserFullName.setText(user_fullname);
		
		if(user_role_name.equalsIgnoreCase("editingteacher"))
		{
			tvRoleName.setText("Faculty");
		}
		else if(user_role_name.equalsIgnoreCase("student"))
		{
			tvRoleName.setText("Student");
		}
		user_propic_url = getArguments().getString("user_propic_url");
		
		// ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(getActivity());
        
        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView 
        imgLoader.DisplayImage(user_propic_url, loader, imgProPic);
		
        //mPlanetTitles = getResources().getStringArray(R.array.CountryArray);
        
		Log.i("call course","course frag");
		
		CourseList.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, mPlanetTitles));
		
		return rootView;
	}
}

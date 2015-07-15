package com.example.moodleattendanceapp;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CourseAttendanceActivity extends Activity {
	
	ActionBar mActionBar;
	
	ListView attendanceList;
	
	SwipeRefreshLayout swipeContainer;
	
	AttendanceListAdapter attendanceListAdapter;
	
	ArrayList<Attendance> attendanceArrayList;
	
	int coursePosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SetActionBar();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_attendance);
		
		swipeContainer = (SwipeRefreshLayout) findViewById(R.id.attendanceSwipeContainer);

		swipeContainer.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				
				
				new AsyncTask<Void, Void, Void>(){

					String response="";
					
					Boolean success=false;

											
					@Override
					protected Void doInBackground(Void... params) {
						
						ServiceHandler sh=new ServiceHandler();
						response=sh.getAttendanceType(GlobalJSONObjects.getInstance().getUser().getCourse().get(coursePosition).getId());
						return null;
						
					}
					
					@Override
					protected void onPostExecute(Void result) {
						
						try {
							Log.i("MAA", response);
							JSONObject obj=new JSONObject(response);							
							GlobalJSONObjects.getInstance().getUser().getCourse().get(coursePosition).refreshAttendance(obj);
							Log.i("MAA",GlobalJSONObjects.getInstance().getUser().getFull_name());
							success=true;
							
						} catch (JSONException e) {

							try {
								ErrorObj errObj=new ErrorObj(response);
								Toast.makeText(CourseAttendanceActivity.this, errObj.getComment(), Toast.LENGTH_SHORT).show();
							} catch (JSONException e1) {
								Log.e("MAA", "error in processing ERROR JSON");
								e1.printStackTrace();
							}

							Log.e("MAA", "error in processing JSON");
							e.printStackTrace();
							
						}				
						
						if(success)
						{
							
							//Log.i("MAA", GlobalJSONObjects.getInstance().getUser().getCourse().get(coursePosition).getAttendance().get(attendancePosition).getSessions().get(0).getSessionDate());
							
													
							//sessionArrayList.addAll(GlobalJSONObjects.getInstance().getUser().getCourse().get(coursePosition).getAttendance().get(attendancePosition).getSessions());
							
							//Log.i("MAA", "size is "+GlobalJSONObjects.getInstance().getUser().getCourse().get(coursePosition).getAttendance().get(attendancePosition).getSessions().size()+"");
							
							attendanceListAdapter.notifyDataSetChanged();
							
							
							
						}
						
						//progressDialog.dismiss();
						
						swipeContainer.setRefreshing(false);

						
					}
				
				}.execute();

				
				
			}
		});
		
		swipeContainer.setColorScheme(android.R.color.holo_blue_bright, 
                android.R.color.holo_green_light, 
                android.R.color.holo_orange_light, 
                android.R.color.holo_red_light);

		
		attendanceList=(ListView) findViewById(R.id.lvCourseAttendanceList);
		
		coursePosition=getIntent().getIntExtra("position",0);
		
		attendanceArrayList=GlobalJSONObjects.getInstance().getUser().getCourse().get(coursePosition).getAttendance();
		
		attendanceListAdapter=new AttendanceListAdapter(this, attendanceArrayList);
		
		attendanceList.setAdapter(attendanceListAdapter);
		
		attendanceList.setOnItemClickListener(attendanceListAdapter);
		
		TextView emptyText = (TextView)findViewById(android.R.id.empty);
		
		attendanceList.setEmptyView(emptyText);
		
	}

	public void SetActionBar() {
		mActionBar = getActionBar();
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#FF6D00")));
		mActionBar.setTitle("Attendance");
		mActionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	
	
	class AttendanceListAdapter extends BaseAdapter implements OnItemClickListener {

		/*********** Declare Used Variables *********/
		Context context;
		   private ArrayList<Attendance> data;
		   private LayoutInflater inflater=null;
		   

		   int i=0;
		    
		   public AttendanceListAdapter(Context c,ArrayList<Attendance> d) {
		        context=c;
		           data=d;
		          
		        
		           /***********  Layout inflator to call external xml layout () ***********/
		            inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        
		   }

		   /******** What is the size of Passed Arraylist Size ************/
		   public int getCount() {
			   
		       return data.size();
		   }

		   public Object getItem(int position) {
		       return position;
		   }

		   public long getItemId(int position) {
		       return position;
		   }
		    
		   /********* Create a holder Class to contain inflated xml file elements *********/
		   

		   /****** Depends upon data size called for each row , Create each ListView row *****/
		   public View getView(int position, View convertView, ViewGroup parent) {
		        
		       View vi = convertView;
		       ViewHolder holder;
		        
		       if(convertView==null){ 
		            
		           /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
		           vi = inflater.inflate(R.layout.course_list_cell_design, null);
		            
		           /****** View Holder Object to contain tabitem.xml file elements ******/

		           holder = new ViewHolder();
		           holder.tvCourseName = (TextView) vi.findViewById(R.id.tvCourseFullName);
		           holder.tvCourseName.setTextColor(Color.WHITE);
		
		          /************  Set holder with LayoutInflater ************/
		           vi.setTag( holder );
		       }
		       else 
		           holder=(ViewHolder)vi.getTag();
		        
		       
		       if(data.size()>0)
		       {
		    	   Log.i("else Data size check","ok");
		           /***** Get each Model object from Arraylist ********/

		           
		    	   
		    	   
		           /************  Set Model values in Holder elements ***********/

		            holder.tvCourseName.setText(data.get(position).getName());

		              
		            /******** Set Item Click Listner for LayoutInflater for each row *******/

		       }
		       return vi;
		   }

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			
			
			Log.i("MAA", data.get(position).getName());
			
			Intent i=new Intent(context,SessionActivity.class);
			
			i.putExtra("course_position", coursePosition);
			i.putExtra("attendance_position", position);
			
			startActivity(i);
			
			
		}
	
	
	
	}
	
	static class ViewHolder{
        
	       public TextView tvCourseName;


	   }
	
}

package com.example.moodleattendanceapp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class UserLoginFragment extends Fragment {

	EditText etUserName, etPassword;
	Button btnUserLogin;
	String uname = "", pwd = "", response = "";

	// flag for Internet connection status
	Boolean isInternetPresent = false, flagResponse = false;
	User u;
	// Connection detector class
	ConnectionDetector cd;

	// SharePreferences for Store Username and Password
	SharedPreferences mSharedPreferences;
	Editor mEditor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.user_login_fragment,
				container, false);

		getActivity().getActionBar().setTitle("Login");

		etUserName = (EditText) rootView.findViewById(R.id.etUsername);
		etPassword = (EditText) rootView.findViewById(R.id.etPassword);
		btnUserLogin = (Button) rootView.findViewById(R.id.btnLogin);

		cd = new ConnectionDetector(getActivity());
		
		mSharedPreferences = getActivity().getSharedPreferences(
				"Login", Context.MODE_PRIVATE);

		if ((mSharedPreferences.contains("Username") && mSharedPreferences
				.contains("Password"))) 
		{
			etUserName.setText(mSharedPreferences.getString("Username", ""));
			etPassword.setText(mSharedPreferences.getString("Password", ""));
		}

		btnUserLogin.setOnClickListener(new OnClickListener() {

			// creating connection detector class instance

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserLogin(v);
				Log.i("Click", "Login frag");

			}
		});

		return rootView;
	}

	public void UserLogin(View v) {

		if (!isEmpty(etUserName) && !isEmpty(etPassword)) {
			// make HTTP request

			// get Internet status
			isInternetPresent = cd.isConnectingToInternet();

			// check for Internet status
			if (isInternetPresent) {
				// Internet Connection is Present
				// make HTTP requests
				// openAlert("Internet Connection","You have internet connection");
				uname = etUserName.getText().toString();
				pwd = etPassword.getText().toString();

				AsyncCallWS task = new AsyncCallWS();
				task.execute("");

			} else {
				// Internet connection is not present
				// Ask user to connect to Internet
				openAlert("No Internet Connection",
						"You don't have internet connection.");
			}
		} else {
			if (isEmpty(etUserName)) {
				etUserName.setHint("Enter Username");
				openAlert("Message", "Please Enter Username");
			} else if (isEmpty(etPassword)) {
				etPassword.setHint("Enter Password");
				openAlert("Message", "Please Enter Password");
			}
		}
	}

	private boolean isEmpty(EditText etText) {
		return etText.getText().toString().trim().length() == 0;
	}

	private void openAlert(String mTitle, String msg) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		alertDialogBuilder.setTitle(mTitle);
		alertDialogBuilder.setMessage(msg);
		// set positive button: Yes message
		alertDialogBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		// show alert
		alertDialog.show();
	}

	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.i("in back", "ok");
			fetchJSON();
			Log.i("in back last", "ok");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			getActivity().setProgressBarIndeterminateVisibility(Boolean.FALSE);
			if (flagResponse == true) {

				mSharedPreferences = getActivity().getSharedPreferences(
						"Login", Context.MODE_PRIVATE);
				mEditor = mSharedPreferences.edit();
				mEditor.putString("Username", uname);
				mEditor.putString("Password", pwd);
				mEditor.commit();

				Log.i("orgid save", "ok");

				Bundle b = new Bundle();

				UserCourseFragment sf = new UserCourseFragment();

				b.putString("user_id", u.getId());
				b.putString("user_fullname", u.getFull_name());
				//b.putString("user_role_name", u.getRole_short_name());
				b.putString("user_propic_url", u.getProfile_pic_url());
				sf.setArguments(b);
				

				getFragmentManager().beginTransaction()
						.replace(R.id.frame_layout, sf).addToBackStack(null)
						.commit();
			} else if (flagResponse == false) {
				openAlert("Login Incorrect",
						"Please enter correct Username and Password");
			}
		}

		@Override
		protected void onPreExecute() {
			getActivity().setProgressBarIndeterminateVisibility(Boolean.TRUE);
		}
	}

	@SuppressWarnings("deprecation")
	public void fetchJSON() {
		Log.i("In fetchJson", "ok");

		Log.i("In C==null", "ok");
		try {
			// Log.i("URL", url);
			String FinalURL = "http://rutvik.ddns.net/webservice.php?method=login&user_name="
					+ uname + "&password=" + pwd;
			// Log.i("Final URL", FinalURL);
			HttpPost post = new HttpPost(FinalURL);
			post.setHeader("Accept", "application/json; charset=UTF-8");

			/*
			 * List<NameValuePair> nameValuePairs = new
			 * ArrayList<NameValuePair>( 2);
			 * //user_name=131040119001&password=Reset@123 nameValuePairs
			 * .add(new BasicNameValuePair("user_name", uname));
			 * nameValuePairs.add(new BasicNameValuePair("password", pwd));
			 * 
			 * Log.i("pass", nameValuePairs.toString()); post.setEntity(new
			 * UrlEncodedFormEntity(nameValuePairs));
			 */
			HttpClient client = new DefaultHttpClient();

			HttpResponse resp = (HttpResponse) client.execute(post);

			HttpEntity entity = resp.getEntity();

			response = EntityUtils.toString(entity);
			Log.v("response info : ", "Hello My Res : " + response);
			if (response.indexOf("error") == -1) {
				// JSONObject UserLogin=new JSONObject(response);
				JSONObject ja = new JSONObject(response);
				Log.i("after json", "ok");

				u = new User(ja.getJSONObject("user"));

				//Log.i("role short nm", "Hello: " + u.getRole_short_name());
				flagResponse = true;
				Log.v("end fetchJson()", "Hello run ok");
			} else {

				flagResponse = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * if (flagResponse == 1) { Log.i("Call readjson", "ok");
		 * readAndParseJSON(response); }
		 */

	}

}

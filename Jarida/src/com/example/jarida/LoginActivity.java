package com.example.jarida;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.File;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class LoginActivity extends Activity{
	
		ProgressDialog dialog;
		ConnectionDetector cd;
		AlertDialogManager alert = new AlertDialogManager();
		UserFunctions funcs = new UserFunctions();

	 	Button btnLogin;
	 	TextView btnLinkToRegister;
	    EditText inputEmail;
	    EditText inputPassword;
	    TextView loginErrorMsg;

	    private static String KEY_SUCCESS = "success";
	    private static String KEY_ERROR = "error";
	    private static String KEY_ERROR_MSG = "error_msg";
	    private static String KEY_UID = "uid";
	    private static String KEY_NAME = "name";
	    private static String KEY_EMAIL = "email";
	    private static String KEY_CREATED_AT = "created_at";
	    
	    String email;
        String password;
	 
        
        @SuppressLint("NewApi")
    	public void beforeSync() {
    		/*cd = new ConnectionDetector(getApplicationContext());
    		if (!cd.isConnectingToInternet()) {
    			alert.showAlertDialog(LoginActivity.this,
    					"Internet Connection Error",
    					"Please connect to Nganya Internet connection", false);
    			return;
    		}*/
    		
    		
    		int currentapiversion = android.os.Build.VERSION.SDK_INT;

    		AsyncTask<String, String, String> task = new LoginTask();

    		if (currentapiversion >=
    			android.os.Build.VERSION_CODES.HONEYCOMB) {
    				task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    		} else {
    			task.execute();
    		}

    	}

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login);

	        inputEmail = (EditText) findViewById(R.id.loginEmail);
	        inputPassword = (EditText) findViewById(R.id.loginPassword);
	        btnLogin = (Button) findViewById(R.id.btnLogin);
	        btnLinkToRegister = (TextView) findViewById(R.id.tvLinkToRegisterScreen);

	        btnLogin.setOnClickListener(new View.OnClickListener() {
	 
	            public void onClick(View view) {
	                email = String.valueOf(inputEmail.getText());
	                password = inputPassword.getText().toString();
	                
	                beforeSync();
	                
	            }
	        });
	        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
	 
	            public void onClick(View view) {
	                Intent i = new Intent(getApplicationContext(),
	                        RegisterActivity.class);
	                startActivity(i);
	                finish();
	            }
	        });
	    }
	    
	    
	    private class LoginTask extends AsyncTask<String, String, String> {
			String indicator = null;
			JSONObject json  = null;
			
			@Override
			protected void onPreExecute() {
				dialog = new ProgressDialog(LoginActivity.this);
				dialog.setIndeterminate(true);
				dialog.setTitle("Logging in");
				dialog.setMessage("Please Wait...");
				dialog.setCancelable(true);
				dialog.show();
			}

			@Override
			protected String doInBackground(String... params) {
				
				
				json = funcs.loginUser(email, password);

				if (json != null) {
					try {
						indicator = json.getString(KEY_SUCCESS);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					dialog.dismiss();
				}
				return indicator;
			}

			@Override
			protected void onPostExecute(String flag) {
				super.onPostExecute(flag);
				dialog.dismiss();

				try {
					int indicator = Integer.parseInt(flag);
					
					if (indicator == 1) {
                        JSONObject json_user = json.getJSONObject("user");
                        funcs.logoutUser(getApplicationContext());
                        DataSource.getInstance(getBaseContext()).addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                       
                        Intent dashboard = new Intent(getApplicationContext(), HomeActivity.class);
                        dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(dashboard);
                        finish();

					} else {
						Toast.makeText(getApplicationContext(),
								"Incorrect username/password", Toast.LENGTH_LONG).show();
					}
					
				} catch (Exception e) {
					serverError(e);
					Log.e("Server Error", String.valueOf(e));
				}
			}
		}

		public void serverError(Exception e) {
			alert.showAlertDialog(
					LoginActivity.this,
					"Network Error",
					"Cannot establish a connection to the server, please check your internet connection",
					false);
			return;
		}
	    

}

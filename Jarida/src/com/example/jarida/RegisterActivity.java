package com.example.jarida;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class RegisterActivity extends Activity {
	
	ProgressDialog dialog;
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	UserFunctions funcs = new UserFunctions();

	
    Button btnRegister;
    EditText inputFullName;
    EditText inputEmail;
    EditText inputPassword;
    TextView registerErrorMsg;
    
    
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    
    String fullname;
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

		AsyncTask<String, String, String> task = new RegistrationTask();

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
        setContentView(R.layout.register);
        
        inputFullName = (EditText) findViewById(R.id.registerName);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);

        btnRegister.setOnClickListener(new View.OnClickListener() {        
            public void onClick(View view) {
                fullname = inputFullName.getText().toString();
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                
                beforeSync();

            	}
            });
    }
    
    
    private class RegistrationTask extends AsyncTask<String, String, String> {
		String indicator = null;
		JSONObject json  = null;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(RegisterActivity.this);
			dialog.setIndeterminate(true);
			dialog.setTitle("Registering");
			dialog.setMessage("Please Wait...");
			dialog.setCancelable(true);
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			json = funcs.registerUser(fullname, email, password);
			
			Log.e("JSON Value", String.valueOf(json));
			
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
			
			int indicator = Integer.parseInt(flag);
			
			try {
				if (indicator == 1) {
                    JSONObject json_user = json.getJSONObject("user");
                    
                    Log.e("JSON user ", String.valueOf(json_user));
                    
                    funcs.logoutUser(getApplicationContext());
                    DataSource.getInstance(getBaseContext()).addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                       
                    Intent dashboard = new Intent(getApplicationContext(), HomeActivity.class);
                    dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(dashboard);
                    finish();

				} else {
					Toast.makeText(getApplicationContext(),
							"Error occurred during registration", Toast.LENGTH_LONG).show();
				}
				
			} catch (Exception e) {
				serverError(e);
				Log.e("Server Error", String.valueOf(e));
			}
		}
	}

	public void serverError(Exception e) {
		alert.showAlertDialog(
				RegisterActivity.this,
				"Network Error",
				"Cannot establish a connection to the server, please check your internet connection",
				false);
		return;
	}
    
    
    
    

}
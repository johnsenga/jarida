package com.example.jarida;


import com.actionbarsherlock.app.SherlockActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyAccountActivity extends SherlockActivity {

	private Button btnLogout;
	UserFunctions funcs = new UserFunctions();

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myaccount);
		
		btnLogout = (Button) findViewById(R.id.btnLogout);
		
		btnLogout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				boolean loggedout = funcs.logoutUser(getApplicationContext());
				
				if (loggedout){
					HomeActivity loginstatus = new HomeActivity();

					/*loginstatus.loggedin = false;
					loginstatusfrag.loggedin = false;*/
					
					Intent loginscreen = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginscreen);
				}
				
			}
		});
		
		
	}



	
	
}

package com.notdecaf.foodinder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class DispatchActivity extends Activity {

	public DispatchActivity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, getString(R.string.applicationID),getString(R.string.clientKey));

		if(ParseUser.getCurrentUser() != null) {
			startActivity(new Intent(this, MainActivity.class));
		}
		else {
			startActivity(new Intent(this,LoginActivity.class));
		}
	}
}

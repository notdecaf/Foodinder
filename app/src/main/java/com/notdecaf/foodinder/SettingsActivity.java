package com.notdecaf.foodinder;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class SettingsActivity extends ActionBarActivity {
	RadioGroup layout;
	RadioGroup alcohol;
	String alcoholChoice;
	String layoutChoice;
	ParseUser user;
	Button saveButton;
	boolean changed;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		changed = false;
		setContentView(R.layout.activity_settings);
		user = ParseUser.getCurrentUser();
		alcohol = (RadioGroup) findViewById(R.id.alcohol_buttons);
		layout = (RadioGroup) findViewById(R.id.layout_buttons);
		saveButton = (Button) findViewById(R.id.saveButton);
		try {
			alcoholChoice = (String)user.get("alcohol");
			if(alcoholChoice.equals("yes")) {
				alcohol.check(R.id.alcohol_yes);
			}
			else {
				alcohol.check(R.id.alcohol_no);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			layoutChoice = (String)user.get("layout");
			if(layoutChoice.equals("left")) {
				layout.check(R.id.layout_left);
			}
			else {
				layout.check(R.id.layout_right);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		alcohol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.alcohol_yes) {
					alcoholChoice = "yes";
					user.put("alcohol",alcoholChoice);
					changed = true;
				}
				else {
					alcoholChoice = "no";
					user.put("alcohol",alcoholChoice);
					changed = true;
				}
			}
		});
		layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.layout_left) {
					layoutChoice = "left";
					user.put("layout",layoutChoice);
					changed = true;
				}
				else {
					layoutChoice = "right";
					user.put("layout",layoutChoice);
					changed = true;
				}
			}
		});
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(changed) {
					user.saveInBackground(new SaveCallback() {
						@Override
						public void done(ParseException e) {
							Toast.makeText(getApplicationContext(), "Preferences Saved", Toast.LENGTH_SHORT).show();
						}
					});
					changed = false;
				}
				else {
					Toast.makeText(getApplicationContext(), "No changes made", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}

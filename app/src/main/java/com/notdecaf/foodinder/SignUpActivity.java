package com.notdecaf.foodinder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpActivity extends ActionBarActivity {
	EditText usernameEdit;
	EditText passwordEdit;
	EditText passwordConfirmEdit;
	EditText dietRestrictionEdit;
	RadioGroup alcoholGroup;
	String alcoholChoice;
	Button mActionButtom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		usernameEdit = (EditText) findViewById(R.id.login_username);
		passwordEdit = (EditText) findViewById(R.id.login_password);
		passwordConfirmEdit = (EditText) findViewById(R.id.login_password_confirm);
		dietRestrictionEdit = (EditText) findViewById(R.id.diet_restrctions);
		mActionButtom = (Button) findViewById(R.id.signup_button);
		alcoholGroup = (RadioGroup) findViewById(R.id.alcohol_buttons);
		alcoholGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.alcohol_yes)
					alcoholChoice = "yes";
				else
					alcoholChoice = "no";
			}
		});

		mActionButtom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				signup();
			}
		});
	}


	public void signup() {
		String username = usernameEdit.getText().toString().trim();
		String password = passwordEdit.getText().toString().trim();
		String passwordagain = passwordConfirmEdit.getText().toString().trim();
		String dietRestrictions = dietRestrictionEdit.getText().toString().trim();

		if (dietRestrictions.matches("")) {
			dietRestrictions = "none";
		}
		boolean fail = false;
		if (username.length() == 0 || !username.matches("[a-zA-z.0-9_]*")) {
			usernameEdit.setError("Invalid username");
			fail = true;
		}
		if (password.length() <= 8) {
			passwordEdit.setError("Invalid password");
			passwordConfirmEdit.setText("");
			fail = true;
		} else if (!passwordagain.equals(password)) {
			fail = true;
			passwordConfirmEdit.setError("Invalid password");
		} else if (alcoholGroup.getCheckedRadioButtonId() == -1) {
			fail = true;
		}
		if (!fail) {
			ParseUser user = new ParseUser();
			user.setUsername(username);
			user.setPassword(password);
			user.put("diet",dietRestrictions);
			user.put("alcohol",alcoholChoice);
			user.put("layout","left");
			user.signUpInBackground(new SignUpCallback() {
				@Override
				public void done(ParseException e) {

if (e != null) {
						// Show the error message
						Toast.makeText(SignUpActivity.this, e.getMessage(),
								Toast.LENGTH_LONG).show();
					} else {
						// Start an intent for the dispatch activity
						Intent intent = new Intent(SignUpActivity.this, DispatchActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
								Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
					}
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

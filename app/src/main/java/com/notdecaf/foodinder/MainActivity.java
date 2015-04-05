package com.notdecaf.foodinder;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {
	DrawerLayout mDrawerLayout;
	ActionBarDrawerToggle mDrawerToggle;
	ListView mDrawerList;
	ArrayList<String> drawerItems;
	ProfileFragment profileFragment;
	HomeFragment homeFragment;
	HistoryFragment historyFragment;
	ArrayAdapter drawerListAdapter;
	ParseUser user;
	boolean left;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		user = ParseUser.getCurrentUser();
		try {
			if (user.get("layout").equals("right")) {
				setContentView(R.layout.activity_main_right);
				left = false;
			} else {
				setContentView(R.layout.activity_main);
				left = true;
			}
		}   catch (Exception e) {
			setContentView(R.layout.activity_main_right);
			left = false;
			user.put("layout","right");
			user.saveInBackground(new SaveCallback() {
				@Override
				public void done(ParseException e) {

				}
			});
		}

		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
		mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolBar,
				R.string.app_name, R.string.app_name);
		drawerItems = new ArrayList<String>();

		drawerItems.add("Profile");
		drawerItems.add("History");

		//Set the beginning page as the home fragment
		homeFragment = new HomeFragment();
		profileFragment = new ProfileFragment();
		historyFragment = new HistoryFragment();

		getSupportFragmentManager().beginTransaction().add(R.id.main_content, homeFragment).commit();

		//Creating the toolbar that will hold the spinning drawer icon
		setSupportActionBar(mToolBar);

		//Set the toggle for the drawer which indicates the state of the drawer and spins the icon
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if(left) {
			mDrawerLayout.setDrawerShadow(R.drawable.rect_shadow_right, GravityCompat.START);
		}
		else {
			mDrawerLayout.setDrawerShadow(R.drawable.rect_shadow_left, GravityCompat.END);
		}
		mDrawerToggle.syncState();

		//Populate the drawer
		drawerListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				drawerItems);
		mDrawerList.setAdapter(drawerListAdapter);

		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

				String selected = drawerItems.get(position);
				if (selected.equals("Profile")) {
					drawerItems.clear();
					String [] items = getResources().getStringArray(R.array.profile_list);
					drawerItems.addAll(Arrays.asList(items));
					drawerListAdapter.notifyDataSetChanged();
					transaction.replace(R.id.main_content,profileFragment);
					transaction.commit();
				} else if (selected.equals("History")){
					drawerItems.clear();
					String [] items = getResources().getStringArray(R.array.history_list);
					drawerItems.addAll(Arrays.asList(items));
					drawerListAdapter.notifyDataSetChanged();
					transaction.replace(R.id.main_content,historyFragment);
					transaction.commit();
				} else {
					drawerItems.clear();
					String [] items = getResources().getStringArray(R.array.home_list);
					drawerItems.addAll(Arrays.asList(items));
					drawerListAdapter.notifyDataSetChanged();
					transaction.replace(R.id.main_content,homeFragment);
					transaction.commit();
				}
				mDrawerLayout.closeDrawers();
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
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
			startActivity(new Intent(this, SettingsActivity.class));
		}
		if(left) {
			if (mDrawerToggle.onOptionsItemSelected(item)) {
				return true;
			}
		}
		else {
			if (item.getItemId() == android.R.id.home) {
				if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
					mDrawerLayout.closeDrawers();
				} else {
					mDrawerLayout.openDrawer(Gravity.RIGHT);
				}
			}
			return false;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		if(left && mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
			mDrawerLayout.closeDrawers();
			return;
		}
		else if(!left && mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
			mDrawerLayout.closeDrawers();
		}
		super.onBackPressed();
	}
}

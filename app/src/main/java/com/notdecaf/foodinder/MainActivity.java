package com.notdecaf.foodinder;

import android.support.v4.app.FragmentTransaction;
import android.content.res.Configuration;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
			return true;
		}
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
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
		if(mDrawerLayout.isDrawerOpen(Gravity.START)){
			mDrawerLayout.closeDrawers();
			return;
		}
		super.onBackPressed();
	}
}

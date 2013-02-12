package com.example.test;

import no.evry.todo.sql.TodoContract;
import no.evry.todo.sql.TodoDbHelper;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class TaskAddActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_add);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
		
			TaskAddFragment fragment = new TaskAddFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.task_add_container, fragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 1, Menu.NONE, "Lagre");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this,
					new Intent(this, TaskListActivity.class));
			return true;
		case 1:
			// Here you can do something useful, show error dialog or something else
			if(saveTask()) {
				NavUtils.navigateUpTo(this,  new Intent(this, TaskListActivity.class));
				return true;
			}
			return false;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/**
	 * Saves the inputed data to the database
	 * 
	 * @return
	 */
	protected boolean saveTask() {
		EditText title = (EditText) findViewById(R.id.editText1);
		EditText desc = (EditText) findViewById(R.id.editText2);
		
		TodoDbHelper mDbHelper = new TodoDbHelper(this);
		ContentValues values = new ContentValues();;
		values.put(TodoContract.TodoItem.COLUMN_NAME_TITLE, title.getText().toString());
		values.put(TodoContract.TodoItem.COLUMN_NAME_DESC, desc.getText().toString());
		values.put(TodoContract.TodoItem.COLUMN_NAME_FINISHED, "false");
		
		long val = mDbHelper.insertRow(values);
		return val > 0;
	}
	
}

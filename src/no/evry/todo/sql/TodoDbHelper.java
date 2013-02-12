package no.evry.todo.sql;

import no.evry.todo.data.TodoItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDbHelper extends SQLiteOpenHelper {
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES = 
			"CREATE TABLE " + TodoContract.TodoItem.TABLE_NAME + " (" + 
			TodoContract.TodoItem._ID + " INTEGER PRIMARY KEY," + 
			TodoContract.TodoItem.COLUMN_NAME_TODO_ID + TEXT_TYPE + COMMA_SEP + 
			TodoContract.TodoItem.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
			TodoContract.TodoItem.COLUMN_NAME_DESC + TEXT_TYPE + COMMA_SEP + 
			TodoContract.TodoItem.COLUMN_NAME_FINISHED + TEXT_TYPE +
			" )";
	
	private static final String SQL_DELETE_ENTRIES = 
			"DROP TABLE IF EXISTS " + TodoContract.TodoItem.TABLE_NAME;
	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Todo.db";
	
	// Define a projection that specifies which columns from the database
	// you will actually use after this query.
	private String[] projection = {
					TodoContract.TodoItem._ID,
					TodoContract.TodoItem.COLUMN_NAME_TITLE,
					TodoContract.TodoItem.COLUMN_NAME_DESC,
					TodoContract.TodoItem.COLUMN_NAME_FINISHED
			    };
	private SQLiteDatabase db;
	
	public TodoDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.db = this.getReadableDatabase();
	}
	
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
	
	public Cursor getAllTodoItems() {
		return getAllTodoItems(null, null);
	}
	
	public Cursor getAllTodoItems(String selection, String[] selectionArgs) {
		String sortOrder =
				TodoContract.TodoItem._ID + " ASC";

		Cursor c = db.query(
			TodoContract.TodoItem.TABLE_NAME,  // The table to query
		    projection,                               // The columns to return
		    selection,                                // The columns for the WHERE clause
		    selectionArgs,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    sortOrder                                 // The sort order
		    );
		
		return c;
	}
	
	public TodoItem getTodoItem(String id) {
		Cursor c = getAllTodoItems(TodoContract.TodoItem._ID + " == ?", new String[] { id } );
		c.moveToFirst();
		String todoid = c.getString(c.getColumnIndex(TodoContract.TodoItem._ID));
		String title = c.getString(c.getColumnIndex(TodoContract.TodoItem.COLUMN_NAME_TITLE));
		String desc = c.getString(c.getColumnIndex(TodoContract.TodoItem.COLUMN_NAME_DESC));
		boolean finished = Boolean.parseBoolean(c.getString(c.getColumnIndex(TodoContract.TodoItem.COLUMN_NAME_FINISHED))); 
		TodoItem item = new TodoItem(todoid, title, desc, finished);
		
		return item;
	}
	
	public long count() {
		return DatabaseUtils.queryNumEntries(db, TodoContract.TodoItem.TABLE_NAME);
	}
	
	public long insertRow(ContentValues values) {
// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(
				TodoContract.TodoItem.TABLE_NAME,
				"null",
		         values);
		return newRowId;
	}
}

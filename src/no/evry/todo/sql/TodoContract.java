package no.evry.todo.sql;

import android.provider.BaseColumns;

public class TodoContract{
	
	private TodoContract()
	{
		
	}
	
	public static abstract class TodoItem implements BaseColumns
	{
		public static final String TABLE_NAME = "todo";
		public static final String COLUMN_NAME_TODO_ID = "todoid";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_DESC = "desc";
		public static final String COLUMN_NAME_FINISHED = "finished";
	}

}

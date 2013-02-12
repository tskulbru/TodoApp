package no.evry.todo.data;
	
public class TodoItem {
	public String id;
	public String title;
	public String desc;
	public boolean finished;
	
	public TodoItem(String id, String title, String desc, boolean finished) {
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.finished = finished;
	}
	
	public TodoItem(String title, String desc) {
		this.title = title;
		this.desc = desc;
		this.finished = false;
	}
}
	


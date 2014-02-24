package test.base;

import test.impl.User;

public class Student extends User {
	private String id;
	Student(String n, String s, String mail,String password, String id){
		super(n, s, mail,password);
		this.setId(id);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public void ViewCompulsory(){
		// list of compulsory sessions
	}
	public void BookTimeSlot(Session s){
		// print timeslots of a session and book one
	}
}

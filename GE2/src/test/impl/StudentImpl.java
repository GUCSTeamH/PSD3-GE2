package test.impl;

import database.DatabaseInterface;
import test.inter.StudentInterface;

public class StudentImpl extends User implements StudentInterface {
	String matric;
	DatabaseInterface db;
	
	StudentImpl(String name, String surname, String email, String matric){
		super(name, surname, email);
		this.matric=matric;
	}
	

	public void bookSession(int time){
		db.signUpToTimeslot(time, this.matric);
		db.add
		
		
	}
	
	public boolean checkSession(){
		//get list of compulsory sessions
		
		return true;
	}
	
	
	
	
}

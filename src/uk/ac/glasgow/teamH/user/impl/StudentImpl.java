package uk.ac.glasgow.teamH.user.impl;

import uk.ac.glasgow.teamH.database.DatabaseInterface;
import uk.ac.glasgow.teamH.user.StudentInterface;

public class StudentImpl extends User implements StudentInterface {
	private String matric;
	private DatabaseInterface db;
	
	public StudentImpl(String name, String surname, String email, String password, String matric){
		super(name, surname, email,password);
		this.matric=matric;
	}
	
	
	public StudentImpl(){
		matric="1";
		
	}
	
	
	

	public void bookSession(int time){
		System.out.println("student: booked session ");
		db.signUpToTimeslot(time, this.matric);

		
		
	}
	
	public boolean checkSession(){
		System.out.println("student: enrolled in all couurses");
		//get list of compulsory sessions
		
		db.checkCompulsorySessions(this.matric);
		return true;
	}
	
	
	
	
}

package uk.ac.glasgow.teamH.user.impl;

import java.sql.ResultSet;

import uk.ac.glasgow.teamH.MyCampus.MyCampusInterface;
import uk.ac.glasgow.teamH.database.DatabaseInterface;
import uk.ac.glasgow.teamH.user.StudentInterface;

public class StudentImpl extends User implements StudentInterface {

	private DatabaseInterface db;
	private MyCampusInterface myc;
	
	public StudentImpl(String name, String surname, String email, String password){
		super(name, surname, email,password);

	}
	
	
	public StudentImpl(DatabaseInterface db){
		this.db=db;

		
	}
	
	public ResultSet getSessionsCourse(int courseID){
		return db.getSessionsCourse(courseID);
	}
	
	public void bookTimetableSlot(int student, int course, int session, int time){
		System.out.println("student: booked session ");
		db.bookTimetableSlot(student, course, session, time);;
	}
	
	public boolean checkIfSignedUpForCompulsory(int student, int session, int course){
		System.out.println("student: enrolled in all couurses");
		//get list of compulsory sessions
		
		boolean check= db.checkIfSignedUpForCompulsory(student, session, course);
		return check;
	}
	
	public boolean login(String mail, String pass){
		return myc.authenticate(mail, pass);
	}
	
	
	
	
}

package test.impl;

import java.util.ArrayList;

import test.base.Session;
import test.base.Session.SessionType;
import test.base.Timeslot;
import test.base.TimetableSlot;
import test.inter.AdminInterface;
import test.inter.LecturerInterface;

public class LecturerImpl implements LecturerInterface {

	ArrayList something;
	AdminInterface admin;
	
	
	public LecturerImpl(){
		;
	}
	
	public void importMyCampusCourse(){
		
	}
	
	public void addSessionToCourse(String courseId){
		
		Timeslot time = new Timeslot(3,"Friday",9,10);
		Session s=new Session(154, SessionType.COMPULSORY, time);
		
		//put session in database, at courseId
		
		System.out.println("Lecturer : session added to course: "+courseId);

		
	}
	
	public void specifySessionType(int sessionId, SessionType type){
		
		System.out.println("Lecturer : changed session type for session "+sessionId+" to "+type);
		//get session from db
		//put session in db with type assigned
		
	}
	

	public void seeDetails(int sessionId){
		
		//get session from db
		
		//display details for all timetable slots
		
		System.out.println("Lecturer : details not available for session: "+sessionId);
		
	}
	
	public void printSomething(){
		System.out.println(something.get(0));
	}

}

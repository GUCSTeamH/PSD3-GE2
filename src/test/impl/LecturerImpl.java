package test.impl;

import java.util.ArrayList;

import MyCampus.MyCampusCommunicator;
//import database.DatabaseInterface;
import test.base.Course;
import test.base.Session;
import test.base.Session.SessionType;
import test.base.Timeslot;
import test.base.TimetableSlot;
import test.inter.AdminInterface;
import test.inter.LecturerInterface;

public class LecturerImpl implements LecturerInterface {

	ArrayList something;
	//DatabaseInterface db;
	MyCampusCommunicator mycampusCommunicator;
	
	public LecturerImpl(){
		
	}
	
	public void importMyCampusCourse(){
		System.out.println("course added");
		
	}
	
	public void registerMyCampusCommunicator(MyCampusCommunicator communicator){
		mycampusCommunicator = communicator;		
	}
	
	public String getCourseInfoFromMyCampus(Course c){
		if (mycampusCommunicator!=null){
			return mycampusCommunicator.getCourseInfo(c);
		}
		else return "Please register for MyCampus Communicator first!!!";
		
	}
	
	public void addSessionToCourse(String courseId){
		
		Timeslot time = new Timeslot(3,"Friday",9,10);
		Session s=new Session(154, SessionType.COMPULSORY, time);
		
		//db.addSession(courseId, s.getSessionType()==SessionType.ONEOFF, s.getSessionType() == SessionType.COMPULSORY);
		
		System.out.println("Lecturer : session added to course: "+courseId);

		
	}
	
	public void specifyRecurrent(int sessionId){
		//db.makeSessionRecurring(sessionId);
		
		System.out.println("Lecturer : changed session  "+sessionId+" recurrent ");
		//get session from db
		//put session in db with type assigned
		
	}
	

	public void seeDetails(int courseId){
		
		//db.getCourseSessionDetails(courseId);
		
		System.out.println("Lecturer : details not available for session: "+courseId);
		
		
	}

	public void addCourse(String courseID, String name) {

		//db.addCourse(courseID, name);
		
	}

}

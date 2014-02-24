package uk.ac.glasgow.teamH.user.impl;

import java.util.ArrayList;

import uk.ac.glasgow.teamH.MyCampus.MyCampusCommunicator;
import uk.ac.glasgow.teamH.database.DatabaseInterface;
import uk.ac.glasgow.teamH.user.Course;
import uk.ac.glasgow.teamH.user.LecturerInterface;
import uk.ac.glasgow.teamH.user.Session;
import uk.ac.glasgow.teamH.user.Session.SessionType;

public class LecturerImpl extends User implements LecturerInterface {

	DatabaseInterface db;
	MyCampusCommunicator mycampusCommunicator;
	
	public LecturerImpl(){
		
		super("Jack","John", "jack.john@dcs.gla.ac.uk", "password");
		
	}
	
	LecturerImpl(String n, String s, String mail,String password){
		super(n, s, mail,password);

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
		// removed timetable slot as a session has multiple timetable slots and not one
		Session s=new Session(154, SessionType.COMPULSORY);
		
		db.addSession(courseId, s.getSessionType()==SessionType.ONEOFF, s.getSessionType() == SessionType.COMPULSORY);
		
		System.out.println("Lecturer : session added to course: "+courseId);

		
	}
	public void addTimetSlotToSession(Session s){
		// add a timetable slot to a session
	}
	
	public void specifyRecurrent(int sessionId){
		db.makeSessionRecurring(sessionId);
		
		System.out.println("Lecturer : changed session  "+sessionId+" recurrent ");
		//get session from db
		//put session in db with type assigned
		
	}
	

	public void seeDetails(int courseId){
		
		db.getCourseSessionDetails(courseId);
		
		System.out.println("Lecturer : details not available for session: "+courseId);
		
		
	}

	public void addCourse(String courseID, String name) {

		db.addCourse(courseID, name);
		
	}

}

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
	
	public void importMyCampusCourse(int courseID){
		db.importMycampusCourse(courseID);
		
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
	
	public void addSession(int courseID, int sessionID, boolean compulsory ){
		
		db.addSession(courseID, sessionID, compulsory);
		
		System.out.println("Lecturer : session added to course: "+courseID);

		
	}
	public void addTimetSlotToSession(Session s){
		// add a timetable slot to a session
	}
	
	public void specifySessionRecurrence(int sessionId, String rec){
		db.specifySessionRecurrence(sessionId, rec);
		
		System.out.println("Lecturer : changed session  "+sessionId+" recurrent ");
		//get session from db
		//put session in db with type assigned
		
	}
	

	public String getTimetableslotDetails(int sessionId){
		
		String details=db.getTimetableslotDetails(sessionId);
		
		System.out.println("Lecturer : details for session: "+sessionId+" : "+details);
		
		return details;
		
		
	}


}

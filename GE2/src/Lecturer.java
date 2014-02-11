import java.util.ArrayList;

import Session.SessionType;


public class Lecturer extends User {

	ArrayList<Course> courses;
	
	Lecturer(String n, String s, String mail){
		super(n, s, mail);
		courses = new ArrayList<Course>();
	}
	
	public Course importCourse(){
		 // get a course fom MyCampus?
		Course c = myca.getCourse(name);
		
		//add course to lecturer's list of courses
		
		courses.add(c);
	}
	
	//if session is defined before : 
	public void addSession(Course c, Session s){
		c.addSession(s);
	}
	
	//if session is not defined before : 
	
	public void defineSession(Course c){
		Session s = new Session();

		//implementation of the session, don't know what attributes it will have yet..
		c.addSession(s);
	}
	
	public void specifySessionType(Session session, SessionType type){
		session.setSessionType(type);
		
	}
	
	
	
}

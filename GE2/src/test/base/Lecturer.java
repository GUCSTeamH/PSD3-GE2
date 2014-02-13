package test.base;
import java.util.ArrayList;




public class Lecturer extends User {

	ArrayList<Course> courses;
	int[] smth = new int[10];
	
	Lecturer(String n, String s, String mail){
		super(n, s, mail);
		courses = new ArrayList<Course>();
	}
	
	public void importCourse(){
		 // get a course fom MyCampus?
		//Course c = myca.getCourse(name);
		
		//add course to lecturer's list of courses
		
		//courses.add(c);
	}
	
	//if session is defined before : 
	public void addSession(Course c, Session s){
		c.addSessionToACourse(s);
	}
	
	//if session is not defined before : 
	
	public void defineSession(Course c){
		//Session s = new Session();

		//implementation of the session, don't know what attributes it will have yet..
		//c.addSessionToACourse(s);
	}
	
	public void specifySessionType(Session session, Session.SessionType type){
		session.setSessionType(type);
		
	}
	
	
	
}

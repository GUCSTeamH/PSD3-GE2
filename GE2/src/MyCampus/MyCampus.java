package MyCampus;

import java.util.HashMap;

import Course.Course;

public class MyCampus implements AuthenticatorInterface,MyCampusCommunicator{
	
	
	HashMap<Course,String> courses;
	
	
	public MyCampus(){
		courses = new HashMap<Course, String>();
	}
	
	
	public boolean authenticate(String a, String b){
		return true;
	}
	
	
	public String getCourseInfo(Course c){
		if(courses.containsKey(c))
			return courses.get(c);
		return "No information about this course! Sorry!!!";
			

		
		
	}



	

}

package uk.ac.glasgow.teamH.MyCampus.impl;

import java.util.HashMap;

import uk.ac.glasgow.teamH.MyCampus.MyCampusInterface;
import uk.ac.glasgow.teamH.user.Course;


public class MyCampus implements MyCampusInterface{
	
	
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

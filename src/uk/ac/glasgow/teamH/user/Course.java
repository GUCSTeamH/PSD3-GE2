package uk.ac.glasgow.teamH.user;
import java.util.LinkedList;


public class Course {
	
	private String name;
	private int id;


	
	
	public Course(int id,String name){
		this.id = id;
		this.name = name;

		
	}
	public int getCourseId(){
		return id;
	}
	
	public String getCourseName(){
		return name;
	}

	
	//we probably don't need this
	public void setCourseName(String n){
		name = n;
	}


}

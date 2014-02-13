package base;
import java.util.LinkedList;


public class Course {
	
	private String name;
	private int id;
	private LinkedList<Session> sessions;
	private LinkedList<Student> students;
	private Lecturer lecturer;

	//add capacity for a course
	//add size - number of students...
	// add prerequisites?
	
	
	public Course(int id,String name){
		this.id = id;
		this.name = name;
		sessions = new LinkedList<Session>();
		students = new LinkedList<Student>();
		
	}
	
	
	public Course(int id,String name, Lecturer l){
		this(id, name);
		lecturer = l;
		
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

	public LinkedList<Student> getStudentsOfTheCourse(){
		return students;
	}
	
	public void registerStudentWithACourse(Student s){
		students.add(s);
	}
	
	public LinkedList<Session> getSessionsForACourse(){
		return sessions;
	}
	
	public void addSessionToACourse(Session s){
		sessions.add(s);
	}
	
	public Lecturer getLecturerOFACourse(){
		return lecturer;
	}
	
	public void assignLecturerForACourse(Lecturer l){
		lecturer = l;
	}
}

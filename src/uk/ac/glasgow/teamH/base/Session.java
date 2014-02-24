package uk.ac.glasgow.teamH.base;
import java.util.LinkedList;


public class Session {
	

	
	//enum compulsory, ...
	public enum SessionType{COMPULSORY, ONEOFF, OPTIONAL};
	
	private int id;
	
	//type of the session
	private SessionType type;
	
		
	//list of students registered with the session
	private LinkedList<Student> students;
	
	//tutor or a lecturer conducting the session
	private Staff staff;
	
	private LinkedList<TimetableSlot> slots;
	
	
	//constructors: probably other will be added according to our needs
	public Session (int id,SessionType type){
		this.type = type;
		this.id = id;
		students = new LinkedList<Student>();
		slots = new LinkedList<TimetableSlot>();
	}
	
	
	public Session(int id, SessionType type, Staff staff){
		this(id, type);
		this.staff=staff;
	}
	 
	
	public void addTimetableSlot(TimetableSlot t){
		slots.add(t);
	}
	public void addTimetableSlot(int wn, String wd, int st, int et){
		slots.add(new TimetableSlot(wn,wd,st,et));
	}
	public int getSessionId(){
		return id;
	}
	
	public SessionType getSessionType(){
		return type;
	}
	
	public void setSessionType(SessionType t){
		type = t;
	}
	
	public LinkedList<Student> getStudentsForASession(){
		return students;
	}

	public void registerStudentForASession(Student s){
		students.add(s);
	}
	

	public Staff getStaff(){
		return staff;
	}
	
	public void assignStaff(Staff s){
		staff = s;
	}
	
	
	
	
	
}

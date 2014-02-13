package test.base;
import java.util.LinkedList;


public class Session {
	

	
	//enum compulsory, ...
	public enum SessionType{COMPULSORY, ONEOFF, OPTIONAL};
	
	private int id;
	
	//type of the session
	private SessionType type;
	
	//when 
	private Timeslot timeslot;
	
	//list of students registered with the session
	private LinkedList<Student> students;
	
	//tutor or a lecturer conducting the session
	private Staff staff;
	
	private LinkedList<TimetableSlot> slots;
	
	
	//constructors: probably other will be added according to our needs
	public Session (int id,SessionType type, Timeslot t){
		this.type = type;
		this.timeslot = t;
		this.id = id;
		students = new LinkedList<Student>();
		slots = new LinkedList<TimetableSlot>();
	}
	
	
	public Session(int id, SessionType type, Timeslot t, Staff staff){
		this(id, type, t);
		this.staff=staff;
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
	

	
	public Timeslot getTime(){
		return timeslot;
	}
	
	public void setTime(Timeslot t){
		timeslot = t;
	}
	
	public Staff getStaff(){
		return staff;
	}
	
	public void assignStaff(Staff s){
		staff = s;
	}
	
	
	
	
	
}

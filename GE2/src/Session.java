import java.sql.Time;
import java.util.LinkedList;


public class Session {
	

	
	//enum compulsory, ...
	public enum SessionType{COMPULSORY, ONEOFF, OPTIONAL};
	
	private int id;
	
	//type of the session
	private SessionType type;
	
	//when 
	private Day day;
	private Time time;
	
	//list of students registered with the session
	private LinkedList<Student> students;
	
	//tutor or a lecturer conducting the session
	private Staff staff;
	
	private LinkedList<TimetableSlot> slots;
	
	
	//constructors: probably other will be added accourding to our needs
	public Session (int id,SessionType type, Day d, Time t){
		this.type = type;
		this.day = d;
		this.time = t;
		this.id = id;
		students = new LinkedList<Student>();
		slots = new LinkedList<TimetableSlot>();
	}
	
	
	public Session(int id, SessionType type, Day d, Time t, Staff staff){
		this(id, type, d, t);
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
	
	public Day getDay(){
		return day;
	}
	
	public void setDay(Day d){
		day = d;
	}
	
	public Time getTime(){
		return time;
	}
	
	public void setTime(Time t){
		time = t;
	}
	
	public Staff getStaff(){
		return staff;
	}
	
	public void assignStaff(Staff s){
		staff = s;
	}
	
	
	
}

package uk.ac.glasgow.teamH.user.impl;




import uk.ac.glasgow.teamH.MyCampus.MyCampusInterface;
import uk.ac.glasgow.teamH.database.DatabaseInterface;
import uk.ac.glasgow.teamH.user.Course;
import uk.ac.glasgow.teamH.user.LecturerInterface;


public class LecturerImpl extends User implements LecturerInterface {

	DatabaseInterface db;
	MyCampusInterface mycampusCommunicator;
	
	public LecturerImpl(DatabaseInterface db){
		
		super("Jack","John", "jack.john@dcs.gla.ac.uk", "password");
		this.db = db;
		
	}
	
	LecturerImpl(String n, String s, String mail,String password){
		super(n, s, mail,password);

	}
	
	public void importMyCampusCourse(int courseID){
		db.importMycampusCourse(courseID);
		
	}
	
	public void registerMyCampusCommunicator(MyCampusInterface mycampus){
		mycampusCommunicator = mycampus;		
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

	
	public void specifySessionRecurrence(int sessionId, String rec){
		db.specifySessionRecurrence(sessionId, rec);
		
		System.out.println("Lecturer : changed session  "+sessionId+" recurrent ");

		
	}
	
	public boolean login(String mail, String pass){
		return mycampusCommunicator.authenticate(mail, pass);
		
	}
	

	public String getSessionDetails(int sessionId){
		return db.getSessionInfo(sessionId);
	}




}

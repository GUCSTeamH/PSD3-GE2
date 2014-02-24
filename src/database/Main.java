package database;

public class Main {
	public static void main(String[] args){
		
    /*	
     * 
    public void addSession(int courseID, int sessionID,boolean compulsory);

	
	public void importMycampusCourse(int courseID);
	
	public void specifySessionRecurrence(int sessionID,String recurrence);
	
	public void assignRoomToTimetableslot(int timetableslotID, String room);
	
	public void bookTimetableSlot(int studentID, int courseID, int sessionID,int timetableslotID);
	
	public boolean checkIfSignedUpForCompulsory(String studentID, String name);
	
	public String getTimetableslotDetails(int sessionID);
	
	public int supportNUsers(int n);
	
	public int supportNTimetableslotsPerSession(int n, int sessionID);
	
	public int supportNSessionTypes(int n, int courseID);
	
	public int supportNCourses(int n);
	
	public boolean authenticate(String username, String password);
	
	public String getUserRole(String username);
	
	
	*/
		
		
		DatabaseImpl db=new DatabaseImpl();
	
	    //db.addSession(2, 2, false);
	    System.out.println(db.getTableInfo("session"));
	    System.out.println();
	    System.out.println(db.getTableInfo("course_session"));
	    
	    db.populateMyCampusCourse();
	    db.importMycampusCourse(1);
	    db.addSession(3, 3, true);
	    db.assignRoomToTimetableslot(1, "BO720");
		db.specifySessionRecurrence(4, "Oneoff");
		db.bookTimetableSlot(1, 2, 3, 4);
	    System.out.println(db.checkIfSignedUpForCompulsory(12, 4,2));
	    db.getTimetableslotDetails(13);
	    db.getUserRole("az");
	    System.out.println(db.authenticate("az", "username"));
	    System.out.println(db.supportNCourses(100));
	    System.out.println(db.supportNSessionTypes(10, 1));
	    System.out.println(db.supportNUsers(1000));
	    System.out.println(db.supportNTimetableslotsPerSession(20, 1));
	    
	    
	}
}

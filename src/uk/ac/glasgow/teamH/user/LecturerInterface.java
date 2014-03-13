package uk.ac.glasgow.teamH.user;




public interface LecturerInterface {

	void importMyCampusCourse(int courseID);
	
	void addSession(int courseId, int sessionID, boolean compulsory);
	
	void specifySessionRecurrence(int sessionId, String recurrence);
	
	String getSessionDetails(int sessionId);

	public boolean login(String mail, String pass);

	
}

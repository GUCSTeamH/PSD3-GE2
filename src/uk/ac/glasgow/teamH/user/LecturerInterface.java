package uk.ac.glasgow.teamH.user;



public interface LecturerInterface {

	void importMyCampusCourse(int courseID);
	
	void addSession(int courseId, int sessionID, boolean compulsory);
	
	void specifySessionRecurrence(int sessionId, String recurrence);
	
	String getTimetableslotDetails(int sessionId);

	
}

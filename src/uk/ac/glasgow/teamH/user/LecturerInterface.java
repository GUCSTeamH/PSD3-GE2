package uk.ac.glasgow.teamH.user;

import java.sql.ResultSet;



public interface LecturerInterface {

	void importMyCampusCourse(int courseID);
	
	void addSession(int courseId, int sessionID, boolean compulsory);
	
	void specifySessionRecurrence(int sessionId, String recurrence);
	
	ResultSet getTimetableslotDetails(int sessionId);
	
	ResultSet getTimetablslotStudentDetails(int sessionId);

	
}

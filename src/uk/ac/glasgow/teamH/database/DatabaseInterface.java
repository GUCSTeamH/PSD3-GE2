package uk.ac.glasgow.teamH.database;

import java.sql.ResultSet;




public interface DatabaseInterface {


public void addSession(int courseID, int sessionID,boolean compulsory);

public void importMycampusCourse(int courseID);

public void specifySessionRecurrence(int sessionID,String recurrence);

public void assignRoomToTimetableslot(int timetableslotID, String room);

public void bookTimetableSlot(int studentID, int courseID, int sessionID,int timetableslotID);

public boolean checkIfSignedUpForCompulsory(int studentID, int sessionID,int courseID);

public boolean checkIfSignedUpForAllCompulsory(int studentID);

public int supportNUsers(int n);

public int supportNTimetableslotsPerSession(int n, int sessionID);

public int supportNSessionTypes(int n, int courseID);

public int supportNCourses(int n);

public boolean authenticate(String username, String password);

public String getUserRole(String username);

public ResultSet getSessionsCourse(int courseID);


public boolean checkForClashes(int studentId);

public String getSessionInfo(int sessionId);

public ResultSet getSessionDetails(int sessionID);

public void populateMyCampusCourse(int courseId, String courseName);

public void populateSession(int sessionId);

public void populateTimetableslot(int timetableslotId) ;

public void populateStudent_Course_SessionFully(int studentId,
		int courseId, int sessionId, int timetableslotId, int startTime,
		int endTime, boolean compulsory,boolean refresh) ;

public void populateStudent_Course_SessionPartially(int studentId,
		int courseId, int sessionId, boolean compulsory,boolean refresh);

public void populateSession_Timetableslot(int sessionId, int timetableslotId);

public void populateSession(int sessionId, String recurring	,boolean compulsory, int timetableslotId, int staffId);

public void populateCourse(int courseId);

public ResultSet getTableInfo(String table);

public boolean checkIfSignedUp(int studentID, int sessionID, int courseID);


}

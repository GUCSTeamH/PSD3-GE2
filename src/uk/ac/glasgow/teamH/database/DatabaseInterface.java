package uk.ac.glasgow.teamH.database;

import java.sql.ResultSet;



public interface DatabaseInterface {


public void addSession(int courseID, int sessionID,boolean compulsory);


public void importMycampusCourse(int courseID);

public void specifySessionRecurrence(int sessionID,String recurrence);

public void assignRoomToTimetableslot(int timetableslotID, String room);

public void bookTimetableSlot(int studentID, int courseID, int sessionID,int timetableslotID);

public boolean checkIfSignedUpForCompulsory(int studentID, int sessionID,int courseID);

public ResultSet getTimetableslotDetails(int sessionID);

public int supportNUsers(int n);

public int supportNTimetableslotsPerSession(int n, int sessionID);

public int supportNSessionTypes(int n, int courseID);

public int supportNCourses(int n);

public boolean authenticate(String username, String password);

public String getUserRole(String username);

public ResultSet getSessionsCourse(int courseID);

public ResultSet getTimetableslotStudentDetails(int sessionID);

}

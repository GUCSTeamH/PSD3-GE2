package uk.ac.glasgow.teamH.database.impl;

import java.sql.ResultSet;

/**
 * @author Michael
 * 
 */
public interface DatabaseInterface {

	/**
	 * adds a session to the database
	 * @param courseID string representing course id e.g DIM3 or PSD3
	 * @param recurring boolean on if session is recurring (once a week)
	 * @param compulsory boolean on if session is compulsory
	 */
	public void addSession(String courseID, boolean recurring,
			boolean compulsory);

	/**
	 * adds a timeslot to the database
	 * @param capacity integer value for capacity size
	 * @param startTime string representation of start of timeslot
	 * @param duration integer representing duration of timeslot in minutes
	 * @param day integer representing day of timeslot: 0 = Monday...6 = Sunday
	 * @param room string representing room timeslot has been allocated
	 */
	public void addTimeslot(int capactiy, String startTime, int duration, int day, String room);

	/**
	 * adds a course to the database
	 * @param courseID string representing course id e.g DIM3 or PSD3
	 * @param name string representing full friendly name of course e.g. Professional Software Development
	 */
	public void addCourse(String courseID, String name);

	/**
	 * adds a student to the database
	 * @param studentID string representing student id e.g. 1101669m
	 * @param name string representing full name of student
	 */
	public void addStudent(String studentID, String name);

	/**
	 * adds the specified student to the specified course
	 * @param studentID string representing student id e.g. 1101669m
	 * @param courseID string representing course id e.g DIM3 or PSD3
	 */
	public void addStudentToCourse(String studentID, String courseID);

	/**
	 * signs the specified student up to the specified timeslot
	 * @param timeID integer representing id of timeslot in database
	 * @param studentID string representing student id e.g. 1101669m
	 */
	public void signUpToTimeslot(int timeID, String studentID);

	/**
	 * changes the specified session to be recurring
	 * @param sessionID integer representing the session id in the database
	 */
	public void makeSessionRecurring(int sessionID);

	/**
	 * binds the specified timeslot to the specified session
	 * @param sessionID integer representing the session id in the database
	 * @param timeID integer representing id of timeslot in database
	 */
	public void bookSession(int sessionID, int timeID);

	/**
	 * gets the compulsory course and sessions that the student has not signed up for 
	 * @param courseID string representing course id e.g DIM3 or PSD3
	 * @param studentID string representing student id e.g. 1101669m
	 * @return a {@link ResultSet} containing the session_id and course_id of all compulsory sessions the student has not signed up for
	 */
	public ResultSet checkCompulsorySessions(String studentID);

	/**
	 * gets the details of all sessions and timeslots for the specified course
	 * @param courseID string representing course id e.g DIM3 or PSD3
	 * @return a {@link ResultSet} containing the time, room, tutor_name, student_id and student_name
	 */
	public ResultSet getCourseSessionDetails(int courseID);

}

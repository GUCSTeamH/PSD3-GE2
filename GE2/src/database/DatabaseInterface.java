package database;

import java.sql.ResultSet;

/**
 * @author Michael
 * 
 */
public interface DatabaseInterface {

	/**
	 * @param capacity
	 * @param course
	 * @param recurring
	 * @param compulsory
	 */
	public void addSession(int capacity, String courseID, boolean recurring,
			boolean compulsory);

	/**
	 * @param sessionID
	 * @param startTime
	 * @param endTime
	 * @param day
	 * @param room
	 */
	public void addTimeslot(String startTime, int duration, int day, String room);

	/**
	 * @param courseID
	 * @param name
	 */
	public void addCourse(String courseID, String name);

	/**
	 * @param studentID
	 * @param name
	 */
	public void addStudent(String studentID, String name);

	/**
	 * @param studentID
	 * @param courseID
	 */
	public void addStudentToCourse(String studentID, String courseID);

	/**
	 * @param timeID
	 * @param studentID
	 */
	public void signUpToTimeslot(int timeID, String studentID);

	/**
	 * @param sessionID
	 */
	public void makeSessionRecurring(int sessionID);

	/**
	 * @param sessionID
	 * @param timeID
	 */
	public void bookSession(int sessionID, int timeID);

	/**
	 * @param courseID
	 * @param studentID
	 * @return
	 */
	public ResultSet checkCompulsorySessions(String studentID);

	/**
	 * @param courseID
	 * @return
	 */
	public ResultSet getCourseSessionDetails(int courseID);

}

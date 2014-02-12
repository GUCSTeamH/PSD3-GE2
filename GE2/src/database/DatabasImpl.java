/**
 * 
 */
package database;

import java.sql.ResultSet;

/**
 * @author Michael
 *
 */
public class DatabasImpl implements DatabaseInterface {

	/* (non-Javadoc)
	 * @see database.DatabaseInterface#addSession(int, java.lang.String, boolean, boolean)
	 */
	@Override
	public void addSession(int capacity, String course, boolean recurring,
			boolean compulsory) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see database.DatabaseInterface#addTimeslot(int, java.lang.String, java.lang.String, int, java.lang.String)
	 */
	@Override
	public void addTimeslot(int sessionID, String startTime, String endTime,
			int day, String room) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see database.DatabaseInterface#addCourse(java.lang.String, java.lang.String)
	 */
	@Override
	public void addCourse(String courseID, String name) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see database.DatabaseInterface#addStudent(java.lang.String, java.lang.String)
	 */
	@Override
	public void addStudent(String studentID, String name) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see database.DatabaseInterface#addStudentToCourse(java.lang.String, java.lang.String)
	 */
	@Override
	public void addStudentToCourse(String studentID, String courseID) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see database.DatabaseInterface#signUpToTimeslot(int, java.lang.String)
	 */
	@Override
	public void signUpToTimeslot(int timeID, String studentID) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see database.DatabaseInterface#makeSessionRecurring(int)
	 */
	@Override
	public void makeSessionRecurring(int sessionID) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see database.DatabaseInterface#bookSession(int, int)
	 */
	@Override
	public void bookSession(int sessionID, int timeID) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see database.DatabaseInterface#checkCompulsorySessions(java.lang.String, java.lang.String)
	 */
	@Override
	public ResultSet checkCompulsorySessions(String courseID, String studentID) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see database.DatabaseInterface#getCourseSessionDetails(int)
	 */
	@Override
	public ResultSet getCourseSessionDetails(int courseID) {
		// TODO Auto-generated method stub
		return null;
	}

}

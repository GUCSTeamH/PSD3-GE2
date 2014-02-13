/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Michael
 *
 */
public class DatabasImpl implements DatabaseInterface {
	Connection connection = null;
	
	
	void connect() {
		try {
			// create a database connection
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:data/BookingSystem.db");
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
	}

	
	
	void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	void createTables() {
		try {
			Statement statement = connection.createStatement();
			statement.addBatch("BEGIN;");
			statement
					.addBatch("CREATE TABLE IF NOT EXISTS course(course_id TEXT PRIMARY KEY, course_name TEXT);");
			statement
					.addBatch("CREATE TABLE IF NOT EXISTS student(student_id TEXT PRIMARY KEY, student_name TEXT);");
			statement
					.addBatch("CREATE TABLE IF NOT EXISTS session(session_id INTEGER PRIMARY KEY AUTOINCREMENT, course_id TEXT, capacity INTEGER, recurring BOOLEAN, compulsory BOOLEAN);");
			statement
					.addBatch("CREATE TABLE IF NOT EXISTS tutor(tutor_id TEXT PRIMARY KEY, tutor_name TEXT);");
			statement
					.addBatch("CREATE TABLE IF NOT EXISTS timeslot(timeslot_id INTEGER PRIMARY KEY AUTOINCREMENT, time TEXT, day INTEGER, room TEXT, session_id INTEGER, tutor_id TEXT);");
			statement
					.addBatch("CREATE TABLE IF NOT EXISTS student_course(student_id TEXT, course_id TEXT);");
			statement
					.addBatch("CREATE TABLE IF NOT EXISTS student_session(student_id TEXT, session_id INTEGER);");
			statement
					.addBatch("CREATE TABLE IF NOT EXISTS student_timeslot(student_id TEXT, timeslot_id INTEGER);");
			statement.addBatch("COMMIT;");
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	/* (non-Javadoc)
	 * @see database.DatabaseInterface#addSession(int, java.lang.String, boolean, boolean)
	 */
	@Override
	public void addSession(int capacity, String courseID, boolean recurring,
			boolean compulsory) {
		try {
			Statement statement = connection.createStatement();
			statement.addBatch("BEGIN;");
			statement
					.addBatch("INSERT INTO session (course_id, capacity, recurring, compulsory) VALUES ("
							+ courseID
							+ ","
							+ capacity
							+ ","
							+ recurring
							+ ","
							+ compulsory + ")");
			statement.addBatch("COMMIT;");
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try{
			Statement statement = connection.createStatement();
			statement.addBatch("BEGIN;");
			statement.addBatch("INSERT INTO course(course_id, course_name) VALUES (" + courseID + ", " + name + ")");
			statement.addBatch("COMMIT;");
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generate catch block
			e.printStackTrace();
		}
	}

	
	/* (non-Javadoc)
	 * @see database.DatabaseInterface#addStudent(java.lang.String, java.lang.String)
	 */
	@Override
	public void addStudent(String studentID, String name) {
		// TODO Auto-generated method stub
		try {
			Statement statement = connection.createStatement();
			statement.addBatch("BEGIN;");
			statement
					.addBatch("INSERT INTO student(student_id, student_name) VALUES ("
							+ studentID + ", " + name + ")");
			statement.addBatch("COMMIT;");
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
	}

	
	/* (non-Javadoc)
	 * @see database.DatabaseInterface#addStudentToCourse(java.lang.String, java.lang.String)
	 */
	@Override
	public void addStudentToCourse(String studentID, String courseID) {
		// TODO Auto-generated method stub
		try {
			Statement statement = connection.createStatement();
			statement.addBatch("BEGIN;");
			statement
					.addBatch("INSERT INTO student_course(student_id, course_id) VALUES ("
							+ studentID + ", " + courseID + ")");
			statement.addBatch("COMMIT;");
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
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

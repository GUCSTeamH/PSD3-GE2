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
			connection = DriverManager
					.getConnection("jdbc:sqlite:data/BookingSystem.db");
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
					.addBatch("CREATE TABLE IF NOT EXISTS timeslot(timeslot_id INTEGER PRIMARY KEY AUTOINCREMENT, time TEXT, duration INTEGER, day INTEGER, room TEXT, session_id INTEGER, tutor_id TEXT);");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.DatabaseInterface#addSession(int, java.lang.String,
	 * boolean, boolean)
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
							+ compulsory + ");");
			statement.addBatch("COMMIT;");
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.DatabaseInterface#addTimeslot(int, java.lang.String,
	 * java.lang.String, int, java.lang.String)
	 */
	@Override
	public void addTimeslot(String startTime, int duration, int day, String room) {
		try {
			Statement statement = connection.createStatement();
			statement.addBatch("BEGIN;");
			statement
					.addBatch("INSERT INTO timeslot (time, duration, day, room) VALUES ("
							+ startTime
							+ ","
							+ duration
							+ ","
							+ day
							+ ","
							+ room + ");");
			statement.addBatch("COMMIT;");
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.DatabaseInterface#addCourse(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void addCourse(String courseID, String name) {
		try {
			Statement statement = connection.createStatement();
			statement.addBatch("BEGIN;");
			statement
					.addBatch("INSERT INTO course(course_id, course_name) VALUES ("
							+ courseID + ", " + name + ");");
			statement.addBatch("COMMIT;");
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generate catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.DatabaseInterface#addStudent(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void addStudent(String studentID, String name) {
		try {
			Statement statement = connection.createStatement();
			statement.addBatch("BEGIN;");
			statement
					.addBatch("INSERT INTO student(student_id, student_name) VALUES ("
							+ studentID + ", " + name + ");");
			statement.addBatch("COMMIT;");
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.DatabaseInterface#addStudentToCourse(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void addStudentToCourse(String studentID, String courseID) {
		try {
			Statement statement = connection.createStatement();
			statement.addBatch("BEGIN;");
			statement
					.addBatch("INSERT INTO student_course(student_id, course_id) VALUES ("
							+ studentID + ", " + courseID + ");");
			statement.addBatch("COMMIT;");
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.DatabaseInterface#signUpToTimeslot(int, java.lang.String)
	 */
	@Override
	public void signUpToTimeslot(int timeID, String studentID) {
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT session_id FROM timeslot WHERE timeslot_id = "
							+ timeID + ";");
			int session_id = rs.getInt("session_id");

			statement.addBatch("BEGIN;");
			statement
					.addBatch("INSERT INTO student_timeslot(timeslot_id, student_id) VALUES ("
							+ timeID + "," + studentID + ");");
			statement
					.addBatch("INSERT INTO student_session(student_id, session_id) VALUES ("
							+ studentID + ", " + session_id + ")");
			statement.addBatch("COMMIT;");
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.DatabaseInterface#makeSessionRecurring(int)
	 */
	@Override
	public void makeSessionRecurring(int sessionID) {
		try {
			Statement statement = connection.createStatement();
			statement.addBatch("BEGIN;");
			statement
					.addBatch("UPDATE session SET recurring = 1 WHERE session_id = "
							+ sessionID + ";");
			statement.addBatch("COMMIT;");
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.DatabaseInterface#bookSession(int, int)
	 */
	@Override
	public void bookSession(int sessionID, int timeID) {
		try {
			Statement statement = connection.createStatement();
			statement.addBatch("BEGIN;");
			statement.addBatch("UPDATE timeslot SET session_id = " + sessionID
					+ " WHERE time_id = " + timeID + ";");
			statement.addBatch("COMMIT;");
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.DatabaseInterface#checkCompulsorySessions(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ResultSet checkCompulsorySessions(String studentID) {
		ResultSet rs = null;
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			rs = statement
					.executeQuery("SELECT student_course.course_id, session.session_id FROM (SELECT s.session_id, s.course_id FROM session AS s WHERE s.compulsory = 1) AS session INNER JOIN (SELECT c.course_id FROM course AS c) AS course ON session.course_id = course.course_id INNER JOIN student_course ON student_course.course_id = course.course_id INNER JOIN (SELECT st.student_id FROM student AS st WHERE st.student_id = "
							+ studentID
							+ ") AS student ON student.student_id = student_course.student_id WHERE session.session_id NOT IN (SELECT s_s.session_id FROM student_session AS s_s WHERE s_s.student_id = student.student_id)");
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.DatabaseInterface#getCourseSessionDetails(int)
	 */
	@Override
	public ResultSet getCourseSessionDetails(int courseID) {
		ResultSet rs = null;
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			rs = statement
					.executeQuery("SELECT timeslot.time, timeslot.room, tutor.tutor_name, student.student_id, student.student_name FROM (SELECT s.session_id FROM session AS s WHERE course_id = "
							+ courseID
							+ ") AS session INNER JOIN (SELECT t.time, t.room, t.tutor_id, t.timeslot_id, t.session_id FROM timeslot AS t) AS timeslot ON session.session_id = timeslot.session_id INNER JOIN (SELECT st.student_id, st.timeslot_id FROM student_timeslot AS st) AS student_timeslot ON timeslot.timeslot_id = student_timeslot.timeslot_id INNER JOIN (SELECT s.student_name, s.student_id FROM student AS s) AS student ON student.student_id = student_timeslot.student_id INNER JOIN (SELECT tt.tutor_id, tt.tutor_name FROM tutor AS tt ) AS tutor ON tutor.tutor_id = timeslot.tutor_id");
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
		return rs;
	}

}

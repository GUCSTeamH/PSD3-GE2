package uk.ac.glasgow.teamH.database.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Michael
 * 
 */
public class DatabaseImpl implements DatabaseInterface {
	Connection connection = null;

	void connect() {
		try {
			// create a database connection
			// Class.forName("org.sqlite.JDBC");
			connection = DriverManager
					.getConnection("jdbc:derby:data/BookingSystem;create=true");
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
/*		} catch (ClassNotFoundException e) {
			e.printStackTrace();*/
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
			connection = DriverManager
					.getConnection("jdbc:derby:data/BookingSystem;create=true");
			Statement statement = connection.createStatement();
			if (!tableExists("course")) {
				statement
						.execute("CREATE TABLE course(course_id VARCHAR(128) PRIMARY KEY, course_name VARCHAR(128), session_id INTEGER, FOREIGN KEY (session_id) REFERENCES session (session_id))");
			}

			if (!tableExists("student")) {
				statement
						.execute("CREATE TABLE student(student_id VARCHAR(128) PRIMARY KEY, student_name VARCHAR(128),course_id VARCHAR(128),session_id INTEGER, timetableslot_id INTEGER,FOREIGN KEY (course_id) REFERENCES course (course_id), FOREIGN KEY (session_id) REFERENCES session (session_id),FOREIGN KEY (session_id) REFERENCES timetableslot (timetableslot_id))");
			}

			if (!tableExists("session")) {
				statement
						.execute("CREATE TABLE session(session_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), recurring VARCHAR(128), compulsory BOOLEAN,timetableslot_id INTEGER, staff_id INTEGER, FOREIGN KEY (timetableslot_id) REFERENCES timetableslot (timetableslot_id),  FOREIGN KEY (staff_id) REFERENCES staff (staff_id))");
			}

			if (!tableExists("staff")) {
				statement
						.execute("CREATE TABLE staff(staff_id INTEGER PRIMARY KEY, staff_name VARCHAR(128))");
			}

			if (!tableExists("timetableslot")) {
				statement
						.execute("CREATE TABLE timetableslot(timetableslot_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), capacity INTEGER, starttime INTEGER, endtime INTEGER, weekday INTEGER, weeknumber INTEGER, room VARCHAR(128), occupied BOOLEAN)");
			}

			if (!tableExists("student_course")) {
				statement
						.execute("CREATE TABLE student_course(student_id VARCHAR(128), course_id VARCHAR(128))");
			}

			if (!tableExists("student_session")) {
				statement
						.execute("CREATE TABLE student_session(student_id VARCHAR(128), session_id INTEGER)");
			}

			if (!tableExists("student_timeslot")) {
				statement
						.execute("CREATE TABLE student_timeslot(student_id VARCHAR(128), timeslot_id INTEGER)");
			}
			connection.close();
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
	public void addSession(String courseID, boolean recurring,
			boolean compulsory) {
		try {
			connection = DriverManager
					.getConnection("jdbc:derby:data/BookingSystem;create=true");
			int recurringInt = (recurring) ? 1 : 0;
			int compulsoryInt = (compulsory) ? 1 : 0;
			Statement statement = connection.createStatement();
			statement
					.addBatch("INSERT INTO session (course_id, recurring, compulsory) VALUES ("
							+ courseID
							+ ","
							+ recurringInt
							+ ","
							+ compulsoryInt + ");");
			statement.executeBatch();
			connection.close();
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
	public void addTimeslot(int capacity, String startTime, int duration,
			int day, String room) {
		try {
			connection = DriverManager
					.getConnection("jdbc:derby:data/BookingSystem;create=true");
			Statement statement = connection.createStatement();
//			statement.addBatch("BEGIN;");
			statement
					.addBatch("INSERT INTO timeslot (time, duration, day, room, capacity) VALUES ("
							+ startTime
							+ ","
							+ duration
							+ ","
							+ day
							+ ","
							+ room + "," + capacity + ");");
			statement.executeBatch();
			connection.close();
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
			connection = DriverManager
					.getConnection("jdbc:derby:data/BookingSystem;create=true");
			Statement statement = connection.createStatement();
			statement
					.addBatch("INSERT INTO course(course_id, course_name) VALUES ("
							+ courseID + ", " + name + ");");
			statement.executeBatch();
			connection.close();
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
			connection = DriverManager
					.getConnection("jdbc:derby:data/BookingSystem;create=true");
			Statement statement = connection.createStatement();
			statement
					.addBatch("INSERT INTO student(student_id, student_name) VALUES ("
							+ studentID + ", " + name + ");");
			connection.close();
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
			connection = DriverManager
					.getConnection("jdbc:derby:data/BookingSystem;create=true");
			Statement statement = connection.createStatement();
			statement
					.addBatch("INSERT INTO student_course(student_id, course_id) VALUES ("
							+ studentID + ", " + courseID + ");");
			statement.executeBatch();
			connection.close();
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
			connection = DriverManager
					.getConnection("jdbc:derby:data/BookingSystem;create=true");
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT session_id FROM timeslot WHERE timeslot_id = "
							+ timeID + ";");
			int session_id = rs.getInt("session_id");

			statement
					.addBatch("INSERT INTO student_timeslot(timeslot_id, student_id) VALUES ("
							+ timeID + "," + studentID + ");");
			statement
					.addBatch("INSERT INTO student_session(student_id, session_id) VALUES ("
							+ studentID + ", " + session_id + ")");
			statement.executeBatch();
			connection.close();
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
			connection = DriverManager
					.getConnection("jdbc:derby:data/BookingSystem;create=true");
			Statement statement = connection.createStatement();
			statement
					.addBatch("UPDATE session SET recurring = 1 WHERE session_id = "
							+ sessionID + ";");
			statement.executeBatch();
			connection.close();
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
			connection = DriverManager
					.getConnection("jdbc:derby:data/BookingSystem;create=true");
			Statement statement = connection.createStatement();
			statement.addBatch("UPDATE timeslot SET session_id = " + sessionID
					+ " WHERE time_id = " + timeID + ";");
			statement.executeBatch();
			connection.close();
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
			connection = DriverManager
					.getConnection("jdbc:derby:data/BookingSystem;create=true");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			rs = statement
					.executeQuery("SELECT student_course.course_id, session.session_id FROM (SELECT s.session_id, s.course_id FROM session AS s WHERE s.compulsory = 1) AS session INNER JOIN (SELECT c.course_id FROM course AS c) AS course ON session.course_id = course.course_id INNER JOIN student_course ON student_course.course_id = course.course_id INNER JOIN (SELECT st.student_id FROM student AS st WHERE st.student_id = "
							+ studentID
							+ ") AS student ON student.student_id = student_course.student_id WHERE session.session_id NOT IN (SELECT s_s.session_id FROM student_session AS s_s WHERE s_s.student_id = student.student_id)");
			connection.close();
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
			connection = DriverManager
					.getConnection("jdbc:derby:data/BookingSystem;create=true");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			rs = statement
					.executeQuery("SELECT timeslot.time, timeslot.room, tutor.tutor_name, student.student_id, student.student_name FROM (SELECT s.session_id FROM session AS s WHERE course_id = "
							+ courseID
							+ ") AS session INNER JOIN (SELECT t.time, t.room, t.tutor_id, t.timeslot_id, t.session_id FROM timeslot AS t) AS timeslot ON session.session_id = timeslot.session_id INNER JOIN (SELECT st.student_id, st.timeslot_id FROM student_timeslot AS st) AS student_timeslot ON timeslot.timeslot_id = student_timeslot.timeslot_id INNER JOIN (SELECT s.student_name, s.student_id FROM student AS s) AS student ON student.student_id = student_timeslot.student_id INNER JOIN (SELECT tt.tutor_id, tt.tutor_name FROM tutor AS tt ) AS tutor ON tutor.tutor_id = timeslot.tutor_id");
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
		return rs;
	}
	
	public Boolean tableExists(String tableName) 
			throws SQLException{
			
		connection = DriverManager
				.getConnection("jdbc:derby:data/BookingSystem;create=true");
			
		DatabaseMetaData metaData = 
			connection.getMetaData();
							
		ResultSet resultSet = 
			metaData.getTables(
				null,
				null,
				tableName.toUpperCase(),
				new String[]{"TABLE"});
			
		Boolean result = resultSet.next();

		resultSet.close();
		connection.close();
		
		return result;
	}

}

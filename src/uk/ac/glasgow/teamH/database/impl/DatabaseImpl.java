package uk.ac.glasgow.teamH.database.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import uk.ac.glasgow.teamH.database.DatabaseInterface;

/**
 * @author Emilia
 * 
 */
public class DatabaseImpl implements DatabaseInterface {
	static Connection connection = null;
	Statement stmt = null;
	public static ResultSet coursetable;
	private static final String connectionString = "jdbc:derby:data/mycampusH;create=true";

	public DatabaseImpl() {
		try {
			connection = getDatabaseConnection();
			createTables();
		}

		catch (SQLException e) {
			System.out.println("Couldn't connect to the specified database!");
			e.printStackTrace();
		}
	}

	private static Connection getDatabaseConnection() throws SQLException {
		return DriverManager.getConnection(connectionString);
	}

	void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	static void createTables() {
		try {
			System.out.println("In create tables");
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			if (!tableExists("course")) {
				statement
						.execute("CREATE TABLE course(course_id INTEGER PRIMARY KEY, course_name VARCHAR(128))");
				System.out.println("Created course table");
			}

			if (!tableExists("session")) {
				statement
						.execute("CREATE TABLE session(session_id INTEGER PRIMARY KEY, recurring VARCHAR(128), compulsory BOOLEAN,timetableslot_id INTEGER, staff_id INTEGER)");
				System.out.println("Created session table");
			}

			if (!tableExists("timetableslot")) {
				statement
						.execute("CREATE TABLE timetableslot(timetableslot_id INTEGER, capacity INTEGER, starttime INTEGER, endtime INTEGER, weekday INTEGER, weeknumber INTEGER, room VARCHAR(128), occupied BOOLEAN, staff_id INTEGER,PRIMARY KEY(timetableslot_id))");
				System.out.println("created timetable slot table");

			}

			if (!tableExists("student_course_session")) {
				statement
						.execute("CREATE TABLE student_course_session(student_id INTEGER, course_id INTEGER,session_id INTEGER,timetableslot_id INTEGER,starttime INTEGER, endtime INTEGER, compulsory BOOLEAN, PRIMARY KEY(student_id,course_id,session_id))");
				System.out.println("Created student_course_session table");
			}

			if (!tableExists("session_timetableslot")) {
				statement
						.execute("CREATE TABLE session_timetableslot(session_id INTEGER, timetableslot_id INTEGER)");
				System.out.println("Created session_timetableslot table");
			}

			if (!tableExists("mycampus_authentication")) {
				statement
						.execute("CREATE TABLE mycampus_authentication(username VARCHAR(128) NOT NULL PRIMARY KEY, password VARCHAR(128) NOT NULL UNIQUE, usertype VARCHAR(128))");
				System.out.println("Created authentication table");
			}

			if (!tableExists("mycampus_course")) {
				statement
						.execute("CREATE TABLE mycampus_course(course_id INTEGER PRIMARY KEY, course_name VARCHAR(128), staff_id INTEGER)");
				System.out.println("Created mycampus_course table");

			}
			if (!tableExists("course_session")) {
				statement
						.execute("CREATE TABLE course_session(course_id INTEGER,session_id INTEGER,PRIMARY KEY(course_id, session_id))");
				System.out.println("Created course_session table");

			}

			connection.close();
		} catch (SQLException e) {
			System.out.println("Error while creating the tables");
			e.printStackTrace();
		}
	}

	// 2.-OK
	@Override
	public void addSession(int courseID, int sessionID, boolean compulsory) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			statement.addBatch("DELETE FROM Session)");
			statement.executeBatch();
			statement.addBatch("DELETE FROM course_session )");
			statement.executeBatch();
			statement
					.addBatch("INSERT INTO session (session_id, compulsory) VALUES ("
							+ sessionID + "," + compulsory + ")");
			statement.executeBatch();

			statement
					.addBatch("INSERT INTO course_session (course_id, session_id) VALUES ("
							+ courseID + "," + sessionID + ")");
			statement.executeBatch();

		} catch (SQLException e) {
			System.out.println("Error while adding a new session");
			e.printStackTrace();
		}

	}

	public ResultSet getTableInfo(String table)  {
		try{
		connection = getDatabaseConnection();
		Statement statement = connection.createStatement();

		String query = "SELECT * FROM " + table;
		ResultSet result = statement.executeQuery(query);
		return result;
		}
		catch(SQLException e){
			System.out.println("Error while getting table info");
			e.printStackTrace();
			return null;
		}

	}

	// ???????????????????
	public ResultSet getSessionDetails(int sessionID) {
		ResultSet result = null;
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM session WHERE session_id = "
					+ sessionID;
			result = statement.executeQuery(query);
			// connection.close();
		} catch (SQLException e) {
			// TODO Auto-generate method stub
			e.printStackTrace();
		}
		return result;
	}

	// 14
	public String getSessionInfo(int sessionID) {
		String details = "";
		ResultSet result = null;
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			String query1 = "SELECT * FROM session WHERE session_id = "
					+ sessionID;
			result = statement.executeQuery(query1);
			if (result.next()) {
				details += "Session id: " + result.getInt(1) + ", recurring: "
						+ result.getString(2) + ", compulsory: "
						+ result.getInt(3) + ", staff id: " + result.getInt(5)
						+ "students: ";
			}

			String query2 = "SELECT student_id FROM student_course_session WHERE session_id = "
					+ sessionID;
			result = statement.executeQuery(query2);
			while (result.next()) {
				details += result.getInt(1) + "; ";
			}
			// connection.close();
		} catch (SQLException e) {
			System.out.println("Error while trying to get session details");
			e.printStackTrace();
		}
		return details;
	}

	// 8-OK
	@Override
	public void assignRoomToTimetableslot(int timetableslotID, String room) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch("UPDATE timetableslot SET room = " + "'" + room
					+ "'" + " WHERE timetableslot_id = " + timetableslotID);
			statement.executeBatch();

		} catch (SQLException e) {
			System.out.println("Error while assigning room to a timetableslot");
			e.printStackTrace();
		}
	}

	// 1-OK
	@Override
	public void importMycampusCourse(int courseID) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			statement
			.addBatch("Delete from course");
	statement.executeBatch();
			String getCourseFromMyCampus = "SELECT * FROM mycampus_course WHERE course_id="
					+ courseID;
			ResultSet info = statement.executeQuery(getCourseFromMyCampus);
			if(	info.next()){
			statement
					.addBatch("INSERT INTO course(course_id, course_name) VALUES ("

							+ info.getInt(1)
							+ ","
							+ "'"
							+ info.getString(2)
							+ "')");
			statement.executeBatch();
			}
		} catch (SQLException e) {
			System.out.println("Error while trying to import from MyCampus");
			e.printStackTrace();
		}
	}

	// 12
	public boolean checkIfSignedUp(int studentID, int sessionID, int courseID) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			String query = "SELECT s.session_id FROM session AS s, student_course_session AS scs WHERE student_id = "
					+ studentID
					+ " AND scs.timetableslot_id IS NOT NULL AND s.session_id = scs.session_id";
			ResultSet result = statement.executeQuery(query);
			boolean r = result.next();
			connection.close();
			return r;
		} catch (SQLException e) {
			System.out.println("Error while checking if a student had signed up for a session");
			e.printStackTrace();
			return false;
		}

	}

	// 4-OK
	@Override
	public void specifySessionRecurrence(int sessionID, String recurrence) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate("UPDATE session SET recurring = " + "'"
					+ recurrence + "'" + " WHERE session_id = " + sessionID);

			connection.close();
		} catch (SQLException e) {
			System.out
					.println("Error while trying to specify session recurrence");
			e.printStackTrace();
		}
	}

	// 11-OK
	@Override
	public void bookTimetableSlot(int studentID, int courseID, int sessionID,
			int timetableslotID) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement
					.addBatch("UPDATE student_course_session SET timetableslot_id = "
							+ timetableslotID
							+ " WHERE session_id = "
							+ sessionID
							+ " AND student_id = "
							+ studentID
							+ " AND course_id = " + courseID);
			statement.executeBatch();

			connection.close();
		} catch (SQLException e) {
			System.out.println("Error while trying to book a timetableslot");
			e.printStackTrace();
		}
	}

	// 12 
	@Override
	public boolean checkIfSignedUpForCompulsory(int studentID, int sessionID,
			int courseID) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			String query = "SELECT s.session_id FROM session AS s, student_course_session AS scs WHERE student_id = "
					+ studentID
					+ " AND scs.timetableslot_id IS NOT NULL AND s.session_id = scs.session_id AND compulsory = true";
			ResultSet result = statement.executeQuery(query);
			boolean r = result.next();
			connection.close();
			return r;
		} catch (SQLException e) {
			System.out.println("Error while trying to check if a student has signed up for all compulsory sessions");
			e.printStackTrace();
			return false;
		}

	}

	public ResultSet getSessionsCourse(int courseID) {

		ResultSet detailsResult = null;
		System.out.println("dede");
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			String queryDetails = "SELECT session_iD FROM course_session WHERE course_id = "
					+ courseID;
			detailsResult = statement.executeQuery(queryDetails);

		} catch (SQLException e) {
			System.out
					.println("Error while trying to get session for a course");
			e.printStackTrace();
		}
		return detailsResult;
	}

	// non-functional distinguish between user types-OK
	public String getUserRole(String username) {
		String r = "";
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement
					.addBatch("INSERT INTO mycampus_authentication(username,password,usertype) VALUES ("
							+ "'"
							+ username
							+ "'"
							+ ","
							+ "'"
							+ "username"
							+ "'" + "," + "'" + "student" + "'" + ")");
			statement.executeBatch();
			String query = "SELECT usertype FROM mycampus_authentication WHERE username= "
					+ "'" + username + "'";
			ResultSet result = statement.executeQuery(query);

			if (result.next()) {
				r = "Type:" + result.getString(1);
			}
			System.out.println(r);
			connection.close();

		} catch (SQLException e) {
			System.out.println("Error while getting user role");
			e.printStackTrace();

		}
		return r;

	}

	// non-functional authenticate user-OK
	public boolean authenticate(String username, String password) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			String query = "SELECT * FROM mycampus_authentication WHERE username= "
					+ "'"
					+ username
					+ "'"
					+ " AND password = "
					+ "'"
					+ password + "'";
			ResultSet result = statement.executeQuery(query);
			boolean r = result.next();
			connection.close();
			return r;
		} catch (SQLException e) {
			System.out
					.println("Error while trying to authenticate from MyCampus");
			e.printStackTrace();
			return false;
		}

	}

	// non-functional support at least 100 courses-OK
	public int supportNCourses(int n) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch("Delete from course ");
			statement.executeBatch();
			for (int i = 0; i < n; i++) {
				statement.addBatch("INSERT INTO course (course_id) VALUES ("
						+ i + ")");
				statement.executeBatch();
			}

			String query = "SELECT * FROM course";

			ResultSet result = statement.executeQuery(query);
			int count = 0;
			while (result.next()) {
				count++;
			}

			connection.close();
			return count;
		} catch (SQLException e) {
			System.out
					.println("Error while trying to insert int course in supportNCourses method");
			e.printStackTrace();
			return 0;
		}

	}

	// non-functional support at least 10 sessions per course-OK
	public int supportNSessionTypes(int n, int courseID) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch("Delete from course_session ");
			statement.executeBatch();

			for (int i = 0; i < n; i++) {
				statement
						.addBatch("INSERT INTO course_session (course_id,session_id) VALUES ("
								+ courseID + "," + i + ")");
				statement.executeBatch();
			}

			String query = "SELECT count(session_id) FROM course_session WHERE course_id ="
					+ courseID + " GROUP BY course_id";

			ResultSet result = statement.executeQuery(query);
			int count = 0;
			if (result.next())
				count = result.getInt(1);

			connection.close();
			return count;
		} catch (SQLException e) {
			System.out
					.println("Error while trying to open the connection in supportNSessionTypes method");
			e.printStackTrace();
			return 0;
		}

	}

	// non-functional support at least 20 timetableslots per session
	public int supportNTimetableslotsPerSession(int n, int sessionID) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch("Delete from timetableslot ");
			statement.executeBatch();

			for (int i = 0; i < n; i++) {
				statement
						.addBatch("INSERT INTO timetableslot (timetableslot_id,session_id) VALUES ("
								+ i + "," + sessionID + ")");
				statement.executeBatch();
			}

			String query = "SELECT count(timetableslot_id) FROM timetableslot WHERE session_id ="
					+ sessionID + " GROUP BY session_id";

			ResultSet result = statement.executeQuery(query);
			int count = 0;
			if (result.next())
				count = result.getInt(1);

			connection.close();
			return count;
		} catch (SQLException e) {
			System.out
					.println("Error while trying to open the connection in supportNTimetableslotsPerSession method");
			e.printStackTrace();
			return 0;
		}

	}

	// non-functional support at least 1000 users-OK
	public int supportNUsers(int n) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch("Delete from mycampus_authentication ");
			statement.executeBatch();

			for (int i = 0; i < n; i++) {
				statement
						.addBatch("INSERT INTO mycampus_authentication (username,password) VALUES ("
								+ "'" + i + "'" + "," + "'" + i + "'" + ")");
				statement.executeBatch();
			}

			String query = "SELECT * FROM mycampus_authentication";

			ResultSet result = statement.executeQuery(query);
			int count = 0;
			while (result.next()) {
				count++;
			}

			connection.close();
			return count;
		} catch (SQLException e) {
			System.out
					.println("Error while trying to open the connection in supportNUsers method");
			e.printStackTrace();
			return 0;
		}

	}

	public static Boolean tableExists(String tableName) throws SQLException {

		connection = getDatabaseConnection();

		DatabaseMetaData metaData = connection.getMetaData();

		ResultSet resultSet = metaData.getTables(null, null,
				tableName.toUpperCase(), new String[] { "TABLE" });

		Boolean result = resultSet.next();

		resultSet.close();
		connection.close();

		return result;
	}

	public boolean timesOverlap(int startTime1, int endTime1, int startTime2,
			int endTime2) {

		if (endTime1 <= startTime2 || startTime1 >= endTime2) {
			return false;
		}

		else {
			return true;
		}

	}

	public boolean checkForClashes(int studentId) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			String query = "SELECT starttime,endtime FROM student_course_session as scs WHERE student_id= "
					+ studentId;
			ResultSet result = statement.executeQuery(query);

			LinkedList<int[]> timetableslotsForStudent = new LinkedList<int[]>();

			while (result.next()) {
				int startEnd[] = new int[2];
				int startTime = result.getInt(1);
				int endTime = result.getInt(2);
				startEnd[0] = startTime;
				startEnd[1] = endTime;
				timetableslotsForStudent.add(startEnd);
			}

			for (int i = 0; i < timetableslotsForStudent.size() - 1; i++) {
				for (int j = i + 1; j < timetableslotsForStudent.size(); j++) {

					int startTime1 = timetableslotsForStudent.get(i)[0];
					int endTime1 = timetableslotsForStudent.get(i)[1];
					int startTime2 = timetableslotsForStudent.get(j)[0];
					int endTime2 = timetableslotsForStudent.get(j)[1];
					System.out.println(startTime1);
					System.out.println(endTime1);
					System.out.println(startTime2);
					System.out.println(endTime2);
					if (timesOverlap(startTime1, endTime1, startTime2, endTime2)) {
						return true;
					}
				}

			}

			connection.close();
		} catch (SQLException e) {
			System.out.println("Error while checking for clashes");
			e.printStackTrace();

		}
		return false;
	}

	public void populateMyCampusCourse(int courseId, String courseName) {
		String deletion = "DELETE FROM mycampus_course";
		String insertion = "INSERT INTO mycampus_course (course_id, course_name) VALUES ("
				+ courseId + "," + "'"+courseName+"')";

		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch(deletion);
			statement.executeBatch();

			statement.addBatch(insertion);
			statement.executeBatch();
		} catch (SQLException e) {
			System.out
					.println("Error while trying to populate mycampus_course");
			e.printStackTrace();
		}

	}

	public void populateSession(int sessionId) {
		String deletion = "DELETE FROM Session";
		String insertion = "INSERT INTO Session (session_id) VALUES ("
				+ sessionId + ")";

		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch(deletion);
			statement.executeBatch();

			statement.addBatch(insertion);
			statement.executeBatch();
		} catch (SQLException e) {
			System.out.println("Error while trying to populate session");
			e.printStackTrace();
		}
	}

	public void populateTimetableslot(int timetableslotId) {
		String deletion = "DELETE FROM Timetableslot";
		String insertion = "INSERT INTO Timetableslot (timetableslot_id) VALUES "
				+ timetableslotId + ")";

		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch(deletion);
			statement.executeBatch();

			statement.addBatch(insertion);
			statement.executeBatch();
		} catch (SQLException e) {
			System.out.println("Error while trying to populate timetableslot");
			e.printStackTrace();
		}
	}

	public void populateStudent_Course_SessionFully(int studentId,
			int courseId, int sessionId, int timetableslotId, int startTime,
			int endTime, boolean compulsory) {
		String deletion = "DELETE FROM student_course_session";
		String insertion = "INSERT INTO student_course_session (student_id,course_id,session_id,timetableslot_id,starttime,endtime) VALUES "
				+ studentId
				+ ","
				+ courseId
				+ ","
				+ sessionId
				+ ","
				+ timetableslotId
				+ ","
				+ startTime
				+ ","
				+ endTime
				+ ","
				+ compulsory + ")";

		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch(deletion);
			statement.executeBatch();

			statement.addBatch(insertion);
			statement.executeBatch();
		} catch (SQLException e) {
			System.out.println("Error while trying to populate student_course_session fully");
			e.printStackTrace();
		}
	}

	public void populateStudent_Course_SessionPartially(int studentId,
			int courseId, int sessionId, boolean compulsory) {
		String deletion = "DELETE FROM student_course_session";
		String insertion = "INSERT INTO student_course_session (student_id,course_id,session_id,timetableslot_id,starttime,endtime,compulsory) VALUES "
				+ studentId
				+ ","
				+ courseId
				+ ","
				+ sessionId
				+ ","
				+ compulsory + ")";

		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch(deletion);
			statement.executeBatch();

			statement.addBatch(insertion);
			statement.executeBatch();
		} catch (SQLException e) {
			System.out.println("Error while trying to populate student_course_session partially");
			e.printStackTrace();
		}
	}

	public void populateSession_Timetableslot(int sessionId, int timetableslotId) {
		String deletion = "DELETE FROM student_course_session";
		String insertion = "INSERT INTO session_timetableslot (session_id,timetableslot_id) VALUES "
				+ sessionId + "," + timetableslotId + ")";

		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch(deletion);
			statement.executeBatch();

			statement.addBatch(insertion);
			statement.executeBatch();
		} catch (SQLException e) {
			System.out.println("Error while trying to populate session_timetableslot");
			e.printStackTrace();
		}
	}

	public void populateSession(int sessionId, String recurring,
			boolean compulsory, int timetableslotId, int staffId) {
		String deletion = "DELETE FROM Session";
		String insertion = "INSERT INTO Session (session_id,recurring,compulsory,timetableslot_id,staff_id) VALUES "
				+ sessionId
				+ ",'"
				+ recurring
				+ "',"
				+ compulsory
				+ ","
				+ timetableslotId + "," + staffId + ")";

		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch(deletion);
			statement.executeBatch();

			statement.addBatch(insertion);
			statement.executeBatch();
		} catch (SQLException e) {
			System.out.println("Error while trying to populate session");
			e.printStackTrace();
		}
	}

}

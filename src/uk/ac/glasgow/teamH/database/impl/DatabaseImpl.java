package uk.ac.glasgow.teamH.database.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import uk.ac.glasgow.teamH.database.DatabaseInterface;

/**
 * @author Michael
 * 
 */
public class DatabaseImpl implements DatabaseInterface {
	static Connection connection = null;
	Statement stmt = null;

	private static final String connectionString = "jdbc:derby:data/mycampus5;create=true";

	public DatabaseImpl() {
		try {
			connection = getDatabaseConnection();
			createTables();
			populate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	private static void populate() {
		// TODO Auto-generated method stub
		try{connection = getDatabaseConnection();
		Statement statement = connection.createStatement();
		boolean compulsory=false;
		String name="A";
		for (int i = 0; i < 125; i++) {
			statement
			.addBatch("INSERT INTO course (course_id, course_name) VALUES ("
					+ i + "," +"'course"+i +"'" + ")");
			statement.executeBatch(); 
			for(int j=0;j<15;j++){
				if (i % 2 == 0) {
					compulsory=true;
					name="A";
					} else {
						name="B";
						compulsory=false;
					}
					
				statement
				.addBatch("INSERT INTO session (session_id, compulsory) VALUES ("
						+ j + "," + compulsory + ")");
		statement.executeBatch(); 
		statement
		.addBatch("INSERT INTO course_session(course_id, session_id) VALUES ("
				+ i + "," + j + ")");
		statement.executeBatch(); 
		for(int f=0;f<30;f++){
			int staffID=(int)(Math.random() * (500+i)) + 0;
			statement
			.addBatch("INSERT INTO timetableslot(timetableslot_id, capacity, starttime, endtime, weekday, weeknumber, room, occupied, staff_id,session_id) VALUES ("
					+ f + "," + 150 + "," + 12 + ","+13+ "," + 2+ "," + i+ ","+"'Boyd Orr"+"'"+","+true+ ","+staffID+ ","+j+ ")");
			statement.executeBatch(); 
			statement
			.addBatch("INSERT INTO session_timetableslot(session_id, timetableslot_id) VALUES ("
				+ j + "," + f + ")");
			statement.executeBatch();
			int studentID=(int)(Math.random() * (595+i)) + 0;
			for(int a=10;a<20;a++){
			statement
			.addBatch("INSERT INTO student_course_session(student_id, course_id,session_id,timetableslot_id) VALUES ("
				+ a + "," + i + "," + j + ","+f+ ")");
 
			statement
			.addBatch("INSERT INTO student_session(student_id, session_id) VALUES ("
				+ a + "," + j + ")");
			statement.executeBatch();
			}
		}
			}
		}
		
		
		for(int a=0;a<250;a++){		
			statement
			.addBatch("INSERT INTO staff(staff_id, staff_name) VALUES ("
				+ a + "," + "'lecturer"+a+"'" + ")");
			statement.executeBatch();
			statement 
			.addBatch("INSERT INTO mycampus_authentication(username, password, usertype) VALUES ("
					+ "'lecturer"+a+"'" + "," + "'lecturer"+a+"'" + ","+"'lecturer"+a+"'" + ")");
			statement.executeBatch();
		}
		for(int a=250;a<500;a++){
			statement
			.addBatch("INSERT INTO staff(staff_id, staff_name) VALUES ("
				+ a + "," + "'tutor"+(a-250)+"'" + ")");
			statement.executeBatch();
			statement
			.addBatch("INSERT INTO mycampus_authentication(username, password, usertype) VALUES ("
					+ "'tutor"+a+"'" + "," + "'tutor"+a+"'" + ","+"'tutor"+a+"'" + ")");
			statement.executeBatch();
		}
		for(int a=0;a<600;a++){
			statement
			.addBatch("INSERT INTO student (student_id, student_name) VALUES ("
					+ a + ","+"'student"+a+"'" + ")");
			statement.executeBatch();
			statement 
			.addBatch("INSERT INTO mycampus_authentication(username, password, usertype) VALUES ("
					+ "'student"+a+"'" + "," + "'student"+a+"'" + ","+"'student"+a+"'" + ")");
			statement.executeBatch();	
		}
		
		for(int a=650;a<700;a++){
			statement
			.addBatch("INSERT INTO student (student_id, student_name) VALUES ("
					+ a + ","+"'student"+a+"'" + ")");
			statement.executeBatch();}
			
			
			
			for(int a=126;a<250;a++){
			statement
					.addBatch("INSERT INTO mycampus_course (course_id,course_name) VALUES ("
							+ a + "," + "'course"+a + "'" + ")");
			statement.executeBatch();}
	
		statement
		.addBatch("INSERT INTO mycampus_authentication(username, password, usertype) VALUES ("
				+ "'admin"+"'" + "," + "'admin"+"'" + ","+"'admin"+"'" + ")");
		statement.executeBatch();
		
		
		
	
		connection.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	void createTables() {
		try {
			System.out.println("In create tables");
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			if (!tableExists("course")) {
				statement
						.execute("CREATE TABLE course(course_id INTEGER PRIMARY KEY, course_name VARCHAR(128))");
				System.out.println("Created course table");
			}

			if (!tableExists("student")) {
				statement
						.execute("CREATE TABLE student(student_id INTEGER PRIMARY KEY, student_name VARCHAR(128))");
				System.out.println("Created student table");
			}
			if (!tableExists("staff")) {

				statement
						.execute("CREATE TABLE staff(staff_id INTEGER PRIMARY KEY, staff_name VARCHAR(128))");
				System.out.println("Created staff table");
			}
			if (!tableExists("session")) {
				statement
						.execute("CREATE TABLE session(session_id INTEGER PRIMARY KEY, recurring VARCHAR(128), compulsory BOOLEAN,timetableslot_id INTEGER, staff_id INTEGER,"
								+ " FOREIGN KEY (staff_id) REFERENCES staff (staff_id))");
				System.out.println("Created session table");
			}
			if (!tableExists("timetableslot")) {
				statement
						.execute("CREATE TABLE timetableslot(timetableslot_id INTEGER, capacity INTEGER, starttime INTEGER, endtime INTEGER, weekday INTEGER, weeknumber INTEGER, room VARCHAR(128), occupied BOOLEAN, staff_id INTEGER,session_id INTEGER,PRIMARY KEY(timetableslot_id,session_id), "
								+ " FOREIGN KEY (session_id) REFERENCES session (session_id))");
				System.out.println("created timetable slot table");

			}

			if (!tableExists("student_course_session")) {
				statement
						.execute("CREATE TABLE student_course_session(student_id INTEGER, course_id INTEGER,session_id INTEGER,timetableslot_id INTEGER, PRIMARY KEY(student_id,course_id,session_id,timetableslot_id))");
				System.out.println("Created student_course_session table");
			}

			if (!tableExists("student_session")) {
				statement
						.execute("CREATE TABLE student_session(student_id INTEGER, session_id INTEGER)");
			}

			// if (!tableExists("student_timeslot")) {
			// statement
			// .execute("CREATE TABLE student_timeslot(student_id INTEGER, timeslot_id INTEGER)");
			// }
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
						.execute("CREATE TABLE mycampus_course(course_id INTEGER PRIMARY KEY, course_name VARCHAR(128), staff_id INTEGER, FOREIGN KEY (staff_id) REFERENCES staff (staff_id))");
				System.out.println("Created mycampus_course table");

			}
			if (!tableExists("course_session")) {
				statement
						.execute("CREATE TABLE course_session(course_id INTEGER,session_id INTEGER,PRIMARY KEY(course_id, session_id))");
				System.out.println("Created course_session table");

			}
			existingTables();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	// 2.-OK
	@Override
	public void addSession(int courseID, int sessionID, boolean compulsory) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			statement.addBatch("Delete from course_session ");
			statement.executeBatch();
			statement.addBatch("Delete from session ");
			statement.executeBatch();
			statement
					.addBatch("INSERT INTO session (session_id, compulsory) VALUES ("
							+ sessionID + "," + compulsory + ")");
			statement.executeBatch();

			statement
					.addBatch("INSERT INTO course_session (course_id, session_id) VALUES ("
							+ courseID + "," + sessionID + ")");
			statement.executeBatch();

			String query1 = "SELECT * FROM SESSION";
			String query2 = "SELECT * FROM course_session";

			ResultSet result1 = statement.executeQuery(query1);
			System.out.println("Table Session");
			System.out.println("---------------------------------------");
			while (result1.next()) {
				System.out.println(result1.getInt(1) + " "
						+ result1.getBoolean(2));
			}
			System.out.println("---------------------------------------");

			ResultSet result2 = statement.executeQuery(query2);
			System.out.println("Table Course_Session");
			System.out.println("---------------------------------------");
			while (result2.next()) {
				System.out.println(result2.getInt(1) + " " + result2.getInt(2));
			}
			System.out.println("---------------------------------------");

			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getTableInfo(String table) {
		String r = table + ":\n--------------------------------------------"
				+ "\n";
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM " + table;
			ResultSet result = statement.executeQuery(query);

			while (result.next()) {
				r += result.getInt(1) + "\n";
			}
			r += "\n------------------------------------------\n";
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return r;
	}

	// 8-OK
	@Override
	public void assignRoomToTimetableslot(int timetableslotID, String room) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			statement.addBatch("Delete from timetableslot ");
			statement.executeBatch();
			statement
					.addBatch("INSERT INTO timetableslot (timetableslot_id) VALUES ("
							+ timetableslotID + ")");
			statement.executeBatch();

			statement.addBatch("UPDATE timetableslot SET room = " + "'" + room
					+ "'" + " WHERE timetableslot_id = " + timetableslotID);
			statement.executeBatch();

			String query = "SELECT * FROM timetableslot";

			ResultSet result = statement.executeQuery(query);
			System.out.println("Table Timetableslot");
			System.out.println("---------------------------------------");
			while (result.next()) {
				System.out
						.println(result.getInt(1) + " " + result.getString(7));
			}
			System.out.println("---------------------------------------");
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 1-OK
	@Override
	public void importMycampusCourse(int courseID) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			statement.addBatch("Delete from course ");
			statement.executeBatch();
			String getCourseFromMyCampus = "SELECT * FROM mycampus_course WHERE course_id="
					+ courseID;
			ResultSet info = statement.executeQuery(getCourseFromMyCampus);
			System.out.println("in db");
			if (info.next()) {
				statement.addBatch("INSERT INTO course VALUES ("
						+ info.getInt(1) + "," + "'" + info.getString(2) + "'"
						+ ")");
				statement.executeBatch();
				String query = "Select * FROM course";
				ResultSet result = statement.executeQuery(query);
				System.out.println("---------------------------------------");
				while (result.next()) {
					System.out.println(result.getInt(1) + " "
							+ result.getString(2));
				}
				System.out.println("---------------------------------------");
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 4-OK
	@Override
	public void specifySessionRecurrence(int sessionID, String recurrence) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			statement.addBatch("Delete from session ");
			statement.executeBatch();
			statement.addBatch("INSERT INTO session (session_id) VALUES ("
					+ sessionID + ")");
			statement.executeBatch();
			// statement
			// .addBatch("UPDATE session SET recurring = ?" + recurrence +
			// " WHERE session_id = "
			// + sessionID );
			statement.executeUpdate("UPDATE session SET recurring = " + "'"
					+ recurrence + "'" + " WHERE session_id = " + sessionID);
			// statement.executeBatch();
			String query = "Select * FROM session";
			ResultSet result = statement.executeQuery(query);
			System.out.println("---------------------------------------");
			while (result.next()) {
				System.out
						.println(result.getInt(1) + " " + result.getString(2));
			}
			System.out.println("---------------------------------------");
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated method stub
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
			statement.addBatch("Delete from student_course_session ");
			statement.executeBatch();
			statement
					.addBatch("INSERT INTO student_course_session (student_id,session_id,course_id) VALUES ("
							+ studentID
							+ ","
							+ sessionID
							+ ","
							+ courseID
							+ ")");
			statement.executeBatch();
			statement
					.addBatch("UPDATE student_course_session SET timetableslot_id = "
							+ timetableslotID
							+ " WHERE session_id = "
							+ sessionID
							+ " AND student_id = "
							+ studentID
							+ " AND course_id = " + courseID);
			statement.executeBatch();
			String query = "Select * FROM student_course_session";
			ResultSet result = statement.executeQuery(query);
			System.out.println("---------------------------------------");
			while (result.next()) {
				System.out.println(result.getInt(1) + " " + result.getInt(2)
						+ " " + result.getInt(3) + " " + result.getInt(4));
			}
			System.out.println("---------------------------------------");
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generate catch block
			e.printStackTrace();
		}
	}

	// 12-OK
	@Override
	public boolean checkIfSignedUpForCompulsory(int studentID, int sessionID,
			int courseID) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch("Delete from student_course_session ");
			statement.executeBatch();
			statement.addBatch("Delete from session ");
			statement.executeBatch();
			statement
					.addBatch("INSERT INTO student_course_session(student_id,session_id,course_id) VALUES ("
							+ studentID
							+ ","
							+ sessionID
							+ ","
							+ courseID
							+ ")");
			statement.executeBatch();

			statement
					.addBatch("INSERT INTO session(session_id,compulsory) VALUES ("
							+ sessionID + "," + true + ")");
			statement.executeBatch();

			String query = "SELECT s.session_id FROM session AS s, student_course_session AS scs WHERE student_id = "
					+ studentID
					+ " AND scs.timetableslot_id IS NOT NULL AND s.session_id = scs.session_id AND compulsory = true";
			ResultSet result = statement.executeQuery(query);
			boolean r = result.next();
			connection.close();
			return r;
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
			return false;
		}

	}

	// 14-OK
	@Override
	public String getTimetableslotDetails(int sessionID) {
		String r = "";
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch("Delete from timetableslot ");
			statement.executeBatch();

			statement
					.addBatch("INSERT INTO timetableslot(timetableslot_id,session_id,room,starttime,endtime) VALUES ("
							+ 1
							+ ","
							+ sessionID
							+ ","
							+ "'"
							+ "BO715"
							+ "'"
							+ "," + 1 + "," + 2 + ")");
			statement.executeBatch();
			for (int i = 0; i < 15; i++) {
				statement
						.addBatch("INSERT INTO student_course_session(student_id,session_id,course_id) VALUES ("
								+ i + "," + sessionID + "," + 100 + ")");
				statement.executeBatch();
			}

			String queryDetails = "SELECT * FROM timetableslot WHERE session_id= "
					+ sessionID;
			String queryStudents = "SELECT student_id FROM student_course_session WHERE session_id ="
					+ sessionID;
			ResultSet detailsResult = statement.executeQuery(queryDetails);
			if (detailsResult.next()) {
				r = "Details:" + detailsResult.getInt(1);
				ResultSet students = statement.executeQuery(queryStudents);
				r += " " + " Students: ";
				while (students.next()) {
					r += students.getInt(1) + ",";
				}
			}
			connection.close();
			System.out.println(r);
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
		return r;
	}

	// non-functional distinguish between user types-OK
	public String getUserRole(String username) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch("Delete from mycampus_authentication ");
			statement.executeBatch();

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
			String r = "";
			if (result.next()) {
				r = "Type:" + result.getString(1);
			}
			System.out.println(r);
			connection.close();
			return r;
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
			return "";
		}

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
			// TODO Auto-generated method stub
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
				// System.out.println(result.getInt(1));
				count++;
			}

			connection.close();
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	public Boolean tableExists(String tableName) throws SQLException {

		connection = getDatabaseConnection();
		;

		DatabaseMetaData metaData = connection.getMetaData();

		ResultSet resultSet = metaData.getTables(null, null,
				tableName.toUpperCase(), new String[] { "TABLE" });

		Boolean result = resultSet.next();

		resultSet.close();
		connection.close();

		return result;
	}

	public void existingTables() throws SQLException {

		connection = getDatabaseConnection();
		;

		DatabaseMetaData metaData = connection.getMetaData();

		ResultSet resultSet;
		resultSet = metaData.getTables(null, null, "%", null);
		while (resultSet.next()) {
			System.out.println(resultSet.getString(3));
		}

		resultSet.close();
		connection.close();

	}

	public void populateMyCampusCourse() {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			statement.addBatch("Delete from mycampus_course ");
			statement.executeBatch();
			for (int i = 0; i < 20; i++) {
				statement
						.addBatch("INSERT INTO mycampus_course (course_id,course_name) VALUES ("
								+ i + "," + "'course" + i + "'" + ")");
				statement.executeBatch();

			}

			String query = "SELECT * FROM mycampus_course";
			ResultSet result = statement.executeQuery(query);
			System.out.println("---------------------------------------");
			while (result.next()) {
				System.out
						.println(result.getInt(1) + " " + result.getString(2));
			}
			System.out.println("---------------------------------------");

			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

}
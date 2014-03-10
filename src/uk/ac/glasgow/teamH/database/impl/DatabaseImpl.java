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
	private static final String connectionString = "jdbc:derby:data/mycampus5;create=true";

	public DatabaseImpl() {
		try {
			connection = getDatabaseConnection();
			createTables();
			populate();

			importMycampusCourse(321);
			boolean found = false;
			ResultSet result = getTableInfo("course");
			System.out.println("trying");
			try {
				System.out.println("try block");
				while (result.next()) {
					System.out.println(result.getInt(1));
					if (result.getInt("course_id") == 321)
						found = true;
				}
				System.out.println(found);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			System.out
					.printf("\n \n CREATING TABLES \n----------------------------------\n----------------------------------\n");
			int sesval = 1;
			int sesplus = 15;
			int timeplus = 30;
			int studentplus = 16;
			for (int i = 1; i <= 105; i++) {
				System.out.println("COURSE" + i);
				statement
						.addBatch("INSERT INTO course(course_id, course_name) VALUES ("
								+ i + "," + "'course" + i + "'" + ")");
				statement.executeBatch();
				if (i > 10) {
					sesplus = 5;
					timeplus = 3;
					studentplus = 4;
				}
				for (int j = sesval; j < sesval + sesplus; j++) {
					// System.out.println("SESSION "+j);
					if (j < 10) {
						if (j < 3) {
							statement
									.addBatch("INSERT INTO session (session_id,recurring, compulsory) VALUES ("
											+ j
											+ ","
											+ "'true'"
											+ ","
											+ true
											+ ")");
							statement.executeBatch();
						} else {
							statement
									.addBatch("INSERT INTO session (session_id,recurring, compulsory) VALUES ("
											+ j
											+ ","
											+ "'false'"
											+ ","
											+ true
											+ ")");
							statement.executeBatch();
						}
					} else {
						statement
								.addBatch("INSERT INTO session (session_id,recurring, compulsory) VALUES ("
										+ j
										+ ","
										+ "'true'"
										+ ","
										+ false
										+ ")");
						statement.executeBatch();
					}

					statement
							.addBatch("INSERT INTO course_session(course_id, session_id) VALUES ("
									+ i + "," + j + ")");
					statement.executeBatch();
					for (int f = 1; f < timeplus; f++) {
						// System.out.println("TIMETABLESLOT "+f);
						int staffID = (int) (Math.random() * (500 + i)) + 0;
						statement
								.addBatch("INSERT INTO timetableslot(timetableslot_id, capacity, starttime, endtime, weekday, weeknumber, room, occupied, staff_id,session_id) VALUES ("
										+ f
										+ ","
										+ 150
										+ ","
										+ 12
										+ ","
										+ 13
										+ ","
										+ 2
										+ ","
										+ i
										+ ","
										+ "'Boyd Orr'"
										+ ","
										+ true
										+ ","
										+ staffID + "," + j + ")");
						statement.executeBatch();
						statement
								.addBatch("INSERT INTO session_timetableslot(session_id, timetableslot_id) VALUES ("
										+ j + "," + f + ")");
						statement.executeBatch();
						for (int a = 1; a < studentplus; a++) {
							// System.out.println("STUDENT COURSE SESSION " +a);
							statement
									.addBatch("INSERT INTO student_course_session(student_id, course_id,session_id,timetableslot_id) VALUES ("
											+ a
											+ ","
											+ i
											+ ","
											+ j
											+ ","
											+ f
											+ ")");

							statement
									.addBatch("INSERT INTO student_session(student_id, session_id) VALUES ("
											+ a + "," + j + ")");
							statement.executeBatch();
						}
					}
				}
				sesval = sesval + 16;
			}

			for (int a = 0; a < 250; a++) {
				// System.out.println(a);
				statement
						.addBatch("INSERT INTO staff(staff_id, staff_name) VALUES ("
								+ a + "," + "'lecturer" + a + "'" + ")");
				statement.executeBatch();
				statement
						.addBatch("INSERT INTO mycampus_authentication(username, password, usertype) VALUES ("
								+ "'lecturer"
								+ a
								+ "'"
								+ ","
								+ "'lecturer"
								+ a
								+ "'" + "," + "'lecturer" + a + "'" + ")");
				statement.executeBatch();
			}
			for (int a = 250; a < 500; a++) {
				// System.out.println(a);
				statement
						.addBatch("INSERT INTO staff(staff_id, staff_name) VALUES ("
								+ a + "," + "'tutor" + (a - 250) + "'" + ")");
				statement.executeBatch();
				statement
						.addBatch("INSERT INTO mycampus_authentication(username, password, usertype) VALUES ("
								+ "'tutor"
								+ a
								+ "'"
								+ ","
								+ "'tutor"
								+ a
								+ "'"
								+ "," + "'tutor" + a + "'" + ")");
				statement.executeBatch();
			}
			for (int a = 0; a < 600; a++) {
				// System.out.println(a);
				statement
						.addBatch("INSERT INTO student (student_id, student_name) VALUES ("
								+ a + "," + "'student" + a + "'" + ")");
				statement.executeBatch();
				statement
						.addBatch("INSERT INTO mycampus_authentication(username, password, usertype) VALUES ("
								+ "'student"
								+ a
								+ "'"
								+ ","
								+ "'student"
								+ a
								+ "'" + "," + "'student" + a + "'" + ")");
				statement.executeBatch();
			}
			for (int a = 650; a < 700; a++) {
				// System.out.println(a);
				statement
						.addBatch("INSERT INTO student (student_id, student_name) VALUES ("
								+ a + "," + "'student" + a + "'" + ")");
				statement.executeBatch();
			}

			for (int a = 126; a < 250; a++) {
				// System.out.println(a);
				statement
						.addBatch("INSERT INTO mycampus_course (course_id,course_name) VALUES ("
								+ a + "," + "'course" + a + "'" + ")");
				statement.executeBatch();
			}

			statement
					.addBatch("INSERT INTO mycampus_authentication(username, password, usertype) VALUES ("
							+ "'admin"
							+ "'"
							+ ","
							+ "'admin"
							+ "'"
							+ ","
							+ "'admin" + "'" + ")");
			statement.executeBatch();

			System.out.println("");

			String query1 = "SELECT * FROM session";
			String query2 = "SELECT * FROM course_session";
			String query3 = "SELECT * FROM timetableslot";
			String query4 = "SELECT * FROM staff";
			String query5 = "SELECT * FROM student";
			String query6 = "SELECT * FROM course";
			ResultSet result1 = statement.executeQuery(query1);
			System.out.println("Table Session");
			System.out.println("---------------------------------------");
			while (result1.next()) {
				System.out.println(" sessionid: " + result1.getInt(1)
						+ "    recurring:s   " + result1.getString(2)
						+ "   compulsory: " + result1.getBoolean(3));
			}
			System.out.println("---------------------------------------");

			ResultSet result2 = statement.executeQuery(query2);
			System.out.println("Table Course_Session");
			System.out.println("---------------------------------------");
			while (result2.next()) {
				System.out.println("course id  :  " + result2.getInt(1)
						+ "  session id:  " + result2.getInt(2));
			}
			System.out.println("---------------------------------------");

			ResultSet result3 = statement.executeQuery(query3);
			System.out.println("Timetable Slot");
			System.out.println("---------------------------------------");
			while (result3.next()) {
				System.out.println(result3.getInt(1) + " " + result3.getInt(2)
						+ " " + result3.getInt(3) + " " + result3.getInt(4)
						+ " " + result3.getInt(5) + " " + result3.getByte(6)
						+ " " + result3.getString(7) + " "
						+ result3.getBoolean(8) + " " + result3.getInt(9) + " "
						+ result3.getInt(10));
			}
			System.out.println("---------------------------------------");

			ResultSet result4 = statement.executeQuery(query4);
			System.out.println("Staff");
			System.out.println("---------------------------------------");
			while (result4.next()) {
				System.out.println(result4.getInt(1) + " "
						+ result4.getString(2));
			}
			System.out.println("---------------------------------------");

			ResultSet result5 = statement.executeQuery(query5);
			System.out.println("Student");
			System.out.println("---------------------------------------");
			while (result5.next()) {
				System.out.println(result5.getInt(1) + " "
						+ result5.getString(2));
			}
			System.out.println("---------------------------------------");

			ResultSet result6 = statement.executeQuery(query6);
			System.out.println("Table Course");
			System.out.println("---------------------------------------");
			while (result6.next()) {
				System.out.println(result6.getInt(1) + " "
						+ result6.getString(2));
			}
			System.out.println("---------------------------------------");
			coursetable = result6;

			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
						.execute("CREATE TABLE timetableslot(timetableslot_id INTEGER, capacity INTEGER, starttime INTEGER, endtime INTEGER, weekday INTEGER, weeknumber INTEGER, room VARCHAR(128), occupied BOOLEAN, staff_id INTEGER,PRIMARY KEY(timetableslot_id)");
				System.out.println("created timetable slot table");

			}

			if (!tableExists("student_course_session")) {
				statement
						.execute("CREATE TABLE student_course_session(student_id INTEGER, course_id INTEGER,session_id INTEGER,timetableslot_id INTEGER,starttime INTEGER, endtime INTEGER, compulsory BOOLEAN, PRIMARY KEY(student_id,course_id,session_id))");
				System.out.println("Created student_course_session table");
			}
/*
			if (!tableExists("student_session")) {
				statement
						.execute("CREATE TABLE student_session(student_id INTEGER, session_id INTEGER)");
			}

*/
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ResultSet getTableInfo(String table) throws SQLException {
		// String r = table + ":\n--------------------------------------------"
		// + "\n";
		connection = getDatabaseConnection();
		Statement statement = connection.createStatement();

		String query = "SELECT * FROM " + table;
		ResultSet result = statement.executeQuery(query);

		// connection.close();
		return result;

	}

	public boolean getCourseRow(int id) throws SQLException {
		connection = getDatabaseConnection();
		Statement statement = connection.createStatement();
		statement
				.addBatch("INSERT INTO course(course_id, course_name) VALUES ("
						+ id + "," + "'course" + id + "'" + ")");
		statement.executeBatch();
		return true;/*
					 * String query =
					 * "SELECT * FROM course WHERE course_id= "+id; ResultSet
					 * result = statement.executeQuery(query);
					 * 
					 * //connection.close(); return result;
					 */

	}

	public boolean findCourse(int courseID) {
		String query = "SELECT course_id FROM course";
		// boolean found=false;
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			result.beforeFirst();
			while (result.next()) {
				int cID = result.getInt(1);
				if (cID == courseID) {
					result.beforeFirst();
					return true;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public ResultSet getSessionDetails(int sessionID) {
		ResultSet result = null;
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			String query1 = "SELECT * FROM session WHERE session_id = "
					+ sessionID;
			result = statement.executeQuery(query1);
			
			// connection.close();
		} catch (SQLException e) {
			// TODO Auto-generate method stub
			e.printStackTrace();
		}
		return result;
	}

	// 8-OK
	@Override
	public void assignRoomToTimetableslot(int timetableslotID, String room) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement
					.addBatch("INSERT INTO timetableslot(timetableslot_id,room) VALUES ( "
							+ timetableslotID + ", " + "'" + room + "'" + ")");
			statement.executeBatch();

			// statement
			// .addBatch("INSERT INTO timetableslot(timetableslot_id,room) VALUES ("
			// + timetableslotID +","+room+ ")");
			// statement.executeBatch();
			/*
			 * statement.addBatch("UPDATE timetableslot SET room = " + "'" +
			 * room + "'" + " WHERE timetableslot_id = " + timetableslotID);
			 * statement.executeBatch();
			 */
			// connection.close();
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

			// String getCourseFromMyCampus =
			// "SELECT * FROM mycampus_course WHERE course_id="
			// + courseID;
			// ResultSet info = statement.executeQuery(getCourseFromMyCampus);
			System.out.println("in db");
			statement
					.addBatch("INSERT INTO course(course_id,course_name) VALUES ("
							+ courseID + "," + "'course" + courseID + "'"

							+ ")");
			// + info.getInt(1) + "," + "'" + info.getString(2) + "'"
			// + ")");
			statement.executeBatch();

			// connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			// TODO Auto-generated method stub
			e.printStackTrace();
			return false;
		}

	}

	public void changeSessionRecurrence(int sessionID, String recurrence) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
			String q = "UPDATE session SET recurring = '" + recurrence
					+ "' WHERE session_id = " + sessionID;
			statement.executeUpdate(q);
			// connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 4-OK
	@Override
	public void specifySessionRecurrence(int sessionID, String recurrence) {
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

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
			// // String query = "Select * FROM session";
			// ResultSet result = statement.executeQuery(query);
			System.out.println("---------------------------------------");

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
			// String query = "Select * FROM student_course_session";
			// ResultSet result = statement.executeQuery(query);
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

	@Override
	public ResultSet getTimetableslotDetails(int sessionID) {
		String r = "";
		ResultSet detailsResult = null;
		System.out.println("dede");
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

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
			detailsResult = statement.executeQuery(queryDetails);
			connection.close();
			System.out.println(r);
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
		return detailsResult;
	}

	@Override
	public ResultSet getTimetableslotStudentDetails(int sessionID) {
		String r = "";
		ResultSet detailsResult = null;
		ResultSet students = null;
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

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
			// String queryStudents =
			// "SELECT student_id FROM student_course_session WHERE session_id ="
			// + sessionID;
			detailsResult = statement.executeQuery(queryDetails);
			connection.close();
			System.out.println(r);
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
		return students;
	}

	public ResultSet getSessionsCourse(int courseID) {
		// String r = "";
		ResultSet detailsResult = null;
		System.out.println("dede");
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			/*
			 * statement.addBatch("Delete from timetableslot ");
			 * statement.executeBatch();
			 * 
			 * statement .addBatch(
			 * "INSERT INTO timetableslot(timetableslot_id,session_id,room,starttime,endtime) VALUES ("
			 * + 1 + "," + sessionID + "," + "'" + "BO715" + "'" + "," + 1 + ","
			 * + 2 + ")"); statement.executeBatch(); for (int i = 0; i < 15;
			 * i++) { statement .addBatch(
			 * "INSERT INTO student_course_session(student_id,session_id,course_id) VALUES ("
			 * + i + "," + sessionID + "," + 100 + ")");
			 * statement.executeBatch(); }
			 */
			String queryDetails = "SELECT session_iD FROM course_session WHERE course_id= "
					+ courseID;
			detailsResult = statement.executeQuery(queryDetails);
			// connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
		return detailsResult;
	}

	// non-functional distinguish between user types-OK
	public String getUserRole(String username) {
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

			// statement.addBatch("Delete from course_session ");
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

	public static Boolean tableExists(String tableName) throws SQLException {

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

	public static void existingTables() throws SQLException {

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
	
	public boolean timesOverlap(int startTime1, int endTime1, int startTime2, int endTime2){

		if(endTime1 <= startTime2 || startTime1>=endTime2){
			return false;
		}
		
		else{
			return true;
		}
		
	}
	public boolean checkForClashes(int studentId){
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
		
			statement
			.addBatch("Delete from student_course_session");
	statement.executeBatch();
	
	statement
	.addBatch("Delete from timetableslot");
statement.executeBatch();



			statement
			.addBatch("INSERT INTO student_course_session(student_id,course_id, session_id,timetableslot_id) VALUES ("
					+ studentId + "," + 111 +","+111+","+111+ ")");
	statement.executeBatch();
	
	statement
	.addBatch("INSERT INTO student_course_session(student_id,course_id, session_id,timetableslot_id) VALUES ("
			+ studentId + "," + 222 +","+222 + ","+222+")");
statement.executeBatch();

statement
.addBatch("INSERT INTO student_course_session(student_id,course_id, session_id,timetableslot_id) VALUES ("
		+ studentId + "," + 777 +","+777 + ","+777+")");
statement.executeBatch();



statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime,session_id) VALUES ("
		+ 111 + "," + 14 +","+15+","+ 1+")");
statement.executeBatch();

statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime, session_id) VALUES ("
		+ 222 + "," + 15 +","+17 +","+ 1+")");
statement.executeBatch();

statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime, session_id) VALUES ("
		+ 333 + "," + 15 +","+16+","+ 1+")");
statement.executeBatch();
statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime, session_id) VALUES ("
		+ 444 + "," + 16 +","+17+","+ 1+ ")");
statement.executeBatch();
statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime, session_id) VALUES ("
		+ 555 + "," + 17 +","+18+ ","+1+")");
statement.executeBatch();

statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime, session_id) VALUES ("
		+ 666 + "," + 13 +","+14+ ","+1+")");
statement.executeBatch();
statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime, session_id) VALUES ("
		+ 777 + "," + 10 +","+11+","+ 1+")");
statement.executeBatch();
String query = "SELECT starttime,endtime FROM student_course_session as scs, timetableslot AS t WHERE scs.timetableslot_id = t.timetableslot_id AND student_id= "
		+ studentId;
ResultSet result = statement.executeQuery(query);


LinkedList<int[]> timetableslotsForStudent = new LinkedList<int[]>();


while(result.next()){
	int startEnd[] = new int[2];
	int startTime = result.getInt(1);
	int endTime = result.getInt(2);
	startEnd[0]=startTime;
	startEnd[1] = endTime;
	timetableslotsForStudent.add(startEnd);
}

for (int i = 0; i < timetableslotsForStudent.size()-1; i++){
	for (int j = i+1; j<timetableslotsForStudent.size();j++){
		
		int startTime1=timetableslotsForStudent.get(i)[0];
		int endTime1=timetableslotsForStudent.get(i)[1];
		int startTime2=timetableslotsForStudent.get(j)[0];
		int endTime2=timetableslotsForStudent.get(j)[1];
		System.out.println(startTime1);
		System.out.println(endTime1);
		System.out.println(startTime2);
		System.out.println(endTime2);
		if(timesOverlap(startTime1, endTime1, startTime2, endTime2)){
			return true;
		}
	}
	
}

			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();

		}
		return false;
	}
	

	
	
	public int getNumberOfClashes(int studentId, int timetableslotId){

		int numberOfClashes = 0;
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();


			String query1 = "SELECT starttime,endtime FROM timetableslot WHERE timetableslot_id = "
					+ timetableslotId;
			ResultSet r = statement.executeQuery(query1);
			int startTime1 = -1;
			int endTime1 = -1;
			if(r.next()){
				startTime1 = r.getInt(1);
				endTime1= r.getInt(2);
			}
			
			
			
			String query2 = "SELECT starttime,endtime FROM student_course_session as scs, timetableslot AS t WHERE scs.timetableslot_id = t.timetableslot_id AND student_id= "
					+ studentId;
			ResultSet result = statement.executeQuery(query2);
			while (result.next()) {
				int startTime2 = result.getInt(1);
				int endTime2 = result.getInt(2);
				if(timesOverlap(startTime1,endTime1,startTime2,endTime2)){
					numberOfClashes++;
				
			}
			System.out.println(r);
			connection.close();
		}
		}catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();

		}
		return numberOfClashes;
	}
	
	
	

	public boolean checkForClashes2(int studentId, int timetableslotId){
		boolean clash = false;
		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();
		
			statement
			.addBatch("Delete from student_course_session");
	statement.executeBatch();
	
	statement
	.addBatch("Delete from timetableslot");
statement.executeBatch();



			statement
			.addBatch("INSERT INTO student_course_session(student_id,course_id, session_id,timetableslot_id) VALUES ("
					+ studentId + "," + 111 +","+111+","+111+ ")");
	statement.executeBatch();
	
	statement
	.addBatch("INSERT INTO student_course_session(student_id,course_id, session_id,timetableslot_id) VALUES ("
			+ studentId + "," + 222 +","+222 + ","+222+")");
statement.executeBatch();



statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime,session_id) VALUES ("
		+ 111 + "," + 14 +","+15+","+ 1+")");
statement.executeBatch();

statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime, session_id) VALUES ("
		+ 222 + "," + 15 +","+17 +","+ 1+")");
statement.executeBatch();

statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime, session_id) VALUES ("
		+ 333 + "," + 15 +","+16+","+ 1+")");
statement.executeBatch();
statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime, session_id) VALUES ("
		+ 444 + "," + 16 +","+17+","+ 1+ ")");
statement.executeBatch();
statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime, session_id) VALUES ("
		+ 555 + "," + 17 +","+18+ ","+1+")");
statement.executeBatch();

statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime, session_id) VALUES ("
		+ 666 + "," + 13 +","+14+ ","+1+")");
statement.executeBatch();
statement
.addBatch("INSERT INTO timetableslot(timetableslot_id,starttime, endtime, session_id) VALUES ("
		+ 777 + "," + 10 +","+11+","+ 1+")");
statement.executeBatch();
			String query1 = "SELECT starttime,endtime FROM timetableslot WHERE timetableslot_id= "
					+ timetableslotId;
			ResultSet r = statement.executeQuery(query1);
			int startTime1 = -1;
			int endTime1 = -1;
			if(r.next()){
				startTime1 = r.getInt(1);
				endTime1= r.getInt(2);
			}
			String query2 = "SELECT starttime,endtime FROM student_course_session as scs, timetableslot AS t WHERE scs.timetableslot_id = t.timetableslot_id AND student_id= "
					+ studentId;
			ResultSet result = statement.executeQuery(query2);
			while (result.next()) {
				int startTime2 = result.getInt(1);
				int endTime2 = result.getInt(2);
				if(timesOverlap(startTime1,endTime1,startTime2,endTime2)){
					clash = true;
					return clash;
				}
				
			}
			System.out.println(r);
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated method stub
			e.printStackTrace();

		}
		return clash;
	}
	
	
	
	public void populateMyCampusCourse(int courseId, String courseName) {
		String deletion = "DELETE FROM MyCampusCourse";
		String insertion = "INSERT INTO MyCampusCourse (course_id, course_name) VALUES "
				+ courseId + "," + courseName + ")";

		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch(deletion);
			statement.executeBatch();

			statement.addBatch(insertion);
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void populateSession(int sessionId){
		String deletion = "DELETE FROM Session";
		String insertion = "INSERT INTO Session (session_id) VALUES "
				+ sessionId + ")";

		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch(deletion);
			statement.executeBatch();

			statement.addBatch(insertion);
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void populateTimetableslot(int timetableslotId){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void populateStudent_Course_SessionFully(int studentId, int courseId, int sessionId, int timetableslotId, int startTime, int endTime, boolean compulsory){
		String deletion = "DELETE FROM student_course_session";
		String insertion = "INSERT INTO student_course_session (student_id,course_id,session_id,timetableslot_id,starttime,endtime) VALUES "
				+studentId+"," + courseId + "," + sessionId+","+ timetableslotId + ","+startTime+","+endTime+","+compulsory +")";

		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch(deletion);
			statement.executeBatch();

			statement.addBatch(insertion);
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void populateStudent_Course_SessionPartially(int studentId, int courseId, int sessionId,boolean compulsory){
		String deletion = "DELETE FROM student_course_session";
		String insertion = "INSERT INTO student_course_session (student_id,course_id,session_id,timetableslot_id,starttime,endtime,compulsory) VALUES "
				+studentId+"," + courseId + "," + sessionId+ ","+compulsory +")";

		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch(deletion);
			statement.executeBatch();

			statement.addBatch(insertion);
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void populateSession_Timetableslot(int sessionId, int timetableslotId){
		String deletion = "DELETE FROM student_course_session";
		String insertion = "INSERT INTO session_timetableslot (session_id,timetableslot_id) VALUES "
				+ sessionId+ ","+timetableslotId +")";

		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch(deletion);
			statement.executeBatch();

			statement.addBatch(insertion);
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void populateSession(int sessionId, String recurring, boolean compulsory, int timetableslotId, int staffId){
		String deletion = "DELETE FROM Session";
		String insertion = "INSERT INTO Session (session_id,recurring,compulsory,timetableslot_id,staff_id) VALUES "
				+sessionId+",'" + recurring + "'," + compulsory+ ","+timetableslotId +","+staffId+")";

		try {
			connection = getDatabaseConnection();
			Statement statement = connection.createStatement();

			statement.addBatch(deletion);
			statement.executeBatch();

			statement.addBatch(insertion);
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

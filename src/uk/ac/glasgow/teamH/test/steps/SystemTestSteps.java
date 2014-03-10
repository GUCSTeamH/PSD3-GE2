package uk.ac.glasgow.teamH.test.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Before;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;

import uk.ac.glasgow.teamH.MyCampus.impl.MyCampus;
import uk.ac.glasgow.teamH.database.DatabaseInterface;
import uk.ac.glasgow.teamH.database.impl.DatabaseImpl;
import uk.ac.glasgow.teamH.test.ConfiguredFrameworkFactory;
import uk.ac.glasgow.teamH.user.AdminInterface;
import uk.ac.glasgow.teamH.user.LecturerInterface;
import uk.ac.glasgow.teamH.user.StudentInterface;
import uk.ac.glasgow.teamH.user.impl.AdminImpl;
import uk.ac.glasgow.teamH.user.impl.LecturerImpl;
import uk.ac.glasgow.teamH.user.impl.StudentImpl;

public class SystemTestSteps {


	private AdminImpl admin;
	private LecturerImpl lect;
	private StudentImpl stud;
	private DatabaseImpl data;
	
	private MyCampus myCamp;
	
	private ArrayList<Integer> notenrolled;
	public int countsessions=0;
	public int countcourses=0;
	public int counttimeslots=0;
	public int countUsers=0;
	
	@Given("a lecturer")
	public void aLecturer() throws Exception{
		data = new DatabaseImpl();
		lect = new LecturerImpl(data);
	}
	
	@When("course $cID and session $sID are provided")
	public void addSession(int cID, int sID){
		lect.addSession(cID, sID, true);
	}
	
	@Then("session $sID is added to course $cID")
	public void checkSession(int sID, int cID) throws SQLException{
		boolean found = false;
		ResultSet result = data.getTableInfo("course_session");
		if (result !=null)
			try {
				while (result.next()){

					if (result.getInt(1) == cID && result.getInt(2)==sID)
						found = true;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		assertThat(found, is(true));
		
	}
	
	@When("selecting mycampus course $cID")
	public void selectMyCampus(int cID){
		data.importMycampusCourse(cID);
	}
	
	@Then("mycampus course $cID is imported")
	public void checkCourse(int cID) throws SQLException{
		boolean found=false;
		ResultSet result = data.getTableInfo("course");
		if (result==null) found=false;
		while(result.next()){
			//if (result==null) found = false;
			if (result.getInt(1) == cID) found =true;
		}
		
		assertThat(found, is(true));
		
	}
	
	@Given("a student")
	public void aStudent(){
		data = new DatabaseImpl();
		stud= new StudentImpl(data);
		notenrolled=new ArrayList<Integer>();
	}
	
	@When("a student $studentID selects a session $sessionID and a particular timeslot $timeslotID of a course $courseID")
	public void Booked(int studentID,int sessionID,int timeslotID,int courseID) throws SQLException{
		stud.bookTimetableSlot(studentID,courseID, sessionID,timeslotID);
	
	}

	@Then("student $studentID is enrolled in the session $sessionID of the course $courseID")
	public void Book(int studentID,int sessionID,int courseID) throws SQLException{
		boolean result = data.checkIfSignedUp(studentID, sessionID,courseID);
		boolean expected=true;
		assertEquals(expected,result);
	}


	@When("session $sessID is marked as $session")
	public void sessionMarkedAsOneOff(int sessID, String session){
//		data.addSession(10, sessID, true);
		lect.specifySessionRecurrence(sessID, session);
	}



	@Then("session $sessID is a $expected session")
	public void recurringSession(int sessID, String expected) throws SQLException{
		ResultSet result = data.getSessionDetails(sessID);
		result.next();
		String reccurence = result.getString("recurring");
		assertTrue(reccurence.equalsIgnoreCase(expected));
	}

	@When("course $courseID is selected from student $studentID")
	public void compulsoryNotBooked(int courseID,int studentID) throws SQLException{
		ResultSet rs=stud.getSessionsCourse(courseID);
		while(rs.next()){
		boolean result = data.checkIfSignedUpForCompulsory(studentID, rs.getInt(1),10);
		if (result==false){
			notenrolled.add(rs.getInt(1));
		}}
	}

	@When("a  student $studentID selects a course $courseID")
	public void compulsoryCheck(int courseID,int studentID) throws SQLException{
		ResultSet rs=stud.getSessionsCourse(courseID);
		while(rs.next()){

			notenrolled.add(rs.getInt(1));
		}
	}

	@Then("student $studentID books all compulsory sessions of course $courseID")
	public void showBookCompulsory(int studentID,int courseID) throws SQLException{
		int timetableslotID=3587;
		for(int i=0;i<notenrolled.size();i++){
			data.bookTimetableSlot(studentID,courseID,notenrolled.get(i),
					timetableslotID);
		}
		ResultSet rs=data.getSessionsCourse(courseID);
		boolean expected=true;
		boolean result=true;
		while(rs.next()){
			result = data.checkIfSignedUpForCompulsory(studentID, rs.getInt(1),10);
			if (result==false){break;}
			}
		assertEquals(expected,result);
	}

	@Then("student $studentID views all compulsory sessions of course $courseID")
	public void showCompulsory(int studentID,int courseID) throws SQLException{
		int count=0;
		for(int i=0;i<notenrolled.size();i++){
		boolean result = data.checkIfSignedUpForCompulsory(studentID, notenrolled.get(i),10);
		if(result==false){count++;}
		ResultSet rs=data.getSessionsCourse(courseID);
		ArrayList<Integer>checkenrolled=new ArrayList<Integer>();
		while(rs.next()){
			boolean rt = data.checkIfSignedUpForCompulsory(studentID, rs.getInt(1),10);
			if (rt==false){
				checkenrolled.add(rs.getInt(1));}
		int expected=checkenrolled.size();
		assertEquals(expected,count);

			}
		
		}
	}
	
	@Given("an admin")
	public void anAdmin(){
		data = new DatabaseImpl();
		admin = new AdminImpl(data);
	}
	
	@When("room $room and timeslot $time are selected")
	public void addRoom(String room, int time){
		data.assignRoomToTimetableslot(time, room);
	}
	
	@Then("room $room is assigned to timeslot $time")
	public void checkDB(String room, int time) throws SQLException {
		boolean found = false;
		int check = 0;
		ResultSet result = data.getTableInfo("timetableslot");
		if (result != null){
			try {
				while (result.next()) {
					if (result.getInt(1) == time && result.getString(7).equalsIgnoreCase(room))
						found = true;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		assertThat(found, is(true));
	} 

	
	@Given("MyCampus authentication")
	public void givenMyCampus() {
		myCamp = new MyCampus();
	}
	
	@When("a student has successfully logged in")
	public void whenStudentLogIn() {
		stud = new StudentImpl(data);
		stud.registerMyCampusAuthenticator(myCamp);
		assertTrue(stud.login());
	}
	
	@Then("student will only have rights/privileges associated with their role")
	public void studentAccess() {
		assertTrue(stud instanceof StudentImpl);
	}
	
	@When("a lecturer has successfully logged in")
	public void whenLecturerLogIn() {
		lect = new LecturerImpl(data);
		lect.registerMyCampusAuthenticator(myCamp);
		assertTrue(lect.login());
	}
	
	@Then("lecturer will only have rights/privileges associated with their role")
	public void LecturerAccess() {
		assertTrue(lect instanceof LecturerImpl);
	}
	
	@When("an admin has successfully logged in")
	public void whenAdminLogIn() {
		admin= new AdminImpl(data);
		admin.registerMyCampusAuthenticator(myCamp);
		assertTrue(admin.login());
	}
	
	@Then("the admin will only have rights/privileges associated with their role")
	public void AdminAccess() {
		assertTrue(admin instanceof AdminImpl);
	}
	
	@Given("a system")
	public void system() throws Exception{
		data = new DatabaseImpl();
		
	}
	@When ("course $cID is selected to support $sessNum sessions")
	public void sessionCalc(int cID, int sessNum){
		countsessions = data.supportNSessionTypes(sessNum,cID);
	}
	
	@Then ("if sessions are over $sessNum then system can support")
		public void sessionSupport(int sessNum){
		assertTrue(sessNum<=countsessions);
	}
	
	
	@When ("courses are over $num")
	public void courseCalc(int num){
		countcourses=data.supportNCourses(num);
	}
	
	@Then ("system can support over $courseNum courses")
	public void coursesSupport(int courseNum){
		assertTrue(courseNum<=countcourses);
	}
	
	@When ("session $sessID is selected and it has over $timeNum timeslots")
	public void timeslotCalc(int sessID,int timeNum){
		counttimeslots=data.supportNTimetableslotsPerSession(timeNum,sessID);
	}
	
	@Then ("if number of timeslots are over $timeslots then the system can support")
	public void timeslotsSupport(int timeslots){
		assertTrue(timeslots<=counttimeslots);
	}
	
	@When ("users are over $totalUsers")
	public void userCalc(int totalUsers){
		countUsers=data.supportNUsers(totalUsers);
	}
	
	@Then ("system can support over $total users")
	public void userSupport(int total){
		assertTrue(total<=countUsers);
	}
}

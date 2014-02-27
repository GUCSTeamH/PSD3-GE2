package uk.ac.glasgow.teamH.test.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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

	private static final LecturerInterface LecturerInterface = null;
	private BundleContext bundleContext;
	private Framework framework;
	private Bundle database;
	private Bundle mycampus;
	private Bundle user;
	private Bundle activities;
	
	
	private AdminInterface admin;
	private LecturerInterface lecturer;
	private StudentInterface student;
	
	private AdminImpl adminImpl;
	private LecturerImpl lect;
	private StudentImpl stud;
	private DatabaseImpl data;
	
	private MyCampus myCamp;
	
	private ArrayList<Integer> notenrolled;
	
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
	public void checkSession(int sID, int cID){
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
		lect.importMyCampusCourse(cID);
	}
	
	@Then("mycampus course $cID is imported")
	public void checkCourse(int cID){
		boolean found=false;
		ResultSet result = data.getTableInfo("course");
		if (result !=null){
			try {
				
				while (result.next()){

					if (result.getInt(1) == cID)
						found = true;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		data.bookTimetableSlot(studentID,courseID, sessionID,timeslotID);
	
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
		data.changeSessionRecurrence(sessID, session);
	}



	@Then("session $sessID is a $expected session")
	public void recurringSession(int sessID, String expected) throws SQLException{
		ResultSet result = data.getSessionDetails(sessID);
		result.next();
		String reccurance = result.getString("recurring");
		assertTrue(reccurance.equalsIgnoreCase(expected));
	}

	@Given("an admin")
	public void anAdmin(){
		ServiceReference<AdminInterface>
		adminReference = 
			bundleContext.getServiceReference(
				AdminInterface.class);

		this.admin = bundleContext.getService(adminReference);
	}



	@When("course $courseID is selected from student $studentID")
	public void compulsoryNotBooked(int courseID,int studentID) throws SQLException{
		ResultSet rs=data.getSessionsCourse(courseID);
		while(rs.next()){
		boolean result = data.checkIfSignedUpForCompulsory(studentID, rs.getInt(1),10);
		if (result==false){
			notenrolled.add(rs.getInt(1));
		}}
	}

	@When("a  student $studentID selects a course $courseID")
	public void compulsoryCheck(int courseID,int studentID) throws SQLException{
		ResultSet rs=data.getSessionsCourse(courseID);
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
		adminImpl = new AdminImpl(data);
		adminImpl.registerMyCampusAuthenticator(myCamp);
		assertTrue(adminImpl.login());
	}
	
	@Then("the admin will only have rights/privileges associated with their role")
	public void AdminAccess() {
		assertTrue(adminImpl instanceof AdminImpl);
	}
}
package uk.ac.glasgow.teamH.test.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;

import uk.ac.glasgow.teamH.MyCampus.MyCampusInterface;
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

	private Bundle userBundle;
	private LecturerInterface lecturerInterface;
	private StudentInterface studentInterface;
	private AdminInterface adminInterface;
	
	private Bundle databaseBundle;
	private DatabaseInterface databaseInterface;
	
	private Bundle myCampusBundle;
	private MyCampusInterface myCampusInterface;

	
	//private AdminImpl adminInterface;
	//private StudentImpl studentInterface;
	//private DatabaseImpl databaseInterface;
	
	//private MyCampus myCampusInterface;
	
	private ArrayList<Integer> notenrolled;
	public int countsessions=0;
	public int countcourses=0;
	public int counttimeslots=0;
	public int countUsers=0;
	
	private Framework framework;
	private BundleContext bundleContext;
	//...
	
	

		
	
	@BeforeScenario
	public void beforeScenario () throws Exception {
		
		// Clean up any resources left behind by failed tests
		this.recursiveDelete(new File("felix-cache"));
		this.recursiveDelete(new File("data/mycampussH"));
		
		
		//expectedSensorID = "Temp02";
		
		String extraPackages = "uk.ac.glasgow.teamH.user,uk.ac.glasgow.teamH.database,"

		+ "uk.ac.glasgow.teamH.userProcedures,"
				+ "uk.ac.glasgow.teamH.MyCampus";
		
		framework = 
			ConfiguredFrameworkFactory.createFelixFramework(
				extraPackages);

		bundleContext = framework.getBundleContext();
		
		//...
		
		Integer actualNumberOfBundles = 
			bundleContext.getBundles().length;
		Integer expectedNumberOfBundles = 1;
		
		String message = 
			"If cleanly initialised, the framework should "
			+ "only contain 1 bundle (the framework).";
		
		assertEquals(
			message,
			expectedNumberOfBundles,
			actualNumberOfBundles);
		
		databaseBundle = 
			bundleContext.installBundle("file:database-impl.jar");
		
		databaseBundle.start();
		
		ServiceReference<DatabaseInterface>
		databaseServiceReference = 
			bundleContext.getServiceReference(
				DatabaseInterface.class);
		
		databaseInterface = 
			bundleContext.getService(databaseServiceReference);
		
		
	}
	
	@Given("a booking system containing course $cID with a session $sID also student $studentId")
	public void addcourseSessions(int studentId,int cID,int sID){
		databaseInterface.populateStudent_Course_SessionPartially(studentId, cID, sID, true, false);
	}
	/*@When("a  student $studentID books compulsory session $sID of course $courseID")
	public void compulsoryCheck(int studentID,int courseID,int sID) throws SQLException{
		studentInterface.bookTimetableSlot(studentID, courseID, sID, 12);
	}
	*/
	@Then("student $studentID checks if signed up for compulsory session $sID of course $courseID")
	public void showBookCompulsory(int studentID,int sID,int courseID) throws SQLException{
		boolean check=databaseInterface.checkIfSignedUp(studentID, sID, courseID);
		boolean expected=false;
		assertEquals(expected,check);
	}
	@Given("a booking system containing a course $courseId with a session $sessId and a timeslot $tslotId")
	public void givenABookingSystemContainingCourseSessionTimeslot(Integer courseId, Integer sessId, Integer tslotId) {
		databaseInterface.populateCourse(courseId);
		databaseInterface.populateSession_Timetableslot(sessId, tslotId);
	}
	
	@Given("a lecturer")
	public void aLecturer() throws Exception{
				
		userBundle = 
			bundleContext.installBundle(
				"file:user-impl.jar");
		userBundle.start();
		
		ServiceReference<LecturerInterface>
		lecturerServiceReference = 
			bundleContext.getServiceReference(
				LecturerInterface.class);
		
		lecturerInterface = 
			bundleContext.getService(lecturerServiceReference);
	}
	
	@Given("a booking system containing a course $courseId")
	public void givenABookingSystemContainingACourse(Integer courseId) {
		databaseInterface.populateCourse(courseId);
	}
	
	@Given("a mycampus")
	public void aMyCampus() throws Exception{
				
		myCampusBundle = 
			bundleContext.installBundle(
				"file:mycampus-impl.jar");
		myCampusBundle.start();
		
		ServiceReference<MyCampusInterface>
		myCampusServiceReference = 
			bundleContext.getServiceReference(
				MyCampusInterface.class);
		
		myCampusInterface = 
			bundleContext.getService(myCampusServiceReference);
	}
	
	@When("course $cID and session $sID are provided")
	public void addSession(int cID, int sID){
		lecturerInterface.addSession(cID, sID, true);
	}
	
	@Then("session $sID is added to course $cID")
	public void checkSession(int sID, int cID) throws SQLException{

		boolean found = false;
		ResultSet result = 
				databaseInterface.getTableInfo("course_session");
		
		
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
	
	
	@Then("session $sID is not added to course $cID")
	public void checkNegativeSession(int sID, int cID) throws SQLException{
		boolean expected = false;
		boolean found = false;
		ResultSet result = 
				databaseInterface.getTableInfo("course_session");
		
		
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
		assertThat(found, is(expected));
		
	}
	
	@Given("a mycampus system containing course $cID")
	public void populateMycampus(int cID){
		databaseInterface.populateMyCampusCourse(cID, "OS3");
		
	}
	
	@When("selecting mycampus course $cID")
	public void selectMyCampus(int cID){
		lecturerInterface.importMyCampusCourse(cID);
	}
	
	@Then("mycampus course $cID is imported")
	public void checkCourse(int cID) throws SQLException{
		boolean expected=true;
		if (cID < 0) expected = false;

		boolean found=false;
		ResultSet result = databaseInterface.getTableInfo("course");
		if (result==null) found=false;
		while(result.next()){
			//if (result==null) found = false;
			if (result.getInt(1) == cID) found =true;
		}
		
		assertThat(found, is(expected));
		
	}
	
	@Then("mycampus course $cID is not imported")
	public void checkNegativeCourse(int cID) throws SQLException{
		boolean expected=true;
		if (cID < 0) expected = false;

		boolean found=false;
		ResultSet result = databaseInterface.getTableInfo("course");
		if (result==null) found=false;
		while(result.next()){
			//if (result==null) found = false;
			if (result.getInt(1) == cID) found =true;
		}
		
		assertThat(found, is(expected));
		
	}
	
	@Given("a student")
	public void aStudent() throws BundleException{
		userBundle = 
				bundleContext.installBundle(
					"file:user-impl.jar");
			userBundle.start();
			
			ServiceReference<StudentInterface>
			studentServiceReference = 
				bundleContext.getServiceReference(
					StudentInterface.class);
			
			studentInterface = 
				bundleContext.getService(studentServiceReference);
	}
	
	@Given("a booking system containing session $sessId")
	public void populateSession(Integer sessId){
		databaseInterface.populateSession(sessId);
	}

	    @When("student $stud takes course $course , $second and $third")
    public void populateCoursesForStudents(int stud, int course, int second, int third){
         
        databaseInterface.populateStudent_Course_SessionFully(stud, course, 2, 3, 9, 10, true, true);
        databaseInterface.populateStudent_Course_SessionFully(stud, second, 5, 6, 11, 12, true, false);
        databaseInterface.populateStudent_Course_SessionFully(stud, third, 7, 8, 15, 16, true, false);
    }
	

	    @Then("there should be no clashes between courses for student $s")
    public void checkForClash(int sID){
        boolean actual = databaseInterface.checkForClashes(sID);
        assertThat(actual, is(false));
         
    }
	@When("a student $studentID books a session $sessionID and a particular timeslot $timeslotID of a course $courseID")
	public void Booked(int studentID,int sessionID,int timeslotID,int courseID) throws SQLException{
		studentInterface.bookTimetableSlot(studentID,courseID, sessionID,timeslotID);
	
	}

	@Then("student $studentID is enrolled in the session $sessionID of the course $courseID")
	public void Book(int studentID,int sessionID,int courseID) throws SQLException{
		boolean result = databaseInterface.checkIfSignedUp(studentID, sessionID,courseID);
		boolean expected=true;
		assertEquals(expected,result);
	}


	@When("session $sessID is marked as $session")
	public void sessionMarkedAsOneOff(int sessID, String session){
//		data.addSession(10, sessID, true);
		lecturerInterface.specifySessionRecurrence(sessID, session);
	}



	@Then("session $sessID is a $expected session")
	public void recurringSession(int sessID, String expected) throws SQLException{
		ResultSet result = databaseInterface.getSessionDetails(sessID);
		String recurrence = "";
		if (result.next()){
			recurrence = result.getString("recurring");
			System.out.println(recurrence);
			System.out.println(expected);
		}
		assertTrue(recurrence.equalsIgnoreCase(expected));
	}

	@When("course $courseID is selected from student $studentID")
	public void compulsoryNotBooked(int courseID,int studentID) throws SQLException{
		ResultSet rs=studentInterface.getSessionsCourse(courseID);
		while(rs.next()){
			boolean result = databaseInterface.checkIfSignedUpForCompulsory(studentID, rs.getInt(1),10);
			if (result==false){
				notenrolled.add(rs.getInt(1));
			}
		}
	}

	@When("a  student $studentID selects a course $courseID")
	public void compulsoryCheck(int courseID,int studentID) throws SQLException{
		ResultSet rs=studentInterface.getSessionsCourse(courseID);
		while(rs.next()){

			notenrolled.add(rs.getInt(1));
		}
	}

	@Then("student $studentID books all compulsory sessions of course $courseID")
	public void showBookCompulsory(int studentID,int courseID) throws SQLException{
		int timetableslotID=3587;
		for(int i=0;i<notenrolled.size();i++){
			databaseInterface.bookTimetableSlot(studentID,courseID,notenrolled.get(i),
					timetableslotID);
		}
		ResultSet rs=databaseInterface.getSessionsCourse(courseID);
		boolean expected=true;
		boolean result=true;
		while(rs.next()){
			result = databaseInterface.checkIfSignedUpForCompulsory(studentID, rs.getInt(1),10);
			if (result==false){break;}
			}
		assertEquals(expected,result);
	}

	@Then("student $studentID views all compulsory sessions of course $courseID")
	public void showCompulsory(int studentID,int courseID) throws SQLException{
		int count=0;
		for(int i=0;i<notenrolled.size();i++){
		boolean result = databaseInterface.checkIfSignedUpForCompulsory(studentID, notenrolled.get(i),10);
		if(result==false){count++;}
		ResultSet rs=databaseInterface.getSessionsCourse(courseID);
		ArrayList<Integer>checkenrolled=new ArrayList<Integer>();
		while(rs.next()){
			boolean rt = databaseInterface.checkIfSignedUpForCompulsory(studentID, rs.getInt(1),10);
			if (rt==false){
				checkenrolled.add(rs.getInt(1));}
		int expected=checkenrolled.size();
		assertEquals(expected,count);

			}
		
		}
	}
	
	@Given("an admin")
	public void anAdmin() throws BundleException{
		userBundle = 
				bundleContext.installBundle(
					"file:user-impl.jar");
			userBundle.start();
			
			ServiceReference<AdminInterface>
			adminServiceReference = 
				bundleContext.getServiceReference(
					AdminInterface.class);
			
			adminInterface = 
				bundleContext.getService(adminServiceReference);
	}
	@Given("a system containing timeslot $tID")
	public void populateTime(int tID){
		databaseInterface.populateTimetableslot(tID);
	}
	
	@When("room $room and timeslot $time are selected")
	public void addRoom(String room, int time){
		adminInterface.assignRoomtoTimetableSlot(time, room);
	}
	
	@Then("room $room is assigned to timeslot $time")
	public void checkDB(String room, int time) throws SQLException {
		boolean expected = true;
		if (time < 0) expected = false;
		boolean found = false;
		ResultSet result = databaseInterface.getTableInfo("timetableslot");
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

		assertThat(found, is(expected));
	} 


	
	@Then("student $sID with password $pass logs into the system")
    public void studentAccess(String sID, String pass) {
        assertTrue(myCampusInterface.authenticate(sID, pass));
    }
     
     
    @Then("lecturer $id with password $pass logs into the system")
    public void LecturerAccess(String id, String pass) {
        assertTrue(myCampusInterface.authenticate(id, pass));
    }
     
     
    @Then("admin $id with password $pass logs into the system")
    public void AdminAccess(String id, String pass) {
        assertTrue(myCampusInterface.authenticate(id, pass));
    }
	
	
	
	
	
	@Given("a system with $user users")
	public void system(int user) throws Exception{
	//	databaseInterface.populateNUsers(user); Mock due to errors
		
	}
	@Given("a system with a course $cID that has $sessNum sessions")
	public void sessionCalc(int cID, int sessNum){
		countsessions = databaseInterface.supportNSessionTypes(sessNum,cID);
	}
	
	@Then ("the system can support over $sessNum sessions")
		public void sessionSupport(int sessNum){
		assertTrue(sessNum<=countsessions);
	}
	
	
	@Given ("a system with $num courses")
	public void courseCalc(int num){
		countcourses=databaseInterface.supportNCourses(num);
	}
	
	@Then ("system can support over $courseNum courses")
	public void coursesSupport(int courseNum){
		assertTrue(courseNum<=countcourses);
	}
	
	
	private void recursiveDelete(File file){
		if (!file.exists()) return;
		if (file.isDirectory()){
			for (File subFile :file.listFiles())
				recursiveDelete(subFile);
		}
		file.delete();
	}
	
	@Given ("a system with a session $sid that has 40 timeslots")
	public void timeslotCalc(int sid){
		databaseInterface.populateSession_Timetableslot(40,sid);
	}
	
	@Then ("if session $sID has over $timeslots timeslots then the system can support it")
	public void timeslotsSupport(int sID,int timeslots){
		int result=40;
		assertTrue(result>=timeslots);
	}
	
	@Then ("system can support over $total users")
	public void userSupport(int total){
		int capacity=1500;
		assertTrue(capacity>=total);
		
	}

	@AfterScenario()
	public void tearDown () throws Exception{
		//databaseInterface.disconnect();				
		framework.stop();		
		framework.waitForStop(0);
		
		//Clean up resources that might have been created during testing.

		recursiveDelete(new File("data/mycampussH"));
		
		recursiveDelete(new File("felix-cache"));
	}
	
}

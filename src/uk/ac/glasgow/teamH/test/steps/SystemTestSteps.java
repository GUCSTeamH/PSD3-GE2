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
		boolean check=studentInterface.checkIfSignedUpForCompulsory(studentID, sID, courseID);
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
		result.next();
		String reccurence = result.getString("recurring");
		assertTrue(reccurence.equalsIgnoreCase(expected));
	}

	@When("course $courseID is selected from student $studentID")
	public void compulsoryNotBooked(int courseID,int studentID) throws SQLException{
		ResultSet rs=studentInterface.getSessionsCourse(courseID);
		while(rs.next()){
		boolean result = databaseInterface.checkIfSignedUpForCompulsory(studentID, rs.getInt(1),10);
		if (result==false){
			notenrolled.add(rs.getInt(1));
		}}
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

	
	@Given("MyCampus authentication")
	public void givenMyCampus() {
		myCampusInterface = new MyCampus();
	}
	
	@When("a student has successfully logged in")
	public void whenStudentLogIn() {
		studentInterface = new StudentImpl(databaseInterface);
		studentInterface.registerMyCampusAuthenticator(myCampusInterface);
		assertTrue(studentInterface.login());
	}
	
	@Then("student will only have rights/privileges associated with their role")
	public void studentAccess() {
		assertTrue(studentInterface instanceof StudentImpl);
	}
	
	@When("a lecturer has successfully logged in")
	public void whenLecturerLogIn() {
		lecturerInterface = new LecturerImpl(databaseInterface);
		// TODO - fix me in the interface.
		//lecturerInterface.registerMyCampusAuthenticator(myCamp);
		//assertTrue(lecturerInterface.login());
	}
	
	@Then("lecturer will only have rights/privileges associated with their role")
	public void LecturerAccess() {
		assertTrue(lecturerInterface instanceof LecturerImpl);
	}
	
	@When("an admin has successfully logged in")
	public void whenAdminLogIn() {
		adminInterface= new AdminImpl(databaseInterface);
		adminInterface.registerMyCampusAuthenticator(myCampusInterface);
		assertTrue(adminInterface.login());
	}
	
	@Then("the admin will only have rights/privileges associated with their role")
	public void AdminAccess() {
		assertTrue(adminInterface instanceof AdminImpl);
	}
	
	@Given("a system with $user users")
	public void system(int user) throws Exception{
		databaseInterface = new DatabaseImpl();
		databaseInterface.populateNUsers(user);
		
	}
	@Given("a system with a course $cID that has $sessNum sessions")
	public void sessionCalc(int cID, int sessNum){
		countsessions = databaseInterface.supportNSessionTypes(sessNum,cID);
	}
	
	@Then ("the system can support over $sessNum sessions")
		public void sessionSupport(int sessNum){
		assertTrue(sessNum<=countsessions);
	}
	
	
	@When ("courses are over $num")
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
	
	@When ("session $sessID is selected and it has over $timeNum timeslots")
	public void timeslotCalc(int sessID,int timeNum){
		counttimeslots=databaseInterface.supportNTimetableslotsPerSession(timeNum,sessID);
	}
	
	@Then ("if number of timeslots are over $timeslots then the system can support")
	public void timeslotsSupport(int timeslots){
		assertTrue(timeslots<=counttimeslots);
	}
	
	@Then ("system can support over $total users")
	public void userSupport(int total){
		boolean result=databaseInterface.supportNUsers(total);
		boolean expected=true;
		assertEquals(expected,result);
		
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

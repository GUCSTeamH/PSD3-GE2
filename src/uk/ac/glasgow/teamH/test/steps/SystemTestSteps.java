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

import uk.ac.glasgow.teamH.database.DatabaseInterface;
import uk.ac.glasgow.teamH.database.impl.DatabaseImpl;
import uk.ac.glasgow.teamH.test.ConfiguredFrameworkFactory;
import uk.ac.glasgow.teamH.user.AdminInterface;
import uk.ac.glasgow.teamH.user.LecturerInterface;
import uk.ac.glasgow.teamH.user.StudentInterface;
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
	
	private LecturerImpl lect;
	private StudentImpl stud;
	private DatabaseImpl data;
	
	/**********************************************************************/
	
	@Given("a lecturer")
	public void aLecturer() throws Exception{
	
		data = new DatabaseImpl();
		lect= new LecturerImpl(data);
	
	}
	
	@When("session $sessID is marked as $session")
	public void sessionMarkedAsOneOff(Integer sessID, String session){
		data.addSession(10, sessID, true);
		lect.specifySessionRecurrence(sessID, session);
	}
	
	
	
	@Then("session $sessID is a $expected session")
	public void recurringSession(Integer sessID, String expected) throws SQLException{
		ResultSet result = data.getTimetableslotDetails(sessID);
		String reccurance="";
		//if(result==null){reccurance =expected;}
		//else 
			reccurance = result.getString(2);
		assertEquals(expected,reccurance);
	}
	
	/*********************************************************************/
	@Given("an admin")
	public void anAdmin(){
		ServiceReference<AdminInterface>
		adminReference = 
			bundleContext.getServiceReference(
				AdminInterface.class);
		
		this.admin = bundleContext.getService(adminReference);
	}
	
	/*********************************************************************/
	ArrayList<Integer> notenrolled;
	@Given("a student")
	public void aStudent(){
		data = new DatabaseImpl();
		stud= new StudentImpl(data);
		notenrolled=new ArrayList<Integer>();
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
}}

}

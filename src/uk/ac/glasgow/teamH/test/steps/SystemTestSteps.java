package uk.ac.glasgow.teamH.test.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	private DatabaseImpl data;
	
	/**********************************************************************/
	
	@Given("a lecturer")
	public void aLecturer() throws Exception{
	
		data = new DatabaseImpl();
		lect= new LecturerImpl(data);
	
	}
	
	@When("session $sessID is marked as $session")
	public void sessionMarkedAsOneOff(Integer sessID, String session){
		lect.specifySessionRecurrence(sessID, session);
	}
	
	
	
	@Then("session $sessID is a $expected session")
	public void recurringSession(Integer sessID, String expected) throws SQLException{
		ResultSet result = data.getSessionDetails(sessID);
		result.next();
		String reccurance = result.getString(2);
		assertTrue(reccurance.equals("one-off"));
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
	@Given("a student")
	public void aStudent(){
		ServiceReference<StudentInterface> 
		studentReference = 
			bundleContext.getServiceReference(
				StudentInterface.class);
		
		this.student = bundleContext.getService(studentReference);
	}
	
	
}

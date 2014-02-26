package uk.ac.glasgow.teamH.test.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

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

import uk.ac.glasgow.teamH.database.impl.DatabaseImpl;
import uk.ac.glasgow.teamH.test.ConfiguredFrameworkFactory;
import uk.ac.glasgow.teamH.user.AdminInterface;
import uk.ac.glasgow.teamH.user.LecturerInterface;
import uk.ac.glasgow.teamH.user.StudentInterface;

public class SystemTestSteps {

	private BundleContext bundleContext;
	private Framework framework;
	private Bundle repository_impl;
	
	private AdminInterface admin;
	private LecturerInterface lecturer;
	private StudentInterface student;
	
	private DatabaseImpl db;
	
	@Before
	public void aRepositoryComponent() throws Exception{
		String extraPackages =
			"uk.ac.glasgow.teamH.database";
		
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
		
		repository_impl = 
			bundleContext.installBundle(
				"file:repository-impl.jar");
		repository_impl.start();
	}
	
	/**********************************************************************/
	
	@Given("a lecturer")
	public void aLecturer(){
		ServiceReference<LecturerInterface> 
		lecturerReference = 
			bundleContext.getServiceReference(
				LecturerInterface.class);
		
		this.lecturer = bundleContext.getService(lecturerReference);
	}
	
	@When("session $sessID is marked as one-off")
	public void sessionMarkedAsOneOff(Integer sessID){
		db.specifySessionRecurrence(sessID, "one-off");
	}
	
	@Then("the session $sessID is not repeated")
	public void recurringSession(Integer sessID, Integer expected){
		ResultSet result = db.getTimetableslotDetails(sessID);
		Integer actual = 0;
		try {
			while (result.next()){
				actual++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertThat(actual, equalTo(expected));
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

package uk.ac.glasgow.teamH.userProcedures;

import uk.ac.glasgow.teamH.user.StudentInterface;

public class StudentActivities {

	private StudentInterface student;
	
	
	StudentActivities(StudentInterface stud){
		student = stud;
	}
	
	/*
	 * implement student user stories after db is ready
	 */
	
	public void checkIfEnrolled(){
		student.checkSession();
	}
	
}

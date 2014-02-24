package uk.ac.glasgow.teamH.userProcedures;

import uk.ac.glasgow.teamH.user.LecturerInterface;

public class LecturerActivities {

	private LecturerInterface lect;

	
	LecturerActivities(LecturerInterface l){
		this.lect=l;
	}
	
	public void importFromMycampus(){
		lect.importMyCampusCourse();
	}

	
	public void startLec(){
		lect.addSessionToCourse("COMPSCI20");
		lect.addSessionToCourse("COMPSCI50");
		lect.seeDetails(20);
	}
	
	/*
	 * implement methods for the lecturer to do , after db is ready
	 */

	public void stop(){
		System.out.println("stopped");
	}
	
	
	
}

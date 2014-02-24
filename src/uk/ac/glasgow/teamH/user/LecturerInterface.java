package uk.ac.glasgow.teamH.user;



public interface LecturerInterface {

	void importMyCampusCourse();
	
	void addCourse(String courseID, String name);
	
	void addSessionToCourse(String courseId);
	
	void specifyRecurrent(int sessionId);
	
	void seeDetails(int sessionId);

	
}

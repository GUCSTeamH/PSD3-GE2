package test.inter;

import test.base.Session.SessionType;

public interface LecturerInterface {

	void importMyCampusCourse();
	
	void addCourse(String courseID, String name);
	
	void addSessionToCourse(String courseId);
	
	void specifyRecurrent(int sessionId);
	
	void seeDetails(int sessionId);

	
}

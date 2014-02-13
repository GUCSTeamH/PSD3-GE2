package test.inter;

import test.base.Session.SessionType;

public interface LecturerInterface {

	void importMyCampusCourse();
	
	void addSessionToCourse(String courseId);
	
	void specifySessionType(int sessionId, SessionType type);
	
	void seeDetails(int sessionId);
	
	void printSomething();
	
}

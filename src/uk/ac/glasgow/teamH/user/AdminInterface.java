package uk.ac.glasgow.teamH.user;

import uk.ac.glasgow.teamH.MyCampus.MyCampusInterface;



public interface AdminInterface {

		//public void createTimeSlot();
		
		public void assignRoomtoTimetableSlot(int timetableslotID, String room);
		
		//public void addTimeToSession(int sessionId, int timeId);
		
		public void checkForClashes(int studentID);

		public void registerMyCampusAuthenticator(
				MyCampusInterface myCampusInterface);

		public boolean login(String mail, String pass);
	
	
}

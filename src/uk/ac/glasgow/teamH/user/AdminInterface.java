package uk.ac.glasgow.teamH.user;

import uk.ac.glasgow.teamH.base.TimetableSlot;

public interface AdminInterface {

		public void createTimeSlot();
		
		public void assignRoom(TimetableSlot slot);
		
		public void addTimeToSession(int sessionId, int timeId);
	
	
}

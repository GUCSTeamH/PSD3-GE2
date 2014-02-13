package test.inter;

import test.base.TimetableSlot;

public interface AdminInterface {

		public void createTimeSlot();
		
		public void assignRoom(TimetableSlot slot);
		
		public void addTimeToSession(int sessionId, int timeId);
	
	
}

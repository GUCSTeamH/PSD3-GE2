package test.inter;

import test.base.TimetableSlot;

public interface AdminInterface {

		public TimetableSlot createTimetableSlot(int sessionId);
		
		public void assignRoom(TimetableSlot slot);
	
	
}

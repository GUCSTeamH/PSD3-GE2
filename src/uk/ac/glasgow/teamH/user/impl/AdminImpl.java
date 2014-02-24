package uk.ac.glasgow.teamH.user.impl;

import uk.ac.glasgow.teamH.base.Room;
import uk.ac.glasgow.teamH.base.TimetableSlot;
import uk.ac.glasgow.teamH.database.impl.DatabaseInterface;
import uk.ac.glasgow.teamH.user.AdminInterface;

public class AdminImpl implements AdminInterface {
	DatabaseInterface db;

	
	public void createTimeSlot() {
		db.addTimeslot(50,"9.00", 1, 3, "507");
		
		System.out.println("new timeslot created ");
		
	}
	
	public void assignRoom(TimetableSlot slot){
		System.out.println("Admin : room booked for slot: "+slot.toString());
		Room room = new Room(513, "Boyd Orr",50);
		slot.assignRoom(room);
	}

	
	public void addTimeToSession(int sessionId, int timeId) {
		System.out.println("added timeslot to a session");
		db.bookSession(sessionId, timeId);
	}
	
	

}

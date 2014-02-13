package test.impl;

import database.DatabaseInterface;
import test.base.Room;
import test.base.Timeslot;
import test.base.TimetableSlot;
import test.inter.AdminInterface;

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
		
		db.bookSession(sessionId, timeId);
	}
	
	

}

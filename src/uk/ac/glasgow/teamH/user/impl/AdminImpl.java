package uk.ac.glasgow.teamH.user.impl;

import uk.ac.glasgow.teamH.database.DatabaseInterface;
import uk.ac.glasgow.teamH.user.AdminInterface;
import uk.ac.glasgow.teamH.user.Room;
import uk.ac.glasgow.teamH.user.TimetableSlot;

public class AdminImpl implements AdminInterface {
	DatabaseInterface db;
	String matric;

	AdminImpl(DatabaseInterface db){
		this.db=db;
		matric="110099c";
	}
	
	public void assignRoomtoTimetableSlot(int slot, String room){
		System.out.println("Admin : room booked for slot: "+slot);
		db.assignRoomToTimetableslot(slot, room);
	}

	
	/*public void addTimeToSession(int sessionId, int timeId) {
		System.out.println("added timeslot to a session");
		db.
	}*/
	
	

}

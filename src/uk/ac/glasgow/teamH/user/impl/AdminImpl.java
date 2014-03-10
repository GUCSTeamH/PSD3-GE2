package uk.ac.glasgow.teamH.user.impl;

import uk.ac.glasgow.teamH.database.DatabaseInterface;
import uk.ac.glasgow.teamH.user.AdminInterface;


public class AdminImpl extends User implements AdminInterface {
	DatabaseInterface db;
	String matric;

	public AdminImpl(DatabaseInterface db){
		this.db=db;
		matric="110099c";
	}
	
	public void assignRoomtoTimetableSlot(int slot, String room){
		System.out.println("Admin : room booked for slot: "+slot);
		db.assignRoomToTimetableslot(slot, room);
	}

	


}

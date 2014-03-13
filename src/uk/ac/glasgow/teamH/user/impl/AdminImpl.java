package uk.ac.glasgow.teamH.user.impl;

import uk.ac.glasgow.teamH.MyCampus.MyCampusInterface;
import uk.ac.glasgow.teamH.database.DatabaseInterface;
import uk.ac.glasgow.teamH.user.AdminInterface;


public class AdminImpl extends User implements AdminInterface {
	DatabaseInterface db;
	String matric;
	MyCampusInterface myc;
	
	public AdminImpl(DatabaseInterface db){
		this.db=db;
		matric="110099c";
	}
	
	public void assignRoomtoTimetableSlot(int slot, String room){
		System.out.println("Admin : room booked for slot: "+slot);
		db.assignRoomToTimetableslot(slot, room);
	}
	
	public void checkForClashes(int studentID){
		db.checkForClashes(studentID);
		
	}

	public boolean login(String mail, String pass){
		return myc.authenticate(mail, pass);
	}


}

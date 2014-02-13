package test.impl;

import test.base.Room;
import test.base.Timeslot;
import test.base.TimetableSlot;
import test.inter.AdminInterface;

public class AdminImpl implements AdminInterface {

	
	public TimetableSlot createTimetableSlot(int sessionId) {
		
		System.out.println("Admin : timetable slot booked for session : "+sessionId);
		Timeslot time = new Timeslot(3, "Friday", 11, 12);

		//return timetableslot or update the database
		return new TimetableSlot(time);
		
		
		//assign room?
		
	}
	
	public void assignRoom(TimetableSlot slot){
		System.out.println("Admin : room booked for slot: "+slot.toString());
		Room room = new Room(513, "Boyd Orr",50);
		slot.assignRoom(room);
	}
	
	

}

package uk.ac.glasgow.teamH.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;



public class Main {

	public static void main(String[] args){
		DatabaseImpl db = new DatabaseImpl();
		System.out.println(db.checkForClashes(1111)+" clashes");
		
		
		
		//1
		db.populateMyCampusCourse(1, "OS3");
		db.importMycampusCourse(1);
		ResultSet result = db.getTableInfo("course");
		try {
			while(result.next()){
				System.out.println(result.getInt(1)+" name: "+result.getString(2));
				
			}
		} catch (SQLException e) {
			System.out.println("Error while reading results");
			e.printStackTrace();
		}
		
		//end of 1
		
		
		
		//2

		//no change in the way
		
		//end of 2
		
		
		//4
		
		db.populateSession(1);
		db.specifySessionRecurrence(1, "one-off");
		 result = db.getTableInfo("session");
		try {
			while(result.next()){
				System.out.println("session: "+result.getInt(1)+" recurrence: "+result.getString(2));
				
			}
		} catch (SQLException e) {
			System.out.println("Error while reading results");
			e.printStackTrace();
		}
		
		// end of 4
		
		
		//8
		

		db.populateTimetableslot(1);
		db.assignRoomToTimetableslot(1, "BO720");
		 result = db.getTableInfo("timetableslot");
		try {
			while(result.next()){
				System.out.println("timetablislotid: "+result.getInt(1)+" room: "+result.getString("room"));
				
			}
		} catch (SQLException e) {
			System.out.println("Error while reading results");
			e.printStackTrace();
		}
		
		//end of 8
		
		//9-proove no clashes
		db.populateStudent_Course_SessionFully(1, 2, 3, 4, 12, 13, true,true);
		db.populateStudent_Course_SessionFully(1, 3, 4, 5, 13, 15, true,false);
		db.populateStudent_Course_SessionFully(1, 4, 5, 6, 15, 16, true,false);
		System.out.println("clashes: "+db.checkForClashes(1));
		//end of 9 with no clashes
		
		//9-proove clashes
		db.populateStudent_Course_SessionFully(1, 2, 3, 4, 12, 13, true,true);
		db.populateStudent_Course_SessionFully(1, 3, 4, 5, 13, 15, true,false);
		db.populateStudent_Course_SessionFully(1, 4, 5, 6, 15, 16, true,false);
		db.populateStudent_Course_SessionFully(1, 5, 6, 7, 14, 15, true,false);
		System.out.println("clashes: "+db.checkForClashes(1));
		
		//end of 9 with clashes
		
		
	
	}
	
	
	
	
	
	
	
	
}

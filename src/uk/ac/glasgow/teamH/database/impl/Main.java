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
		
		
		//11
		

		db.populateStudent_Course_SessionPartially(1, 2, 3, false, true);;
		db.bookTimetableSlot(1, 2, 3, 4);
		 
		result = db.getTableInfo("student_course_session");
		try {
			while(result.next()){
				System.out.println("studentid: "+result.getInt(1)+" courseid: "+result.getString("course_id")+" sessionid: "+result.getString("session_id")+" timetableslotid: "+result.getString("timetableslot_id"));
				
				
			}
		} catch (SQLException e) {
			System.out.println("Error while reading results");
			e.printStackTrace();
		}
		
		//end of 11
		
		//12
		

		db.populateStudent_Course_SessionFully(1, 2, 3, 4, 12, 14, false, true);
		db.populateStudent_Course_SessionFully(1, 3, 4, 5, 14, 15, true, false);
		db.populateStudent_Course_SessionPartially(1, 4, 5, true, false);
		db.populateStudent_Course_SessionPartially(1, 5, 6, false, false);


		System.out.println("signed up for all compulsory "+db.checkIfSignedUpForAllCompulsory(1));
				
	
		
		//end of 12
		
		
		//14
		db.populateSession(1);
		db.populateStudent_Course_SessionPartially(2, 3, 1, true, true);
		db.populateStudent_Course_SessionPartially(3, 4, 1, true, false);
		db.populateStudent_Course_SessionPartially(4, 5, 1, true, false);
		System.out.println(db.getSessionInfo(1));
		
		//end of 14
		
		//additional story
		
		db.populateSession_Timetableslot(1, 2);

		 
		result = db.getTableInfo("session_timetableslot");
		try {
			while(result.next()){
				System.out.println("sessionid: "+result.getInt(1)+" timetableslotid: "+result.getInt(2));
				
				
			}
		} catch (SQLException e) {
			System.out.println("Error while reading results");
			e.printStackTrace();
		}
		
	
	}
	
	
	
	
	
	
	
	
}

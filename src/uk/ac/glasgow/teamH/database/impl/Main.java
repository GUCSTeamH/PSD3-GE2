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
		
	}
	
	
	
	
	
	
	
	
}

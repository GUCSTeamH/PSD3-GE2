package uk.ac.glasgow.teamH.database.impl;

public class Main {

	public static void main(String[] args){
		DatabaseImpl db = new DatabaseImpl();
		System.out.println(db.checkForClashes(1111)+" clashes");
	}
}

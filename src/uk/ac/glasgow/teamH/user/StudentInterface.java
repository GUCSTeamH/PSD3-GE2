package uk.ac.glasgow.teamH.user;

import java.sql.ResultSet;

import uk.ac.glasgow.teamH.MyCampus.MyCampusInterface;

public interface StudentInterface {

	public void bookTimetableSlot(int student, int course, int session, int time);
	
	public boolean checkIfSignedUpForCompulsory( int student, int session, int course);
	public ResultSet getSessionsCourse(int courseID);

	public void registerMyCampusAuthenticator(
			MyCampusInterface myCampusInterface);

	public boolean login(String mail, String pass);
}

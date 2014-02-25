package uk.ac.glasgow.teamH.user;

public interface StudentInterface {

	public void bookTimetableSlot(int student, int course, int session, int time);
	
	public boolean checkIfSignedUpForCompulsory( int student, int session, int course);
	
}

public class Admin extends User{

	Admin(String n, String s, String mail) {
		super(n, s, mail);
		// TODO Auto-generated constructor stub
	}
	
	public void createTimetableSlot(){
	};
	public void addRoom(TimetableSlot t,Room r){
		t.assignRoom(r);
	};

}

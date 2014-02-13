

public class TimetableSlot {

	private Room room;
	private boolean free;
	private int capacity;  
	private int weekNumber; //Timeslot attribute	
	private String weekDay; //Timeslot attribute	
	private int startTime; // Timeslot attribute	
	private int endTime; //Timeslot attribute	
	
	public TimetableSlot(int wn, String wd, int st, int et, Room r){
		weekNumber = wn;
		weekDay = wd;
		startTime = st;
		endTime = et;
		room = r;
	}
	public TimetableSlot(int wn, String wd, int st, int et, Room r, boolean free){
		this.weekNumber = wn;
		this.weekDay = wd;
		this.startTime = st;
		this.endTime = et;
		this.room = r;
		this.free = free;
	}
	
	

	
	public int getWeekNum(){
		return weekNumber;
	}
	
	public String getWeekDay(){
		return weekDay;
	}
	
	public int getStartTime(){
		return startTime;
	}
	
	public int getEndTime(){
		return endTime;
	}
	
	public Room getRoom(){
		return room;
	}
	
	public void assignRoom (Room r){
		room = r;
	}
	 
	public boolean isFree(){
		return free;
	}
	
	public void setFree(boolean f){
		free = f;
	}
	
	
	//should be used to add capacity to a session- returns true if successful, 
	// false if the specified capacity is greater than the room capacity
	public boolean setCapacity(int capacity){
		if(capacity<=room.getCapacity()){
			this.capacity = capacity;
			return true;
		}
		else {
			return false;
		}
	}
	public String toString(){
		return "Time :"+Integer.toString(startTime)+" - "+Integer.toString(endTime)+"\n Room: "+room.getRoom()+"\n"+"Students: (list) "+"\n Tutor: (name) \n";
	}
}

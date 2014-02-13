package test.base;


public class TimetableSlot {
	
	
	private Timeslot timeslot;
	private Room room;
	private boolean free;
	private int capacity;
	
	
	public TimetableSlot( Timeslot t){
		timeslot = t;

	}
	
	public TimetableSlot( Timeslot t,boolean free){
		this(t);
		this.free = free;
	}
	
	

	
	public Timeslot getTime(){
		return timeslot;
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
		return timeslot.toString();
	}
}

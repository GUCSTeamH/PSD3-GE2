

public class TimetableSlot {
	private Day day;
	private Time time;
	private Room room;
	private boolean free;
	
	
	public TimetableSlot(Day d, Time t, Room r){
		day = d;
		time = t;
		room = r;
	}
	
	public TimetableSlot(Day d, Time t, Room r, boolean free){
		this(d, t, r);
		this.free = free;
	}
	
	
	
	public Day getDay(){
		return day;
	}
	
	public Time getTime(){
		return time;
	}
	
	public Room getRoom(){
		return room;
	}
	
	public void setRoom (Room r){
		room = r;
	}
	 
	public boolean isFree(){
		return free;
	}
	
	public void setFree(boolean f){
		free = f;
	}
}

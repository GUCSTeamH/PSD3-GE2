package uk.ac.glasgow.teamH.user;


public class TimetableSlot {
	
	private Room room;
	private boolean free;
	private int weekNumber;
	private String weekDay;
	private int startTime;
	private int endTime;
	
	public TimetableSlot(int wn, String wd, int st, int et){
		weekNumber = wn;
		weekDay = wd;
		startTime = st;
		endTime = et;
	}
	
	public TimetableSlot(int wn, String wd, int st, int et,boolean free){
		this(wn,wd,st,et);
		this.free = free;
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
	
	public String toString(){
		return weekDay+" "+startTime+" "+endTime;
	}
	
}

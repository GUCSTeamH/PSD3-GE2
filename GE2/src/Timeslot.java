
public class Timeslot {

	
	private int weekNumber;
	private String weekDay;
	private int startTime;
	private int endTime;
	
	
	public Timeslot (int wn, String wd, int st, int et){
		weekNumber = wn;
		weekDay = wd;
		startTime = st;
		endTime = et;
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
}

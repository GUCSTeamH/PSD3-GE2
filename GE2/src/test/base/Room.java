package test.base;

public class Room {
	
	private int number;
	private String building;
	private int capacity;
	
	
	public Room (int number, String building, int capacity){
		this.number = number;
		this.building = building;
		this.capacity = capacity;
	}
	
	
	public String getRoom(){
		return building + " " + number;
	}
	
	public int getCapacity(){
		return capacity;
	}
	

}

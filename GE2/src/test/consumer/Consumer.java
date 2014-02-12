package test.consumer;

import test.inter.LecturerInterface;

public class Consumer {

	private LecturerInterface lect;
	
	Consumer(LecturerInterface l){
		this.lect=l;
	}
	
	public void print(){
		System.out.println("something");
	}
	
	public void stop(){
		System.out.println("stopped");
	}
	
	
	
}

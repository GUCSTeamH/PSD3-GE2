package test.impl;

import java.util.ArrayList;

import test.LecturerInterface;

public class LecturerImpl implements LecturerInterface {

	ArrayList something;
	
	public LecturerImpl(){
		something=new ArrayList();
	}
	
	public void addSomething(int i) {
		
		if (something.size() < 10) something.add(i);
		
	}
	
	public int printSomething(){
		return (int) something.get(0);
	}

}

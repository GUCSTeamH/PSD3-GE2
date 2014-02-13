package test.consumer;

import test.base.Session.SessionType;
import test.inter.AdminInterface;
import test.inter.LecturerInterface;
import test.inter.StudentInterface;

public class Consumer {

	private LecturerInterface lect;
	private StudentInterface stud;
	private AdminInterface admin;
	
	Consumer(LecturerInterface l){
		this.lect=l;
	}
	
	Consumer(StudentInterface st){
		this.stud=st;
	}
	
	Consumer(AdminInterface ad){
		this.admin=ad;
	}

	
	public void startLec(){
		lect.addSessionToCourse("COMPSCI20");
		lect.addSessionToCourse("COMPSCI50");
		lect.seeDetails(20);
	}
	
	public void startStud(){
		stud.bookSession(9);
		System.out.println(stud.checkSession());
		
	}
	
	public void startAdmin(){
		
		
	}
	
	public void stop(){
		System.out.println("stopped");
	}
	
	
	
}

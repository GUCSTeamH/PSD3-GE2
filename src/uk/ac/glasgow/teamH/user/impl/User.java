package uk.ac.glasgow.teamH.user.impl;

import uk.ac.glasgow.teamH.MyCampus.MyCampusInterface;

public abstract class User {
	MyCampusInterface mycampusAuthenticator;
	String name, surname, email, password;
	
	protected User(String n, String s, String mail,String p){
		name=n;
		surname=s;
		email=mail;
		password = p;
	}
	
	
	public User(){
		
	}
	
	public void registerMyCampusAuthenticator(MyCampusInterface mycampus){
		mycampusAuthenticator = mycampus;
	}
	
	public boolean login(){
		if(mycampusAuthenticator!=null)
			return mycampusAuthenticator.authenticate(email, password);
		else {
			System.out.println("Please register for MyCampus Authenticator first!!!");
			return false;
		}
	}
	
	
}

package test.impl;

import MyCampus.AuthenticatorInterface;

public abstract class User {
	AuthenticatorInterface mycampusAuthenticator;
	String name, surname, email, password;
	
	protected User(String n, String s, String mail,String p){
		name=n;
		surname=s;
		email=mail;
		password = p;
	}
	
	
	public User(){
		
	}
	
	public void registerMyCampusAuthenticator(AuthenticatorInterface authenticator){
		mycampusAuthenticator = authenticator;
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

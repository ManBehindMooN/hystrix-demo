package com.trivadis.demo.hystrix;

import java.util.Date;

public class UserClient {
	
	private static Person fallback = new Person();
	 
	static {
		fallback.lastname = "Doe";
		fallback.firstname = "Jon";
		fallback.userid = "0001";
		fallback.telefonnummer = "-";
		fallback.readtime = new Date();
		fallback.state = Person.State.fallbacked;
	}
	
	public UserClient() {
		super();
	}
	
	public static Person loadPerson(String userid, int worktime) throws Exception {
		if (userid.equals("1111")) {
			Person person = new Person();
			person.lastname = "X";
			person.firstname = "Mr.";
			person.userid = userid;
			person.telefonnummer = "000-1111";
			person.readtime = new Date();
			person.state = Person.State.live;
			Thread.sleep(worktime);
			return person;
		}
		
		throw new RuntimeException("User not found");
	}

	public static Person loadFallback (String userid) {
		if (fallback.userid.equals(userid)) {
			return fallback;
		}
		throw new RuntimeException("Service down");
	}

}
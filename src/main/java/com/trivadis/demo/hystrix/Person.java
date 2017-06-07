package com.trivadis.demo.hystrix;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Typing;

@JsonSerialize(typing = Typing.STATIC)
public class Person {

	public String firstname;
	public String lastname;
	public String userid;
	public String telefonnummer;
	public Date readtime;
	public State state;

	public enum State {
		live, fallbacked
	}

}

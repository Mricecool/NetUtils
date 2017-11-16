package com.netutils;

public class MySingle {

	private static MySingle mySingle = new MySingle();

	private MySingle() {

	}

	public static MySingle getInstance() {
		return mySingle;
	}

}

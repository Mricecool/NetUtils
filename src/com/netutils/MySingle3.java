package com.netutils;

public class MySingle3 {

	private static MySingle3 mySingle3;

	private MySingle3() {

	}

	public static MySingle3 getInstance() {
		if (mySingle3 == null) {
			synchronized (mySingle3) {
				if (mySingle3 == null) {
					mySingle3 = new MySingle3();
				}
			}
		}
		return mySingle3;
	}

}

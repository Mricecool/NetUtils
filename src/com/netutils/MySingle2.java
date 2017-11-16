package com.netutils;

public class MySingle2 {

	private static MySingle2 mSingle2 = null;

	private MySingle2() {

	}

	public static synchronized MySingle2 getInstance() {
		if (mSingle2 == null) {
			mSingle2 = new MySingle2();
		}
		return mSingle2;
	}

}

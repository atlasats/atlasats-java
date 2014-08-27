package com.atlas.websockets;


public class Util {

	public static double round (double val, int decimals) {
		double exp = powers[decimals];
		val *= exp;
		val = (double) Math.round (val);
		return val / exp;
	}
	
	public static double roundDown (double val) {
		return round (val - 0.00005, 4);
	}
	
	public static double roundUp (double val) {
		return round (val + 0.00004999, 4);
	}

	public static final double MIN_SIZE = 0.0001;
	
	public static final double MIN_PRICE = 0.01;
	
	private static final double[] powers = { 1, 10, 100, 1000, 10000 };
}

package com.sparkx120.jwake.graphics2d.mandlebrot;

public class Mandlebrot {
	private static int iterationsMax = 1000;
	public static void main(String[] args){
		if(args.length == 1){
			iterationsMax = Integer.parseInt(args[0]);
		}
		
	}
}

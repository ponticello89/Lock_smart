package com.lockscreen;

public class Utility {

	public int getValuePercentOf(int percent, int value){
						
		return (value/100)*percent;
	}
	
	public String dxFiller(String value, String filler, int lenght){
		
		while (value.length() < lenght) {
			value = filler + value;			
		}
		
		return value;
	}
}

package com.lambda;

public class Works {
	
	private String name;
	private String number;
	private String year;
	
	public Works(String name,String number,String year) {
		this.name = name;
		this.number = number;
		this.year = year;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

}

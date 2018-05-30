package com.lambda;

public class Employee {
	
	private String name;
	private String city;
	private int sales;
	
	public Employee(String name,String city,int sales) {
		this.name = name;
		this.city = city;
		this.sales = sales;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}
}

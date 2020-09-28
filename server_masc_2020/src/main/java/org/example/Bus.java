package org.example;

public class Bus {
	
	private int busID;
	private float averageFeedback;
	private String driver;
	private int totalSeats;
	private int usedSeats;
	
	public int getBusID() {
		return busID;
	}
	public void setBusID(int busID) {
		this.busID = busID;
	}
	public float getAverageFeedback() {
		return averageFeedback;
	}
	public void setAverageFeedback(float averageFeedback) {
		this.averageFeedback = averageFeedback;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public int getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	public int getUsedSeats() {
		return usedSeats;
	}
	public void setUsedSeats(int usedSeats) {
		this.usedSeats = usedSeats;
	}

}

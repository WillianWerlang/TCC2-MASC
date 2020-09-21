package org.example;

public class BusStop {
	
	public BusStop(int iD, double averagefeedback) {
		super();
		ID = iD;
		this.averagefeedback = averagefeedback;
	}
	
	private int ID;
	private double averagefeedback;
	
	public int getID() {
		return ID;
	}
	
	public double getAveragefeedback() {
		return averagefeedback;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public void setAveragefeedback(double averagefeedback) {
		this.averagefeedback = averagefeedback;
	}	
}

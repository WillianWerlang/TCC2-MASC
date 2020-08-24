package org.example;

import java.time.LocalTime;

public class Bus {

	public Bus(int ID, int totalSpaces) {
		super();
		this.ID = ID;
		this.totalSpaces = totalSpaces;
	}
	
	private int ID;
	private LocalTime startCurrentSchedule;
	private BusRoute currentSchedule;
	private Position lastPosition;
	private int totalSpaces;
	private int usedSpaces;
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public BusRoute getCurrentSchedule() {
		return currentSchedule;
	}
	
	public void setCurrentSchedule(BusRoute currentSchedule) {
		this.currentSchedule = currentSchedule;
	}
	
	public Position getLastPosition() {
		return lastPosition;
	}
	
	public void setLastPosition(Position lastPosition) {
		this.lastPosition = lastPosition;
	}
	
	public int getTotalSpaces() {
		return totalSpaces;
	}
	
	public void setTotalSpaces(int totalSpaces) {
		this.totalSpaces = totalSpaces;
	}
	
	public int getUsedSpaces() {
		return usedSpaces;
	}
	
	public void setUsedSpaces(int usedSpaces) {
		this.usedSpaces = usedSpaces;
	}
	
	public boolean isAvailable() {
		return true;
	}

	public LocalTime getStartCurrentSchedule() {
		return startCurrentSchedule;
	}

	public void setStartCurrentSchedule(LocalTime startCurrentSchedule) {
		this.startCurrentSchedule = startCurrentSchedule;
	}
}

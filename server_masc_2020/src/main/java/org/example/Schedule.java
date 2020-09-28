package org.example;

import java.sql.Timestamp;
import java.util.List;

public class Schedule {

	private int scheduleID;
	private int busRouteId;
	private List<Timestamp> time;
	private Bus bus;
	
	public int getScheduleID() {
		return scheduleID;
	}
	
	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}
	
	public int getBusRoute() {
		return busRouteId;
	}
	
	public void setBusRoute(int busRouteId) {
		this.busRouteId = busRouteId;
	}
	
	public List<Timestamp> getTime() {
		return time;
	}
	
	public void setTime(List<Timestamp> time) {
		this.time = time;
	}
	
	public Bus getBus() {
		return bus;
	}
	
	public void setBus(Bus bus) {
		this.bus = bus;
	}
}

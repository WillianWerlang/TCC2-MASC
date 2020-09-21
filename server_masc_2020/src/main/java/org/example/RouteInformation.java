package org.example;

public class RouteInformation {
	
	private int busRouteID;
	private int start;
	private int end;
	private float distance;
	
	public int getBusRouteID() {
		return busRouteID;
	}
	
	public void setBusRouteID(int busRouteID) {
		this.busRouteID = busRouteID;
	}
	
	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
	
	public float getDistance() {
		return distance;
	}
	
	public void setDistance(float distance) {
		this.distance = distance;
	}
}

package org.example;

public class Position {
	
	public Position(int ID, float lat, float lng, BusStop busStop) {
		super();
		this.ID = ID;
		this.lat = lat;
		this.lng = lng;
		this.busStop = busStop;
	}
	
	private int ID;
	private float lat;
	private float lng;
	private BusStop busStop;
	
	public int getID() {
		return ID;
	}
	
	public float getLat() {
		return lat;
	}
	
	public float getLng() {
		return lng;
	}
	
	public BusStop getBusStop() {
		return busStop;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public void setLat(float lat) {
		this.lat = lat;
	}
	
	public void setlng(float lng) {
		this.lng = lng;
	}
	
	public void setBusStop(BusStop busStop) {
		this.busStop = busStop;
	}
}

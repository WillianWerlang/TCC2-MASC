package org.example;

public class Position {
	
	public Position(int ID, float lat, float longe, BusStop busStop) {
		super();
		this.ID = ID;
		this.lat = lat;
		this.longe = longe;
		this.busStop = busStop;
	}
	
	private int ID;
	private float lat;
	private float longe;
	private BusStop busStop;
	public int getID() {
		return ID;
	}
	public float getLat() {
		return lat;
	}
	public float getLonge() {
		return longe;
	}
	public BusStop getBusStop() {
		return busStop;
	}
	
}

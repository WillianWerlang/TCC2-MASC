package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Position {
	
	public Position(int ID, double lat, double longe, BusStop busStop) {
		super();
		this.ID = ID;
		this.lat = lat;
		this.longe = longe;
		this.busStop = busStop;
	}
	
	private int ID;
	private double lat;
	private double longe;
	private BusStop busStop;
	
}

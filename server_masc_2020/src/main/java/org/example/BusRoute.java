package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BusRoute {
	
	public BusRoute(int iD) {
		super();
		ID = iD;
		this.positions = new ArrayList<Position>();
	}
	
	private int ID;
	private List<Position> positions;
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public List<Position> getPositions() {
		return positions;
	}
	
	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
	
}

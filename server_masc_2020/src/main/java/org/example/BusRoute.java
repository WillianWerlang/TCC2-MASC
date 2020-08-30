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
	private int start;
	private int end;
	
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
	
}

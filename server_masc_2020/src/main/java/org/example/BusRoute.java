package org.example;

import java.util.ArrayList;
import java.util.List;

public class BusRoute {
	
	public BusRoute(int iD, String name) {
		super();
		this.ID = iD;
		this.Name = name;
		this.positions = new ArrayList<Position>();
		
	}
	
	private int ID;
	private String Name;
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

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	} 	
}

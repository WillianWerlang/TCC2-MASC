package org.example;

import java.util.Date;

public class Feedback {

	public Feedback(BusStop busStop, int note, User user, Date date) {
		super();
		this.busStop = busStop;
		this.note = note;
		this.user = user;
		this.date = date;
	}
	
	private BusStop busStop;
	private int note;
	private User user;
	private Date date;
}

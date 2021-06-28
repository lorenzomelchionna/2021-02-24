package it.polito.tdp.PremierLeague.model;

public class Event {
	
	public enum EventType {
		GOAL,
		ESPULSIONE,
		INFORTUNIO
	};
	
	private int t;
	private EventType type;
	
	public Event(int t, EventType type) {
		super();
		this.t = t;
		this.type = type;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

}

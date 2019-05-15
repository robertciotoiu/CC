package main;

import java.util.ArrayList;

public class State {
	String name;
	ArrayList<Transition> transitions;

	public State(String name) {
		this.name = name;
		transitions = new ArrayList<Transition>();
	}

	public void addTransition(Transition t) {
		transitions.add(t);
	}
}
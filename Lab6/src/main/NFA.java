package main;

public class NFA extends NfaToDfa {
	DFA convertToDFA() {

		DFA dfa = new DFA();

		dfa.input = input;
		newStates(dfa, initial);

		return dfa;
	}

	private void newStates(DFA dfa, State state) {

		if (state.equals(initial)) {
			// If the state is the initial state of nfa
			State newInitial = new State(initial.name);
			dfa.addInitialState(newInitial);
			newStates(dfa, newInitial);
		} else {
			for (String letter : input) {
				// Find the next dfa state
				String name = stateComponents(state, letter);
				if (!name.equals("")) {
					State newState = dfa.findStateInProgramFunction(name);
					// If the state isn't in program function
					if (newState == null) {
						newState = new State(name);
						// Look if the new state has in his name the name of some final state of nfa
						for (State finalState : last) {
							if (newState.name.contains(finalState.name)) {
								dfa.addFinalState(newState);
							}
						}
						// Add the new state to dfa
						dfa.addState(newState);
					}
					state.addTransition(new Transition(letter, newState));
					if (newState.transitions.isEmpty()) {
						newStates(dfa, newState);
					}
				}
			}
		}
	}

	private String stateComponents(State newState, String letter) {
		// Find the next state
		String name = "";
		for (State state : programFunction) {
			boolean contains = newState.name.contains(state.name);
			if (contains) {
				name += nextDFAState(state, letter);
			}
		}
		return name;
	}

	private String nextDFAState(State state, String letter) {
		String name = "";
		for (Transition t : state.transitions) {
			if (t.letter.equals(letter))
				name += t.next.name;
		}
		return name;
	}

}
package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainConsole {
	
	public static NFA nfa = new NFA();
	
	public static void main(String[] args) throws IOException {
		String regex = new String("(a*b|(b*c)|(c*)");
		NFAregex input = NFAregex.compile(regex);//to nfa
		input.writeFile();//prepares the input file used for conversion from nfa to dfa
		System.out.println("-----REGEX-----");
		System.out.println(regex);
		System.out.println("      |"+'\n'+"      |"+'\n'+"      |"+'\n'+"     \\/");
		
		init();
		DFA dfa;
		System.out.println("-----NFA-----");
		System.out.print(nfa);
		dfa = nfa.convertToDFA();
		System.out.println("      |"+'\n'+"      |"+'\n'+"      |"+'\n'+"     \\/");
		System.out.println("-----DFA-----");
		System.out.println(dfa);

	}
	public static void init()
	{
		try {
			FileReader file = new FileReader("src\\main\\Example.txt");
			BufferedReader readFile = new BufferedReader(file);
			// Read a row
			String row = readFile.readLine();
			String[] input = row.split(" ");// take the input
			for (String letter : input) {// separate them
				nfa.addLetterInput(letter);
			}
			row = readFile.readLine();// read initial state
			nfa.addInitialState(new State(row));
			row = readFile.readLine();// read initial state
			String[] finalStates = row.split(" ");
			for (String state : finalStates) {// parse final states
				nfa.addFinalState(new State(state));
			}
			row = readFile.readLine();
			State state1;
			State state2;
			// We read line by line the transitions until \n
			while (row != null) {
				String[] part = row.split(" ");
				state1 = nfa.findStateInProgramFunction(part[0]);
				state2 = nfa.findStateInProgramFunction(part[2]);
				if (state1 == null) {
					nfa.addState(new State(part[0]));
					state1 = nfa.findStateInProgramFunction(part[0]);
				}
				if (state2 == null) {
					if (part[0] == part[2]) {
						state2 = state1;
					} else {
						nfa.addState(new State(part[2]));
						state2 = nfa.findStateInProgramFunction(part[2]);
					}
				}
				state1.addTransition(new Transition(part[1], state2));
				row = readFile.readLine();
			}
			file.close();
		} catch (IOException e) {
			// Exception if the file could not be opened
			System.err.printf("An error occurred opening the file. %s.\n", e.getMessage());
		}
	}

}

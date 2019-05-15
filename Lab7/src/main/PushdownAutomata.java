package main;

import java.util.ArrayList;
import java.util.Stack;

public class PushdownAutomata {
	/*
	 * The aim of this programming assigment is to write a program that simulates a
	 * pushdown automaton which accepts the language L = {wcwR}. The approach we
	 * will use:
	 * 
	 */
	private static Stack stack = new Stack();

	public static void main(String[] args) {
		//valid input example
		String input = "0100000";// set here the string
		int R = 5;// set here the nr. of occurrences of the last 'w'
		char w = '0';// set the w
		char c = '1';// set the c
		// note: in our example w at the power 0 will be 0 w's, w at the power 1 will be
		// 1 w.
		
		//invalid input example
		String input2 = "01000010";
		int R2 = 1;
		char w2 = '0';
		char c2 = '1';
		
		pushdownAutomataV2(input, R, w, c);
		pushdownAutomataV2(input2,R2,w2,c2);

	}

	private static void pushdownAutomataV2(String input, int r, char w, char c) {
		for (int i = 0; i < input.length(); i++) {
			stack.push(input.charAt(i));
		}
		char symbol;

		while (r != 0 && (symbol = (char) stack.pop())== w) {
			r--;
		}
		if (stack.pop().equals(c)) {
		}
		else
		{
			System.out.println("INVALID INPUT");
			return;
		}
		if (stack.pop().equals(w)) {
		}
		else
		{
			System.out.println("INVALID INPUT");
			return;
		}
		if(stack.isEmpty())
		{
			System.out.println("CORRECT INPUT");
			return;
		}
		else
		{
			System.out.println("INVALID INPUT");
			return;
		}
	}

	private static void pushdownAutomata(String input, int r, char w, char c) {
		int state = 0;
		int i = 0;
		while (input != null) {
			switch (state) {
			case 0:
				if (input.charAt(0) == w) {
					stack.push(w);
					state = 1;
				} else {
					System.out.println("Invalid INPUT: Missing first w");
					return;
				}
				break;
			case 1:
				if (input.charAt(1) == c) {
					stack.push(c);
					state = 2;
					input = input.substring(2);
				} else {
					System.out.println("Invalid INPUT: Missing c");
					return;
				}
				break;
			case 2:
				if (input.length() != 0) {
					if (input.charAt(0) == w && r != 0) {
						stack.push(w);
						input = input.substring(1);
						r--;
					} else {
						System.out.println("TOO MANY last w");
						return;
					}
				} else if (r == 0 && input.length() == 0) {
					System.out.println("VALID INPUT");
					System.out.println(stack);
					return;
				}
				break;
			}
		}
	}
}

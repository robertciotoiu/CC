package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class NFAregex {


	public ArrayList<Integer> states;
	public ArrayList<NFAtransition> transitions;
	public int final_state;

	public NFAregex() {
		this.states = new ArrayList<Integer>();
		this.transitions = new ArrayList<NFAtransition>();
		this.final_state = 0;
	}

	public NFAregex(int size) {
		this.states = new ArrayList<Integer>();
		this.transitions = new ArrayList<NFAtransition>();
		this.final_state = 0;
		this.setStateSize(size);
	}

	public NFAregex(char c) {
		this.states = new ArrayList<Integer>();
		this.transitions = new ArrayList<NFAtransition>();
		this.setStateSize(2);
		this.final_state = 1;
		this.transitions.add(new NFAtransition(0, 1, c));
	}

	public void setStateSize(int size) {
		for (int i = 0; i < size; i++)
			this.states.add(i);
	}
	
	public void writeFile() throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter("src\\main\\Example.txt", "UTF-8");
		
		HashMap symbols = new HashMap();
		int i=0;
		for (NFAtransition t : transitions) {//put in file the symbols
			if(!symbols.containsValue(t.trans_symbol))
			{
				symbols.put(i, t.trans_symbol);
				i++;
				writer.print(t.trans_symbol+" ");
			}
		}
		writer.println("");
		writer.println("Initial state: q"+states.get(0));
		writer.println("Final states: q"+final_state);
		for (NFAtransition t : transitions) {
			writer.println("q" + t.state_from  +" " + t.trans_symbol +" q" + t.state_to + "");
		}
		
		
		writer.close();
	}

	public void print() throws FileNotFoundException, UnsupportedEncodingException {
		writeFile();
		
		System.out.print("States: ");
		for(int i = 0; i< states.size();i++)
		{
			System.out.print(states.get(i)+" ");
		}
		System.out.println("");
		System.out.print("Symbols: ");
		HashMap symbols = new HashMap();
		int i=0;
		for (NFAtransition t : transitions) {
			if(!symbols.containsValue(t.trans_symbol))
			{
				symbols.put(i, t.trans_symbol);
				i++;
				System.out.print(t.trans_symbol+" ");
			}
		}
		System.out.println("");
		System.out.println("Initial state: q"+states.get(0));
		System.out.println("Final states: q"+final_state);
		for (NFAtransition t : transitions) {
			System.out.println("q" + t.state_from  +" " + t.trans_symbol +" q" + t.state_to + "");
		}
		
		System.out.println("");
	}

	public static NFAregex kleene(NFAregex n) {

		NFAregex result = new NFAregex(n.states.size() + 2);
		result.transitions.add(new NFAtransition(0, 1, 'E'));

		for (NFAtransition t : n.transitions) {
			result.transitions.add(new NFAtransition(t.state_from + 1, t.state_to + 1, t.trans_symbol));
		}

		result.transitions.add(new NFAtransition(n.states.size(), n.states.size() + 1, 'E'));
		result.transitions.add(new NFAtransition(n.states.size(), 1, 'E'));
		result.transitions.add(new NFAtransition(0, n.states.size() + 1, 'E'));
		result.final_state = n.states.size() + 1;

		return result;
	}

	public static NFAregex concat(NFAregex n, NFAregex m) {
		m.states.remove(0);

		for (NFAtransition t : m.transitions) {
			n.transitions.add(new NFAtransition(t.state_from + n.states.size() - 1, t.state_to + n.states.size() - 1,
					t.trans_symbol));
		}

		for (Integer s : m.states) {
			n.states.add(s + n.states.size() + 1);
		}

		n.final_state = n.states.size() + m.states.size() - 2;
		return n;
	}

	public static NFAregex union(NFAregex n, NFAregex m) {
		NFAregex result = new NFAregex(n.states.size() + m.states.size() + 2);

		result.transitions.add(new NFAtransition(0, 1, 'E'));

		for (NFAtransition t : n.transitions) {
			result.transitions.add(new NFAtransition(t.state_from + 1, t.state_to + 1, t.trans_symbol));
		}

		result.transitions.add(new NFAtransition(n.states.size(), n.states.size() + m.states.size() + 1, 'E'));
		result.transitions.add(new NFAtransition(0, n.states.size() + 1, 'E'));

		for (NFAtransition t : m.transitions) {
			result.transitions.add(new NFAtransition(t.state_from + n.states.size() + 1, t.state_to + n.states.size() + 1,
					t.trans_symbol));
		}

		result.transitions
				.add(new NFAtransition(m.states.size() + n.states.size(), n.states.size() + m.states.size() + 1, 'E'));
		result.final_state = n.states.size() + m.states.size() + 1;
		return result;
	}

	public static boolean alpha(char c) {
		return c >= 'a' && c <= 'z';
	}

	public static boolean alphabet(char c) {
		return alpha(c) || c == 'E';
	}

	public static boolean regexOperator(char c) {
		return c == '(' || c == ')' || c == '*' || c == '|';
	}

	public static boolean validRegExChar(char c) {
		return alphabet(c) || regexOperator(c);
	}

	public static boolean validRegEx(String regex) {
		if (regex.isEmpty())
			return false;
		for (char c : regex.toCharArray())
			if (!validRegExChar(c))
				return false;
		return true;
	}

	public static NFAregex compile(String regex) {
		if (!validRegEx(regex)) {
			System.out.println("Invalid REGEX");
			return new NFAregex();
		}

		Stack<Character> operators = new Stack<Character>();
		Stack<NFAregex> operands = new Stack<NFAregex>();
		Stack<NFAregex> concat_stack = new Stack<NFAregex>();
		boolean ccflag = false;
		char operator;
		char c;
		int para_count = 0;
		NFAregex NFAregex1;
		NFAregex NFAregex2;

		for (int i = 0; i < regex.length(); i++) {
			c = regex.charAt(i);
			if (alphabet(c)) {
				operands.push(new NFAregex(c));
				if (ccflag) {
					operators.push('.');
				} else
					ccflag = true;
			} else {
				if (c == ')') {
					ccflag = false;
					if (para_count == 0) {
						System.out.println("Invalid REGEX: Parathensis not balanced");
						System.exit(1);
					} else {
						para_count--;
					}
					while (!operators.empty() && operators.peek() != '(') {
						operator = operators.pop();
						if (operator == '.') {
							NFAregex2 = operands.pop();
							NFAregex1 = operands.pop();
							operands.push(concat(NFAregex1, NFAregex2));
						} else if (operator == '|') {
							NFAregex2 = operands.pop();
							if (!operators.empty() && operators.peek() == '.') {
								concat_stack.push(operands.pop());
								while (!operators.empty() && operators.peek() == '.') {
									concat_stack.push(operands.pop());
									operators.pop();
								}
								NFAregex1 = concat(concat_stack.pop(), concat_stack.pop());
								while (concat_stack.size() > 0) {
									NFAregex1 = concat(NFAregex1, concat_stack.pop());
								}
							} else {
								NFAregex1 = operands.pop();
							}
							operands.push(union(NFAregex1, NFAregex2));
						}
					}
				} else if (c == '*') {
					operands.push(kleene(operands.pop()));
					ccflag = true;
				} else if (c == '(') { // if any other operator: push
					operators.push(c);
					para_count++;
				} else if (c == '|') {
					operators.push(c);
					ccflag = false;
				}
			}
		}
		while (operators.size() > 0) {
			if (operands.empty()) {
				System.out.println("Invalid REGEX: Not balanced");
				System.exit(1);
			}
			operator = operators.pop();
			if (operator == '.') {
				NFAregex2 = operands.pop();
				NFAregex1 = operands.pop();
				operands.push(concat(NFAregex1, NFAregex2));
			} else if (operator == '|') {
				NFAregex2 = operands.pop();
				if (!operators.empty() && operators.peek() == '.') {
					concat_stack.push(operands.pop());
					while (!operators.empty() && operators.peek() == '.') {
						concat_stack.push(operands.pop());
						operators.pop();
					}
					NFAregex1 = concat(concat_stack.pop(), concat_stack.pop());
					while (concat_stack.size() > 0) {
						NFAregex1 = concat(NFAregex1, concat_stack.pop());
					}
				} else {
					NFAregex1 = operands.pop();
				}
				operands.push(union(NFAregex1, NFAregex2));
			}
		}
		return operands.pop();
	}
}
package main;

/*
 * The aim of this programming assigment is to write a program that simulates a Turing Machine which
accepts the language L = {anbncn| n ≥ 1}.
*/
public class TuringMachine {
	public static void main(String[] args) {
		String input = "000111222";//input string
		char a = '0';//a
		char b = '1';//b
		char c = '2';//c
		turingMachine(input, a, b, c);
	}



	private static void turingMachine(String input, char a, char b, char c) {
		System.out.println(input);
		int state = 0;
		int i = 0;
		while (true) {
			switch (state) {
			case 0:
				if (input.charAt(i) == a) {
					if(i == 0)
					{
						input = 'x' + input.substring(i + 1);
					}
					else {
						input = input.substring(0, i) + 'x' + input.substring(i + 1);
					}
					state = 1;
					i++;
					System.out.println(input);
				}
				else
				{
					for(;i<input.length();i++)
					{
						if(!(input.charAt(i)=='y' ||input.charAt(i)=='z'))
						{
							System.out.println("Incorrect input");
							return;
						}
					}
					System.out.println("Correct input");
					return;
				}
				break;
			case 1:
				if (input.charAt(i) == a) {
					i++;
				} else if (input.charAt(i) == 'y') {
					i++;
				} else {
					if (input.charAt(i) == b) {
						input = input.substring(0, i) + 'y' + input.substring(i + 1);
						state = 2;
						i++;
						System.out.println(input);
					}
					else
					{
						System.out.println("Incorrect input");
						return;
					}
				}
				break;
			case 2:
				if (input.charAt(i) == b) {
					i++;
				} else if (input.charAt(i) == 'z') {
					i++;
				} else if (input.charAt(i) == c) {
					input = input.substring(0, i ) + 'z' + input.substring(i + 1);
					state = 3;
					i--;
					System.out.println(input);
				}
				else
				{
					System.out.println("Incorrect input");
					return;
				}
				break;
			case 3:
				if (input.charAt(i) == b) {
					i--;
				} else if (input.charAt(i) == a) {
					i--;
				} else if (input.charAt(i) == 'z') { 
					i--;
				} else if (input.charAt(i) == 'y') {
					i--;
				} else if (input.charAt(i) == 'x') {
					i++;
					state = 0;
				}
				else
				{
					System.out.println("Incorrect input");
					return;
				}
			}
		}
	}
}

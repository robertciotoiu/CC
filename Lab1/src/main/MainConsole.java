package main;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MainConsole {

	static Hashtable<Integer, String> east = new Hashtable<Integer, String>();
	static Hashtable<Integer, String> west = new Hashtable<Integer, String>();

	public static void main(String args[]) throws Exception {

		List<String> actions = new ArrayList<String>();

		String w = "Man crosses with Wolf ";// case 1
		String g = "Man crosses with Goat ";// case 2
		String c = "Man crosses with Cabbage ";// case 3
		String n = "Man crosses with Nothing ";// case 4

		actions.add(w);
		actions.add(g);
		actions.add(c);
		actions.add(n);

		String man = "man";
		String wolf = "wolf";
		String goat = "goat";
		String cabbage = "cabbage";

		east.put(0, man);
		east.put(1, wolf);
		east.put(2, goat);
		east.put(3, cabbage);
		// automata(east, west, actions);
		automata(actions);
	}

	public static boolean verify_posibility(Hashtable<Integer, String> e) {
		if (e.containsKey(1) && e.containsKey(2) && !e.containsKey(0)) {
			return false;
		}
		if (e.containsKey(2) && e.containsKey(3) && !e.containsKey(0)) {
			return false;
		}
		return true;
	}

	public static boolean check_completed() {
		if (west.containsKey(0) && west.containsKey(1) && west.containsKey(2) && west.containsKey(3)) {
			return true;
		} else
			return false;
	}

	public static void automata(List<String> actions) {
		int state = 1;
		int man_position = 0;// 0-east 1-west;
		print_status();
		while (!check_completed()) {

			man_position = get_man_position();
			switch (state) {
			case 1:
				if (mwgc(man_position)) {
					do_g(man_position);
					print_status();
					state = 1;
				} else
					state = 2;
				break;
			case 2:
				if (wg(man_position)) {
					do_w(man_position);
					print_status();
					state = 1;
				} else {
					state = 3;
				}
				break;
			case 3:
				if (gc(man_position)) {
					do_g(man_position);
					print_status();
					state = 1;
				} else
					state = 4;
				break;
			case 4:
				if (man_position == 1) {
					do_m();
					state = 1;
					print_status();
				} else {
					state = 5;
				}
				break;
			case 5:
				if (man_position == 0 && verify("cabbage")) {
					do_c(man_position);
					print_status();
					state = 1;
				} else
					state = 6;
			case 6:
				if (man_position == 0 && verify("wolf")) {
					do_w(man_position);
					print_status();
					state = 1;
				} else
					state = 7;
				break;
			case 7:
				if (man_position == 0 && verify("goat")) {
					do_g(man_position);
					print_status();
					state = 1;
				} else
					state = 1;
				break;
			}
		}
	}

	private static int get_man_position() {
		if (west.contains("man"))
			return 1;
		else
			return 0;
	}

	private static void print_status() {
		System.out.print(east + " - " + west);
		System.out.println();
	}

	private static boolean verify(String string) {
		if (east.contains(string))
			return true;
		return false;

	}

	private static void do_w(int man_position) {
		if (man_position == 0) {
			west.put(0, "man");
			west.put(1, "wolf");
			east.remove(0);
			east.remove(1);
		} else {
			east.put(0, "man");
			east.put(1, "wolf");
			west.remove(0);
			west.remove(1);
		}
	}

	private static void do_g(int man_position) {
		if (man_position == 0) {
			west.put(0, "man");
			west.put(2, "goat");
			east.remove(0);
			east.remove(2);
		} else {
			east.put(0, "man");
			east.put(2, "goat");
			west.remove(0);
			west.remove(2);
		}
	}

	private static void do_m() {
		east.put(0, "man");
		west.remove(0);
	}

	private static void do_c(int man_position) {
		if (man_position == 0) {
			west.put(0, "man");
			west.put(3, "cabbage");
			east.remove(0);
			east.remove(3);
		} else {
			east.put(0, "man");
			east.put(3, "cabbage");
			west.remove(0);
			west.remove(3);
		}
	}

	private static boolean mwgc(int man_position) {
		if (man_position == 0) {
			if (east.contains("wolf") && east.contains("goat") && east.contains("cabbage")) {
				return true;
			} else
				return false;
		} else {
			if (west.contains("wolf") && west.contains("goat") && west.contains("cabbage")) {
				return true;
			} else
				return false;
		}
	}

	private static boolean wg(int man_position) {
		if (man_position == 0) {
			if (east.contains("wolf") && east.contains("goat")) {
				return true;
			} else
				return false;
		} else {
			if (west.contains("wolf") && west.contains("goat")) {
				return true;
			} else
				return false;
		}
	}

	private static boolean gc(int man_position) {
		if (man_position == 0) {
			if (east.contains("goat") && east.contains("cabbage")) {
				return true;
			} else
				return false;
		} else {
			if (west.contains("goat") && west.contains("cabbage")) {
				return true;
			} else
				return false;
		}
	}

}

package main;

import java.awt.*;
import java.awt.event.*;

// An AWT GUI program inherits the top-level container java.awt.Frame
public class GUI extends Frame {
	private static TextField tfCount;
	private Button state0, state1, btnReset;
	private static int state = 1;
	private static int state2_times = 0;

	// Constructor to setup the GUI components and event handlers
	public GUI() {
		setLayout(new FlowLayout());
		add(new Label("STATE:")); // an anonymous instance of Label
		tfCount = new TextField("0", 10);
		tfCount.setEditable(false); // read-only
		add(tfCount); // "super" Frame adds tfCount

		state0 = new Button("INPUT 0");
		add(state0);

		tfCount.setText("state 1");
		state0.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				automata(0);
			}
		});

		state1 = new Button("INPUT 1");
		add(state1);
		state1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				automata(1);
			}
		});

		btnReset = new Button("Reset");
		add(btnReset);
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				state = 1;
				state2_times = 0;
				tfCount.setText("state 1");
			}
		});

		setTitle("AWT Counter");
		setSize(400, 100);
		setVisible(true);
	}

	public static void automata(int input) {
		switch (state) {
		case 1:
			if (input == 0) {
				state = 1;
				tfCount.setText("state 1");
			} else if (input == 1) {
				state = 2;
				tfCount.setText("state 2");
			}
			break;
		case 2:
			if (state2_times == 1) {
				tfCount.setText("Final state reached");
				break;
			} else {
				state2_times++;
			}
			if (input == 0) {
				state = 3;
				tfCount.setText("state 3");
			} else if (input == 1) {
				state = 2;
				tfCount.setText("Final state reached");
			}
			break;
		case 3:
			if (input == 0 || input == 1) {
				state = 2;
				tfCount.setText("Final state reached");
			}

		}
	}
}
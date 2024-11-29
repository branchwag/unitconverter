import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JLabel label = new JLabel();
		
		label.setText("What would you like to convert?");
		
		frame.setTitle("Unit Converter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.black);
		frame.add(label);
		
		//m to ft 3.28
		//ft to inches 12
		//meters to inches
		
		//hr to min
		//min to seconds
		
		//error if distance to time
		
	}

}

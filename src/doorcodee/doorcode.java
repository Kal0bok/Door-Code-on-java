package doorcodee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class doorcode {
	public static void main(String[] args) {

		// make window
        JFrame frame = new JFrame("Door-code");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 3, 5, 5)); // place for buttons 3x4 with spaces
		
     // array with numbers, space and cancel
        String[] numbers = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "", "Cancel"};
        
     // add buttons
        for (String number : numbers) {
            JButton button = new JButton(number);
            if (number.isEmpty()) {
                button.setEnabled(false); // empty cell disable
            } else if (number.equals("Cancel")) {
                button.addActionListener(e -> System.exit(0)); // close if press Cancel
            } else {
                button.addActionListener(e -> System.out.println("Выбрано: " + number)); // output number
            }
            frame.add(button);
            
         // window settings
            frame.setSize(300, 400);
            frame.setLocationRelativeTo(null); // center
            frame.setVisible(true); // show window
        }
	}

}

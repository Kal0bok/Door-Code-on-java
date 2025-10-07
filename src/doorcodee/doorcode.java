package doorcodee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class doorcode {
	public static void main(String[] args) {

		// make window
		JFrame frame = new JFrame("Код двери");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5); // spaces for buttons
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
		
     // array with numbers, space and cancel
        String[] numbers = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "", "0", "Cancel"};
        
     // button size
        Dimension buttonSize = new Dimension(80, 80); // size for all buttons
     
     // buttons with custom place
        // first column: 7, 4, 1
        gbc.gridx = 0; gbc.gridy = 0; frame.add(createButton("7"), gbc);
        gbc.gridx = 0; gbc.gridy = 1; frame.add(createButton("4"), gbc);
        gbc.gridx = 0; gbc.gridy = 2; frame.add(createButton("1"), gbc);

        // second column: 8, 5, 2, 0
        gbc.gridx = 1; gbc.gridy = 0; frame.add(createButton("8"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; frame.add(createButton("5"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; frame.add(createButton("2"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; frame.add(createButton("0"), gbc);

        // third column: 9, 6, 3, Cancel
        gbc.gridx = 2; gbc.gridy = 0; frame.add(createButton("9"), gbc);
        gbc.gridx = 2; gbc.gridy = 1; frame.add(createButton("6"), gbc);
        gbc.gridx = 2; gbc.gridy = 2; frame.add(createButton("3"), gbc);
        gbc.gridx = 2; gbc.gridy = 3; frame.add(createButton("Cancel"), gbc);
        
     // empty cell in first column, third row
        gbc.gridx = 0; gbc.gridy = 3;
        JButton emptyButton = new JButton("");
        emptyButton.setEnabled(false);
        emptyButton.setPreferredSize(buttonSize);
        frame.add(emptyButton, gbc);
        
     // window settings
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null); // center
        frame.setVisible(true); // show window
	}
		//create a button
        private static JButton createButton(String number, Dimension size) {
            JButton button = new JButton(number);
            button.setPreferredSize(size); // set size
            if (number.isEmpty()) {
                button.setEnabled(false); // empty cell disabled
            } else if (number.equals("Cancel")) {
                button.addActionListener(e -> System.exit(0)); // close if press Cancel
            } else {
                button.addActionListener(e -> System.out.println("Choose: " + number)); // show number in console
            }
            return button;
        
    }      
	}
	


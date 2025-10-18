package doorcodee;

import javax.swing.*;
import java.awt.*;

public class doorcommand {
    private static final String ADMIN_CODE = "000"; // Administrator access code
    private static JLabel displayLabel; // Reference to the display label
    private static StringBuilder displayText; // Reference to the display text

    // Constructor for initializing references
    public doorcommand(JLabel label, StringBuilder text) {
        displayLabel = label;
        displayText = text;
    }

    // Create a button with specified text and size
    public JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setPreferredSize(size); // Set the button size
        button.addActionListener(e -> handleButtonAction(text)); // Bind the action handler
        return button;
    }

    // Handle button actions
    private void handleButtonAction(String buttonText) {
        if (buttonText.equals("Cancel")) {
            displayText.setLength(0); // Clear the text
            displayLabel.setText(" "); // Clear the display
        } else if (buttonText.equals("Enter")) {
            // Check if the entered code equals "000"
            if (displayText.toString().equals(ADMIN_CODE)) {
                JOptionPane.showMessageDialog(null, 
                    "Access granted: Administrator privileges obtained!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                displayText.setLength(0); // Clear the text after successful entry
                displayLabel.setText(" "); // Clear the display
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Access denied: Incorrect code!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                displayText.setLength(0); // Clear the text after failed attempt
                displayLabel.setText(" "); // Clear the display
            }
        } else {
            displayText.append(buttonText); // Append button text to the display
            displayLabel.setText(displayText.toString()); // Update the display
        }
    }
}
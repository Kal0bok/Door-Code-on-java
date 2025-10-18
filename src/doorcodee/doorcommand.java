package doorcodee;

import javax.swing.*;
import java.awt.*;

public class doorcommand {
    private static final String ADMIN_CODE = "000"; // The code for admin access
    private static JLabel displayLabel; // Reference to the display label
    private static StringBuilder displayText; // Reference to the display text

    // Constructor to initialize references
    public doorcommand(JLabel label, StringBuilder text) {
        displayLabel = label;
        displayText = text;
    }

    // Create a button with specified text and size
    public JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setPreferredSize(size); // Set button size
        button.addActionListener(e -> handleButtonAction(text)); // Attach action listener
        return button;
    }

    // Handle button actions
    private void handleButtonAction(String buttonText) {
        if (buttonText.equals("Cancel")) {
            displayText.setLength(0); // Clear text
            displayLabel.setText(" "); // Clear display
        } else if (buttonText.equals("Enter")) {
            // Check if the entered code is "000"
            if (displayText.toString().equals(ADMIN_CODE)) {
                JOptionPane.showMessageDialog(null, 
                    "Access Granted: Administrator Rights Obtained!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                displayText.setLength(0); // Clear text after successful access
                displayLabel.setText(" "); // Clear display
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Access Denied: Invalid Code!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                displayText.setLength(0); // Clear text after failed attempt
                displayLabel.setText(" "); // Clear display
            }
        } else {
            displayText.append(buttonText); // Append button text to display
            displayLabel.setText(displayText.toString()); // Update display
        }
    }
}
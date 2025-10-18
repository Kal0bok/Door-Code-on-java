package doorcodee;

import javax.swing.*;
import java.awt.*;

public class doorcommand {
    private static final String ADMIN_CODE = "000"; // Admin access code
    private static JLabel displayLabel; // Reference to the display label
    private static StringBuilder displayText; // Reference to the display text

    // Constructor to initialize label and text references
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
            displayText.setLength(0); // Clear the text
            displayLabel.setText(" "); // Clear the display
        } else if (buttonText.equals("Enter")) {
            // Check if the entered code matches the admin code
            if (displayText.toString().equals(ADMIN_CODE)) {
                displayLabel.setText("Admin Access Granted"); // Show admin access message
            } else {
                displayLabel.setText("ERROR"); // Show access denied message
            }
            displayText.setLength(0); // Clear the text after checking
        } else {
            displayText.append(buttonText); // Append button text to display
            displayLabel.setText(displayText.toString()); // Update the display
        }
    }
}
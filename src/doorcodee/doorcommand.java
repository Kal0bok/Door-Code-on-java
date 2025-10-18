package doorcodee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class doorcommand {
    private static final String ADMIN_CODE = "000"; // Admin access code
    private static final String FIRST_PART_CODE = "07"; // First part of door code
    private static final String SECOND_PART_CODE = "0208"; // Second part of door code
    private static final String CORRECT_KEY = "9?*6j1%@k0s^"; // Correct key for key menu
    private static final String[] KEY_OPTIONS = {
        CORRECT_KEY,
        "k8#j2@9p5$m*", // Incorrect key 1
        "3%q7v!t4&n0x", // Incorrect key 2
        "a9^b5*k2@m1p", // Incorrect key 3
        "z6$j9#r3%y8v", // Incorrect key 4
        "4@p8^k1*n5!q"  // Incorrect key 5
    }; // Key options for menu
    private static final int APARTMENT_MIN = 1; // Minimum apartment number
    private static final int APARTMENT_MAX = 70; // Maximum apartment number
    private static final int TIMER_DELAY = 5000; // Timer delay in milliseconds (5 seconds)
    private static JLabel displayLabel; // Reference to the display label
    private static StringBuilder displayText; // Reference to the display text
    private static boolean expectingSecondPart = false; // Flag for second part of door code
    private static StringBuilder secondPartText = new StringBuilder(); // Store second part of code
    private static Timer apartmentTimer; // Timer for apartment call delay
    private static Timer doorCodeTimer; // Timer for door code reset

    // Constructor to initialize label and text references
    public doorcommand(JLabel label, StringBuilder text) {
        displayLabel = label;
        displayText = text;
        // Initialize timer for apartment call
        apartmentTimer = new Timer(TIMER_DELAY, e -> handleApartmentTimerAction());
        apartmentTimer.setRepeats(false); // Timer runs only once
        // Initialize timer for door code reset
        doorCodeTimer = new Timer(TIMER_DELAY, e -> handleDoorCodeTimerAction());
        doorCodeTimer.setRepeats(false); // Timer runs only once
    }

    // Handle apartment timer action for apartment call
    private void handleApartmentTimerAction() {
        String input = displayText.toString();
        try {
            int apartmentNumber = Integer.parseInt(input);
            if (apartmentNumber >= APARTMENT_MIN && apartmentNumber <= APARTMENT_MAX) {
                displayLabel.setText("Calling Apartment " + apartmentNumber); // Show call message
                displayText.setLength(0); // Clear the text
            } else {
                displayLabel.setText("Invalid Apartment Number"); // Show invalid message
                displayText.setLength(0); // Clear the text
            }
        } catch (NumberFormatException e) {
            // Not a valid number, do nothing
        }
    }

    // Handle door code timer action for reset
    private void handleDoorCodeTimerAction() {
        if (expectingSecondPart) {
            displayText.setLength(0); // Clear first part text
            secondPartText.setLength(0); // Clear second part text
            expectingSecondPart = false; // Reset second part flag
            displayLabel.setText(" "); // Clear the display
        }
    }

    // Show key selection menu and handle result
    private void showKeyMenu() {
        // Ask user if they want to use a key
        int choice = JOptionPane.showConfirmDialog(null, 
            "Use a key?", 
            "Key Menu", 
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            // Show key options
            String selectedKey = (String) JOptionPane.showInputDialog(null, 
                "Select a key:", 
                "Key Selection", 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                KEY_OPTIONS, 
                KEY_OPTIONS[0]);
            
            if (selectedKey != null) {
                // Check if selected key is correct
                if (selectedKey.equals(CORRECT_KEY)) {
                    displayLabel.setText("Door Opened"); // Show door opened message
                } else {
                    displayLabel.setText("Invalid Key"); // Show invalid key message
                }
                displayText.setLength(0); // Clear the text
                secondPartText.setLength(0); // Clear second part text
                expectingSecondPart = false; // Reset second part flag
            }
        }
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
        // Stop both timers on any button press
        apartmentTimer.stop();
        doorCodeTimer.stop();

        // Check if the wide zero button (bottom) is pressed
        if (buttonText.equals("◯") && !expectingSecondPart) {
            // Check if the wide zero button is the one in the fifth row
            // Since only the wide zero button is in the fifth row, we assume it's that one
            showKeyMenu(); // Show key selection menu
            return;
        }

        if (buttonText.equals("Cancel")) {
            displayText.setLength(0); // Clear the text
            secondPartText.setLength(0); // Clear second part text
            expectingSecondPart = false; // Reset second part flag
            displayLabel.setText(" "); // Clear the display
        } else if (buttonText.equals("Enter")) {
            if (expectingSecondPart) {
                // Check if second part of code is complete
                if (secondPartText.toString().equals(SECOND_PART_CODE)) {
                    displayLabel.setText("Door Opened"); // Show door opened message
                } else {
                    displayLabel.setText("Access Denied"); // Show access denied message
                }
                displayText.setLength(0); // Clear the text
                secondPartText.setLength(0); // Clear second part text
                expectingSecondPart = false; // Reset second part flag
            } else {
                // Check if input is admin code or apartment number
                String input = displayText.toString();
                if (input.equals(ADMIN_CODE)) {
                    displayLabel.setText("Admin Access Granted"); // Show admin access message
                    displayText.setLength(0); // Clear the text
                } else {
                    try {
                        int apartmentNumber = Integer.parseInt(input);
                        if (apartmentNumber >= APARTMENT_MIN && apartmentNumber <= APARTMENT_MAX) {
                            displayLabel.setText("Calling Apartment " + apartmentNumber); // Show call message
                            displayText.setLength(0); // Clear the text
                        } else {
                            displayLabel.setText("Invalid Apartment Number"); // Show invalid message
                            displayText.setLength(0); // Clear the text
                        }
                    } catch (NumberFormatException e) {
                        displayLabel.setText("Access Denied"); // Show access denied message
                        displayText.setLength(0); // Clear the text
                    }
                }
            }
        } else if (buttonText.equals("*")) {
            // Check if first part of code is correct
            if (displayText.toString().equals(FIRST_PART_CODE)) {
                displayText.setLength(0); // Clear first part text
                secondPartText.setLength(0); // Clear second part text
                expectingSecondPart = true; // Set flag to expect second part
                displayLabel.setText("----"); // Show four dashes
                doorCodeTimer.restart(); // Start door code timer
            } else {
                displayText.setLength(0); // Clear the text
                displayLabel.setText("Access Denied"); // Show access denied message
            }
        } else if (expectingSecondPart) {
            // Handle input for second part of code
            if (secondPartText.length() < 4) {
                secondPartText.append(buttonText); // Append digit to second part
                StringBuilder display = new StringBuilder(secondPartText.toString());
                // Append remaining dashes
                while (display.length() < 4) {
                    display.append("-");
                }
                displayLabel.setText(display.toString()); // Update display with current input and dashes
                doorCodeTimer.restart(); // Restart door code timer
            }
        } else {
            // Handle regular input (first part, admin code, or apartment number)
            displayText.append(buttonText); // Append button text to display
            displayLabel.setText(displayText.toString()); // Update the display
            // Start apartment timer for apartment call
            apartmentTimer.restart();
        }
    }
}
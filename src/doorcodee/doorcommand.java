package doorcodee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class doorcommand {
    private static String ADMIN_CODE = "#000#123#"; // Admin access code (mutable)
    private static String FIRST_PART_CODE = "07"; // First part of door code (mutable)
    private static String SECOND_PART_CODE = "0208"; // Second part of door code (mutable)
    private static String CORRECT_KEY = "9?*6j1%@k0s^"; // Correct key for key menu (mutable)
    private static final String[] INCORRECT_KEYS = {
        "k8#j2@9p5$m*", // Incorrect key 1
        "3%q7v!t4&n0x", // Incorrect key 2
        "a9^b5*k2@m1p", // Incorrect key 3
        "z6$j9#r3%y8v", // Incorrect key 4
        "4@p8^k1*n5!q"  // Incorrect key 5
    }; // Incorrect key options
    private static int APARTMENT_MIN_RANGE = 1; // Minimum apartment number (mutable)
    private static int APARTMENT_MAX_RANGE = 15; // Maximum apartment number (mutable)
    private static final int MIN_RANGE_DIFFERENCE = 15; // Minimum allowed range difference
    private static final int TIMER_DELAY = 5000; // Timer delay in milliseconds (5 seconds)
    private static JLabel displayLabel; // Reference to the display label
    private static StringBuilder displayText; // Reference to the display text
    private static boolean expectingSecondPart = false; // Flag for second part of door code
    private static boolean showingConfirmMenu = false; // Flag for admin menu
    private static boolean showingAdminCodeConfirmMenu = false; // Flag for admin code change confirmation menu
    private static boolean showingDoorAccessMenu = false; // Flag for door access sub-menu
    private static boolean showingDoorCodeConfirmMenu = false; // Flag for door code change confirmation menu
    private static boolean showingKeyConfirmMenu = false; // Flag for key change confirmation menu
    private static StringBuilder secondPartText = new StringBuilder(); // Store second part of code
    private static Timer apartmentTimer; // Timer for apartment call delay
    private static Timer doorCodeTimer; // Timer for door code reset
    private static Timer adminCodeTimer; // Timer for admin code reset
    private static int confirmMenuSelectedIndex = 0; // Selected index for menus
    private static doordialogue dialogueSystem; // Dialogue system for apartment calls

    // Constructor to initialize label and text references
    public doorcommand(JLabel label, StringBuilder text) {
    	displayLabel = label;
        displayText = text;
        dialogueSystem = new doordialogue(displayLabel); // Pass display label to dialogue system
        
        // Initialize timer for apartment call
        apartmentTimer = new Timer(TIMER_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleApartmentTimerAction();
            }
        });
        apartmentTimer.setRepeats(false); // Timer runs only once
        
        // Initialize timer for door code reset
        doorCodeTimer = new Timer(TIMER_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDoorCodeTimerAction();
            }
        });
        doorCodeTimer.setRepeats(false); // Timer runs only once
        
        // Initialize timer for admin code reset
        adminCodeTimer = new Timer(TIMER_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAdminCodeTimerAction();
            }
        });
        adminCodeTimer.setRepeats(false); // Timer runs only once
        
        // Enable HTML rendering for bold text
        displayLabel.setText("<html></html>");
    }

    // Handle apartment timer action for apartment call
    private void handleApartmentTimerAction() {
        String input = displayText.toString();
        try {
            int apartmentNumber = Integer.parseInt(input);
            if (apartmentNumber >= APARTMENT_MIN_RANGE && apartmentNumber <= APARTMENT_MAX_RANGE) {
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

    // Handle admin code timer action for reset
    private void handleAdminCodeTimerAction() {
        displayText.setLength(0); // Clear the text
        displayLabel.setText(" "); // Clear the display
    }

    // Show admin menu for selecting Change range, Change admin code, or Change door access
    private void showConfirmMenu() {
        String[] options = {"Change range", "Change admin code", "Change door access"};
        StringBuilder display = new StringBuilder("<html>");
        for (int i = 0; i < options.length; i++) {
            if (i == confirmMenuSelectedIndex) {
                display.append("<span style='display: inline-block; width: 150px; background-color: #ADD8E6; font-weight: bold;'>")
                       .append(options[i])
                       .append("</span>");
            } else {
                display.append("<span style='display: inline-block; width: 150px;'>")
                       .append(options[i])
                       .append("</span>");
            }
        }
        display.append("</html>");
        displayLabel.setText(display.toString()); // Show admin menu with selected option highlighted
        showingConfirmMenu = true; // Set admin menu flag
    }

    // Show confirmation menu for changing admin code
    private void showAdminCodeConfirmMenu() {
        String[] options = {"Yes", "No"};
        StringBuilder display = new StringBuilder("<html>Confirm admin code change? ");
        for (int i = 0; i < options.length; i++) {
            if (i == confirmMenuSelectedIndex - 3) { // Adjust for indices 3 (Yes), 4 (No)
                display.append("<span style='display: inline-block; width: 100px; background-color: #ADD8E6; font-weight: bold;'>")
                       .append(options[i])
                       .append("</span>");
            } else {
                display.append("<span style='display: inline-block; width: 100px;'>")
                       .append(options[i])
                       .append("</span>");
            }
        }
        display.append("</html>");
        displayLabel.setText(display.toString()); // Show confirmation menu with selected option highlighted
        showingAdminCodeConfirmMenu = true; // Set admin code confirm menu flag
    }

    // Show door access sub-menu for selecting Change door code or Change key
    private void showDoorAccessMenu() {
        String[] options = {"Change door code", "Change key"};
        StringBuilder display = new StringBuilder("<html>");
        for (int i = 0; i < options.length; i++) {
            if (i == confirmMenuSelectedIndex - 5) { // Adjust for indices 5 (Change door code), 6 (Change key)
                display.append("<span style='display: inline-block; width: 150px; background-color: #ADD8E6; font-weight: bold;'>")
                       .append(options[i])
                       .append("</span>");
            } else {
                display.append("<span style='display: inline-block; width: 150px;'>")
                       .append(options[i])
                       .append("</span>");
            }
        }
        display.append("</html>");
        displayLabel.setText(display.toString()); // Show door access menu with selected option highlighted
        showingDoorAccessMenu = true; // Set door access menu flag
    }

    // Show confirmation menu for changing door code
    private void showDoorCodeConfirmMenu() {
        String[] options = {"Yes", "No"};
        StringBuilder display = new StringBuilder("<html>Confirm door code change? ");
        for (int i = 0; i < options.length; i++) {
            if (i == confirmMenuSelectedIndex - 7) { // Adjust for indices 7 (Yes), 8 (No)
                display.append("<span style='display: inline-block; width: 100px; background-color: #ADD8E6; font-weight: bold;'>")
                       .append(options[i])
                       .append("</span>");
            } else {
                display.append("<span style='display: inline-block; width: 100px;'>")
                       .append(options[i])
                       .append("</span>");
            }
        }
        display.append("</html>");
        displayLabel.setText(display.toString()); // Show confirmation menu with selected option highlighted
        showingDoorCodeConfirmMenu = true; // Set door code confirm menu flag
    }

    // Show confirmation menu for changing key
    private void showKeyConfirmMenu() {
        String[] options = {"Yes", "No"};
        StringBuilder display = new StringBuilder("<html>Confirm key change? ");
        for (int i = 0; i < options.length; i++) {
            if (i == confirmMenuSelectedIndex - 9) { // Adjust for indices 9 (Yes), 10 (No)
                display.append("<span style='display: inline-block; width: 100px; background-color: #ADD8E6; font-weight: bold;'>")
                       .append(options[i])
                       .append("</span>");
            } else {
                display.append("<span style='display: inline-block; width: 100px;'>")
                       .append(options[i])
                       .append("</span>");
            }
        }
        display.append("</html>");
        displayLabel.setText(display.toString()); // Show confirmation menu with selected option highlighted
        showingKeyConfirmMenu = true; // Set key confirm menu flag
    }

    // Validate new admin code format (#XXX#XXX# where X are digits)
    private boolean isValidAdminCode(String code) {
        if (code.length() != 9) return false;
        if (code.charAt(0) != '#' || code.charAt(4) != '#' || code.charAt(8) != '#') return false;
        for (int i : new int[]{1,2,3,5,6,7}) {
            if (!Character.isDigit(code.charAt(i))) return false;
        }
        return !code.equals(ADMIN_CODE); // Ensure different from current
    }

    // Validate new door code parts (first: 2 digits, second: 4 digits, combination different from current)
    private boolean isValidDoorCode(String newFirst, String newSecond) {
        if (newFirst.length() != 2 || newSecond.length() != 4) return false;
        if (!newFirst.matches("\\d{2}") || !newSecond.matches("\\d{4}")) return false;
        return !(newFirst.equals(FIRST_PART_CODE) && newSecond.equals(SECOND_PART_CODE));
    }

    // Validate new key (12 characters, different from current)
    private boolean isValidKey(String newKey) {
        if (newKey.length() != 12) return false;
        return !newKey.equals(CORRECT_KEY);
    }

    // Show key selection menu and handle result
    private void showKeyMenu() {
        // Create key options with current correct key
        String[] options = new String[INCORRECT_KEYS.length + 1];
        options[0] = CORRECT_KEY;
        System.arraycopy(INCORRECT_KEYS, 0, options, 1, INCORRECT_KEYS.length);

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
                options, 
                options[0]);
            
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
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonAction(text);
            }
        }); // Attach action listener
        return button;
    }

    // Handle button actions
    private void handleButtonAction(String buttonText) {
        // If dialogue is active, block other actions
        if (dialogueSystem.isInDialogue() && !buttonText.equals("Cancel")) {
            JOptionPane.showMessageDialog(null, 
                "Please finish the current dialogue before using the panel", 
                "Dialogue Active", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Stop all timers on any button press
        apartmentTimer.stop();
        doorCodeTimer.stop();
        adminCodeTimer.stop();

        // Check if the wide zero button (bottom) is pressed
        if (buttonText.equals("◯") && !expectingSecondPart && !showingConfirmMenu && !showingAdminCodeConfirmMenu && !showingDoorAccessMenu && !showingDoorCodeConfirmMenu && !showingKeyConfirmMenu) {
            // Assume wide zero button is in the fifth row
            showKeyMenu(); // Show key selection menu
            return;
        }

        if (buttonText.equals("Cancel")) {
            dialogueSystem.forceEndDialogue(); // End any active dialogue
            displayText.setLength(0); // Clear the text
            secondPartText.setLength(0); // Clear second part text
            expectingSecondPart = false; // Reset second part flag
            showingConfirmMenu = false; // Reset admin menu flag
            showingAdminCodeConfirmMenu = false; // Reset admin code confirm menu flag
            showingDoorAccessMenu = false; // Reset door access menu flag
            showingDoorCodeConfirmMenu = false; // Reset door code confirm menu flag
            showingKeyConfirmMenu = false; // Reset key confirm menu flag
            confirmMenuSelectedIndex = 0; // Reset menu selection
            displayLabel.setText(" "); // Clear the display
        } else if (buttonText.equals("Enter")) {
            if (showingDoorCodeConfirmMenu) {
                if (confirmMenuSelectedIndex == 7) { // Yes selected for door code change
                    showingDoorCodeConfirmMenu = false; // Exit door code confirm menu
                    confirmMenuSelectedIndex = 0; // Reset menu selection
                    // Prompt for new door code parts
                    String newFirstPart = JOptionPane.showInputDialog(null, 
                        "Enter new first part (2 digits):", 
                        "Set New Door Code First Part", 
                        JOptionPane.PLAIN_MESSAGE);
                    String newSecondPart = JOptionPane.showInputDialog(null, 
                        "Enter new second part (4 digits):", 
                        "Set New Door Code Second Part", 
                        JOptionPane.PLAIN_MESSAGE);
                    if (newFirstPart != null && newSecondPart != null && isValidDoorCode(newFirstPart, newSecondPart)) {
                        FIRST_PART_CODE = newFirstPart; // Update first part
                        SECOND_PART_CODE = newSecondPart; // Update second part
                        displayLabel.setText("Door Code Changed"); // Show success message
                    } else {
                        displayLabel.setText("Invalid Door Code Format or Same as Current"); // Show error message
                    }
                    displayText.setLength(0); // Clear the text
                } else { // No selected
                    displayText.setLength(0); // Clear the text
                    showingDoorCodeConfirmMenu = false; // Reset door code confirm menu flag
                    confirmMenuSelectedIndex = 0; // Reset menu selection
                    displayLabel.setText(" "); // Clear the display
                }
            } else if (showingKeyConfirmMenu) {
                if (confirmMenuSelectedIndex == 9) { // Yes selected for key change
                    showingKeyConfirmMenu = false; // Exit key confirm menu
                    confirmMenuSelectedIndex = 0; // Reset menu selection
                    // Prompt for new key
                    String newKey = JOptionPane.showInputDialog(null, 
                        "Enter new key (12 characters):", 
                        "Set New Key", 
                        JOptionPane.PLAIN_MESSAGE);
                    if (newKey != null && isValidKey(newKey)) {
                        CORRECT_KEY = newKey; // Update key
                        displayLabel.setText("Key Changed"); // Show success message
                    } else {
                        displayLabel.setText("Invalid Key Format or Same as Current"); // Show error message
                    }
                    displayText.setLength(0); // Clear the text
                } else { // No selected
                    displayText.setLength(0); // Clear the text
                    showingKeyConfirmMenu = false; // Reset key confirm menu flag
                    confirmMenuSelectedIndex = 0; // Reset menu selection
                    displayLabel.setText(" "); // Clear the display
                }
            } else if (showingAdminCodeConfirmMenu) {
                if (confirmMenuSelectedIndex == 3) { // Yes selected for admin code change
                    showingAdminCodeConfirmMenu = false; // Exit admin code confirm menu
                    confirmMenuSelectedIndex = 0; // Reset menu selection
                    // Prompt for new admin code
                    String newAdminCode = JOptionPane.showInputDialog(null, 
                        "Enter new admin code (#____#____#):", 
                        "Set New Admin Code", 
                        JOptionPane.PLAIN_MESSAGE);
                    if (newAdminCode != null && isValidAdminCode(newAdminCode)) {
                        ADMIN_CODE = newAdminCode; // Update admin code
                        displayLabel.setText("Admin Code Changed"); // Show success message
                    } else {
                        displayLabel.setText("Invalid Admin Code Format or Same as Current"); // Show error message
                    }
                    displayText.setLength(0); // Clear the text
                } else { // No selected
                    displayText.setLength(0); // Clear the text
                    showingAdminCodeConfirmMenu = false; // Reset admin code confirm menu flag
                    confirmMenuSelectedIndex = 0; // Reset menu selection
                    displayLabel.setText(" "); // Clear the display
                }
            } else if (showingDoorAccessMenu) {
                if (confirmMenuSelectedIndex == 5) { // Change door code selected
                    showingDoorAccessMenu = false; // Exit door access menu
                    confirmMenuSelectedIndex = 7; // Set to Yes in door code confirm menu
                    showDoorCodeConfirmMenu(); // Show door code confirmation menu
                } else { // Change key selected
                    showingDoorAccessMenu = false; // Exit door access menu
                    confirmMenuSelectedIndex = 9; // Set to Yes in key confirm menu
                    showKeyConfirmMenu(); // Show key confirmation menu
                }
            } else if (showingConfirmMenu) {
                if (confirmMenuSelectedIndex == 0) { // Change range selected
                    showingConfirmMenu = false; // Exit admin menu
                    confirmMenuSelectedIndex = 0; // Reset menu selection
                    // Prompt for new min and max range
                    String minInput = JOptionPane.showInputDialog(null, 
                        "Enter new min apartment number:", 
                        "Set Apartment Min Range", 
                        JOptionPane.PLAIN_MESSAGE);
                    String maxInput = JOptionPane.showInputDialog(null, 
                        "Enter new max apartment number:", 
                        "Set Apartment Max Range", 
                        JOptionPane.PLAIN_MESSAGE);
                    try {
                        int newMinRange = Integer.parseInt(minInput);
                        int newMaxRange = Integer.parseInt(maxInput);
                        if (newMinRange >= 1 && newMaxRange >= newMinRange && 
                            (newMaxRange - newMinRange) >= MIN_RANGE_DIFFERENCE) {
                            APARTMENT_MIN_RANGE = newMinRange; // Update min apartment number
                            APARTMENT_MAX_RANGE = newMaxRange; // Update max apartment number
                            displayLabel.setText("Range set to " + newMinRange + "-" + newMaxRange); // Show success message
                        } else {
                            displayLabel.setText("Invalid Range: Min >= 1, Max >= Min, Difference >= " + MIN_RANGE_DIFFERENCE); // Show error message
                        }
                    } catch (NumberFormatException e) {
                        displayLabel.setText("Invalid Range Input"); // Show error message
                    }
                    displayText.setLength(0); // Clear the text
                } else if (confirmMenuSelectedIndex == 1) { // Change admin code selected
                    showingConfirmMenu = false; // Exit admin menu
                    confirmMenuSelectedIndex = 3; // Set to Yes in admin code confirm menu
                    showAdminCodeConfirmMenu(); // Show admin code confirmation menu
                } else { // Change door access selected
                    showingConfirmMenu = false; // Exit admin menu
                    confirmMenuSelectedIndex = 5; // Set to Change door code in door access menu
                    showDoorAccessMenu(); // Show door access sub-menu
                }
            } else if (expectingSecondPart) {
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
                    showConfirmMenu(); // Show admin menu
                } else {
                    try {
                        int apartmentNumber = Integer.parseInt(input);
                        if (apartmentNumber >= APARTMENT_MIN_RANGE && apartmentNumber <= APARTMENT_MAX_RANGE) {
                            // Instead of direct call, start dialogue
                            if (!dialogueSystem.isInDialogue()) {
                                displayLabel.setText("Calling Apartment " + apartmentNumber);
                                displayText.setLength(0);
                                // Start dialogue with a small timer for realism
                                Timer callTimer = new Timer(1000, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        dialogueSystem.startDialogue(apartmentNumber);
                                    }
                                });
                                callTimer.setRepeats(false);
                                callTimer.start();
                            }
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
        } else if (buttonText.equals("↑")) {
            if (showingDoorCodeConfirmMenu) {
                confirmMenuSelectedIndex = ((confirmMenuSelectedIndex - 7 - 1 + 2) % 2) + 7; // Move between 7 and 8
                showDoorCodeConfirmMenu();
            } else if (showingKeyConfirmMenu) {
                confirmMenuSelectedIndex = ((confirmMenuSelectedIndex - 9 - 1 + 2) % 2) + 9; // Move between 9 and 10
                showKeyConfirmMenu();
            } else if (showingAdminCodeConfirmMenu) {
                confirmMenuSelectedIndex = ((confirmMenuSelectedIndex - 3 - 1 + 2) % 2) + 3; // Move between 3 and 4
                showAdminCodeConfirmMenu();
            } else if (showingDoorAccessMenu) {
                confirmMenuSelectedIndex = ((confirmMenuSelectedIndex - 5 - 1 + 2) % 2) + 5; // Move between 5 and 6
                showDoorAccessMenu();
            } else if (showingConfirmMenu) {
                confirmMenuSelectedIndex = (confirmMenuSelectedIndex - 1 + 3) % 3; // Move between 0,1,2
                showConfirmMenu();
            }
        } else if (buttonText.equals("↓")) {
            if (showingDoorCodeConfirmMenu) {
                confirmMenuSelectedIndex = ((confirmMenuSelectedIndex - 7 + 1) % 2) + 7; // Move between 7 and 8
                showDoorCodeConfirmMenu();
            } else if (showingKeyConfirmMenu) {
                confirmMenuSelectedIndex = ((confirmMenuSelectedIndex - 9 + 1) % 2) + 9; // Move between 9 and 10
                showKeyConfirmMenu();
            } else if (showingAdminCodeConfirmMenu) {
                confirmMenuSelectedIndex = ((confirmMenuSelectedIndex - 3 + 1) % 2) + 3; // Move between 3 and 4
                showAdminCodeConfirmMenu();
            } else if (showingDoorAccessMenu) {
                confirmMenuSelectedIndex = ((confirmMenuSelectedIndex - 5 + 1) % 2) + 5; // Move between 5 and 6
                showDoorAccessMenu();
            } else if (showingConfirmMenu) {
                confirmMenuSelectedIndex = (confirmMenuSelectedIndex + 1) % 3; // Move between 0,1,2
                showConfirmMenu();
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
                // Check if the full second part code is entered
                if (secondPartText.length() == 4) {
                    if (secondPartText.toString().equals(SECOND_PART_CODE)) {
                        displayLabel.setText("Door Opened"); // Show door opened message
                    } else {
                        displayLabel.setText("Access Denied"); // Show access denied message
                    }
                    displayText.setLength(0); // Clear the text
                    secondPartText.setLength(0); // Clear second part text
                    expectingSecondPart = false; // Reset second part flag
                    doorCodeTimer.stop(); // Stop the timer
                }
            }
        } else {
            // Handle regular input (first part, admin code, or apartment number)
            displayText.append(buttonText); // Append button text to display
            displayLabel.setText(displayText.toString()); // Update the display
            // Start apartment timer only if input is not the first part of door code
            if (!displayText.toString().equals(FIRST_PART_CODE)) {
                apartmentTimer.restart();
            }
            // Show admin menu if full admin code is entered
            if (displayText.toString().equals(ADMIN_CODE)) {
                displayLabel.setText("Admin Access Granted"); // Show admin access message
                displayText.setLength(0); // Clear the text
                showConfirmMenu();
            }
        }
    }
}
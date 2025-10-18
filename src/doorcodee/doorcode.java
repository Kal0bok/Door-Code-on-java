package doorcodee;

import javax.swing.*;
import java.awt.*;

public class doorcode {
    private static JLabel displayLabel; // Place for displaying numbers in the window
    private static StringBuilder displayText = new StringBuilder(); // Storage for entered numbers
    private static doorcommand logic; // Logic handler

    public static void main(String[] args) {
        // Create the window
        JFrame frame = new JFrame("DOOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(0, 5)); // Main layout with vertical spacing

        // Create top panel for header and display
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Create panel for header and stripes
        JPanel headerPanel = new JPanel(new BorderLayout(5, 0));
        
        // Add header label
        JLabel headerLabel = new JLabel("DIGITALas:", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setPreferredSize(new Dimension(100, 30)); // Fixed header size
        headerPanel.add(headerLabel, BorderLayout.WEST);

        // Create panel for three horizontal stripes
        JPanel stripesPanel = new JPanel(new GridLayout(3, 1, 0, 7)); // 3 rows, 1 column, 7px gap
        stripesPanel.setPreferredSize(new Dimension(50, 30)); // Small width, same height as header
        for (int i = 0; i < 3; i++) {
            JLabel stripe = new JLabel();
            stripe.setBackground(Color.BLACK);
            stripe.setOpaque(true);
            stripe.setPreferredSize(new Dimension(50, 5)); // Small width, 5px height
            stripesPanel.add(stripe);
        }
        headerPanel.add(stripesPanel, BorderLayout.EAST);

        // Add header panel to top panel
        topPanel.add(headerPanel, BorderLayout.NORTH);

        // Add label for displaying numbers
        displayLabel = new JLabel(" ", SwingConstants.CENTER);
        displayLabel.setFont(new Font("Arial", Font.BOLD, 16));
        displayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        displayLabel.setPreferredSize(new Dimension(340, 60)); // Size for 4 columns
        topPanel.add(displayLabel, BorderLayout.CENTER);

        // Add top panel to the window
        frame.add(topPanel, BorderLayout.NORTH);

        // Initialize logic handler after creating displayLabel
        logic = new doorcommand(displayLabel, displayText);

        // Create panel for buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5); // Button spacing
        gbc.weighty = 1.0;

        // Button sizes
        Dimension buttonSize = new Dimension(80, 80); // Size for columns 0-2
        Dimension wideButtonSize = new Dimension(170, 80); // Size for wide button

        // Buttons with specified positions
        // First column: 7, 4, 1, *
        gbc.weightx = 0.75; // Slightly more weight for first column
        gbc.gridx = 0; gbc.gridy = 0; buttonPanel.add(logic.createButton("7", buttonSize), gbc);
        gbc.gridx = 0; gbc.gridy = 1; buttonPanel.add(logic.createButton("4", buttonSize), gbc);
        gbc.gridx = 0; gbc.gridy = 2; buttonPanel.add(logic.createButton("1", buttonSize), gbc);
        gbc.gridx = 0; gbc.gridy = 3; buttonPanel.add(logic.createButton("*", buttonSize), gbc);

        // Second column: 8, 5, 2, 0
        gbc.weightx = 1.0; // Normal weight for second column
        gbc.gridx = 1; gbc.gridy = 0; buttonPanel.add(logic.createButton("8", buttonSize), gbc);
        gbc.gridx = 1; gbc.gridy = 1; buttonPanel.add(logic.createButton("5", buttonSize), gbc);
        gbc.gridx = 1; gbc.gridy = 2; buttonPanel.add(logic.createButton("2", buttonSize), gbc);
        gbc.gridx = 1; gbc.gridy = 3; buttonPanel.add(logic.createButton("0", buttonSize), gbc);

        // Third column: 9, 6, 3, #
        gbc.weightx = 1.0; // Normal weight for third column
        gbc.gridx = 2; gbc.gridy = 0; buttonPanel.add(logic.createButton("9", buttonSize), gbc);
        gbc.gridx = 2; gbc.gridy = 1; buttonPanel.add(logic.createButton("6", buttonSize), gbc);
        gbc.gridx = 2; gbc.gridy = 2; buttonPanel.add(logic.createButton("3", buttonSize), gbc);
        gbc.gridx = 2; gbc.gridy = 3; buttonPanel.add(logic.createButton("#", buttonSize), gbc);

        // Fourth column: ↑, ↓, Enter, Cancel
        gbc.weightx = 2.0; // Wider column
        gbc.gridx = 3; gbc.gridy = 0; buttonPanel.add(logic.createButton("↑", buttonSize), gbc);
        gbc.gridx = 3; gbc.gridy = 1; buttonPanel.add(logic.createButton("↓", buttonSize), gbc);
        gbc.gridx = 3; gbc.gridy = 2; buttonPanel.add(logic.createButton("Enter", buttonSize), gbc);
        gbc.gridx = 3; gbc.gridy = 3; buttonPanel.add(logic.createButton("Cancel", buttonSize), gbc);

        // New button in the fifth row, between second and third columns
        gbc.weightx = 1.0; // Reset weight for new button
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2; // Spans columns 1 and 2
        buttonPanel.add(logic.createButton("0", wideButtonSize), gbc); // Wide button

        // Add button panel to the window
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Window settings
        frame.setSize(400, 540); // Height unchanged
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true); // Show the window
    }
}
package doorcodee;

import javax.swing.*;
import java.awt.*;

public class doorcode {
    private static JLabel displayLabel; // place to show numbers in the window
    private static StringBuilder displayText = new StringBuilder(); // save pressed numbers
    private static doorcommand logic; // Logic handler
    
    public static void main(String[] args) {
    	
    	// Initialize logic handler
        logic = new doorcommand(displayLabel, displayText);
        
        // make window
        JFrame frame = new JFrame("DOOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(0, 5)); // main layout with vertical gap

        // create top panel for header and display
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // create header panel for title and stripes
        JPanel headerPanel = new JPanel(new BorderLayout(5, 0));
        
        // add header label (title)
        JLabel headerLabel = new JLabel("DIGITALas:", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setPreferredSize(new Dimension(100, 30)); // fixed size for header
        headerPanel.add(headerLabel, BorderLayout.WEST);

        // create panel for three horizontal stripes
        JPanel stripesPanel = new JPanel(new GridLayout(3, 1, 0, 7)); // 3 rows, 1 column, 2px vertical gap
        stripesPanel.setPreferredSize(new Dimension(50, 30)); // small width, height matches header
        for (int i = 0; i < 3; i++) {
            JLabel stripe = new JLabel();
            stripe.setBackground(Color.BLACK);
            stripe.setOpaque(true);
            stripe.setPreferredSize(new Dimension(50, 5)); // small width, 5px height
            stripesPanel.add(stripe);
        }
        headerPanel.add(stripesPanel, BorderLayout.EAST);

        // add header panel to top panel
        topPanel.add(headerPanel, BorderLayout.NORTH);

        // add text place to show numbers
        displayLabel = new JLabel(" ", SwingConstants.CENTER);
        displayLabel.setFont(new Font("Arial", Font.BOLD, 16));
        displayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        displayLabel.setPreferredSize(new Dimension(340, 60)); // adjusted for 4 columns
        topPanel.add(displayLabel, BorderLayout.CENTER);

        // add top panel to frame
        frame.add(topPanel, BorderLayout.NORTH);

        // create panel for buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5); // spaces for buttons
        gbc.weighty = 1.0;

        // button sizes
        Dimension buttonSize = new Dimension(80, 80); // size for columns 0-2
        Dimension wideButtonSize = new Dimension(170, 80); // size for wide button

     // Buttons with custom placement
        // First column: 7, 4, 1, *
        gbc.weightx = 0.75; // Slightly increased weight for first column
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

        // new button in fifth row, between second and third columns
        gbc.weightx = 1.0; // reset weight for new button
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2; // spans columns 1 and 2
        buttonPanel.add(createButton("0", wideButtonSize), gbc); // wider button

        // add button panel to frame
        frame.add(buttonPanel, BorderLayout.CENTER);

        // window settings
        frame.setSize(400, 540); // height unchanged
        frame.setLocationRelativeTo(null); // center
        frame.setVisible(true); // show window
    
    }
}
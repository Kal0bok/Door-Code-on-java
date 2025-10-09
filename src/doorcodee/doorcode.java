package doorcodee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class doorcode {
    private static JLabel displayLabel; // place to show numbers in the window
    private static StringBuilder displayText = new StringBuilder(); // save pressed numbers

    public static void main(String[] args) {
        // make window
        JFrame frame = new JFrame("DOOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5); // spaces for buttons
        gbc.weighty = 1.0;

        // add header label (title)
        JLabel headerLabel = new JLabel("DIGITALas:", SwingConstants.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.weightx = 1.0;
        frame.add(headerLabel, gbc);

        // add text place to show numbers
        displayLabel = new JLabel(" ", SwingConstants.CENTER);
        displayLabel.setFont(new Font("Arial", Font.BOLD, 16));
        displayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        displayLabel.setPreferredSize(new Dimension(320, 60)); // size for 4 columns
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 4; // take 4 columns
        gbc.weightx = 1.0;
        frame.add(displayLabel, gbc);

        // button size
        Dimension buttonSize = new Dimension(80, 80); // size for all buttons
        Dimension wideButtonSize = new Dimension(170, 80); // size for wide button

        // reset gridwidth for buttons
        gbc.gridwidth = 1;

        // buttons with custom place
        // first column: 7, 4, 1, *
        gbc.weightx = 1.0; // weight for columns 0-2
        gbc.gridx = 0; gbc.gridy = 2; frame.add(createButton("7", buttonSize), gbc);
        gbc.gridx = 0; gbc.gridy = 3; frame.add(createButton("4", buttonSize), gbc);
        gbc.gridx = 0; gbc.gridy = 4; frame.add(createButton("1", buttonSize), gbc);
        gbc.gridx = 0; gbc.gridy = 5; frame.add(createButton("*", buttonSize), gbc);

        // second column: 8, 5, 2, 0
        gbc.gridx = 1; gbc.gridy = 2; frame.add(createButton("8", buttonSize), gbc);
        gbc.gridx = 1; gbc.gridy = 3; frame.add(createButton("5", buttonSize), gbc);
        gbc.gridx = 1; gbc.gridy = 4; frame.add(createButton("2", buttonSize), gbc);
        gbc.gridx = 1; gbc.gridy = 5; frame.add(createButton("0", buttonSize), gbc);

        // third column: 9, 6, 3, #
        gbc.gridx = 2; gbc.gridy = 2; frame.add(createButton("9", buttonSize), gbc);
        gbc.gridx = 2; gbc.gridy = 3; frame.add(createButton("6", buttonSize), gbc);
        gbc.gridx = 2; gbc.gridy = 4; frame.add(createButton("3", buttonSize), gbc);
        gbc.gridx = 2; gbc.gridy = 5; frame.add(createButton("#", buttonSize), gbc);

        // fourth column: ↑, ↓, Enter, Cancel
        gbc.weightx = 2.0; // wider column
        gbc.gridx = 3; gbc.gridy = 2; frame.add(createButton("↑", buttonSize), gbc);
        gbc.gridx = 3; gbc.gridy = 3; frame.add(createButton("↓", buttonSize), gbc);
        gbc.gridx = 3; gbc.gridy = 4; frame.add(createButton("Enter", buttonSize), gbc);
        gbc.gridx = 3; gbc.gridy = 5; frame.add(createButton("Cancel", buttonSize), gbc);

        // new button in sixth row, between second and third columns
        gbc.weightx = 1.0; // reset weight for new button
        gbc.gridx = 1; gbc.gridy = 6; gbc.gridwidth = 2; // spans columns 1 and 2
        frame.add(createButton("0", wideButtonSize), gbc); // wider button

        // window settings
        frame.setSize(380, 540); // increased height for new row
        frame.setLocationRelativeTo(null); // center
        frame.setVisible(true); // show window
    }

    // create a button
    private static JButton createButton(String number, Dimension size) {
        JButton button = new JButton(number);
        button.setPreferredSize(size); // set size
        if (number.equals("Cancel")) {
            button.addActionListener(e -> {
                displayText.setLength(0); // clean text
                displayLabel.setText(" "); // clean place
            }); // clear if press Cancel
        } else {
            button.addActionListener(e -> {
                displayText.append(number); // add number or symbol
                displayLabel.setText(displayText.toString()); // refresh place for numbers
            }); // show number or symbol in window
        }
        return button;
    }
}
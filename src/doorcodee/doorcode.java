package doorcodee;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

	//input field validation
public class doorcode {
	static String virkParb(String zinojums, String noklusejums) {
        String virkne;
        do {
            virkne = JOptionPane.showInputDialog(zinojums, noklusejums);
            if (virkne == null) return null;
        } while (!virkne.matches("[a-zA-Z ]+"));
        return virkne;
    }
		//dialog with buttons
	private static String showGridChoice(String message, String title, String[] options) {
        final String[] selected = {null};
        
        JDialog dialog = new JDialog((Frame)null, title, true);
        
      //tittle 
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.add(new JScrollPane(textArea), BorderLayout.NORTH);
        
      //panel with buttons
        Jpanel buttonPanel = new JPanel(new GridLayout(3, 3, 7, 7));
        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                selected[0] = option;
                dialog.dispose();  
            });
            buttonPanel.add(button);
            
            //button cancel
            JButton cancelButton = new JButton("Atcelt");
            cancelButton.addActionListener(e -> {
                selected[0] = null;
                dialog.dispose();
            });
            buttonPanel.add(cancelButton);

            dialog.add(buttonPanel, BorderLayout.CENTER);
        }
        
	}
	
	
	
	
	
	public static void main(String[] args) {

	}

}

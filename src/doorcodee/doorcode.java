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
	private static String showGridChoice(String message, String title) {
        final String[] selected = {null};
        
        //make modal dialog
        JDialog dialog = new JDialog((Frame)null, title, true);
        dialog.setLayout(new BorderLayout());
        
      //tittle 
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.add(new JScrollPane(textArea), BorderLayout.NORTH);
        
      //panel with buttons 4x3
        JPanel buttonPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        String[] numbers = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "", "Cancel",};
        for(String number : numbers) {
        	JButton button = new JButton(number);
        	if(number.isEmpty()) {
        		button.setEnabled(false);//empty cell no active
        	}else if (number.equals("Cancel")) {
        		button.addActionListener(e ->{
        			selected[0] = null;// if press button cancel return null
        			dialog.dispose();
        		});
        	
        		}else {
        			button.addActionListener(e ->{
        				selected[0] = number;//save choosen number
        				dialog.dispose();
        			});
        	}
        	buttonPanel.add(button);
        }
        dialog.add(buttonPanel, BorderLayout.CENTER);
        
        dialog.setSize(300, 400);//small size for door-code
        dialog.setLocationRelativeTo(null); //make center on screen
        dialog.setVisible(true);//show dialog
        
        return selected[0];//return choosen number or null
	}
	
	public static void main(String[] args) {

	}

}

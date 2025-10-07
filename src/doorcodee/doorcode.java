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
        
      
        
	}
	
	public static void main(String[] args) {

	}

}

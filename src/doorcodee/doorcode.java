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
	
	
	
	
	
	
	
	public static void main(String[] args) {

	}

}

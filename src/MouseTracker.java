
// This program displays the current location of the mouse 

import acm.graphics.*;
import acm.program.*;

import java.awt.event.*;

public class MouseTracker extends GraphicsProgram {
	public void run() {
		resize(800, 600);
		
		label = new GLabel("");
		label.setFont("Times New Roman-36");
		add(label, 50, 50);
		
		addMouseListeners();
	}
	
	public void mouseMoved(MouseEvent e) {
		label.setLabel("Mouse : (" + e.getX() + ", " + e.getY() + ")");
	}
	
	
	//the label is to be tracked between method calls, hence it is a private instance variable
	private GLabel label;

}

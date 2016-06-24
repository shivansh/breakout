// This program puts a circle wherever you click on the screen

import acm.graphics.*;
import acm.program.*;

import java.awt.Color;
import java.awt.event.*;

public class ClickForFace extends GraphicsProgram {

  private static final double FACE_DIAM = 30;

  public void init() {
    addMouseListeners();
    resize(800, 600);
  }


  public void mouseClicked(MouseEvent e) {
    GOval face = new GOval(30, 30);

    //setFilled() command is necessary to display colour

    face.setFilled(true);
    face.setFillColor(Color.green);
    add(face, e.getX(), e.getY());
  }

}

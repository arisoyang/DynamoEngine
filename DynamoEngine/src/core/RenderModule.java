package core;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
public class RenderModule extends JFrame{

private Screen sc;

public RenderModule(){
	 sc = new Screen();
	 add(sc);
     setTitle("GameScreen");
     setDefaultCloseOperation(EXIT_ON_CLOSE);
     setSize(500, 500);
     setLocationRelativeTo(null);
     setVisible(true);
     setResizable(false);
	
}

public void update(int a){
	sc.getState(a);
	sc.repaint();
}


}

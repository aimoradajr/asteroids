package asteroids;

import java.awt.Color;
import java.awt.color.ColorSpace;

import javax.swing.JFrame;

import asteroids.Field;

public class Asteroids extends JFrame {

	public static final long serialVersionUID = 1L;
	
	public Asteroids() {
		add(new Field());
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Field.WIDTH, Field.HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Asteroids");
        setResizable(false);
        setVisible(true);
        setBackground(Color.RED);
	}

	public static void main(String[] args) {
		new Asteroids();
	}

}

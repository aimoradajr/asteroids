package asteroids;

import java.awt.Graphics2D;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class AsteroidSprite extends Sprite {
	
	private static String[] asteroid_image =
		{ "asteroid_small.png", "asteroid_med.png", "asteroid_large.png" };
	public int size;
	private static int[] asteroid_points =
		{ 30, 20, 10 };
	
	private static Random generator;
	public int type;
	private static int images_available=10;
	
	private void setStartPosition() {		
		move.setLength(0.5);
		
		if( generator == null) {
			generator = new Random();
		}
		
		int wall = generator.nextInt(4);
		int steps = 0;
		switch(wall) {
			case 0: // From top
				x = generator.nextInt(Field.WIDTH);
				y = -height;
				steps = 5;
				setDelta(steps);
				break;
			case 1: // From right
				x = Field.WIDTH;
				y = generator.nextInt(Field.HEIGHT);
				steps = 9;
				setDelta(steps);
				break;
			case 2: // From botton
				x = generator.nextInt(Field.WIDTH);
				y = Field.HEIGHT;
				steps = 12;
				setDelta(steps);
				break;
			case 3: // From left
				x = 0;
				y = generator.nextInt(Field.HEIGHT);
				steps = 1;
				break;
		}
		steps += generator.nextInt(6);
		setDelta(steps);
	}
	
	public void paint(Graphics2D g2d){
    	g2d.drawImage(getImage(), getX(), getY(), width, height, null);	
	}
	
	public int getPoints() {
		return asteroid_points[size];
	}
	
	private void setRandomDirection() {
		int orientation = generator.nextInt(16);
		setDelta(orientation);
	}
	
	public AsteroidSprite(double x, double y, int size, int type) {
		super(x, y, "res/theme1/image/asteroid/"+size+"/"+type+asteroid_image[size]);
		
		setAsteroidSize(size);
	}
	
	private void setAsteroidSize(int s) {
		//'size' values : 0 , 1 , 2
		this.size = s;
		//derive actual size 
        setSize( (s+1)*12, (s+1)*12);
	}

	public AsteroidSprite(double x, double y, int size) {
		// Use default image
		super(x, y, "res/theme1/image/asteroid/"+size+"/0"+asteroid_image[size]);
		this.size = size;
	}
	
	public static AsteroidSprite generateLarge(double x, double y) {
		generator = new Random();
		AsteroidSprite large = new AsteroidSprite(x, y, 2, generator.nextInt(images_available));
		large.setStartPosition();
		return large;
	}
	
	public static AsteroidSprite spawnFragment(double x, double y, int size) {
		AsteroidSprite a = new AsteroidSprite(x, y, size, generator.nextInt(images_available));
		a.setRandomDirection();
		return a;
	}

	public ArrayList<AsteroidSprite> spawnFragments() {
		if (size == 0) // Smaller fragments is not possible
			return null;
		
		ArrayList<AsteroidSprite> fragments = new ArrayList<AsteroidSprite>();

		for(int i = 3; i > 0; i--) {
			fragments.add( spawnFragment(x, y, size-1) );
		}

		return fragments;
	}
}


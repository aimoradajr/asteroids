package asteroids;

import java.awt.Graphics2D;

import javax.swing.Timer;

public abstract class BulletSprite extends Sprite {
	protected GunSprite gun;
	protected String name = "Unknown";
	
	public BulletSprite( GunSprite gun, double x, double y, int width, int height, int orientation) {
		super(x, y, "res/theme1/image/bullet/bullet.png");
		move.setLength(4);
		setDelta(orientation);
		this.gun = gun;
		setSize(width,height);
		updatePosition();						//bullets automatically adjust to the the center of the gun
		this.gun = gun;
	}

	private void updatePosition() {
		x = x+(gun.width/2)-(width/2);			//adjustments to position relative to gun size and bullet size
		y = y+(gun.height/2)-(height/2);
	
	}

	public void move() {
		x += - move.getX();
		y += move.getY();
		
		if ( x < 0 || x > Field.WIDTH || y < 0 || y > Field.HEIGHT ) {
			setVisible(false);
		}
	}
	
	public void paint(Graphics2D g2d){
    	g2d.drawImage( getImage(), getX(), getY(), width, height, null);	
	}
}

package asteroids;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;


public class ExplosionSprite extends Sprite {
	public ExplosionSprite(double x, double y) {
		super(x, y, "image/other/explosion.png");
		setSize( 100, 100 );
	}
	
	public void paint(Graphics2D g2d){
    	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  Float.parseFloat( Double.toString (getOpacity() ) ) ) );
    	g2d.drawImage(getImage(), getX()-(width/2) , getY()-(height/2), width, height , null);
    	fade();
	}
}

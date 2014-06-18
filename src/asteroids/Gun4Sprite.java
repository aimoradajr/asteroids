package asteroids;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.Timer;

public class Gun4Sprite extends GunSprite {

	public Gun4Sprite(ShipSprite ship, int x, int y) {
		super(ship, x, y);
		name = "burst fire";
		
		setCoolDown(1000);
	}
	
	public void fire(){
		final GunSprite tempg = this;
		
		if(isCoolingDown) return;
		
		if(!isFiring) return;
		
		new Thread(new Runnable() {
	        public void run(){
	    		for(int i=0; i<5; i++){

		        	bullets.add( new Bullet1Sprite( tempg, x, y, ship.orientation ) );
		    		
		    		try {
		    		    Thread.sleep(100);
		    		} catch(InterruptedException ex) {
		    		    Thread.currentThread().interrupt();
		    		}
	    		}
	    		
	        }
	    }).start();
		
		isCoolingDown = true;
		cooldownTimer.restart();
	}
}

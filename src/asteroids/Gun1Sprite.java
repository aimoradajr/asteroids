package asteroids;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Gun1Sprite extends GunSprite {

	public Gun1Sprite(ShipSprite ship, int x, int y) {
		super(ship, x, y);
		name = "gun 1";
		
		setCoolDown(200);
	}

	public void fire(){
		
		if(isCoolingDown) return;
		
		if(!isFiring) return;
		
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation ) );
		
		isCoolingDown = true;
		cooldownTimer.restart();
	}
}

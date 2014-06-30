package asteroids;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Gun2Sprite extends GunSprite {

	public Gun2Sprite(ShipSprite ship, int x, int y) {
		super(ship, x, y,"image/bullet/bullet.png","image/bullet/bullet.png");
		name = "gun 2";

		setCoolDown(200);
	}
	
	public void fire(){
		if(isCoolingDown) return;
		
		if(!isFiring) return;

		bullets.add( new Bullet2Sprite( this, x, y, ship.orientation ) );
		
		isCoolingDown = true;
		cooldownTimer.restart();
	}
}

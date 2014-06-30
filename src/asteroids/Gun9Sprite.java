package asteroids;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Gun9Sprite extends GunSprite {

	public Gun9Sprite(ShipSprite ship, int x, int y) {
		super(ship, x, y,"image/bullet/bullet.png","image/bullet/bullet.png");
		name = "fireworks";
		
		setCoolDown(200);
	}

	public void fire(){
		
		if(isCoolingDown) return;
		
		if(!isFiring) return;
		
		bullets.add( new Bullet4Sprite( this, x, y, ship.orientation ) );
		
		isCoolingDown = true;
		cooldownTimer.restart();
	}
}

package asteroids;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Gun5Sprite extends GunSprite {

	public Gun5Sprite(ShipSprite ship, int x, int y) {
		super(ship, x, y,"image/bullet/bullet.png","image/bullet/bullet.png");
		name = "machine gun";
		
		setCoolDown(50);
	}

	public void fire(){
		
		if(isCoolingDown) return;
		
		if(!isFiring) return;
		
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation ) );
		
		isCoolingDown = true;
		cooldownTimer.restart();
	}
}

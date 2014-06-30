package asteroids;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Gun8Sprite extends GunSprite {

	public Gun8Sprite(ShipSprite ship, int x, int y) {
		super(ship, x, y,"image/bullet/bullet.png","image/bullet/bullet.png");
		name = "oOo";
		
		setCoolDown(100);
	}

	public void fire(){
		
		if(isCoolingDown) return;
		
		if(!isFiring) return;

		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation-1 ) );
		bullets.add( new Bullet2Sprite( this, x, y, ship.orientation ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+1 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+7 ) );
		bullets.add( new Bullet2Sprite( this, x, y, ship.orientation+8 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+9 ) );
		
		isCoolingDown = true;
		cooldownTimer.restart();
	}
}

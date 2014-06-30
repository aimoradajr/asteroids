package asteroids;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Gun6Sprite extends GunSprite {

	public Gun6Sprite(ShipSprite ship, int x, int y) {
		super(ship, x, y,"image/bullet/bullet.png","image/bullet/bullet.png");
		name = "shot gun";
		
		setCoolDown(100);
	}

	public void fire(){
		
		if(isCoolingDown) return;
		
		if(!isFiring) return;
		
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation-1 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+1 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation-2 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+2 ) );
		
		isCoolingDown = true;
		cooldownTimer.restart();
	}
}

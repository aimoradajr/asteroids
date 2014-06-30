package asteroids;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Gun7Sprite extends GunSprite {

	public Gun7Sprite(ShipSprite ship, int x, int y) {
		super(ship, x, y,"image/bullet/bullet.png","image/bullet/bullet.png");
		name = "360 shot";
		
		setCoolDown(100);
	}

	public void fire(){
		
		if(isCoolingDown) return;
		
		if(!isFiring) return;
		
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+1 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+2 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+3 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+4 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+5 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+6 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+7 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+8 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+9 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+10 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+11 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+12 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+13 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+14 ) );
		bullets.add( new Bullet1Sprite( this, x, y, ship.orientation+15 ) );
		
		isCoolingDown = true;
		cooldownTimer.restart();
	}
}

package asteroids;

public class Bullet3Sprite extends BulletSprite{

	public Bullet3Sprite(GunSprite gun, double x, double y, int orientation) {
		super(gun, x, y, 30, 30,orientation);
		name = "basic large";
	}

}

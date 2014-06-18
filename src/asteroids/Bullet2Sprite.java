package asteroids;

public class Bullet2Sprite extends BulletSprite{

	public Bullet2Sprite(GunSprite gun, double x, double y, int orientation) {
		super(gun, x, y, 20, 20,orientation);
		name = "basic medium";
	}

}

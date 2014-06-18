package asteroids;

public class Bullet1Sprite extends BulletSprite{

	public Bullet1Sprite(GunSprite gun, double x, double y, int orientation) {
		super(gun, x, y, 8, 8,orientation);
		name = "basic small";
	}

}

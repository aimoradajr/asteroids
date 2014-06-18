package asteroids;

public class AlienSprite extends Sprite {

	public AlienSprite() {
		super(640, 60, "image/aliens/alien-1.png");
		move.setLength(2);
		setDelta(12);
	}
	
}

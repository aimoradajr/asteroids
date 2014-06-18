package asteroids;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Bullet4Sprite extends BulletSprite implements ActionListener{
	private Timer t;
	private int countdown_to_explosion;
	public Bullet4Sprite(GunSprite gun, double x, double y, int orientation) {
		super(gun, x, y, 20, 20,orientation);
		name = "exploding bullet";
		
		countdown_to_explosion = 10;
		
		t = new Timer(100,this);
		t.start();
	}

    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == t){
    		countdown_to_explosion--;
    		if(countdown_to_explosion<=0){
    			t.stop();
    			explode();
    		}
    	}
    }

	private void explode() {
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 0 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 1 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 2 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 3 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 4 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 5 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 6 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 7 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 8 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 9 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 10 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 11 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 12 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 13 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 14 ) );
		gun.bullets.add( new Bullet1Sprite( gun, x, y, 15 ) );
		
		gun.bullets.remove(gun.bullets.indexOf(this));
	}
    
}

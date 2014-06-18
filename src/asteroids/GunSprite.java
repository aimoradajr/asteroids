package asteroids;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

public abstract class GunSprite extends Sprite implements ActionListener{
	protected ArrayList<BulletSprite> bullets;
	public String name = "Unknown";

	protected int cooldownTime = 200; 
	protected boolean isCoolingDown = false;
	protected boolean isFiring = false;
	protected Timer cooldownTimer;
	
	// Cos/Sin tables with 16 pre-generated values aligned to orientation value
	protected double CosTab[] = {0, -0.76, -1.41, -1.85, -2, -1.84, -1.41, -0.76,
								  0, 0.76, 1.41, 1.85, 2, 1.85, 1.41, 0.76 };
	protected double SinTab[] = { 2, 1.85, 1.41, 0.76, 0, -0.76, -1.41, -1.85,
									-2, -1.84, -1.41, -0.76, 0, 0.76, 1.41, 1.85 };
	
	protected ShipSprite ship;
	
	public GunSprite(ShipSprite ship, int x, int y){
		super(x, y, "image/bullet/bullet.png");
		bullets = new ArrayList<BulletSprite>();
		setSize(10,10);
		this. ship = ship;
        
        //gun cooldown timer
        cooldownTimer = new Timer(cooldownTime, this);
        cooldownTimer.start();
	}
	
	public ArrayList<BulletSprite> getBullets() {
		return bullets;
	}
	
	public void setCoolDown(int delay){
		cooldownTime = delay;
		cooldownTimer = new Timer(cooldownTime,this);
		cooldownTimer.restart();
	}
	
	//override this
	public void fire(){
		if(isCoolingDown) return;
		
		if(!isFiring) return;
		
		bullets.add(new Bullet1Sprite(this,x,y,ship.orientation));
		
		isCoolingDown = true;
		cooldownTimer.restart();
	}
	
	public void paint(Graphics2D g2d){
		updatePosition();
		g2d.drawImage( getImage(), (int)x , (int)y, width, height, null );
	}

	private void updatePosition() {
		x = ship.x+(ship.width/2)-(width/2);			//adjustments to position relative to ship size and gun size
		y = ship.y+(ship.height/2)-(height/2);
	}

	private void enableGun() {
		isFiring = true;
	}
	
	private void disableGun() {
		isFiring = false;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cooldownTimer){
			isCoolingDown = false;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if( key == KeyEvent.VK_SPACE)
			disableGun();
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if( key == KeyEvent.VK_SPACE)
			enableGun();
		
	}
}

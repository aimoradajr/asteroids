package asteroids;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
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
	
	public GunSprite(ShipSprite ship, int x, int y, String imageFile, String thumbnailFile){
		super(x, y, imageFile,thumbnailFile);
		bullets = new ArrayList<BulletSprite>();
		setSize(10,10);
		setThumbnailSize(20,20);
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
	
	public void paintThumbnail(Graphics2D g2d, int gunNumber){
		g2d.drawImage( getThumbnailImage(), ( (getThumbnailWidth()+5)*gunNumber )+5, Field.HEIGHT-( getThumbnailHeight()+50 ), getThumbnailWidth(), getThumbnailHeight(), null );

		g2d.setColor(Color.DARK_GRAY);
		g2d.setStroke(new BasicStroke(1));
        g2d.setFont(new Font("Verdana", Font.BOLD, 15));
		
		if(ship.selectedGun==gunNumber){
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.drawString(name,((getThumbnailWidth()+5)*gunNumber )+5, Field.HEIGHT-(getThumbnailHeight()+60));
		}
		g2d.drawRect(( (getThumbnailWidth()+5)*gunNumber )+5, Field.HEIGHT-( getThumbnailHeight()+50 ), getThumbnailWidth(), getThumbnailHeight());

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

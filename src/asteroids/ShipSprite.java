package asteroids;

import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class ShipSprite extends Sprite implements ActionListener{
	
	public int orientation;
	public boolean isAlive;
	private int bulletsFired=0;
	private final int MAX_SHIP_INDEX = 16; // Steps of 22.5 degrees.
	//private final int BULLET_SIZE = 8;
	
	private boolean isRotatingClockwise=false;
	private boolean isRotatingCounterClockwise=false;
	private Timer rotationCooldownTimer;
	private int rotation_cooldown_time = 1; //*100 milisecconds
	private boolean isRotationCoolingDown = false;
	
	private boolean isMovingForward = false;
	private int boost_cooldown_time = 3; //*100 milisecconds
	private boolean isBoostCoolingDown = false;
	private Timer boostCooldownTimer;
	
	private LinkedList<GunSprite> guns;
	public int selectedGun = 0;
	
	private int spawnPointX,spawnPointY;
	
	private static String[] ship_names =
		{ "ship_180.png", "ship_202.png", "ship_225.png", "ship_247.png",
		  "ship_270.png", "ship_292.png", "ship_315.png", "ship_337.png",
		  "ship_000.png", "ship_022.png", "ship_045.png", "ship_067.png",
		  "ship_090.png", "ship_112.png", "ship_135.png", "ship_157.png"
		};	
	
	// Cos/Sin tables with 16 pre-generated values aligned to orientation value
	private double CosTab[] = {0, -0.76, -1.41, -1.85, -2, -1.84, -1.41, -0.76,
								  0, 0.76, 1.41, 1.85, 2, 1.85, 1.41, 0.76 };
	private double SinTab[] = { 2, 1.85, 1.41, 0.76, 0, -0.76, -1.41, -1.85,
									-2, -1.84, -1.41, -0.76, 0, 0.76, 1.41, 1.85 };
	
	private Image[] ship_images;
	
	//private ArrayList<BulletSprite> bullets;  <- added to Gun object
	
	public ShipSprite(int x, int y) {
		ImageIcon icon;
		
		ship_images = new Image[MAX_SHIP_INDEX];
		for ( int i = 0; i < MAX_SHIP_INDEX; i++ ) {
            icon = new ImageIcon( this.getClass().getResource( "/res/theme1/image/ship/"+ship_names[i] ) );
            ship_images[i] = icon.getImage();
		}

		//bullets = new ArrayList<BulletSprite>();
        
        isAlive = true;
        
        //rotation cooldowntimer
        rotationCooldownTimer = new Timer(rotation_cooldown_time*100, this);
        rotationCooldownTimer.start();
        
        //boost cooldaown timer
        boostCooldownTimer = new Timer(rotation_cooldown_time*100, this);
        boostCooldownTimer.start();
        
        setSize( 32, 32);
        setSpawnPoint(x,y);
        
        //gun
        guns = new LinkedList<GunSprite>();
        addGun(new Gun1Sprite(this,(int)x+(width/2),(int)y+(height/2)));	//position gun in center of ship
        addGun(new Gun2Sprite(this,(int)x+(width/2),(int)y+(height/2)));
        addGun(new Gun3Sprite(this,(int)x+(width/2),(int)y+(height/2)));
        addGun(new Gun4Sprite(this,(int)x+(width/2),(int)y+(height/2)));
        addGun(new Gun5Sprite(this,(int)x+(width/2),(int)y+(height/2)));
        addGun(new Gun6Sprite(this,(int)x+(width/2),(int)y+(height/2)));
        addGun(new Gun7Sprite(this,(int)x+(width/2),(int)y+(height/2)));
        addGun(new Gun8Sprite(this,(int)x+(width/2),(int)y+(height/2)));
        addGun(new Gun9Sprite(this,(int)x+(width/2),(int)y+(height/2)));
        
        spawn();
        
        move.print();
        orientation = 8;
        setDelta(orientation);
        move.print();
	}
	
	public void addGun(GunSprite gun) {
        guns.add(gun);
        
	}

	public GunSprite getGun(){
		if(selectedGun<guns.size())
		{
			return (GunSprite) guns.get(selectedGun);
		}
		return null;
	}
	
	public GunSprite getGun(int i){
		if(i < guns.size() && i >= 0)
		{
			return (GunSprite) guns.get(i);
		}
		else {
			System.out.println("Gun not available.");
		}
		return null;
	}
	
	public int getGunsCount(){
		return guns.size();
	}
	
	public void setSpawnPoint(int x, int y) {
        spawnPointX = x;
        spawnPointY = y;
	}
	
	public void spawn(){
		x = spawnPointX;
		y = spawnPointY;
	}
	
	public void reSpawn(){
		x = spawnPointX;
		y = spawnPointY;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getBulletsFired()
	{
		return bulletsFired;
	}
	
	public void die(){
		isAlive = false;
	}
	
//	public ArrayList<BulletSprite> getBullets() {
//		return bullets;
//	}
	
	public Image getImage() {
		return ship_images[orientation];
	}
	
	public void fire() {
		if(!isAlive) return;
		
		//fire!!
		if(getGun()!=null)
			getGun().fire();
		
		bulletsFired ++;
	}
	
//	NOTE: FAILED ATTEMPT TO USE ROTATE INSTEAD OF HAVING MULTIPLE IMAGES FOR EACH ORIENTATION, problem with ship bounds and bullets
//	public void paint(Graphics2D g2d ){
//		Image ship_img = getImage();
//		
//		g2d.translate(getX(), getY());
//		g2d.rotate(Math.toRadians((orientation-8)*22));
//		
//    	g2d.drawImage( ship_img, ship_img.getWidth(null)/-2, ship_img.getHeight(null)/-2,null );
//    	g2d.rotate(Math.toRadians((orientation-8)*-22));
//    	g2d.translate(getX()*-1, getY()*-1);
//		
//	}
	
	public void paint(Graphics2D g2d ){
		g2d.drawImage( getImage(), (int)x , (int)y, width, height, null );
		
		//paint gun
		if(guns.size()>0)
			guns.get(selectedGun).paint(g2d);
	}
	
	public int getOrientation(){
		return orientation;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x + 2, (int) y + 2, width - 4 , height - 4);
	}
	
	public void move() {
		if(!isAlive) return;
		super.move();
		if(move.getLength() > 0) {
			move.setLength( move.getLength() - 0.02 );
		}
		
		fire();
		
		if(isRotatingClockwise)
			rotateClockwise();
		else if(isRotatingCounterClockwise)
			rotateCounterClockWise();
		if(isMovingForward)
			goForward();
	}
	
	public void boostSpeed(int orientation, double speed) {
		if(!isAlive) return;
		if (move.getLength() < 7) {
			double rad = orientation * Math.PI / 8;	
			move.addPolar( rad, speed );
		}
		System.out.println("Boosted with " + speed + " at " + orientation + " steps (amount " + move.getLength() + ")");
	}
	
	public void keyPressed(KeyEvent e) {
		getGun().keyPressed(e);
		
		if(!isAlive) return;
		int key = e.getKeyCode();
		
		if( key == KeyEvent.VK_RIGHT )
			isRotatingClockwise = true;
		
		if( key == KeyEvent.VK_LEFT )
			isRotatingCounterClockwise = true;
		
		if( key == KeyEvent.VK_UP) {
			isMovingForward = true;
		}
		
		if( key == KeyEvent.VK_1) {
			selectGun(0);
		}
		
		if( key == KeyEvent.VK_2) {
			selectGun(1);
		}
		
		if( key == KeyEvent.VK_3) {
			selectGun(2);
		}
		
		if( key == KeyEvent.VK_4) {
			selectGun(3);
		}
		
		if( key == KeyEvent.VK_5) {
			selectGun(4);
		}
		
		if( key == KeyEvent.VK_6) {
			selectGun(5);
		}
		
		if( key == KeyEvent.VK_7) {
			selectGun(6);
		}
		
		if( key == KeyEvent.VK_8) {
			selectGun(7);
		}
		
		if( key == KeyEvent.VK_9) {
			selectGun(8);
		}

		if( key == KeyEvent.VK_Q) {
			changeGun();
		}
	}
	
	private void goForward() {
		if(isBoostCoolingDown) return;
		
		if(!isMovingForward) return;

		boostSpeed(orientation, 1);
		
		isBoostCoolingDown = true;
		boostCooldownTimer.restart();
	}

	private void rotateCounterClockWise() {
		if(isRotationCoolingDown) return;
		
		if(!isRotatingCounterClockwise) return;
		
		if(--orientation < 0)
			orientation = MAX_SHIP_INDEX - 1;
		
		isRotationCoolingDown = true;
		rotationCooldownTimer.restart();
	}

	private void rotateClockwise() {
		if(isRotationCoolingDown) return;
		
		if(!isRotatingClockwise) return;
		
		if(++orientation >= MAX_SHIP_INDEX)
			orientation = 0;
		
		isRotationCoolingDown = true;
		rotationCooldownTimer.restart();
	}

	private void changeGun() {
		selectedGun = (selectedGun+1)%guns.size();
		System.out.println("Gun ["+(selectedGun+1)+"] Selected.");
	}

	private void selectGun(int i) {
		if(i<guns.size()){
			selectedGun = i;
			System.out.println("Gun ["+(selectedGun+1)+"] Selected.");
		}
		else{
			System.out.println("Gun ["+(i+1)+"] Not Available.");
		}
	}

	public void keyReleased(KeyEvent e) {
		getGun().keyReleased(e);
		
		if(!isAlive) return;
		int key = e.getKeyCode();
		
		if( key == KeyEvent.VK_RIGHT )
			isRotatingClockwise = false;
		
		if( key == KeyEvent.VK_LEFT )
			isRotatingCounterClockwise = false;
		
		if( key == KeyEvent.VK_UP) {
			isMovingForward = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == rotationCooldownTimer){
			isRotationCoolingDown = false;
		}
		if(e.getSource() == boostCooldownTimer){
			isBoostCoolingDown = false;
		}
	}
}

package asteroids;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Field extends JPanel implements ActionListener {
	
	public static final long serialVersionUID = 1L;
	
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	
    private Timer timer;
    private ShipSprite ship;
    private ArrayList<AsteroidSprite> asteroids;
    private AlienSprite alien;
    private LinkedList<ExplosionSprite> explosions;
    
    private Font font;
    private int score = 0;
    private int lives_left = 5;
    private int max_life = 10;
    
    private ImageIcon bg;
    private ImageIcon lifeIcon;
    private ImageIcon gameOverIcon;
    private ImageIcon gameStartIcon;
    private float gameStartIconOpacity = 1f;
    
    private int themeTotal = 1;
    private int theme = 0;
    private GameState game_state = GameState.INIT;
    
    private boolean debug_mode = false;
    
    public Field() {
    	addKeyListener(new TAdapter());
    	setFocusable(true);
    	
        setBackground(Color.RED);
        setDoubleBuffered(true);

        bg = new ImageIcon(this.getClass().getResource("res/theme1/image/bg/bg.png"));
        lifeIcon = new ImageIcon(this.getClass().getResource("res/theme1/image/other/life_icon.png"));
        gameOverIcon = new ImageIcon(this.getClass().getResource("res/theme1/image/other/gameover.png"));
        gameStartIcon = new ImageIcon(this.getClass().getResource("res/theme1/image/other/gamestart.png"));
        
        //font = new Font("Serif", Font.PLAIN, 24);		

        timer = new Timer(15, this);
        
        startNewGame();
        //alien = new AlienSprite();
    }

	private void startNewGame() {
		lives_left = 5;
	    score = 0;
		
        ship = new ShipSprite(WIDTH/2,HEIGHT/2);       
        asteroids = new ArrayList<AsteroidSprite>();
        for( int i = 0; i < 5; i++ ) {
        	launch_asteroid();
        }
        
        explosions = new LinkedList<ExplosionSprite>();
        
        timer.start();

		game_state = GameState.RUNNING;
		
		showStartImage();
	}

	private void showStartImage() {
		gameStartIconOpacity = 1f;
	}

	public void launch_asteroid() {
		// Asteroids positions need to be launched outside the visible screen.
		asteroids.add( AsteroidSprite.generateLarge(0, 0) );
	}
	
	public void changeTheme(){
		theme = (theme +1 ) % themeTotal;
	}
    
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setFont(new Font("Verdana", Font.PLAIN, 24));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.drawImage(bg.getImage(),0,0,this);
        
        //asteroids
        for(int i = 0; i < asteroids.size(); i++) {
        	AsteroidSprite a = asteroids.get(i);
        	a.paint(g2d);
        }
        
        //bullets
        if(ship.getGun()!=null){
        	for(int k=0; k < ship.getGunsCount();k++){
		        ArrayList<BulletSprite> bullets = ship.getGun(k).getBullets();
		        for(int i = 0; i < bullets.size(); i++) {
		        	BulletSprite b = bullets.get(i);
		        	b.paint(g2d);
		        }
        	}
        }
        
        //ship
        if(ship.isAlive)
        	ship.paint(g2d);
        
        //g2d.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
        
        //draw ship lives
        for(int i = 0 ; i< lives_left;i++){
        	g2d.drawImage(lifeIcon.getImage(), (WIDTH-50)-(i*lifeIcon.getIconWidth()), 10, this);
        }
        
        //draw explosions
        ExplosionSprite ex;
        g2d.setFont(new Font("Serif", Font.PLAIN, 15));	
        for(Iterator<ExplosionSprite> i = explosions.iterator();i.hasNext();)
        {
        	ex = i.next();
        	ex.paint(g2d);
            if(debug_mode) g2d.drawString(ex.getOpacity()+"", ex.getX(), ex.getY());
        }
    	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        //debug_mode
        if(debug_mode){
        	g2d.setColor(Color.LIGHT_GRAY);
        	int yy = 60;
        	g2d.drawString("--debug_mode--", 20, yy+=20);
        	g2d.drawString("game_state:"+game_state, 20, yy+=20);
        	g2d.drawString("ships_left:"+lives_left, 20, yy+=20);
        	g2d.drawString("asteroids:"+asteroids.size(), 20, yy+=20);
        	g2d.drawString("bullets fired:"+ship.getBulletsFired(), 20, yy+=20);
        	g2d.drawString("orientation:"+ship.getOrientation(), 20, yy+=20);
        	yy+=20;
        	g2d.drawString("KEYS:", 20, yy+=20);
        	g2d.drawString("n: new game", 20, yy+=20);
        	g2d.drawString("a: add 1 asteroid", 20, yy+=20);
        	g2d.drawString("x: remove 1 asteroid", 20, yy+=20);
        	g2d.drawString("p: pause/play", 20, yy+=20);
        	g2d.drawString("q: change weapon", 20, yy+=20);
        	g2d.drawString("1-9: select weapon", 20, yy+=20);
        	g2d.drawString("l: add 1 life", 20, yy+=20);
        	g2d.drawString("k: remove 1 life", 20, yy+=20);
        	g2d.drawString("i: add 100 asteroids", 20, yy+=20);
        	g2d.drawString("o: remove 100 asteroids", 20, yy+=20);
            g2d.drawString(ship.getX()+","+ship.getY(), ship.getX(), ship.getY());
        }

        //score
        g2d.setFont(new Font("Verdana", Font.PLAIN, 24));
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawString("SCORE: " + score, 10, 30);
        
        //gameover
        if(game_state == GameState.END)
        	g2d.drawImage(gameOverIcon.getImage(), (WIDTH/2) -(gameOverIcon.getIconWidth()/2) , (HEIGHT/2) -(gameOverIcon.getIconHeight()/2) , this);
        
        //start
    	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,  gameStartIconOpacity ) );
    	 	g2d.drawImage(gameStartIcon.getImage(), (WIDTH/2) -(gameStartIcon.getIconWidth()/2) , (HEIGHT/3) -(gameStartIcon.getIconHeight()/2) , this);
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }	
    
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == timer){
	    	for(int g = 0 ; g < ship.getGunsCount() ; g++){
		    	ArrayList<BulletSprite> bullets = ship.getGun(g).getBullets();
		    	
		        for(int i = 0; i < asteroids.size(); i++) {
		        	AsteroidSprite a = asteroids.get(i);
		        	if(a.isVisible()) {
		        		a.move();
		        	} else
		        		asteroids.remove(i);
		        }
		    	
		        for(int i = 0; i < bullets.size(); i++) {
		        	BulletSprite b = bullets.get(i);
		        	if(b.isVisible()) {
		        		b.move();
		        	} else
		        		bullets.remove(i);
		        }
	    	}
	        
	        if( asteroids.size() == 0 ) {       
	  			System.out.println("Asteroids : 0 - Game Over");
	        }
	        
	    	ship.move();
	    	
	    	//alien.move();
	    	
	    	checkCollisions();
	
	    	if(gameStartIconOpacity >= 0.01)
	    		gameStartIconOpacity -= 0.01;
			
	    	repaint();
    	}
    }
    
    public void checkCollisions() {
    	
    	// Check if asteroids hit ship
    	if(ship.isAlive){
	    	Rectangle s = ship.getBounds();
	    	for(int i = 0; i < asteroids.size(); i++) {
	    		AsteroidSprite a = asteroids.get(i);
	    		Rectangle r = a.getBounds();
	    		
	    		if( a.isVisible() && s.intersects(r) ) {
	    			a.setVisible(false);
	    			System.out.println("Collision Ship/Asteroid");
	    			decreaseLife();
	
	    			explosions.add(new ExplosionSprite(a.getX(),a.getY()));
	
	    		}
	    	}
    	}
    	
    	// Check if bullets hit any asteroids
    	for(int g = 0 ; g < ship.getGunsCount() ; g++){
	    	ArrayList<BulletSprite> bullets = ship.getGun(g).getBullets();
	    	for(int i = 0; i < bullets.size(); i++) {
	          	BulletSprite b = bullets.get(i);
	          	Rectangle r1 = b.getBounds();
	          	
	          	for(int j = 0; j < asteroids.size(); j++) {
	          		AsteroidSprite a = asteroids.get(j);
	          		Rectangle r2 = a.getBounds();
	          		
	          		if( a.isVisible() && b.isVisible() && r1.intersects(r2)) {
	          			b.setVisible(false);
	          			a.setVisible(false);
	          			System.out.println("Collision Bullet/Asteroid");
	          			
	          			score += a.getPoints();
	
	          			if( a.size > 0 ) {
	          				asteroids.addAll( a.spawnFragments() );
	          			}
	        			explosions.add(new ExplosionSprite(b.getX(),b.getY()));
	        			
	          			System.out.println("Asteroids : " + asteroids.size());
	          			break; // Bullet can only hit once.
	          		}
	          	}
	    	}
    	}
    	
    }
    
    private void decreaseLife() {
		if(lives_left>0){
			lives_left--;
			ship.reSpawn();
		}
		if(lives_left<1)
			endGame();
	}
    
    private void endGame() {
		ship.die();
		game_state = GameState.END;
	}

	private void increaseLife() {
		if(lives_left<max_life)
			lives_left++;
	}
    
	private class TAdapter extends KeyAdapter {

    	public void keyReleased(KeyEvent e) {
    		ship.keyReleased(e);
    	}
    	
    	public void keyPressed(KeyEvent e) {
    		ship.keyPressed(e);
    		
    		int key = e.getKeyCode();

    		if( key == KeyEvent.VK_ESCAPE ){
    			System.exit(1);
    		}
    		if( key == KeyEvent.VK_D ){
    			if(debug_mode)
    				debug_mode=false;
				else{
					debug_mode=true;
				}
    		}
    		if( key == KeyEvent.VK_N ){
    			startNewGame();
    		}
    		//add an asteroid
    		if( key == KeyEvent.VK_A ){
        		launch_asteroid();
        		System.out.println("1 asteroid added");
    		}
    		//remove an asteroid
    		if( key == KeyEvent.VK_X ){
    			if(asteroids.size()>0){
    				asteroids.remove(asteroids.size()-1);
    				System.out.println("1 asteroid removed");
        		}
    		}
    		//add 100 asteroids
    		if( key == KeyEvent.VK_I ){
    			int i;
    			for(i = 0 ; i< 100; i++)
        		launch_asteroid();
				System.out.println(i+" asteroid(s) added! INSANEEEEEEEEEEEEEEEEEEEEEEEEEEE");
    		}
    		//remove 100 asteroids
    		if( key == KeyEvent.VK_O ){
    			int i;
    			for(i = 0 ; i< 100; i++)
        			if(asteroids.size()>0){
        				asteroids.remove(asteroids.size()-1);
            		}
        			else{
        				break;
        			}
				System.out.println(i+" asteroid(s) removed");
    		}
    		if( key == KeyEvent.VK_P ){
    			togglePauseGame();
    		}
    		if( key == KeyEvent.VK_L ){
    			increaseLife();
    		}
    		if( key == KeyEvent.VK_K ){
    			decreaseLife();
    		}
    	}
    	
		private void togglePauseGame() {
			if(game_state != GameState.END){
				if(timer.isRunning())
				{
					timer.stop();
					game_state = GameState.PAUSED;
					repaint();
				}
				else 
				{
					timer.start();
					game_state = GameState.RUNNING;
				}
			}
		}
    }
}

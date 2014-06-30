package asteroids;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

import tools2d.Vector2D;

public class Sprite {

	protected double x;
	protected double y;
	private boolean visible = true;
	protected Image image;					
	protected Image thumbnail;				
	
	public Vector2D move = new Vector2D( Math.PI, 1);
	protected int width;
	protected int height;
	protected int thumbnailWidth;
	protected int thumbnailHeight;
	
	protected double opacity = 1;
	
	public Sprite() {};
	
	public Sprite(double x, double y, String imageFile) {
		ImageIcon icon = new ImageIcon();
		
		try{
			icon = new ImageIcon(this.getClass().getResource("/res/theme1/" + imageFile));
		}
		catch(Exception e){
			System.out.println("ImageIcon not found: \""+imageFile+"\"");
			icon = new ImageIcon(this.getClass().getResource("/res/theme1/image/image_not_found.png" ));
		}
		
		image = icon.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		this.x = x;
		this.y = y;
	}
	
	public Sprite(double x, double y, String imageFile, String thumbnailFile) {
		ImageIcon icon = new ImageIcon();
		
		try{
			icon = new ImageIcon(this.getClass().getResource("/res/theme1/" + imageFile));
		}
		catch(Exception e){
			System.out.println("ImageIcon not found: \""+imageFile+"\"");
			icon = new ImageIcon(this.getClass().getResource("/res/theme1/image/image_not_found.png" ));
		}
		
		ImageIcon icon2 = new ImageIcon();
		
		try{
			icon2 = new ImageIcon(this.getClass().getResource("/res/theme1/" + thumbnailFile));
		}
		catch(Exception e){
			System.out.println("ImageIcon not found: \""+thumbnailFile+"\"");
			icon2 = new ImageIcon(this.getClass().getResource("/res/theme1/image/image_not_found.png" ));
		}
		
		image = icon.getImage();
		thumbnail = icon2.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		this.x = x;
		this.y = y;
	}
	
	public int getThumbnailWidth(){
		return thumbnailWidth;
	}

	public int getThumbnailHeight(){
		return thumbnailHeight;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Image getThumbnailImage() {
		return thumbnail;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean state) {
		visible = state;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}
	
	public void setDelta(int steps) {
		// Compensate for screen coordinate system
		move.setAngle( steps * Math.PI / 8);
	}
	
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public void setThumbnailSize(int width, int height){
		this.thumbnailWidth = width;
		this.thumbnailHeight = height;
	}
	
	public void move() {
		x += -move.getX();
		y += move.getY();
		
		if ( y > Field.HEIGHT && move.getY() > 0 ) {
			y = -height;
		}
		
		if ( y < -height && move.getY() < 0 ) {
			y = Field.HEIGHT;
		}
		
		if ( x > Field.WIDTH && -move.getX() > 0 ) {
			x = -width;
		}
		
		if ( x < -width & -move.getX() < 0 ) {
			x = Field.WIDTH;
		}
	}

	public double getOpacity(){
		return opacity;
	}
	
	public void fade(){
		if(opacity > 0){
			opacity -= 0.02;
			if(opacity < 0 ){
				opacity = 0;
			}
		}
	}

}
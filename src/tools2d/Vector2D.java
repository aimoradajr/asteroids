package tools2d;

public class Vector2D {
	private double length;
	private double radian;

	public Vector2D(double angle, double len) {
		setAngle(angle);
		setLength(len);
	}
	
	public void setAngle( double angle ) {
		radian = angle;
	}
	
	public double getAngle() {
		return radian;
	}
	
	public void setLength(double len) {
		length = len;
	}
	
	public double getLength() {
		return length;
	}
	
	public double getX() {
		return Math.sin( radian ) * length ;
	}
	
	public double getY() {
		return Math.cos( radian ) * length ;
	}

	public void setPolar(double angle, double len) {
		radian = angle;
		length = len;
		System.out.println("Set : " + length + " at " + angle + " radians");
	}
	
	public void addPolar(double angle, double len) {
		Vector2D addVector = new Vector2D(angle, len);
	
		double rx = getX() + addVector.getX();
		double ry = getY() + addVector.getY();
		
		length = Math.sqrt( rx*rx + ry*ry );
		radian = Math.atan2( rx, ry );
	}
	
	public void print() {
		System.out.println("  Len: " + length + ", rad: " + radian + ", x: " + getX() + ", y: " + getY());
	}
}
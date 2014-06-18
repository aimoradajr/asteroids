package tools2d;

import static org.junit.Assert.*;

import org.junit.Test;

public class Vector2DTest {

	static final double DEV = 0.00001;
	
	@Test
	public void testVector2D() {
		Vector2D v = new Vector2D(0, 1);
		assertEquals( "Wrong length", 1, v.getLength(), DEV ); 
		assertEquals( "Wrong angle", 0, v.getAngle(), DEV );
	}

	@Test
	public void testGetX() {
		Vector2D v = new Vector2D(0, 2);
		v.print();
		assertEquals( 0, v.getX(), DEV );
		
		Vector2D v2 = new Vector2D(Math.PI / 2, 2);
		v2.print();
		assertEquals( 2, v2.getX(), DEV );
		
		Vector2D v3 = new Vector2D(Math.PI , 2);
		v3.print();
		assertEquals( 0, v3.getX(), DEV );
		
		Vector2D v4 = new Vector2D(Math.PI * 1.5, 2);
		v4.print();
		assertEquals( -2, v4.getX(), DEV );
	}

	@Test
	public void testGetY() {
		Vector2D v = new Vector2D(0, 2);
		assertEquals( 2, v.getY(), DEV );
		
		Vector2D v2 = new Vector2D(Math.PI / 2, 2);
		assertEquals( 0, v2.getY(), DEV );		
		
		Vector2D v3 = new Vector2D(Math.PI, 2);
		assertEquals( -2, v3.getY(), DEV );	
		
		Vector2D v4 = new Vector2D(Math.PI * 1.5, 2);
		assertEquals( 0, v4.getY(), DEV );	
	}

	@Test
	public void testSetGetLength() {
		Vector2D v = new Vector2D(0, 1);
		v.setLength(0.5);
		assertEquals( 0.5, v.getLength(), DEV);
	}

	@Test
	public void testSetAngle() {
		Vector2D v = new Vector2D(0, 1);
		v.setAngle( Math.PI );
		assertEquals( Math.PI, v.getAngle(), DEV);
	}
	
	@Test
	public void testAddPolarUpToUp() {
		Vector2D v = new Vector2D(0, 1); 
		v.addPolar(0,1); 
		assertEquals( 0, v.getAngle(), 0 );
		assertEquals( 2, v.getLength(), 0 );
	}
	
	@Test
	public void testAddPolarRightToUp() {
		Vector2D v = new Vector2D(0, 1);
		v.addPolar( Math.PI/2, 1 );
		assertEquals( Math.PI/4, v.getAngle(), DEV );
		assertEquals( Math.sqrt(2), v.getLength(), DEV );
	}

	@Test
	public void testAddPolarDownToUp() {
		Vector2D v = new Vector2D(0, 1); 
		v.addPolar( Math.PI, 1 ); 
	//	assertEquals( "Angle not nulled!", 0, v.getAngle(), DEV );
		assertEquals( "Length not nulled!", 0, v.getLength(), DEV );
	}
}

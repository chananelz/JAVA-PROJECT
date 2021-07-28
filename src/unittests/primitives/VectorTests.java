package unittests.primitives;

import static org.junit.Assert.*;
import org.junit.Test;

import primitives.*;
import static primitives.Util.*;

/**
 * Testing Vector
 * 
 * @author shmulik
 *
 */
public class VectorTests {
	/**
	 * Test method for
	 * {@link primitives.Vector#Vector(primitives.Coordinate, primitives.Coordinate, primitives.Coordinate)}.
	 */
	@Test
	public void testConstructorCoordinate() {
		// =============== Boundary Values Tests ==================
		// TC01: Test zero vector
		assertThrows("zero vector does not throw an exception", IllegalArgumentException.class,
				() -> new Vector(new Coordinate(0), new Coordinate(0), new Coordinate(0)));
	}

	/**
	 * Test method for {@link primitives.Vector#Vector(double, double, double)}.
	 */
	@Test
	public void testConstructorDouble() {
		// =============== Boundary Values Tests ==================
		// TC01: Test zero vector
		assertThrows("zero vector does not throw an exception", IllegalArgumentException.class,
				() -> new Vector(0, 0, 0));
	}

	/**
	 * Test method for
	 * {@link primitives.Vector#Vector(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testConstructorPoint3D() {
		// =============== Boundary Values Tests ==================
		// TC01: Test zero vector
		assertThrows("zero vector does not throw an exception", IllegalArgumentException.class,
				() -> new Vector(Point3D.ZERO));
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() {
		Vector v1 = new Vector(1, 4, 7);
		Vector v2 = new Vector(2, 6, -3);
		Vector v3 = new Vector(3, 10, 4);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Test add
		assertEquals("add() wrong result", v3, v1.add(v2));
	}

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	public void testSubtract() {
		Vector v1 = new Vector(1, 4, 7);
		Vector v2 = new Vector(2, 6, -3);
		Vector v3 = new Vector(-1, -2, 10);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Test subtract
		assertEquals("Subtract() wrong result", v3, v1.subtract(v2));
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale() {
		Vector v1 = new Vector(1, 4, 7);
		double num = 4;
		Vector v3 = new Vector(4, 16, 28);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Test scale
		assertEquals("Scale() wrong result", v3, v1.scale(num));
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	public void testDotProduct() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);
		Vector v3 = new Vector(0, 3, -2);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Test dotProduct result
		assertTrue("dotProduct() wrong value", isZero(v1.dotProduct(v2) + 28));

		// =============== Boundary Values Tests ==================
		// TC10: Test dot product of orthogonal vectors
		assertTrue("dotProduct() for orthogonal vectors is not zero", isZero(v1.dotProduct(v3)));
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);

		// ============ Equivalence Partitions Tests ==============
		Vector v3 = new Vector(0, 3, -2);
		Vector vr = v1.crossProduct(v3);

		// TC01: Test that length of cross-product is proper (orthogonal vectors taken
		// for simplicity)
		assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

		// TC02: Test cross-product result orthogonality to its operands
		assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
		assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

		// =============== Boundary Values Tests ==================
		// TC10: Test zero vector from cross-productof co-lined vectors
		assertThrows("crossProduct() for parallel vectors does not throw an exception", IllegalArgumentException.class,
				() -> v1.crossProduct(v2));
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	public void testLengthSquared() {
		Vector v1 = new Vector(1, 2, 3);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Text length squared result
		assertTrue("lengthSquared() wrong value", isZero(v1.lengthSquared() - 14));
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	public void testLength() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Text length result
		assertTrue("length() wrong value", isZero(new Vector(0, 3, 4).length() - 5));
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	public void testNormalize() {
		Vector v = new Vector(1, 2, 3);
		Vector vCopy = new Vector(v.getHead());
		Vector vCopyNormalize = vCopy.normalize();

		// =============== Boundary Values Tests ==================
		// TC10: normalize does not need to create a new vector
		assertSame("normalize() function creates a new vector", vCopy, vCopyNormalize);
		// TC11: normalize need to create a unit vector
		assertTrue("normalize() result is not a unit vector", isZero(vCopyNormalize.length() - 1));
	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}.
	 */
	@Test
	public void testNormalized() {
		Vector v = new Vector(1, 2, 3);
		Vector u = v.normalized();

		// =============== Boundary Values Tests ==================
		// TC10: normalizated need to create a new vector
		assertNotSame("normalizated() function does not create a new vector", u, v);
	}
	
	/**
	 * Test method for {@link primitives.Vector#getOrthogonal()}.
	 */
	@Test
	public void testGetOrthogonal() {
		Vector v = new Vector(1, 0, 3);
		Vector u = new Vector(1, 0, 0);
		Vector w = new Vector(0, 2, 3);
		Vector x = new Vector(0, 2, 0);
		Vector y = new Vector(1, 2, 3);
		
		// ============ Equivalence Partitions Tests ==============
		// TC0: getOrthogonal need to create a new orthogonal vector
		assertTrue("getOrthogonal() wrong result ", y.dotProduct(y.getOrthogonal()) == 0);
		
		// =============== Boundary Values Tests ==================
		// TC1: getOrthogonal need to create a new orthogonal vector
		assertTrue("getOrthogonal() wrong result ", v.dotProduct(v.getOrthogonal()) == 0);
		// TC2: getOrthogonal need to create a new orthogonal vector
		assertTrue("getOrthogonal() wrong result ", u.dotProduct(u.getOrthogonal()) == 0);
		// TC3: getOrthogonal need to create a new orthogonal vector
		assertTrue("getOrthogonal() wrong result ", w.dotProduct(w.getOrthogonal()) == 0);
		// TC4: getOrthogonal need to create a new orthogonal vector
		assertTrue("getOrthogonal() wrong result ", x.dotProduct(x.getOrthogonal()) == 0);

	}
}

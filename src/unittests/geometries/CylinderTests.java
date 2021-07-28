package unittests.geometries;

import static org.junit.Assert.*;
import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Cylinder
 * 
 * @author Shmulik & Chananel
 */
public class CylinderTests {
	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Ray r = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1));
		Cylinder c = new Cylinder(r, 1, 2);

		// ============ Equivalence Partitions Tests ==============
		// TC01: test normal in side
		assertEquals("Bad normal to cylinder", new Vector(1, 0, 0), c.getNormal(new Point3D(1, 0, 1)));
		// TC02: test normal in base A
		assertEquals("Bad normal to cylinder", new Vector(0, 0, -1), c.getNormal(new Point3D(0, 0, 0)));
		// TC03: test normal in base B
		assertEquals("Bad normal to cylinder", new Vector(0, 0, 1), c.getNormal(new Point3D(0, 0, 2)));

		// =============== Boundary Values Tests ==================
		// TC10: test normal in suture point (normal vector defined according to the
		// base of the tube)
		assertEquals("Bad normal to cylinder", new Vector(0, 0, -1), c.getNormal(new Point3D(1, 0, 0)));
		// TC11: test normal in suture point (normal vector defined according to the
		// base of the tube)
		assertEquals("Bad normal to cylinder", new Vector(0, 0, 1), c.getNormal(new Point3D(1, 0, 2)));
		// TC12: test normal in base center 1
		assertEquals("Bad normal to cylinder", new Vector(0, 0, -1), c.getNormal(new Point3D(0, 0, 0)));
		// TC13: test normal in base center 2
		assertEquals("Bad normal to cylinder", new Vector(0, 0, 1), c.getNormal(new Point3D(0, 0, 2)));
	}
}

package unittests.geometries;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * Testing Plane
 * 
 * @author shmulik
 *
 */
public class PlaneTests {
	/**
	 * Test method for
	 * {@link geometries.Plane#Plane(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testConstructorPoint3D() {
		// ============ Boundary Values Tests ==============
		// TC10 P1 and P2 are the same
		assertThrows("P1 and P2 are the same", IllegalArgumentException.class,
				() -> new Plane(new Point3D(0, 0, 1), new Point3D(0, 0, 1), new Point3D(0, 1, 0)));

		// TC11 P1, P2 and P3 on the same line
		assertThrows("P1, P2 and P3 on the same line", IllegalArgumentException.class,
				() -> new Plane(new Point3D(0, 0, 1), new Point3D(0, 0, 2), new Point3D(0, 0, 3)));
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormalPoint3D() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals("Bad normal to plane", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
	}

	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Plane plane = new Plane(new Point3D(1, 1, 1), new Point3D(2, 0, 1), new Point3D(3, 0, 1));

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray intersects the plane (1 points)
		assertEquals("Ray intersect the plane", List.of(new Point3D(1, 2, 1)),
				plane.findIntersections(new Ray(new Point3D(0, 1, 2), new Vector(1, 1, -1))));

		// TC02: Ray does not intersect the plane (0 points)
		assertEquals("Ray does not intersect the plane", null,
				plane.findIntersections(new Ray(new Point3D(0, 1, 3), new Vector(1, 1, 1))));

		// =============== Boundary Values Tests ==================

		// **** Group: Ray is parallel to the plane
		// TC10: Ray is included in the plane (0 points)
		assertNull("Ray is parallel to the plane and included in it",
				plane.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 0, 0))));

		// TC11: Ray is not included in the plane (0 points)
		assertNull("Ray is parallel to the plane and not included in it",
				plane.findIntersections(new Ray(new Point3D(0, 0, 2), new Vector(1, 0, 0))));

		// **** Group: Ray is orthogonal to the plane
		// TC12: Ray starts before the plane (1 points)
		assertEquals("Ray is orthogonal to the plane and starts before it", List.of(new Point3D(0, 0, 1)),
				plane.findIntersections(new Ray(new Point3D(0, 0, -1), new Vector(0, 0, 1))));

		// TC13: Ray starts in the plane (0 points)
		assertNull("Ray is orthogonal to the plane and starts in it",
				plane.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1))));

		// TC14: Ray starts after the plane (0 points)
		assertNull("Ray is orthogonal to the plane and starts after it",
				plane.findIntersections(new Ray(new Point3D(0, 0, 3), new Vector(0, 0, 1))));

		// **** Group: Special cases
		// TC15: Ray begins at the plane (0 points)
		assertEquals("Ray begins at the plane", null,
				plane.findIntersections(new Ray(new Point3D(1, 2, 1), new Vector(1, 0, 1))));

		// TC16: Ray begins in the same point that appear in the plane (0 points)
		assertNull("Ray begins in the same point that appear in the plane",
				plane.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(1, 0, 1))));
	}

	/**
	 * Test method for
	 * {@link geometries.Plane#findGeoIntersections(primitives.Ray, double)}.
	 */
	@Test
	public void testFindGeoIntersections() {
		List<GeoPoint> result;
		Plane plane = new Plane(new Point3D(1, 1, 1), new Point3D(2, 0, 1), new Point3D(3, 0, 1));
		Ray ray = new Ray(new Point3D(0, 1, 2), new Vector(1, 1, -1));

		// TC01: point inside the distance (1 points)
		result = plane.findGeoIntersections(ray, 2);
		assertEquals("", List.of(new GeoPoint(plane, new Point3D(1, 2, 1))), result);

		// TC02: point outside the distance (0 points)
		result = plane.findGeoIntersections(ray, 0.5);
		assertNull("", result);
	}
}

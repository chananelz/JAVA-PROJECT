package unittests.geometries;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * Testing Sphere
 * 
 * @author Shmulik & Chananel
 *
 */
public class SphereTests {
	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Sphere s = new Sphere(new Point3D(0, 0, 0), 1);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Test normal of sphere
		assertEquals("Bad normal to sphere", new Vector(0, 0, 1), s.getNormal(new Point3D(0, 0, 1)));
	}

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray's line is outside the sphere (0 points)
		assertNull("Ray's line out of sphere",
				sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

		// TC02: Ray starts before and crosses the sphere (2 points)
		Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
		Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
		List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(3, 1, 0)));
		assertEquals("Wrong number of points", 2, result.size());
		if (result.get(0).getX() > result.get(1).getX())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Ray crosses sphere", List.of(p1, p2), result);

		// TC03: Ray starts inside the sphere (1 point)
		Point3D p = new Point3D(2, 0, 0);
		result = sphere.findIntersections(new Ray(new Point3D(1.5, 0, 0), new Vector(1, 0, 0)));
		assertEquals("Ray starts inside the sphere", List.of(p), result);

		// TC04: Ray starts after the sphere (0 points)
		assertNull("Ray starts after the sphere",
				sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(-1, 0, 0))));

		// =============== Boundary Values Tests ==================

		// **** Group: Ray's line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		result = sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(-1, 1, 0)));
		assertEquals("Ray starts inside the sphere", List.of(new Point3D(1, 1, 0)), result);

		// TC12: Ray starts at sphere and goes outside (0 points)
		assertNull("Ray starts after the sphere",
				sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 1, 0))));

		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the sphere (2 points)
		p1 = new Point3D(0, 0, 0);
		p2 = new Point3D(2, 0, 0);
		result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 0, 0)));
		assertEquals("Wrong number of points", 2, result.size());
		if (result.get(0).getX() > result.get(1).getX())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Ray starts before the sphere", List.of(p1, p2), result);

		// TC14: Ray starts at sphere and goes inside (1 points)
		result = sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(-1, 0, 0)));
		assertEquals("Ray starts at sphere and goes inside", List.of(new Point3D(0, 0, 0)), result);

		// TC15: Ray starts inside (1 points)
		result = sphere.findIntersections(new Ray(new Point3D(1.5, 0, 0), new Vector(1, 0, 0)));
		assertEquals("Ray starts inside", List.of(new Point3D(2, 0, 0)), result);

		// TC16: Ray starts at the center (1 points)
		result = sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(1, 0, 0)));
		assertEquals("Ray starts at the center", List.of(new Point3D(2, 0, 0)), result);

		// TC17: Ray starts at sphere and goes outside (0 points)
		assertNull("Ray starts at sphere and goes outside",
				sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0))));

		// TC18: Ray starts after sphere (0 points)
		assertNull("Ray starts after the sphere",
				sphere.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(1, 0, 0))));

		// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
		// TC19: Ray starts before the tangent point
		assertNull("Ray starts before the tangent point",
				sphere.findIntersections(new Ray(new Point3D(0, -1, 0), new Vector(1, 0, 0))));

		// TC20: Ray starts at the tangent point
		assertNull("Ray starts at the tangent point",
				sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(1, 0, 0))));

		// TC21: Ray starts after the tangent point
		assertNull("Ray starts after the tangent point",
				sphere.findIntersections(new Ray(new Point3D(2, -1, 0), new Vector(1, 0, 0))));

		// **** Group: Special cases
		// TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's
		// center line
		assertNull("Ray starts at the tangent point",
				sphere.findIntersections(new Ray(new Point3D(1, -2, 0), new Vector(1, 0, 0))));
	}

	/**
	 * Test method for
	 * {@link geometries.Sphere#findGeoIntersections(primitives.Ray, double)}.
	 */
	@Test
	public void testFindGeoIntersections() {
		List<GeoPoint> result;
		Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);
		Ray ray = new Ray(new Point3D(-1, 0, 0), new Vector(1, 0, 0));

		GeoPoint p1 = new GeoPoint(sphere, new Point3D(0, 0, 0));
		GeoPoint p2 = new GeoPoint(sphere, new Point3D(2, 0, 0));

		// ============ Equivalence Partitions Tests ==============

		// TC01: tow points inside the distance (2 points)
		result = sphere.findGeoIntersections(ray, 4);
		assertEquals("Wrong number of points", 2, result.size());
		if (result.get(0).point.getX() > result.get(1).point.getX())
			result = List.of(result.get(1), result.get(0));
		assertEquals("", List.of(p1, p2), result);

		// TC02: one point inside the distance (1 points)
		result = sphere.findGeoIntersections(ray, 1);
		assertEquals("", List.of(p1), result);

		// TC03: the points outside the distance (0 points)
		result = sphere.findGeoIntersections(ray, 0.5);
		assertNull("", result);

		// =============== Boundary Values Tests ==================

		// TC10: the second point right in the distance (2 points)
		result = sphere.findGeoIntersections(ray, 3);
		assertEquals("Wrong number of points", 2, result.size());
		if (result.get(0).point.getX() > result.get(1).point.getX())
			result = List.of(result.get(1), result.get(0));
		assertEquals("", List.of(p1, p2), result);

		// TC20: the first point right in the distance (1 points)
		result = sphere.findGeoIntersections(ray, 2);
		assertEquals("", List.of(p1), result);
	}
}

package unittests.primitives;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * test ray class
 * 
 * @author Shmulik & Chananel
 *
 */
public class RayTests {

	/**
	 * Test method for {@link primitives.Ray#getClosestPoint(java.util.List)}.
	 */
	@Test
	public void testGetClosestPoint() {
		Ray ray = new Ray(Point3D.ZERO, new Vector(1, 0, 0));
		List<Point3D> points = new ArrayList<Point3D>();
		Point3D firstPoint = new Point3D(1, 0, 0);
		Point3D secondPoint = new Point3D(2, 0, 0);
		Point3D thirdPoint = new Point3D(6, 0, 0);

		// ============ Equivalence Partitions Tests ==============

		// TC01: the close point is in the middle
		points.add(0, secondPoint);
		points.add(1, firstPoint);
		points.add(2, thirdPoint);

		assertEquals("point in the middel", firstPoint, ray.getClosestPoint(points));

		// ============ Boundary Values Tests ==============

		// TC10: the list is null
		points = null;
		assertNull("list is null", ray.getClosestPoint(points));

		// TC11: the close point is in the beginning
		points = new ArrayList<Point3D>();
		points.add(0, firstPoint);
		points.add(1, secondPoint);
		points.add(2, thirdPoint);

		assertEquals("point in the middel", firstPoint, ray.getClosestPoint(points));

		// TC12: the list is in the end
		points.clear();
		points.add(0, thirdPoint);
		points.add(1, secondPoint);
		points.add(2, firstPoint);

		assertEquals("min point in the middel", firstPoint, ray.getClosestPoint(points));
	}

	/**
	 * Test method for {@link primitives.Ray#getClosestGeoPoint(java.util.List)}.
	 */
	@Test
	public void testGetClosestGeoPoint() {
		Ray ray = new Ray(Point3D.ZERO, new Vector(1, 0, 0));
		List<GeoPoint> points = new ArrayList<GeoPoint>();
		GeoPoint firstPoint = new GeoPoint(null, new Point3D(1, 0, 0));
		GeoPoint secondPoint = new GeoPoint(null, new Point3D(2, 0, 0));
		GeoPoint thirdPoint = new GeoPoint(null, new Point3D(6, 0, 0));

		// ============ Equivalence Partitions Tests ==============

		// TC01: the close point is in the middle
		points.add(0, secondPoint);
		points.add(1, firstPoint);
		points.add(2, thirdPoint);

		assertEquals("point in the middel", firstPoint, ray.getClosestGeoPoint(points));

		// ============ Boundary Values Tests ==============

		// TC10: the list is null
		points = null;
		assertNull("list is null", ray.getClosestGeoPoint(points));

		// TC11: the close point is in the beginning
		points = new ArrayList<GeoPoint>();
		points.add(0, firstPoint);
		points.add(1, secondPoint);
		points.add(2, thirdPoint);

		assertEquals("point in the middel", firstPoint, ray.getClosestGeoPoint(points));

		// TC12: the list is in the end
		points.clear();
		points.add(0, thirdPoint);
		points.add(1, secondPoint);
		points.add(2, firstPoint);

		assertEquals("min point in the middel", firstPoint, ray.getClosestGeoPoint(points));
	}
}

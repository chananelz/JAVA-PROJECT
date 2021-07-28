package unittests.geometries;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class PolygonTests {

	/**
	 * Test method for
	 * {@link geometries.Polygon#Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}

		// TC02: Wrong vertices order
		assertThrows("Constructed a polygon with wrong order of vertices", IllegalArgumentException.class,
				() -> new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0), new Point3D(1, 0, 0),
						new Point3D(-1, 1, 1)));

		// TC03: Not in the same plane
		assertThrows("Constructed a polygon with vertices that are not in the same plane",
				IllegalArgumentException.class, () -> new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
						new Point3D(0, 1, 0), new Point3D(0, 2, 2)));

		// TC04: Concave quadrangular
		assertThrows("Constructed a concave polygon", IllegalArgumentException.class,
				() -> new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
						new Point3D(0.5, 0.25, 0.5)));

		// =============== Boundary Values Tests ==================

		// TC10: Vertex on a side of a quadrangular
		assertThrows("Constructed a polygon with vertix on a side", IllegalArgumentException.class,
				() -> new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
						new Point3D(0, 0.5, 0.5)));

		// TC11: Last point = first point
		assertThrows("Constructed a polygon with vertice on a side", IllegalArgumentException.class,
				() -> new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
						new Point3D(0, 0, 1)));

		// TC12: Colocated points
		assertThrows("Constructed a polygon with vertice on a side", IllegalArgumentException.class,
				() -> new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
						new Point3D(0, 1, 0)));
	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
				new Point3D(-1, 1, 1));
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
	}

	/**
	 * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		List<Point3D> result;
		Triangle triangle = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 1), new Point3D(0, 1, 1));

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray's line is inside triangle (1 points)
		result = triangle.findIntersections(new Ray(new Point3D(0.5, 0.25, 0), new Vector(0, 0, 1)));
		assertEquals("Ray intersection inside the triangle", List.of(new Point3D(0.5, 0.25, 1)), result);

		// TC02: Ray's line is Outside against edge (0 points)
		result = triangle.findIntersections(new Ray(new Point3D(-1, 0.5, 0), new Vector(0, 0, 1)));
		assertNull("Ray's line is Outside against edge", result);

		// TC03: Ray's line is Outside against vertex (0 points)
		result = triangle.findIntersections(new Ray(new Point3D(-1, -1, 0), new Vector(0, 0, 1)));
		assertNull("Ray's line is Outside against vertex", result);

		// =============== Boundary Values Tests =================

		// TC10: Ray's line is on edge (0 points)
		result = triangle.findIntersections(new Ray(new Point3D(0.5, 0.5, 0), new Vector(0, 0, 1)));
		assertNull("Ray's line is on edge", result);

		// TC11: Ray's line is in vertex (0 points)
		result = triangle.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1)));
		assertNull("Ray's line is in vertex", result);

		// TC12: Ray's line is On edge's continuation (0 points)
		result = triangle.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 1)));
		assertNull("Ray's line is On edge's continuation", result);

		Polygon polygon = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 1), new Point3D(1, 1, 1),
				new Point3D(0, 1, 1));

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray's line is inside polygon (1 points)
		result = polygon.findIntersections(new Ray(new Point3D(0.5, 0.25, 0), new Vector(0, 0, 1)));
		assertEquals("Ray intersection inside the polygon", List.of(new Point3D(0.5, 0.25, 1)), result);

		// TC02: Ray's line is Outside against edge (0 points)
		result = polygon.findIntersections(new Ray(new Point3D(-1, 0.5, 0), new Vector(0, 0, 1)));
		assertNull("Ray's line is Outside against edge", result);

		// TC03: Ray's line is Outside against vertex (0 points)
		result = polygon.findIntersections(new Ray(new Point3D(-1, -1, 0), new Vector(0, 0, 1)));
		assertNull("Ray's line is Outside against vertex", result);

		// =============== Boundary Values Tests =================

		// TC10: Ray's line is on edge (0 points)
		result = polygon.findIntersections(new Ray(new Point3D(0.5, 1, 0), new Vector(0, 0, 1)));
		assertNull("Ray's line is on edge", result);

		// TC11: Ray's line is in vertex (0 points)
		result = triangle.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1)));
		assertNull("Ray's line is in vertex", result);

		// TC12: Ray's line is On edge's continuation (0 points)
		result = polygon.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 1)));
		assertNull("Ray's line is On edge's continuation", result);
	}

	/**
	 * Test method for
	 * {@link geometries.Polygon#findGeoIntersections(primitives.Ray, double)}.
	 */
	@Test
	public void testFindGeoIntersections() {
		List<GeoPoint> result;
		Polygon polygon = new Polygon(new Point3D(5, 1, 4), new Point3D(4, -4, 4), new Point3D(-9, -7, 4),
				new Point3D(-8, 4, 4));
		Ray ray = new Ray(new Point3D(0, 0, 8), new Vector(0, 0, -1));

		// TC01: point inside the distance (1 points)
		result = polygon.findGeoIntersections(ray, 5);
		assertEquals("", List.of(new GeoPoint(polygon, new Point3D(0, 0, 4))), result);

		// TC02: point outside the distance (0 points)
		result = polygon.findGeoIntersections(ray, 3);
		assertNull("", result);
	}
}

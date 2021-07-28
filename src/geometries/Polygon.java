package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 */
public class Polygon extends Geometry {
	/**
	 * List of polygon's vertices
	 */
	protected List<Point3D> vertices;

	/**
	 * Associated plane in which the polygon lays
	 */
	protected Plane plane;

	/**
	 * Polygon constructor based on vertices list. The list must be ordered by edge
	 * path. The polygon must be convex.
	 * 
	 * @param vertices list of vertices according to their order by edge path
	 * @throws IllegalArgumentException in any case of illegal combination of
	 *                                  vertices:
	 *                                  <ul>
	 *                                  <li>Less than 3 vertices</li>
	 *                                  <li>Consequent vertices are in the same
	 *                                  point
	 *                                  <li>The vertices are not in the same
	 *                                  plane</li>
	 *                                  <li>The order of vertices is not according
	 *                                  to edge path</li>
	 *                                  <li>Three consequent vertices lay in the
	 *                                  same line (180&#176; angle between two
	 *                                  consequent edges)
	 *                                  <li>The polygon is concave (not convex)</li>
	 *                                  </ul>
	 */
	public Polygon(Point3D... vertices) {
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		this.vertices = List.of(vertices);
		// Generate the plane according to the first three vertices and associate the
		// polygon with this plane.
		// The plane holds the invariant normal (orthogonal unit) vector to the polygon
		plane = new Plane(vertices[0], vertices[1], vertices[2]);
		if (vertices.length == 3)
			return; // no need for more tests for a Triangle

		Vector n = plane.getNormal();

		// Subtracting any subsequent points will throw an IllegalArgumentException
		// because of Zero Vector if they are in the same point
		Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
		Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

		// Cross Product of any subsequent edges will throw an IllegalArgumentException
		// because of Zero Vector if they connect three vertices that lay in the same
		// line.
		// Generate the direction of the polygon according to the angle between last and
		// first edge being less than 180 deg. It is hold by the sign of its dot product
		// with
		// the normal. If all the rest consequent edges will generate the same sign -
		// the
		// polygon is convex ("kamur" in Hebrew).
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
		for (int i = 1; i < vertices.length; ++i) {
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices[i].subtract(vertices[i - 1]);
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
	}

	@Override
	public Vector getNormal(Point3D point) {
		return plane.getNormal();
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		List<GeoPoint> result = plane.findGeoIntersections(ray, maxDistance);

		// Ray not intersects the plane
		if (result == null)
			return null;

		Point3D p0 = ray.getP0();
		Vector dir = ray.getDir();

		// vi = pi - p0
		List<Vector> vectors = new LinkedList<>();
		for (Point3D point : vertices)
			vectors.add(point.subtract(p0));

		int sign; // This is marking variable - save the sign of the vector
		Vector vNn = vectors.get(vectors.size() - 1).crossProduct(vectors.get(0)).normalize(); // vn x v1
		double dirDotVNn = alignZero(dir.dotProduct(vNn));
		if (dirDotVNn == 0)
			return null;
		sign = dirDotVNn > 0 ? 1 : -1;

		// ni = normalize(vi x vi+1)
		for (int i = 0; i < vectors.size() - 1; ++i) {
			Vector currentN = vectors.get(i).crossProduct(vectors.get(i + 1)).normalize();
			double dot = alignZero(dir.dotProduct(currentN));
			if (dot == 0 || dot * sign < 0)
				return null;
		}

		// all v dot Ni have the same sign
		result.get(0).geometry = this;
		return result;
	}

	@Override
	protected double[] getMinMax() {	
		double minX = Double.POSITIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double minZ = Double.POSITIVE_INFINITY;
		double maxX = Double.NEGATIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		double maxZ = Double.NEGATIVE_INFINITY;

		for (Point3D point : vertices) {
			double x = point.getX();
			double y = point.getY();
			double z = point.getZ();

			if (x > maxX)
				maxX = x;
			if (x < minX)
				minX = x;

			if (y > maxY)
				maxY = y;
			if (y < minY)
				minY = y;

			if (z > maxZ)
				maxZ = z;
			if (z < minZ)
				minZ = z;
		}

		double minMax[] = { minX, minY, minZ, maxX, maxY, maxZ };
		return minMax;
	}
}

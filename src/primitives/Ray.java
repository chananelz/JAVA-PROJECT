package primitives;

import java.util.List;
import java.util.stream.Collectors;

import geometries.Intersectable.GeoPoint;

/**
 * Ray - any of a set of straight lines passing through one point
 * 
 * @author shmulik
 */
public class Ray {
	/**
	 * This is a fixed value that represent a little number. It help us to prevent
	 * unwanted image that caused by accuracy problem.
	 */
	private static final double DELTA = 0.1;

	private Point3D p0; // the starting point
	private Vector dir; // the direction vector

	/**
	 * Constructor that get point and vector and create ray
	 * 
	 * @param point  - starting point
	 * @param vector - direction vector
	 */
	public Ray(Point3D point, Vector vector) {
		p0 = point;
		dir = vector.normalized();
	}

	/**
	 * We need this Constructor to prevent unwanted image that caused by accuracy
	 * problem. We want to move the intersection point to safe place according to
	 * the direction of the normal.
	 * 
	 * @param head      - the point that we want to move according to the direction
	 *                  of the normal.
	 * @param direction - the direction of the ray
	 * @param normal    - To know in which direction to move the point
	 */
	public Ray(Point3D head, Vector direction, Vector normal) {
		double signedDelta = normal.dotProduct(direction) > 0 ? DELTA : -DELTA;
		p0 = head.add(normal.scale(signedDelta));
		dir = direction.normalized();
	}

	/**
	 * get p0
	 * 
	 * @return the starting point
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 * get direction vector
	 * 
	 * @return the direction vector
	 */
	public Vector getDir() {
		return dir;
	}

	/**
	 * calculate point on the direction vector in distance t from p0
	 * 
	 * @param t - distance
	 * @return the point
	 */
	public Point3D getPoint(double t) {
		return p0.add(dir.scale(t));
	}

	/**
	 * find the closes point to p0 from a list of points
	 * 
	 * @param points - list of points
	 * @return - the closes point to p0
	 */
	public Point3D getClosestPoint(List<Point3D> points) {
		return (points == null) ? null
				: getClosestGeoPoint(
						points.stream().map(p -> new GeoPoint(null, p)).collect(Collectors.toList())).point;
	}

	/**
	 * find the closes point to p0 from a list of GeoPoints (point with geometry)
	 * 
	 * @param points - list of GeoPoints
	 * @return - the closes GeoPoint to p0
	 */
	public GeoPoint getClosestGeoPoint(List<GeoPoint> points) {
		if (points == null)
			return null;

		GeoPoint closesPoint = null;
		double minDistance = Double.POSITIVE_INFINITY;

		for (GeoPoint goePoint : points) {
			double dist = goePoint.point.distanceSquared(p0);
			if (dist < minDistance) {
				closesPoint = goePoint;
				minDistance = dist;
			}
		}

		return closesPoint;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		Ray other = (Ray) obj;
		return this.p0.equals(other.p0) && this.dir.equals(other.dir);
	}

	@Override
	public String toString() {
		return p0.toString() + " " + dir.toString();
	}
	
}

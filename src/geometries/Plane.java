package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * Plane class represents plain in the geometric space
 * 
 * @author shmulik
 *
 */
public class Plane extends Geometry {
	private Point3D p0; // point on the plane
	private Vector normal; // normal vector to the plane

	/**
	 * Constructor that calculate the normal and build a plain object
	 * 
	 * @param p1 - first point
	 * @param p2 - second point
	 * @param p3 - third point
	 */
	public Plane(Point3D p1, Point3D p2, Point3D p3) {
		p0 = p1;

		// Calculate two vectors of the plane (point subtraction)
		// and the vector orthogonal to them (cross product).
		Vector v1 = p2.subtract(p1);
		Vector v2 = p3.subtract(p1);
		normal = v1.crossProduct(v2).normalize();
	}

	/**
	 * Constructor that build a plain object
	 * 
	 * @param point  - point on the plane
	 * @param vector - The normal vector
	 */
	public Plane(Point3D point, Vector vector) {
		p0 = point;
		normal = vector.normalize();
	}

	/**
	 * @return the p0
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 * @return the normal
	 */
	public Vector getNormal() {
		return normal;
	}

	@Override
	public Vector getNormal(Point3D point) {
		return normal;
	}

	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		Point3D rayP0 = ray.getP0();
		Vector dir = ray.getDir();

		double nv = normal.dotProduct(dir);

		// Check if ray is contained within the plane
		if (isZero(nv))
			return null;

		if (p0.equals(rayP0))
			return null;

		double numerator = normal.dotProduct(p0.subtract(rayP0));
		double t = alignZero(numerator / nv);

		// return Geopoint only if it according to the distance limit
		return (t > 0 && alignZero(t - maxDistance) <= 0) ? List.of(new GeoPoint(this, ray.getPoint(t))) : null;
	}

	@Override
	public String toString() {
		return p0 + " " + normal;
	}

	@Override
	protected double[] getMinMax() {
		double minX = Double.NEGATIVE_INFINITY;
		double minY = Double.NEGATIVE_INFINITY;
		double minZ = Double.NEGATIVE_INFINITY;
		double maxX = Double.POSITIVE_INFINITY;
		double maxY = Double.POSITIVE_INFINITY;
		double maxZ = Double.POSITIVE_INFINITY;

		//the plane is parallel to one of the axis
		if (normal.equals(Vector.X)) {
			double x = p0.getX();
			minX = x;
			maxX = x;
		}
		if (normal.equals(Vector.Y)) {
			double y = p0.getY();
			minY = y;
			maxY = y;
		}
		if (normal.equals(Vector.Z)) {
			double z = p0.getZ();
			minZ = z;
			maxZ = z;
		}

		double minMax[] = { minX, minY, minZ, maxX, maxY, maxZ };
		return minMax;
	}
}

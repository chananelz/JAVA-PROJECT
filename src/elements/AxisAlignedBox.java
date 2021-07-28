package elements;

import static primitives.Util.*;
import primitives.*;

/**
 * AxisAlignedBox class represent a box that parallel to axes.
 * 
 * @author shmulik
 */
public class AxisAlignedBox {
	private double minX;
	private double minY;
	private double minZ;
	private double maxX;
	private double maxY;
	private double maxZ;

	private double tStart = Double.NEGATIVE_INFINITY;
	private double tEnd = Double.POSITIVE_INFINITY;

	/**
	 * constructor for min and max.
	 * 
	 * @param minMax - array with minimum and maximum coordinate
	 *               (0-minX,1-minY,2-minZ,3-maxX,4-maxY,5-maxZ).
	 */
	public AxisAlignedBox(double minMax[]) {
		minX = minMax[0];
		minY = minMax[1];
		minZ = minMax[2];
		maxX = minMax[3];
		maxY = minMax[4];
		maxZ = minMax[5];
	}

	/**
	 * Checks if there is at least one intersection between the ray and the box.
	 * 
	 * @param ray - the ray to intersect.
	 * @return true - if there is an intersection, false - otherwise.
	 */
	public boolean checkIntersection(Ray ray) {
		Point3D p0 = ray.getP0();
		Vector dir = ray.getDir();
		Point3D rD = dir.getHead();

		// the ray is parallel to x axis
		double p0X = p0.getX();
		double rDX = rD.getX();
		if (isZero(rDX) && (p0X > maxX || p0X < minX))
			return false;

		// the ray is parallel to y axis
		double p0Y = p0.getY();
		double rDY = rD.getY();
		if (isZero(rDY) && (p0Y > maxY || p0Y < minY))
			return false;

		// the ray is parallel to z axis
		double p0Z = p0.getZ();
		double rDZ = rD.getZ();
		if (isZero(rDZ) && (p0Z > maxZ || p0Z < minZ))
			return false;

		tStart = Double.NEGATIVE_INFINITY;
		tEnd = Double.POSITIVE_INFINITY;
		updateTStartTEnd(minX, maxX, p0X, 1 / rDX);
		// box is behind
		if (tEnd < 0)
			return false;

		updateTStartTEnd(minY, maxY, p0Y, 1 / rDY);
		// box is missed or box is behind
		if (tStart > tEnd || tEnd < 0)
			return false;

		updateTStartTEnd(minZ, maxZ, p0Z, 1 / rDZ);
		// box is missed or box is behind
		if (tStart > tEnd || tEnd < 0)
			return false;

		return true;
	}

	/**
	 * find the intersection interval between the ray to the box in one dimension
	 * (x, y or z).
	 * 
	 * @param min - the component from minimum point.
	 * @param max - the component from maximum point.
	 * @param p0  - the component from start point of the ray.
	 * @param rD  - the component from direction vector of the ray.
	 */
	private void updateTStartTEnd(double min, double max, double p0, double rD) {
		double t1 = (min - p0) * rD;
		double t2 = (max - p0) * rD;
		if (t1 > t2) {
			double temp = t1;
			t1 = t2;
			t2 = temp;
		}

		if (t1 > tStart)
			tStart = t1;
		if (t2 < tEnd)
			tEnd = t2;
	}

	/**
	 * find the center in one dimension.
	 * 
	 * @param axis - the axis to find his center.
	 * @return the center component.
	 */
	public double getCenter(char axis) {
		if (axis == 'x')
			return minX + ((maxX - minX) / 2);

		else if (axis == 'y')
			return minY + ((maxY - minY) / 2);

		else
			return minZ + ((maxZ - minZ) / 2);
	}

	public double[] getMinMax() {
		return new double[] { minX, minY, minZ, maxX, maxY, maxZ };
	}

}

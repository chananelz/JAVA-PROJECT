package elements;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import primitives.*;
import static primitives.Util.*;

/**
 * RaysBeam class represent beam that generate due to intersect of a ray with
 * uneven material. This class will generate the list of the rays that
 * rayTracerBasic class will use to determine the color of a particular pixel
 * 
 * @author Shmulik & Chananel
 *
 */
public class RaysBeam {

	/**
	 * BlackBoard class represent a circle that location in a fix place from the
	 * intersection point. This shape will help us to determine the scattering of
	 * the beam
	 * 
	 * @author Shmulik & Chananel
	 */
	public class BlackBoard {
		/**
		 * this is a fix value that represent the distance between the intersection
		 * point and the BlackBoard
		 */
		private double blackBoardDistance;
		private Random rand = new Random(); // for the move

		private Point3D center;
		private Vector xAxis;
		private Vector yAxis;

		private double radius;

		/**
		 * construct the BlackBoard - center point, radius size and axes.
		 * 
		 * @param r   - distance between center to radius
		 * @param ray - for direction and center point
		 */
		public BlackBoard(double r, Ray ray, double distance) {
			Vector vTo = ray.getDir();
			blackBoardDistance = distance;
			radius = r * distance;
			
			center = ray.getPoint(blackBoardDistance);
			xAxis = vTo.getOrthogonal();
			yAxis = xAxis.crossProduct(vTo);
		}

		/**
		 * this method help us to find the next ray in the beam.
		 * We chose to calculate it with random library
		 * @return
		 */
		public Point3D getNextPoint() {
			Point3D point = center.add(xAxis.scale(movePoint()) //
					.add(yAxis.scale(movePoint())));
			
			// we want the random point only if it inside a circle around the center. 
			return (alignZero(point.distance(center) - radius) < 0) ? point : null;
		}

		/**
		 *  this method help getNextPoint to find the next ray in the beam.
		 * @return
		 */
		public double movePoint() {
			return (rand.nextDouble() * 2 - 1) * radius;
		}

	}

	private Point3D p0; // starting point for all rays
	private BlackBoard blackBoard; // the ending point for all rays

	/**
	 * number of rays to generate and return
	 */
//	private static final int NUM_OF_RAYS = 80;

	/**
	 * construct the start point and the blackBoard according to the ray.
	 * 
	 * @param ray
	 */
	public RaysBeam(Ray ray, double ky, double distance) {
		p0 = ray.getP0();
		blackBoard = new BlackBoard(ky, ray, distance);
	}

	/**
	 * generate rays from p0 to the scope of the black board randomly.
	 * 
	 * @return list of rays
	 */
	public List<Ray> generateRays(int numOfRays) {
		Point3D point;
		List<Ray> rays = new LinkedList<>(); // to return
		
		while (rays.size() < numOfRays) {
			// move the center point along the axes of the blackBoard
			point = blackBoard.getNextPoint();

			// construct a new ray
			if (point != null)
				rays.add(new Ray(p0, point.subtract(p0)));
		}

		return rays;
	}

}

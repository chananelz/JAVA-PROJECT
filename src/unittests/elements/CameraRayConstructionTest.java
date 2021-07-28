package unittests.elements;

import static org.junit.Assert.*;

import org.junit.Test;

import elements.Camera;
import geometries.*;
import primitives.*;

/**
 * Integration test - Camera and Ray
 * 
 * @author Shmulik & Chananel
 *
 */
public class CameraRayConstructionTest {

	/**
	 * test camera and sphere
	 */
	@Test
	public void testSphere() {
		// TC01: 3X3 case of ray-sphere 2 point intersections
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1).setViewPlaneSize(3, 3);
		Sphere sphere = new Sphere(new Point3D(0, 0, -3), 1);

		assertEquals("Bad ray - case of ray-sphere 2 point intersections", 2, countIntersections(camera, sphere));

		// TC02: 3X3 case of ray-sphere 18 point intersections
		camera = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1).setViewPlaneSize(3, 3);
		sphere = new Sphere(new Point3D(0, 0, -2.5), 2.5);

		assertEquals("Bad ray - case of ray-sphere 18 point intersections", 18, countIntersections(camera, sphere));

		// TC03: 3X3 case of ray-sphere 10 point intersections
		sphere = new Sphere(new Point3D(0, 0, -2), 2);
		assertEquals("Bad ray - case of ray-sphere 10 point intersections", 10, countIntersections(camera, sphere));

		// TC04: 3X3 case of ray-sphere 9 point intersections
		sphere = new Sphere(new Point3D(0, 0, 1.5), 4);
		assertEquals("Bad ray - case of ray-sphere 9 point intersections", 9, countIntersections(camera, sphere));

		// TC05: 3X3 case of ray-sphere 0 point intersections
		sphere = new Sphere(new Point3D(0, 0, 1), 0.5);
		assertEquals("Bad ray - case of ray-sphere 0 point intersections", 0, countIntersections(camera, sphere));
	}

	/**
	 * test camera and plain
	 */
	@Test
	public void testPlain() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1).setViewPlaneSize(3, 3);

		// TC01: 3X3 case of ray-Plane 9 point intersections
		Plane plane = new Plane(new Point3D(0, 0, -10), new Vector(0, 0, 1));
		assertEquals("Bad ray - case of ray-Plane 9 point intersections", 9, countIntersections(camera, plane));

		// TC02: 3X3 case of ray-Plane incline 9 point intersections
		plane = new Plane(new Point3D(-0.5, 0, -1), new Point3D(-0.5, -2, -1.5), new Point3D(-1.5, 0, -1));
		assertEquals("Bad ray - case of ray-Plane incline 9 point intersections", 9, countIntersections(camera, plane));

		// TC03: 3X3 case of ray-Plane 6 point intersections
		plane = new Plane(new Point3D(-0.5, 0, -1), new Point3D(-0.5, -2, 1), new Point3D(-1.5, 0, -1));
		assertEquals("Bad ray - case of ray-Plane 6 point intersections", 6, countIntersections(camera, plane));
	}

	/**
	 * test camera and triangle
	 */
	@Test
	public void testTriangle() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0));
		camera.setDistance(1).setViewPlaneSize(3, 3);

		// TC01: 3X3 case of ray-Triangle 1 point intersections
		Triangle triangle = new Triangle(new Point3D(0, 1, -2), new Point3D(-1, -1, -2), new Point3D(1, -1, -2));
		assertEquals("Bad ray - case of ray-Triangle 1 point intersections", 1, countIntersections(camera, triangle));

		// TC02: 3X3 case of ray-Triangle 2 point intersections
		triangle = new Triangle(new Point3D(0, 20, -2), new Point3D(-1, -1, -2), new Point3D(1, -1, -2));
		assertEquals("Bad ray - case of ray-Triangle 2 point intersections", 2, countIntersections(camera, triangle));
	}

	/**
	 * help method - build ray and find the instructions
	 * 
	 * @param camera - starting point of the ray
	 * @param shape  - the shape that cut by the rays
	 * @param sum    - sum the Cutting point
	 * @return - sum
	 */
	private int countIntersections(Camera camera, Intersectable shape) {
		int sum = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				var intersection = shape.findIntersections(camera.constructRayThroughPixel(3, 3, j, i));
				if (intersection != null)
					sum += intersection.size();
			}
		}

		return sum;
	}

}

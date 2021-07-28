package elements;

import primitives.*;

/**
 * LightSource is interface that represents behavior of light - intensity from
 * the light on geometry and L the direction of the geometry
 * 
 * @author Shmulik & Chananel
 */
public interface LightSource {
	/**
	 * get the intensity
	 * 
	 * @param p - intensity in this point
	 */
	public Color getIntensity(Point3D p);

	/**
	 * L vector - represents the direction of the light - to certain point
	 * 
	 * @param p -certain point
	 * @return
	 */
	public Vector getL(Point3D p);

	/**
	 * calculate the distance between the light source and the point
	 * 
	 * @param point - we want to calculate the distance to this point
	 * @return - the distance
	 */
	public double getDistance(Point3D point);
}

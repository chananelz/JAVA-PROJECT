package elements;

import primitives.*;

/**
 * Directional light class represents the light that come to the scene from
 * Infinite distance - like the sun this class extends Light class and
 * implements LightSource
 * 
 * @author Shmulik & Chananel
 */
public class DirectionalLight extends Light implements LightSource {
	// direction is the vector that represent the direction of a light
	private Vector direction;

	/**
	 * constructor to Directional Light
	 * 
	 * @param intensity - the intensity of the light
	 * @param direction - the direction of the light
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point3D p) {
		return intensity;
	}

	@Override
	public Vector getL(Point3D p) {
		return direction;
	}

	@Override
	public double getDistance(Point3D point) {
		return Double.POSITIVE_INFINITY;
	}

}

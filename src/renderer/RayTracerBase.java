package renderer;

import primitives.Color;
import primitives.Ray;
import scene.*;

/**
 * RayTracerBase class is a abstract class that have tow methods which help
 * determine the color of the image in a particular pixel
 * 
 * @author Shmulik & Chananel
 */
public abstract class RayTracerBase {
	/**
	 * the scene to trace rays on
	 */
	protected Scene scene;

	/**
	 * initialize scene
	 * 
	 * @param scene - get scene to initialize this scene
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/**
	 * get ray and determine his color (ray = pixel) Calculate the color in the
	 * intersection point between the geometries and a certain ray
	 * 
	 * @param ray - the ray
	 * @return - the color in this pixel
	 */
	public abstract Color traceRay(Ray ray);
}

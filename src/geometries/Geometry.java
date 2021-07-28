package geometries;

import primitives.*;

/**
 * Geometry abstract class represents Some geometric body with color and
 * material, and we can get the normal vector from a point on geometry.
 * 
 * @author shmulik
 */
public abstract class Geometry extends Intersectable {
	/**
	 * The light that the body emits
	 */
	protected Color emission = Color.BLACK;
	private Material material = new Material();

	/**
	 * set emission
	 * 
	 * @param emission - the emission color
	 * @return this
	 */
	public Geometry setEmission(Color emmission) {
		this.emission = emmission;
		return this;
	}

	/**
	 * get emission
	 * 
	 * @return the emission
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * set material
	 * 
	 * @param material - the material to set
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}

	/**
	 * get material
	 * 
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * calculate the normal vector to the body at specific point.
	 * 
	 * @param point - A point on the geometric shape (the method don't check it)
	 * @return The normal vector
	 */
	public abstract Vector getNormal(Point3D point);

}

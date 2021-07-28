package elements;

import primitives.Color;

/**
 * light abstract class represents a light with intensity
 * 
 * @author Shmulik & Chananel
 *
 */
abstract class Light {
	/**
	 * the intensity of the light
	 */
	protected Color intensity;

	/**
	 * constructor to Light
	 * 
	 * @param intensity - the intensity of the light
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * get the intensity of the light
	 * 
	 * @return the intensity
	 */
	public Color getIntensity() {
		return intensity;
	}

}

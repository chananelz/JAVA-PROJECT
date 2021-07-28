package elements;

import primitives.*;

/**
 * AmbientLight class represents the ambient light that affects all the scene
 * 
 * @author Shmulik & Chananel
 */
public class AmbientLight extends Light {
	/**
	 * Default constructor of Ambient Light (Black color)
	 */
	public AmbientLight() {
		super(Color.BLACK);
	}

	/**
	 * constructor of AmbientLight
	 * 
	 * @param intensity - the intensity of the light
	 * @param kA        - a factor to intensity of the light
	 */
	public AmbientLight(Color intensity, double kA) {
		super(intensity.scale(kA));
	}

}

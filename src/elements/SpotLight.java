package elements;

import primitives.*;
import static primitives.Util.*;

/**
 * SpotLight is a class that represents Spot - a sours of light that light in
 * 180 deg this class extends Light class and implements LightSource interface
 * 
 * @author Shmulik & Chananel
 *
 */
public class SpotLight extends PointLight {
	// the general direction of the spot
	private Vector direction;
	// narrowBeam this factor cause the beam to be in more focus
	private int narrowBeam = 1;

	/**
	 * constructor of spot light
	 * 
	 * @param intensity
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
	}

	/**
	 * set value to narrowBeam
	 * @param narrowBeam the narrowBeam to set
	 */
	public SpotLight setNarrowBeam(int narrowBeam) {
		this.narrowBeam = narrowBeam;
		return this;
	}

	@Override
	public Color getIntensity(Point3D p) {
		double numerator = alignZero(direction.dotProduct(super.getL(p)));
		if (numerator <= 0)
			return Color.BLACK;

		if (narrowBeam != 1)
			numerator = Math.pow(numerator, narrowBeam);
		return super.getIntensity(p).scale(numerator);
	}
}

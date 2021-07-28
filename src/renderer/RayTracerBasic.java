package renderer;

import java.util.List;

import elements.LightSource;
import elements.RaysBeam;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import static primitives.Util.*;
import scene.Scene;

/**
 * RayTracerBasic class extends RayTracerBase, this class determine the color of
 * the image in a particular pixel - according to the ray that come from the
 * camera
 * 
 * @author Shmulik & Chananel
 */
public class RayTracerBasic extends RayTracerBase {
	/**
	 * fixed value - how many light loose in the event that the beam spite to tow
	 * spared ray
	 */
	private static final double INITIAL_K = 1.0;
	/**
	 * Stop conditions - number of iterations in the mutual recursion
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	/**
	 * Stop conditions - minimum amount of light
	 */
	private static final double MIN_CALC_COLOR_K = 0.001;

	private int numOfRays = 80;
	private double blackBoardDistance = 100;
	
	private boolean cbr = false;

	/**
	 * @param numOfRays the numOfRays to set
	 * @return this (builder pattern)
	 */
	public RayTracerBasic setNumOfRays(int numOfRays) {
		this.numOfRays = numOfRays;
		return this;
	}

	/**
	 * @param blackBoardDistance the blackBoardDistance to set
	 * @return this (builder pattern)
	 */
	public RayTracerBasic setBlackBoardDistance(double blackBoardRadius) {
		this.blackBoardDistance = blackBoardRadius;
		return this;
	}

	/**
	 * @param cbr - if to first check intersections with the box
	 * @return this (builder pattern)
	 */
	public RayTracerBasic setCbr(boolean cbr) {
		this.cbr = cbr;
		return this;
	}

	/**
	 * initialize scene
	 * 
	 * @param scene - the scene
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray.getDir());
	}

	/**
	 * calculate the color in this point according to the Phong Reflectance Model
	 * (ambient light)
	 * 
	 * @param geopoint - the intersection point with the ray
	 * @param v        - the ray direction
	 * @return the color for this ray
	 */
	private Color calcColor(GeoPoint geopoint, Vector v) {
		return calcColor(geopoint, v, MAX_CALC_COLOR_LEVEL, INITIAL_K) //
				.add(scene.ambientLight.getIntensity());
	}

	/**
	 * calculate the color in this point according to the Phong Reflectance Model.
	 * (local effects - diffusive and Specular, global effects - reflection and
	 * refraction)
	 * 
	 * @param intersection - the intersection point with the ray
	 * @param v            - the ray direction
	 * @param level        - current level of the recursion
	 * @param k            - for the intensity
	 * @return the color for this ray
	 */
	private Color calcColor(GeoPoint intersection, Vector v, int level, double k) {
		Color color = intersection.geometry.getEmission();
		color = color.add(calcLocalEffects(intersection, v, k));
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, v, level, k));
	}

	/**
	 * calculate the local effects in the point considering all the light sources
	 * 
	 * @param intersection - the intersection point
	 * @param ray          - the ray from the light
	 * @param k
	 * @return the color
	 */
	private Color calcLocalEffects(GeoPoint intersection, Vector v, double k) {
		Vector n = intersection.geometry.getNormal(intersection.point);

		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;

		Material material = intersection.geometry.getMaterial();

		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				double ktr = transparency(lightSource, l, n, intersection);
				if (ktr * k > MIN_CALC_COLOR_K) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale( //
							ktr * (calcDiffusive(material.kD, nl) + //
									calcSpecular(material.kS, l, n, v, material.nShininess, nl)) //
					);
					color = color.add(lightIntensity);
				}
			}
		}

		return color;
	}

	/**
	 * calculate the diffusive component
	 * 
	 * @param kd - coefficient in the formula
	 * @param nl - dot product between l and the normal When l and n are normalized,
	 *           l · n is the cosine of the angle between them
	 * @return double that represent the diffusive part
	 */
	private double calcDiffusive(double kd, double nl) {
		return kd * Math.abs(nl);
	}

	/**
	 * calculate the Specular component
	 * 
	 * @param ks         - coefficient in the formula
	 * @param l          - vector between the light and the intersection point
	 * @param n          - normal of the body
	 * @param v          - the direction of the camera
	 * @param nShininess - coefficient in the formula
	 * @param nl         - dot product between l and the normal
	 * @return double that represent the Specular part
	 */
	private double calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, double nl) {
		Vector r = l.subtract(n.scale(nl * 2));

		double minusVDotR = alignZero(-v.dotProduct(r));
		return minusVDotR <= 0 ? 0 : Math.pow(minusVDotR, nShininess) * ks;
	}

	/**
	 * Calculate global effect of the light on the color - with all the ray that
	 * split from the main ray
	 *
	 * @param geopoint the point
	 * @param ray      the ray to draw
	 * @param level    number of iterations in the mutual recursion
	 * @param k        - how many light loose in the event that the beam spite to
	 *                 tow spared ray
	 * @return the color
	 */
	private Color calcGlobalEffects(GeoPoint geopoint, Vector v, int level, double k) {
		Color color = Color.BLACK;
		Material material = geopoint.geometry.getMaterial();
		Vector normal = geopoint.geometry.getNormal(geopoint.point);

		double kr = material.kR, kkr = k * kr;
		if (kkr > MIN_CALC_COLOR_K) {
			double nl = normal.dotProduct(v);
			Vector r = v.subtract(normal.scale(nl * 2)).normalized();

			color = calcGlobalEffect(new Ray(geopoint.point, r, normal), level, kr, kkr, material.kG, normal);
		}

		double kt = material.kT, kkt = k * kt;
		if (kkt > MIN_CALC_COLOR_K) {
			color = calcGlobalEffect(new Ray(geopoint.point, v, normal), level, kt, kkt, material.kB, normal);
		}

		return color;
	}

	/**
	 * help method - calculate a global effect on the closest point.
	 * 
	 * @param ray   - the ray to draw
	 * @param level - number of iterations in the mutual recursion
	 * @param kx    - how many light loose
	 * @param kkx   - kx * k
	 * @return the color of the ray
	 */
	private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx, double ky, Vector normal) {
		GeoPoint gp = findClosestIntersection(ray);
		if (gp == null)
			return scene.background.scale(kx);

		if (ky == 0)
			return calcColor(gp, ray.getDir(), level - 1, kkx).scale(kx);

		Color sumColor = Color.BLACK;
		RaysBeam raysBeam = new RaysBeam(ray, ky, blackBoardDistance);
		List<Ray> rays = raysBeam.generateRays(numOfRays);
		for (Ray r : rays) {
			Vector rDir = r.getDir();
			if (ray.getDir().dotProduct(normal) * rDir.dotProduct(normal) > 0) {
				gp = findClosestIntersection(r);
				sumColor = sumColor.add(gp == null ? scene.background : calcColor(gp, rDir, level - 1, kkx)).scale(kx);
			}
		}
		return sumColor.reduce(rays.size());
	}

	/**
	 * find the value that represent the transparency of specific ray according to
	 * all the shape that the ray intersect
	 * 
	 * @param light    - lightSource for the distance.
	 * @param l        - vector from the light to the point
	 * @param n        - normal vector to the geometry in the point
	 * @param geopoint - the point on the geometry
	 * @return transparency between 0 to 1
	 */
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geopoint.point, lightDirection, n);

		List<GeoPoint> intersections = cbr ? //
				scene.geometries.findCbrGeoIntersections(lightRay, light.getDistance(geopoint.point)) : //
				scene.geometries.findGeoIntersections(lightRay, light.getDistance(geopoint.point));
		
		double ktr = 1.0;
		if (intersections != null) {
			for (GeoPoint gp : intersections) {
				ktr *= gp.geometry.getMaterial().kT;
				if (ktr < MIN_CALC_COLOR_K)
					return 0.0;
			}
		}

		return ktr;
	}

	/**
	 * calculate the closest intersection point to the source light
	 * 
	 * @param ray - ray to intersect
	 * @return the closest intersection
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = cbr ? //
				scene.geometries.findCbrGeoIntersections(ray, Double.POSITIVE_INFINITY) : //
				scene.geometries.findGeoIntersections(ray);

		// if there is intersections - the color determine by the close point
		return intersections == null ? null : ray.getClosestGeoPoint(intersections);
	}

}

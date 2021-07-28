package scene;

import java.util.LinkedList;
import java.util.List;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

/**
 * Scene class represent the scene each scene has a name, background color,
 * AmbientLight, and geometries
 * 
 * @author Shmulik & Chananel
 */
public class Scene {
	/**
	 * Scene name
	 */
	public String name;
	/**
	 * background color for the scene
	 */
	public Color background = Color.BLACK;
	/**
	 * ambientLight color for the scene
	 */
	public AmbientLight ambientLight = new AmbientLight();
	/**
	 * geometries in the scene
	 */
	public Geometries geometries = new Geometries();
	/**
	 * lights in the scene
	 */
	public List<LightSource> lights = new LinkedList<LightSource>();

	/**
	 * Constructor of scene initialize scene with a name
	 * 
	 * @param name - the name of the scene
	 */
	public Scene(String name) {
		this.name = name;
	}

	/**
	 * set lights
	 * 
	 * @param lights - list of lights
	 * @return this
	 */
	public Scene setlights(List<LightSource> lights) {
		this.lights = lights;
		return this;
	}

	/**
	 * set background
	 * 
	 * @param background the background to set
	 * @return this
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * set ambient light
	 * 
	 * @param ambientLight the ambientLight to set
	 * @return this
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	/**
	 * set geometries
	 * 
	 * @param geometries the geometries to set
	 * @return this
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}

	/**
	 * Arranges the geometries in a hierarchical way.
	 */
	public void buildHierarchy() {
		geometries = buildHierarchy(geometries);
	}

	/**
	 * Arranges the geometries in a hierarchical way - recursive.
	 * 
	 * @param currentGeometries - the current list to arrange.
	 * @return a list arranged hierarchically
	 */
	private Geometries buildHierarchy(Geometries currentGeometries) {
		double[] minMax = currentGeometries.getMinMaxHelper();
		double lengthX = minMax[3] - minMax[0];
		double lengthY = minMax[4] - minMax[1];
		double lengthZ = minMax[5] - minMax[2];

		if (lengthX >= lengthY && lengthX >= lengthZ)
			return extracted(currentGeometries, 'x');

		else if (lengthY >= lengthX && lengthY >= lengthZ)
			return extracted(currentGeometries, 'y');

		else
			return extracted(currentGeometries, 'z');
	}

	/**
	 * Arranges the geometries according to one dimension.
	 * 
	 * @param currentGeometries - the current list to arrange.
	 * @param longestAxis       - the axis to arrange according to him.
	 * @return a list arranged hierarchically
	 */
	private Geometries extracted(Geometries currentGeometries, char longestAxis) {
		Geometries firstGeometries = new Geometries();
		Geometries secondGeometries = new Geometries();

		double center = currentGeometries.getCenter(longestAxis);
		List<Intersectable> currentList = currentGeometries.getGeometriesList();
		for (Intersectable geometry : currentList) {
			if (geometry.getCenter(longestAxis) > center)
				firstGeometries.add(geometry);
			else
				secondGeometries.add(geometry);
		}

		if (firstGeometries.getGeometriesList().isEmpty())
			return new Geometries(secondGeometries);

		if (secondGeometries.getGeometriesList().isEmpty())
			return new Geometries(firstGeometries);

		return new Geometries(buildHierarchy(firstGeometries), buildHierarchy(secondGeometries));
	}
}

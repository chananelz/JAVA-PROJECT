package scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import elements.AmbientLight;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Point3D;

/**
 * SceneBuilder is a class that initialize a scene according to the XML fill we
 * chose to use Document Object Model so the architecture of this way to open
 * XML fill include create scene object
 * 
 * @author Shmulik & Chananel
 *
 */
public class SceneBuilder {
	// the scene and his strings form 
	private SceneDescriptor descriptor;
	private Scene scene;
	
	/**
	 * @return the scene
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * constructor that build the scene from scene descriptor.
	 */
	public SceneBuilder(String sceneName ,String fileName) {
		scene = new Scene(sceneName);
		descriptor = new SceneDescriptor(fileName);
		
		buildScene();
	}

	/**
	 * this method initialize the scene object from the XML file
	 * 
	 * @param fileName - the name of the fill in the workspace
	 * @return scene object
	 */
	private void buildScene() {
		scene.setAmbientLight(new AmbientLight(getColor(descriptor.ambientLightAttributes.get("Color")), 1))
			.setBackground(getColor(descriptor.sceneAttributes.get("backgroundColor")));

		scene.geometries.add(getSphere(descriptor.spheres.get(0)));
		
		List<Map<String, String>> triangles = descriptor.triangles;
		for (var triangle : triangles)
			scene.geometries.add(getTriangle(triangle));
	}

	/**
	 * parse string map to Triangle
	 * 
	 * @param map - the attributes of Triangle
	 * @return Triangle
	 */
	private Triangle getTriangle(Map<String, String> map) {
		return new Triangle(getPoint(map.get("p0")), 
				getPoint(map.get("p1")),
				getPoint(map.get("p2")));
	}

	/**
	 * parse string map to Sphere
	 * 
	 * @param map - the attributes of Sphere
	 * @return Sphere
	 */
	private Sphere getSphere(Map<String, String> map) {
		return new Sphere(getPoint(map.get("center")),
				Double.parseDouble(map.get("radius")));
	}

	/**
	 * get point from string
	 * 
	 * @param str - the string that represent point
	 * @return Point3D object
	 */
	private Point3D getPoint(String str) {
		ArrayList<Double> doubles = getDouble(str);

		return new Point3D(doubles.get(0), doubles.get(1), doubles.get(2));
	}

	/**
	 * get Color from string
	 * 
	 * @param str - the string that represent Color
	 * @return the color
	 */
	private Color getColor(String str) {
		ArrayList<Double> doubles = getDouble(str);

		return new Color(doubles.get(0), doubles.get(1), doubles.get(2));
	}

	/**
	 * get double from string (that in form "x1 x2 x3 x4")
	 * 
	 * @param str - the string that represent double
	 * @return List of numbers
	 */
	private ArrayList<Double> getDouble(String str) {
		ArrayList<Double> numList = new ArrayList<>();
		String[] numText = str.split(" ");
		
		for (int i = 0; i < numText.length; i++)
			numList.add(Double.parseDouble(numText[i]));
		
		return numList;
	}

}

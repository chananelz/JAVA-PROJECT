package scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * SceneDescriptor class represents the scene that store in XML file in
 * collections.
 * 
 * @author Shmulik & Chananel
 */

public class SceneDescriptor {
	/**
	 * map for scene attributes
	 */
	public Map<String, String> sceneAttributes = new HashMap<String, String>();
	/**
	 * map for ambient light attributes
	 */
	public Map<String, String> ambientLightAttributes = new HashMap<String, String>();
	/**
	 * list of maps for spheres attributes
	 */
	public List<Map<String, String>> spheres = new LinkedList<>();
	/**
	 * list of maps for triangles attributes
	 */
	public List<Map<String, String>> triangles = new LinkedList<>();

	/**
	 * constructor that put the strings from xml file in the collections
	 * 
	 * @param fileName - the xml file
	 */
	public SceneDescriptor(String fileName) {
		// Build the tools for accessing the file
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(fileName);

			Element root = doc.getDocumentElement();
			readFile(root);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * parse the file elements to strings and save them in collections
	 * 
	 * @param root - the root element of the document
	 */
	private void readFile(Element root) {
		// background-color
		sceneAttributes.put("backgroundColor", root.getAttribute("background-color"));

		// ambient-light
		Element alc = getElementByTagName(root, "ambient-light");
		ambientLightAttributes.put("Color", alc.getAttribute("color"));

		// geometries
		Map<String, String> sphereMap = new HashMap<String, String>();
		Map<String, String> triangleMap;
		Element geom = getElementByTagName(root, "geometries");

		// sphere
		Element sp = getElementByTagName(geom, "sphere");
		sphereMap.put("radius", sp.getAttribute("radius"));
		sphereMap.put("center", sp.getAttribute("center"));
		spheres.add(sphereMap);

		// triangles
		NodeList triangleList = geom.getElementsByTagName("triangle");
		for (int i = 0; i < triangleList.getLength(); i++) {
			Node t = triangleList.item(i);
			triangleMap = new HashMap<String, String>();
			if (t.getNodeType() == Node.ELEMENT_NODE) {
				Element tt = (Element) t;
				triangleMap.put("p0", tt.getAttribute("p0"));
				triangleMap.put("p1", tt.getAttribute("p1"));
				triangleMap.put("p2", tt.getAttribute("p2"));
			}
			triangles.add(triangleMap);
		}

	}

	/**
	 * get the first element according to tagName
	 * 
	 * @param parent  - the parent element
	 * @param tagName - the name of the element
	 * @return - the child element
	 */
	private Element getElementByTagName(Element parent, String tagName) {
		NodeList node = parent.getElementsByTagName(tagName);
		return (Element) node.item(0);
	}

}

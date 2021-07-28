package unittests.lights;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for Glossy Surfaces and Blurry Glass
 * 
 * @author shmulik & chananel
 */
public class GlossySurfacesBlurryGlassTests {

	private Scene scene = new Scene("Test scene") //
			.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

	private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(200, 200).setDistance(1000);

	/**
	 * image for Glossy Surfaces and Blurry Glass - Without them (for comparison)
	 */
	//@Test
	public void mP1Image() {
		scene.geometries.add( //
				new Plane(new Point3D(0, 0, -250), new Vector(0, 0, 1)) //
						.setEmission(new Color(120, 120, 120)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5)), //
				new Plane(new Point3D(0, -50, 0), new Vector(0, 1, 0)) //
						.setEmission(new Color(150, 150, 150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5)), //

				new Polygon(new Point3D(-125, 70, 0), new Point3D(-10, 70, 0), new Point3D(-10, -50, 0),
						new Point3D(-125, -50, 0)) //
								.setEmission(new Color(25, 25, 100)) //
								.setMaterial(new Material().setkD(0.2).setkS(0.2).setkT(0.6)), //
				new Polygon(new Point3D(-70, 10, -250), new Point3D(-100, 10, 0), new Point3D(-100, 0, 0),
						new Point3D(-70, 0, -250)) //
								.setEmission(new Color(java.awt.Color.DARK_GRAY)) //
								.setMaterial(new Material().setkD(0.5)), //
				new Sphere(new Point3D(-105, 25, -150), 4) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.05)), //
				new Sphere(new Point3D(-105, 18, -150), 8) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.05)), //
				new Sphere(new Point3D(-90, 22, -160), 3) //
						.setEmission(new Color(153, 76, 0)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.05)), //
				new Sphere(new Point3D(-90, 15, -160), 7) //
						.setEmission(new Color(java.awt.Color.GREEN)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.05)), //

				new Polygon(new Point3D(65, 100, -249), new Point3D(120, 100, -249), new Point3D(120, 20, -249),
						new Point3D(65, 20, -249)) //
								.setEmission(new Color(100, 100, 200)) //
								.setMaterial(new Material().setkR(1)), //
				new Polygon(new Point3D(60, 15, -250), new Point3D(100, 15, 40), new Point3D(100, -50, 40),
						new Point3D(60, -50, -250)) //
								.setEmission(new Color(java.awt.Color.DARK_GRAY)) //
								.setMaterial(new Material().setkD(0.5)), //
				new Sphere(new Point3D(110, 30, -150), 4) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.05)), //
				new Sphere(new Point3D(110, 23, -150), 8) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.05)) //
		);

		scene.lights.add(new DirectionalLight(new Color(125, 75, 0), new Vector(0, 0, -1)));
		scene.lights.add(new PointLight(new Color(225, 225, 0), new Point3D(0, 1000, 500)));
		scene.lights.add(new SpotLight(new Color(250, 150, 0), new Point3D(250, 500, 250), new Vector(-1, 1, 1)) //
				.setkL(0.00001).setkQ(0.00000001));

		ImageWriter imageWriter = new ImageWriter("mP1Image", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setTracer(new RayTracerBasic(scene));
		
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * image for Glossy Surfaces and Blurry Glass
	 */
	@Test
	public void mP1Image2() {
		scene.geometries.add( //
				new Plane(new Point3D(0, 0, -250), new Vector(0, 0, 1)) //
						.setEmission(new Color(120, 120, 120)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5)), //
				new Plane(new Point3D(0, -50, 0), new Vector(0, 1, 0)) //
						.setEmission(new Color(150, 150, 150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5)), //

				new Polygon(new Point3D(-125, 70, 0), new Point3D(-10, 70, 0), new Point3D(-10, -50, 0),
						new Point3D(-125, -50, 0)) //
								.setEmission(new Color(25, 25, 100)) //
								.setMaterial(new Material().setkT(1).setkB(0.01)), //
				new Polygon(new Point3D(-70, 10, -250), new Point3D(-100, 10, 0), new Point3D(-100, 0, 0),
						new Point3D(-70, 0, -250)) //
								.setEmission(new Color(java.awt.Color.DARK_GRAY)) //
								.setMaterial(new Material().setkD(0.5)), //
				new Sphere(new Point3D(-105, 25, -150), 4) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.05)), //
				new Sphere(new Point3D(-105, 18, -150), 8) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.05)), //
				new Sphere(new Point3D(-90, 22, -160), 3) //
						.setEmission(new Color(153, 76, 0)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.05)), //
				new Sphere(new Point3D(-90, 15, -160), 7) //
						.setEmission(new Color(java.awt.Color.GREEN)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.05)), //

				new Polygon(new Point3D(65, 100, -249), new Point3D(120, 100, -249), new Point3D(120, 20, -249),
						new Point3D(65, 20, -249)) //
								.setEmission(new Color(100, 100, 200)) //
								.setMaterial(new Material().setkR(1).setkG(0.05)), //
				new Polygon(new Point3D(60, 15, -250), new Point3D(100, 15, 40), new Point3D(100, -50, 40),
						new Point3D(60, -50, -250)) //
								.setEmission(new Color(java.awt.Color.DARK_GRAY)) //
								.setMaterial(new Material().setkD(0.5)), //
				new Sphere(new Point3D(110, 30, -150), 4) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.05)), //
				new Sphere(new Point3D(110, 23, -150), 8) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.05)) //
		);

		scene.lights.add(new DirectionalLight(new Color(125, 75, 0), new Vector(0, 0, -1)));
		scene.lights.add(new PointLight(new Color(225, 225, 0), new Point3D(0, 1000, 500)));
		scene.lights.add(new SpotLight(new Color(250, 150, 0), new Point3D(250, 500, 250), new Vector(-1, 1, 1)) //
				.setkL(0.00001).setkQ(0.00000001));

		ImageWriter imageWriter = new ImageWriter("mP1Image2", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setTracer(new RayTracerBasic(scene).setCbr(true))
				.setMultithreading(3).setDebugPrint();

		render.renderImage();
		render.writeToImage();
	}
}

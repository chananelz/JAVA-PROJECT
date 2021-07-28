package unittests.lights;

import java.util.Random;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * testing the acceleration
 * 
 * @author user1
 *
 */
public class Acceleration {

	/**
	 * house test
	 */
	@Test
	public void accelerationTest() {

		Scene scene = new Scene("Test scene") //
				.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		Camera camera = new Camera(new Point3D(0, -20, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(1000);

		scene.geometries.add(
				// SKY
				new Plane(new Point3D(50, -10, 700), new Point3D(0, 10, 500), new Point3D(10, -10, 700)) //
						.setEmission(new Color(0, 0, 64)),

				// FLOOR
				new Plane(new Point3D(50, 10, 700), new Point3D(0, 10, 500), new Point3D(10, 10, 700)) //
						.setEmission(new Color(java.awt.Color.DARK_GRAY)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(60)),

				// MOON
				new Sphere(new Point3D(20, -100, 400), 10) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setkS(0.5).setnShininess(30)),
				new Sphere(new Point3D(10, -100, 390), 9) //
						.setEmission(new Color(0, 0, 64)) //
						.setMaterial(new Material().setkS(0.5).setnShininess(30)),

				// ROOFS - FRONT
				new Polygon(new Point3D(-52, -9, -265), new Point3D(-18, -9, -265), new Point3D(-18, -25, -125),
						new Point3D(-52, -25, -125)) //
								.setEmission(new Color(153, 0, 0)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60)),
				// ROOFS - BACK
				new Polygon(new Point3D(-52, -25, -125), new Point3D(-18, -25, -125), new Point3D(-18, -9, 15),
						new Point3D(-52, -9, 15)) //
								.setEmission(new Color(153, 0, 0)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60)),

				// ROOF LEFT SIDE
				new Triangle(new Point3D(-50, -10, -250), new Point3D(-50, -25, -125), new Point3D(-50, -10, 0)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkS(0.8).setnShininess(60)),

				// ROOF RIGHT SIDE
				new Triangle(new Point3D(-20, -10, -250), new Point3D(-20, -25, -125), new Point3D(-20, -10, 0)) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkS(0.8).setnShininess(60)),

				// HOME
				new Polygon(new Point3D(-20, 100, -250), new Point3D(-50, 100, -250), new Point3D(-50, -10, -250),
						new Point3D(-20, -10, -250)) //
								.setEmission(new Color(166,166,166)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60)),
				new Polygon(new Point3D(-50, 100, 0), new Point3D(-50, 100, -250), new Point3D(-50, -10, -250),
						new Point3D(-50, -10, 0)) //
								.setEmission(new Color(166,166,166)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60)),
				new Polygon(new Point3D(-20, 100, 0), new Point3D(-20, 100, -250), new Point3D(-20, -10, -250),
						new Point3D(-20, -10, 0)) //
								.setEmission(new Color(166,166,166)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60)),

				// YADIT DOOR
				new Sphere(new Point3D(-38, 0, -250.0001), 0.5) //
						.setEmission(new Color(224, 224, 224)) //
						.setMaterial(new Material().setkS(0.8).setnShininess(60)),

				// DOOR
				new Polygon(new Point3D(-39, 8, -250.0001), new Point3D(-31, 8, -250.0001),
						new Point3D(-31, -7, -250.0001), new Point3D(-39, -7, -250.0001)) //
								.setEmission(new Color(51, 25, 0)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60)),

				// top left -door
				new Polygon(new Point3D(-38, -1, -250.1001), new Point3D(-35.5, -1, -250.1001),
						new Point3D(-35.5, -6, -250.1001), new Point3D(-38, -6, -250.1001)) //
								.setEmission(new Color(153, 76, 0)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60)),
				// bottom left door
				new Polygon(new Point3D(-38, 7, -250.1001), new Point3D(-35.5, 7, -250.1001),
						new Point3D(-35.5, 0, -250.1001), new Point3D(-38, 0, -250.1001)) //
								.setEmission(new Color(153, 76, 0)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60).setkT(1)),

				// top right door
				new Polygon(new Point3D(-35, -1, -250.1001), new Point3D(-32, -1, -250.1001),
						new Point3D(-32, -6, -250.1001), new Point3D(-35, -6, -250.1001)) //
								.setEmission(new Color(153, 76, 0)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60)),

				// bottom right door
				new Polygon(new Point3D(-35, 7, -250.1001), new Point3D(-32, 7, -250.1001),
						new Point3D(-32, 0, -250.1001), new Point3D(-35, 0, -250.1001)) //
								.setEmission(new Color(153, 76, 0)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60)),

				// WINDOWS
				new Polygon(new Point3D(-49, 0, -250.0001), new Point3D(-41, 0, -250.0001),
						new Point3D(-41, -7, -250.0001), new Point3D(-49, -7, -250.0001)) //
								.setEmission(new Color(51, 25, 0)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60)),
				// LEFT TOP WINDOW
				new Polygon(new Point3D(-48.5, -0.5, -250.0002), new Point3D(-45.2, -0.5, -250.0002),
						new Point3D(-45.2, -3.3, -250.0002), new Point3D(-48.5, -3.3, -250.0002)) //
								.setEmission(new Color(192, 192, 192)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60)),
				// LEFT BOTTOM WINDOW
				new Polygon(new Point3D(-48.5, -3.7, -250.0002), new Point3D(-45.2, -3.7, -250.0002),
						new Point3D(-45.2, -6.5, -250.0002), new Point3D(-48.5, -6.5, -250.0002)) //
								.setEmission(new Color(192, 192, 192)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60).setkT(0.8)),
				// right top window
				new Polygon(new Point3D(-44.8, -0.5, -250.0002), new Point3D(-41.5, -0.5, -250.0002),
						new Point3D(-41.5, -3.3, -250.0002), new Point3D(-44.8, -3.3, -250.0002)) //
								.setEmission(new Color(192, 192, 192)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60).setkT(0.8)),
				// LEFT BOTTOM WINDOW
				new Polygon(new Point3D(-44.8, -3.7, -250.0002), new Point3D(-41.5, -3.7, -250.0002),
						new Point3D(-41.5, -6.5, -250.0002), new Point3D(-44.8, -6.5, -250.0002)) //
								.setEmission(new Color(192, 192, 192)) //
								.setMaterial(new Material().setkS(0.8).setnShininess(60).setkT(0.8)),

				// MENORAT BERECHA
				new Triangle(new Point3D(80, 10, 0), new Point3D(80, -10, 0), new Point3D(83, -10, 0)) //
						.setEmission(new Color(java.awt.Color.BLACK)) //
						.setMaterial(new Material().setnShininess(60)),
				new Triangle(new Point3D(80, 10, 0), new Point3D(83, 10, 0), new Point3D(83, -10, 0)) //
						.setEmission(new Color(java.awt.Color.BLACK)) //
						.setMaterial(new Material().setnShininess(60)),
				new Sphere(new Point3D(81.5, -10, 0), 5) //
						.setEmission(new Color(java.awt.Color.YELLOW)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.5).setnShininess(30)),

				// POOL
				new Triangle(new Point3D(30, 5, -700), new Point3D(0, 10, 0), new Point3D(80, 10, 0)) //
						.setEmission(new Color(75, 209, 246)) //
						.setMaterial(new Material().setkD(0.4).setkS(0.1).setnShininess(60).setkR(0.5)),
				new Triangle(new Point3D(30, 5, -700), new Point3D(0, 10, 0), new Point3D(0, 5, -700)) //
						.setEmission(new Color(75, 209, 246)) //
						.setMaterial(new Material().setkD(0.4).setkS(0.1).setnShininess(60).setkR(0.5)),

				new Triangle(new Point3D(0, 5, -30), new Point3D(0, 10, 0), new Point3D(80, 10, 0)) //
						.setEmission(new Color(160, 82, 45)) //
						.setMaterial(new Material().setkS(0.8).setnShininess(60)),
				new Triangle(new Point3D(78, 5, -30), new Point3D(0, 5, -30), new Point3D(80, 10, 0)) //
						.setEmission(new Color(160, 82, 45)) //
						.setMaterial(new Material().setkS(0.8).setnShininess(60)),

				new Triangle(new Point3D(30.5, 4.75, -700), new Point3D(30, 5, -700), new Point3D(80, 10, 0)) //
						.setEmission(new Color(160, 82, 45)) //
						.setMaterial(new Material().setkS(0.8).setnShininess(60)),
				new Triangle(new Point3D(30.5, 4.75, -700), new Point3D(78, 5, -30), new Point3D(80, 10, 0)) //
						.setEmission(new Color(160, 82, 45)) //
						.setMaterial(new Material().setkS(0.8).setnShininess(60)),

				new Triangle(new Point3D(0, 5, -700), new Point3D(0, 4.75, -730), new Point3D(30, 5, -700)) //
						.setEmission(new Color(160, 82, 45)) //
						.setMaterial(new Material().setnShininess(60)),
				new Triangle(new Point3D(32, 4.9, -730), new Point3D(0, 4.75, -730), new Point3D(30.5, 5, -700)) //
						.setEmission(new Color(160, 82, 45)) //
						.setMaterial(new Material().setnShininess(60))
		);
		
		// stars
		Random rand = new Random(); // for the move
		double xMove = rand.nextDouble() * 150;
		double yMove = rand.nextDouble() * 100;
		double zMove = rand.nextDouble() * 100;

		for (int j = 0; j < 1000; j++) {
			scene.geometries.add(new Sphere(new Point3D(20 + xMove, -100 + yMove, 400 + zMove), 0.2) //
					.setEmission(new Color(java.awt.Color.white)),
					new Sphere(new Point3D(20 + xMove + 0.01, -100 + yMove + 0.01, 400 + zMove + 0.01), 0.2) //
					.setEmission(new Color(java.awt.Color.white)),
					new Sphere(new Point3D(20 + xMove - 0.01, -100 + yMove + 0.01, 400 + zMove + 0.01), 0.2) //
					.setEmission(new Color(java.awt.Color.white)),
					new Sphere(new Point3D(20 + xMove + 0.01, -100 + yMove - 0.01, 400 + zMove + 0.01), 0.2) //
					.setEmission(new Color(java.awt.Color.white)),
					new Sphere(new Point3D(20 + xMove + 0.01, -100 + yMove + 0.01, 400 + zMove - 0.01), 0.2) //
					.setEmission(new Color(java.awt.Color.white))
					); //

			xMove = rand.nextDouble() * 115;
			yMove = rand.nextDouble() * 80;
			zMove = rand.nextDouble() * 80;

			if (j % 4 == 0) {
				xMove = xMove * -1;
			}

			if (j % 4 == 1) {
				yMove = yMove * -1;
			}

			if (j % 4 == 2) {
				zMove = zMove * -1;
			}
			if (j % 4 == 3) {
				xMove = xMove * -1;
				yMove = yMove * -1;
				zMove = zMove * -1;
			}

		}

		Point3D flowerAdding = new Point3D(25, 0, -200);

		double flowerX = flowerAdding.getX();
		double flowerY = flowerAdding.getY();
		double flowerZ = flowerAdding.getZ();

		double oldFlowerX = flowerX;

		for (int i = 0; i < 4; i++) {
			flowerX = oldFlowerX;

			for (int j = 0; j < 4; j++) {
				scene.geometries.add(

						// FLOWERS
						new Triangle(new Point3D(-60 + flowerX, 10 + flowerY, -250 + flowerZ),
								new Point3D(-60 + flowerX, 5 + flowerY, -250 + flowerZ),
								new Point3D(-61 + flowerX, 5 + flowerY, -250 + flowerZ)) //
										.setEmission(new Color(34, 139, 34)) //
										.setMaterial(new Material().setnShininess(60)),
						new Triangle(new Point3D(-60 + flowerX, 10 + flowerY, -250 + flowerZ),
								new Point3D(-61 + flowerX, 10 + flowerY, -250 + flowerZ),
								new Point3D(-61 + flowerX, 5 + flowerY, -250 + flowerZ)) //
										.setEmission(new Color(34, 139, 34)) //
										.setMaterial(new Material().setkS(0.8).setnShininess(60)),

						new Sphere(new Point3D(-60.5 + flowerX, 5 + flowerY, -250 + flowerZ), 2) //
								.setEmission(new Color(java.awt.Color.BLACK)) //
								.setMaterial(new Material().setkD(0.2).setkS(0.5).setnShininess(30)),
						new Sphere(new Point3D(-60.75 + flowerX, 3.5 + flowerY, -250 + flowerZ), 1) //
								.setEmission(new Color(java.awt.Color.YELLOW)) //
								.setMaterial(new Material().setkD(0.2).setkS(0.5).setnShininess(30).setkT(1)),
						new Sphere(new Point3D(-59.5 + flowerX, 3.75 + flowerY, -250 + flowerZ), 1) //
								.setEmission(new Color(java.awt.Color.YELLOW)) //
								.setMaterial(new Material().setkD(0.2).setkS(0.5).setnShininess(30).setkT(1)),
						new Sphere(new Point3D(-59 + flowerX, 5 + flowerY, -250 + flowerZ), 1) //
								.setEmission(new Color(java.awt.Color.YELLOW)) //
								.setMaterial(new Material().setkD(0.2).setkS(0.5).setnShininess(30).setkT(1)),
						new Sphere(new Point3D(-59.5 + flowerX, 6.25 + flowerY, -250 + flowerZ), 1) //
								.setEmission(new Color(java.awt.Color.YELLOW)) //
								.setMaterial(new Material().setkD(0.2).setkS(0.5).setnShininess(30).setkT(1)),
						new Sphere(new Point3D(-60.8 + flowerX, 6.7 + flowerY, -250 + flowerZ), 1) //
								.setEmission(new Color(java.awt.Color.YELLOW)) //
								.setMaterial(new Material().setkD(0.2).setkS(0.5).setnShininess(30).setkT(1)),
						new Sphere(new Point3D(-62 + flowerX, 5.75 + flowerY, -250 + flowerZ), 1) //
								.setEmission(new Color(java.awt.Color.YELLOW)) //
								.setMaterial(new Material().setkD(0.2).setkS(0.5).setnShininess(30).setkT(1)),
						new Sphere(new Point3D(-62 + flowerX, 4.25 + flowerY, -250 + flowerZ), 1) //
								.setEmission(new Color(java.awt.Color.YELLOW)) //
								.setMaterial(new Material().setkD(0.2).setkS(0.5).setnShininess(30).setkT(1)));

				flowerX += 10;
			}
			flowerZ -= 40;
		}

		scene.lights.add(
				// WINDOW LIGHT
				new PointLight(new Color(java.awt.Color.WHITE), new Point3D(-55, -75, 150)));
		scene.lights.add(// PANAS Light
				new PointLight(new Color(java.awt.Color.YELLOW), new Point3D(81.5, -10, 0.001)));

		//scene.buildHierarchy();
		ImageWriter imageWriter = new ImageWriter("MiniProject2", 1800, 1800);
		Render render = new Render() //
				.setCamera(camera) //
				.setImageWriter(imageWriter) //
				.setTracer(new RayTracerBasic(scene).setCbr(true)) //
				.setMultithreading(3).setDebugPrint();

		render.renderImage();
		render.writeToImage();
	}

}
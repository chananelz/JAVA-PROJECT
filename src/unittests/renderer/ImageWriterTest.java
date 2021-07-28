package unittests.renderer;

import org.junit.Test;

import primitives.Color;
import renderer.ImageWriter;

/**
 * Test for ImageWriter class
 * 
 * @author shmulik
 */
public class ImageWriterTest {
	/**
	 * Test method for {@link renderer.ImageWriter#writeToImage()}. this test try to
	 * create simple image with imageWriter method
	 */
	@Test
	public void testWriteToImage() {
		ImageWriter imageWriter = new ImageWriter("FirstImage", 800, 500);
		
		Color blue = new Color(java.awt.Color.BLUE);
		Color red = new Color(java.awt.Color.RED);

		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();

		// Go over each pixel and give it a suitable color
		for (int j = 0; j < nX; j++) {
			for (int i = 0; i < nY; i++) {
				if (i % 50 == 0 || j % 50 == 0)
					imageWriter.writePixel(j, i, red);
				else
					imageWriter.writePixel(j, i, blue);
			}
		}

		imageWriter.writeToImage();
	}
}

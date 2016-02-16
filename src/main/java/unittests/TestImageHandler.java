package unittests;

import static org.junit.Assert.*;
import javafx.scene.image.Image;

import org.junit.Before;
import org.junit.Test;

import thecollector.model.ImageHandler;
import thecollector.utils.NetUtil;

/**
 * Test class for {@code ImageHandler}.
 * <p>
 * Expectations:
 * <ul>
 * <li>The Image Handler requires a MultiVerse ID.</li>
 * <li>The Image Handler can accept a "Background Loading" boolean.</li>
 * <li>The Image Handler returns an Image.</li>
 * <li>The URL formulated for the image is valid.</li>
 * </ul>
 * 
 * @author Ian Claridge
 *
 */
public class TestImageHandler {

	private int multiverseId01 = 0;
	private int multiverseId02 = 370812;
	private ImageHandler imageHandler01;
	private ImageHandler imageHandler02;
	
	@Before
	public void setUp() throws Exception {
		this.imageHandler01 = new ImageHandler(this.multiverseId01);
		this.imageHandler02 = new ImageHandler(this.multiverseId02, true);
	}
	
	@Test
	public void testCanCreateImages() {
		Image image01 = this.imageHandler01.createImage();
		assertNotNull(image01);
		Image image02 = this.imageHandler02.createImage();
		assertNotNull(image02);
	}

	@Test
	public void testImageLoadsSuccessfully() {
		Image image01 = this.imageHandler01.createImage();
		assertFalse(image01.isError());
		Image image02 = this.imageHandler02.createImage();
		assertFalse(image02.isError());
	}
	
	@Test
	public void testImageUrlIsValid() {
		
		assertEquals(200, NetUtil.getResponseCode(NetUtil.getConnection(this.imageHandler01.getUrl())));
		assertEquals(200, NetUtil.getResponseCode(NetUtil.getConnection(this.imageHandler02.getUrl())));
	}

}

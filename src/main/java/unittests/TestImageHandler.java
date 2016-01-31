package unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for ImageHandler.
 * 
 * Expectations:
 * o The Image Handler requires a MultiVerse ID.
 * o The Image Handler returns an Image.
 * o The URL formulated for the image is valid.
 * 
 * @author Ian Claridge
 *
 */
public class TestImageHandler {

	private int multiVerseId01 = 0;
	private int multiVerseId02 = 370812;
	private ImageHandler imageHandler01;
	private ImageHandler imageHandler02;
	
	@Before
	public void setUp() throws Exception {
		this.imageHandler01 = new ImageHandler(this.multiVerseId01);
		this.imageHandler02 = new ImageHandler(this.multiVerseId02, true);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}

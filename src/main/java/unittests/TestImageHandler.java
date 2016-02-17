package unittests;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.BeforeClass;
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
	
	private static Logger logger() { return Logger.getLogger(NetUtil.class.getName()); }
	
	/**
	 * This is a dummy JavaFX application, needed to run this unit test. This is because
	 * the JavaFX image objects created in the test need a JavaFX thread to be running.
	 */
	public static class UnitTestApp extends Application {
	    @Override
	    public void start(Stage primaryStage) throws Exception {
	        // Do nothing.
	    }
	}
	
	@BeforeClass
	/**
	 * Before the unit test class is instantiated, start a new thread that runs the
	 * above dummy JavaFX application.
	 */
	public static void initJFX() {
	    Thread javaFxUnitTestThread = new Thread("JavaFX Initialisation Thread") {
	        public void run() {
	            Application.launch(UnitTestApp.class, new String[0]);
	        }
	    };
	    javaFxUnitTestThread.setDaemon(true);
	    javaFxUnitTestThread.start();
	    
	    // Allow the JavaFX thread time to start up (half a second seems OK)
	    // before starting the unit tests.
	    try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			logger().log(Level.SEVERE, "Exception occurred!", e);
		}
	}
	
	private int multiverseId01 = 0;
	private int multiverseId02 = 370812;
	private ImageHandler imageHandler01;
	private ImageHandler imageHandler02;
	
	@Before
	public void setUp() throws Exception {
		this.imageHandler01 = new ImageHandler(this.multiverseId01, true);
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
		this.imageHandler01.createImage();
		assertEquals(200, NetUtil.getResponseCode(NetUtil.getConnection(this.imageHandler01.getUrl())));
		this.imageHandler02.createImage();
		assertEquals(200, NetUtil.getResponseCode(NetUtil.getConnection(this.imageHandler02.getUrl())));
	}

}

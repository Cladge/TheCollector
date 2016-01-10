package unittests;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import thecollector.model.Settings;
import thecollector.model.SettingsFile;

/**
 * Test Settings file.
 * 
 * Expectations:
 * 1. The file has a path.
 * 2. The file path (directory) can be created.
 * 3. The file path exists.
 * 4. The file path (directory) can be deleted.
 * 5. The file can be created.
 * 7. The file can be deleted.
 * 
 * @author Ian Claridge
 */
public class TestSettingsFile {
	
	private String path = "C:" + System.getProperty("file.separator") + "testSettingsFilePath";
	private SettingsFile settingsFile = new SettingsFile(path);
	private Properties testProperties;

	@Before
	public void setUp() throws Exception {
		this.path = "C:" + System.getProperty("file.separator") + "testSettingsFilePath";
		this.settingsFile = new SettingsFile(path);
    	this.testProperties = new Properties();
		this.testProperties.setProperty("keyTest1", "1");
		this.testProperties.setProperty("keyTest2", "2");
	}

	@Test
	public void testSettingsFilePath() {
		assertEquals(this.path, this.settingsFile.getSettingsPath());
		assertTrue(this.settingsFile.mkdir());
		assertTrue(this.settingsFile.pathExists());
		assertTrue(this.settingsFile.deldir());
	}

	@Test
	public void testSettingsFile() {
		assertEquals(this.path, this.settingsFile.getSettingsPath());
		assertTrue(this.settingsFile.mkdir());
		assertTrue(this.settingsFile.pathExists());
		assertTrue(this.settingsFile.create());
		assertTrue(this.settingsFile.delete());
		assertTrue(this.settingsFile.deldir());
	}
}

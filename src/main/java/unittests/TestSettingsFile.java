package unittests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import thecollector.model.SettingsFile;

/**
 * Test class for {@code SettingsFile}.
 * <p>
 * Expectations:
 * <ul>
 * <li>The settings (properties) file requires a name and path.</li>
 * <li>The settings (properties) file points to a valid properties file.</li>
 * <li>The file is created if it does not exist.</li>
 * <li>A single property can be retrieved.</li>
 * <li>All properties can be retrieved.</li>
 * <li>A single property can be set.</li>
 * <li>The settings (properties) file can be written.</li>
 * <li>The settings (properties) file can be deleted.</li>
 * <li>The settings (properties) path can be deleted.</li>
 * </ul>
 * 
 * @author Ian Claridge
 */
public class TestSettingsFile {
	
	private String settingsPath;
	private String settingsName;
	private SettingsFile settingsFile;

	@Before
	public void setUp() throws Exception {	
		this.settingsPath = "C:" + System.getProperty("file.separator") + "testSettingsFilePath";
		this.settingsName = "TestSettings.xml";
	}
	
	@After
	public void cleanUp() throws Exception {
		this.settingsFile.delete();
		this.settingsFile.deletePath();
	}

	@Test
	public void testSettingsFileCanCreate() {
		this.settingsFile = new SettingsFile(this.settingsPath, this.settingsName);
		File settingsFilePath = this.settingsFile.getFile();
		assertTrue(settingsFilePath.exists());
		assertTrue(this.settingsFile.settingsOK());
		assertTrue(this.settingsFile.delete());
		assertTrue(this.settingsFile.deletePath());
	}
	
	@Test
	public void testSettingsFileSetProperty() {
		this.settingsFile = new SettingsFile(this.settingsPath, this.settingsName);
		assertNull(this.settingsFile.setProperty("key1", "value1"));
		String newValue = (String) this.settingsFile.setProperty("key1", "value2");
		assertEquals("value1", newValue);
	}

	@Test
	public void testSettingsFileGetProperty() {
		this.settingsFile = new SettingsFile(this.settingsPath, this.settingsName);
		this.settingsFile.setProperty("key1", "value1");
		this.settingsFile.setProperty("key2", "value2");
		String value1 = this.settingsFile.getProperty("key1");
		String value2 = this.settingsFile.getProperty("key2");
		String value3 = this.settingsFile.getProperty("key3");
		assertEquals("value1", value1);
		assertEquals("value2", value2);
		assertEquals("Not Found", value3);	
	}

	@Test
	public void testSettingsFileGetProperties() {
		this.settingsFile = new SettingsFile(this.settingsPath, this.settingsName);
		this.settingsFile.setProperty("key1", "value1");
		this.settingsFile.setProperty("key2", "value2");
		List<String> keysList = this.settingsFile.getProperties();
		assertEquals(3, keysList.size());	// The 1st entry in the properties is the default key, hence 3 entries.
	}

	@Test
	public void testSettingsFileSave() {
		this.settingsFile = new SettingsFile(this.settingsPath, this.settingsName);
		this.settingsFile.setProperty("key1", "value1");
		this.settingsFile.setProperty("key2", "value2");
		assertTrue(this.settingsFile.save("Test comment"));
	}
}

package unittests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Hashtable;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import thecollector.model.Settings;
import thecollector.model.SettingsFile;

/**
 * Test Settings file.
 * 
 * Expectations:
 * o The settings (properties) file requires a name and path.
 * o The settings (properties) file points to a valid properties file.
 * o The file is created if it does not exist.
 * o A single property can be retrieved.
 * o All properties can be retrieved.
 * o A single property can be set.
 * o The settings (properties) file can be written.
 * o The settings (properties) file can be deleted.
 * o The settings (properties) path can be deleted.
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

	@Test
	public void testSettingsFileCanCreate() {
		this.settingsFile = new SettingsFile(this.settingsPath, this.settingsName);
		File settingsFilePath = this.settingsFile.getFile();
		assertTrue(settingsFilePath.exists());
		assertTrue(this.settingsFile.delete());
		assertTrue(this.settingsFile.deletePath());
	}
	
	@Test
	public void testSettingsFileSetProperty() {
		this.settingsFile = new SettingsFile(this.settingsPath, this.settingsName);
		assertNull(this.settingsFile.setProperty("key1", "value1"));
		String newValue = (String) this.settingsFile.setProperty("key1", "value2");
		assertEquals("value1", newValue);
		this.settingsFile.delete();
		this.settingsFile.deletePath();
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
		this.settingsFile.delete();
		this.settingsFile.deletePath();
	}

	/**
	@Test
	public void testSettingsFileGetProperties() {
		this.settingsFile = new SettingsFile(this.settingsPath, this.settingsName);
		this.settingsFile.setProperty("key1", "value1");
		this.settingsFile.setProperty("key2", "value2");
		Hashtable properties = this.settingsFile.getProperties();
		assertEquals(2, properties.size());
		String value1 = properties.get("key1");
		String value2 = properties.get("key2");
		assertEquals("value1", value1);
		assertEquals("value2", value2);
		this.settingsFile.delete();
		this.settingsFile.deletePath();
	}

	@Test
	public void testSettingsFileSave() {
		this.settingsFile = new SettingsFile(this.settingsPath, this.settingsName);
		this.settingsFile.setProperty("key1", "value1");
		this.settingsFile.setProperty("key2", "value2");
		assertTrue(this.settingsFile.save("Test comment"));
		this.settingsFile.delete();
		this.settingsFile.deletePath();
	}
	
	**/
}

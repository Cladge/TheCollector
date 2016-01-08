package unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestSettingsFile {
	
	String path = "C:" + System.getProperty("file.separator") + "testSettingsFilePath";
	FakeSettingsFile settingsFile = new FakeSettingsFile(path);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSettingsPath() {
		assertEquals(path, this.settingsFile.getSettingsPath());
	}

	@Test
	public void testSettingsPathExists() {
		assertFalse(this.settingsFile.pathExists());
	}

	@Test
	public void testMkDir() {
		assertTrue(this.settingsFile.mkdir());
	}
}

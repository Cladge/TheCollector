package unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import thecollector.model.SettingsManager;

/**
 * Test Settings Manager.
 * 
 * Expectations:
 * 1. On instantiation, the Settings Manager sets the application settings path.
 * 
 * @author Ian Claridge
 */
public class TestSettingsManager {
	
	private SettingsManager settingsManager = new SettingsManager();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSettingsPath() {
		assertTrue(settingsManager.settingsFilePathExists());
	}

}

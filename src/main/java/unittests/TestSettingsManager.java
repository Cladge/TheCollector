package unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import thecollector.model.SettingsManager;

public class TestSettingsManager {
	
	SettingsManager settingsManager = new SettingsManager();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSettingsPath() {
		assertTrue(settingsManager.settingsFileExists());
	}

}

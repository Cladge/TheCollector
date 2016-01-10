package thecollector.model;

import java.io.File;
import java.util.logging.Level;

import thecollector.utils.AbstractLogger;

/**
 * A class to represent a settings file.
 * 
 * @author Ian Claridge
 */
public class SettingsFile extends AbstractLogger {
	private String settingsPath;
	private File settingsFilePath;
	
	/**
	 * Constructor.
	 * 
	 * @param settingsPath - String
	 */
	public SettingsFile(String settingsPath) {
		this.settingsPath = settingsPath;
		try {
			this.settingsFilePath = new File(this.settingsPath);
		} catch (NullPointerException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
		}
	}
	
	/**
	 * Get the current settings path.
	 * 
	 * @return String - settings path
	 */
	public String getSettingsPath() {
		return this.settingsPath;
	}

	/**
	 * Shows if the settings path exists.
	 * 
	 * @return boolean - file exists/does not exist
	 */
	public boolean pathExists() {
		return this.settingsFilePath.exists();
	}
		
	/**
	 * Creates the directory for the given pathname.
	 * 
	 * @return boolean - true if and only if the directory was created; false otherwise
	 */
	public boolean mkdir() {
		return this.settingsFilePath.mkdir();
	}

	/**
	 * Delete the directory for the given pathname.
	 * 
	 * @return boolean - true if and only if the directory was deleted; false otherwise
	 */
	public boolean deldir() {
		return this.settingsFilePath.delete();
	}
}

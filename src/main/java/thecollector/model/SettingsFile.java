package thecollector.model;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import thecollector.utils.AbstractLogger;

/**
 * A class to represent a settings file.
 * 
 * @author Ian Claridge
 */
public class SettingsFile extends AbstractLogger {
	private String settingsPath;
	private String settingsName;
	private File settingsPropertyFile;
	
	/**
	 * Constructor.
	 * 
	 * @param settingsPath - String
	 */
	public SettingsFile(String settingsPath, String settingsName) {
		this.settingsPath = settingsPath;
		this.settingsName = settingsName;
		try {
			this.settingsPropertyFile = new File(this.settingsPath + System.getProperty("file.separator") + this.settingsName);
			if (!this.settingsPropertyFile.exists()) {
				// Create the directory and file if not exist.
				File settingsPathFile = new File(this.settingsPath);
				if (!settingsPathFile.exists()) {
					settingsPathFile.mkdir();
				}
				this.settingsPropertyFile.createNewFile();
			}
		} catch (NullPointerException | IOException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
		}
	}
	
	public File getFile() {
		return this.settingsPropertyFile;
	}
	
	/**
	 * Delete the properties file.
	 * 
	 * @return boolean - true if and only if the directory was deleted; false otherwise
	 */
	public boolean delete() {
		return this.settingsPropertyFile.delete();
	}

	/**
	 * Delete the properties file path.
	 * 
	 * @return boolean - true if and only if the directory was deleted; false otherwise
	 */
	public boolean deletePath() {
		boolean deleteSuccess = false;
		File settingsPathFile = new File(this.settingsPath);
		if (settingsPathFile.exists()) {
			deleteSuccess = settingsPathFile.delete();
		}
		
		return deleteSuccess;
	}
}

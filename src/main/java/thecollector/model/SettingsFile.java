package thecollector.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import thecollector.utils.AbstractLogger;
import thecollector.utils.FileUtil;

/**
 * A class to represent a settings file.
 * 
 * @author Ian Claridge
 */
public class SettingsFile extends AbstractLogger {
	private String settingsPath;
	private String settingsName;
	private File settingsPropertyFile;
	private Properties properties;
	
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
			
			// If the settings file does not exist create it - and the parent directory if needs be.
			if (!this.settingsPropertyFile.exists()) {
				File settingsPathFile = new File(this.settingsPath);
				if (!settingsPathFile.exists()) {
					settingsPathFile.mkdir();
				}
				this.settingsPropertyFile.createNewFile();
				this.properties = new Properties();
				this.setProperty("Version", Settings.APPLICATION_VERSION);
				this.save("Default settings");
			}
			
			// Read the settings into member properties object.
			this.readProperties();
			
		} catch (NullPointerException | IOException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
		}
	}
	
	/**
	 * Read in the properties from the settings file.
	 */
	private void readProperties() {
		this.properties = FileUtil.readPropertiesXmlFile(this.settingsPropertyFile);
	}
	
	/**
	 * Return the settings file.
	 * 
	 * @return File - the settings file.
	 */
	public File getFile() {
		return this.settingsPropertyFile;
	}
	
	/**
	 * Delete the properties file.
	 * 
	 * @return boolean - true if and only if the file was deleted; false otherwise
	 */
	public boolean delete() {
		return this.settingsPropertyFile.delete();
	}

	/**
	 * Delete the properties file directory.
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
	
	/**
	 * Get the value from the properties object associated with the passed key.
	 * 
	 * @param key - String
	 * 
	 * @return String - the value associated with the key, or "Not Found".
	 */
	public String getProperty(String key) {
		String defaultValue = "Not Found";
		return this.properties.getProperty(key, defaultValue);
	}
	
	/**
	 * Return a list of the properies keys.
	 * 
	 * @return List<String> - keys list
	 */
	public List<String> getProperties() {
		List<String> keysList = new ArrayList<String>();
		Enumeration<?> enumKeys = this.properties.propertyNames();
		while (enumKeys.hasMoreElements()) {
			keysList.add((String) enumKeys.nextElement());
		}
		
		return keysList;
	}
	
	/**
	 * Set a key, value pair in the properties object.
	 * 
	 * @param key - String
	 * @param value - String
	 * 
	 * @return Object - the previous value of the specified key, or null if it did not have one. 
	 */
	public Object setProperty(String key, String value) {
		return this.properties.setProperty(key, value);
	}
	
	/**
	 * Write out the properties to the settings file.
	 * 
	 * @param comment - String
	 * 
	 * @return boolean - true if save was successful, else false.
	 */
	public boolean save(String comment) {
		return FileUtil.writePropertiesXmlFile(this.settingsPropertyFile, this.properties, comment);
	}
}

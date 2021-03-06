package thecollector.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import thecollector.utils.FileUtil;
import thecollector.utils.LoggerUtil;

/**
 * A class to represent a settings file.
 * 
 * @author Ian Claridge
 */
public class SettingsFile {
	private String settingsPath;
	private String settingsName;
	private File settingsPropertyFile;
	private Properties properties;
	private boolean settingsOK;
	
	/**
	 * Constructor.
	 * 
	 * @param settingsPath - String
	 */
	public SettingsFile(String settingsPath, String settingsName) {
		this.settingsPath = settingsPath;
		this.settingsName = settingsName;
		this.settingsOK = true;
		
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
				this.save(Settings.SETTINGS_COMMENT);
			}
			
			// Read the settings into member properties object.
			this.readProperties();
			
		} catch (NullPointerException | IOException e) {
			LoggerUtil.logger(this).log(Level.SEVERE, "Exception occurred", e);
			this.settingsOK = false;
		}
	}
	
	/**
	 * Read in the properties from the settings file.
	 */
	protected void readProperties() {
		this.properties = FileUtil.readPropertiesXmlFile(this.settingsPropertyFile);
	}
	
	/**
	 * Is the settings file OK?
	 * 
	 * @return boolean - true, the file is OK; false, there was a problem
	 */
	public boolean settingsOK() {
		return this.settingsOK;
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

package thecollector.model;

public class SettingsManager {
	
	private SettingsFile settingsFilePath;
	
	public SettingsManager() {
		this.setSettingsDirectory();
	}

	/**
	 * Gets the system user settings directory. 
	 * 
	 * @throws IllegalStateException thrown if the user directory property cannot be found,
	 * or the directory cannot be created.
	 */
	private void setSettingsDirectory() {
		String userHome = System.getProperty("user.home");
		if(userHome == null) {
			throw new IllegalStateException("user.home==null");
		}
		this.settingsFilePath = new SettingsFile(userHome + System.getProperty("file.separator") + "." + Settings.APPLICATION_NAME);
		if(!this.settingsFilePath.fileExists()) {
			if(!settingsFilePath.mkdir()) {
				throw new IllegalStateException(this.settingsFilePath.toString());
			}
		}
	}

	/**
	 * Return the settings file path.
	 * 
	 * @return SettingsFile - the settings file path.
	 */
	public SettingsFile getSettingsFile() {
		return this.settingsFilePath;
	}

	/**
	 * Shows if the settings file exists.
	 * 
	 * @return boolean - true, the file exists; or false, it does not.
	 */
	public boolean settingsFileExists() {
		return this.getSettingsFile().fileExists();
	}

	/**
	 * Shows if the settings file path exists.
	 * 
	 * @return boolean - true, the file path exists; or false, it does not.
	 */
	public boolean settingsFilePathExists() {
		return this.getSettingsFile().pathExists();
	}
}

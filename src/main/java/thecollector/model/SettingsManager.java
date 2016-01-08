package thecollector.model;

public class SettingsManager {
	
	private SettingsFile settingsFile;
	
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
		this.settingsFile = new SettingsFile(userHome + System.getProperty("file.separator") + "." + Settings.APPLICATION_NAME);
		if(!this.settingsFile.fileExists()) {
			if(!settingsFile.mkdir()) {
				throw new IllegalStateException(this.settingsFile.toString());
			}
		}
	}

	/**
	 * Return the settings file.
	 * 
	 * @return SettingsFile - the settings file path.
	 */
	public SettingsFile getSettingsFile() {
		return this.settingsFile;
	}

	/**
	 * Shows if the settings file exists.
	 * 
	 * @return boolean - true, the file exists; or false, it does not.
	 */
	public boolean settingsFileExists() {
		return this.getSettingsFile().fileExists();
	}
}

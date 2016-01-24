package thecollector.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

/**
 * Helper class for reading and writing BufferedWriter files.
 */
public class FileUtil {
	
	private static Logger logger() { return Logger.getLogger(FileUtil.class.getName()); }
	
	/**
	 * The character set. UTF-8 works good for windows, mac and Umlaute.
	 */
	private static final Charset CHARSET = Charset.forName("UTF-8");

	/**
	 * Reads the specified file and returns the content as a String.
	 * 
	 * @param
	 * @return StringBuffer
	 * @throws IOException thrown if an I/O error occurs opening the file
	 */
	public static String readFile(File file) throws IOException {
		StringBuffer stringBuffer = new StringBuffer();

		BufferedReader reader = Files.newBufferedReader(file.toPath(), CHARSET);

		String line = null;
		while ((line = reader.readLine()) != null) {
			stringBuffer.append(line);
		}

		reader.close();

		return stringBuffer.toString();
	}

	/**
	 * Saves the content String to the specified file.
	 * 
	 * @param
	 * @param
	 * @throws IOException thrown if an I/O error occurs opening or creating the file
	 */
	public static void saveFile(String content, File file) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(file.toPath(), CHARSET, null);
		writer.write(content, 0, content.length());
		writer.close();
	}

	/**
	 * Saves the validity of the specified file.
	 * 
	 * @param file
	 */
	public static boolean checkFile(File fileToCheck) {
		// System.out.println("File path: " + fileToCheck.getAbsolutePath());
		boolean fileisValid = false;
		if (fileToCheck.exists()) {
			if (fileToCheck.isFile()) {
				if (fileToCheck.canRead()) {
					if (fileToCheck.isAbsolute()) {
						fileisValid = true;
					}
				}
			}
		}
		return fileisValid;
	}

	/**
	 * Gets the application directory in relation to the user home path.
	 * 
	 * @param String
	 * 
	 * @return String - the application name
	 */
	public static String getUserAppDirectory(String appName) {
		String userHome = System.getProperty("user.home");
		if(userHome == null) {
			throw new IllegalStateException("user.home==null");
		}
		String settingsPath = userHome + System.getProperty("file.separator") + "." + appName;
		
		return settingsPath;
	}
	
	/**
	 * Get the file path of a given file relative to the supplied class.
	 * 
	 * @param className - String
	 * @param filename - String
	 * 
	 * @return String - the file's path.
	 */
	public static String getResourcePath(String className, String filename) {
		String filePath = "";
		
		try {
			Class<?> classPathToCheck = Class.forName(className);
			ClassLoader loader = classPathToCheck.getClassLoader();
			URL pathURL = loader.getResource(filename);
			if (pathURL != null) {
				filePath = pathURL.getPath();	
			}
			
		} catch (ClassNotFoundException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
		}
		
		return filePath;
	}
	
	/**
	 * Create the given directory if it does not exist.
	 * 
	 * @param path - String
	 * 
	 * @return boolean - true, directory exists/was created OK; false, error occurred creating directory.
	 */
	public static boolean createDirectory(String path) {
		File dirPath = new File(path);
		boolean createSuccess = true;
		
		if (!dirPath.exists()) {
			createSuccess = dirPath.mkdir();
		}
		
		return createSuccess;
	}

	/**
	 * Method for writing out a Properties file.
	 * 
	 * @param
	 */
	public static void writePropertiesFile(File propFile) {
		try {
			Properties properties = new Properties();
			properties.setProperty("key", "value");

			FileOutputStream fileOut = new FileOutputStream(propFile);
			properties.store(fileOut, "Application Settings");
			fileOut.close();
		} catch (FileNotFoundException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
		} catch (IOException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
		}
	}

	/**
	 * Method for reading a Properties file.
	 * 
	 * @param
	 */
	public static Properties readPropertiesFile(File propFile) {
		Properties properties = new Properties();
		try {
			FileInputStream fileInput = new FileInputStream(propFile);
			properties.load(fileInput);
			fileInput.close();

			// Example of how to iterate the properties object:
			/*Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
			}*/
		} catch (FileNotFoundException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
		} catch (IOException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
		}
		
		return properties;
	}

	/**
	 * Method for writing out an XML Properties file.
	 * 
	 * @param
	 */
	public static boolean writePropertiesXmlFile(File propFile, Properties properties, String comment) {
		boolean writeSuccess = true;
		try {
			FileOutputStream fileOut = new FileOutputStream(propFile);
			properties.storeToXML(fileOut, comment);
			fileOut.close();
		} catch (IOException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
			writeSuccess = false;
		}
		
		return writeSuccess;
	}

	/**
	 * Method for reading an XML Properties file.
	 * 
	 * @param
	 */
	public static Properties readPropertiesXmlFile(File propFile) {
		Properties properties = new Properties();
		try {
			FileInputStream fileInput = new FileInputStream(propFile);
			properties.loadFromXML(fileInput);
			fileInput.close();

			// Example of how to iterate the properties object:
			/*Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
			}*/
		} catch (FileNotFoundException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
		} catch (IOException e) {
			logger().log(Level.SEVERE, "Exception occured", e);
		}
		
		return properties;
	}


	/**
	 * Test JSON simple encoding.
	 * 
	 * @param
	 */
	public static void testEncodeJSONObject() {
		JSONObject obj=new JSONObject();
		obj.put("name","foo");
		obj.put("num",new Integer(100));
		obj.put("balance",new Double(1000.21));
		obj.put("is_vip",new Boolean(true));
		obj.put("nickname",null);
		System.out.println("");
		System.out.print(obj);
	}


	/**
	 * Test JSON simple encoding with streaming.
	 * 
	 * @param
	 */
	public static void testEncodeJSONObjectStream() {
		JSONObject obj=new JSONObject();
		obj.put("name","foo");
		obj.put("num",new Integer(100));
		obj.put("balance",new Double(1000.21));
		obj.put("is_vip",new Boolean(true));
		obj.put("nickname",null);
		StringWriter out = new StringWriter();
		try {
			obj.writeJSONString(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger().log(Level.SEVERE, "Exception occured", e);
		}
		String jsonText = out.toString();
		System.out.println("");
		System.out.print(jsonText);
	}

}

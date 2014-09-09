package thecollector.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.Properties;

import org.json.simple.JSONObject;

/**
 * Helper class for reading and writing files.
 */
public class FileUtil {

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
		BufferedWriter writer = Files.newBufferedWriter(file.toPath(), CHARSET);
		writer.write(content, 0, content.length());
		writer.close();
	}

	/**
	 * Saves the validity of the specified file.
	 * 
	 * @param file
	 */
	public static void checkFile(File fileToCheck) {
		System.out.println("File path: " + fileToCheck.getAbsolutePath());
		//    System.out.println(fileToCheck.getCanonicalPath());
		System.out.println("Is absolute: " + fileToCheck.isAbsolute());
		System.out.println("Can read: " + fileToCheck.canRead());
		System.out.println("Is a file: " + fileToCheck.isFile());
		System.out.println("File exists: " + fileToCheck.exists());
		//    System.out.println(fileToCheck.getAbsoluteFile().exists());  
	}

	/**
	 * Gets the system user settings directory. 
	 * 
	 * @param
	 * @return File
	 * @throws IllegalStateException thrown if the user directory property cannot be found,
	 * or the directory cannot be created.
	 */
	public static File getSettingsDirectory(String appName) {
		String userHome = System.getProperty("user.home");
		if(userHome == null) {
			throw new IllegalStateException("user.home==null");
		}
		File home = new File(userHome);
		File settingsDirectory = new File(home, "." + appName);
		if(!settingsDirectory.exists()) {
			if(!settingsDirectory.mkdir()) {
				throw new IllegalStateException(settingsDirectory.toString());
			}
		}
		return settingsDirectory;
	}

	/**
	 * Test method for writing out a Properties file.
	 * 
	 * @param
	 */
	public static void writePropertiesFile(File propFile) {
		try {
			Properties properties = new Properties();
			properties.setProperty("favoriteAnimal", "marmot");
			properties.setProperty("favoriteContinent", "Antarctica");
			properties.setProperty("favoritePerson", "Nicole");

			FileOutputStream fileOut = new FileOutputStream(propFile);
			properties.store(fileOut, "Favorite Things");
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for reading a Properties file.
	 * 
	 * @param
	 */
	public static void readPropertiesFile(File propFile) {
		try {
			FileInputStream fileInput = new FileInputStream(propFile);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				System.out.println(key + ": " + value);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for writing out an XML Properties file.
	 * 
	 * @param
	 */
	public static void writePropertiesXmlFile(File propFile) {
		try {
			Properties properties = new Properties();
			properties.setProperty("favoriteAnimal", "marmot");
			properties.setProperty("favoriteContinent", "Antarctica");
			properties.setProperty("favoritePerson", "Nicole");

			FileOutputStream fileOut = new FileOutputStream(propFile);
			properties.storeToXML(fileOut, "Favorite Things");
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for reading an XML Properties file.
	 * 
	 * @param
	 */
	public static void readPropertiesXmlFile(File propFile) {
		try {
			FileInputStream fileInput = new FileInputStream(propFile);
			Properties properties = new Properties();
			properties.loadFromXML(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				System.out.println(key + ": " + value);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			e.printStackTrace();
		}
		String jsonText = out.toString();
		System.out.println("");
		System.out.print(jsonText);
	}

}

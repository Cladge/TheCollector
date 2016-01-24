package thecollector.utils;

import java.util.logging.Logger;

/**
 * A logging class that returns a handle to the java.util.logging.Logger if required.
 * <br><br>
 * All classes that require some sort of logging ability should use this class
 * and call the logger() method to get the handle.
 * <br><br>
 * The output from any logging method on any class that obtains a handle on the Logger this way
 * will always display the correct package, class and method for easy identification.
 * <br><br>
 * Example calls using <i>this</i> class:
 * <br><br>
 * <b>Using logging level and message:</b>
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;<i>LoggerUtil.logger(this).log(Level.[required level], "[String message]");</i>
 * <br><br>
 * <b>Using logging level, message and thrown exception:</b>
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;<i>LoggerUtil.logger(this).log(Level.[required level], "[String message]", [exception e]);</i>
 * <br><br>
 * For more examples of log methods, see the <a href="https://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html">documentation</a> on the java.util.logging.Logger class.
 * 
 * @author Ian Claridge
 *
 */
public class LoggerUtil {
	
	public static Logger logger(Object objectBeingLogged) { return Logger.getLogger(objectBeingLogged.getClass().getName()); }

}

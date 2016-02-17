package thecollector.model;

import javafx.scene.image.Image;

/**
 * A utility class for creating Java FX images from given URLs.
 * 
 * @author Ian Claridge
 */
public class ImageHandler {
	
	/**
	 * Example of image link with known multiverse ID (click image to see URL):
	 * (http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=370812&type=card)
	 */
	public static final String QUERY_DATA = "?multiverseid=%s&type=card";
	
	private int multiverseId;
	private boolean backgroundLoading;
	private Image image;
	private String imageUrl;
	
	/**
	 * Constructor. Set if background loading should be set for the image.
	 * 
	 * @param multiVerseId - int
	 * @param backgroundLoading - boolean
	 */
	public ImageHandler(int multiVerseId, boolean backgroundLoading) {
		this.multiverseId = multiVerseId;
		this.backgroundLoading = backgroundLoading;
	}
	
	/**
	 * Constructor. Pass the required MultiVerse ID.
	 * 
	 * @param multiVerseId - int
	 */
	public ImageHandler(int multiVerseId) {
		this(multiVerseId, false);
	}
	
	/**
	 * Create an image based on a URL that uses the Multiverse ID passed in the constructor as query data.
	 * 
	 * @return Image
	 */
	public Image createImage() {
		this.imageUrl = String.format(Settings.GATHERER_URL + ImageHandler.QUERY_DATA, this.multiverseId);
		this.image = new Image(this.imageUrl, this.backgroundLoading);
		return this.image;
	}
	
	/**
	 * Return the URL that was formulated from the Multiverse ID passed in the constructor.
	 * 
	 * @return String
	 */
	public String getUrl() {
		return this.imageUrl;
	}

}

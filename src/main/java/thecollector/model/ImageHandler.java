package thecollector.model;

import thecollector.utils.FileUtil;
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
	private static final String QUERY_DATA = "?multiverseid=%s&type=card";
	private static final String LOADING_IMAGE_URL = FileUtil.getResourceUrl("thecollector.model.ImageHandler", Settings.LOADING_IMAGE).toString();
	private static final String ERROR_IMAGE_URL = FileUtil.getResourceUrl("thecollector.model.ImageHandler", Settings.ERROR_IMAGE).toString();
	
	private int multiverseId;
	private boolean backgroundLoading;
	private Image image;
	private Image placeholderImage;
	private Image errorImage;
	private String imageUrl;
	private boolean imageLoaded;
	private boolean imageLoadingError;
	
	/**
	 * Constructor. Set if background loading should be set for the image.
	 * 
	 * @param multiverseId - int
	 * @param backgroundLoading - boolean
	 */
	public ImageHandler(int multiverseId, boolean backgroundLoading) {
		this.multiverseId = multiverseId;
		this.backgroundLoading = backgroundLoading;
		this.setImageLoaded(false);
		this.setImageError(false);
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
	 * If there was a prior loading error, re-create the image object.
	 * 
	 * @return Image
	 */
	public Image getImage() {
		// If image not created (or there was an error in the last loading attempt), create the image.
		if (this.image == null || this.imageHadErrorLoading()) {
			this.imageUrl = String.format(Settings.GATHERER_URL + ImageHandler.QUERY_DATA, this.multiverseId);
			this.image = new Image(this.imageUrl, this.backgroundLoading);
			this.setImageError(false);
		}
		
		return this.image;
	}
	
	/**
	 * Return a "place holder" image, which can be used while a main image is being loaded.
	 * 
	 * @return Image
	 */
	public Image getPlaceholderImage() {
		if (this.placeholderImage == null) {
			this.placeholderImage = new Image(ImageHandler.LOADING_IMAGE_URL);	
		}
		
		return this.placeholderImage;
	}

	/**
	 * Return an "error" image, which can be used if an image failed to load.
	 * 
	 * @return Image
	 */
	public Image getErrorImage() {
		if (this.errorImage == null) {
			this.errorImage = new Image(ImageHandler.ERROR_IMAGE_URL);
		}
		
		// Getting the error image means an error occurred. Set the flag accordingly.
		this.setImageError(true);
		
		return this.errorImage;
	}
	
	/**
	 * Return the URL that was formulated from the Multiverse ID passed in the constructor.
	 * 
	 * @return String
	 */
	public String getUrl() {
		return this.imageUrl;
	}
	
	/**
	 * Public setter to show that an image has loaded successfully.
	 * 
	 * @param imageLoaded - boolean
	 */
	public void setImageLoaded(boolean imageLoaded) {
		this.imageLoaded = imageLoaded;
	}
	
	/**
	 * Return if the image has been successfully loaded or not.
	 * 
	 * @return boolean
	 */
	public boolean imageHasLoaded() {
		return this.imageLoaded;
	}

	/**
	 * Private setter to show that an image had an error when loading.
	 * 
	 * @param imageError - boolean
	 */
	private void setImageError(boolean imageError) {
		this.imageLoadingError = imageError;
	}

	/**
	 * Return if the image had a loading error or not.
	 * 
	 * @return boolean
	 */
	private boolean imageHadErrorLoading() {
		return this.imageLoadingError;
	}

}

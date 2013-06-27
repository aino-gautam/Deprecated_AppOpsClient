package in.appops.client.common.config.field;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Image;

public class ImageField extends BaseField {

	private Image image ;
	
	public ImageField(){
		image = new Image();
	}

	/******************************** BaseField Overriden methods ****************************************/
	/**
	 * creates the field UI
	 */
	@Override
	public void create() {
		
	  getBasePanel().add(image,DockPanel.CENTER);
	  
	}

	@Override
	public void configure() {
		if(getImageBlobId()!=null)
			image.setUrl(getImageBlobId());
		if(getImageTitle()!=null)
			image.setTitle(getImageTitle());
		if(getBaseFieldPrimCss()!=null)
			image.setStylePrimaryName(getBaseFieldPrimCss());		
		if(getBaseFieldCss()!=null)
			image.setStylePrimaryName(getBaseFieldCss());
	}
	
	/*********************** Configuration methods *****************************/
	
	/**
	 * Method return the image blobId.
	 * @return
	 */
	private String getImageBlobId() {
		String blobId = null;
		if (getConfigurationValue(ImageFieldConstant.IMGFD_BLOBID) != null) {
			blobId = getConfigurationValue(ImageFieldConstant.IMGFD_BLOBID).toString();
		}
		return blobId;
	}
	
	/**
	 * Method return the image title.
	 * @return
	 */
	private String getImageTitle() {
		String title = null;
		if (getConfigurationValue(ImageFieldConstant.IMGFD_TITLE) != null) {
			title = getConfigurationValue(ImageFieldConstant.IMGFD_TITLE).toString();
		}
		return title;
	}
	
	/***************************   ***********************************************/
	
	public interface ImageFieldConstant  extends BaseFieldConstant{
		
		/**  Specifies the title to be displayed on the image ****/
		public static final String IMGFD_TITLE = "title";
		
		/**  Specifies the image blobId ****/
		public static final String IMGFD_BLOBID = "blobId";
		
	}

}

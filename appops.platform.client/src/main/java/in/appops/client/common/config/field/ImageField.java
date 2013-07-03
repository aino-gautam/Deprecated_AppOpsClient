package in.appops.client.common.config.field;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Image;

/**
* Field class to represent a {@link ImageField} .
* @author pallavi@ensarm.com
*
*<p>
<h3>Configuration</h3>
<a href="ImageField.ImageFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>
ImageField imageField = new ImageField();<br>
Configuration configuration = new Configuration();<br>
configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, "images/test2.jpg");<br>
configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,"showcaseImage");<br>
configuration.setPropertyByName(ButtonFieldConstant.BTNFD_TITLE, "Configurable Image");<br>
imageField.setConfiguration(conf);<br>
imageField.configure();<br>
imageField.create();<br>

</p>*/
public class ImageField extends BaseField implements ClickHandler{

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
	  image.addClickHandler(this);
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
	 * Method return the event which will be used when user clicks on image..  
	 * @return
	 */
	private Integer getImageClickEvent() {
		Integer eventType = null;
		if (getConfigurationValue(ImageFieldConstant.IMGFD_CLICK_EVENT) != null) {
			eventType = (Integer) getConfigurationValue(ImageFieldConstant.IMGFD_CLICK_EVENT);
		}
		return eventType;
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
	
	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(image)){
			FieldEvent fieldEvent = new FieldEvent();
			fieldEvent.setEventType(getImageClickEvent());
			AppUtils.EVENT_BUS.fireEvent(fieldEvent);
		}
		
	}
	
	/***************************   ***********************************************/
	
	public interface ImageFieldConstant  extends BaseFieldConstant{
		
		/**  Specifies the title to be displayed on the image ****/
		public static final String IMGFD_TITLE = "title";
		
		/**  Specifies the image blobId ****/
		public static final String IMGFD_BLOBID = "blobId";
		
		/** Specifies the event that will be fired on image click **/
		public static final String IMGFD_CLICK_EVENT = "imageClickEvent";
		
	}

	

}

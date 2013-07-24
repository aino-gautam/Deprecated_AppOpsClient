package in.appops.client.common.config.field;

import java.util.logging.Level;
import java.util.logging.Logger;

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
	private Logger logger = Logger.getLogger(getClass().getName());

	public ImageField(){
		image = new Image();
	}

	/******************************** ****************************************/
	/**
	 * creates the field UI
	 */
	@Override
	public void create() {
		try {
			logger.log(Level.INFO, "[ImageField] ::In create method ");
			if(getImageClickEvent()!=0)
				image.addClickHandler(this);
			getBasePanel().add(image,DockPanel.CENTER);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ImageField] ::Exception in create method :"+e);
		}
	  
	}

	/**
	 * Configure the image field.
	 */
	@Override
	public void configure() {
		try {
			logger.log(Level.INFO, "[ImageField] ::In configure method ");
			if(getImageBlobId()!=null)
				image.setUrl(getImageBlobId());
			if(getImageTitle()!=null)
				image.setTitle(getImageTitle());
			if(getBaseFieldPrimCss()!=null)
				image.setStylePrimaryName(getBaseFieldPrimCss());		
			if(getBaseFieldCss()!=null)
				image.addStyleName(getBaseFieldCss());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ImageField] ::Exception in configure method :"+e);
		}
	}
	
	/*********************** *****************************/
	
	/**
	 * Method return the image blobId.
	 * @return
	 */
	private String getImageBlobId() {
		String blobId = null;
		try {
			logger.log(Level.INFO, "[ImageField] ::In getImageBlobId method ");
			if (getConfigurationValue(ImageFieldConstant.IMGFD_BLOBID) != null) {
				blobId = getConfigurationValue(ImageFieldConstant.IMGFD_BLOBID).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ImageField] ::Exception in getImageBlobId method :"+e);
		}
		return blobId;
	}
	
	
	/**
	 * Method return the event which will be used when user clicks on image..  
	 * @return
	 */
	private Integer getImageClickEvent() {
		Integer eventType = 0;
		try {
			logger.log(Level.INFO, "[ImageField] ::In getImageClickEvent method ");
			if (getConfigurationValue(ImageFieldConstant.IMGFD_CLICK_EVENT) != null) {
				eventType = (Integer) getConfigurationValue(ImageFieldConstant.IMGFD_CLICK_EVENT);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ImageField] ::Exception in getImageClickEvent method :"+e);
		}
		return eventType;
	}
	
	/**
	 * Method return the image title.
	 * @return
	 */
	private String getImageTitle() {
		String title = null;
		try {
			logger.log(Level.INFO, "[ImageField] ::In getImageTitle method ");
			if (getConfigurationValue(ImageFieldConstant.IMGFD_TITLE) != null) {
				title = getConfigurationValue(ImageFieldConstant.IMGFD_TITLE).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ImageField] ::Exception in getImageTitle method :"+e);
		}
		return title;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		try {
			logger.log(Level.INFO, "[ImageField] ::In onClick method ");
			if(event.getSource().equals(image)){
				FieldEvent fieldEvent = new FieldEvent();
				fieldEvent.setEventType(getImageClickEvent());
				AppUtils.EVENT_BUS.fireEvent(fieldEvent);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ImageField] ::Exception in onClick method :"+e);
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
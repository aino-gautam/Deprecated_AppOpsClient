package in.appops.client.common.config.field;

import in.appops.client.common.config.model.PropertyModel;
import in.appops.client.common.event.FieldEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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
configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,"showcaseImage");<br>
configuration.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Configurable Image");<br>
imageField.setConfiguration(conf);<br>
imageField.configure();<br>
imageField.create();<br>

</p>*/
public class ImageField extends BaseField implements ClickHandler{

	private Image image ;
	private HandlerRegistration clickHandler = null ;
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
		super.create();
		try {
			logger.log(Level.INFO, "[ImageField] ::In create method ");
			clickHandler = image.addClickHandler(this);
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
		super.configure();
		logger.log(Level.INFO, "[ImageField] ::In configure method ");
		try {
			super.configure();
			image.setVisible(isFieldVisible());

			/**
			 * TODO Blob id should be removed and the image/image blob has to be passed as default value
			 */
			if(getImageBlobId()!=null)
				image.setUrl(getImageBlobId());
			
			if(getDefaultValue() != null && getBindProperty() == null) {
				setValue(getDefaultValue());
			} else if(getBindProperty() != null && !getBindProperty().toString().equals("")){
				Object value = ((PropertyModel)model).getPropertyValue(getBindProperty());
				if(value != null || (value == null && getDefaultValue() != null)) {
					setValue(value);
				}
			}
			
			if(getImageTitle()!=null)
				image.setTitle(getImageTitle());
			if(getBaseFieldPrimCss()!=null)
				image.setStylePrimaryName(getBaseFieldPrimCss());
			if(getBaseFieldDependentCss()!=null)
				image.addStyleName(getBaseFieldDependentCss());

			if (getBasePanelPrimCss() != null)
				getBasePanel().setStylePrimaryName(getBasePanelPrimCss());
			if (getBasePanelDependentCss() != null)
				getBasePanel().addStyleName(getBasePanelDependentCss());

		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ImageField] ::Exception in configure method :"+e);
		}
	}

	/**
	 * Method removed registered handlers from field
	 */
	@Override
	public void removeRegisteredHandlers() {

		if(clickHandler!=null)
			clickHandler.removeHandler();

	}

	/**
	 * Overriden method from BaseField sets the value to image.
	 */
	@Override
	public void setValue(Object value) {
		try {
			super.setValue(value);
			setFieldValue(value.toString());
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ImageField]::Exception In setValue  method :"+e);
		}
	}

	/**
	 * Overriden method from BaseField returns the converted field value.
	 */
	@Override
	public Object getValue() {
		String value = null;
		try {
			value =  getFieldValue();
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ImageField]::Exception In getValue  method :"+e);
		}
		return value;
	}

	/**
	 * Overriden method from BaseField returns the url of image.
	 */
	@Override
	public String getFieldValue() {
		logger.log(Level.INFO, "[ImageField] ::In getFieldValue method ");
		String value = null;
		try {
			value =  image.getUrl();
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ImageField]::Exception In getFieldValue  method :"+e);
		}
		return value;
	}

	/**
	 * Overriden method from BaseField sets the url to image.
	 */
	@Override
	public void setFieldValue(String fieldValue) {
		logger.log(Level.INFO, "[ImageField] ::In setFieldValue method ");
		try {
			image.setUrl(fieldValue);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ImageField]::Exception In setFieldValue  method :"+e);
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
			if (viewConfiguration.getConfigurationValue(ImageFieldConstant.IMGFD_BLOBID) != null) {
				blobId = viewConfiguration.getConfigurationValue(ImageFieldConstant.IMGFD_BLOBID).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ImageField] ::Exception in getImageBlobId method :"+e);
		}
		return blobId;
	}


	/**
	 * Method return the image title.
	 * @return
	 */
	private String getImageTitle() {
		String title = null;
		try {
			logger.log(Level.INFO, "[ImageField] ::In getImageTitle method ");
			if (viewConfiguration.getConfigurationValue(ImageFieldConstant.IMGFD_TITLE) != null) {
				title = viewConfiguration.getConfigurationValue(ImageFieldConstant.IMGFD_TITLE).toString();
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
				fieldEvent.setEventType(FieldEvent.CLICKED);
				fieldEvent.setEventSource(this);
				fireLocalEvent(fieldEvent);
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

	}



}
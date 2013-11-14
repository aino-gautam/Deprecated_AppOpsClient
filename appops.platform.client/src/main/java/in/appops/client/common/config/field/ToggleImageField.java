package in.appops.client.common.config.field;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;

/**
* Field class to represent a {@link ToggleImageField} .
* @author pallavi@ensarm.com
*<p>
<h3>Configuration</h3>
<a href="ToggleImageField.ToggleImageFieldConstant.html">Available configurations</a>
</p>
<p>
<h3>Example</h3>
ToggleImageField toggleImageField = new ToggleImageField();<br>
Configuration conf = new Configuration();<br>
conf.setPropertyByName(ToggleImageFieldConstant.TIMGFD_UPSTATE_URL, "images/pinkHeart.jpg");<br>
conf.setPropertyByName(ToggleImageFieldConstant.TIMGFD_UPSTATE_TITLE,"unlike");<br>
conf.setPropertyByName(ToggleImageFieldConstant.TIMGFD_DWNSTATE_URL, "images/grayHeart.jpg");<br>
conf.setPropertyByName(ToggleImageFieldConstant.TIMGFD_DWNSTATE_TITLE,"like");<br>
conf.setPropertyByName(ToggleImageFieldConstant.TIMGFD_STATEIMG_PCLS,"toggleImageCss");<br>
toggleImageField.setConfiguration(conf);<br>
toggleImageField.configure();<br>
toggleImageField.create();<br>
</p>*/
 
@SuppressWarnings("rawtypes")
public class ToggleImageField extends BaseField implements ValueChangeHandler{

	private ToggleButton toggleButton ;
	private Image upStateImage;
	private Image downStateImage;
	private Logger logger = Logger.getLogger(getClass().getName());

	public ToggleImageField(){
		
	}

	/******************************** ****************************************/
	/**
	 * creates the field UI
	 */
	@Override
	public void create() {
		try {
			logger.log(Level.INFO, "[ToggleImageField] ::In create method ");
			toggleButton.addValueChangeHandler(this);
			getBasePanel().add(toggleButton,DockPanel.CENTER);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ToggleImageField] ::Exception in create method :"+e);
		}
	}

	/**
	 * Configure the image field.
	 */
	@Override
	public void configure() {
		try {
			logger.log(Level.INFO, "[ToggleImageField] ::In configure method ");
			
			upStateImage = getStateImage(getUpStateImageUrl());
			downStateImage = getStateImage(getDwnStateImageUrl());
			toggleButton = new ToggleButton(upStateImage, downStateImage);
			
			setToggleImageTitle(getUpStateImageTitle());
			
			if(getBaseFieldPrimCss()!=null)
				toggleButton.setStylePrimaryName(getBaseFieldPrimCss());		
			if(getBaseFieldDependentCss()!=null)
				toggleButton.addStyleName(getBaseFieldDependentCss());
			
			if (getBasePanelPrimCss() != null)
				getBasePanel().setStylePrimaryName(getBasePanelPrimCss());
			if (getBasePanelDependentCss() != null)
				getBasePanel().addStyleName(getBasePanelDependentCss());
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ToggleImageField] ::Exception in configure method :"+e);
		}
	}
	
	/**
	 * Method sets the title to toggle image .
	 * @param title
	 */
	private void setToggleImageTitle(String title){
		if(title != null) {
			toggleButton.setTitle(title);
		}
	}
	
	/**
	 * Method creates image, get styles from configuration and set it to image and return that image instance.
	 * @param upStateImageUrl.
	 * @return stateImage
	 */
	private Image getStateImage(String upStateImageUrl) {
		
		Image stateImage = new Image();
		stateImage.setUrl(upStateImageUrl);
		if (getStateImagePrimaryCss()!= null)
			stateImage.setStylePrimaryName(getStateImagePrimaryCss());
		
		if (getStateImageDependentCss()!= null)
			stateImage.setStylePrimaryName(getStateImageDependentCss());
		
		return stateImage;
	}

	/**
	 * Overriden method from BaseField sets the value to toggleImageField, if value is true then it press the toggle button 
	 * also fires valueChangeEvent.
	 */
	@Override
	public void setValue(Object value) {
		try {
			logger.log(Level.INFO, "[ToggleImageField] ::In setValue method ");
			super.setValue(value);
			
			boolean isUp =  Boolean.valueOf(value.toString());
			toggleButton.setValue(!isUp, true);

		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ToggleImageField]::Exception In setValue  method :"+e);
		}
	}
	
	/**
	 * Overriden method from BaseField returns whether toggleImageField is currently down or not.
	 */
	@Override
	public Object getValue() {
		logger.log(Level.INFO, "[ToggleImageField] ::In getValue method ");
		boolean isUp = false;
		try {
			isUp = !toggleButton.getValue();
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[ToggleImageField]::Exception In getValue  method :"+e);
		}
		return isUp;
	}
	
	/*********************** *****************************/
	
	/**
	 * Method gets the up-state image url from configuration.
	 * @return up-state image url.
	 */
	private String getUpStateImageUrl() {
		String url = null;
		try {
			logger.log(Level.INFO, "[ToggleImageField] ::In getUpStateImageUrl method ");
			if (getConfigurationValue(ToggleImageFieldConstant.TIMGFD_UPSTATE_URL) != null) {
				url = getConfigurationValue(ToggleImageFieldConstant.TIMGFD_UPSTATE_URL).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ToggleImageField] ::Exception in getUpStateImageUrl method :"+e);
		}
		return url;
	}
	
	/**
	 * Method gets the down-state image url from configuration.
	 * @return down-state image url.
	 */
	private String getDwnStateImageUrl() {
		String url = null;
		try {
			logger.log(Level.INFO, "[ToggleImageField] ::In getDwnStateImageUrl method ");
			if (getConfigurationValue(ToggleImageFieldConstant.TIMGFD_DWNSTATE_URL) != null) {
				url = getConfigurationValue(ToggleImageFieldConstant.TIMGFD_DWNSTATE_URL).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ToggleImageField] ::Exception in getDwnStateImageUrl method :"+e);
		}
		return url;
	}
	
	/**
	 * Method gets the up-state image title from configuration.
	 * @return down-state image title.
	 */
	private String getUpStateImageTitle() {
		String title = null;
		try {
			logger.log(Level.INFO, "[ToggleImageField] ::In getUpStateImageTitle method ");
			if (getConfigurationValue(ToggleImageFieldConstant.TIMGFD_UPSTATE_TITLE) != null) {
				title = getConfigurationValue(ToggleImageFieldConstant.TIMGFD_UPSTATE_TITLE).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ToggleImageField] ::Exception in getUpStateImageTitle method :"+e);
		}
		return title;
	}
	
	/**
	 * Method gets the down-state image title from configuration.
	 * @return down-state image title.
	 */
	private String getDwnStateImageTitle() {
		String title = null;
		try {
			logger.log(Level.INFO, "[ToggleImageField] ::In getDwnStateImageTitle method ");
			if (getConfigurationValue(ToggleImageFieldConstant.TIMGFD_DWNSTATE_TITLE) != null) {
				title = getConfigurationValue(ToggleImageFieldConstant.TIMGFD_DWNSTATE_TITLE).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ToggleImageField] ::Exception in getDwnStateImageTitle method :"+e);
		}
		return title;
	}
	
	/**
	 * Method gets the state image primary css from configuration.
	 * @return state image primary css.
	 */
	private String getStateImagePrimaryCss() {
		String pcls = null;
		try {
			logger.log(Level.INFO, "[ToggleImageField] ::In getStateImagePrimaryCss method ");
			if (getConfigurationValue(ToggleImageFieldConstant.TIMGFD_STATEIMG_PCLS) != null) {
				pcls = getConfigurationValue(ToggleImageFieldConstant.TIMGFD_STATEIMG_PCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ToggleImageField] ::Exception in getStateImagePrimaryCss method :"+e);
		}
		return pcls;
	}
	
	
	/**
	 * Method gets the state image dependent css from configuration.
	 * @return state image dependent css.
	 */
	private String getStateImageDependentCss() {
		String dcls = null;
		try {
			logger.log(Level.INFO, "[ToggleImageField] ::In getStateImageDependentCss method ");
			if (getConfigurationValue(ToggleImageFieldConstant.TIMGFD_STATEIMG_DCLS) != null) {
				dcls = getConfigurationValue(ToggleImageFieldConstant.TIMGFD_STATEIMG_DCLS).toString();
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ToggleImageField] ::Exception in getStateImageDependentCss method :"+e);
		}
		return dcls;
	}
	
	/******************************************************************/
	/**
	 * Method sets the title to image and fires VALUECHANGED event.
	 */
	@Override
	public void onValueChange(ValueChangeEvent event) {
		try {
			logger.log(Level.INFO, "[ToggleImageField] ::In onValueChange method ");
			if(event.getSource().equals(toggleButton)){
				if(toggleButton.isDown()){
					setToggleImageTitle(getDwnStateImageTitle());
				}else{
					setToggleImageTitle(getUpStateImageTitle());
				}
				
				FieldEvent fieldEvent = new FieldEvent();
				fieldEvent.setEventType(fieldEvent.VALUECHANGED);
				fieldEvent.setEventData(!toggleButton.isDown());
				fieldEvent.setEventSource(this);
				AppUtils.EVENT_BUS.fireEvent(fieldEvent);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[ToggleImageField] ::Exception in onValueChange method :"+e);
		}
	}
	
		
	/***************************   ***********************************************/
	
	public interface ToggleImageFieldConstant  extends BaseFieldConstant{
		
		/**  Specifies the up-state image url. ****/
		public static final String TIMGFD_UPSTATE_URL = "upStateUrl";
		
		/**  Specifies the down-state image url. ****/
		public static final String TIMGFD_DWNSTATE_URL = "downStateUrl";
		
		/**  Specifies up-state image title ****/
		public static final String TIMGFD_UPSTATE_TITLE = "upStateTitle";
		
		/**  Specifies down-state image title ****/
		public static final String TIMGFD_DWNSTATE_TITLE = "downStateTitle";
		
		/**  Specifies the primary css style to be used for state image field. ****/
		public static final String TIMGFD_STATEIMG_PCLS = "stateImgPrimaryCss";
		
		/**  Specifies the dependent css style to be used for state image field. ****/
		public static final String TIMGFD_STATEIMG_DCLS = "stateImgDependentCss";
		
	}
	
}

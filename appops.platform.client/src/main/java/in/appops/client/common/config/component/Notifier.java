package in.appops.client.common.config.component;

import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.platform.core.shared.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class Notifier extends Composite{
	
	private ImageField imageFld;
	private LabelField messageLbl;
	private HorizontalPanel basePanel;
	private Configuration configuration;
	
	public static String DEFAULT_NOTIFICATION_IMG = "images/defaultLoader.gif";
	public static String DEFAULT_NOTIFICATION_MESSAGE = "Loading...";
	public static String DEFAULT_NOTIFICATION_IMGCSS = "defaultNotificationIcon";
	public static String DEFAULT_NOTIFICATION_MSGCSS = "defaultNotificationMessage";
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	
	public Notifier(Configuration configuration){
		basePanel = new HorizontalPanel();
		this.configuration = configuration;
		if(getNotifierBasePanelCss()!=null)
			basePanel.setStylePrimaryName(getNotifierBasePanelCss());
		initWidget(basePanel);
	}
	
	/**
	 * Creates an icon only 
	 */
	public void createImageNotifier(){    
		try {
			basePanel.clear();
			imageFld = new ImageField();
			imageFld.setConfiguration(getNotificaionImgConfig());
			imageFld.configure();
			imageFld.create();
			basePanel.add(imageFld);
			basePanel.setCellHorizontalAlignment(imageFld, HasHorizontalAlignment.ALIGN_CENTER);
			basePanel.setCellVerticalAlignment(imageFld, HasVerticalAlignment.ALIGN_MIDDLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a message only notification
	 */
	public void createMessageNotifier(){
		try {
			basePanel.clear();
			messageLbl = new LabelField();
			messageLbl.setConfiguration(getNotificationLblConfig());
			messageLbl.configure();
			messageLbl.create();
			basePanel.add(messageLbl);
			basePanel.setCellHorizontalAlignment(messageLbl, HasHorizontalAlignment.ALIGN_CENTER);
			basePanel.setCellVerticalAlignment(messageLbl, HasVerticalAlignment.ALIGN_MIDDLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Creates a notifier with an icon as well as a message
	 */
	private void create(){
		try {
			basePanel.clear();
			imageFld = new ImageField();
			messageLbl = new LabelField();
			
			imageFld.setConfiguration(getNotificaionImgConfig());
			imageFld.configure();
			imageFld.create();
			
			messageLbl.setConfiguration(getNotificationLblConfig());
			messageLbl.configure();
			messageLbl.create();
			
			basePanel.add(imageFld);
			basePanel.add(messageLbl);
			
			basePanel.setCellHorizontalAlignment(imageFld, HasHorizontalAlignment.ALIGN_RIGHT);
			basePanel.setCellVerticalAlignment(imageFld, HasVerticalAlignment.ALIGN_MIDDLE);
			basePanel.setCellHorizontalAlignment(messageLbl, HasHorizontalAlignment.ALIGN_LEFT);
			basePanel.setCellVerticalAlignment(messageLbl, HasVerticalAlignment.ALIGN_MIDDLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Creates a notifier with the default icon and message
	 */
	public void createDefaultNotifier(){
		create();
	}
	

	public void hideNotifier(){
		setVisible(false);
	}
	
	public void showNotifier(){
		setVisible(true);
	}
	
	/**
	 * Creates the Image configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getNotificaionImgConfig(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, getNotificationImageUrl());
			configuration.setPropertyByName(ImageFieldConstant.BF_PCLS,getNotificationImageCss());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}
	
	/**
	 * Creates the Label configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getNotificationLblConfig() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, getNotificationMsg());
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, getNotificationMsgCss());
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setLoaderMessageLblCss(String messageCss) {
		messageLbl.setConfiguration(getNotificationLblConfig());
		messageLbl.configure();
		messageLbl.create();
	}
	
	public void setLoaderImageCss(String imageCss) {
		imageFld.setConfiguration(getNotificaionImgConfig());
		imageFld.configure();
		imageFld.create();
	}
	
	public String getNotificationImageUrl() {
		String imageUrl = DEFAULT_NOTIFICATION_IMG;
		try {
			if(hasConfiguration(NotifierConstant.NOTIFICATION_IMAGE_URL)) {
				imageUrl =  configuration.getPropertyByName(NotifierConstant.NOTIFICATION_IMAGE_URL);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[Notifier]::Exception In getImageUrl  method :"+e);
		}
		return imageUrl;
	}
	
	public String getNotificationImageCss() {
		String imageCss = DEFAULT_NOTIFICATION_IMGCSS;
		try {
			if(hasConfiguration(NotifierConstant.NOTIFICATION_IMG_CSS)) {
				imageCss =  configuration.getPropertyByName(NotifierConstant.NOTIFICATION_IMG_CSS);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[Notifier]::Exception In getImageCss  method :"+e);
		}
		return imageCss;
	}
	
	public String getNotificationMsg() {
		String msg = DEFAULT_NOTIFICATION_MESSAGE;
		try {
			if(hasConfiguration(NotifierConstant.NOTIFICATION_MSG)) {
				msg =  configuration.getPropertyByName(NotifierConstant.NOTIFICATION_MSG);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[Notifier]::Exception In getMessage  method :"+e);
		}
		return msg;
	}
	
	public String getNotificationMsgCss() {
		String css = DEFAULT_NOTIFICATION_MSGCSS;
		try {
			if(hasConfiguration(NotifierConstant.NOTIFICATION_MSG_CSS)) {
				css =  configuration.getPropertyByName(NotifierConstant.NOTIFICATION_MSG_CSS);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[Notifier]::Exception In getMessageCss  method :"+e);
		}
		return css;
	}
	
	public String getNotifierBasePanelCss() {
		String css = null;
		try {
			if(hasConfiguration(NotifierConstant.BASEPANEL_CSS)) {
				css =  configuration.getPropertyByName(NotifierConstant.BASEPANEL_CSS);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[Notifier]::Exception In getNotifierBasePanelCss  method :"+e);
		}
		return css;
	}
	
	/**
	 * Returns true if the configuration is provided.
	 * @param configKey - The configuration to check
	 * @return
	 */
	protected boolean hasConfiguration(String configKey) {
		try {
			if(configuration != null && configuration.getPropertyByName(configKey) != null) {
				return true;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[Notifier]::Exception In hasConfiguration  method :"+e);

		}
		return false;
	}
	
	public interface NotifierConstant {
		
		public static final String NOTIFICATION_IMAGE_URL = "imageUrl";
		
		public static final String NOTIFICATION_MSG = "message";
		
		public static final String NOTIFICATION_IMG_CSS = "imageCss";
		
		public static final String NOTIFICATION_MSG_CSS = "msgCss";
		
		public static final String BASEPANEL_CSS = "notifierBasepanelCss";
	}
}
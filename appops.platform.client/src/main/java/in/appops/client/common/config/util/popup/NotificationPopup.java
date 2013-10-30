package in.appops.client.common.config.util.popup;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.NotificationEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NotificationPopup extends PopupPanel implements FieldEventHandler {

	private Configuration configuration;
	private VerticalPanel basePanel;
	private HorizontalPanel buttonPanel;
	
	private String popupMode;
	private String popupMessage;
	private String positionMode;
	
	private ButtonField yesButton;
	private ButtonField noButton;
	private ButtonField okButton;
	private HandlerRegistration fieldEventHandler = null;
	
	public NotificationPopup() {
		super();
		initialize();
		add(basePanel);
		
		if(fieldEventHandler == null) {
			fieldEventHandler = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		}
	}
	
	private void initialize() {
		basePanel = new VerticalPanel();
		buttonPanel = new HorizontalPanel();
	}

	public void deregisterHandler(){
		fieldEventHandler.removeHandler();
	}
	
	public void create() {
		try {
			basePanel.clear();
			LabelField messageLbl = new LabelField();
			messageLbl.setConfiguration(getLabelConfig(popupMessage));
			messageLbl.configure();
			messageLbl.create();
			basePanel.add(messageLbl);
			
			if(popupMode.equals(NotificationPopupConstant.POPUP_MODE_CONFIRMATION)) {
				createConfirmationPopup();
			} else if(popupMode.equals(NotificationPopupConstant.POPUP_MODE_NOTIFICATION)) {
				createNotificationPopup();
			}
			
			if(positionMode.equals(NotificationPopupConstant.POPUP_CENTER_POSITION)) {
				center();
			} else if(positionMode.equals(NotificationPopupConstant.POPUP_CUSTOM_POSITION)) {
				int top = getPopupTopPosition();
				int left = getPopupLeftPosition();
				setPopupPosition(left, top);
			}
			
			setAnimationEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int getPopupTopPosition() {
		int top = 0;
		if(configuration.getPropertyByName(NotificationPopupConstant.POPUP_CUSTOM_POSITION_TOP) != null) {
			top = Integer.valueOf(configuration.getPropertyByName(NotificationPopupConstant.POPUP_CUSTOM_POSITION_TOP).toString());
		}
		return top;
	}
	
	private int getPopupLeftPosition() {
		int left = 0;
		if(configuration.getPropertyByName(NotificationPopupConstant.POPUP_CUSTOM_POSITION_LEFT) != null) {
			left = Integer.valueOf(configuration.getPropertyByName(NotificationPopupConstant.POPUP_CUSTOM_POSITION_LEFT).toString());
		}
		return left;
	}

	private void createNotificationPopup() {
		try {
			buttonPanel.clear();
			okButton = new ButtonField();
			okButton.setConfiguration(getButtonConfiguration("Ok"));
			okButton.configure();
			okButton.create();
			buttonPanel.add(okButton);
			basePanel.add(buttonPanel);
			
			buttonPanel.setStylePrimaryName("notificationButtonPanel");
			buttonPanel.setCellHorizontalAlignment(okButton, HasHorizontalAlignment.ALIGN_CENTER);
			buttonPanel.setWidth("100%");
			basePanel.setWidth("100%");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createConfirmationPopup() {
		try {
			buttonPanel.clear();
			yesButton = new ButtonField();
			noButton = new ButtonField();
			
			yesButton.setConfiguration(getButtonConfiguration("Yes"));
			yesButton.configure();
			yesButton.create();
			buttonPanel.add(yesButton);
			
			noButton.setConfiguration(getButtonConfiguration("No"));
			noButton.configure();
			noButton.create();
			buttonPanel.add(noButton);
			buttonPanel.setCellWidth(yesButton, "50px");
			
			buttonPanel.setStylePrimaryName("notificationButtonPanel");
			basePanel.add(buttonPanel);
			basePanel.setCellHorizontalAlignment(buttonPanel, HasHorizontalAlignment.ALIGN_RIGHT);
			//basePanel.setCellWidth(buttonPanel, "50%");
		//	buttonPanel.setWidth("100%");
			basePanel.setWidth("100%");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void configure() {
		try {
			if(configuration != null) {
				popupMode = getPopupMode();
				popupMessage = getPopupMessage();
				
				if(configuration.getPropertyByName(NotificationPopupConstant.POPUP_PRIMARY_CSS) != null) {
					setStylePrimaryName(configuration.getPropertyByName(NotificationPopupConstant.POPUP_PRIMARY_CSS).toString());
				}
				if(configuration.getPropertyByName(NotificationPopupConstant.POPUP_DEPENDENT_CSS) != null) {
					addStyleName(configuration.getPropertyByName(NotificationPopupConstant.POPUP_DEPENDENT_CSS).toString());
				}
				
				positionMode = getPopupPositionMode();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getPopupPositionMode() {
		String positionMode = NotificationPopupConstant.POPUP_CENTER_POSITION;
		if(configuration.getPropertyByName(NotificationPopupConstant.POPUP_POSITION_MODE) != null) {
			positionMode = configuration.getPropertyByName(NotificationPopupConstant.POPUP_POSITION_MODE).toString();
		}
		return positionMode;
	}

	private String getPopupMessage() {
		String popupMessage = null;
		try {
			if(configuration.getPropertyByName(NotificationPopupConstant.POPUP_MESSAGE) != null) {
				popupMessage = configuration.getPropertyByName(NotificationPopupConstant.POPUP_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return popupMessage;
	}

	private String getPopupMode() {
		String popupMode = null;
		try {
			if(configuration.getPropertyByName(NotificationPopupConstant.POPUP_MODE) != null) {
				popupMode = configuration.getPropertyByName(NotificationPopupConstant.POPUP_MODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return popupMode;
	}
	
	/**
	 * Creates the Label configuration object and return.
	 * @return Configuration instance
	 */
	public Configuration getLabelConfig(String displayText) {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, "popupLbl");
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
	
	/**
	 * Creates the button configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getButtonConfiguration(String displayText){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, displayText);
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,"notificationPopupButton");
			configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		if(eventType == FieldEvent.CLICKED) {
			if(event.getEventSource() instanceof ButtonField) {
				ButtonField source = (ButtonField) event.getEventSource();
				if(popupMode.equals(NotificationPopupConstant.POPUP_MODE_CONFIRMATION)) {
					if(source.equals(yesButton)) {
						NotificationEvent notificationEvent = new NotificationEvent();
						notificationEvent.setEventSource(this);
						notificationEvent.setEventType(NotificationEvent.YES_BUTTON_CLICKED);
						AppUtils.EVENT_BUS.fireEvent(notificationEvent);
					} else if(source.equals(noButton)) {
						NotificationEvent notificationEvent = new NotificationEvent();
						notificationEvent.setEventSource(this);
						notificationEvent.setEventType(NotificationEvent.NO_BUTTON_CLICKED);
						AppUtils.EVENT_BUS.fireEvent(notificationEvent);
					}
				} else if(popupMode.equals(NotificationPopupConstant.POPUP_MODE_NOTIFICATION)) {
					if(source.equals(okButton)) {
						NotificationEvent notificationEvent = new NotificationEvent();
						notificationEvent.setEventSource(this);
						notificationEvent.setEventType(NotificationEvent.OK_BUTTON_CLICKED);
						AppUtils.EVENT_BUS.fireEvent(notificationEvent);
					}
				}
			}
		}
	}
	
	public interface NotificationPopupConstant {
		
		public static final String POPUP_MODE = "popupMode";
		
		public static final String POPUP_MODE_CONFIRMATION = "popupModeConfirmation";
		
		public static final String POPUP_MODE_NOTIFICATION = "popupModeNotification";
		
		public static final String POPUP_MESSAGE = "popupMessage";
		
		public static final String POPUP_POSITION_MODE = "popupPositionMode";
		
		public static final String POPUP_CENTER_POSITION = "popupCenterPosition";
		
		public static final String POPUP_CUSTOM_POSITION = "popupCustomPosition";
		
		public static final String POPUP_CUSTOM_POSITION_TOP = "popupCustomPositionTop";
		
		public static final String POPUP_CUSTOM_POSITION_LEFT = "popupCustomPositionLeft";
		
		public static final String POPUP_PRIMARY_CSS = "popupPrimaryCss";
		
		public static final String POPUP_DEPENDENT_CSS = "popupDependentCss";
	}
}

package in.appops.showcase.web.gwt.componentconfiguration.client;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author kamalakar@ensarm.com
 */
public class ShowUserInfoPopup extends PopupPanel{

	private HorizontalPanel userInfo;
	private HorizontalPanel actionPanel;
	private VerticalPanel vPanel;
	private ImageField userIcon;
	private ButtonField logoutButton;
	private LabelField userName;
	private Entity userEntity;

	private final String USER_ICON_IMAGE_CSS = "userIconImage";

	public ShowUserInfoPopup(Entity userEntity) {
		super(true);
		this.userEntity = userEntity;
	}

	public void createUI(){

		userInfo = new HorizontalPanel();
		actionPanel = new HorizontalPanel();
		vPanel = new VerticalPanel();
		userIcon = new ImageField();

		userIcon.setConfiguration(getUserIconConfig(userEntity.getPropertyByName("userIcon").toString()));
		userIcon.configure();
		userIcon.create();
		userInfo.add(userIcon);

		userName = new LabelField();
		userName.setConfiguration(getLabelFieldConfiguration(true,"appops-LabelField",null,userEntity.getPropertyByName("userName").toString()));
		userName.configure();
		userName.create();
		userInfo.add(userName);

		userInfo.setCellHorizontalAlignment(userIcon, HasHorizontalAlignment.ALIGN_LEFT);
		userInfo.setCellHorizontalAlignment(userName, HasHorizontalAlignment.ALIGN_CENTER);
		userInfo.setStylePrimaryName("userInfoPanel");

		logoutButton = new ButtonField();
		logoutButton.setConfiguration(getButtonConfiguration());
		logoutButton.configure();
		logoutButton.create();

		actionPanel.add(logoutButton);
		actionPanel.setCellHorizontalAlignment(logoutButton, HasHorizontalAlignment.ALIGN_RIGHT);
		actionPanel.setStylePrimaryName("actionPanel");
		vPanel.add(userInfo);
		vPanel.add(actionPanel);
		setStylePrimaryName("userPopup");
		setWidget(vPanel);
	}

	private Configuration getUserIconConfig(String userIcon) {
		Configuration config = null;
		try{
			config = new Configuration();
			config.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, userIcon);
			config.setPropertyByName(ImageFieldConstant.BF_PCLS, USER_ICON_IMAGE_CSS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}

	private Configuration getButtonConfiguration(){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Log Out");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,"logoutButton");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}

	/**
	 * creates the configuration object for a {@link}LabelField
	 * @param allowWordWrap boolean true / false
	 * @param primaryCss String the primary css style name to be applied to the field as defined in the css file
	 * @param secondaryCss (optional) String the dependent css style name to be applied to the field as defined in the css file
	 * @param debugId (optional) String the debug id for the {@link}LabelField
	 * @return
	 */
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String displayText){

		Configuration conf = new Configuration();
		try {
			conf.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, allowWordWrap);
			conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
			conf.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conf;
	}

}

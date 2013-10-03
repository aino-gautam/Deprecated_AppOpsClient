/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client;

import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.ImageField.ImageFieldConstant;
import in.appops.client.common.config.field.LinkField;
import in.appops.client.common.config.field.LinkField.LinkFieldConstant;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class AugsHeader extends Composite implements FieldEventHandler{

	private HorizontalPanel basePanel;
	
	private final String IMG_PATH = "images/augsLogo.jpg";
	private final String USERICON_IMG_PATH = "images/userIcon.jpg";
	private final String AUGS_ICON_IMAGE_CSS = "augsLogoImage";
	private final String USER_ICON_IMAGE_CSS = "userIconImage";
	private final String CREATE_NEW_SERVICE_LINK_BASE_CSS = "createServiceHeaderLinkBase";
	private final String SELECT_SERVICE_LISTBOX_CSS = "headerServiceSelectionListBox";
	private final String HEADER_LINK_CSS = "augsHeaderLink";

	private LinkField createServiceLink;
	private ImageField logoImgField;
	private ListBoxField selectServiceListFld;
	private ListBoxField versionServiceSelectionListFld;

	private LinkField updateLink;
	private ImageField userIconImgFld;
	
	public AugsHeader(){
		initialize();
	}

	private void initialize() {
		try{
			basePanel = new HorizontalPanel();
			basePanel.setStylePrimaryName("augesHeader");
			initWidget(basePanel);
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createUi(){
		try{

			logoImgField = new ImageField();
			logoImgField.setConfiguration(getLogoConfig());
			logoImgField.configure();
			logoImgField.create();
			
			createServiceLink = new LinkField();
			createServiceLink.setConfiguration(getCreateSeriveLinkConfig());
			createServiceLink.configure();
			createServiceLink.create();
			
			selectServiceListFld = new ListBoxField();
			selectServiceListFld.setConfiguration(getSelectServiceConfig());
			selectServiceListFld.configure();
			selectServiceListFld.create();
			
			versionServiceSelectionListFld = new ListBoxField();
			versionServiceSelectionListFld = new ListBoxField();
			versionServiceSelectionListFld.setConfiguration(getVersionSelectionFldConfig());
			versionServiceSelectionListFld.configure();
			versionServiceSelectionListFld.create();
			
			updateLink = new LinkField();
			updateLink.setConfiguration(getUpdateConfig());
			updateLink.configure();
			updateLink.create();
			
			userIconImgFld = new ImageField();
			userIconImgFld.setConfiguration(getUserIconConfig());
			userIconImgFld.configure();
			userIconImgFld.create();
			
			
			basePanel.add(logoImgField);
			
			HorizontalPanel innerBasePanel = new HorizontalPanel();
			
			innerBasePanel.add(createServiceLink);
			
			innerBasePanel.add(selectServiceListFld);

			innerBasePanel.add(versionServiceSelectionListFld);

			innerBasePanel.add(updateLink);

			innerBasePanel.add(userIconImgFld);

			basePanel.add(innerBasePanel);
			
			basePanel.setCellHorizontalAlignment(logoImgField, HorizontalPanel.ALIGN_LEFT);
			basePanel.setCellHorizontalAlignment(innerBasePanel, HorizontalPanel.ALIGN_RIGHT);
			
			innerBasePanel.setCellHorizontalAlignment(createServiceLink, HorizontalPanel.ALIGN_RIGHT);
			innerBasePanel.setCellHorizontalAlignment(versionServiceSelectionListFld, HorizontalPanel.ALIGN_RIGHT);
			innerBasePanel.setCellHorizontalAlignment(updateLink, HorizontalPanel.ALIGN_RIGHT);
			innerBasePanel.setCellHorizontalAlignment(userIconImgFld, HorizontalPanel.ALIGN_RIGHT);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Configuration getVersionSelectionFldConfig() {
		Configuration config = null;
		try{
			config = new Configuration();
			config.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"---Select version ---");
			config.setPropertyByName(ListBoxFieldConstant.BF_PCLS, SELECT_SERVICE_LISTBOX_CSS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return config;	
	}

	private Configuration getUserIconConfig() {
		Configuration config = null;
		try{
			config = new Configuration();
			config.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "Current user details");
			config.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, USERICON_IMG_PATH);
			config.setPropertyByName(ImageFieldConstant.BF_PCLS, USER_ICON_IMAGE_CSS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return config;		
	}

	private Configuration getUpdateConfig() {
		Configuration config = null;
		try{
			config = new Configuration();
			config.setPropertyByName(LinkFieldConstant.LNK_DISPLAYTEXT, "Updates");
			config.setPropertyByName(LinkFieldConstant.LNK_TITLE, "Show all your updates");
			config.setPropertyByName(LinkFieldConstant.LNKTYPE_HYPERLINK, true);
			config.setPropertyByName(LinkFieldConstant.BF_PCLS, HEADER_LINK_CSS);
			config.setPropertyByName(LinkFieldConstant.BF_BASEPANEL_PCLS, CREATE_NEW_SERVICE_LINK_BASE_CSS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return config;	
	}

	private Configuration getSelectServiceConfig() {
		Configuration config = null;
		try{
			config = new Configuration();
			config.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"---Select service ---");
			config.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getAllServiceList");
			config.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getServices");
			config.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			config.setPropertyByName(ListBoxFieldConstant.BF_PCLS, SELECT_SERVICE_LISTBOX_CSS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return config;	
	}

	private Configuration getCreateSeriveLinkConfig() {
		Configuration config = null;
		try{
			config = new Configuration();
			config.setPropertyByName(LinkFieldConstant.LNK_DISPLAYTEXT, "Create Service");
			config.setPropertyByName(LinkFieldConstant.LNK_TITLE, "Link to create new service");
			config.setPropertyByName(LinkFieldConstant.LNKTYPE_HYPERLINK, true);
			config.setPropertyByName(LinkFieldConstant.BF_PCLS, HEADER_LINK_CSS);
			config.setPropertyByName(LinkFieldConstant.BF_BASEPANEL_PCLS, CREATE_NEW_SERVICE_LINK_BASE_CSS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return config;	
	}

	private Configuration getLogoConfig() {
		Configuration config = null;
		try{
			config = new Configuration();
			config.setPropertyByName(ImageFieldConstant.IMGFD_TITLE, "AUGS Home");
			config.setPropertyByName(ImageFieldConstant.IMGFD_BLOBID, IMG_PATH);
			config.setPropertyByName(ImageFieldConstant.BF_PCLS, AUGS_ICON_IMAGE_CSS);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return config;		
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
			if(event.getEventSource().equals(logoImgField)){
				ConfigEvent configEvent = new ConfigEvent(ConfigEvent.AUGSHOME, null,this);
				AppUtils.EVENT_BUS.fireEvent(configEvent);
			}
			else if(event.getEventSource().equals(createServiceLink)){
				ConfigEvent configEvent = new ConfigEvent(ConfigEvent.CREATENEWSERVICE, null,this);
				AppUtils.EVENT_BUS.fireEvent(configEvent);
			}
			else if(event.getEventSource().equals(updateLink)){
				ConfigEvent configEvent = new ConfigEvent(ConfigEvent.AUGSHOME, null,this);
				AppUtils.EVENT_BUS.fireEvent(configEvent);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

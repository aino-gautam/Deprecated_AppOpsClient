package in.appops.client.common.snippet;

import in.appops.client.common.event.ActionEvent;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.server.core.services.platform.coreplatformservice.constant.ServiceConstant;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ServiceIconSnippet extends CardSnippet {

	private VerticalPanel basePanel = new VerticalPanel();
	private Image serviceIcon;
	private LabelField serviceEntityTitle;
	private HorizontalPanel labelPanel= new HorizontalPanel();
	private boolean showOnlyIcon = false;
	
	public ServiceIconSnippet() {
		
	}
	
	@Override
	public void initialize(){
		
		super.initialize();
		basePanel.setStylePrimaryName("serviceEntityBasePanel");
		
		String blobId = getEntity().getProperty("blobId").getValue().toString();
		BlobDownloader blobDownloader = new BlobDownloader();
		serviceIcon = new Image(blobDownloader.getIconDownloadURL(blobId));
		basePanel.add(serviceIcon);
		
		if(!showOnlyIcon){
			serviceEntityTitle = new LabelField();
			serviceEntityTitle.setFieldValue(getEntity().getProperty(ServiceConstant.NAME).getValue().toString());
			serviceEntityTitle.setConfiguration(getLabelFieldConfiguration(true, "serviceEntityTitle", null, null));
			try {
				serviceEntityTitle.createField();
			} catch (AppOpsException e) {
		
				e.printStackTrace();
			}
			labelPanel.setStylePrimaryName("serviceEntityTitlePanel");
			labelPanel.add(serviceEntityTitle);
	
			labelPanel.setCellHorizontalAlignment(serviceEntityTitle, HasHorizontalAlignment.ALIGN_CENTER);
			labelPanel.setCellVerticalAlignment(serviceEntityTitle, HasVerticalAlignment.ALIGN_TOP);
			basePanel.add(labelPanel);
		}
		
		basePanel.setCellHorizontalAlignment(serviceIcon, HasHorizontalAlignment.ALIGN_CENTER);
		basePanel.setCellVerticalAlignment(serviceIcon,HasVerticalAlignment.ALIGN_TOP);
		
		basePanel.addDomHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				serviceIconClickEvent(event);
				
			}
		},  ClickEvent.getType());
		
		add(basePanel,DockPanel.CENTER);
	}
	
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap,
		String primaryCss, String secondaryCss, String debugId) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_WORDWRAP,allowWordWrap);
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS,primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS,secondaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return configuration;
	}

	
	public void serviceIconClickEvent(ClickEvent event) {
		ActionEvent actionEvent  = new ActionEvent();
		actionEvent.setEventType(ActionEvent.LOADENTITYHOME);
		String servicename = getEntity().getProperty(ServiceConstant.NAME).getValue().toString();
		String widgetName = null;
		
		if(servicename.equalsIgnoreCase("social")){
			widgetName  = SnippetConstant.POSTSERVICEHOME;
		}else if(servicename.equalsIgnoreCase(TypeConstants.CALENDAR)){
			widgetName  = SnippetConstant.CALENDARSERVICEHOME;
		}else if(servicename.equalsIgnoreCase(TypeConstants.MEDIA)){
			widgetName  = SnippetConstant.MEDIASERVICEHOME;
		}else if(servicename.equalsIgnoreCase(TypeConstants.CONTACT)){
			widgetName  = SnippetConstant.CONTACTSERVICEHOME;
		}else if(servicename.equalsIgnoreCase("messenger")){
			widgetName  = SnippetConstant.CHATSERVICEHOME;
		}else if(servicename.equalsIgnoreCase("usermessage")){
			widgetName  = SnippetConstant.USERMESSAGESERVICEHOME;
		}else{
		
			widgetName  = SnippetConstant.HOMESNIPPET;
		}
		actionEvent.setEventData(widgetName);
		AppUtils.EVENT_BUS.fireEvent(actionEvent);
	}

	public boolean isShowOnlyIcon() {
		return showOnlyIcon;
	}

	public void setShowOnlyIcon(boolean showOnlyIcon) {
		this.showOnlyIcon = showOnlyIcon;
	}

}

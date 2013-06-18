package in.appops.client.common.components;

import in.appops.client.common.fields.ImageField;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.snippet.Snippet;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;
import in.appops.platform.server.core.services.usermessage.constant.MessageConstant;

import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MessageWithUserSnippet extends Composite implements Snippet{
	
	
	private HorizontalPanel mainPanel;
	private Entity entity;
	public MessageWithUserSnippet() {
		initialize();
		/*mainPanel.setWidth("100%");
        mainPanel.setHeight("100%");*/
		initWidget(mainPanel);
	}
	
	@Override
	public void initialize() {
		mainPanel = new HorizontalPanel();
		
	}
	
	public void createUi(){
		FlexTable flexTable = new FlexTable();
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		flexTable.setWidget(0, 0, createUserImageField());
		flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		flexTable.setWidget(0, 3, createUserNameLabelField());
		//horizontalPanel.setBorderWidth(1);
		horizontalPanel.add(flexTable);
		mainPanel.add(horizontalPanel);
		horizontalPanel.setStylePrimaryName("snippetPanelForMessage");
		mainPanel.setStyleName("messageWithUserSnippet");
		//horizontalPanel.addStyleName("snippetPanel");
		
	}

	private VerticalPanel createUserNameLabelField() {
		
		VerticalPanel vpPanel = new VerticalPanel();
		try{
			LabelField labelField = new LabelField();
			Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
			if(entity.getPropertyByName(ContactConstant.NAME)!=null){
				String fieldValue=  entity.getPropertyByName(ContactConstant.NAME);
				labelField.setFieldValue(fieldValue);
			}else{
				labelField.setFieldValue("user");
			}
			labelField.setConfiguration(labelConfig);
			labelField.create();
			vpPanel.add(labelField);
			Configuration labelConfig1 = getLabelFieldConfiguration(true, "appops-LabelField", "userWithMessagePanelLabelContent", null);
			LabelField messageLabelField = new LabelField();
			if(entity.getPropertyByName(MessageConstant.DESCRIPTION)!=null){
				String messageStr = entity.getPropertyByName(MessageConstant.DESCRIPTION);
				
				messageLabelField.setFieldValue(messageStr);
				messageLabelField.setConfiguration(labelConfig1);
				messageLabelField.create();
			}
			HorizontalPanel panel = new HorizontalPanel();
			panel.add(messageLabelField);
			panel.setWidth("100%");
			vpPanel.add(panel);
		}catch(Exception e){
			e.printStackTrace();
			
			
		}
		return vpPanel;
	}
	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration config = new Configuration();
		config.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		config.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return config;
	}
	private HorizontalPanel createUserImageField() {
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		BlobDownloader downloader = new BlobDownloader();
		try{
			Configuration imageConfig =null;
			if(entity.getPropertyByName(ContactConstant.IMGBLOBID)!=null){
				String blobId = entity.getPropertyByName(ContactConstant.IMGBLOBID).toString();
				String url = downloader.getIconDownloadURL(blobId);
				imageConfig = getImageFieldConfiguration(url, "defaultIcon","defaultIcon_Small");
			}else{
				imageConfig = getImageFieldConfiguration("images/default_Icon.png", "defaultIcon","defaultIcon_Small");
			}
		
		final ImageField imageField = new ImageField();
		
		imageField.setConfiguration(imageConfig);
        imageField.create();
        imageField.addErrorHandler(new ErrorHandler() {
			
			@Override
			public void onError(ErrorEvent event) {
				imageField.setUrl("images/default_Icon.png");
			}
		});
        
        horizontalPanel.add(imageField);
		}catch(Exception e){
			e.printStackTrace();
		}
		return horizontalPanel;
	}

	public Configuration getImageFieldConfiguration(String url, String primaryCSS, String secondaryCss) {
		Configuration config = new Configuration();
		config.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, url);
		config.setPropertyByName(ImageField.IMAGEFIELD_PRIMARYCSS, primaryCSS);
		config.setPropertyByName(ImageField.IMAGEFIELD_DEPENDENTCSS, secondaryCss);
		return config;
	}
	
	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
		
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void setConfiguration(Configuration configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionContext getActionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActionContext(ActionContext actionContext) {
		// TODO Auto-generated method stub
		
	}

}

package in.appops.client.common.components;

import in.appops.client.common.bound.EntityBound;
import in.appops.client.common.contactmodel.ContactSnippet;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.ImageField;
import in.appops.client.common.fields.LabelField;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.client.common.snippet.Snippet;
import in.appops.client.common.snippet.SnippetFactory;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContactSuggestionSnippet  extends Composite implements EntityBound, ClickHandler{

	private VerticalPanel basePanel;
	private Entity entity;
	private String displayText;
	
	public ContactSuggestionSnippet() {
		initialize();
		initWidget(basePanel);
	}
	
	private void initialize() {
		basePanel = new VerticalPanel();
		
	}
	
	public void createSuggestionSnippet(SnippetFactory snippetFactory) {
		
		final ContactSnippet snippet = (ContactSnippet)snippetFactory.getSnippetByEntityType(entity.getType(), null);
	//	snippet.setEntity(entity);
		//snippet.initialize(entity);
		snippet.setSelectionAllowed(false);
		/*displayText = entity.getPropertyByName("name");
		
		Label displayLabel = new Label(displayText);
		displayLabel.addClickHandler(this);*/
		snippet.setWidth("100%");
		BlobDownloader downloader = new BlobDownloader();
		//ContactSnippet contactSnippet = new ContactSnippet(false);
		String blobId = entity.getPropertyByName(ContactConstant.IMGBLOBID).toString();
		String url = downloader.getIconDownloadURL(blobId);
		Configuration imageConfig = getImageFieldConfiguration(url, "defaultIcon");
		Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
		snippet.setConfigurationForFields(labelConfig, imageConfig);
		snippet.initialize(entity);
		snippet.addStyleName("flowPanelContent");
		
		ClickHandler handler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				FieldEvent fieldEvent = new FieldEvent();
				fieldEvent.setEventType(FieldEvent.SUGGESTION_CLICKED);			
				fieldEvent.setEventData(entity);	
				AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, ContactSuggestionSnippet.this);
			}
		};
		
		snippet.addDomHandler(handler, ClickEvent.getType());
		
		
		basePanel.add(snippet);
	}

	@Override
	public void onClick(ClickEvent event) {
		FieldEvent fieldEvent = new FieldEvent();
		fieldEvent.setEventType(FieldEvent.SUGGESTION_CLICKED);			
		fieldEvent.setEventData(displayText);	
		AppUtils.EVENT_BUS.fireEventFromSource(fieldEvent, this);
		
	}

	public Configuration getImageFieldConfiguration(String url, String primaryCSS) {
		Configuration config = new Configuration();
		config.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, url);
		config.setPropertyByName(ImageField.IMAGEFIELD_PRIMARYCSS, primaryCSS);
		return config;
	}
	
	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration config = new Configuration();
		config.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		config.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		config.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
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
	public void addFieldEventHandler(FieldEventHandler handler){
		AppUtils.EVENT_BUS.addHandlerToSource(FieldEvent.TYPE, this, handler);
	}
}

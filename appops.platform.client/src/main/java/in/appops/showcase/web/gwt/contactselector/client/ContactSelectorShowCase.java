package in.appops.showcase.web.gwt.contactselector.client;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.contactmodel.ContactSelector;
import in.appops.client.common.contactmodel.ContactSelectorModel;
import in.appops.client.common.contactmodel.ContactSnippet;
import in.appops.client.common.fields.ImageField;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContactSelectorShowCase implements EntryPoint, ClickHandler {

	private Button viewContacts;
	private ContactSelector contactSelector;
	private VerticalPanel contactListPanel;
	private FlowPanel snippetPanel;
	
	@Override
	public void onModuleLoad() {
		contactSelector = createContactSelector();
		VerticalPanel basePanel = new VerticalPanel();
		viewContacts = new Button("View selected contacts");
		viewContacts.addClickHandler(this);
		viewContacts.setStylePrimaryName("appops-Button");
		viewContacts.addStyleName("contactListAlignment");
		contactListPanel = new VerticalPanel();
		snippetPanel = new FlowPanel();
		snippetPanel.setSize("100%", "100%");
		
		basePanel.add(contactSelector);
		basePanel.add(viewContacts);
		basePanel.add(contactListPanel);
		contactListPanel.setStylePrimaryName("contactListPanel");
		contactListPanel.setSize("100%", "100%");
		basePanel.setSize("100%", "100%");
		RootPanel.get().add(basePanel);
	}

	private ContactSelector createContactSelector() {
		Query query = new Query();
		query.setQueryName("getContactList");
		query.setListSize(8);
		ContactSelectorModel contactSelectorModel = new ContactSelectorModel(query,"contact.ContactService.getEntityList",0);
		contactSelectorModel.setNearByContact(true);
		contactSelectorModel.setYourContact(true);
		contactSelectorModel.setContactKnown(true);
		ContactSelector contactSelector = new ContactSelector(contactSelectorModel);
		contactSelector.setSelectionAllowed(true);
		return contactSelector;
	}

	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(viewContacts)) {
			contactListPanel.clear();
			snippetPanel.clear();
			
			LabelField titleLabel = new LabelField();
			titleLabel.setFieldValue("List of selected contacts ");
			titleLabel.setConfiguration(getLabelFieldConfiguration(true, "contactSelectortitleLabel", null, null));
			titleLabel.create();
			titleLabel.addStyleName("contactListAlignment");
			contactListPanel.add(titleLabel);
			
			ArrayList<Entity> list = contactSelector.getSelectedContacts();
			Iterator<Entity> iterator = list.iterator();
			BlobDownloader downloader = new BlobDownloader();
			while(iterator.hasNext()) {
				Entity entity = iterator.next();
				ContactSnippet contactSnippet = new ContactSnippet(false);
				String blobId = entity.getPropertyByName(ContactConstant.IMGBLOBID).toString();
				String url = downloader.getIconDownloadURL(blobId);
				Configuration imageConfig = getImageFieldConfiguration(url, "defaultIcon");
				Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
				contactSnippet.setConfigurationForFields(labelConfig, imageConfig);
				contactSnippet.initialize(entity);
				contactSnippet.addStyleName("flowPanelContent");
				snippetPanel.add(contactSnippet);
			}
			
			contactListPanel.add(snippetPanel);
			
			if(list.isEmpty() || list == null) {
				titleLabel.setFieldValue("No contacts selected");
				titleLabel.reset();
				
			}
		}
	}
	
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		return configuration;
	}
	
	public Configuration getImageFieldConfiguration(String url, String primaryCSS) {
		Configuration config = new Configuration();
		config.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, url);
		config.setPropertyByName(ImageField.IMAGEFIELD_PRIMARYCSS, primaryCSS);
		return config;
	}
}

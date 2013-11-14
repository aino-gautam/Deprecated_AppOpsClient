package in.appops.client.common.contactmodel;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.core.EntityListReceiver;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.SelectionEvent;
import in.appops.client.common.event.handlers.SelectionEventHandler;
import in.appops.client.common.fields.ImageField;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import java.util.Iterator;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContactSelector extends Composite implements SelectionEventHandler,EntityListReceiver{

	private boolean isSelectionAllowed;
	private VerticalPanel basePanel;
	private HorizontalPanel titleHorizonPanel;
	private HorizontalPanel innerHorizonPanel;
	private ScrollPanel scrollPanel;
	private EntityList nearByContactEntityList;
	private EntityList yourContactEntityList;
	private EntityList contactMayKnownEntityList;
	private EntityList selectedContactList = new EntityList();
	private ContactSelectorModel contactSelectorModel;
	private LabelField nearByContact;
	private LabelField yourContact;
	private LabelField contactMayKnown;
	private FlowPanel nearByContactFlowPanel;
	private FlowPanel yourContactFlowPanel;
	private FlowPanel contactMayKnownFlowPanel;
	private boolean isNearByContact;
	private boolean isYourContact;
	private boolean isContactKnown;
	
	public ContactSelector(ContactSelectorModel contactSelectorModel) {
		this.contactSelectorModel = contactSelectorModel;
		init();
		initWidget(basePanel);
		AppUtils.EVENT_BUS.addHandler(SelectionEvent.TYPE, this);
	}
	
	private void init() {
		basePanel = new VerticalPanel();
		titleHorizonPanel = new HorizontalPanel();
		innerHorizonPanel = new HorizontalPanel();
		
		isNearByContact = contactSelectorModel.isNearByContact();
		isYourContact = contactSelectorModel.isYourContact();
		isContactKnown = contactSelectorModel.isContactKnown();
		
		addNearByContactComponents();
		addYourContactComponents();
		addontactMayKnownComponents();
		setInnerPanelWidth();
		
		scrollPanel = new ScrollPanel(innerHorizonPanel);
		int height = Window.getClientHeight() - 120;
		int width = Window.getClientWidth() - 100;
		scrollPanel.setHeight(height + "px");
		scrollPanel.setWidth(width + "px");
		titleHorizonPanel.setWidth("100%");
		basePanel.add(titleHorizonPanel);
		basePanel.add(scrollPanel);
		basePanel.setStylePrimaryName("contactSelectorBase");
		
		contactSelectorModel.getEntityList(0, this);
	}

	private void addNearByContactComponents() {
		if(isNearByContact) {
			nearByContact = new LabelField();
			nearByContact.setFieldValue("Contact Near You");
			Configuration config = getLabelFieldConfiguration(true, "contactSelectortitleLabel", null, null);
			nearByContact.setConfiguration(config);
			nearByContact.configure();
			nearByContact.create();
			nearByContactFlowPanel = new FlowPanel();
			nearByContactFlowPanel.setSize("100%", "100%");
			innerHorizonPanel.add(nearByContactFlowPanel);
			titleHorizonPanel.add(nearByContact);
		}
	}

	private void addYourContactComponents() {
		if(isYourContact) {
			yourContact = new LabelField();
			yourContact.setFieldValue("Your Contacts");
			Configuration config = getLabelFieldConfiguration(true, "contactSelectortitleLabel", null, null);
			yourContact.setConfiguration(config);
			nearByContact.configure();
			yourContact.create();
			yourContactFlowPanel = new FlowPanel();
			yourContactFlowPanel.setSize("100%", "100%");
			innerHorizonPanel.add(yourContactFlowPanel);
			titleHorizonPanel.add(yourContact);
		}
	}

	private void addontactMayKnownComponents() {
		if(isContactKnown) {
			contactMayKnown = new LabelField();
			contactMayKnown.setFieldValue("Contacts you may known");
			Configuration config = getLabelFieldConfiguration(true, "contactSelectortitleLabel", null, null);
			contactMayKnown.setConfiguration(config);
			contactMayKnown.configure();
			contactMayKnown.create();
			contactMayKnownFlowPanel = new FlowPanel();
			contactMayKnownFlowPanel.setSize("100%", "100%");
			innerHorizonPanel.add(contactMayKnownFlowPanel);
			titleHorizonPanel.add(contactMayKnown);
		}
	}
	
	private void setInnerPanelWidth() {
		
		if(isNearByContact && isYourContact && isContactKnown) {
			addTitle();
			addAllComponents();
		} else if(!isNearByContact && isYourContact && isContactKnown) {
			addTitle(yourContact, contactMayKnown);
			addOnlyTwoComponents(yourContactFlowPanel, contactMayKnownFlowPanel);
		} else if(isNearByContact && !isYourContact && isContactKnown) {
			addTitle(nearByContact, contactMayKnown);
			addOnlyTwoComponents(nearByContactFlowPanel, contactMayKnownFlowPanel);
		} else if(isNearByContact && isYourContact && !isContactKnown) {
			addTitle(nearByContact, yourContact);
			addOnlyTwoComponents(nearByContactFlowPanel, yourContactFlowPanel);
		} else if(!isNearByContact && !isYourContact && isContactKnown) {
			addTitle(contactMayKnown);
			addOnlyOneComponent(contactMayKnownFlowPanel);
		} else if(!isNearByContact && isYourContact && !isContactKnown) {
			addTitle(yourContact);
			addOnlyOneComponent(yourContactFlowPanel);
		} else if(isNearByContact && !isYourContact && !isContactKnown) {
			addTitle(nearByContact);
			addOnlyOneComponent(nearByContactFlowPanel);
		} else if(!isNearByContact && !isYourContact && !isContactKnown) {
			
		}
		
		innerHorizonPanel.setSize("100%", "100%");
	}

	private void addTitle(LabelField contactMayKnown2) {
		titleHorizonPanel.setCellWidth(contactMayKnown2, "100%");
		titleHorizonPanel.addStyleName("contactSelectorTitlePanel");
	}

	private void addTitle(LabelField yourContact2, LabelField contactMayKnown2) {
		titleHorizonPanel.setCellWidth(yourContact2, "50%");
		titleHorizonPanel.setCellWidth(contactMayKnown2, "50%");
		titleHorizonPanel.addStyleName("contactSelectorTitlePanel");
	}

	private void addTitle() {
		titleHorizonPanel.setCellWidth(nearByContact, "33%");
		titleHorizonPanel.setCellWidth(yourContact, "33%");
		titleHorizonPanel.setCellWidth(contactMayKnown, "33%");
		titleHorizonPanel.addStyleName("contactSelectorTitlePanel");
	}

	private void addOnlyOneComponent(FlowPanel panel) {
		innerHorizonPanel.setCellWidth(panel, "100%");
	}

	private void addOnlyTwoComponents(FlowPanel panel1, FlowPanel panel2) {
		innerHorizonPanel.setCellWidth(panel1, "50%");
		innerHorizonPanel.setCellWidth(panel2, "50%");
	}

	private void addAllComponents() {
		
		innerHorizonPanel.setCellWidth(nearByContactFlowPanel, "33%");
		innerHorizonPanel.setCellWidth(yourContactFlowPanel, "33%");
		innerHorizonPanel.setCellWidth(contactMayKnownFlowPanel, "33%");
	}

	public boolean isSelectionAllowed() {
		return isSelectionAllowed;
	}

	public void setSelectionAllowed(boolean isSelectionAllowed) {
		this.isSelectionAllowed = isSelectionAllowed;
	}

	public EntityList getNearByContactEntityList() {
		return nearByContactEntityList;
	}

	public void setNearByContactEntityList(EntityList nearByContactEntityList) {
		this.nearByContactEntityList = nearByContactEntityList;
	}

	public EntityList getSelectedContacts() {
		return selectedContactList;
	}
	
	public EntityList getYourContactEntityList() {
		return yourContactEntityList;
	}

	public void setYourContactEntityList(EntityList yourContactEntityList) {
		this.yourContactEntityList = yourContactEntityList;
	}

	public EntityList getContactMayKnownEntityList() {
		return contactMayKnownEntityList;
	}

	public void setContactMayKnownEntityList(EntityList contactMayKnownEntityList) {
		this.contactMayKnownEntityList = contactMayKnownEntityList;
	}

	public void initialize(EntityList entityList) {
		
		if(isNearByContact) {
			BlobDownloader downloader = new BlobDownloader();
			setNearByContactEntityList(entityList);
			if(nearByContactEntityList != null && !nearByContactEntityList.isEmpty()) {
				Iterator<Entity> iterator = nearByContactEntityList.iterator();
				while(iterator.hasNext()) {
					Entity entity = iterator.next();
					ContactSnippet contactSnippet = new ContactSnippet(isSelectionAllowed);
					String blobId = entity.getPropertyByName(ContactConstant.IMGBLOBID).toString();
					String url = downloader.getIconDownloadURL(blobId);
					Configuration imageConfig = getImageFieldConfiguration(url, "defaultIcon");
					Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
					contactSnippet.setConfigurationForFields(labelConfig, imageConfig);
					contactSnippet.initialize(entity);
					contactSnippet.addStyleName("flowPanelContent");
					nearByContactFlowPanel.add(contactSnippet);
				}
			}
		}

		if(isYourContact) {
			BlobDownloader downloader = new BlobDownloader();
			setYourContactEntityList(entityList);
			if(yourContactEntityList != null && !yourContactEntityList.isEmpty()) {
				Iterator<Entity> iterator = yourContactEntityList.iterator();
				while(iterator.hasNext()) {
					Entity entity = iterator.next();
					ContactSnippet contactSnippet = new ContactSnippet(isSelectionAllowed);
					String blobId = entity.getPropertyByName(ContactConstant.IMGBLOBID).toString();
					String url = downloader.getIconDownloadURL(blobId);
					Configuration imageConfig = getImageFieldConfiguration(url, "defaultIcon");
					Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
					contactSnippet.setConfigurationForFields(labelConfig, imageConfig);
					contactSnippet.initialize(entity);
					contactSnippet.addStyleName("flowPanelContent");
					yourContactFlowPanel.add(contactSnippet);
				}
			}
		}

		if(isContactKnown) {
			BlobDownloader downloader = new BlobDownloader();
			setContactMayKnownEntityList(entityList);
			if(contactMayKnownEntityList != null && !contactMayKnownEntityList.isEmpty()) {
				Iterator<Entity> iterator = contactMayKnownEntityList.iterator();
				while(iterator.hasNext()) {
					Entity entity = iterator.next();
					ContactSnippet contactSnippet = new ContactSnippet(isSelectionAllowed);
					String blobId = entity.getPropertyByName(ContactConstant.IMGBLOBID).toString();
					String url = downloader.getIconDownloadURL(blobId);
					Configuration imageConfig = getImageFieldConfiguration(url, "defaultIcon");
					Configuration labelConfig = getLabelFieldConfiguration(true, "flowPanelContent", null, null);
					contactSnippet.setConfigurationForFields(labelConfig, imageConfig);
					contactSnippet.initialize(entity);
					contactSnippet.addStyleName("flowPanelContent");
					contactMayKnownFlowPanel.add(contactSnippet);
				}
			}
		}
	}

	@Override
	public void onSelection(SelectionEvent event) {
		int eventType = event.getEventType();
		ContactSnippet contactSnippet = (ContactSnippet) event.getEventData();
		Entity entity = contactSnippet.getEntity();
		
		switch (eventType) {
		case SelectionEvent.SELECTED: {
			selectedContactList.add(entity);
			break;
		}
		case SelectionEvent.DESELECTED: {
			selectedContactList.remove(entity);
			break;
		}
		case SelectionEvent.DATARECEIVED: {
			//selectedContactList.remove(entity);
			break;
		}
		default:
			break;
		}
	}
	
	public Configuration getImageFieldConfiguration(String url, String primaryCSS) {
		Configuration config = new Configuration();
		config.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, url);
		config.setPropertyByName(ImageField.IMAGEFIELD_PRIMARYCSS, primaryCSS);
		return config;
	}

	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityListReceived(EntityList entityList) {
		initialize(entityList);
	}

	@Override
	public void onEntityListUpdated() {
		// TODO Auto-generated method stub
		
	}
	
	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration conf = new Configuration();
		conf.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, allowWordWrap);
		conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Config label");
		conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		conf.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		return conf;
	}

	@Override
	public void updateCurrentView(Entity entity) {
		// TODO Auto-generated method stub
		
	}
}

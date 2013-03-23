package in.appops.client.common.contactmodel;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.SelectionEvent;
import in.appops.client.common.event.handlers.SelectionEventHandler;
import in.appops.client.common.fields.ImageField;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.Iterator;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContactSelector extends Composite implements SelectionEventHandler{

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
	private Label nearByContact;
	private Label yourContact;
	private Label contactMayKnown;
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
		int height = Window.getClientHeight() - 110;
		int width = Window.getClientWidth() - 100;
		scrollPanel.setHeight(height + "px");
		scrollPanel.setWidth(width + "px");
		titleHorizonPanel.setWidth("100%");
		basePanel.add(titleHorizonPanel);
		basePanel.add(scrollPanel);
		basePanel.setStylePrimaryName("contactSelectorBase");
	}

	private void addNearByContactComponents() {
		if(isNearByContact) {
			nearByContact = new Label("Contact Near You");
			nearByContactFlowPanel = new FlowPanel();
			nearByContactFlowPanel.setSize("100%", "100%");
			innerHorizonPanel.add(nearByContactFlowPanel);
			titleHorizonPanel.add(nearByContact);
			nearByContact.setStylePrimaryName("contactSelectortitleLabel");
		}
	}

	private void addYourContactComponents() {
		if(isYourContact) {
			yourContact = new Label("Your Contacts");
			yourContactFlowPanel = new FlowPanel();
			yourContactFlowPanel.setSize("100%", "100%");
			innerHorizonPanel.add(yourContactFlowPanel);
			titleHorizonPanel.add(yourContact);
			yourContact.setStylePrimaryName("contactSelectortitleLabel");
		}
	}

	private void addontactMayKnownComponents() {
		if(isContactKnown) {
			contactMayKnown = new Label("Contacts you may known");
			contactMayKnownFlowPanel = new FlowPanel();
			contactMayKnownFlowPanel.setSize("100%", "100%");
			innerHorizonPanel.add(contactMayKnownFlowPanel);
			titleHorizonPanel.add(contactMayKnown);
			contactMayKnown.setStylePrimaryName("contactSelectortitleLabel");
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

	private void addTitle(Label label) {
		titleHorizonPanel.setCellWidth(label, "100%");
		titleHorizonPanel.addStyleName("contactSelectorTitlePanel");
	}

	private void addTitle(Label label1, Label label2) {
		titleHorizonPanel.setCellWidth(label1, "50%");
		titleHorizonPanel.setCellWidth(label2, "50%");
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

	public void initialize() {
		
		if(isNearByContact) {
			setNearByContactEntityList(contactSelectorModel.getEntityList());
			if(nearByContactEntityList != null && !nearByContactEntityList.isEmpty()) {
				Iterator<Entity> iterator = nearByContactEntityList.iterator();
				while(iterator.hasNext()) {
					Entity entity = iterator.next();
					ContactSnippet contactSnippet = new ContactSnippet(isSelectionAllowed);
					contactSnippet.setConfigurationForImageField(getImageFieldConfiguration());
					contactSnippet.initialize(entity);
					contactSnippet.addStyleName("flowPanelContent");
					nearByContactFlowPanel.add(contactSnippet);
				}
			}
		}

		if(isYourContact) {
			setYourContactEntityList(contactSelectorModel.getEntityList());
			if(yourContactEntityList != null && !yourContactEntityList.isEmpty()) {
				Iterator<Entity> iterator = yourContactEntityList.iterator();
				while(iterator.hasNext()) {
					Entity entity = iterator.next();
					ContactSnippet contactSnippet = new ContactSnippet(isSelectionAllowed);
					contactSnippet.setConfigurationForImageField(getImageFieldConfiguration());
					contactSnippet.initialize(entity);
					contactSnippet.addStyleName("flowPanelContent");
					yourContactFlowPanel.add(contactSnippet);
				}
			}
		}

		if(isContactKnown) {
			setContactMayKnownEntityList(contactSelectorModel.getEntityList());
			if(contactMayKnownEntityList != null && !contactMayKnownEntityList.isEmpty()) {
				Iterator<Entity> iterator = contactMayKnownEntityList.iterator();
				while(iterator.hasNext()) {
					Entity entity = iterator.next();
					ContactSnippet contactSnippet = new ContactSnippet(isSelectionAllowed);
					contactSnippet.setConfigurationForImageField(getImageFieldConfiguration());
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
	
	public Configuration getImageFieldConfiguration() {
		Configuration config = new Configuration();
		String userImage = "images/default_Icon.png";
		config.setPropertyByName(ImageField.IMAGEFIELD_BLOBID, userImage);
		config.setPropertyByName(ImageField.IMAGEFIELD_PRIMARYCSS, "defaultIcon");
		return config;
	}
}

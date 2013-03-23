package in.appops.client.common.contactmodel;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.SelectionEvent;
import in.appops.client.common.fields.ImageField;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class ContactSnippet extends Composite implements ClickHandler {
	
	private FocusPanel basePanel;
	private HorizontalPanel userDetailsPanel;
	private FlowPanel userNameFlowPanel;
	private Label userName;
	private ImageField imageField;
	private Entity entity;
	private boolean isSelected;
	private boolean isSelectionAllowed;
	
	public ContactSnippet(boolean isSelectionAllowed) {
		this.isSelectionAllowed = isSelectionAllowed;
		initialize();
		initWidget(basePanel);
	}

	private void initialize() {
		basePanel = new FocusPanel();
		userDetailsPanel = new HorizontalPanel();
		userNameFlowPanel = new FlowPanel();
		userName = new Label();
		imageField = new ImageField();
		
		userNameFlowPanel.add(userName);
		userNameFlowPanel.setWidth("120px");
		userDetailsPanel.add(userNameFlowPanel);
		userDetailsPanel.add(imageField);
		basePanel.add(userDetailsPanel);
		userDetailsPanel.setWidth("100%");
		basePanel.addClickHandler(this);
		userName.addStyleName("flowPanelContent");
		basePanel.setStylePrimaryName("contactSnippetBasePanel");
	}
	
	public void initialize(Entity entity) {
		this.entity = entity;
		String name = entity.getPropertyByName(ContactConstant.NAME).toString();
		userName.setText(name);
	}

	public FocusPanel getBasePanel() {
		return basePanel;
	}

	public Entity getEntity() {
		return entity;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public void onClick(ClickEvent event) {
		if(isSelectionAllowed) {
			SelectionEvent selectionEvent = new SelectionEvent();
			if(isSelected) {
				setSelected(false);
				selectionEvent.setEventType(SelectionEvent.DESELECTED);
				basePanel.removeStyleName("selectedContactSnippet");
			} else {
				setSelected(true);
				selectionEvent.setEventType(SelectionEvent.SELECTED);
				basePanel.addStyleName("selectedContactSnippet");
			}
			selectionEvent.setEventData(this);
			AppUtils.EVENT_BUS.fireEvent(selectionEvent);
		}
	}

	public void setConfigurationForImageField(Configuration config) {
		imageField.setConfiguration(config);
		try {
			imageField.createField();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}
}

package in.appops.client.common.contactmodel;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.SelectionEvent;
import in.appops.client.common.fields.ImageField;
import in.appops.client.common.fields.LabelField;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;
import in.appops.client.common.snippet.Snippet;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ContactSnippet extends Composite implements Snippet, ClickHandler {
	
	private FocusPanel basePanel;
	private HorizontalPanel userDetailsPanel;
	private FlowPanel userNameFlowPanel;
	private LabelField userName;
	private ImageField imageField;
	private Entity entity;
	private boolean isSelected;
	private boolean isSelectionAllowed;
	
	public ContactSnippet() {
		initialize();
		initWidget(basePanel);
	}
	
	public ContactSnippet(boolean isSelectionAllowed) {
		this.isSelectionAllowed = isSelectionAllowed;
		initialize();
		initWidget(basePanel);
		basePanel.setStylePrimaryName("contactSnippetBasePanel");
	}
    @Override
	public void initialize() {
		basePanel = new FocusPanel();
		userDetailsPanel = new HorizontalPanel();
		userNameFlowPanel = new FlowPanel();
		userName = new LabelField();
		imageField = new ImageField();
		
		userNameFlowPanel.add(userName);
		userNameFlowPanel.setWidth("120px");
		userDetailsPanel.add(userNameFlowPanel);
		userDetailsPanel.add(imageField);
		basePanel.add(userDetailsPanel);
		userDetailsPanel.setWidth("100%");
		basePanel.addClickHandler(this);
	}
	
	public void initialize(Entity entity) {
		this.entity = entity;
		String name = entity.getPropertyByName(ContactConstant.NAME).toString();
		userName.setFieldValue(name);
		userName.resetField();
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
	
	

	public void setConfigurationForFields(Configuration labelConfig, Configuration imageConfig) {

		try {
			userName.setConfiguration(labelConfig);
			userName.createField();
			imageField.setConfiguration(imageConfig);
            imageField.createField();
            imageField.addErrorHandler(new ErrorHandler() {
				
				@Override
				public void onError(ErrorEvent event) {
					imageField.setUrl("images/default_Icon.png");
				}
			});
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setEntity(Entity entity) {
		// TODO Auto-generated method stub
		
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
	public boolean isSelectionAllowed() {
		return isSelectionAllowed;
	}

	public void setSelectionAllowed(boolean isSelectionAllowed) {
		this.isSelectionAllowed = isSelectionAllowed;
	}
}

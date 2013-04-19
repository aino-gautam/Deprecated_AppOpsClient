package in.appops.client.common.snippet;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.SelectionEvent;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CardSnippet extends DockPanel implements Snippet,ClickHandler,MouseOverHandler,MouseOutHandler{
	
	private Entity entity;
	private String type;
	private Image checkMarkSelectedImage = new Image("images/checkMarkSelected.png");
	private Image checkMarkNotSelectedImage = new Image("images/checkMarkNotSelected.png");
	VerticalPanel checkMarkPanel = new VerticalPanel();
	private Configuration configuration;	
	
	public CardSnippet() {
		//initialize();
	}
	
			
	
	@Override
	public Widget asWidget() {
		return this;
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
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
		
	}

	@Override
	public void initialize() {
		addDomHandler(this, MouseOutEvent.getType());
		addDomHandler(this, MouseOverEvent.getType());
		
		checkMarkPanel.add(checkMarkNotSelectedImage);
		
		checkMarkNotSelectedImage.setStylePrimaryName("checkMarkImageInGridPanel");
		checkMarkSelectedImage.setStylePrimaryName("checkMarkImageInGridPanel");
		
		checkMarkPanel.setCellVerticalAlignment(checkMarkNotSelectedImage, ALIGN_TOP);
		
		checkMarkPanel.setWidth("12px");
		
		checkMarkNotSelectedImage.addClickHandler(this);
		checkMarkSelectedImage.addClickHandler(this);
		checkMarkNotSelectedImage.setVisible(false);
		
		if((Boolean)getConfiguration().getPropertyByName(SnippetConstant.SELECTIONMODE)){
			add(checkMarkPanel,DockPanel.EAST);
		}
				
		setSpacing(5);
		
		setStylePrimaryName("cardSnippetPanel");
	}

	@Override
	public void onClick(ClickEvent event) {
		
		Widget widget  = (Widget) event.getSource();
		SelectionEvent selectionEvent = new SelectionEvent();
		
		if(widget instanceof Image){
			if(widget.equals(checkMarkNotSelectedImage)){
				checkMarkPanel.remove(checkMarkNotSelectedImage);
				checkMarkPanel.add(checkMarkSelectedImage);
				checkMarkPanel.setCellVerticalAlignment(checkMarkSelectedImage, ALIGN_TOP);
				
				selectionEvent.setEventType(SelectionEvent.SELECTED);
				selectionEvent.setEventData(entity);
				
			}else if(widget.equals(checkMarkSelectedImage)){
				
				checkMarkPanel.remove(checkMarkSelectedImage);
				checkMarkPanel.add(checkMarkNotSelectedImage);
				checkMarkPanel.setCellVerticalAlignment(checkMarkNotSelectedImage, ALIGN_TOP);
				selectionEvent.setEventType(SelectionEvent.DESELECTED);
				selectionEvent.setEventData(entity);
			}
			
			AppUtils.EVENT_BUS.fireEvent(selectionEvent);
			
		}
		
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
		
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}



	@Override
	public void onMouseOver(MouseOverEvent event) {
		checkMarkNotSelectedImage.setVisible(true);
	}



	@Override
	public void onMouseOut(MouseOutEvent event) {
		checkMarkNotSelectedImage.setVisible(false);
		
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
	
	public void selectSnippet(){
		//if(!checkMarkSelectedImage.isVisible()){
			checkMarkPanel.remove(checkMarkNotSelectedImage);
			checkMarkPanel.add(checkMarkSelectedImage);
			checkMarkPanel.setCellVerticalAlignment(checkMarkSelectedImage, ALIGN_TOP);
		//}
		
	}
	
	public void deSelectSnippet(){
		//if(checkMarkSelectedImage.isVisible()){
			checkMarkPanel.remove(checkMarkSelectedImage);
			checkMarkPanel.add(checkMarkNotSelectedImage);
			checkMarkPanel.setCellVerticalAlignment(checkMarkNotSelectedImage, ALIGN_TOP);
		//}
	}

}
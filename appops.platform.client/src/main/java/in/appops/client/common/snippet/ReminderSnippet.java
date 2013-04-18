package in.appops.client.common.snippet;

import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.LinkField;
import in.appops.client.common.snippet.Snippet;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.server.core.services.calendar.constant.ReminderConstant;
import in.appops.platform.server.core.services.calendar.constant.ReminderTypeConstant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ReminderSnippet extends VerticalPanel implements Snippet , ClickHandler{
	
	private Image crossImg = new Image("images/crossImage.png");
	private LinkField editLinkField = new LinkField();
	private final DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
	private final DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
	private Entity embededEntity;
	private Entity entity;
	private String type;
		
	public ReminderSnippet() {
	}
	
	
	public ReminderSnippet(Entity reminder) {
		this.entity = reminder;
	}
	
	@Override
	public void initialize(){
		
		Entity entity = getEntity();
		
		try {
			String title = entity.getProperty(ReminderConstant.TITLE).getValue().toString();
			
			LinkField reminderTitleLink = new LinkField();
			reminderTitleLink.setFieldValue(title);
			reminderTitleLink.setConfiguration(getLinkFieldConfiguration(LinkField.LINKFIELDTYPE_HYPERLINK, "postLink", "reminderTitleLabel", null));
			reminderTitleLink.createField();
						
			//reminderTitleLink.getWidget().addDomHandler(this, ClickEvent.getType());
			
			
			
			/*reminderTitleLink = new Anchor(title);
			reminderTitleLink.addClickHandler(this);
			reminderTitleLink.setStylePrimaryName("appops-LinkField");
			reminderTitleLink.addStyleName("reminderTitleLabel");*/
			
								
			HorizontalPanel reminderTitlePanel = new HorizontalPanel();
			reminderTitlePanel.add(reminderTitleLink);
			reminderTitlePanel.add(crossImg);
			crossImg.setHeight("14px");
			reminderTitlePanel.setCellHorizontalAlignment(crossImg, HasHorizontalAlignment.ALIGN_RIGHT);
			reminderTitlePanel.setStylePrimaryName("reminderTitlePanel");
			
						
			add(reminderTitlePanel);
			
			LabelField dateTimeLbl = getLabelField("Remind me on:","postLabel");
			
			Date date = (Date) entity.getProperty(ReminderConstant.REMINDERTIME).getValue();
			
			String dateTimeValue = DateTimeFormat.getLongDateFormat().format(date) +" at "+ DateTimeFormat.getShortTimeFormat().format(date);
					
			LabelField dateTime = getLabelField(dateTimeValue,"postLabel");
			
			LabelField typeLabel = getLabelField("Remind me via:","postLabel");
					
			Entity reminderType = (Entity) entity.getProperty(ReminderConstant.REMINDERTYPE);
					
			LabelField type = getLabelField(reminderType.getProperty(ReminderTypeConstant.TYPE).getValue().toString(),"postLabel");
					
			add(typeLabel);
			
			FlexTable flex = new FlexTable();
			flex.setWidget(0, 0, dateTimeLbl);
			flex.setWidget(0, 1, dateTime);
			flex.setWidget(1, 0, typeLabel);
			flex.setWidget(1, 1, type);
			add(flex);
			
			editLinkField.setFieldValue("Edit");
			editLinkField.setConfiguration(getLinkFieldConfiguration(LinkField.LINKFIELDTYPE_HYPERLINK, "postLink", null, null));
			editLinkField.createField();
			
			HorizontalPanel linkPanel = new HorizontalPanel();
			linkPanel.add(editLinkField);
			//snippetPanel.add(editLinkField);
			
			//addDomHandler(this,ClickEvent.getType());
			
					
			setStylePrimaryName("snippetPanel");
		} catch (AppOpsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Configuration getLinkFieldConfiguration(String linkFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LinkField.LINKFIELD_TYPE, linkFieldType);
		configuration.setPropertyByName(LinkField.LINKFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	private LabelField getLabelField(String lblText,String primaryCss) throws AppOpsException{
		LabelField label = new LabelField();
		label.setFieldValue(lblText);
		Configuration config = getLabelFieldConfiguration(true,primaryCss,null,null);
		label.setConfiguration(config);
		label.createField();
		return label;
		
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
	public void onClick(ClickEvent event) {
		Widget widget = (Widget) event.getSource();
					
	}
	
	@SuppressWarnings("unchecked")
	private Entity getEmbededEntity(Entity reminderEntity){
			
		Map parameters = new HashMap();
		parameters.put("reminderEntity", reminderEntity);
		
		StandardAction action = new StandardAction(Entity.class, "calendar.CalendarService.getEmbededEntityForReminder", parameters);
		dispatch.execute(action, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Operation failed");
			}

			@Override
			public void onSuccess(Result result) {
				embededEntity = (Entity) result.getOperationResult();
				
			}
		});
		return embededEntity;
	}
	
	private String constructLink(Entity entity){
		String serviceId  = entity.getProperty(ReminderConstant.SERVICEID).getValue().toString();
		String typeId  = entity.getProperty(ReminderConstant.TYPEID).getValue().toString();
		String instanceId  = entity.getProperty(ReminderConstant.INSTANCEID).getValue().toString();
		String WidgetName  = entity.getProperty("widgetName").getValue().toString();
		
		String link = "serviceId="+serviceId+"typeId="+typeId+"instanceId="+instanceId+"WidgetName="+WidgetName;
		
		return link;
		
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

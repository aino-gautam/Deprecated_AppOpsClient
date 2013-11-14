package in.appops.client.common.snippet;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.fields.LinkField;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.calendar.constant.EventConstant;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class EventSnippet extends VerticalPanel implements Snippet{

	private LinkField eventNameLinkField;
	private Entity entity;
	private String type;
	
	public EventSnippet() {
		// TODO Auto-generated constructor stub
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
		FlexTable eventSnippetFlexTable = new FlexTable();
		
		try{
		
		String eventName=entity.getPropertyByName(EventConstant.NAME);
		
		eventNameLinkField = new LinkField();
		eventNameLinkField.setFieldValue(eventName);
		eventNameLinkField.setConfiguration(getLinkFieldConfiguration(LinkField.LINKFIELDTYPE_ANCHOR, "reminderTitleLabel", "crossImageCss", null));
		eventNameLinkField.configure();
		eventNameLinkField.create();
		
		HorizontalPanel evenNameHorizontalPanel = new HorizontalPanel();
		evenNameHorizontalPanel.add(eventNameLinkField);
		
		add(evenNameHorizontalPanel);
		
		Date fromDate=entity.getPropertyByName(EventConstant.FROMDATE);
		Date fromTime=entity.getPropertyByName(EventConstant.FROMTIME);
		//TODO :time also take from entity and append to date(both from & to) 		
		
		DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy");
		String formatedFromDate = fmt.getFormat("dd-MM-yyyy").format(fromDate);
		String formatedFromTime = fmt.getFormat("HH:mm:ss").format(fromTime);
		formatedFromDate = formatedFromDate +" "+formatedFromTime;
		
		LabelField fromDateLabelField = new LabelField();
		fromDateLabelField.setFieldValue("From :");
		Configuration configFromDateLabelField = getLabelFieldConfiguration(true,"postLabel",null,null);
		fromDateLabelField.setConfiguration(configFromDateLabelField);
		fromDateLabelField.configure();
		fromDateLabelField.create();
		
		
		LabelField fromDateLabel = new LabelField();
		fromDateLabel.setFieldValue(formatedFromDate.toString());
		Configuration config = getLabelFieldConfiguration(true,"postLabel",null,null);
		fromDateLabel.setConfiguration(config);
		fromDateLabel.configure();
		fromDateLabel.create();
		
		
		LabelField dashLabel = new LabelField();
		dashLabel.setFieldValue("-");
		Configuration configdashLabel = getLabelFieldConfiguration(true,"postLabel",null,null);
		dashLabel.setConfiguration(configdashLabel);
		dashLabel.configure();
		dashLabel.create();
		
		
		Date toDate=entity.getPropertyByName(EventConstant.TODATE);
		Date toTime=entity.getPropertyByName(EventConstant.TOTIME);
		//Date formatedToDate = fmt.parse(toDate.toString());
		String formatedToDate = fmt.getFormat("dd-MM-yyyy").format(toDate);
		String formatedToTime = fmt.getFormat("HH:mm:ss").format(toTime);
		
		formatedToDate = formatedToDate +" "+ formatedToTime;
		
		LabelField toDateLabelField = new LabelField();
		toDateLabelField.setFieldValue("To :");
		Configuration configTODateLabelField = getLabelFieldConfiguration(true,"postLabel",null,null);
		toDateLabelField.setConfiguration(configTODateLabelField);
		toDateLabelField.configure();
		toDateLabelField.create();
		
		
		LabelField toDateLabel = new LabelField();
		toDateLabel.setFieldValue(formatedToDate.toString());
		Configuration configToDate = getLabelFieldConfiguration(true,"postLabel",null,null);
		toDateLabel.setConfiguration(configToDate);
		toDateLabel.configure();
		toDateLabel.create();
		
		eventSnippetFlexTable.setWidget(0, 0, fromDateLabelField);
		eventSnippetFlexTable.setWidget(0, 2, fromDateLabel);
		
		eventSnippetFlexTable.setWidget(0, 4, dashLabel);
		
		eventSnippetFlexTable.setWidget(0, 6, toDateLabelField);
		eventSnippetFlexTable.setWidget(0, 8, toDateLabel);
		
		
		
		add(eventSnippetFlexTable);
		setStylePrimaryName("snippetPanel");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		configuration.setPropertyByName(LabelFieldConstant.BF_DCLS, secondaryCss);
		return configuration;
	}
	
	private Configuration getLinkFieldConfiguration(String linkFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LinkField.LINKFIELD_TYPE, linkFieldType);
		configuration.setPropertyByName(LinkField.LINKFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LinkField.LINKFIELD_DEBUGID, debugId);
		return configuration;
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

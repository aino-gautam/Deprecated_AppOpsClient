package in.appops.client.common.config.breadcrumb;

import in.appops.client.common.config.field.LinkField;
import in.appops.client.common.config.field.LinkField.LinkFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.ListIterator;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BreadcrumbPopup extends PopupPanel implements FieldEventHandler{

	private VerticalPanel basePanel;
	private HandlerRegistration fieldEventHandler= null;
	private EntityList entitylist;
	private int levelNo;
	private BreadcrumbPopup selfReference;
	
	private final String BCPOPUPCSS = "breadCrumbPopup";
	private final String BRSPRIMARY_LINK_CSS = "breadcrumbLink";
	private final String BRS_LINK_BASE_CSS = "breadcrumbLinkBase";
	
	public BreadcrumbPopup(EntityList childlist) {
		super(true);
		selfReference = this;
		this.entitylist=childlist;
		if(fieldEventHandler ==null)
			fieldEventHandler = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}

	public void createUI(){
		setStylePrimaryName(BCPOPUPCSS);
		basePanel= new VerticalPanel();
		ListIterator<Entity> itr= entitylist.listIterator();
		while(itr.hasNext()){
			Entity entity=itr.next();
			LinkField link= new LinkField();
			link.setEntity(entity);
			link.setConfiguration(getLinkFieldConfiguration());
			link.configure();
			link.create();
			basePanel.add(link);
		}
		setWidget(basePanel);
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
			if(event.getEventType() ==  FieldEvent.CLICKED){
				if(event.getEventSource() instanceof LinkField){
					String name = ((LinkField)event.getEventSource()).getValue().toString();
					if(entitylist !=null) {
						ListIterator<Entity> itr= entitylist.listIterator();
						while(itr.hasNext()){
							Entity entity=itr.next();
							if(entity.getPropertyByName("name").toString().equals(name)){
								hide();
								HashMap<String, Object> valueMap = new HashMap<String, Object>();
								valueMap.put("LEVEL", levelNo);
								valueMap.put("ENTITY", entity);
								
								FieldEvent breadCrumbEvent = new FieldEvent(FieldEvent.BREACRUMBUPDATE, valueMap);
								breadCrumbEvent.setEventSource(selfReference);
								AppUtils.EVENT_BUS.fireEvent(breadCrumbEvent);
							}
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Configuration getLinkFieldConfiguration(){
		Configuration configuration= new Configuration();
		
		configuration.setPropertyByName(LinkFieldConstant.BF_BINDPROP, "name");
		configuration.setPropertyByName(LinkFieldConstant.BF_PCLS, BRSPRIMARY_LINK_CSS);
		configuration.setPropertyByName(LinkFieldConstant.BF_BASEPANEL_PCLS, BRS_LINK_BASE_CSS);
		
		return configuration;
	}

	public void setLevelNo(int levelNo) {
		this.levelNo = levelNo;
	}
}

package in.appops.client.common.config.breadcrumb;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * @author Kamlakar
 * reworked by : mahesh@ensarm.com
 */
public class BreadcrumbView extends Composite implements FieldEventHandler{

	private FlowPanel baseFlowPanel;
	private Configuration breadcrumbConfig;
	
	private Entity actionEnt= new Entity();
	public Logger logger = Logger.getLogger(getClass().getName());

	public BreadcrumbView(){
		initialize();
	}
	
	private void initialize() {
		try{
			baseFlowPanel= new FlowPanel();
			initWidget(baseFlowPanel);
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void configure() {
			
		
	}
 
	public void create(){
		try {
			logger.log(Level.INFO, "[BreadcrumbComponent] ::In create method ");
			BreadcrumbSnippet component = new BreadcrumbSnippet();
			component.setActionEntity(actionEnt);
			component.create();
			baseFlowPanel.add(component);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[BreadcrumbComponent] ::Exception In create method "+e);
		}
	}

	/**
	 * @return the breadcrumbConfig
	 */
	public Configuration getBreadcrumbConfig() {
		return breadcrumbConfig;
	}

	/**
	 * @param breadcrumbConfig the breadcrumbConfig to set
	 */
	public void setBreadcrumbConfig(Configuration breadcrumbConfig) {
		this.breadcrumbConfig = breadcrumbConfig;
	}

	/**
	 * @return the actionEnt
	 */
	public Entity getActionEnt() {
		return actionEnt;
	}

	/**
	 * @param actionEnt the actionEnt to set
	 */
	public void setActionEnt(Entity actionEnt) {
		this.actionEnt = actionEnt;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
			if(event.getEventType() == FieldEvent.BREACRUMBUPDATE){
				Entity entity = (Entity) event.getEventData();
				BreadcrumbSnippet component = new BreadcrumbSnippet();
				component.setActionEntity(entity);
				component.create();
				baseFlowPanel.add(component);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}

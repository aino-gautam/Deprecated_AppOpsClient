package in.appops.client.common.config.breadcrumb;

import in.appops.client.common.config.model.EntityListModel;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	public static final String PCSS = "breadCrumbPcss";
	public static final String SNIPPETCONIFG = "breadCrumbSnippetConfig";
	
	private Entity actionEnt= new Entity();
	public Logger logger = Logger.getLogger("BreadcrumbView");
	private EntityListModel entityListModel;
	int counter =0;
	HashMap<Integer, String> levelNameMap;
	
	public BreadcrumbView(){
		initialize();
	}
	
	private void initialize() {
		try{
			baseFlowPanel= new FlowPanel();
			levelNameMap = new HashMap<Integer, String>();
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
			counter = 0;
			logger.log(Level.INFO, "[BreadcrumbComponent] ::In create method ");
	
			BreadcrumbSnippet component = new BreadcrumbSnippet();
			component.setActionEntity(actionEnt);
			component.setLevelNo(counter);
			component.create();
			baseFlowPanel.add(component);
			counter++;
			levelNameMap.put(counter, actionEnt.getPropertyByName("name").toString());
			
		/*	BreadcrumbSnippet breadCrumbSnippet = new BreadcrumbSnippet();
			Configuration snippetConfig = breadcrumbConfig.getPropertyByName(SNIPPETCONIFG);
			breadCrumbSnippet.setBreadCrumbSnippetConfig(snippetConfig);

			breadCrumbSnippet.create();
			baseFlowPanel.add(breadCrumbSnippet);*/
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
				HashMap<String, Object> valueMap = (HashMap<String, Object>) event.getEventData();
				Entity entity = (Entity) valueMap.get("ENTITY");
				int levelNo = (Integer) valueMap.get("LEVEL");
				
				updateBreadcrumb(levelNo);
				
				BreadcrumbSnippet component = new BreadcrumbSnippet();
				component.setActionEntity(entity);
				component.setLevelNo(counter);
				component.create();
				baseFlowPanel.add(component);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateBreadcrumb(int levelNo) {
		try{
			if(!(counter>levelNo)){
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setModel(EntityListModel model) {
		this.entityListModel = model;
	}

	public void setConfiguration(Configuration breadCrumbConfig2) {
		this.breadcrumbConfig = breadCrumbConfig2;
	}

}

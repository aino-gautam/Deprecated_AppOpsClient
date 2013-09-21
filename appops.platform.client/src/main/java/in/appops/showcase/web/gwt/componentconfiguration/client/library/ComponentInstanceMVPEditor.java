/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import java.util.ArrayList;

import in.appops.platform.core.entity.Entity;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author mahesh@nesarm.com
 *
 */
public class ComponentInstanceMVPEditor extends Composite {

	private TabPanel configuratorInstanceBasePanel;
	private Entity modelInstanceEnt;
	private Entity viewInstanceEnt;
	private Entity configInstEnt;
	private Entity pageCompInstEntity;
	private Entity pageEntity;
	private ArrayList<Widget> instanceMVPEditorsList = null;
	
	
	private final String MODEL = "model";
	private final String VIEW = "view";
	
	public ComponentInstanceMVPEditor() {
		initialize();
	}
	
	public void initialize() {
		configuratorInstanceBasePanel = new TabPanel();
		initWidget(configuratorInstanceBasePanel);
	}
	
	public void deregisterHandler(){
		
		if(instanceMVPEditorsList!= null)
			deregisterPreviousInstances();
	}

	public void createUi() {
		try{
			configuratorInstanceBasePanel.setStylePrimaryName("fullWidth");
			
			if(instanceMVPEditorsList ==null)
				instanceMVPEditorsList = new ArrayList<Widget>();
			
			deregisterPreviousInstances();
			
			if(modelInstanceEnt != null) {
				configuratorInstanceBasePanel.add(getModelConfigInstanceEditor(), MODEL);
			}
			if(viewInstanceEnt != null) {
				configuratorInstanceBasePanel.add(getViewConfigInstanceEditor(), VIEW);
			}
			configuratorInstanceBasePanel.selectTab(0);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Widget getModelConfigInstanceEditor() {
		ModelConfigurationInstanceEditor modConfigInstanceEditor = new ModelConfigurationInstanceEditor();
		modConfigInstanceEditor.setModelConfigInstance(modelInstanceEnt);
		modConfigInstanceEditor.setPageEntity(pageEntity);
		modConfigInstanceEditor.createUi();
		modConfigInstanceEditor.fetchModel();
		instanceMVPEditorsList.add(modConfigInstanceEditor);
		return modConfigInstanceEditor;
	}
	
	public ViewComponentInstanceEditor getViewConfigInstanceEditor() {
		ViewComponentInstanceEditor viewConfigInstanceEditor = new ViewComponentInstanceEditor();
		viewConfigInstanceEditor.setPageEntity(pageEntity);
		viewConfigInstanceEditor.setViewConfigInstnceEntity(viewInstanceEnt);
		viewConfigInstanceEditor.setParentCompInstanceEnt(pageCompInstEntity);
		viewConfigInstanceEditor.createUi();
		instanceMVPEditorsList.add(viewConfigInstanceEditor);
		return viewConfigInstanceEditor;
	}
	
	private void deregisterPreviousInstances(){
		for(Widget mvpInstance :instanceMVPEditorsList){
			if(mvpInstance instanceof ModelConfigurationInstanceEditor){
				((ModelConfigurationInstanceEditor)mvpInstance).deregisterHandler();
			}else if(mvpInstance instanceof ViewConfigurationInstanceEditor){
				((ViewComponentInstanceEditor)mvpInstance).deregisterHandler();
			}
		}
		instanceMVPEditorsList.clear();
	}

	public Entity getModelInstanceEnt() {
		return modelInstanceEnt;
	}

	public void setModelInstanceEnt(Entity modelInstanceEnt) {
		this.modelInstanceEnt = modelInstanceEnt;
	}

	public Entity getViewInstanceEnt() {
		return viewInstanceEnt;
	}

	public void setViewInstanceEnt(Entity viewInstanceEnt) {
		this.viewInstanceEnt = viewInstanceEnt;
	}

	public Entity getConfigInstEnt() {
		return configInstEnt;
	}

	public void setConfigInstEnt(Entity configInstEnt) {
		this.configInstEnt = configInstEnt;
	}

	/**
	 * @return the pageCompInstEntity
	 */
	public Entity getPageCompInstEntity() {
		return pageCompInstEntity;
	}

	/**
	 * @param pageCompInstEntity the pageCompInstEntity to set
	 */
	public void setPageCompInstEntity(Entity pageCompInstEntity) {
		this.pageCompInstEntity = pageCompInstEntity;
	}

	public Entity getPageEntity() {
		return pageEntity;
	}

	public void setPageEntity(Entity pageEntity) {
		this.pageEntity = pageEntity;
	}
	
}

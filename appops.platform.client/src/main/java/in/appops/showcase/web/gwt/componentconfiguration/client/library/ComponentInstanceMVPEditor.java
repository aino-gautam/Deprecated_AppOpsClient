/**
 * 
 */
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

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
	
	
	private final String MODEL = "model";
	private final String VIEW = "view";
	
	public ComponentInstanceMVPEditor() {
		initialize();
	}
	
	public void initialize() {
		configuratorInstanceBasePanel = new TabPanel();
		initWidget(configuratorInstanceBasePanel);
	}
	
	public void createUi() {
		try{
			configuratorInstanceBasePanel.setStylePrimaryName("fullWidth");
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
		return modConfigInstanceEditor;
	}
	
	public ViewComponentInstanceEditor getViewConfigInstanceEditor() {
		ViewComponentInstanceEditor viewConfigInstanceEditor = new ViewComponentInstanceEditor();
		viewConfigInstanceEditor.setPageEntity(pageEntity);
		viewConfigInstanceEditor.setViewConfigInstnceEntity(viewInstanceEnt);
		viewConfigInstanceEditor.setParentCompInstanceEnt(pageCompInstEntity);
		viewConfigInstanceEditor.createUi();
		return viewConfigInstanceEditor;
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

package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.platform.core.entity.Entity;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConfigurationInstanceMVPEditor extends Composite{
	private TabPanel configuratorInstanceBasePanel;
	private Entity modelInstanceEnt;
	private Entity viewInstanceEnt;
	private Entity configInstEnt;
	private Entity pageEntity;
	
	private final String MODEL = "model";
	private final String VIEW = "view";
	
	public ConfigurationInstanceMVPEditor() {
		initialize();
	}
	
	public void initialize() {
		configuratorInstanceBasePanel = new TabPanel();
		initWidget(configuratorInstanceBasePanel);
	}
	
	public void createUi() {
		
		if(configuratorInstanceBasePanel!=null)
			configuratorInstanceBasePanel.clear();
		
		configuratorInstanceBasePanel.setStylePrimaryName("fullWidth");
		if(modelInstanceEnt != null) {
			configuratorInstanceBasePanel.add(getModelConfigInstanceEditor(), MODEL);
		}
		if(viewInstanceEnt != null) {
			configuratorInstanceBasePanel.add(getViewConfigInstanceEditor(), VIEW);
		}
		configuratorInstanceBasePanel.selectTab(0);
	}
	
	private Widget getModelConfigInstanceEditor() {
		ModelConfigurationInstanceEditor modConfigInstanceEditor = new ModelConfigurationInstanceEditor();
		modConfigInstanceEditor.setModelConfigInstance(modelInstanceEnt);
		modConfigInstanceEditor.setPageEntity(pageEntity);
		modConfigInstanceEditor.createUi();
		modConfigInstanceEditor.fetchModel();
		return modConfigInstanceEditor;
	}
	
	public ViewConfigurationInstanceEditor getViewConfigInstanceEditor() {
		ViewConfigurationInstanceEditor viewConfigInstanceEditor = new ViewConfigurationInstanceEditor(viewInstanceEnt);
		viewConfigInstanceEditor.setPageEntity(pageEntity);
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

	public Entity getPageEntity() {
		return pageEntity;
	}

	public void setPageEntity(Entity pageEntity) {
		this.pageEntity = pageEntity;
	}
	
}

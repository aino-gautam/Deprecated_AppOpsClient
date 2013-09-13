package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.platform.core.entity.Entity;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class will let one add / edit htmlsnippet properties.
 * The html snippet is a MVP, hence it has 3 top level configuration viz. model, view, presenter.
 * Each of this is represented by one tab, where specific configurations are added, edited and displayed.
 * @author nitish@ensarm.com
 */
public class HTMLSnippetConfigurationEditor extends Composite implements SelectionHandler{
	private TabPanel configuratorBasePanel;
	private Entity modelConfigurationType;
	private Entity viewConfigurationType;
	private Entity presenterConfigurationType;
	
	private ViewConfigurationEditor viewConfigEditor;
	
	private final String MODEL = "model";
	private final String VIEW = "view";
	
	public HTMLSnippetConfigurationEditor() {
		initialize();
	}
	
	public void initialize() {
		configuratorBasePanel = new TabPanel();
		viewConfigEditor = new ViewConfigurationEditor();
		configuratorBasePanel.addSelectionHandler(this);
		initWidget(configuratorBasePanel);
	}
	
	public void createUi() {
		viewConfigEditor.setViewConfigTypeEntity(viewConfigurationType);
		configuratorBasePanel.setStylePrimaryName("fullWidth");
		configuratorBasePanel.add(getModelEditor(), MODEL);
		configuratorBasePanel.add(getViewEditor(), VIEW);
		configuratorBasePanel.selectTab(0);
	}
	
	private Widget getModelEditor() {
		ModelConfigurationEditor modConfigEditor = new ModelConfigurationEditor();
		modConfigEditor.setModelConfigType(modelConfigurationType);
		modConfigEditor.createUi();
		return modConfigEditor;
	}
	
	public ViewConfigurationEditor getViewEditor() {
		return viewConfigEditor;
	}
	
	public Entity getModelConfigurationType(){
		return this.modelConfigurationType;
	}
	
	public void setModelConfigurationType(Entity modelConfigurationType) {
		this.modelConfigurationType = modelConfigurationType;
	}

	public Entity getViewConfigurationType(){
		return this.viewConfigurationType;
	}
	
	public void setViewConfigurationType(Entity viewConfigurationType) {
		this.viewConfigurationType = viewConfigurationType;
	}

	@Override
	public void onSelection(SelectionEvent event) {
		Integer i = (Integer)event.getSelectedItem();
		if (configuratorBasePanel.getTabBar().getTabHTML(i).equalsIgnoreCase(VIEW)){
			viewConfigEditor.createUI();
		}
		
	}

}
package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.platform.core.entity.Entity;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConfigurationInstanceMVPEditor extends Composite{
	/*private VerticalPanel confInstBasePanel;
	private VerticalPanel instanceEditorPanel;
	private ListBoxField widgetInstListField;
	private TextField widgetTypeTextField;
	private Map<String, String> widgetInstTypeMap;
	
	public ConfigurationInstanceEditor() {
		initialize();
	}
	
	private void initialize() {
		confInstBasePanel = new VerticalPanel();
		instanceEditorPanel = new VerticalPanel();
		widgetInstListField = new ListBoxField();
		widgetTypeTextField = new TextField();
	}
	
	public void createUi() {
		createWidgetSelector();
		confInstBasePanel.add(instanceEditorPanel);
	}

	private void createWidgetSelector() {
		HorizontalPanel selectorPanel = new HorizontalPanel();
		
		widgetInstListField = new ListBoxField();
		widgetInstListField.setConfiguration(getInstanceListBoxConfig());
		widgetInstListField.configure();
		widgetInstListField.create();
		
		selectorPanel.add(widgetInstListField);
		
		selectorPanel.add(widgetTypeTextField);
		
		confInstBasePanel.add(selectorPanel);
	}
	
	private Configuration getInstanceListBoxConfig() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--- Select the instance of widget ---");
			
			if(getInstanceList() != null && !getInstanceList().isEmpty()) {
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS, getInstanceList());
			}
		} catch (Exception e) {
			
		}
		return configuration;
	}
	
	private ArrayList<String> getInstanceList() {
		ArrayList<String> instanceList = null;
		
		if(widgetInstTypeMap != null && !widgetInstTypeMap.isEmpty()) {
			instanceList = new ArrayList<String>();
			for(Map.Entry<String, String> widgetEnt : widgetInstTypeMap.entrySet()) {
				String widgetInst = widgetEnt.getKey();
				instanceList.add(widgetInst);
			}
		}
		return instanceList;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		
		if(event.getEventSource().equals(widgetInstListField)) {
			SelectedItem selectedItem = (SelectedItem) event.getEventData();
			String instance = selectedItem.getItemString();
			String type = widgetInstTypeMap.get(instance);
			widgetTypeTextField.setValue(type);
			
			HTMLSnippetConfigurationEditor confEditor = new HTMLSnippetConfigurationEditor();
			confEditor.setEditorMode(EditorMode.CONFINSTANCE);
			confEditor.createUi();
			instanceEditorPanel.clear();
			instanceEditorPanel.add(confEditor);
		}
	}*/
	
	private TabPanel configuratorInstanceBasePanel;
	private Entity modelInstanceEnt;
	private Entity viewInstanceEnt;
	private Entity configInstEnt;
	
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
		configuratorInstanceBasePanel.setStylePrimaryName("fullWidth");
		if(modelInstanceEnt != null) {
			configuratorInstanceBasePanel.add(getModelConfigInstanceEditor(), MODEL);
		}
		if(viewInstanceEnt != null) {
			//	configuratorInstanceBasePanel.add(getViewConfigInstanceEditor(), VIEW);
		}
		configuratorInstanceBasePanel.selectTab(0);
	}
	
	private Widget getModelConfigInstanceEditor() {
		ModelConfigurationInstanceEditor modConfigInstanceEditor = new ModelConfigurationInstanceEditor();
		modConfigInstanceEditor.setModelConfigInstance(modelInstanceEnt);
		modConfigInstanceEditor.createUi();
		modConfigInstanceEditor.fetchModel();
		return modConfigInstanceEditor;
	}
	
	public ViewConfigurationInstanceEditor getViewConfigInstanceEditor() {
		ViewConfigurationInstanceEditor viewConfigInstanceEditor = new ViewConfigurationInstanceEditor(viewInstanceEnt, configInstEnt);
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
	
}

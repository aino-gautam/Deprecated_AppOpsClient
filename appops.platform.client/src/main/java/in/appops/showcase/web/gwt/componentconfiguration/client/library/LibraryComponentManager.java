package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.shared.Configuration;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LibraryComponentManager extends Composite implements ConfigEventHandler{
	
	private VerticalPanel basePanel;
	private ConfigurationEditor configurationEditor;
	private Logger logger = Logger.getLogger("LibraryComponentManager");
	
	/** Field id**/
	public static final String LIBRARYLISTBOX_ID = "libraryListBoxId";
	public final String LIBRARYLISTBOX_PCLS = "libraryListBoxField";
	
	public LibraryComponentManager() {
		
	}

	public void initialize() {
		try {
			basePanel = new VerticalPanel();
			
			ListBoxField libraryBox = new ListBoxField();
			libraryBox.setConfiguration(getLibraryListBoxConfiguration());
			libraryBox.configure();
			libraryBox.create();
									
			ComponentManager componentManager = new ComponentManager();
			componentManager.createUi();
			
			configurationEditor = new ConfigurationEditor();
			
			basePanel.add(libraryBox);
			basePanel.add(componentManager);
			basePanel.add(configurationEditor);
			
			AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
			initWidget(basePanel);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "LibraryComponentManager :: initialize :: Exception", e);
		}
	}
	
	private Configuration getLibraryListBoxConfiguration() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,LIBRARYLISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getLibraries");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_PCLS,LIBRARYLISTBOX_PCLS);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--- Please select a library---");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "LibraryComponentManager :: getLibraryListBoxConfiguration :: Exception", e);
		}
		return configuration;
	}

	@Override
	public void onConfigEvent(ConfigEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			
			case ConfigEvent.NEW_COMPONENT_REGISTERED: {
				/*configurationEditor.createUi();	
				HashMap<String, Entity> map = (HashMap<String, Entity>) event.getEventData();
				Entity configTypeEntity   = map.get("componentConfigType");
				parentConfTypeEnt = configTypeEntity;*/
			}case ConfigEvent.COMPONENTSELECTED: {
				
				HashMap<String, Object> map = (HashMap<String, Object>) event.getEventData();
				Entity componentEntity   = (Entity) map.get("component");
				Entity configTypeEnt = new Entity();
				configTypeEnt.setType(new MetaType("Configtype"));
				
				Key<Long> key = new Key<Long>(Long.parseLong(componentEntity.getPropertyByName("configtypeId").toString()));
				
				configTypeEnt.setPropertyByName("id", key);
				
				configurationEditor.createUi(componentEntity, configTypeEnt);
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

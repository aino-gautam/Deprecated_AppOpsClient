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

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LibraryComponentManager extends Composite implements ConfigEventHandler{
	
	private VerticalPanel basePanel;
	private ConfigurationEditor configurationEditor;
	private Logger logger = Logger.getLogger("LibraryComponentManager");
	private ComponentManager componentManager = null;
	private HandlerRegistration configEventhandler;
	
	/** Field id**/
	public static final String LIBRARYLISTBOX_ID = "libraryListBoxId";
	public final String LIBRARYLISTBOX_PCLS = "libraryListBoxField";
	
	public LibraryComponentManager() {
		
	}
	
	public void deregisterHandler(){
		try {
			if(componentManager!=null)
				componentManager.deregisterHandler();
			if(configurationEditor!=null)
				configurationEditor.deregisterHandler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialize() {
		try {
			basePanel = new VerticalPanel();
			ScrollPanel scrollPanel = new ScrollPanel(basePanel);
			
			ListBoxField libraryBox = new ListBoxField();
			libraryBox.setConfiguration(getLibraryListBoxConfiguration());
			libraryBox.configure();
			libraryBox.create();
									
			componentManager = new ComponentManager();
			componentManager.createUi();
			
			configurationEditor = new ConfigurationEditor(this);
			
			basePanel.add(libraryBox);
			basePanel.add(componentManager);
			basePanel.add(configurationEditor);
			
			
			basePanel.setCellHeight(libraryBox, "10%");
			basePanel.setCellHeight(componentManager, "45%");
			basePanel.setCellHeight(configurationEditor, "45%");
						
			basePanel.setSize("100%", "100%");
			
			int width = Window.getClientWidth() - 70;
			int height = Window.getClientHeight() - 100;
			
			int toolBarWidth = (width/7);
			scrollPanel.setSize((width-toolBarWidth)+"px", height+"px");
			
			if(configEventhandler==null)
				configEventhandler = AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
			initWidget(scrollPanel);
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
				break;
			}case ConfigEvent.COMPONENTSELECTED: {
				
				HashMap<String, Object> map = (HashMap<String, Object>) event.getEventData();
				Entity componentEntity   = (Entity) map.get("component");
				Entity configTypeEnt = new Entity();
				configTypeEnt.setType(new MetaType("Configtype"));
				
				Key<Long> key = new Key<Long>(Long.parseLong(componentEntity.getPropertyByName("configtypeId").toString()));
				
				configTypeEnt.setPropertyByName("id", key);
				
				configurationEditor.createUi(componentEntity, configTypeEnt);
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

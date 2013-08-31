package in.appops.showcase.web.gwt.registercomponent.client;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ComponentPanel extends HorizontalPanel implements ClickHandler{
	
	private Entity componentEntity;
	private final String COMPLISTLBL_CSS = "compRegisterLabelCss";
	private final String COMPPANEL_CSS = "compPanelCss";
	
	public ComponentPanel() {
		
	}
	
	public ComponentPanel(Entity componentEntity) {
		this.componentEntity = componentEntity;
	}
	
	public void createUi(){
		addHandler(this, ClickEvent.getType());
		LabelField nameLbl = new LabelField();
		Configuration headerLblConfig = getLblConfig(componentEntity.getPropertyByName("name").toString());
	
		nameLbl.setConfiguration(headerLblConfig);
		nameLbl.configure();
		nameLbl.create();
		
		LabelField descLbl = new LabelField();
		Configuration descLblConfig = getLblConfig(componentEntity.getPropertyByName("desc").toString());
	
		descLbl.setConfiguration(descLblConfig);
		descLbl.configure();
		descLbl.create();
		
		add(nameLbl);
		add(descLbl);
		
		setStylePrimaryName(COMPPANEL_CSS);
		
	}
	
	private Configuration getLblConfig(String componentName) {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, componentName);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, COMPLISTLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}

	@Override
	public void onClick(ClickEvent event) {
		
	}
	

}

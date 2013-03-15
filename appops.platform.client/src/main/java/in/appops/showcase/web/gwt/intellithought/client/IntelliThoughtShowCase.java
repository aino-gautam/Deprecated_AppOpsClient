package in.appops.showcase.web.gwt.intellithought.client;

import in.appops.client.common.components.IntelliThoughtWidget;
import in.appops.client.common.fields.LabelField;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class IntelliThoughtShowCase implements EntryPoint{
	
	public void onModuleLoad() {
		
		IntelliThoughtWidget shareComponent = new IntelliThoughtWidget();
		
		Configuration intelliFieldConf = getIntelliFieldConfiguration("intelliShareField", null);
		Configuration intelliShareComponentConf = getIntelliShareConfiguration();
		shareComponent.setIntelliShareFieldConfiguration(intelliFieldConf);
		try {
			shareComponent.createComponent(intelliShareComponentConf);
		} catch (AppOpsException e) {
			e.printStackTrace();
		}
		RootPanel.get().add(shareComponent);
		
	}
	
	private Configuration getIntelliShareConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(IntelliThoughtWidget.IS_INTELLISHAREFIELD, true);
		configuration.setPropertyByName(IntelliThoughtWidget.IS_ATTACHMEDIAFIELD, true);
		return configuration;
	}


	private Configuration getIntelliFieldConfiguration(String primaryCss, String secondaryCss){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		return configuration;
	}
}
package in.appops.showcase.web.gwt.contactbox.client;

import in.appops.client.common.components.IntelliThoughtWidget;
import in.appops.client.common.components.SendMessageWidget;
import in.appops.client.common.fields.ContactBoxField;
import in.appops.client.common.fields.LabelField;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class ContactBoxEntry implements EntryPoint{

	@Override
	public void onModuleLoad() {
		/*ContactBoxField contactBoxField = new ContactBoxField();
		contactBoxField.setConfiguration(getConfiguration(null, null));
		try {
			contactBoxField.createField();
		} catch (AppOpsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Configuration intelliFieldConf = getIntelliFieldConfiguration("intelliShareField", null);
		SendMessageWidget messageWidget = new  SendMessageWidget();
		messageWidget.setIntelliShareFieldConfiguration(intelliFieldConf);
		try {
			messageWidget.createComponent(getConfiguration(null, null));
		} catch (AppOpsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RootPanel.get().add(messageWidget);
	}

	private Configuration getConfiguration(String primaryCss, String secondaryCss){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		
		configuration.setPropertyByName(IntelliThoughtWidget.IS_INTELLISHAREFIELD, true);
		configuration.setPropertyByName(IntelliThoughtWidget.IS_ATTACHMEDIAFIELD, true);
		return configuration;
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

package in.appops.showcase.web.gwt.wizardform.client;

import in.appops.client.touch.Navigator;
import in.appops.client.touch.WizardForm;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class WizardFormShowcase implements EntryPoint {

	@Override
	public void onModuleLoad() {

		WizardForm form = new WizardForm();
		PersonalDetailsScreen screen1 = new PersonalDetailsScreen();
		ContactDetailsScreen screen2 = new ContactDetailsScreen();
		
		//screen1.createScreen();
		//screen2.createScreen();
		form.addScreen(screen1, 1);
		form.addScreen(screen2, 2);
		
		form.setConfiguration(getFormConfiguration());
		try {
			form.initializeForm();
		} catch (AppOpsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		form.displayScreen(1);
		
		RootPanel.get().add(form);
	}
	
	private Configuration getFormConfiguration(){
		Configuration conf = new Configuration();
		conf.setPropertyByName(WizardForm.NAVIGATOR_ALIGNMENT, WizardForm.ALIGNMENT_BOTTOM);
		
		Configuration navConf = new Configuration();
		navConf.setPropertyByName(Navigator.NAVIGATOR_ALIGNMENT, Navigator.ALIGNMENT_HORIZONTAL);
		
		conf.setPropertyByName(WizardForm.NAVIGATOR_CONFIG, navConf);
		
		return conf;
	}

}

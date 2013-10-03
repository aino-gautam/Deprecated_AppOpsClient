package in.appops.showcase.web.gwt.componentconfiguration.client.developer;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.snippet.activity.ActivityComponent;
import in.appops.client.common.snippet.activity.ActivityModel;
import in.appops.platform.core.shared.Configuration;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class AllActivityWidget extends Composite{

	private TabPanel panel;
	//private ScrollPanel updatePanel;
	private VerticalPanel updatePanelAllActivity;
	private VerticalPanel updatePanelMyActivity;


	public AllActivityWidget() {
		initialize();
	}

	public void initialize(){
		panel = new TabPanel();
		//updatePanel= new ScrollPanel();
		updatePanelAllActivity= new VerticalPanel();
		ActivityModel activityModel = new ActivityModel();
		ActivityComponent activityComponent= new ActivityComponent(activityModel);
		activityComponent.createUi();
		updatePanelAllActivity.add(activityComponent);

		updatePanelMyActivity= new VerticalPanel();
		ActivityModel myActivityModel = new ActivityModel();
		ActivityComponent myactivityComponent= new ActivityComponent(myActivityModel);
		myactivityComponent.createUi();
		updatePanelMyActivity.add(myactivityComponent);
		
		initWidget(panel);
	}

	public void createUI(){

		panel.add(updatePanelAllActivity, "All Activity");
		panel.add(updatePanelMyActivity, "My Activity");

		/*int height = Window.getClientHeight() - 150;

		updatePanel.setHeight(height+"px");*/
		updatePanelAllActivity.setStylePrimaryName("updatePanel");
		updatePanelMyActivity.setStylePrimaryName("updatePanel");

		//showUpdates();
		panel.selectTab(0);
		//	panel.setWidth("400px");
	}

	public void showUpdates(){

		VerticalPanel vPanel= new VerticalPanel();

		for(int i=0;i<10;i++){
			vPanel.add(createUpdateRecord());
		}
		updatePanelAllActivity.add(vPanel);
	}

	private Widget createUpdateRecord(){

		HorizontalPanel hPanel= new HorizontalPanel();
		final LabelField labelField = new LabelField();
		labelField.setConfiguration(getLabelFieldConfiguration(true,"appops-LabelField","You are now following project AugsApp"));
		labelField.configure();
		labelField.create();
		hPanel.add(labelField);
		hPanel.setCellHorizontalAlignment(labelField, HasHorizontalAlignment.ALIGN_CENTER);
		hPanel.setStylePrimaryName("updateRecord");
		return hPanel;
	}

	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss,String displayText){

		Configuration conf = new Configuration();
		try {
			conf.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, allowWordWrap);
			conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conf;
	}

}

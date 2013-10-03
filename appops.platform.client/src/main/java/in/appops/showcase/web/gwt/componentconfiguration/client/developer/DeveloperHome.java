package in.appops.showcase.web.gwt.componentconfiguration.client.developer;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DeveloperHome extends Composite{

	private HorizontalPanel hPanel;
	private ScrollPanel servicePanel;
	//	private VerticalPanel verticalUpdtePanel;
	//	private VerticalPanel verticalServicePanel;
	//	private HorizontalPanel updateLabelPanel;
	//	private HorizontalPanel serviceLabelPanel;
	//	private LabelField updateLabel;
	//	private LabelField serviceLabel;
	private AllActivityWidget activity;

	public DeveloperHome() {

	}

	public void initialize() {

		hPanel= new HorizontalPanel();
		servicePanel= new ScrollPanel();
		/*updateLabelPanel= new HorizontalPanel();
		verticalUpdtePanel= new VerticalPanel();
		verticalServicePanel = new VerticalPanel();
		serviceLabelPanel = new HorizontalPanel();
		updateLabel = new LabelField();
		serviceLabel= new LabelField();*/
		activity= new AllActivityWidget();
	}

	public void createUI() {

		/*updateLabel.setConfiguration(getLabelFieldConfiguration(true,"appops-LabelField","Updates"));
		updateLabel.configure();
		updateLabel.create();
		updateLabelPanel.add(updateLabel);

		serviceLabel.setConfiguration(getLabelFieldConfiguration(true,"appops-LabelField","Recent Services"));
		serviceLabel.configure();
		serviceLabel.create();
		serviceLabelPanel.add(serviceLabel);*/

		/*	verticalUpdtePanel.add(updateLabelPanel);
		verticalUpdtePanel.setCellHorizontalAlignment(updateLabelPanel, HasHorizontalAlignment.ALIGN_CENTER);

		verticalServicePanel.add(serviceLabelPanel);
		verticalServicePanel.setCellHorizontalAlignment(serviceLabelPanel, HasHorizontalAlignment.ALIGN_CENTER);

		verticalUpdtePanel.add(activity.createUI());
		verticalServicePanel.add(servicePanel);

		hPanel.add(verticalUpdtePanel);
		hPanel.add(verticalServicePanel);

		hPanel.setCellHorizontalAlignment(verticalUpdtePanel, HasHorizontalAlignment.ALIGN_LEFT);
		hPanel.setCellHorizontalAlignment(verticalServicePanel, HasHorizontalAlignment.ALIGN_RIGHT);
		 */

		activity.createUI();
		hPanel.add(activity);
		hPanel.add(servicePanel);

		hPanel.setCellHorizontalAlignment(activity, HasHorizontalAlignment.ALIGN_LEFT);
		hPanel.setCellHorizontalAlignment(servicePanel, HasHorizontalAlignment.ALIGN_RIGHT);

		hPanel.setCellWidth(activity, "70%");
		hPanel.setCellWidth(servicePanel, "30%");

		hPanel.setStylePrimaryName("mainPanel");

		int height = Window.getClientHeight() - 150;

		servicePanel.setHeight(height+"px");
		servicePanel.setStylePrimaryName("servicePanel");

		initWidget(hPanel);

	}

	public Widget createUpdateRecord(){

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

	public Widget createServiceRecord(String service){

		HorizontalPanel hPanel= new HorizontalPanel();
		final LabelField labelField = new LabelField();
		labelField.setConfiguration(getLabelFieldConfiguration(true,"appops-LabelField",service));
		labelField.configure();
		labelField.create();
		hPanel.add(labelField);
		hPanel.setCellHorizontalAlignment(labelField, HasHorizontalAlignment.ALIGN_CENTER);
		hPanel.setStylePrimaryName("serviceRecord");
		return hPanel;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getServiceRecords(){

		final VerticalPanel vPanel= new VerticalPanel();
		servicePanel.add(vPanel);

		Query query= new Query();
		query.setListSize(5);
		query.setQueryName("getAllServicesNames");
		DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
		DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);

		Map parameterMap = new HashMap();
		parameterMap.put("query", query);

		StandardAction action = new StandardAction(EntityList.class, "appdefinition.AppDefinitionService.getAllServiceList", parameterMap);
		dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result<EntityList> result) {
				if(result !=null){
					EntityList list=result.getOperationResult();
					ListIterator<Entity> itr=list.listIterator();
					while(itr.hasNext()){
						Entity entity=itr.next();
						vPanel.add(createServiceRecord(entity.getPropertyByName("name").toString()));
					}
				}else{
					//showNotification("Data not saved..");
				}
			}
		});
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

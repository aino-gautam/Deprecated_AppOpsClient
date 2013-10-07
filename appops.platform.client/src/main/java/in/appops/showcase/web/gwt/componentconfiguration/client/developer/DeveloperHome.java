package in.appops.showcase.web.gwt.componentconfiguration.client.developer;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.StateField;
import in.appops.client.common.config.field.StateField.StateFieldConstant;
import in.appops.client.common.config.field.textfield.TextField.TextFieldConstant;
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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author kamalakar@ensarm.com
 */
public class DeveloperHome extends Composite{

	private HorizontalPanel hPanel;
	private VerticalPanel servicePanel;
	private AllActivityWidget activity;

	public DeveloperHome() {

	}

	public void initialize() {

		hPanel= new HorizontalPanel();
		servicePanel= new VerticalPanel();
		activity= new AllActivityWidget();
	}

	public void createUI() {

		activity.createUI();
		hPanel.add(activity);
		hPanel.add(servicePanel);

		hPanel.setCellHorizontalAlignment(activity, HasHorizontalAlignment.ALIGN_LEFT);
		hPanel.setCellHorizontalAlignment(servicePanel, HasHorizontalAlignment.ALIGN_RIGHT);

		hPanel.setCellWidth(activity, "70%");
		hPanel.setCellWidth(servicePanel, "30%");

		hPanel.setStylePrimaryName("mainPanel");

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

		ButtonField serviceButton = new ButtonField();
		serviceButton.setConfiguration(getButtonConfiguration(service,"serviceRecord"));
		serviceButton.configure();
		serviceButton.create();

		return serviceButton;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getServiceRecords(){

		final VerticalPanel vPanel= new VerticalPanel();

		StateField serviceSuggestionbox= new StateField();
		serviceSuggestionbox.setConfiguration(getServiceSuggestBoxConfiguration());
		serviceSuggestionbox.configure();
		serviceSuggestionbox.create();

		servicePanel.add(serviceSuggestionbox);
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

	/**
	 * Creates the Service Listbox configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getServiceSuggestBoxConfiguration() {

		try {
			Configuration configuration = new Configuration();
			try {
				configuration.setPropertyByName(StateFieldConstant.IS_STATIC_BOX,false);
				configuration.setPropertyByName(StateFieldConstant.STFD_OPRTION,"appdefinition.AppDefinitionService.getAllServiceList");
				configuration.setPropertyByName(StateFieldConstant.STFD_QUERYNAME,"getAllServiceForSuggestion");
				configuration.setPropertyByName(StateFieldConstant.STFD_ENTPROP,"name");
				configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter service name");
				configuration.setPropertyByName(StateFieldConstant.STFD_QUERY_MAXRESULT,5);
				configuration.setPropertyByName(StateFieldConstant.IS_AUTOSUGGESTION,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Configuration getButtonConfiguration(String displayText,String primaryCss ){
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, displayText);
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,primaryCss);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration;
	}

}

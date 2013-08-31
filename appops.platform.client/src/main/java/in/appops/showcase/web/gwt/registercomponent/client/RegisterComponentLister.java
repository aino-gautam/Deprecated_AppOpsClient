/**
 * 
 */
package in.appops.showcase.web.gwt.registercomponent.client;

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
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author pallavi@ensarm.com
 *
 */
public class RegisterComponentLister extends Composite {

	private VerticalPanel basePanel;
	private FlexTable compListPanel;
	private int componentRow = 0;
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
	
	public RegisterComponentLister(){
		initialize();
	}

	void createUi() {
		populate(getDummyList());
	}

	private void initialize() {
		basePanel = new VerticalPanel();
		
		compListPanel = new FlexTable();
		LabelField compListLbl = new LabelField();
		Configuration compListLblConfig = getCompListLblConfig();
	
		compListLbl.setConfiguration(compListLblConfig);
		compListLbl.configure();
		compListLbl.create();
		
		basePanel.add(compListLbl);
		basePanel.add(compListPanel);
		
		basePanel.setCellHorizontalAlignment(compListLbl, HorizontalPanel.ALIGN_CENTER);
		
		initWidget(basePanel);
	}
	
	private Configuration getCompListLblConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Component List ");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	
	@SuppressWarnings("unchecked")
	private void populateComponents() {
		try {
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Query queryObj = new Query();
			queryObj.setQueryName("");
						
			Map parameterMap = new HashMap();
			parameterMap.put("query", queryObj);
			
			StandardAction action = new StandardAction(EntityList.class, "", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<EntityList> result) {
					if(result!=null){
						EntityList componentlist   = result.getOperationResult();
						if(!componentlist.isEmpty()){
							populate(componentlist);
						}
					}
				}
			});
		} catch (Exception e) {
		}
	}
	
	private void populate(EntityList componentlist) {
		
		for(Entity comp:componentlist){
			ComponentPanel componentPanel = new ComponentPanel(comp);
			componentPanel.createUi();
			compListPanel.setWidget(componentRow, 0, componentPanel);
			componentRow++;
		}
	}
	
	private EntityList getDummyList(){
		Entity labelField = new Entity();
		labelField.setPropertyByName("name","LabelField");
		labelField.setPropertyByName("desc","   Display text");
		
		Entity dateLabelField = new Entity();
		dateLabelField.setPropertyByName("name","DateLabelField");
		dateLabelField.setPropertyByName("desc","   Display date in different formats");
		
		EntityList list = new EntityList();
		list.add(labelField);
		list.add(dateLabelField);
		return list;
		
	}
}

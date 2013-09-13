package in.appops.showcase.web.gwt.componentconfiguration.client.library;

import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.client.EntityContext;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.core.util.EntityList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ViewConfigurationInstanceEditor extends Composite implements FieldEventHandler{

	private VerticalPanel basePanel;
	private VerticalPanel innerPanel;
	private ScrollPanel scrollPanel;
	private FlexTable configurationListTable;
	private ArrayList<InstanceEditor> editorList;
	private ButtonField saveConfInstanceBtnFld;
	private int currentRow = 0;
	private Entity parentConfigInstance = null;
	private Entity configTypeEntity = null;
	private Image  loaderImage = null;
	
	/** Field ID **/
	private static String SAVECONFINSTANCE_BTN_ID = "saveConfInstanceBtnId";
	
	/** CSS styles **/
	private final String SAVECONFINSTANCE_BTN_PCLS = "saveConfInstanceBtnCss";
	private final String POPUP_CSS = "popupCss";
	private final String POPUP_LBL_PCLS = "popupLbl";
	private final String POPUPGLASSPANELCSS = "popupGlassPanel";
	private final String HEADERLBL_CSS = "componentSectionHeaderLbl";
		
	public ViewConfigurationInstanceEditor() {
		
	}
	
	public ViewConfigurationInstanceEditor( Entity parentConfigInstance, Entity configTypeEntity) {
		this.parentConfigInstance = parentConfigInstance;
		this.configTypeEntity = configTypeEntity;
	}
	
	public void createUi(){
		try {
			innerPanel = new VerticalPanel();
			
			loaderImage = new Image("images/opptinLoader.gif");
			loaderImage.setStylePrimaryName("appops-intelliThoughtActionImage");
			loaderImage.setVisible(true);
			innerPanel.add(loaderImage);
			
			basePanel = new VerticalPanel();
			scrollPanel = new ScrollPanel(innerPanel);
			scrollPanel.setHeight("300px");
			/*scrollPanel.setWidth("100%");*/
			
			
			saveConfInstanceBtnFld = new ButtonField();
			saveConfInstanceBtnFld.setConfiguration(getDoneBtnConfig());
			saveConfInstanceBtnFld.configure();
			saveConfInstanceBtnFld.create();
			
			basePanel.add(scrollPanel);
			basePanel.add(saveConfInstanceBtnFld);
			
			initWidget(basePanel);
			basePanel.setWidth("100%");
			basePanel.setHeight("100%");
			
			AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
					
			Long configTypeId = ((Key<Long>)configTypeEntity.getPropertyByName("id")).getKeyValue();
			
			getConfigTypeEntity(configTypeId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Configuration getDoneBtnConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Done");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,SAVECONFINSTANCE_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, SAVECONFINSTANCE_BTN_ID);
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_TITLE, "Save config Instance");
		}
		catch(Exception e){
			
		}
		return configuration;
	}
	
	/**
	 * 
	 * @param configTypeEntity
	 */
	private void initialize(Entity configTypeEntity) {
		try {
			loaderImage.setVisible(false);
			innerPanel.clear();
			String name = null;
			if (configTypeEntity.getPropertyByName("configtypes") != null) {
				ArrayList<Entity> entityList = configTypeEntity.getPropertyByName("configtypes");
				HashMap<String, EntityList> configTypeHashMap = new HashMap<String, EntityList>();

				for (Entity configTypeEnt : entityList) {
					name = configTypeEnt.getPropertyByName("keyname");
					if (configTypeHashMap.containsKey(name)) {

						EntityList existingConfigTypeList = configTypeHashMap.get(name);
						existingConfigTypeList.add(configTypeEnt);
						configTypeHashMap.put(name, existingConfigTypeList);
					} else {
						EntityList list = new EntityList();
						list.add(configTypeEnt);
						configTypeHashMap.put(name, list);
					}
				}
				
				createEditor(configTypeHashMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addHeaderLabel(){
		
		LabelField nameLbl = new LabelField();
		Configuration nameLblConfig = getLabelFieldConf("Instance Name",HEADERLBL_CSS,null);
	
		nameLbl.setConfiguration(nameLblConfig);
		nameLbl.configure();
		nameLbl.create();
		
		LabelField descLbl = new LabelField();
		Configuration valueLblConfig = getLabelFieldConf("Instance Value",HEADERLBL_CSS,null);
	
		descLbl.setConfiguration(valueLblConfig);
		descLbl.configure();
		descLbl.create();
		
		configurationListTable.setWidget(currentRow, 0, nameLbl);
		configurationListTable.setWidget(currentRow, 1, descLbl);
		
		
		currentRow++;
	}
	
	
	private void createEditor(HashMap<String, EntityList> configTypeHashMap) {
		try {
			if(editorList==null)
				editorList = new ArrayList<InstanceEditor>();
			
			configurationListTable = new FlexTable();
			addHeaderLabel();
			
			Iterator it = configTypeHashMap.entrySet().iterator();
			
			while (it.hasNext()) {
			    Map.Entry pairs = (Map.Entry)it.next();
			    EntityList list = (EntityList) pairs.getValue();
			    InstanceEditor instanceEditor = new InstanceEditor(configurationListTable, currentRow ,pairs.getKey().toString(), list);
			    instanceEditor.createUi();
			    editorList.add(instanceEditor);
			    currentRow++;
			}
			
			
			innerPanel.add(configurationListTable);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void getConfigTypeEntity(Long configTypeId) {
		try {
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync dispatch = new StandardDispatchAsync(exceptionHandler);

			Map parameterMap = new HashMap();
			parameterMap.put("configurationId", configTypeId);

			StandardAction action = new StandardAction(Entity.class,"appdefinition.AppDefinitionService.getConfigurationType",parameterMap);
			
			dispatch.execute(action, new AsyncCallback<Result<Entity>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<Entity> result) {
					if (result != null) {
						Entity configType = result.getOperationResult();
						if (configType != null) {
							initialize(configType);
						}else{
							showNotificationForEmptyConfList();
						}
					}

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showNotificationForEmptyConfList() {
		try {
			innerPanel.clear();
			LabelField notificationMessageField = createLabelField("No configurations available ","componentSectionHeaderLbl","");
			innerPanel.add(notificationMessageField);
			innerPanel.setCellHorizontalAlignment(notificationMessageField, HasHorizontalAlignment.ALIGN_CENTER);
			scrollPanel.setStylePrimaryName("propertyConfListScrollPanel");
			configurationListTable.setStylePrimaryName("configurationListTable");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private LabelField createLabelField(String name, String primaryCss,String secondaryCss) {
		try{
			LabelField labelField = new LabelField();
			labelField.setConfiguration(createLabelConfiguration(name,primaryCss));
			labelField.configure();
			labelField.create();
			return labelField;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private Configuration createLabelConfiguration(String text, String css) {
		Configuration configuration = new Configuration();
		try{
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, text);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, css);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;

	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try{
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			
			if (event.getEventType() == FieldEvent.CLICKED) {
				if (event.getEventSource() instanceof ButtonField){
					ButtonField doneBtnField = (ButtonField) eventSource;
					if (doneBtnField.getBaseFieldId().equals(SAVECONFINSTANCE_BTN_ID)) {
						if(editorList!=null){
							saveConfigInstances();				
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private EntityList getConfigurationInstanceList() {
		try {
			EntityList list = new EntityList();
			for (int index = 0; index < editorList.size(); index++) {
				InstanceEditor editor = editorList.get(index);
				Entity instanceEnt = editor.getPopulatedConfigInstanceEntity();
				
				EntityContext context = new EntityContext();
				instanceEnt.setPropertyByName("context", context);
				instanceEnt.setProperty("configinstance", parentConfigInstance);
				instanceEnt.setProperty("configtype", configTypeEntity);
				
				list.add(instanceEnt);
			}
			return list;
			
		} catch (Exception e) {
			if(e instanceof AppOpsException){
				AppOpsException ex = (AppOpsException) e;
				showPopup(ex.getMsg());
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private void saveConfigInstances() {
		try {
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync dispatch = new StandardDispatchAsync(exceptionHandler);

			Map parameterMap = new HashMap();
			parameterMap.put("confInstEntityList", getConfigurationInstanceList());
			
			StandardAction action = new StandardAction(EntityList.class, "appdefinition.AppDefinitionService.saveConfigurationInstanceList", parameterMap);
			
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<EntityList> result) {
					if (result != null) {
						EntityList configInstanceList = result.getOperationResult();
						if (configInstanceList != null) {
							showPopup("Configuration Instances saved successfully");
						}
					}

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Used to show popup at perticular position.
	 * @param popuplabel
	 */
	private void showPopup(String popuplabel){
		try {
			LabelField popupLbl = new LabelField();
			popupLbl.setConfiguration(getLabelFieldConf(popuplabel,POPUP_LBL_PCLS,null));
			popupLbl.configure();
			popupLbl.create();
					
			PopupPanel popup = new PopupPanel();
			popup.setAnimationEnabled(true);
			popup.setAutoHideEnabled(true);
			popup.setGlassEnabled(true);
			popup.setGlassStyleName(POPUPGLASSPANELCSS);
			popup.setStylePrimaryName(POPUP_CSS);
			popup.add(popupLbl);
			popup.setPopupPosition(542, 70);
			popup.show();
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Creates the table name label field configuration object and return.
	 * @param displayText
	 * @param primaryCss
	 * @param dependentCss
	 * @param propEditorLblPanelCss
	 * @return Configuration instance
	 */
	private Configuration getLabelFieldConf(String displayText , String primaryCss , String dependentCss ){
		Configuration conf = new Configuration();
		try {
			conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
			conf.setPropertyByName(LabelFieldConstant.BF_DCLS, dependentCss);
			
		} catch (Exception e) {
			
		}
		return conf;
	}
}

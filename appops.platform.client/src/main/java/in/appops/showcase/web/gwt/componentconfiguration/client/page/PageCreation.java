package in.appops.showcase.web.gwt.componentconfiguration.client.page;

import in.appops.client.common.config.dsnip.HTMLProcessor;
import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.config.field.StateField;
import in.appops.client.common.config.field.StateField.StateFieldConstant;
import in.appops.client.common.config.field.suggestion.AppopsSuggestion;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.client.common.util.AppEnviornment;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.client.EntityContext;
import in.appops.platform.client.EntityContextGenerator;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.services.platform.coreplatformservice.constant.ServiceConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PageCreation extends Composite implements FieldEventHandler {

	private VerticalPanel basePanel;
	
	private static final String APP_LISTBOX_ID = "appListBoxFieldId";
	private static final String PAGE_LISTBOX_ID = "pageListBoxFieldId";
	private static final String CREATE_PAGE_BUTTON_ID = "createPageButtonFieldId";
	private static final String TEXTAREA_FIELD_ID = "textAreaFieldId";
	private static final String PROCESS_PAGE_BUTTON_ID = "processPageButtonFieldId";
	
	private static String SELECT_PAGE_PANEL_CSS = "selectPagePanel";
	private static String CREATE_PAGE_BUTTON_CSS = "createPageButton";
	private static String CREATE_PAGE_BUTTON_PANEL_CSS = "createPageButtonPanel";
	private static String EDIT_PAGE_LABEL_CSS = "editPageLabel";
	private static String EDIT_PAGE_PANEL_CSS = "editPagePanel";
	private static String PROCESS_PAGE_BUTTON_CSS = "processPageButton";
	private static String PAGECREATION_BASEPANEL_CSS = "pageCreationBasePanel";
	private static String NEW_PAGE_TITLE_LABEL_CSS = "newPageTitleLabel";
	private static String NEW_PAGE_NAME_PANEL_CSS = "newPageNamePanel";
	
	private final String POPUP_CSS = "popupCss";
	private final String POPUP_LBL_PCLS = "popupLbl";
	
	private StateField serviceSuggestionbox;
	private ListBoxField appListbox;
	private ListBoxField pageListbox;
	private TextField htmltextArea;
	private TextField pageNametextField;
	private ButtonField processPageButton;
	private ButtonField createPageButton;
	private Entity appEntity;
	private Entity serviceEntity;
	private VerticalPanel pageConfigPanel;
	
	public PageCreation() {
		initialize();
		createUI();
		initWidget(basePanel);
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}
	
	public void initialize() {
		basePanel = new VerticalPanel();
		pageConfigPanel = new VerticalPanel();
	}
	
	public void createUI() {
		try {
			basePanel.clear();
			HorizontalPanel panelToSelectPage = createPanelToSelectPage();
			HorizontalPanel createPageButtonPanel = createPageButtonPanel();
			HorizontalPanel newPageNamePanel = createNewPageNamePanel();
			VerticalPanel editPagePanel = createEditPagePanel();
			
			basePanel.add(panelToSelectPage);
			basePanel.add(createPageButtonPanel);
			basePanel.add(newPageNamePanel);
			basePanel.add(editPagePanel);
			basePanel.add(pageConfigPanel);
			pageConfigPanel.setWidth("100%");
			basePanel.setStylePrimaryName(PAGECREATION_BASEPANEL_CSS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private VerticalPanel createEditPagePanel() {
		try {
			VerticalPanel editPagePanel = new VerticalPanel();
			
			LabelField titleLabelField = new LabelField();
			titleLabelField.setConfiguration(getEditPageLabelConfig());
			titleLabelField.configure();
			titleLabelField.create();
			editPagePanel.add(titleLabelField);
			
			htmltextArea = new TextField();
			htmltextArea.setConfiguration(getTextAreaFieldConfiguration());
			htmltextArea.configure();
			htmltextArea.create();
			editPagePanel.add(htmltextArea);
			
			processPageButton = new ButtonField();
			processPageButton.setConfiguration(getProcessPageButtonConfiguration());
			processPageButton.configure();
			processPageButton.create();
			editPagePanel.add(processPageButton);
			editPagePanel.setCellHorizontalAlignment(processPageButton, HasHorizontalAlignment.ALIGN_RIGHT);
			
			editPagePanel.setStylePrimaryName(EDIT_PAGE_PANEL_CSS);
			return editPagePanel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private HorizontalPanel createPageButtonPanel() {
		try {
			HorizontalPanel createPageButtonPanel = new HorizontalPanel();
			
			createPageButton = new ButtonField();
			createPageButton.setConfiguration(getCreatePageButtonConfiguration());
			createPageButton.configure();
			createPageButton.create();
			
			createPageButtonPanel.add(createPageButton);
			createPageButtonPanel.setCellHorizontalAlignment(createPageButton, HasHorizontalAlignment.ALIGN_RIGHT);
			createPageButtonPanel.setStylePrimaryName(CREATE_PAGE_BUTTON_PANEL_CSS);
			
			return createPageButtonPanel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private HorizontalPanel createPanelToSelectPage() {
		try {
			HorizontalPanel panelToSelectPage = new HorizontalPanel();
			
			serviceSuggestionbox = new StateField();
			serviceSuggestionbox.setConfiguration(getServiceSuggestBoxConfiguration());
			serviceSuggestionbox.configure();
			serviceSuggestionbox.create();
			
			appListbox = new ListBoxField();
			appListbox.setConfiguration(getAppListBoxConfiguration(false,null));
			appListbox.configure();
			appListbox.create();
			
			pageListbox = new ListBoxField();
			pageListbox.setConfiguration(getPageListBoxConfiguration(false,null));
			pageListbox.configure();
			pageListbox.create();
			
			panelToSelectPage.add(serviceSuggestionbox);
			panelToSelectPage.add(appListbox);
			panelToSelectPage.add(pageListbox);
			
			panelToSelectPage.setCellWidth(serviceSuggestionbox, "33%");
			panelToSelectPage.setCellWidth(appListbox, "33%");
			panelToSelectPage.setCellWidth(pageListbox, "33%");
			panelToSelectPage.setCellHorizontalAlignment(appListbox, HasHorizontalAlignment.ALIGN_CENTER);
			panelToSelectPage.setCellHorizontalAlignment(pageListbox, HasHorizontalAlignment.ALIGN_RIGHT);
			panelToSelectPage.setStylePrimaryName(SELECT_PAGE_PANEL_CSS);
			
			return panelToSelectPage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the Page Listbox configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getPageListBoxConfiguration(boolean isEnabled, HashMap<String, Object> paramMap) {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,PAGE_LISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--Please select a page--");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ENABLED, isEnabled);
			
			if(paramMap != null) {
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getEntityList");
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getAllPagesForApp");
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERY_RESTRICTION,paramMap);
			} else {
				ArrayList<String> items = new ArrayList<String>();
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,items);
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates the App Listbox configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getAppListBoxConfiguration(boolean isEnabled, HashMap<String, Object> paramMap) {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,APP_LISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--Please select an app--");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ENABLED, isEnabled);
			
			if(paramMap != null) {
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getEntityList");
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getAllAppsForService");
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERY_RESTRICTION,paramMap);
			} else {
				ArrayList<String> items = new ArrayList<String>();
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,items);
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	
	/**
	 * Creates the create new Page button configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getCreatePageButtonConfiguration(){
		try {
			Configuration configuration = new Configuration();
			try {
				configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Create new page");
				configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,CREATE_PAGE_BUTTON_CSS);
				configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, true);
				configuration.setPropertyByName(ButtonFieldConstant.BF_ID, CREATE_PAGE_BUTTON_ID);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the edit Page label configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getEditPageLabelConfig() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Enter page html");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, EDIT_PAGE_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the TextAreaField configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getTextAreaFieldConfiguration(){
		try {
			Configuration configuration = new Configuration();
			try {
				configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTTYPE_TXTAREA);
				configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
				configuration.setPropertyByName(TextFieldConstant.BF_ID, TEXTAREA_FIELD_ID);
				configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, 15);
				configuration.setPropertyByName(TextFieldConstant.TF_CHARWIDTH, 80);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the Process page button configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getProcessPageButtonConfiguration() {
		try {
			Configuration configuration = new Configuration();
			try {
				configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Process page");
				configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,PROCESS_PAGE_BUTTON_CSS);
				configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, true);
				configuration.setPropertyByName(ButtonFieldConstant.BF_ID, PROCESS_PAGE_BUTTON_ID);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public HorizontalPanel createNewPageNamePanel() {
		HorizontalPanel newPageNamePanel = new HorizontalPanel();
		
		LabelField titleLabelField = new LabelField();
		titleLabelField.setConfiguration(getNewPageTitleLabelConfig());
		titleLabelField.configure();
		titleLabelField.create();
		newPageNamePanel.add(titleLabelField);
		
		pageNametextField = new TextField();
		pageNametextField.setConfiguration(getTextFieldConfiguration());
		pageNametextField.configure();
		pageNametextField.create();
		newPageNamePanel.add(pageNametextField);
		
		newPageNamePanel.setCellWidth(titleLabelField, "20%");
		newPageNamePanel.setCellVerticalAlignment(titleLabelField, HasVerticalAlignment.ALIGN_MIDDLE);
		newPageNamePanel.setStylePrimaryName(NEW_PAGE_NAME_PANEL_CSS);
		return newPageNamePanel;
	}
	
	private Configuration getTextFieldConfiguration() {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, 1);
			configuration.setPropertyByName(TextFieldConstant.BF_READONLY, false);
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_POS, TextFieldConstant.BF_SUGGESTION_INLINE);
			configuration.setPropertyByName(TextFieldConstant.BF_SUGGESTION_TEXT, "Enter field value");
			configuration.setPropertyByName(TextFieldConstant.BF_VALIDATEONCHANGE, true);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, TextFieldConstant.BF_SIDE);
			configuration.setPropertyByName(TextFieldConstant.TF_MAXLENGTH, 100);
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Configuration getNewPageTitleLabelConfig() {
		try {
			Configuration configuration = null;	
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Enter Page Name");
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, NEW_PAGE_TITLE_LABEL_CSS);
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		if(eventType == FieldEvent.SUGGESTION_SELECTED) {
			if(event.getEventSource() instanceof StateField) {
				if (event.getEventSource().equals(serviceSuggestionbox)) {
					if (event.getEventData() instanceof AppopsSuggestion) {
						htmltextArea.setValue("");
						pageNametextField.setValue("");

						AppopsSuggestion selectedSuggestion = (AppopsSuggestion) event.getEventData();
						serviceEntity = selectedSuggestion.getEntity();
						AppEnviornment.CURRENTSERVICE = serviceEntity;
						Long serviceId = ((Key<Long>) serviceEntity.getPropertyByName(ServiceConstant.ID)).getKeyValue();
						HashMap<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("serviceId", serviceId);

						appListbox.setConfiguration(getAppListBoxConfiguration(true, paramMap));
						appListbox.configure();
						appListbox.create();
					}
				}
			}
		} else if(eventType == FieldEvent.CLICKED) {
			if(event.getEventSource() instanceof ButtonField) {
				ButtonField source = (ButtonField) event.getEventSource();
				if(source.equals(createPageButton)) {
					htmltextArea.setValue("");
					pageNametextField.setValue("");
					((TextBox) pageNametextField.getWidget()).setFocus(true);
					pageListbox.setValue(pageListbox.getSuggestionValueForListBox());
				} else if(source.equals(processPageButton)) {
					boolean isEnabled = appListbox.isFieldEnabled();
					if(isEnabled) {
						String value = (String) appListbox.getValue();
						if(value.equals(appListbox.getSuggestionValueForListBox())) {
							showPopup("Please select an App");
						} else {
							ArrayList<Element> appopsContainerFields = validateHTML();
							if(appopsContainerFields != null) {
								saveComponentDef(appopsContainerFields);
							} else {
								showPopup("Html format not valid");
							}
						}
					} else {
						showPopup("Please select an App");
					}
				}
			}
		} else if(eventType == FieldEvent.VALUECHANGED) {
			if(event.getEventSource() instanceof ListBoxField) {
				ListBoxField source = (ListBoxField) event.getEventSource();
				if(source.equals(appListbox)) {
					htmltextArea.setValue("");
					pageNametextField.setValue("");
					String value = (String) source.getValue();
					if(value.equals(source.getSuggestionValueForListBox())) {
						pageListbox.setConfiguration(getPageListBoxConfiguration(false,null));
						pageListbox.configure();
						pageListbox.create();
					} else {
						appEntity = source.getAssociatedEntity(value);
						AppEnviornment.CURRENTAPP = appEntity;
						Long appId = ((Key<Long>)appEntity.getPropertyByName("id")).getKeyValue();
						HashMap<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("appId", appId);
						
						pageListbox.setConfiguration(getPageListBoxConfiguration(true,paramMap));
						pageListbox.configure();
						pageListbox.create();
					}
				} else if(source.equals(pageListbox)) {
					String value = (String) source.getValue();
					if(value.equals(source.getSuggestionValueForListBox())) {
						htmltextArea.setValue("");
						pageNametextField.setValue("");
						pageConfigPanel.clear();
					} else {
						Entity pageEntity = source.getAssociatedEntity(value);
						String pageName = pageEntity.getPropertyByName("name").toString();
						pageNametextField.setValue(pageName);
						fetchComponentDefinationEnt(pageEntity);
						/*ConfigEvent configEvent = new ConfigEvent(ConfigEvent.SHOWPAGECONFIGURATION, null, this);
						AppUtils.EVENT_BUS.fireEvent(configEvent);*/
					}
				}
			}
		}
	}

	private void fetchComponentDefinationEnt(Entity pageEntity) {
		
	}
	
	private ArrayList<Element> validateHTML(){
		String htmlStr = htmltextArea.getFieldText();
		NodeList<Element> spans = HTMLProcessor.getSpanElementsFromHTML(htmlStr);
		if(spans != null){
			ArrayList<Element> appopsContainerFields = HTMLProcessor.getContainerElements(spans);
			if(appopsContainerFields != null){
				return appopsContainerFields;
			}
		}
		return null;
	}
	
	private Entity getComponentInstEntity() {
		try{
			Entity compEntity = new Entity();
			compEntity.setType(new MetaType("Componentinstance"));
			compEntity.setPropertyByName("instancename", getPageName());
			compEntity.setPropertyByName("htmldescription", htmltextArea.getValue().toString());
			compEntity.setProperty("componentdefinition", getComponentDefinitionEnt());
			compEntity.setProperty("appEnt", appEntity);
			return compEntity;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Entity getComponentDefinitionEnt() {
		try{
			Entity compoDefEntity = new Entity();
			compoDefEntity.setType(new MetaType("Componentdefinition"));
			Key<Long> key = new Key<Long>(65L);
			Property<Key<Long>> keyProp = new Property<Key<Long>>(key);
			compoDefEntity.setProperty("id", keyProp);
			compoDefEntity.setPropertyByName("name", "Page");
			compoDefEntity.setPropertyByName("typeId", 179L);
			compoDefEntity.setPropertyByName("isMvp", 0);
			return compoDefEntity;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void saveComponentDef(final ArrayList<Element> appopsContainerFields) {
		try{
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Entity componentInstEnt = getComponentInstEntity();
			Map parameterMap = new HashMap();
			parameterMap.put("componentInstEnt", componentInstEnt);
			
			Key<Long> appKey = appEntity.getPropertyByName("id");
			Long appId = appKey.getKeyValue();
			EntityContext appContext  = EntityContextGenerator.defineContext(null, appId);

			Key<Long> serviceKey = serviceEntity.getPropertyByName("id");
			Long serviceId = serviceKey.getKeyValue();
			EntityContext context  = appContext.defineContext(serviceId);

			parameterMap.put("context", appContext);
			
			StandardAction action = new StandardAction(HashMap.class, "appdefinition.AppDefinitionService.createPage", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<HashMap<String, Entity>>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
				}

				@Override
				public void onSuccess(Result<HashMap<String, Entity>> result) {
					if(result!=null){
						pageConfigPanel.clear();
						PageConfiguration pageConfig = new PageConfiguration();
						pageConfigPanel.add(pageConfig);
						
						HashMap<String, Entity> entityMap = result.getOperationResult();
						if(entityMap != null && !entityMap.isEmpty()) {
							Entity pageCompInstEnt = entityMap.get("pageCompInstEntity");
							Entity pageEnt = entityMap.get("pageEntity");
							if(pageCompInstEnt != null) {
								pageConfig.setPageComponentInstEntity(pageCompInstEnt);
								pageConfig.setPageEntity(pageEnt);
							}
						}
						pageConfig.populateSpansListBox(appopsContainerFields);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getPageName() {
		String name = pageNametextField.getValue().toString();
		if(name.contains(" ")) {
			if(name.startsWith(" ")) {
				name = name.substring(1);
			}
			if(name.endsWith(" ")) {
				name = name.subSequence(0, name.length()).toString();
			}
			name = name.replace(" ", "_");
		}
		return name;
	}
	
	/**
	 * Used to show popup at perticular position.
	 * @param popuplabel
	 */
	private void showPopup(String popuplabel){
		try {
			LabelField popupLbl = new LabelField();
			popupLbl.setConfiguration(getLabelFieldConf(popuplabel,POPUP_LBL_PCLS,null,null));
			popupLbl.configure();
			popupLbl.create();
					
			PopupPanel popup = new PopupPanel();
			popup.setAnimationEnabled(true);
			popup.setAutoHideEnabled(true);
			popup.setStylePrimaryName(POPUP_CSS);
			popup.add(popupLbl);
			popup.center();
		} catch (Exception e) {
			e.printStackTrace();
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
	private Configuration getLabelFieldConf(String displayText , String primaryCss , String dependentCss ,String propEditorLblPanelCss){
		Configuration conf = new Configuration();
		try {
			conf.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, displayText);
			conf.setPropertyByName(LabelFieldConstant.BF_PCLS, primaryCss);
			conf.setPropertyByName(LabelFieldConstant.BF_DCLS, dependentCss);
			conf.setPropertyByName(LabelFieldConstant.BF_BASEPANEL_PCLS, propEditorLblPanelCss);
		} catch (Exception e) {
			
		}
		return conf;
	}
}
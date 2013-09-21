package in.appops.showcase.web.gwt.componentconfiguration.client.page;

import in.appops.client.common.config.dsnip.HTMLProcessor;
import in.appops.client.common.config.field.BaseField.BaseFieldConstant;
import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.config.field.SelectedItem;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.server.core.service.appdefinition.domain.Componentdefinition;
import in.appops.showcase.web.gwt.componentconfiguration.client.library.HTMLSnippetConfigurationEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User for creating html snippets and processing them and managing their configurations.
 * @author nairutee
 *
 */
public class SnippetManager extends Composite implements FieldEventHandler {
	
	private VerticalPanel basePanel;
	private TextField snippetNameTextBox ;
	private TextField snippetHtmlTextArea ;
	private ButtonField saveAndProcessButton;
	private LabelField headerLf;
	private ListBoxField libraryBox;
	private Entity libraryEntity;
	private VerticalPanel propConfigEditorVp = new VerticalPanel();

	private Logger logger = Logger.getLogger("SnippetManager");
	
	private final String SAVECOMP_BTN_PCLS = "saveCompBtnCss";
	private final String HEADERLBL_CSS = "headerLabel";
	private final String REGULARLBL_CSS = "regularLabel";
	private final String TEXTFIELD_CSS = "textField";
	private HandlerRegistration fieldEventHandler =  null;
	private ArrayList<HTMLSnippetConfigurationEditor> htmlEditorList = null;
	
	/** Field id**/
	public static final String LIBRARYLISTBOX_ID = "snippetLibraryListBoxId";
	private static String SAVE_PROCESS_SNIPPET_BTN_ID = "saveProcessSnippetBtnId";
	
	/**
	 * Constructor
	 */
	public SnippetManager() {
		if(fieldEventHandler == null)
			fieldEventHandler = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}

	public void deregisterHandler(){
		fieldEventHandler.removeHandler();
		
		if(htmlEditorList!= null)
			deregisterPreviousInstances();
	}
	
	private void deregisterPreviousInstances(){
		for(HTMLSnippetConfigurationEditor htmlConfigurationEditor :htmlEditorList){
			htmlConfigurationEditor.deregisterHandler();
		}
		htmlEditorList.clear();
	}
	
	/**
	 * initializes the widget
	 */
	public void initialize() {
		basePanel = new VerticalPanel();
		basePanel.setWidth("100%");
		if(htmlEditorList == null)
			htmlEditorList = new ArrayList<HTMLSnippetConfigurationEditor>();
		
		initWidget(basePanel);
		createSnippetRegistrationUI();
	}
	
	/**
	 * Constructs the UI
	 */
	private void createSnippetRegistrationUI(){
		FlexTable containerTable = new FlexTable();
		
		/* list box for library selection */
		libraryBox = new ListBoxField();
		libraryBox.setConfiguration(getLibraryListBoxConfiguration());
		libraryBox.configure();
		libraryBox.create();
		
		/* text box for entering component name*/
		snippetNameTextBox = new TextField();
		Configuration nameTfConfig = getSnippetNameTextBoxConfig();
		snippetNameTextBox.setConfiguration(nameTfConfig);
		snippetNameTextBox.configure();
		snippetNameTextBox.create();
		
		/* heading label for snippet html text area */
		LabelField headerLbl = new LabelField();
		Configuration headerLblConfig = getHeaderLblConfig("Enter the snippet html in the text area below: ");
		headerLbl.setConfiguration(headerLblConfig);
		headerLbl.configure();
		headerLbl.create();
		
		/* text area for entering component html */
		snippetHtmlTextArea = new TextField();
		Configuration descTfConfig = getSnippetHtmlTextAreaConfig();
		snippetHtmlTextArea.setConfiguration(descTfConfig);
		snippetHtmlTextArea.configure();
		snippetHtmlTextArea.create();
		
		/* button to process & save the component */
		saveAndProcessButton = new ButtonField();
		Configuration savebTnConfig = getSaveAndProcessBtnConfig();
		saveAndProcessButton.setConfiguration(savebTnConfig);
		saveAndProcessButton.configure();
		saveAndProcessButton.create();
		
		headerLf = new LabelField();
		headerLf.setConfiguration(getHeaderLblConfig("Snippet Creation"));
		headerLf.configure();
		headerLf.create();
		
		
		containerTable.setWidget(0, 0, libraryBox);
		containerTable.setWidget(1, 0, snippetNameTextBox);
		containerTable.setWidget(2, 0, headerLbl);
		containerTable.setWidget(3, 0, snippetHtmlTextArea);
		containerTable.setWidget(4, 0, saveAndProcessButton);
		
		basePanel.add(headerLf);
		basePanel.setCellHorizontalAlignment(headerLf, HorizontalPanel.ALIGN_CENTER);
		basePanel.add(containerTable);
		basePanel.add(propConfigEditorVp);
	}

	/**
	 * initializes the configuration editing component and lists the span elements available.
	 */
	public void createConfigurationEditorUI(HashMap<String, Entity> list, ArrayList<Element> spansList){
		propConfigEditorVp.setWidth("100%");
		propConfigEditorVp.clear();

		HTML html = new HTML("<hr style=\"border-bottom : 1px dotted gray;\" \"width:100%;\"/>");
		
		LabelField headerLbl = new LabelField();
		Configuration headerLblConfig = getHeaderLblConfig(" Snippet Configurations ");
		headerLbl.setConfiguration(headerLblConfig);
		headerLbl.configure();
		headerLbl.create();
		
		propConfigEditorVp.add(html);
		propConfigEditorVp.add(headerLbl);
		
		HTMLSnippetConfigurationEditor configEditor = new HTMLSnippetConfigurationEditor();
		Entity viewConfigEntity   = list.get("viewConfigType");
		Entity modelConfigEntity   = list.get("modelConfigType");
		configEditor.setModelConfigurationType(modelConfigEntity);
		configEditor.setViewConfigurationType(viewConfigEntity);
		configEditor.getViewEditor().populateSpansListBox(spansList);
		configEditor.createUi();
		
		deregisterPreviousInstances();
		htmlEditorList.add(configEditor);
		
		propConfigEditorVp.add(configEditor);
		
	}
	
	private ArrayList<Element> validateHTML(){
		String htmlStr = snippetHtmlTextArea.getFieldText();
		NodeList<Element> spans = HTMLProcessor.getSpanElementsFromHTML(htmlStr);
		if(spans != null){
			ArrayList<Element> appopsFields = HTMLProcessor.getAppopsFieldElements(spans);
			if(appopsFields != null){
				return appopsFields;
			}
		}
		return null;
	}
	

	
	/**
	 * method for setting configuration properties for the library listbox
	 * @return
	 */
	private Configuration getLibraryListBoxConfiguration() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,LIBRARYLISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getLibraries");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getAllLibraries");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--- Please select a library---");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "SnippetManager :: getLibraryListBoxConfiguration :: Exception", e);
		}
		return configuration;
	}
	
	/**
	 * method for setting configuration properties for the snippet name text box
	 * @return
	 */
	private Configuration getSnippetNameTextBoxConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, TEXTFIELD_CSS);
			configuration.setPropertyByName(TextFieldConstant.VALIDATEFIELD, false);
			configuration.setPropertyByName(TextFieldConstant.TF_CHARWIDTH, 500);
			configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, "Enter component name");

		}
		catch(Exception e){
			e.printStackTrace();
			logger.log(Level.SEVERE, "SnippetManager :: getSnippetNameTextBoxConfig :: Exception", e);
		}
		return configuration;
	}
	
	/**
	 * method for setting configuration properties for the snippet html text area
	 * @return
	 */
	private Configuration getSnippetHtmlTextAreaConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTTYPE_TXTAREA);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, TEXTFIELD_CSS);
			configuration.setPropertyByName(TextFieldConstant.VALIDATEFIELD, false);
			configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, 10);
			configuration.setPropertyByName(TextFieldConstant.TF_CHARWIDTH, 120);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, BaseFieldConstant.BF_BOTTOM);
			configuration.setPropertyByName(TextFieldConstant.BF_INVLDMSG,"Invalid HTML format. No span elements found!");
			configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, "Enter snippet html here");

		}
		catch(Exception e){
			e.printStackTrace();
			logger.log(Level.SEVERE, "SnippetManager :: getSnippetHtmlTextAreaConfig :: Exception", e);
		}
		return configuration;
	}
	
	private Configuration getSaveAndProcessBtnConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Process and Save Snippet");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,SAVECOMP_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, true);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, SAVE_PROCESS_SNIPPET_BTN_ID);

		}
		catch(Exception e){
			e.printStackTrace();
			logger.log(Level.SEVERE, "SnippetManager :: getSaveAndProcessBtnConfig :: Exception", e);
		}
		return configuration;
	}

	/**
	 * configuration settings for labels that are used as headers
	 * @param labelText
	 * @return
	 */
	private Configuration getHeaderLblConfig(String labelText) {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, labelText);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
			logger.log(Level.SEVERE, "SnippetManager :: getHeaderLblConfig :: Exception", e);
		}
		return configuration;
	}
	
	/**
	 * settings for configuration of label fields used to display normal text
	 * @param labelText
	 * @return
	 */
	private Configuration getLblConfig(String labelText) {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, labelText);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, REGULARLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
			logger.log(Level.SEVERE, "SnippetManager :: getLblConfig :: Exception", e);
		}
		return configuration;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case FieldEvent.CLICKED: {
				if (eventSource instanceof ButtonField) {
					ButtonField btnField = (ButtonField) eventSource;
					if (btnField.getBaseFieldId().equals(SAVE_PROCESS_SNIPPET_BTN_ID)) {
						if(libraryEntity!=null){
							ArrayList<Element> spansList = validateHTML();
							if(spansList != null && !spansList.isEmpty())
								saveComponent(spansList);
							else
								Window.alert("Html snippet not in proper format");
						}else
							Window.alert("Please select a library");
					}
				}
				break;
			}
			case FieldEvent.VALUECHANGED:{
				if(eventSource instanceof ListBoxField){
					ListBoxField listBoxField = (ListBoxField) eventSource;
					SelectedItem selectedItem = (SelectedItem) event.getEventData();
					if(listBoxField.getBaseFieldId().equalsIgnoreCase(SnippetManager.LIBRARYLISTBOX_ID)){
						Entity libEntity = selectedItem.getAssociatedEntity();
						libraryEntity = libEntity;
					}
				}
			}
			default:
				break;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "SnippetManager :: onFieldEvent :: Exception", e);
		}
	}
	
	/**
	 * Saves the html snippet 
	 */
	@SuppressWarnings("unchecked")
	private void saveComponent(final ArrayList<Element> spanList) {
		try{
			DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
			DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
			
			Entity componentDefEnt = getComponentDefinitionEntity();
			Map parameterMap = new HashMap();
			parameterMap.put("componentDefinition", componentDefEnt);
			parameterMap.put("library", libraryEntity);
			
			StandardAction action = new StandardAction(Entity.class, "appdefinition.AppDefinitionService.saveMVPComponentDefinition", parameterMap);
			dispatch.execute(action, new AsyncCallback<Result<HashMap<String, Entity>>>() {

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					logger.log(Level.SEVERE, "SnippetManager :: saveComponent :: Exception", caught);
				}

				@Override
				public void onSuccess(Result<HashMap<String, Entity>> result) {
					if(result!=null){
						HashMap<String, Entity> list = result.getOperationResult();
						Entity compEntity   = list.get("component");
						if(compEntity!=null){
							Window.alert("Component Saved...");
							createConfigurationEditorUI(list, spanList);
						}
					}
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ComponentRegistrationForm :: saveComponent :: Exception", e);
		}

	}
	
	/**
	 * creates the componentDefinition entity that needs to be saved.
	 * @return {@link Componentdefinition}
	 */
	private Entity getComponentDefinitionEntity() {
		
		try{
			Entity compEntity = new Entity();
			compEntity.setType(new MetaType("Componentdefinition"));

			compEntity.setPropertyByName("name", snippetNameTextBox.getValue().toString());
			compEntity.setPropertyByName("htmldescription", snippetHtmlTextArea.getValue().toString());
			compEntity.setPropertyByName("typeId",159L); // ems typeId for html snipppet
			
			return compEntity;
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "SnippetManager :: getComponentDefinitionEntity :: Exception", e);
		}
		return null;
		
	}
}